package com.tencent.ieg.ntv.ctrl;

import android.app.Activity;
import android.content.Context;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventDeliverRewardResult;
import com.tencent.ieg.ntv.event.EventHolderBonusUpdateTime;
import com.tencent.ieg.ntv.event.EventHolderItemUpdate;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.EventShowHolderBonusList;
import com.tencent.ieg.ntv.event.EventShowHolderBonusTips;
import com.tencent.ieg.ntv.event.HolderBonusItemClick;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.network.RewardInfo;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.ieg.ntv.view.HolderBonusCountInfo;
import com.tencent.ieg.ntv.view.HolderBonusItemInfo;
import com.tencent.ieg.ntv.view.HolderBonusItemView;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HolderBonusController {
    /* access modifiers changed from: private */
    public static final String TAG = HolderBonusController.class.getSimpleName();
    private static HolderBonusController sInstance;
    private List<HolderBonusCountInfo> countTargets;
    /* access modifiers changed from: private */
    public Timer countTimer;
    private long isecTillStart = 1800000;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public long mCount = 0;
    private IEventListener onDeliverResult = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventDeliverRewardResult evt = (EventDeliverRewardResult) event;
            final short result = evt.mResult;
            final short rewardid = evt.mRewardId;
            final short itemid = evt.mItemId;
            final short status = evt.mStatus;
            if (evt.mResult == 0) {
                RewardInfo rinfo = NetworkModule.getInstance().getRewardInfo();
                boolean update = false;
                if (!(rinfo == null || rinfo.itemList == null)) {
                    for (int i = 0; i < rinfo.itemList.size(); i++) {
                        HolderBonusItemInfo hinfo = rinfo.itemList.get(i);
                        if (hinfo != null && hinfo.itemid == itemid) {
                            rinfo.itemList.get(i).status = status;
                            update = true;
                        }
                    }
                }
                if (update) {
                    NetworkModule.getInstance().updateRewardInfo_local(rinfo);
                }
                ((Activity) HolderBonusController.this.mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        if (status == HolderBonusItemView.BonusItemStatus.RECEIVED) {
                            HolderBonusController.this.showHolderBonusTips(result, rewardid, itemid, status);
                        }
                        HolderBonusController.this.checkRewardBonus();
                        if (result == 0) {
                            EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ITEM_UPDATE, new EventHolderItemUpdate(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_BTNRECEIVE), itemid, status));
                            Logger.d(HolderBonusController.TAG, "onDeliverResult EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ITEM_UPDATE, evt); itemid=" + itemid);
                        }
                    }
                });
            }
        }
    };
    private IEventListener onRewardInfoResult = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            Logger.d(HolderBonusController.TAG, "onRewardInfoResult in.");
            ((Activity) HolderBonusController.this.mContext).runOnUiThread(new Runnable() {
                public void run() {
                    HolderBonusController.this.checkRewardBonus();
                }
            });
            if (HolderBonusController.this.initCountingTarget()) {
                long unused = HolderBonusController.this.mCount = 0;
                HolderBonusController.this.startTimer();
            } else if (HolderBonusController.this.countTimer != null) {
                HolderBonusController.this.countTimer.cancel();
            }
        }
    };

    public static HolderBonusController getInstance() {
        if (sInstance == null) {
            sInstance = new HolderBonusController();
        }
        return sInstance;
    }

    public void destroy() {
        EventManager.getInstance().unregister(NTVDefine.EVT_HOLDER_BONUS_ON_DELIVER_RESULT, this.onDeliverResult);
        EventManager.getInstance().unregister(NTVDefine.EVT_HOLDER_BONUS_ON_REWARDINFO_RESULT, this.onRewardInfoResult);
    }

    public void init(Context context) {
        this.mContext = context;
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_ON_DELIVER_RESULT, this.onDeliverResult);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_ON_REWARDINFO_RESULT, this.onRewardInfoResult);
    }

    public void requestHolderDeliverReward(HolderBonusItemClick evt) {
        NetworkModule.getInstance().sendHolderDeliverRewardRequest(NetworkModule.getInstance().getRewardInfo().sRewardId, evt.sItemId, evt.sStatus);
    }

    /* access modifiers changed from: private */
    public boolean initCountingTarget() {
        Logger.d(TAG, "initCountingTarget in.");
        RewardInfo info = NetworkModule.getInstance().getRewardInfo();
        if (info == null || info.sResult != 0) {
            Logger.d(TAG, "startCounting - false, info == null || info.sResult != 0");
            return false;
        }
        if (this.countTargets != null) {
            this.countTargets.clear();
        } else {
            this.countTargets = new LinkedList();
        }
        long curMSec = Util.getCurrentMSeconds();
        long st = ((long) info.iStartTime) * 1000;
        if (curMSec >= ((long) info.iEndTime) * 1000) {
            Logger.d(TAG, "startCounting - false, curMSec < st - isecTillStart || curMSec >= et");
            return false;
        } else if (curMSec < st) {
            HolderBonusCountInfo ifo = new HolderBonusCountInfo(HolderBonusCountInfo.CountTargetType.TYPE_SHOW_HALL_ICON, 0, st);
            Logger.d(TAG, "initCountingTarget target0:" + (st - curMSec));
            this.countTargets.add(ifo);
            return true;
        } else {
            boolean hasReceivable = false;
            for (int i = 0; i < info.itemList.size(); i++) {
                HolderBonusItemInfo itemInfo = info.itemList.get(i);
                if (itemInfo.status == HolderBonusItemView.BonusItemStatus.NOTYET) {
                    long tt = ((long) itemInfo.viewtime) * 1000;
                    HolderBonusCountInfo ifo2 = new HolderBonusCountInfo(HolderBonusCountInfo.CountTargetType.TYPE_COUNT_TO_TARGET, itemInfo.itemid, tt);
                    Logger.d(TAG, "initCountingTarget target1:" + tt);
                    this.countTargets.add(ifo2);
                } else if (itemInfo.status == HolderBonusItemView.BonusItemStatus.RECEIVABLE) {
                    hasReceivable = true;
                }
            }
            String timeText = "-:-";
            if (this.countTargets.size() != 0) {
                timeText = formatTimeText(this.countTargets.get(0).mTime);
            } else if (hasReceivable) {
                timeText = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_CAN_RECEIVE);
            }
            updateTimeTextUI(timeText, hasReceivable);
            if (this.countTargets.size() != 0 || hasReceivable) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void startTimer() {
        if (this.countTimer != null) {
            this.countTimer.cancel();
        }
        this.countTimer = new Timer();
        this.countTimer.schedule(new TimerTask() {
            public void run() {
                long unused = HolderBonusController.this.mCount = HolderBonusController.this.mCount + 1000;
                HolderBonusController.this.triggerTarget();
                HolderBonusController.this.updateTimesText();
            }
        }, 1000, 1000);
    }

    /* access modifiers changed from: private */
    public void triggerTarget() {
        Logger.d(TAG, "triggerTarget in.");
        if (this.countTargets != null && this.countTargets.size() > 0) {
            int i = 0;
            while (true) {
                if (i >= this.countTargets.size()) {
                    break;
                }
                HolderBonusCountInfo info = this.countTargets.get(i);
                if (info.mType == HolderBonusCountInfo.CountTargetType.TYPE_SHOW_HALL_ICON) {
                    if (Util.getCurrentMSeconds() >= info.mTime) {
                        NetworkModule.getInstance().sendHolderRewardInfoRequest();
                        if (this.countTimer != null) {
                            this.countTimer.cancel();
                        }
                    }
                } else if (info.mType == HolderBonusCountInfo.CountTargetType.TYPE_COUNT_TO_TARGET && (this.mCount == info.mTime || (this.mCount > info.mTime - 1000 && this.mCount < info.mTime + 1000))) {
                    RewardInfo rinfo = NetworkModule.getInstance().getRewardInfo();
                    boolean update = false;
                    if (!(rinfo == null || rinfo.itemList == null)) {
                        for (int j = 0; j < rinfo.itemList.size(); j++) {
                            if (rinfo.itemList.get(j) != null && info.mItemId == rinfo.itemList.get(j).itemid) {
                                rinfo.itemList.get(j).status = HolderBonusItemView.BonusItemStatus.RECEIVABLE;
                                update = true;
                            }
                        }
                    }
                    if (update) {
                        NetworkModule.getInstance().updateRewardInfo_local(rinfo);
                        initCountingTarget();
                        Logger.d(TAG, "triggerTarget NetworkModule.getInstance().updateRewardInfo_local(rinfo);");
                    }
                    final short sitemid = info.mItemId;
                    ((Activity) this.mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ITEM_UPDATE, new EventHolderItemUpdate(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_BTNRECEIVE), sitemid, HolderBonusItemView.BonusItemStatus.RECEIVABLE));
                            Logger.d(HolderBonusController.TAG, "triggerTarget EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ITEM_UPDATE, evt);");
                        }
                    });
                    requestHolderDeliverReward(new HolderBonusItemClick(info.mItemId, HolderBonusItemView.BonusItemStatus.RECEIVABLE));
                }
                i++;
            }
        }
        checkIfOutofdate();
    }

    private void checkIfOutofdate() {
        Logger.d(TAG, "checkIfOutofdate in.");
        RewardInfo info = NetworkModule.getInstance().getRewardInfo();
        if (info != null && info.iEndTime != 0 && Util.getCurrentMSeconds() >= ((long) info.iEndTime) * 1000) {
            ((Activity) this.mContext).runOnUiThread(new Runnable() {
                public void run() {
                    HolderBonusController.this.checkRewardBonus();
                    EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_OUT_OF_DATE, (Event) null);
                    Logger.d(HolderBonusController.TAG, "checkIfOutofdate EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_OUT_OF_DATE, null);");
                }
            });
            new Timer().schedule(new TimerTask() {
                public void run() {
                    cancel();
                    NetworkModule.getInstance().sendHolderRewardInfoRequest();
                }
            }, 1000);
        }
    }

    /* access modifiers changed from: private */
    public void updateTimesText() {
        String txt;
        Logger.d(TAG, "updateTimesText in.");
        long targetT = 0;
        if (this.countTargets != null && this.countTargets.size() > 0) {
            for (int i = 0; i < this.countTargets.size(); i++) {
                HolderBonusCountInfo info = this.countTargets.get(i);
                if (info.mType == HolderBonusCountInfo.CountTargetType.TYPE_COUNT_TO_TARGET) {
                    if (targetT == 0) {
                        targetT = info.mTime;
                    } else if (info.mTime < targetT) {
                        targetT = info.mTime;
                    }
                }
            }
        }
        if (targetT != 0) {
            boolean openbox = false;
            Logger.d(TAG, "updateTimesText mCount=" + this.mCount + ", targetT=" + targetT);
            if (this.mCount < targetT) {
                txt = formatTimeText(targetT - this.mCount);
            } else {
                txt = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_CAN_RECEIVE);
                openbox = true;
                Logger.d(TAG, "updateTimesText mCount >= targetT");
            }
            RewardInfo info2 = NetworkModule.getInstance().getRewardInfo();
            if (!(info2 == null || info2.itemList == null)) {
                for (int i2 = 0; i2 < info2.itemList.size(); i2++) {
                    HolderBonusItemInfo fo = info2.itemList.get(i2);
                    if (fo != null && fo.status == HolderBonusItemView.BonusItemStatus.RECEIVABLE) {
                        openbox = true;
                        Logger.d(TAG, "updateTimesText fo.status == HolderBonusItemView.BonusItemStatus.RECEIVABLE");
                    }
                }
            }
            updateTimeTextUI(txt, openbox);
        }
    }

    private String formatTimeText(long time) {
        int leftTs = (int) (time / 1000);
        int m = leftTs / 60;
        String sh = String.valueOf(leftTs / 3600);
        String sm = String.valueOf(m);
        String ss = String.valueOf(leftTs - (m * 60));
        StringBuilder append = new StringBuilder().append(sh.equals("0") ? "" : sh + ":");
        if (sm.length() == 1) {
            sm = "0" + sm;
        }
        StringBuilder append2 = append.append(sm).append(":");
        if (ss.length() == 1) {
            ss = "0" + ss;
        }
        return append2.append(ss).toString();
    }

    private void updateTimeTextUI(final String text, final boolean openbox) {
        Logger.d(TAG, "updateTimeTextUI in.");
        ((Activity) this.mContext).runOnUiThread(new Runnable() {
            public void run() {
                EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_UPDATE_TIMETEXT, new EventHolderBonusUpdateTime(text, openbox));
            }
        });
    }

    /* access modifiers changed from: private */
    public void checkRewardBonus() {
        Logger.d(TAG, "checkRewardBonus in.");
        EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ON_SHOW_OR_NOT, (Event) null);
    }

    public boolean ifShowRewardBonus() {
        RewardInfo info = NetworkModule.getInstance().getRewardInfo();
        if (info == null || info.sResult != 0) {
            return false;
        }
        long curMSec = Util.getCurrentMSeconds();
        long st = ((long) info.iStartTime) * 1000;
        long et = ((long) info.iEndTime) * 1000;
        if (curMSec < st || curMSec >= et || info.itemList == null || info.itemList.size() == 0) {
            return false;
        }
        boolean allGot = true;
        int i = 0;
        while (true) {
            if (i >= info.itemList.size()) {
                break;
            }
            HolderBonusItemInfo fo = info.itemList.get(i);
            if (fo.status == HolderBonusItemView.BonusItemStatus.NOTYET || fo.status == HolderBonusItemView.BonusItemStatus.RECEIVABLE) {
                allGot = false;
            } else {
                i++;
            }
        }
        allGot = false;
        if (allGot) {
            return false;
        }
        return true;
    }

    public void showHolderBonusList() {
        EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_SHOW_LIST_UI, new EventShowHolderBonusList(NetworkModule.getInstance().getRewardInfo()));
    }

    public void showHolderBonusTips(short result, short rewardId, short itemId, short status) {
        RewardInfo rewardInfo = NetworkModule.getInstance().getRewardInfo();
        String itemName = "";
        String iconUrl = "";
        if (!(rewardInfo == null || rewardInfo.itemList == null)) {
            int i = 0;
            while (true) {
                if (i >= rewardInfo.itemList.size()) {
                    break;
                } else if (rewardInfo.itemList.get(i).itemid == itemId) {
                    itemName = rewardInfo.itemList.get(i).itemName;
                    iconUrl = rewardInfo.itemList.get(i).iconUrl;
                    break;
                } else {
                    i++;
                }
            }
            if (itemName == null || itemName.length() == 0) {
                itemName = "";
                Logger.w(TAG, "showHolderBonusTips - itemid:" + itemId + " 's name not found.");
            }
        }
        EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_SHOW_TIPS_UI, new EventShowHolderBonusTips(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_RECEIVE_DESC).replace("{0}", ""), iconUrl, itemName));
    }
}
