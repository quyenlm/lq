package com.tencent.imsdk.webview.qq;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.Headers;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.os.RemoteException;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.webview.qq.ShareAnimMenu;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.util.List;
import java.util.Map;

public class WebViewWithFavActivity extends Activity {
    private static final int FILE_CHOOSER_REQUEST_CODE = 78;
    private static final int FILE_CHOOSER_REQUEST_CODE_FOR_ANDROID5 = 79;
    private static final int GRID_COLUMN_LANDSCAPE = 5;
    private static final int GRID_COLUMN_PORTRAIT = 4;
    private static final String INNER_PROCESS = "imsdk_inner_webview";
    private static final String NAV_HIDE = "com.tencent.imsdk.webview.navHide";
    private static final String NAV_NEST_SCROLL = "com.tencent.imsdk.webview.nestScroll";
    private ImageButton mBackBtn;
    private Drawable mBackDrawable;
    private Drawable mBackOnDrawable;
    private BottomBehaviorLand mBehaviorLand;
    private BottomBehaviorPortrait mBehaviorPortrait;
    /* access modifiers changed from: private */
    public ProgressBar mBottomProgressBar;
    private LinearLayout mBottomToolBarLayout;
    /* access modifiers changed from: private */
    public boolean mBound = false;
    /* access modifiers changed from: private */
    public Messenger mClientMessenger = null;
    /* access modifiers changed from: private */
    public View mCustomView;
    /* access modifiers changed from: private */
    public IX5WebChromeClient.CustomViewCallback mCustomViewCallback;
    private ImageButton mForwardBtn;
    private Drawable mForwardDrawable;
    private Drawable mForwardOnDrawable;
    private boolean mIsX5Work = false;
    private MsgHandler mMsgHandler = null;
    private AppBarLayout.ScrollingViewBehavior mNestBehavior;
    /* access modifiers changed from: private */
    public int mOriginalOrientation;
    /* access modifiers changed from: private */
    public int mOriginalSystemUiVisibility;
    private CoordinatorLayout.LayoutParams mParamsBottomBarView = null;
    private CoordinatorLayout.LayoutParams mParamsNestView = null;
    private ImageButton mRefreshBottomBtn = null;
    private RelativeLayout mRefreshBottomLayout;
    private ImageButton mRefreshTopBtn = null;
    /* access modifiers changed from: private */
    public Messenger mServerMessenger = null;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IMLogger.d("onServiceConnected iBinder=" + iBinder);
            if (iBinder != null) {
                Messenger unused = WebViewWithFavActivity.this.mServerMessenger = new Messenger(iBinder);
                boolean unused2 = WebViewWithFavActivity.this.mBound = true;
                WebViewWithFavActivity.this.sendMessageToServer(23, "", WebViewWithFavActivity.this.mClientMessenger);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            IMLogger.d("onServiceDisconnected");
            Messenger unused = WebViewWithFavActivity.this.mServerMessenger = null;
            boolean unused2 = WebViewWithFavActivity.this.mBound = false;
        }
    };
    /* access modifiers changed from: private */
    public ShareAnimMenu mShareAnimMenu = null;
    private List<Map<String, Object>> mShareDataList;
    private GridView mShareGridView = null;
    private ImageButton mStopBottomBtn = null;
    private ImageButton mStopTopBtn = null;
    /* access modifiers changed from: private */
    public TextView mTitleTv;
    private boolean mToolBarCanLandShow = false;
    /* access modifiers changed from: private */
    public ProgressBar mTopProgressBar;
    private LinearLayout mTopToolBarLayout;
    /* access modifiers changed from: private */
    public ValueCallback<Uri> mUploadFile;
    /* access modifiers changed from: private */
    public ValueCallback<Uri[]> mUploadFileAndroid5;
    private CustomWebChromeClient mWebChromeClient;
    /* access modifiers changed from: private */
    public NestedWebView mWebView;
    private float mZoomHeight = 1.0f;
    private float mZoomWidth = 1.0f;

    private void loadUrl(Intent intent) {
        if (intent != null) {
            String originalUrl = intent.getStringExtra(WebViewManager.LABEL_URL);
            IMLogger.d("loadUrl = " + originalUrl);
            if (originalUrl != null && this.mWebView != null) {
                this.mWebView.loadUrl(originalUrl);
                return;
            }
            return;
        }
        IMLogger.w("loadUrl intent is null!");
    }

    private void initLayout() {
        this.mBehaviorPortrait = new BottomBehaviorPortrait(this, (AttributeSet) null);
        this.mBehaviorLand = new BottomBehaviorLand(this, (AttributeSet) null);
        this.mNestBehavior = new AppBarLayout.ScrollingViewBehavior(this, (AttributeSet) null);
        this.mWebView = (NestedWebView) findViewById(ResourceUtil.getId(this, "nested_webview_container"));
        if (this.mWebView != null) {
            this.mParamsNestView = (CoordinatorLayout.LayoutParams) this.mWebView.getLayoutParams();
        }
        initDynamicRes();
        setNavNestScrollEnable();
        if (DisplayUtil.isLandscape(this)) {
            if (this.mRefreshBottomLayout != null) {
                this.mRefreshBottomLayout.setVisibility(0);
            }
            if (!this.mToolBarCanLandShow) {
                if (this.mTopToolBarLayout != null) {
                    this.mTopToolBarLayout.setVisibility(8);
                }
                this.mParamsNestView.setBehavior((CoordinatorLayout.Behavior) null);
                if (this.mParamsBottomBarView != null) {
                    this.mParamsBottomBarView.setBehavior(this.mBehaviorLand);
                }
            }
        }
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(int visibility) {
                IMLogger.d("status bar change " + visibility);
                if (visibility == 0) {
                    WebViewWithFavActivity.this.setFullScreenCompat();
                } else {
                    IMLogger.d("not visibility");
                }
            }
        });
    }

    private void initDynamicRes() {
        Resources resources = getResources();
        this.mBackDrawable = ResourceUtil.getDrawable(this, "back");
        this.mBackOnDrawable = ResourceUtil.getDrawable(this, "back_on");
        this.mForwardDrawable = ResourceUtil.getDrawable(this, "forward");
        this.mForwardOnDrawable = ResourceUtil.getDrawable(this, "forward_on");
        this.mForwardBtn = (ImageButton) findViewById(ResourceUtil.getId(this, "forward"));
        this.mBackBtn = (ImageButton) findViewById(ResourceUtil.getId(this, "back"));
        this.mTitleTv = (TextView) findViewById(ResourceUtil.getId(this, "tv_title"));
        this.mTopToolBarLayout = (LinearLayout) findViewById(ResourceUtil.getId(this, "qq_webview_top_toolbar"));
        if (this.mTopToolBarLayout != null) {
            this.mToolBarCanLandShow = Boolean.parseBoolean((String) this.mTopToolBarLayout.getTag());
            IMLogger.d("mToolBarCanLandShow = " + this.mToolBarCanLandShow);
        }
        this.mBottomToolBarLayout = (LinearLayout) findViewById(ResourceUtil.getId(this, "qq_webview_bottom_toolbar"));
        if (this.mBottomToolBarLayout != null) {
            this.mParamsBottomBarView = (CoordinatorLayout.LayoutParams) this.mBottomToolBarLayout.getLayoutParams();
        }
        this.mTopProgressBar = (ProgressBar) findViewById(ResourceUtil.getId(this, "progressbar_top"));
        this.mBottomProgressBar = (ProgressBar) findViewById(ResourceUtil.getId(this, "progressbar_bottom"));
        ViewGroup viewGroup = (ViewGroup) findViewById(ResourceUtil.getId(this, "view_refresh_top"));
        if (viewGroup != null) {
            this.mRefreshTopBtn = (ImageButton) viewGroup.findViewById(ResourceUtil.getId(this, Headers.REFRESH));
            this.mStopTopBtn = (ImageButton) viewGroup.findViewById(ResourceUtil.getId(this, "stop"));
        }
        ViewGroup viewGroup2 = (ViewGroup) findViewById(ResourceUtil.getId(this, "view_refresh_bottom"));
        if (viewGroup2 != null) {
            this.mRefreshBottomBtn = (ImageButton) viewGroup2.findViewById(ResourceUtil.getId(this, Headers.REFRESH));
            this.mStopBottomBtn = (ImageButton) viewGroup2.findViewById(ResourceUtil.getId(this, "stop"));
        }
        this.mRefreshBottomLayout = (RelativeLayout) findViewById(ResourceUtil.getId(this, "layout_refresh_bottom"));
        int resID = ResourceUtil.getLayoutId(this, "layout_share_view");
        if (resID != 0) {
            this.mShareAnimMenu = new ShareAnimMenu(this, resID);
            Button btnCancel = (Button) this.mShareAnimMenu.findViewById(ResourceUtil.getId(this, "share_cancel"));
            if (btnCancel != null) {
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        WebViewWithFavActivity.this.mShareAnimMenu.dismiss((ShareAnimMenu.IDismissListener) null);
                    }
                });
                Typeface tf = ResourceUtil.getTypeface(this, "share_cancel");
                if (tf != null) {
                    btnCancel.setTypeface(tf);
                }
            }
            this.mShareGridView = (GridView) this.mShareAnimMenu.findViewById(ResourceUtil.getId(this, "share_gridview"));
        }
    }

    private void initShareLayoutData() {
        if (this.mShareGridView != null) {
            if (DisplayUtil.isPortrait(this)) {
                this.mShareGridView.setNumColumns(4);
            } else {
                this.mShareGridView.setNumColumns(5);
            }
            this.mShareDataList = ShareManager.getChannelListData(this);
            if (this.mShareDataList != null) {
                String[] from = {"image", "channel"};
                int[] to = {ResourceUtil.getId(this, "share_channel_icon"), ResourceUtil.getId(this, "share_channel_title")};
                int resID = ResourceUtil.getLayoutId(this, "layout_share_grid_item");
                if (resID != 0) {
                    this.mShareGridView.setAdapter(new SimpleAdapter(this, this.mShareDataList, resID, from, to));
                    this.mShareGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                            IMLogger.d("handleShareEvent, onItemClick=" + position);
                            if (WebViewWithFavActivity.this.mShareAnimMenu != null) {
                                WebViewWithFavActivity.this.mShareAnimMenu.dismiss(new ShareAnimMenu.IDismissListener() {
                                    public void onDismiss() {
                                        WebViewWithFavActivity.this.handleShareEvent(position);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void handleShareEvent(int position) {
        if (this.mShareDataList == null || this.mShareDataList.size() == 0) {
            IMLogger.e("handleShareEvent mShareDataList null/size = 0!");
            return;
        }
        Map<String, Object> item = this.mShareDataList.get(position);
        if (item == null) {
            IMLogger.e("handleShareEvent get item:" + position + " error!");
        } else if (this.mWebView == null) {
            IMLogger.e("mWebView is null, please check!!!");
        } else {
            int channelId = ((Integer) item.get("id")).intValue();
            switch (channelId) {
                case 99:
                    ShareManager.shareToBrowser(this, this.mWebView.getUrl());
                    return;
                default:
                    try {
                        ShareManager.shareToChannel(this, item.get("channel").toString(), this.mWebView.getTitle(), this.mWebView.getUrl());
                        return;
                    } catch (Exception e) {
                        IMLogger.w("share " + channelId + " failed : " + e.getMessage());
                        return;
                    }
            }
        }
    }

    public void refreshOnclick(View target) {
        if (this.mWebView != null) {
            this.mWebView.reload();
        }
    }

    public void stopOnclick(View target) {
        if (this.mWebView != null) {
            this.mWebView.stopLoading();
        }
    }

    public void shareOnclick(View target) {
        initShareLayoutData();
        if (this.mShareAnimMenu != null) {
            this.mShareAnimMenu.show();
        }
    }

    public void closeOnClick(View target) {
        processWebViewEvent(29);
    }

    public void backOnClick(View target) {
        processWebViewEvent(26);
    }

    public void forwardOnClick(View target) {
        processWebViewEvent(27);
    }

    private void getIntentValue(Intent intent) {
        int i = 0;
        if (intent != null) {
            this.mZoomHeight = intent.getFloatExtra(WebViewManager.LABEL_HEIGHT, 0.8f);
            this.mZoomWidth = intent.getFloatExtra(WebViewManager.LABEL_WIDTH, 0.8f);
            boolean isLandscape = intent.getBooleanExtra(WebViewManager.LABEL_IS_LANDSCAPE, true);
            boolean isUserSetOrientation = intent.getBooleanExtra(WebViewManager.LABEL_IS_ORIENTATION, false);
            this.mIsX5Work = intent.getBooleanExtra(WebViewManager.EXTRA_X5_WORK, false);
            IMLogger.d("getIntentValue mIsX5Work = " + this.mIsX5Work);
            if (isUserSetOrientation) {
                if (!isLandscape) {
                    i = 1;
                }
                setRequestedOrientation(i);
            }
        }
    }

    private void processZoom() {
        if (this.mZoomHeight != 1.0f || this.mZoomWidth != 1.0f) {
            DisplayMetrics display = getResources().getDisplayMetrics();
            int width = display.widthPixels;
            int height = display.heightPixels;
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.height = (int) (((float) height) * this.mZoomHeight);
            p.width = (int) (((float) width) * this.mZoomWidth);
            getWindow().setAttributes(p);
        }
    }

    private void initWebView() {
        if (this.mWebView == null) {
            IMLogger.e("mWebView is null, please check!!!");
            return;
        }
        this.mWebChromeClient = new CustomWebChromeClient();
        this.mWebView.setWebChromeClient(this.mWebChromeClient);
        this.mWebView.setWebViewClient(new WebViewClient() {
            @SuppressLint({"NewApi"})
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                IMLogger.d("loading url:" + url);
                if (url.startsWith("IMSDK:")) {
                    try {
                        Intent intent = Intent.parseUri(url, 1);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent((ComponentName) null);
                        if (Build.VERSION.SDK_INT >= 15) {
                            intent.setSelector((Intent) null);
                        }
                        WebViewWithFavActivity.this.startActivity(intent);
                    } catch (Exception e) {
                        IMLogger.d(e.getMessage());
                    }
                } else if (url.startsWith(WebView.SCHEME_MAILTO) || url.startsWith("geo:") || url.startsWith(WebView.SCHEME_TEL) || url.startsWith("smsto:")) {
                    WebViewWithFavActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } else {
                    view.loadUrl(url);
                }
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                WebViewWithFavActivity.this.changeBackForwardBtnState();
                WebViewWithFavActivity.this.displayRefreshBtn(true);
                if (WebViewWithFavActivity.this.mWebView != null) {
                    String title = WebViewWithFavActivity.this.mWebView.getTitle();
                    if (WebViewWithFavActivity.this.mTitleTv != null && !T.ckIsEmpty(title)) {
                        WebViewWithFavActivity.this.mTitleTv.setText(title);
                        Typeface tf = ResourceUtil.getTypeface(WebViewWithFavActivity.this, "webview_title");
                        if (tf != null) {
                            WebViewWithFavActivity.this.mTitleTv.setTypeface(tf);
                        }
                    }
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                WebViewWithFavActivity.this.displayRefreshBtn(false);
            }
        });
        this.mWebView.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    WebViewWithFavActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                } catch (ActivityNotFoundException e) {
                    IMLogger.e("default browser is uninstalled!");
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 11) {
            this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
            this.mWebView.removeJavascriptInterface("accessibility");
            this.mWebView.removeJavascriptInterface("accessibilityTraversal");
        }
        this.mWebView.addJavascriptInterface(this, "IMSDK");
        WebSettings webSetting = this.mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setTextZoom(100);
        if (Build.VERSION.SDK_INT >= 11) {
            webSetting.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            webSetting.setMixedContentMode(2);
        }
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(FileTracerConfig.FOREVER);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (this.mWebView.getX5WebViewExtension() != null) {
            IMLogger.d("CoreVersion getX5WebViewExtension QQBrowserVersion : " + this.mWebView.getX5WebViewExtension().getQQBrowserVersion());
        } else {
            IMLogger.d("CoreVersion getX5WebViewExtension null");
        }
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    /* access modifiers changed from: private */
    public void displayRefreshBtn(boolean isShow) {
        int i;
        int i2;
        int i3 = 0;
        if (!(this.mRefreshBottomBtn == null || this.mStopBottomBtn == null)) {
            this.mRefreshBottomBtn.setVisibility(isShow ? 0 : 8);
            ImageButton imageButton = this.mStopBottomBtn;
            if (!isShow) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            imageButton.setVisibility(i2);
        }
        if (this.mRefreshTopBtn != null && this.mStopTopBtn != null) {
            ImageButton imageButton2 = this.mRefreshTopBtn;
            if (isShow) {
                i = 0;
            } else {
                i = 8;
            }
            imageButton2.setVisibility(i);
            ImageButton imageButton3 = this.mStopTopBtn;
            if (isShow) {
                i3 = 8;
            }
            imageButton3.setVisibility(i3);
        }
    }

    /* access modifiers changed from: private */
    public void changeBackForwardBtnState() {
        if (this.mWebView != null) {
            if (this.mForwardBtn != null) {
                if (this.mWebView.canGoForward()) {
                    if (this.mForwardOnDrawable != null) {
                        this.mForwardBtn.setImageDrawable(this.mForwardOnDrawable);
                    }
                } else if (this.mForwardDrawable != null) {
                    this.mForwardBtn.setImageDrawable(this.mForwardDrawable);
                }
            }
            if (this.mBackBtn == null) {
                return;
            }
            if (this.mWebView.canGoBack()) {
                if (this.mBackOnDrawable != null) {
                    this.mBackBtn.setImageDrawable(this.mBackOnDrawable);
                }
            } else if (this.mBackDrawable != null) {
                this.mBackBtn.setImageDrawable(this.mBackDrawable);
            }
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            setFullScreenCompat();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        setFullScreenCompat();
        this.mWebView.onResume();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.mWebView.onPause();
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void setFullScreenCompat() {
        IMLogger.d("current version number is " + Build.VERSION.SDK_INT);
        if (MetaDataUtil.readMetaDataFromApplication(this, NAV_HIDE, false)) {
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
                getWindow().getDecorView().setSystemUiVisibility(8);
            } else if (Build.VERSION.SDK_INT >= 19) {
                View decorView = getWindow().getDecorView();
                IMLogger.d(MessengerShareContentUtility.SHARE_BUTTON_HIDE);
                decorView.setSystemUiVisibility(4871);
            }
        }
        IMLogger.d("decor view height1:" + getWindow().getDecorView().getHeight());
    }

    private int getNavigationBarHeight() {
        Resources resources = getResources();
        return resources.getDimensionPixelSize(resources.getIdentifier("navigation_bar_height", "dimen", SystemMediaRouteProvider.PACKAGE_NAME));
    }

    private void setFullScreenCompatBefore() {
        IMLogger.d("current version number is " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT < 16) {
            requestWindowFeature(1);
            getWindow().setFlags(1024, 1024);
            return;
        }
        View decorView = getWindow().getDecorView();
        int uiOptions = 5381;
        if (MetaDataUtil.readMetaDataFromApplication(this, NAV_HIDE, false)) {
            uiOptions = 5893 | 2;
        }
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    private boolean killWebViewInSingleProcess() {
        try {
            IMLogger.d("killWebViewInSingleProcess start");
            for (ActivityManager.RunningAppProcessInfo appProcess : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
                if (appProcess.processName.contains(INNER_PROCESS)) {
                    if (!AndroidRomUtil.autoKillMultiProcess()) {
                        IMLogger.d("killWebViewInSingleProcess");
                        Process.killProcess(appProcess.pid);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        return false;
    }

    @SuppressLint({"NewApi"})
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMLogger.d("LifeCycle onCreate, thread=" + Thread.currentThread().getName());
        if (isIndependentProcess()) {
            IMConfig.initialize(this);
            if (Build.VERSION.SDK_INT >= 28 && Application.getProcessName().contains(INNER_PROCESS)) {
                try {
                    IMLogger.d("on Android P setDataDirectorySuffix start");
                    android.webkit.WebView.setDataDirectorySuffix(INNER_PROCESS);
                } catch (Exception e) {
                    IMLogger.d("on Android P setDataDirectorySuffix error: " + e.getMessage());
                }
            }
        }
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            public void onCoreInitFinished() {
                IMLogger.d("Qbsdk onCoreInitFinished");
            }

            public void onViewInitFinished(boolean b) {
                IMLogger.d("Qbsdk onViewInitFinished : " + b);
            }
        });
        getIntentValue(getIntent());
        if (!this.mIsX5Work) {
            QbSdk.forceSysWebView();
            IMLogger.d("onCreate Qbsdk with system core!");
        } else {
            IMLogger.d("onCreate Qbsdk with X5 core!");
        }
        processZoom();
        getWindow().setFlags(1024, 1024);
        setContentView(ResourceUtil.getLayoutId(this, "com_tencent_msdk_webview_window"));
        initLayout();
        initWebView();
        changeBackForwardBtnState();
        loadUrl(getIntent());
        bindWebViewService();
        HandlerThread handlerThread = new HandlerThread("handler-thread-client");
        handlerThread.start();
        this.mMsgHandler = new MsgHandler(handlerThread.getLooper());
        this.mClientMessenger = new Messenger(this.mMsgHandler);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && this.mWebView != null) {
            loadUrl(intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        IMLogger.d("LifeCycle onDestroy");
        sendMessageToServer(21, "", (Messenger) null);
        try {
            if (this.mWebView != null) {
                this.mWebView.stopLoading();
                this.mWebView.removeAllViews();
                this.mWebView.destroy();
                this.mWebView = null;
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        super.onDestroy();
        unBindWebViewService();
        killWebViewInSingleProcess();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        IMLogger.d("onConfigurationChanged");
        setNavNestScrollEnable();
        processZoom();
        this.mWebView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                WebViewWithFavActivity.this.changeWebViewToolBar();
                if (Build.VERSION.SDK_INT >= 16) {
                    WebViewWithFavActivity.this.mWebView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    WebViewWithFavActivity.this.mWebView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void changeWebViewToolBar() {
        boolean z = true;
        if (DisplayUtil.isPortrait(this)) {
            IMLogger.d("onConfigurationChanged isPortrait");
            this.mShareGridView.setNumColumns(4);
            if (this.mRefreshBottomLayout != null) {
                this.mRefreshBottomLayout.setVisibility(8);
            }
            if (!this.mToolBarCanLandShow) {
                this.mParamsNestView.setBehavior(this.mNestBehavior);
                if (this.mTopToolBarLayout != null) {
                    this.mTopToolBarLayout.setVisibility(0);
                }
                if (this.mParamsBottomBarView != null) {
                    this.mParamsBottomBarView.setBehavior(this.mBehaviorPortrait);
                }
                StringBuilder append = new StringBuilder().append("mBottomToolBarLayout v:");
                if (this.mBottomToolBarLayout.getVisibility() != 0) {
                    z = false;
                }
                IMLogger.d(append.append(z).toString());
                if (this.mBottomToolBarLayout != null) {
                    this.mBottomToolBarLayout.setVisibility(0);
                }
            }
        } else if (DisplayUtil.isLandscape(this)) {
            StringBuilder append2 = new StringBuilder().append("mBottomToolBarLayout v:");
            if (this.mBottomToolBarLayout.getVisibility() != 0) {
                z = false;
            }
            IMLogger.d(append2.append(z).toString());
            IMLogger.d("onConfigurationChanged isLandscape");
            this.mShareGridView.setNumColumns(5);
            if (this.mRefreshBottomLayout != null) {
                this.mRefreshBottomLayout.setVisibility(0);
            }
            if (!this.mToolBarCanLandShow) {
                this.mParamsNestView.setBehavior((CoordinatorLayout.Behavior) null);
                if (this.mParamsBottomBarView != null) {
                    this.mParamsBottomBarView.setBehavior(this.mBehaviorLand);
                }
                if (this.mTopToolBarLayout != null) {
                    this.mTopToolBarLayout.setVisibility(8);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void processWebViewEvent(int eventId) {
        if (this.mWebView == null) {
            IMLogger.e("mWebView is null , please check init.");
            return;
        }
        IMLogger.d("eventId = " + eventId);
        switch (eventId) {
            case 26:
                if (this.mCustomView != null) {
                    if (this.mWebChromeClient != null) {
                        this.mWebChromeClient.onHideCustomView();
                        return;
                    }
                    return;
                } else if (this.mWebView.canGoBack()) {
                    this.mWebView.goBack();
                    return;
                } else {
                    return;
                }
            case 27:
                if (this.mWebView.canGoForward()) {
                    this.mWebView.goForward();
                    return;
                }
                return;
            case 28:
                refreshOnclick((View) null);
                return;
            case 29:
                finish();
                return;
            default:
                IMLogger.w("not support eventId = " + eventId);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IMLogger.d("WVMActivity onActivityResult, requestCode:" + requestCode + ", resultCode:" + resultCode);
        if (resultCode == -1) {
            switch (requestCode) {
                case 78:
                    if (this.mUploadFile != null) {
                        this.mUploadFile.onReceiveValue(data == null ? null : data.getData());
                        this.mUploadFile = null;
                        return;
                    }
                    return;
                case 79:
                    if (this.mUploadFileAndroid5 != null) {
                        onActivityResultAboveL(requestCode, resultCode, data);
                        return;
                    }
                    return;
                default:
                    return;
            }
        } else if (resultCode == 0) {
            switch (requestCode) {
                case 78:
                    if (this.mUploadFile != null) {
                        this.mUploadFile.onReceiveValue((Object) null);
                        this.mUploadFile = null;
                        return;
                    }
                    return;
                case 79:
                    if (this.mUploadFileAndroid5 != null) {
                        this.mUploadFileAndroid5.onReceiveValue((Object) null);
                        this.mUploadFileAndroid5 = null;
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @TargetApi(21)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent data) {
        if (requestCode == 79 && this.mUploadFileAndroid5 != null) {
            Uri[] results = null;
            if (resultCode == -1 && data != null) {
                String dataString = data.getDataString();
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        results[i] = clipData.getItemAt(i).getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
            this.mUploadFileAndroid5.onReceiveValue(results);
            this.mUploadFileAndroid5 = null;
        }
    }

    class CustomWebChromeClient extends WebChromeClient {
        CustomWebChromeClient() {
        }

        private void dealWithFileChooser(int requestCode) {
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.addCategory("android.intent.category.OPENABLE");
            intent.setType("*/*");
            try {
                int resID = ResourceUtil.getStringId(WebViewWithFavActivity.this, "msdk_upload_file_title");
                WebViewWithFavActivity.this.startActivityForResult(Intent.createChooser(intent, resID != 0 ? WebViewWithFavActivity.this.getResources().getString(resID) : "update file"), requestCode);
            } catch (ActivityNotFoundException e) {
                IMLogger.d(e.getMessage());
            }
        }

        public void onProgressChanged(WebView webView, int newProgress) {
            if (newProgress == 100) {
                WebViewWithFavActivity.this.changeBackForwardBtnState();
                if (!(WebViewWithFavActivity.this.mTopProgressBar == null || WebViewWithFavActivity.this.mTopProgressBar.getVisibility() == 8)) {
                    WebViewWithFavActivity.this.mTopProgressBar.setVisibility(8);
                }
                if (WebViewWithFavActivity.this.mBottomProgressBar != null && WebViewWithFavActivity.this.mBottomProgressBar.getVisibility() != 8) {
                    WebViewWithFavActivity.this.mBottomProgressBar.setVisibility(8);
                }
            } else if (WebViewWithFavActivity.this.mTopProgressBar == null || WebViewWithFavActivity.this.mBottomProgressBar == null) {
                if (WebViewWithFavActivity.this.mTopProgressBar != null) {
                    if (WebViewWithFavActivity.this.mTopProgressBar.getVisibility() == 8) {
                        WebViewWithFavActivity.this.mTopProgressBar.setVisibility(0);
                    }
                    WebViewWithFavActivity.this.mTopProgressBar.setProgress(newProgress);
                }
                if (WebViewWithFavActivity.this.mBottomProgressBar != null) {
                    if (WebViewWithFavActivity.this.mBottomProgressBar.getVisibility() == 8) {
                        WebViewWithFavActivity.this.mBottomProgressBar.setVisibility(0);
                    }
                    WebViewWithFavActivity.this.mBottomProgressBar.setProgress(newProgress);
                }
            } else if (DisplayUtil.isPortrait(WebViewWithFavActivity.this)) {
                if (WebViewWithFavActivity.this.mTopProgressBar.getVisibility() == 8) {
                    WebViewWithFavActivity.this.mTopProgressBar.setVisibility(0);
                }
                WebViewWithFavActivity.this.mTopProgressBar.setProgress(newProgress);
            } else {
                if (WebViewWithFavActivity.this.mBottomProgressBar.getVisibility() == 8) {
                    WebViewWithFavActivity.this.mBottomProgressBar.setVisibility(0);
                }
                WebViewWithFavActivity.this.mBottomProgressBar.setProgress(newProgress);
            }
        }

        public boolean onShowFileChooser(WebView mWebView, com.tencent.smtt.sdk.ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            IMLogger.d("onShowFileChooser");
            if (WebViewWithFavActivity.this.mUploadFileAndroid5 != null) {
                WebViewWithFavActivity.this.mUploadFileAndroid5.onReceiveValue((Object) null);
                ValueCallback unused = WebViewWithFavActivity.this.mUploadFileAndroid5 = null;
            }
            ValueCallback unused2 = WebViewWithFavActivity.this.mUploadFileAndroid5 = filePathCallback;
            dealWithFileChooser(79);
            return true;
        }

        public void openFileChooser(com.tencent.smtt.sdk.ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
            ValueCallback unused = WebViewWithFavActivity.this.mUploadFile = uploadFile;
            dealWithFileChooser(78);
        }

        public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
            IMLogger.d("onShowCustomView");
            if (WebViewWithFavActivity.this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            View unused = WebViewWithFavActivity.this.mCustomView = view;
            int unused2 = WebViewWithFavActivity.this.mOriginalSystemUiVisibility = WebViewWithFavActivity.this.getWindow().getDecorView().getSystemUiVisibility();
            int unused3 = WebViewWithFavActivity.this.mOriginalOrientation = WebViewWithFavActivity.this.getRequestedOrientation();
            IX5WebChromeClient.CustomViewCallback unused4 = WebViewWithFavActivity.this.mCustomViewCallback = customViewCallback;
            ((ViewGroup) WebViewWithFavActivity.this.getWindow().getDecorView()).addView(WebViewWithFavActivity.this.mCustomView, new ViewGroup.LayoutParams(-1, -1));
            WebViewWithFavActivity.this.getWindow().getDecorView().setSystemUiVisibility(3846);
            WebViewWithFavActivity.this.setRequestedOrientation(0);
        }

        public void onHideCustomView() {
            IMLogger.d("onHideCustomView");
            ((ViewGroup) WebViewWithFavActivity.this.getWindow().getDecorView()).removeView(WebViewWithFavActivity.this.mCustomView);
            View unused = WebViewWithFavActivity.this.mCustomView = null;
            WebViewWithFavActivity.this.getWindow().getDecorView().setSystemUiVisibility(WebViewWithFavActivity.this.mOriginalSystemUiVisibility);
            WebViewWithFavActivity.this.setRequestedOrientation(WebViewWithFavActivity.this.mOriginalOrientation);
            WebViewWithFavActivity.this.mCustomViewCallback.onCustomViewHidden();
            IX5WebChromeClient.CustomViewCallback unused2 = WebViewWithFavActivity.this.mCustomViewCallback = null;
        }

        public void openFileChooser(com.tencent.smtt.sdk.ValueCallback<Uri> uploadFile, String acceptType) {
            ValueCallback unused = WebViewWithFavActivity.this.mUploadFile = uploadFile;
            dealWithFileChooser(78);
        }

        public void openFileChooser(com.tencent.smtt.sdk.ValueCallback<Uri> uploadFile) {
            ValueCallback unused = WebViewWithFavActivity.this.mUploadFile = uploadFile;
            dealWithFileChooser(78);
        }
    }

    @JavascriptInterface
    public void jsCallNative(String jsParasJson) {
        IMLogger.d("jsCallNative jsParasJson=" + jsParasJson);
        sendMessageToServer(22, jsParasJson, (Messenger) null);
    }

    public void callJs(final String parasJson) {
        IMLogger.d("callJs parasJson : " + parasJson);
        if (this.mWebView == null) {
            IMLogger.d("webView instance is null");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    StringBuilder js = new StringBuilder("IMSDKCallJs");
                    js.append("('").append(parasJson).append("')");
                    if (Build.VERSION.SDK_INT >= 19) {
                        WebViewWithFavActivity.this.mWebView.evaluateJavascript(js.toString(), new com.tencent.smtt.sdk.ValueCallback<String>() {
                            public void onReceiveValue(String s) {
                                WebViewWithFavActivity.this.sendMessageToServer(4, s, (Messenger) null);
                            }
                        });
                    } else {
                        WebViewWithFavActivity.this.mWebView.loadUrl("javascript:" + js.toString());
                    }
                }
            });
        }
    }

    private void bindWebViewService() {
        bindService(new Intent(this, WebViewService.class), this.mServiceConnection, 1);
    }

    private void unBindWebViewService() {
        IMLogger.d("unBindWebViewService mBound=" + this.mBound);
        if (this.mBound) {
            unbindService(this.mServiceConnection);
            this.mBound = false;
        }
    }

    private void sendMessageToHandler(int what, int arg1) {
        if (this.mMsgHandler != null) {
            if (this.mMsgHandler.hasMessages(what)) {
                this.mMsgHandler.removeMessages(what);
            }
            Message msg = this.mMsgHandler.obtainMessage(what);
            msg.arg1 = arg1;
            msg.sendToTarget();
        }
    }

    /* access modifiers changed from: private */
    public void sendMessageToServer(int what, String parasJson, Messenger messenger) {
        if (!this.mBound) {
            bindWebViewService();
            return;
        }
        Message msg = Message.obtain((Handler) null, what);
        Bundle data = new Bundle();
        if (!T.ckIsEmpty(parasJson)) {
            data.putString("json_data", parasJson);
        }
        if (messenger != null) {
            msg.replyTo = messenger;
        }
        msg.setData(data);
        try {
            this.mServerMessenger.send(msg);
        } catch (RemoteException e) {
            IMLogger.d(e.getMessage());
        }
    }

    private void setNavNestScrollEnable() {
        if (!MetaDataUtil.readMetaDataFromApplication(this, NAV_HIDE, false) && !MetaDataUtil.readMetaDataFromApplication(this, NAV_NEST_SCROLL, false)) {
            IMLogger.d("nest scroll function will disable");
            this.mBehaviorPortrait = null;
            this.mBehaviorLand = null;
            this.mNestBehavior = null;
            if (this.mParamsNestView != null && this.mParamsBottomBarView != null) {
                this.mParamsNestView.setBehavior((CoordinatorLayout.Behavior) null);
                this.mParamsBottomBarView.setBehavior((CoordinatorLayout.Behavior) null);
                int height = 0;
                int resID = ResourceUtil.getDimenId(this, "toolbar_all_height");
                if (resID != 0) {
                    height = (int) getResources().getDimension(resID);
                }
                this.mParamsNestView.bottomMargin = height;
                if (DisplayUtil.isPortrait(this)) {
                    this.mParamsNestView.topMargin = height;
                } else if (DisplayUtil.isLandscape(this) && !this.mToolBarCanLandShow) {
                    this.mParamsNestView.topMargin = 0;
                }
            }
        }
    }

    class MsgHandler extends Handler {
        public MsgHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IMLogger.d("handleMessage, msg=" + msg.what);
            if (WebViewWithFavActivity.this.mWebView == null) {
                IMLogger.e("mWebView is null , unknown problem happen");
                return;
            }
            switch (msg.what) {
                case 24:
                    Bundle data = msg.getData();
                    WebViewWithFavActivity.this.callJs(data != null ? data.getString("json_data") : "");
                    return;
                default:
                    final int eventId = msg.what;
                    WebViewWithFavActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            WebViewWithFavActivity.this.processWebViewEvent(eventId);
                        }
                    });
                    return;
            }
        }
    }

    private boolean isIndependentProcess() {
        try {
            IMLogger.d("isIndependentProcess start");
            for (ActivityManager.RunningAppProcessInfo appProcess : ((ActivityManager) getSystemService("activity")).getRunningAppProcesses()) {
                if (appProcess.processName.contains(INNER_PROCESS)) {
                    IMLogger.d("isIndependentProcess return true");
                    return true;
                }
            }
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
        }
        return false;
    }
}
