package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.HolderBonusController;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetCDNFile;
import com.tencent.ieg.ntv.event.EventGetCDNFileFinish;
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
import java.util.ArrayList;

public class HolderBonusFragment extends FrameLayout {
    /* access modifiers changed from: private */
    public static final String TAG = HolderBonusFragment.class.getSimpleName();
    private ImageView alphabg;
    /* access modifiers changed from: private */
    public HolderBonusController holderBonusController;
    private HolderBonusItemAdapter itemAdapter = null;
    private Context mContext;
    /* access modifiers changed from: private */
    public IEventListener mItemIconUrlEventListener = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            Bitmap bitmap;
            EventGetCDNFileFinish evt = (EventGetCDNFileFinish) event;
            if (HolderBonusFragment.this.pannelTips_citem_icon_url.equals(evt.url)) {
                if (!(evt.data == null || (bitmap = BitmapFactory.decodeByteArray(evt.data, 0, evt.data.length)) == null || HolderBonusFragment.this.pannelTips_citem_icon == null)) {
                    HolderBonusFragment.this.pannelTips_citem_icon.setImageBitmap(bitmap);
                }
                EventManager.getInstance().unregister(eventId, this);
            }
        }
    };
    /* access modifiers changed from: private */
    public RewardInfo mRewardInfo;
    private IEventListener onBonusItemClick = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            HolderBonusFragment.this.holderBonusController.requestHolderDeliverReward((HolderBonusItemClick) event);
        }
    };
    private View.OnClickListener onClose = new View.OnClickListener() {
        public void onClick(View view) {
            HolderBonusFragment.this.setVisibility(8);
        }
    };
    private View.OnClickListener onCloseTips = new View.OnClickListener() {
        public void onClick(View view) {
            HolderBonusFragment.this.pannelTip.setVisibility(8);
            HolderBonusFragment.this.pannelTipBgCover.setVisibility(8);
        }
    };
    private IEventListener onShowBonusPannel = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            RewardInfo unused = HolderBonusFragment.this.mRewardInfo = ((EventShowHolderBonusList) event).mRewardInfo;
            if (HolderBonusFragment.this.mRewardInfo != null && HolderBonusFragment.this.mRewardInfo.itemList != null) {
                HolderBonusFragment.this.setVisibility(0);
                HolderBonusFragment.this.pannelBonus.setVisibility(0);
                HolderBonusFragment.this.pannelTip.setVisibility(8);
                HolderBonusFragment.this.updatePannelBonusData(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TITLE), TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_END_TIME).replace("{0}", String.format("<font color='#f4dba6'>%s</font>", new Object[]{Util.getEndTimeString((long) HolderBonusFragment.this.mRewardInfo.iEndTime)})), HolderBonusFragment.this.mRewardInfo.itemList);
            }
        }
    };
    private IEventListener onShowTipsPannel = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventShowHolderBonusTips evt = (EventShowHolderBonusTips) event;
            if (evt != null) {
                HolderBonusFragment.this.setVisibility(0);
                HolderBonusFragment.this.pannelTipBgCover.setVisibility(0);
                HolderBonusFragment.this.pannelTip.setVisibility(0);
                HolderBonusFragment.this.pannelTips_ctext.setText(evt.tipText);
                HolderBonusFragment.this.pannelTips_citem_name.setText(evt.iconName);
                if (evt.iconUrl != null && evt.iconUrl.length() > 5) {
                    String unused = HolderBonusFragment.this.pannelTips_citem_icon_url = evt.iconUrl;
                    EventManager.getInstance().register(3002, HolderBonusFragment.this.mItemIconUrlEventListener);
                    EventManager.getInstance().post(3001, new EventGetCDNFile(evt.iconUrl));
                }
            }
        }
    };
    private IEventListener onUpdateBonusPannel = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            RewardInfo rinfo;
            Logger.d(HolderBonusFragment.TAG, "onUpdateBonusPannel");
            if (HolderBonusFragment.this.pannelBonus.getVisibility() == 0 && (rinfo = NetworkModule.getInstance().getRewardInfo()) != null) {
                String titleStr = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_TITLE);
                String descStr = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_END_TIME).replace("{0}", String.format("<font color='#f4dba6'%s</font>", new Object[]{Util.getEndTimeString((long) rinfo.iEndTime)}));
                if (rinfo.itemList == null) {
                    rinfo.itemList = new ArrayList<>();
                }
                HolderBonusFragment.this.updatePannelBonusData(titleStr, descStr, rinfo.itemList);
            }
        }
    };
    /* access modifiers changed from: private */
    public ConstraintLayout pannelBonus;
    private TextView pannelBonus_dec;
    private ListView pannelBonus_list;
    private TextView pannelBonus_title;
    /* access modifiers changed from: private */
    public ConstraintLayout pannelTip;
    /* access modifiers changed from: private */
    public ImageView pannelTipBgCover;
    private Button pannelTips_btn;
    /* access modifiers changed from: private */
    public ImageView pannelTips_citem_icon;
    /* access modifiers changed from: private */
    public String pannelTips_citem_icon_url;
    /* access modifiers changed from: private */
    public TextView pannelTips_citem_name;
    /* access modifiers changed from: private */
    public TextView pannelTips_ctext;
    private TextView pannelTips_title;

    public HolderBonusFragment(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initUI();
        initUIEvents();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.ntvs_holder_bonus_ui, this);
        setVisibility(8);
        this.alphabg = (ImageView) findViewById(R.id.ntv_holder_bonus_alphabg);
        this.alphabg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.pannelBonus = (ConstraintLayout) findViewById(R.id.ntv_holder_bonus_box);
        this.pannelTip = (ConstraintLayout) findViewById(R.id.ntv_holder_bonus_tips);
        ((ImageView) findViewById(R.id.ntv_holder_bonus_box_tclose)).setOnClickListener(this.onClose);
        ((ImageView) findViewById(R.id.ntv_holder_bonus_tips_tclose)).setOnClickListener(this.onCloseTips);
        ((Button) findViewById(R.id.ntv_holder_bonus_tips_bclose)).setOnClickListener(this.onCloseTips);
        this.pannelBonus_title = (TextView) findViewById(R.id.ntv_holder_bonus_box_ttext);
        this.pannelBonus_dec = (TextView) findViewById(R.id.ntv_holder_bonus_limitdec_text);
        this.pannelBonus_list = (ListView) findViewById(R.id.ntv_holder_bonus_box_list);
        this.pannelTips_ctext = (TextView) findViewById(R.id.ntv_holder_bonus_tips_ctext);
        this.pannelTips_citem_icon = (ImageView) findViewById(R.id.ntv_holder_bonus_tips_citem);
        this.pannelTips_citem_name = (TextView) findViewById(R.id.ntv_holder_bonus_tips_citem_name);
        this.pannelTips_title = (TextView) findViewById(R.id.ntv_holder_bonus_tips_ttext);
        this.pannelTips_btn = (Button) findViewById(R.id.ntv_holder_bonus_tips_bclose);
        this.pannelTipBgCover = (ImageView) findViewById(R.id.ntv_holder_bonus_tips_bgcover);
        this.pannelTipBgCover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.pannelTipBgCover.setVisibility(8);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_ITEM_CLICK, this.onBonusItemClick);
        this.holderBonusController = HolderBonusController.getInstance();
        this.holderBonusController.init(this.mContext);
        this.pannelTips_title.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_REWARD_RECEIVE_TITLE));
        this.pannelTips_btn.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_HOLDER_BONUS_DIALOG_OK_BTN));
        this.pannelBonus_title.setShadowLayer(8.0f, 0.0f, 0.0f, getResources().getColor(R.color.ntvs_main_tab_txt_color_shadow));
        this.pannelTips_title.setShadowLayer(8.0f, 0.0f, 0.0f, getResources().getColor(R.color.ntvs_main_tab_txt_color_shadow));
    }

    private void initUIEvents() {
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_SHOW_LIST_UI, this.onShowBonusPannel);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_SHOW_TIPS_UI, this.onShowTipsPannel);
        EventManager.getInstance().register(NTVDefine.EVT_HOLDER_BONUS_SHOW_LIST_UI_UPDATE, this.onUpdateBonusPannel);
    }

    public void updatePannelBonusData(String title, String dec, ArrayList<HolderBonusItemInfo> bonusList) {
        Logger.d(TAG, "updatePannelBonusData in.");
        this.pannelBonus_title.setText(title);
        this.pannelBonus_dec.setText(Html.fromHtml(dec));
        ArrayList<HolderBonusItemInfo> cloneBonusList = (ArrayList) bonusList.clone();
        this.pannelBonus_list.setTranscriptMode(0);
        this.pannelBonus_list.setSelector(new ColorDrawable(0));
        if (this.itemAdapter == null) {
            this.itemAdapter = new HolderBonusItemAdapter(this.mContext, cloneBonusList);
            this.pannelBonus_list.setAdapter(this.itemAdapter);
            return;
        }
        this.itemAdapter.clear();
        this.itemAdapter.addAll(cloneBonusList);
        this.itemAdapter.notifyDataSetChanged();
    }
}
