package com.tencent.ieg.ntv.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.ReplayCtrl;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventHideSystemUI;
import com.tencent.ieg.ntv.event.EventMainTabClick;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventPageInfoData;
import com.tencent.ieg.ntv.model.DataForReport;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.RedDotHelper;
import com.tencent.ieg.ntv.utils.Util;
import com.vk.sdk.api.VKApiConst;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NTVShowActivity extends AppCompatActivity {
    private static final String TAG = NTVShowActivity.class.getSimpleName();
    /* access modifiers changed from: private */
    public int curDisplayRotation = -1;
    /* access modifiers changed from: private */
    public int currentTabIndex;
    /* access modifiers changed from: private */
    public EventHideSystemUI eventHideSystemUI;
    /* access modifiers changed from: private */
    public List<EventPageInfoData.PageInfo> flowWebViewPageInfoList;
    private List<BaseContentFragment> listFragment = null;
    /* access modifiers changed from: private */
    public EventPageInfoData.PageInfo livePlayerPageInfo;
    private LobbyContentFragment lobbyContentFragment;
    private final Handler mHideHandler = new Handler() {
        public void handleMessage(Message msg) {
            EventManager.getInstance().post(NTVDefine.EVT_HIDE_NAVIGATION_UI, NTVShowActivity.this.eventHideSystemUI);
        }
    };
    private OrientationEventListener mOrientationEventListener;
    private IEventListener mPageInfoListener = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            NTVShowActivity.this.onPageList(((EventPageInfoData) event).pageInfoList);
            EventManager.getInstance().unregister(eventId, this);
        }
    };
    /* access modifiers changed from: private */
    public TABEventListener mTABEventListener;
    private View.OnClickListener onReplayBtnsClick = new View.OnClickListener() {
        public void onClick(View view) {
            ReplayCtrl.getInstance().onBtnsClick(view.getId());
        }
    };
    private IEventListener onTabClick = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            RedDotInfo redDotInfo;
            EventMainTabClick evt = (EventMainTabClick) event;
            ReplayCtrl.getInstance().synTabIndex(NTVShowActivity.this.currentTabIndex);
            if (NTVShowActivity.this.currentTabIndex != evt.mIndex) {
                int unused = NTVShowActivity.this.currentTabIndex = evt.mIndex;
                if (NTVShowActivity.this.mTABEventListener != null) {
                    NTVShowActivity.this.mTABEventListener.onTabChange(evt.mIndex);
                }
                for (int i = 0; i < NTVShowActivity.this.tabsArray.size(); i++) {
                    if (i == evt.mIndex) {
                        ((MainTabItemView) NTVShowActivity.this.tabsArray.get(i)).setSeclected(true);
                        NTVShowActivity.this.tabView.setCurrentItem(i, false);
                        String tabLabel = ((MainTabItemView) NTVShowActivity.this.tabsArray.get(i)).getButtonLabel();
                        if (NTVShowActivity.this.livePlayerPageInfo == null) {
                            DataForReport.getInstance().logWebViewTabEvent(tabLabel);
                        } else {
                            DataForReport.getInstance().logLivePlayerTabEvent(i);
                            if (NTVShowActivity.this.currentTabIndex != 0) {
                                DataForReport.getInstance().logWebViewTabEvent(tabLabel);
                            }
                        }
                        if (i != 0) {
                            redDotInfo = ((EventPageInfoData.PageInfo) NTVShowActivity.this.flowWebViewPageInfoList.get(i - 1)).redDotInfo;
                        } else if (NTVShowActivity.this.livePlayerPageInfo != null) {
                            redDotInfo = NTVShowActivity.this.livePlayerPageInfo.redDotInfo;
                        } else {
                            redDotInfo = ((EventPageInfoData.PageInfo) NTVShowActivity.this.flowWebViewPageInfoList.get(i)).redDotInfo;
                        }
                        RedDotHelper.getInstance().clearRedDot(((MainTabItemView) NTVShowActivity.this.tabsArray.get(i)).getDot(), redDotInfo);
                    } else {
                        ((MainTabItemView) NTVShowActivity.this.tabsArray.get(i)).setSeclected(false);
                    }
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public FDTabView tabView;
    /* access modifiers changed from: private */
    public ArrayList<MainTabItemView> tabsArray;
    private LinearLayout tabsContainer;

    public interface TABEventListener {
        void onTabChange(int i);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        getWindow().setFlags(128, 128);
        TVShowManager.getInstance().init(this);
        Intent intent = getIntent();
        Logger.setEnable(intent.getBooleanExtra("enableLog", false));
        TVShowManager.getInstance().setLanguage(intent.getStringExtra(VKApiConst.LANG));
        TVShowManager.getInstance().setAccountInfo(intent.getStringExtra(GGLiveConstants.PARAM.UID), intent.getStringExtra("token"), intent.getStringExtra("ticket"), intent.getStringExtra("username"), intent.getStringExtra("banchat"));
        TVShowManager.getInstance().setGameInfo(intent.getStringExtra("gameid"), intent.getStringExtra("areaid"), intent.getStringExtra("partitionid"));
        TVShowManager.getInstance().initI18NText(intent.getStringExtra("I18NTextStr"));
        TVShowManager.getInstance().initIntConfig(intent.getStringExtra("IntConfigStr"));
        TVShowManager.getInstance().setFirebaseToken(intent.getStringExtra("firebasetoken"));
        NetworkModule.getInstance().init();
        setContentView(R.layout.ntvs_activity);
        this.curDisplayRotation = Util.getDisplayRotation(this);
        Logger.d(TAG, "curDisplayRotation: " + this.curDisplayRotation);
        updateNotchLayout();
        this.mOrientationEventListener = new OrientationEventListener(this) {
            public void onOrientationChanged(int orientation) {
                int displayRotation = Util.getDisplayRotation(NTVShowActivity.this);
                if (NTVShowActivity.this.curDisplayRotation != displayRotation) {
                    int unused = NTVShowActivity.this.curDisplayRotation = displayRotation;
                    NTVShowActivity.this.updateNotchLayout();
                }
            }
        };
        TVShowManager.getInstance().setActivity(this);
        initUI();
        initPages();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(int i) {
                NTVShowActivity.this.delayedHide(1000);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mOrientationEventListener == null) {
            return;
        }
        if (this.mOrientationEventListener.canDetectOrientation()) {
            this.mOrientationEventListener.enable();
        } else {
            this.mOrientationEventListener.disable();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (this.mOrientationEventListener != null) {
            this.mOrientationEventListener.disable();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        destroyFragment();
        super.onDestroy();
        System.exit(0);
    }

    /* access modifiers changed from: private */
    public void updateNotchLayout() {
        Logger.d(TAG, "updateNotchLayout(), curDisplayRotation: " + this.curDisplayRotation);
        if (Util.hasNotchScreen(this)) {
            int notchHeight = Util.getNotchHeight(this);
            View v = findViewById(R.id.ntvs_main);
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) v.getLayoutParams();
            if (this.curDisplayRotation == 1) {
                lp.setMargins(notchHeight, 0, 0, 0);
            } else if (this.curDisplayRotation == 3) {
                lp.setMargins(0, 0, notchHeight, 0);
            }
            v.setLayoutParams(lp);
        }
    }

    private void initUI() {
        ((ImageView) findViewById(R.id.ntvs_main_imgbtn_close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = NTVShowActivity.this.getPackageManager().getLaunchIntentForPackage(Util.getPackageName());
                intent.putExtra(NTVDefine.XP_RESULT_KEY, NTVDefine.XP_CLOSE_CENTER);
                NTVShowActivity.this.startActivity(intent);
                NTVShowActivity.this.finish();
            }
        });
        initHolderUI();
        RedDotHelper.getInstance().setContext(this);
        ImageView replaybtn_back = (ImageView) findViewById(R.id.replay_btn_back);
        replaybtn_back.setOnClickListener(this.onReplayBtnsClick);
        ImageView replaybtn_forward = (ImageView) findViewById(R.id.replay_btn_forward);
        replaybtn_forward.setOnClickListener(this.onReplayBtnsClick);
        ((ImageView) findViewById(R.id.replay_btn_refresh)).setOnClickListener(this.onReplayBtnsClick);
        ((ImageView) findViewById(R.id.replay_btn_close)).setOnClickListener(this.onReplayBtnsClick);
        ReplayCtrl.getInstance().initReplayUI((ConstraintLayout) findViewById(R.id.replay_ctn), (WebView) findViewById(R.id.replay_ctn_webview), this, replaybtn_back, replaybtn_forward);
    }

    private void initPages() {
        EventManager.getInstance().register(5001, this.mPageInfoListener);
        EventManager.getInstance().register(1000, this.onTabClick);
    }

    public void onPageList(List<EventPageInfoData.PageInfo> pageList) {
        int offset;
        boolean z;
        this.livePlayerPageInfo = null;
        this.flowWebViewPageInfoList = new LinkedList();
        int tabsCount = 0;
        this.currentTabIndex = -1;
        for (int i = 0; i < pageList.size(); i++) {
            EventPageInfoData.PageInfo pageInfo = pageList.get(i);
            if (pageInfo.type.equals(EventPageInfoData.PageType.TYPE_TVPLAYER)) {
                this.livePlayerPageInfo = pageInfo;
                tabsCount++;
            } else if (pageInfo.type.equals("webview")) {
                this.flowWebViewPageInfoList.add(pageInfo);
                tabsCount++;
            }
            if (this.currentTabIndex == -1 && pageInfo.isDefault) {
                this.currentTabIndex = i;
            }
        }
        this.tabsContainer = (LinearLayout) findViewById(R.id.ntvs_main_tabs_container);
        this.tabsArray = new ArrayList<>(tabsCount);
        for (int i2 = 0; i2 < tabsCount; i2++) {
            MainTabItemView tabItem = new MainTabItemView(this, i2);
            tabItem.setDotVisibility(8);
            tabItem.setSeclected(false);
            this.tabsContainer.addView(tabItem);
            this.tabsArray.add(tabItem);
        }
        if (this.currentTabIndex >= tabsCount) {
            this.currentTabIndex = 0;
        }
        resetTabWith();
        if (this.listFragment == null) {
            this.listFragment = new ArrayList();
        }
        if (this.livePlayerPageInfo != null) {
            this.tabsArray.get(0).setButtonLable(this.livePlayerPageInfo.name);
            RedDotHelper.getInstance().clearRedDot(this.tabsArray.get(0).getDot(), this.livePlayerPageInfo.redDotInfo);
            if (this.lobbyContentFragment == null) {
                this.lobbyContentFragment = new LobbyContentFragment();
                this.mTABEventListener = this.lobbyContentFragment.getTabEventListener();
                this.listFragment.add(this.lobbyContentFragment);
            }
            LobbyContentFragment lobbyContentFragment2 = this.lobbyContentFragment;
            if (this.currentTabIndex == 0) {
                z = true;
            } else {
                z = false;
            }
            lobbyContentFragment2.isActive = z;
        }
        int webViewCount = this.flowWebViewPageInfoList.size();
        if (this.livePlayerPageInfo != null) {
            offset = 1;
        } else {
            offset = 0;
        }
        for (int i3 = 0; i3 < webViewCount; i3++) {
            RedDotHelper.getInstance().updateRedDot(this.tabsArray.get(i3 + offset).getDot(), this.flowWebViewPageInfoList.get(i3).redDotInfo);
            this.tabsArray.get(i3 + offset).setButtonLable(this.flowWebViewPageInfoList.get(i3).name);
            WebViewContentFragment webFrag = new WebViewContentFragment();
            webFrag.setUrl(infoUrl(this.flowWebViewPageInfoList.get(i3).url));
            this.listFragment.add(webFrag);
        }
        if (this.tabView == null) {
            this.tabView = (FDTabView) findViewById(R.id.ntvs_main_tabview);
            this.tabView.init(getSupportFragmentManager(), this.listFragment);
            this.tabView.setOffscreenPageLimit(pageList.size());
        }
        if (this.tabsArray != null && this.tabsArray.get(this.currentTabIndex) != null) {
            this.tabsArray.get(this.currentTabIndex).setSeclected(true);
            this.tabView.setCurrentItem(this.currentTabIndex);
            ReplayCtrl.getInstance().synTabIndex(this.currentTabIndex);
            String tabLabel = this.tabsArray.get(this.currentTabIndex).getButtonLabel();
            if (this.livePlayerPageInfo == null) {
                DataForReport.getInstance().logWebViewTabEvent(tabLabel);
                return;
            }
            DataForReport.getInstance().logLivePlayerTabEvent(this.currentTabIndex);
            if (this.currentTabIndex != 0) {
                DataForReport.getInstance().logWebViewTabEvent(tabLabel);
            }
        }
    }

    private void resetTabWith() {
        for (int i = 0; i < this.tabsArray.size(); i++) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.tabsArray.get(i).getLayoutParams();
            lp.weight = 1.0f / ((float) this.tabsArray.size());
            this.tabsArray.get(i).setLayoutParams(lp);
        }
    }

    private void destroyFragment() {
        if (this.listFragment != null) {
            for (int i = 0; i < this.listFragment.size(); i++) {
                if (this.listFragment.get(i) != null) {
                    this.listFragment.get(i).onDestroy();
                }
            }
        }
    }

    private String infoUrl(String url) {
        if (url == null) {
            String str = url;
            return "";
        }
        Logger.d(TAG, "infoUrl : url = " + url);
        String url2 = url.replace("{platid}", "1").replace("{language}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_LANGUAGE)).replace("{openid}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID)).replace("{token}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_TOKEN)).replace("{ticket}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_TICKET)).replace("{gameid}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_GAMEID)).replace("{areaid}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_AREAID)).replace("{partition}", Util.getMConfig(NTVDefine.KEY_MCONF_GAME_PARTITIONID));
        Logger.d(TAG, "infoUrl 2 : url = " + url2);
        String str2 = url2;
        return url2;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            this.eventHideSystemUI = new EventHideSystemUI();
            this.eventHideSystemUI.hasFocus = hasFocus;
            delayedHide(1000);
        }
    }

    /* access modifiers changed from: private */
    public void delayedHide(int delayMillis) {
        this.mHideHandler.removeMessages(0);
        this.mHideHandler.sendEmptyMessageDelayed(0, (long) delayMillis);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == 66 || event.getKeyCode() == 4) {
            this.eventHideSystemUI = new EventHideSystemUI();
            this.eventHideSystemUI.hasFocus = true;
            delayedHide(1000);
        }
        return super.dispatchKeyEvent(event);
    }

    private void initHolderUI() {
        addContentView(new HolderBonusFragment(this), new FrameLayout.LayoutParams(-1, -1));
    }
}
