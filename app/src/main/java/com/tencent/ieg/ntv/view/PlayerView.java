package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.player.PlayerController;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.CommonTipCachInfo;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.rtmp2.ui.TXCloudVideoView;
import java.util.List;

public class PlayerView extends FrameLayout {
    /* access modifiers changed from: private */
    public static final String TAG = PlayerView.class.getSimpleName();
    private View mBottomCtrlBar;
    private TextView mBtnChannel;
    private TextView mBtnResolution;
    private Button mBtnWIFIContinue;
    private CustomListView mChannelListView;
    private boolean mFullScreen = false;
    private TextView mNetWifiTipsText;
    private ConstraintSet mNormalSet = new ConstraintSet();
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (PlayerView.this.mUIEventListener != null) {
                int id = v.getId();
                if (id == R.id.video_view) {
                    PlayerView.this.mUIEventListener.onVideoView();
                } else if (id == R.id.btn_play_pause) {
                    PlayerView.this.mUIEventListener.onPlayPause();
                } else if (id == R.id.btn_play_refresh) {
                    PlayerView.this.mUIEventListener.onPlayRefresh();
                } else if (id == R.id.btn_toggle_fullscreen) {
                    PlayerView.this.mUIEventListener.onFullscreen();
                } else if (id == R.id.btn_resolution) {
                    PlayerView.this.mUIEventListener.onResolution();
                } else if (id == R.id.image_viewers) {
                    PlayerView.this.mUIEventListener.onViewers();
                } else if (id == R.id.net_status_wifi_btn) {
                    PlayerView.this.mUIEventListener.onWIFIContinue();
                } else if (id == R.id.btn_channel) {
                    PlayerView.this.mUIEventListener.onChannel();
                }
            }
        }
    };
    private boolean mPopUpMode = false;
    private CustomListView mResolutionListView;
    private ConstraintLayout mRootLayout;
    private TextView mSubTitleText;
    private View mTipPannel;
    private View mTitleBar;
    private TextView mTitleText;
    private TextView mTypeTagText;
    /* access modifiers changed from: private */
    public UIEventListener mUIEventListener;
    private ConstraintSet mVideoOnlySet = new ConstraintSet();
    private TXCloudVideoView mVideoView;
    private TextView mViewNumText;

    public interface UIEventListener {
        void onChannel();

        void onChannel(int i);

        void onFullscreen();

        void onPlayPause();

        void onPlayRefresh();

        void onResolution();

        void onResolution(int i);

        void onVideoView();

        void onViewers();

        void onWIFIContinue();
    }

    public PlayerView(Context context) {
        super(context);
        initUI();
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public PlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        LayoutInflater.from(getContext()).inflate(R.layout.fullscreen_player_layout, this);
        this.mVideoView = (TXCloudVideoView) findViewById(R.id.video_view);
        this.mVideoView.setOnClickListener(this.mOnClickListener);
        this.mTitleBar = findViewById(R.id.title_bar);
        this.mTitleText = (TextView) findViewById(R.id.tv_title);
        this.mTitleText.setOnClickListener(this.mOnClickListener);
        this.mSubTitleText = (TextView) findViewById(R.id.tv_sub_title);
        this.mSubTitleText.setOnClickListener(this.mOnClickListener);
        this.mTypeTagText = (TextView) findViewById(R.id.ntvs_tv_type_tag);
        this.mTypeTagText.setOnClickListener(this.mOnClickListener);
        this.mViewNumText = (TextView) findViewById(R.id.tv_viewers_count);
        ((ImageView) findViewById(R.id.image_viewers)).setOnClickListener(this.mOnClickListener);
        this.mTipPannel = findViewById(R.id.net_status_panel);
        this.mTipPannel.setVisibility(8);
        this.mBottomCtrlBar = findViewById(R.id.bottom_ctrl_bar);
        this.mBottomCtrlBar.setOnClickListener(this.mOnClickListener);
        ((ImageView) findViewById(R.id.btn_play_pause)).setOnClickListener(this.mOnClickListener);
        ((ImageView) findViewById(R.id.btn_play_refresh)).setOnClickListener(this.mOnClickListener);
        ((ImageView) findViewById(R.id.btn_toggle_fullscreen)).setOnClickListener(this.mOnClickListener);
        this.mBtnResolution = (TextView) findViewById(R.id.btn_resolution);
        this.mBtnResolution.setOnClickListener(this.mOnClickListener);
        this.mResolutionListView = (CustomListView) findViewById(R.id.resolution_custom_list_view);
        this.mResolutionListView.setSelector(new ColorDrawable(0));
        this.mResolutionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d(PlayerView.TAG, "onItemClick, i=" + i + ",l=" + l);
                if (PlayerView.this.mUIEventListener != null) {
                    PlayerView.this.mUIEventListener.onResolution(i);
                }
            }
        });
        this.mNetWifiTipsText = (TextView) findViewById(R.id.net_status_wifi);
        this.mBtnWIFIContinue = (Button) findViewById(R.id.net_status_wifi_btn);
        this.mBtnWIFIContinue.setOnClickListener(this.mOnClickListener);
        this.mRootLayout = (ConstraintLayout) findViewById(R.id.play_parent);
        this.mNormalSet.clone(this.mRootLayout);
        this.mVideoOnlySet.clone(this.mRootLayout);
        this.mVideoOnlySet.connect(R.id.bottom_ctrl_bar, 3, R.id.play_parent, 4, 0);
        this.mVideoOnlySet.clear(R.id.bottom_ctrl_bar, 4);
        this.mBtnChannel = (TextView) findViewById(R.id.btn_channel);
        this.mBtnChannel.setOnClickListener(this.mOnClickListener);
        this.mChannelListView = (CustomListView) findViewById(R.id.channel_custom_list_view);
        this.mChannelListView.setSelector(new ColorDrawable(0));
        this.mChannelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.d(PlayerView.TAG, "onItemClick, i=" + i + ",l=" + l);
                if (PlayerView.this.mUIEventListener != null) {
                    PlayerView.this.mUIEventListener.onChannel(i);
                }
            }
        });
    }

    public void setFullScreen(boolean fullScreen) {
        this.mFullScreen = fullScreen;
        if (this.mFullScreen) {
            this.mNormalSet.setMargin(R.id.video_view, 3, 0);
            this.mNormalSet.applyTo(this.mRootLayout);
            this.mVideoOnlySet.setMargin(R.id.video_view, 3, 0);
            this.mVideoOnlySet.connect(R.id.title_bar, 4, R.id.play_parent, 3, 0);
            this.mVideoOnlySet.clear(R.id.title_bar, 3);
        }
    }

    public void setChannelList(List<String> channelList) {
        this.mChannelListView.setItemTextList(channelList);
    }

    public void setResolutionList(List<String> resolutionList) {
        this.mResolutionListView.setItemTextList(resolutionList);
    }

    public void showCommonTip(boolean showPannel, boolean showBtn, String tipText) {
        int i;
        int i2 = 0;
        Logger.d(TAG, "chltag showCommonTip showPannel:" + showPannel + ", showBtn:" + showBtn + ", tipText:" + tipText);
        PlayerController.getInstance().setCommonTipInfoCach(new CommonTipCachInfo(showPannel, showBtn, tipText));
        this.mNetWifiTipsText.setText(tipText);
        this.mBtnWIFIContinue.setText(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_NOT_WIFI_BTN_TEXT));
        Button button = this.mBtnWIFIContinue;
        if (showBtn) {
            i = 0;
        } else {
            i = 8;
        }
        button.setVisibility(i);
        View view = this.mTipPannel;
        if (!showPannel) {
            i2 = 8;
        }
        view.setVisibility(i2);
    }

    public void setTitleText(String title) {
        this.mTitleText.setText(title);
    }

    public void setSubTitleText(String title) {
        this.mSubTitleText.setText(title);
    }

    public void setTypeTagText(String title) {
        this.mTypeTagText.setText(title);
    }

    public void setViewNumText(int num) {
        this.mViewNumText.setText(String.valueOf(num));
    }

    public void setResolutionSideBarVisible(boolean visible) {
        findViewById(R.id.resolution_side_bar).setVisibility(visible ? 0 : 8);
    }

    public void setChannelSideBarVisible(boolean visible) {
        findViewById(R.id.channel_side_bar).setVisibility(visible ? 0 : 8);
    }

    public void setResolutionSelected(int index) {
        for (int i = 0; i < this.mResolutionListView.getCount(); i++) {
            this.mResolutionListView.setItemTextColor(i, -1);
        }
        if (index < 0 || index > this.mResolutionListView.getCount()) {
            index = 0;
        }
        this.mResolutionListView.setItemTextColor(index, getResources().getColor(R.color.ntvs_custom_listview_selected));
        this.mBtnResolution.setText(this.mResolutionListView.getItemText(index));
    }

    public void setChannelSelected(int index) {
        for (int i = 0; i < this.mChannelListView.getCount(); i++) {
            this.mChannelListView.setItemTextColor(i, -1);
        }
        if (index < 0 || index > this.mResolutionListView.getCount()) {
            index = 0;
        }
        this.mChannelListView.setItemTextColor(index, getResources().getColor(R.color.ntvs_custom_listview_selected));
        this.mBtnChannel.setText(this.mChannelListView.getItemText(index));
    }

    public void hideBtnChannel() {
        this.mBtnChannel.setVisibility(8);
    }

    public void setPopUpMode(boolean popUpMode) {
        this.mPopUpMode = true;
    }

    public void setCtrlBarVisible(boolean visible) {
        if (this.mPopUpMode) {
            visible = false;
        }
        this.mBottomCtrlBar.setVisibility(visible ? 0 : 8);
    }

    public void setInViewImmersive(boolean value, boolean bTransition) {
        int i;
        int i2;
        int i3 = 8;
        if (!this.mPopUpMode) {
            Logger.d(TAG, "chltag setInViewImmersive value:" + value + ", bTransition:" + bTransition);
            if (Build.VERSION.SDK_INT < 19) {
                View view = this.mBottomCtrlBar;
                if (value) {
                    i = 8;
                } else {
                    i = 0;
                }
                view.setVisibility(i);
                if (this.mFullScreen) {
                    View view2 = this.mTitleBar;
                    if (!value) {
                        i3 = 0;
                    }
                    view2.setVisibility(i3);
                }
            } else if (bTransition) {
                if (value) {
                    this.mVideoOnlySet.applyTo(this.mRootLayout);
                } else {
                    this.mNormalSet.applyTo(this.mRootLayout);
                }
                TransitionManager.beginDelayedTransition(this.mRootLayout);
            } else {
                this.mNormalSet.applyTo(this.mRootLayout);
                View view3 = this.mBottomCtrlBar;
                if (value) {
                    i2 = 8;
                } else {
                    i2 = 0;
                }
                view3.setVisibility(i2);
                if (this.mFullScreen) {
                    View view4 = this.mTitleBar;
                    if (!value) {
                        i3 = 0;
                    }
                    view4.setVisibility(i3);
                }
            }
        }
    }

    public void setUIEventListener(UIEventListener listener) {
        this.mUIEventListener = listener;
    }

    public TXCloudVideoView getTXCloudVideoView() {
        return (TXCloudVideoView) findViewById(R.id.video_view);
    }

    public void setPlayPauseBtnImage(int index) {
        ((ImageView) findViewById(R.id.btn_play_pause)).setImageResource(index == 0 ? R.drawable.stop_icon : R.drawable.play_icon);
        PlayerController.getInstance().setPlayOrStopStyle(index);
    }

    public void setFullscreenBtnImage(int index) {
        ((ImageView) findViewById(R.id.btn_toggle_fullscreen)).setImageResource(index == 0 ? R.drawable.qp_icon : R.drawable.sq_icon);
    }
}
