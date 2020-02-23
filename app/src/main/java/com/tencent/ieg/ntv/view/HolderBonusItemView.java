package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetCDNFile;
import com.tencent.ieg.ntv.event.EventGetCDNFileFinish;
import com.tencent.ieg.ntv.event.EventHolderItemUpdate;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.HolderBonusItemClick;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;

public class HolderBonusItemView extends FrameLayout {
    /* access modifiers changed from: private */
    public static final String TAG = HolderBonusItemView.class.getSimpleName();
    /* access modifiers changed from: private */
    public Button bonusBtn;
    /* access modifiers changed from: private */
    public ImageView bonusIcon;
    private TextView bonusSubTitle;
    private TextView bonusTitle;
    private ImageView divider;
    private Button doneBtn;
    /* access modifiers changed from: private */
    public String iconUrl;
    private IEventListener mEventListener = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            Bitmap bitmap;
            if (eventId == 3002) {
                EventGetCDNFileFinish evt = (EventGetCDNFileFinish) event;
                if (HolderBonusItemView.this.iconUrl.equals(evt.url)) {
                    if (!(evt.data == null || (bitmap = BitmapFactory.decodeByteArray(evt.data, 0, evt.data.length)) == null || HolderBonusItemView.this.bonusIcon == null)) {
                        HolderBonusItemView.this.bonusIcon.setImageBitmap(bitmap);
                    }
                    EventManager.getInstance().unregister(eventId, this);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean mOutOfDate = false;
    /* access modifiers changed from: private */
    public short m_itemid;
    /* access modifiers changed from: private */
    public short m_status;
    private View.OnClickListener onBtnClick = new View.OnClickListener() {
        public void onClick(View view) {
            if (HolderBonusItemView.this.m_status == BonusItemStatus.RECEIVABLE && !HolderBonusItemView.this.mOutOfDate) {
                EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ITEM_CLICK, new HolderBonusItemClick(HolderBonusItemView.this.m_itemid, BonusItemStatus.RECEIVED));
            }
        }
    };
    private IEventListener onEventUpdate = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            Logger.d(HolderBonusItemView.TAG, "onCountingUpdate in. m_itemid=" + HolderBonusItemView.this.m_itemid + ", m_status=" + HolderBonusItemView.this.m_status + ", eventId=" + eventId);
            if (eventId == 5107) {
                boolean unused = HolderBonusItemView.this.mOutOfDate = false;
                EventHolderItemUpdate evt = (EventHolderItemUpdate) event;
                if (evt != null && evt.mItemId == HolderBonusItemView.this.m_itemid) {
                    HolderBonusItemView.this.setBonusBtnStatus(evt.btnLable, evt.mStatus);
                }
            } else if (eventId == 5109) {
                boolean unused2 = HolderBonusItemView.this.mOutOfDate = true;
                if (HolderBonusItemView.this.m_status == BonusItemStatus.RECEIVABLE) {
                    HolderBonusItemView.this.bonusBtn.setBackgroundResource(R.drawable.common_button_middle_disable);
                    HolderBonusItemView.this.bonusBtn.setTextColor(HolderBonusItemView.this.getResources().getColor(R.color.ntvs_common_color_grey));
                }
            }
        }
    };

    public static class BonusItemStatus {
        public static short NOTYET = 0;
        public static short RECEIVABLE = 1;
        public static short RECEIVED = 2;
    }

    public HolderBonusItemView(@NonNull Context context) {
        super(context);
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.holder_bonus_item_view, this);
        this.bonusIcon = (ImageView) findViewById(R.id.ntv_holder_bonus_item_icon);
        this.bonusTitle = (TextView) findViewById(R.id.ntv_holder_bonus_item_title);
        this.bonusSubTitle = (TextView) findViewById(R.id.ntv_holder_bonus_item_subtitle);
        this.bonusBtn = (Button) findViewById(R.id.ntv_holder_bonus_item_btn);
        this.doneBtn = (Button) findViewById(R.id.ntv_holder_bonus_item_btn_done);
        this.divider = (ImageView) findViewById(R.id.ntv_holder_bonus_item_divider);
        this.bonusBtn.setOnClickListener(this.onBtnClick);
        this.doneBtn.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TASK_RECEIVED));
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_ITEM_UPDATE, this.onEventUpdate);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_OUT_OF_DATE, this.onEventUpdate);
        this.mOutOfDate = false;
    }

    public void setBonusIcon(String url) {
        this.iconUrl = url;
        try {
            this.bonusIcon.setImageBitmap((Bitmap) null);
        } catch (Exception e) {
        }
        EventManager.getInstance().register(3002, this.mEventListener);
        EventManager.getInstance().post(3001, new EventGetCDNFile(this.iconUrl));
    }

    public void setBonusTitle(String dec) {
        if (this.bonusTitle != null) {
            this.bonusTitle.setText(dec);
        }
    }

    public void setBonusSubTitle(String name) {
        if (this.bonusSubTitle != null) {
            this.bonusSubTitle.setText(name);
        }
    }

    public void setBonusBtnStatus(String btnlabel, short status) {
        this.m_status = status;
        if (this.bonusBtn != null) {
            this.bonusBtn.setText(btnlabel);
        }
        if (status == BonusItemStatus.RECEIVABLE) {
            this.bonusBtn.setVisibility(0);
            this.doneBtn.setVisibility(4);
            this.bonusBtn.setBackgroundResource(R.drawable.common_button_middle_yellow);
            this.bonusBtn.setTextColor(getResources().getColor(R.color.ntvs_common_color_white));
        } else if (status == BonusItemStatus.RECEIVED) {
            this.bonusBtn.setVisibility(4);
            this.doneBtn.setVisibility(0);
        } else {
            this.bonusBtn.setVisibility(0);
            this.doneBtn.setVisibility(4);
            this.bonusBtn.setBackgroundResource(R.drawable.common_button_middle_disable);
            this.bonusBtn.setTextColor(getResources().getColor(R.color.ntvs_common_color_grey));
        }
    }

    public void setDividerVisibility(int visibility) {
        this.divider.setVisibility(visibility);
    }

    public void setOtherDataForUse(short itemid, short status) {
        this.m_itemid = itemid;
        this.m_status = status;
    }
}
