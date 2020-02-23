package com.tencent.ieg.ntv.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.HolderBonusController;
import com.tencent.ieg.ntv.ctrl.MatchController;
import com.tencent.ieg.ntv.ctrl.chat.ChatManager;
import com.tencent.ieg.ntv.ctrl.player.PlayerController;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventChatTips;
import com.tencent.ieg.ntv.event.EventHolderBonusUpdateTime;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventRedDotInfoData;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.RedDotHelper;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.ieg.ntv.view.NTVShowActivity;
import java.util.ArrayList;
import java.util.List;

public class LobbyContentFragment extends BaseContentFragment {
    /* access modifiers changed from: private */
    public static final String TAG = LobbyContentFragment.class.getSimpleName();
    private boolean bIconStypeOpen;
    ConstraintLayout com_tip;
    TextView com_tip_text;
    public boolean isActive = false;
    CheckableImageView lobby_tab_0 = null;
    TextView lobby_tab_0_txt = null;
    CheckableImageView lobby_tab_1 = null;
    ImageView lobby_tab_1_dot = null;
    TextView lobby_tab_1_txt = null;
    private ConstraintLayout mHolderBonusHall;
    private ImageView mHolderBonusIcon;
    private TextView mHolderBonusTimes;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            if (view.getId() == R.id.ntv_holder_bonus_icon) {
                LobbyContentFragment.this.mPlayerController.onHolderBonus();
            }
        }
    };
    /* access modifiers changed from: private */
    public PlayerController mPlayerController;
    /* access modifiers changed from: private */
    public List<RedDotInfo> mRedDotInfoList;
    private IEventListener onCheckShowHolderBonus = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            LobbyContentFragment.this.checkifShowHolderBonus();
        }
    };
    private IEventListener onComTipsEvtUI = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventChatTips evt = (EventChatTips) event;
            String tipText = (evt == null || evt.tipText == null) ? "" : evt.tipText;
            Logger.d(LobbyContentFragment.TAG, "onComTipsEvtUI tipTest:" + tipText);
            if (LobbyContentFragment.this.com_tip_text != null) {
                LobbyContentFragment.this.com_tip_text.setText(tipText);
            }
            if (LobbyContentFragment.this.com_tip != null) {
                LobbyContentFragment.this.com_tip.setVisibility(evt.show ? 0 : 8);
            }
        }
    };
    private IEventListener onRedDotInfoListener = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            List unused = LobbyContentFragment.this.mRedDotInfoList = ((EventRedDotInfoData) event).redDotInfoList;
            if (LobbyContentFragment.this.mRedDotInfoList != null) {
                for (RedDotInfo redDotInfo : LobbyContentFragment.this.mRedDotInfoList) {
                    if (redDotInfo.getType().equals(RedDotInfo.RedDotType.TYPE_MATCHINFO) && LobbyContentFragment.this.tv.getCurrentItem() != 1) {
                        RedDotHelper.getInstance().updateRedDot(LobbyContentFragment.this.lobby_tab_1_dot, redDotInfo);
                        return;
                    }
                }
            }
        }
    };
    private NTVShowActivity.TABEventListener onTabEvent = new NTVShowActivity.TABEventListener() {
        public void onTabChange(int idx) {
            Logger.d(LobbyContentFragment.TAG, "onTabChange:" + idx);
            if (LobbyContentFragment.this.mPlayerController != null) {
                if (idx == 0) {
                    LobbyContentFragment.this.mPlayerController.resume();
                    LobbyContentFragment.this.isActive = true;
                    return;
                }
                LobbyContentFragment.this.mPlayerController.pause();
                LobbyContentFragment.this.isActive = false;
            }
        }
    };
    private IEventListener onUpdateTimeText = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventHolderBonusUpdateTime updateEvt = (EventHolderBonusUpdateTime) event;
            LobbyContentFragment.this.setHolderBonusTimesText(updateEvt.timeText, updateEvt.openBox);
        }
    };
    FDTabView tv;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.ntvs_lobby_view, container, false);
        initUI(v);
        return v;
    }

    private boolean initUI(View v) {
        this.tv = (FDTabView) v.findViewById(R.id.ntvs_subveiw_tabview);
        List<BaseContentFragment> lf = new ArrayList<>();
        lf.add(new ChatContentFragment());
        lf.add(new MatchScheduleFragment());
        this.tv.init(getActivity().getSupportFragmentManager(), lf);
        this.tv.setOffscreenPageLimit(2);
        ((ImageView) v.findViewById(R.id.ntvs_lobby_tab_0)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.com_tip = (ConstraintLayout) v.findViewById(R.id.tv_center_com_tip);
        this.com_tip_text = (TextView) v.findViewById(R.id.tv_center_com_tip_text);
        if (this.com_tip != null) {
            this.com_tip.setVisibility(8);
        }
        EventManager.getInstance().register(5021, this.onComTipsEvtUI);
        this.lobby_tab_0 = (CheckableImageView) v.findViewById(R.id.ntvs_lobby_tab_0);
        this.lobby_tab_0.setChecked(true);
        this.lobby_tab_1 = (CheckableImageView) v.findViewById(R.id.ntvs_lobby_tab_1);
        this.lobby_tab_1.setChecked(false);
        this.lobby_tab_0_txt = (TextView) v.findViewById(R.id.ntvs_lobby_tab_0_txt);
        highLightTab(this.lobby_tab_0_txt, true);
        this.lobby_tab_1_txt = (TextView) v.findViewById(R.id.ntvs_lobby_tab_1_txt);
        highLightTab(this.lobby_tab_1_txt, false);
        this.lobby_tab_0_txt.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_LOBBY_TAB_0_NAME));
        this.lobby_tab_1_txt.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_LOBBY_TAB_1_NAME));
        this.lobby_tab_1_dot = (ImageView) v.findViewById(R.id.ntvs_lobby_tab_1_dot);
        this.lobby_tab_0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LobbyContentFragment.this.lobby_tab_0.setChecked(true);
                LobbyContentFragment.this.lobby_tab_1.setChecked(false);
                LobbyContentFragment.this.highLightTab(LobbyContentFragment.this.lobby_tab_0_txt, true);
                LobbyContentFragment.this.highLightTab(LobbyContentFragment.this.lobby_tab_1_txt, false);
                LobbyContentFragment.this.tv.setCurrentItem(0, false);
            }
        });
        this.lobby_tab_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TVShowManager.getInstance().onReportEvent("ntv_match_tab_click");
                LobbyContentFragment.this.lobby_tab_1.setChecked(true);
                LobbyContentFragment.this.lobby_tab_0.setChecked(false);
                LobbyContentFragment.this.highLightTab(LobbyContentFragment.this.lobby_tab_0_txt, false);
                LobbyContentFragment.this.highLightTab(LobbyContentFragment.this.lobby_tab_1_txt, true);
                LobbyContentFragment.this.tv.setCurrentItem(1, false);
                if (LobbyContentFragment.this.mRedDotInfoList != null) {
                    for (RedDotInfo redDotInfo : LobbyContentFragment.this.mRedDotInfoList) {
                        if (redDotInfo.getType().equals(RedDotInfo.RedDotType.TYPE_MATCHINFO)) {
                            RedDotHelper.getInstance().clearRedDot(LobbyContentFragment.this.lobby_tab_1_dot, redDotInfo);
                            return;
                        }
                    }
                }
            }
        });
        EventManager.getInstance().register(5007, this.onRedDotInfoListener);
        this.mHolderBonusHall = (ConstraintLayout) v.findViewById(R.id.ntv_holder_bonus_hall);
        this.mHolderBonusIcon = (ImageView) v.findViewById(R.id.ntv_holder_bonus_icon);
        this.mHolderBonusTimes = (TextView) v.findViewById(R.id.ntv_holder_bonus_timetxt);
        this.mHolderBonusIcon.setOnClickListener(this.mOnClickListener);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_ON_SHOW_OR_NOT, this.onCheckShowHolderBonus);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_UPDATE_TIMETEXT, this.onUpdateTimeText);
        checkifShowHolderBonus();
        return true;
    }

    /* access modifiers changed from: private */
    public void checkifShowHolderBonus() {
        this.mHolderBonusHall.setVisibility(HolderBonusController.getInstance().ifShowRewardBonus() ? 0 : 8);
    }

    /* access modifiers changed from: private */
    public void setHolderBonusTimesText(String text, boolean openBox) {
        Logger.d(TAG, "setHolderBonusTimesText : " + text + ", openbox=" + openBox);
        this.mHolderBonusTimes.setText(text);
        if (openBox != this.bIconStypeOpen) {
            Drawable drawable = getResources().getDrawable(R.drawable.rewardbox_close);
            if (openBox) {
                drawable = getResources().getDrawable(R.drawable.rewardbox_open);
            }
            this.bIconStypeOpen = openBox;
            this.mHolderBonusIcon.setImageDrawable(drawable);
        }
    }

    private void ifShowChatView() {
        if (Util.getMConfig(NTVDefine.KCONF_CHAT_MODULE_OPEN).equals("0")) {
            this.lobby_tab_0.setChecked(false);
            this.lobby_tab_0.setVisibility(8);
            this.lobby_tab_1.setChecked(true);
            highLightTab(this.lobby_tab_0_txt, false);
            this.lobby_tab_0_txt.setVisibility(8);
            highLightTab(this.lobby_tab_1_txt, true);
            this.tv.setCurrentItem(1);
        }
    }

    /* access modifiers changed from: private */
    public void highLightTab(TextView textView, boolean highlight) {
        if (highlight) {
            textView.setTextColor(getResources().getColor(R.color.ntvs_main_tab_txt_color_selected));
        } else {
            textView.setTextColor(getResources().getColor(R.color.ntvs_main_tab_txt_color));
        }
    }

    public NTVShowActivity.TABEventListener getTabEventListener() {
        return this.onTabEvent;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.mPlayerController = PlayerController.getInstance();
        this.mPlayerController.init(getActivity(), (PlayerView) getView().findViewById(R.id.lobby_vedio_player_view));
        ChatManager.GetInstance().init(true, getActivity());
        ifShowChatView();
        MatchController.getInstance().init(getActivity());
    }

    public void onResume() {
        super.onResume();
        if (this.isActive) {
            this.mPlayerController.resume();
        }
    }

    public void onPause() {
        if (this.isActive) {
            this.mPlayerController.pause();
        }
        super.onPause();
    }

    public void onShow() {
        super.onShow();
    }

    public void onHidden() {
        super.onHidden();
    }
}
