package com.tencent.imsdk.webview.qq;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.imsdk.android.friend.IMSDKFileProvider;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.etc.CommonUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.MetaDataUtil;
import com.tencent.imsdk.tool.etc.T;
import com.tencent.imsdk.webview.api.IMWebViewActionListener;
import com.tencent.imsdk.webview.request.WebviewActionResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class WebViewManager {
    protected static final int CODE_WEBVIEW_CALLJS_RETURN = 4;
    protected static final int CODE_WEBVIEW_CLOSE = 2;
    protected static final int CODE_WEBVIEW_JS_CALL = 3;
    protected static final int CODE_WEBVIEW_OPEN = 1;
    public static final String EXTRA_X5_WORK = "extra_x5_work";
    protected static final String KEY_JSON = "json_data";
    public static final String LABEL_HEIGHT = "webview_height";
    public static final String LABEL_IS_LANDSCAPE = "webview_is_landscape";
    public static final String LABEL_IS_ORIENTATION = "webview_is_orientation";
    public static final String LABEL_SHARE_URL = "webview_share_url";
    public static final String LABEL_URL = "webview_url";
    public static final String LABEL_WIDTH = "webview_width";
    protected static final int MSG_ACTIVITY_FINISH = 21;
    protected static final int MSG_ACTIVITY_MESSENGER = 23;
    protected static final int MSG_JAVA_CALL_JS = 24;
    protected static final int MSG_JAVA_CALL_JS_RETURN = 25;
    protected static final int MSG_JS_CALL_JAVA = 22;
    protected static final int MSG_WEBVIEW_EVENT_BACK = 26;
    protected static final int MSG_WEBVIEW_EVENT_CLOSE = 29;
    protected static final int MSG_WEBVIEW_EVENT_FORWARD = 27;
    protected static final int MSG_WEBVIEW_EVENT_RELOAD = 28;
    protected static final String X5_CORE = "com.tencent.imsdk.webview.x5work";
    private static WebViewManager instance = null;
    /* access modifiers changed from: private */
    public static FrameLayout.LayoutParams layoutParams = null;
    public static IMWebViewActionListener mActionListener;
    /* access modifiers changed from: private */
    public int WEBVIEW_ID = 41468;
    /* access modifiers changed from: private */
    public Context act = null;
    private int bgA = 0;
    private int bgB = 0;
    private int bgG = 0;
    private int bgR = 0;
    /* access modifiers changed from: private */
    public CloseWebViewCallBack closeCallback;
    private DisplayMetrics displayMetrics;
    private boolean isLandscape = true;
    /* access modifiers changed from: private */
    public boolean isLoaded = false;
    /* access modifiers changed from: private */
    public boolean isSetBackgroundColor = false;
    private boolean isToolBar = false;
    public boolean isUserSetOrientation = false;
    /* access modifiers changed from: private */
    public Messenger mClientMessenger = null;
    private boolean mIsToolbar;
    /* access modifiers changed from: private */
    public boolean mIsX5Set = false;
    /* access modifiers changed from: private */
    public boolean mIsX5Work = false;
    private Messenger mServerMessenger = null;
    /* access modifiers changed from: private */
    public WebView mWebView = null;
    /* access modifiers changed from: private */
    public OpenWebViewCallBack openCallback;
    private String url = "";
    /* access modifiers changed from: private */
    public boolean waitForClose = false;
    private float webviewHeight = 0.8f;
    private float webviewWidth = 0.7f;

    public interface CloseWebViewCallBack {
        void onFail();

        void onSuccess();
    }

    public interface OpenWebViewCallBack {
        void onFail();

        void onSuccess();
    }

    private synchronized void setX5Config(boolean isX5Work) {
        this.mIsX5Set = true;
        this.mIsX5Work = isX5Work;
    }

    private void runOnUiThread(Runnable runnable) {
        try {
            getCurrentActivity().runOnUiThread(runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentUrl() {
        return this.url;
    }

    public static WebViewManager getInstance() {
        if (instance == null) {
            synchronized (WebViewManager.class) {
                if (instance == null) {
                    instance = new WebViewManager();
                }
            }
        }
        return instance;
    }

    public void init(Context cnt) {
        this.act = cnt;
        this.displayMetrics = new DisplayMetrics();
        getCurrentActivity().getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
        layoutParams = new FrameLayout.LayoutParams(this.displayMetrics.widthPixels, this.displayMetrics.heightPixels);
    }

    public void setZoom(float webviewHeight2, float webviewWidht) {
        this.webviewHeight = webviewHeight2;
        this.webviewWidth = webviewWidht;
        setPosition((int) ((((float) this.displayMetrics.widthPixels) * (1.0f - webviewWidht)) / 2.0f), (int) ((((float) this.displayMetrics.heightPixels) * (1.0f - webviewHeight2)) / 2.0f), (int) (((float) this.displayMetrics.widthPixels) * webviewHeight2), (int) (((float) this.displayMetrics.heightPixels) * webviewWidht));
    }

    public void setOrientation(boolean isLandscape2) {
        this.isLandscape = isLandscape2;
    }

    public void openUrl(String url2, String ticket, boolean toolBar, boolean browser) {
        if (browser) {
            String openUrl = url2;
            try {
                if (!openUrl.startsWith("http://") && !openUrl.startsWith("https://") && !openUrl.startsWith(IMSDKFileProvider.FILE_SCHEME) && !openUrl.startsWith("ftp://")) {
                    IMLogger.w("you had better use a valid url start with http:// or https:// or ftp:// or file://");
                    openUrl = "http://" + openUrl;
                }
                IMLogger.d("url open by browser is : " + openUrl);
                List<String> paraList = new ArrayList<>();
                int resID = ResourceUtil.getArrayId(this.act, "urlKeyFilter");
                if (resID != 0) {
                    for (String trim : this.act.getResources().getStringArray(resID)) {
                        paraList.add(trim.trim());
                    }
                } else {
                    IMLogger.w("urlKeyFilter not set!");
                }
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(DisplayUtil.filterUrlParaKey(openUrl, paraList)));
                if (this.act != null) {
                    this.act.startActivity(intent);
                } else {
                    IMLogger.e("initialize should be called before open url");
                }
            } catch (Exception e) {
                IMLogger.e(e.getMessage());
            }
        } else {
            openUrl(url2, ticket, toolBar);
        }
    }

    public void openUrl(String openUrl, String ticket, boolean toolBar) {
        this.isToolBar = toolBar;
        IMLogger.d(openUrl);
        if (openUrl == null) {
            IMLogger.w("this api need a valid url start with http:// or https:// or file:// or ftp://");
            return;
        }
        if (!openUrl.startsWith("http://") && !openUrl.startsWith("https://") && !openUrl.startsWith(IMSDKFileProvider.FILE_SCHEME) && !openUrl.startsWith("ftp://")) {
            IMLogger.w("you had better use a valid url start with http:// or https:// or ftp:// or file://");
            openUrl = "http://" + openUrl;
        }
        String shareUrl = openUrl;
        try {
            if (!TextUtils.isEmpty(ticket)) {
                if (CommonUtil.ckIsEmpty(new URL(openUrl).getQuery())) {
                    openUrl = openUrl + "?";
                    IMLogger.d("ckIsEmpty openUrl=" + openUrl);
                } else {
                    if (!openUrl.endsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
                        openUrl = openUrl + HttpRequest.HTTP_REQ_ENTITY_JOIN;
                    }
                    IMLogger.d("not ckIsEmpty openUrl=" + openUrl);
                }
                openUrl = openUrl + "sTicket=" + ticket;
                IMLogger.d("openUrl=" + openUrl);
            }
            this.url = openUrl;
            IMLogger.d("open url = " + openUrl);
            this.mIsToolbar = this.isToolBar;
            if (this.isToolBar) {
                HandlerThread handlerThread = new HandlerThread("handler-thread-server");
                handlerThread.start();
                this.mServerMessenger = new Messenger(new MsgHandler(handlerThread.getLooper()));
                WebViewService.setMessenger(this.mServerMessenger);
                loadUrlInWnd(this.url, shareUrl, toolBar);
                return;
            }
            loadUrlInWebView(this.url);
        } catch (MalformedURLException e) {
            IMLogger.w("this api need a valid url start with http:// or https:// ");
            e.printStackTrace();
        }
    }

    public void openUrlWithX5(String openUrl, String ticket, boolean toolBar, boolean isX5Work) {
        this.isToolBar = toolBar;
        IMLogger.d(openUrl);
        if (openUrl == null) {
            IMLogger.w("this api need a valid url start with http:// or https:// or file:// or ftp://");
            return;
        }
        if (!openUrl.startsWith("http://") && !openUrl.startsWith("https://") && !openUrl.startsWith(IMSDKFileProvider.FILE_SCHEME) && !openUrl.startsWith("ftp://")) {
            IMLogger.w("you had better use a valid url start with http:// or https:// or ftp:// or file://");
            openUrl = "http://" + openUrl;
        }
        String shareUrl = openUrl;
        try {
            if (!TextUtils.isEmpty(ticket)) {
                if (CommonUtil.ckIsEmpty(new URL(openUrl).getQuery())) {
                    openUrl = openUrl + "?";
                    IMLogger.d("ckIsEmpty openUrl=" + openUrl);
                } else {
                    if (!openUrl.endsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
                        openUrl = openUrl + HttpRequest.HTTP_REQ_ENTITY_JOIN;
                    }
                    IMLogger.d("not ckIsEmpty openUrl=" + openUrl);
                }
                openUrl = openUrl + "sTicket=" + ticket;
                IMLogger.d("openUrl=" + openUrl);
            }
            this.url = openUrl;
            IMLogger.d("open url = " + openUrl);
            this.mIsToolbar = this.isToolBar;
            setX5Config(isX5Work);
            if (this.isToolBar) {
                HandlerThread handlerThread = new HandlerThread("handler-thread-server");
                handlerThread.start();
                this.mServerMessenger = new Messenger(new MsgHandler(handlerThread.getLooper()));
                WebViewService.setMessenger(this.mServerMessenger);
                loadUrlInWnd(this.url, shareUrl, toolBar);
                return;
            }
            loadUrlInWebView(this.url);
        } catch (MalformedURLException e) {
            IMLogger.w("this api need a valid url start with http:// or https:// ");
            e.printStackTrace();
        }
    }

    private void loadUrlInWebView(final String url2) {
        this.isLoaded = false;
        this.openCallback = new OpenWebViewCallBack() {
            public void onSuccess() {
                if (WebViewManager.this.waitForClose) {
                    WebViewManager.this.close();
                }
            }

            public void onFail() {
            }
        };
        IMLogger.d("test  loadUrlInWebView start");
        initWebView();
        runOnUiThread(new Runnable() {
            @SuppressLint({"NewApi"})
            public void run() {
                if (WebViewManager.this.mWebView != null) {
                    if (WebViewManager.this.mWebView.getParent() != null) {
                        ((ViewGroup) WebViewManager.this.mWebView.getParent()).removeView(WebViewManager.this.mWebView);
                    }
                    if (WebViewManager.this.mWebView.getParent() == null) {
                        IMLogger.d("add webview start!");
                        if (WebViewManager.this.getCurrentActivity() != null) {
                            IMLogger.d("getCurrentActivity is not null");
                        } else {
                            IMLogger.d("getCurrentActivity is null");
                        }
                        WebViewManager.this.getCurrentActivity().getWindow().addContentView(WebViewManager.this.mWebView, WebViewManager.layoutParams);
                        IMLogger.d("add webview end!");
                    } else {
                        WebViewManager.this.mWebView.setLayoutParams(WebViewManager.layoutParams);
                    }
                    WebViewManager.this.mWebView.setVisibility(0);
                    WebViewManager.this.mWebView.requestFocus();
                    WebViewManager.this.mWebView.bringToFront();
                    WebViewManager.this.mWebView.loadUrl(url2);
                    WebViewManager.this.openCallback.onSuccess();
                    boolean unused = WebViewManager.this.isSetBackgroundColor = false;
                    return;
                }
                IMLogger.d("web view instance is null !");
                WebViewManager.this.initWebView();
            }
        });
        IMLogger.d("loadUrlInWebView end");
    }

    /* access modifiers changed from: private */
    @SuppressLint({"NewApi"})
    public void initWebView() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (WebViewManager.this.mWebView != null) {
                    IMLogger.d("mWebView is not null");
                    return;
                }
                QbSdk.initX5Environment(WebViewManager.this.act, new QbSdk.PreInitCallback() {
                    public void onCoreInitFinished() {
                        IMLogger.d("Qbsdk onCoreInitFinished");
                    }

                    public void onViewInitFinished(boolean b) {
                        IMLogger.d("Qbsdk onViewInitFinished : " + b);
                    }
                });
                if (!WebViewManager.this.getX5WorkFlag(WebViewManager.this.act, WebViewManager.this.mIsX5Set, WebViewManager.this.mIsX5Work)) {
                    QbSdk.forceSysWebView();
                    IMLogger.d("initWebView with system core!");
                } else {
                    IMLogger.d("initWebView with X5 core!");
                }
                WebView unused = WebViewManager.this.mWebView = new WebView(WebViewManager.this.act);
                WebViewManager.this.mWebView.setId(WebViewManager.this.WEBVIEW_ID);
                WebViewManager.this.mWebView.setWebViewClient(new WebViewClient() {
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        boolean unused = WebViewManager.this.isLoaded = true;
                    }

                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }
                });
                WebViewManager.this.mWebView.setWebChromeClient(new WebChromeClient());
                WebViewManager.this.mWebView.setDownloadListener(new DownloadListener() {
                    @SuppressLint({"NewApi"})
                    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                        try {
                            WebViewManager.this.act.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                        } catch (ActivityNotFoundException e) {
                            IMLogger.e("default browser is uninstalled!");
                        }
                    }
                });
                try {
                    if (Build.VERSION.SDK_INT >= 11) {
                        WebViewManager.this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
                        WebViewManager.this.mWebView.removeJavascriptInterface("accessibility");
                        WebViewManager.this.mWebView.removeJavascriptInterface("accessibilityTraversal");
                    }
                    WebViewManager.this.mWebView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    WebViewManager.this.mWebView.addJavascriptInterface(WebViewManager.this, "IMSDK");
                    WebSettings webSetting = WebViewManager.this.mWebView.getSettings();
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
                    webSetting.setAppCachePath(WebViewManager.this.act.getDir("appcache", 0).getPath());
                    webSetting.setDatabasePath(WebViewManager.this.act.getDir("databases", 0).getPath());
                    webSetting.setGeolocationDatabasePath(WebViewManager.this.act.getDir("geolocation", 0).getPath());
                    webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
                    webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
                    CookieSyncManager.createInstance(WebViewManager.this.act);
                    CookieSyncManager.getInstance().sync();
                } catch (Exception e) {
                    IMLogger.d(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public Activity getCurrentActivity() {
        try {
            if (this.act != null) {
                return (Activity) this.act;
            }
            return null;
        } catch (Exception e) {
            IMLogger.e(e.getMessage());
            return null;
        }
    }

    public void setBackgroundColor(int a, int r, int g, int b) {
        IMLogger.d("set background color : " + a + ", " + r + ", " + g + ", " + b);
        this.isSetBackgroundColor = true;
        this.bgA = a;
        this.bgR = r;
        this.bgG = g;
        this.bgB = b;
    }

    public boolean setCenter(boolean center) {
        IMLogger.d("set center : " + center);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
            return false;
        } else if (!center) {
            return center;
        } else {
            layoutParams.gravity = 17;
            return center;
        }
    }

    public void setSize(int width, int height) {
        IMLogger.d("set size : " + width + ", " + height);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
            return;
        }
        layoutParams.width = width;
        layoutParams.height = height;
    }

    public void setPosition(int left, int top, int width, int height) {
        IMLogger.d("set position : " + left + ", " + top + ", " + width + ", " + height);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
            return;
        }
        layoutParams.gravity = 51;
        layoutParams.leftMargin = left;
        layoutParams.topMargin = top;
        layoutParams.width = width;
        layoutParams.height = height;
        this.webviewWidth = ((float) width) / ((float) this.displayMetrics.widthPixels);
        this.webviewHeight = ((float) height) / ((float) this.displayMetrics.heightPixels);
    }

    public void setX(int x) {
        IMLogger.d("set x : " + x);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
        } else {
            layoutParams.leftMargin = x;
        }
    }

    public void setY(int y) {
        IMLogger.d("set y : " + y);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
        } else {
            layoutParams.topMargin = y;
        }
    }

    public void setWidth(int width) {
        IMLogger.d("set w : " + width);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
        } else {
            layoutParams.width = width;
        }
    }

    public void setHeight(int height) {
        IMLogger.d("set h : " + height);
        if (layoutParams == null) {
            IMLogger.d("web view instance is null !");
        } else {
            layoutParams.height = height;
        }
    }

    public void getFocus() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (WebViewManager.this.mWebView != null) {
                    WebViewManager.this.mWebView.bringToFront();
                    WebViewManager.this.mWebView.requestFocus();
                    IMLogger.d("webview isFocusable " + WebViewManager.this.mWebView.isFocusable() + " , isFocused " + WebViewManager.this.mWebView.isFocused() + ",  isFocusableInTouchMode " + WebViewManager.this.mWebView.isFocusableInTouchMode());
                }
            }
        });
    }

    public void close() {
        IMLogger.d("close, mIsToolbar=" + this.mIsToolbar);
        if (this.mIsToolbar) {
            sendMessageToClient(29, "");
            return;
        }
        this.closeCallback = new CloseWebViewCallBack() {
            public void onSuccess() {
                boolean unused = WebViewManager.this.waitForClose = false;
            }

            public void onFail() {
                boolean unused = WebViewManager.this.waitForClose = true;
            }
        };
        closeWebview();
    }

    private void closeWebview() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    IMLogger.d("test closeWebview");
                    WebView webView = (WebView) ((Activity) WebViewManager.this.act).findViewById(WebViewManager.this.WEBVIEW_ID);
                    if (webView == null) {
                        IMLogger.w("test webView cannot find by id");
                        WebViewManager.this.closeCallback.onFail();
                        return;
                    }
                    IMLogger.d("test closeWebview mWebView!=null");
                    if (webView.getParent() != null) {
                        ((ViewGroup) webView.getParent()).removeView(webView);
                    }
                    webView.stopLoading();
                    webView.setVisibility(4);
                    webView.removeAllViews();
                    webView.destroy();
                    WebView unused = WebViewManager.this.mWebView = null;
                    boolean unused2 = WebViewManager.this.isLoaded = false;
                    WebViewManager.this.closeCallback.onSuccess();
                    WebViewManager.this.notifyWebViewClose();
                    IMLogger.d("test when close,web view re-set to null !");
                } catch (Exception e) {
                    IMLogger.e(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public void forward() {
        IMLogger.d("forward, mIsToolbar=" + this.mIsToolbar);
        if (this.mIsToolbar) {
            sendMessageToClient(27, "");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (WebViewManager.this.mWebView == null) {
                        IMLogger.d("web view instance is null !");
                    } else {
                        WebViewManager.this.mWebView.goForward();
                    }
                }
            });
        }
    }

    public void back() {
        IMLogger.d("back, mIsToolbar=" + this.mIsToolbar);
        if (this.mIsToolbar) {
            sendMessageToClient(26, "");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (WebViewManager.this.mWebView == null) {
                        IMLogger.d("web view instance is null !");
                    } else {
                        WebViewManager.this.mWebView.goBack();
                    }
                }
            });
        }
    }

    public void reload() {
        IMLogger.d("reload, mIsToolbar=" + this.mIsToolbar);
        if (this.mIsToolbar) {
            sendMessageToClient(28, "");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (WebViewManager.this.mWebView == null) {
                        IMLogger.d("web view instance is null !");
                    } else {
                        WebViewManager.this.mWebView.reload();
                    }
                }
            });
        }
    }

    public void stop() {
        IMLogger.d("stop");
        runOnUiThread(new Runnable() {
            public void run() {
                if (WebViewManager.this.mWebView == null) {
                    IMLogger.d("web view instance is null !");
                } else {
                    WebViewManager.this.mWebView.stopLoading();
                }
            }
        });
    }

    private void loadUrlInWnd(String url2, String shareUrl, boolean toolbar) {
        IMLogger.d("loadUrlInWnd 1");
        Intent webviewIntent = new Intent();
        webviewIntent.putExtra(LABEL_URL, url2);
        webviewIntent.putExtra(LABEL_HEIGHT, this.webviewHeight);
        webviewIntent.putExtra(LABEL_WIDTH, this.webviewWidth);
        webviewIntent.putExtra(LABEL_IS_LANDSCAPE, this.isLandscape);
        webviewIntent.putExtra(LABEL_SHARE_URL, shareUrl);
        webviewIntent.putExtra(LABEL_IS_ORIENTATION, this.isUserSetOrientation);
        webviewIntent.putExtra(EXTRA_X5_WORK, getX5WorkFlag(this.act, this.mIsX5Set, this.mIsX5Work));
        webviewIntent.putExtra("favUrl", "");
        webviewIntent.putExtra("adIds", "");
        if (toolbar) {
            webviewIntent.setClass(this.act, WebViewWithFavActivity.class);
        }
        try {
            IMLogger.d("act.startActivity(webviewIntent)");
            if (this.act == null) {
                IMLogger.e("context is null,pls check initialize");
            }
            startFavActivity(this.act, webviewIntent);
        } catch (ActivityNotFoundException e) {
            IMLogger.w("cann't start WebViewActivity，Are you sure to register activity?");
        }
    }

    public boolean isActivated() {
        IMLogger.d("isShowing");
        if (this.mWebView != null) {
            return this.isLoaded;
        }
        IMLogger.d("web view instance is null !");
        return false;
    }

    public boolean canGoBack() {
        IMLogger.d("canGoBack");
        if (this.mWebView != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (WebViewManager.this.mWebView == null) {
                        IMLogger.d("web view instance is null !");
                    } else {
                        WebViewManager.this.mWebView.canGoBack();
                    }
                }
            });
            return this.mWebView.canGoBack();
        }
        IMLogger.d("web view instance is null !");
        return false;
    }

    public boolean canGoForward() {
        IMLogger.d("canGoForward");
        if (this.mWebView != null) {
            return this.mWebView.canGoForward();
        }
        IMLogger.d("web view instance is null !");
        return false;
    }

    public void setWebViewActionListener(IMWebViewActionListener listener) {
        IMLogger.d("setWebViewActionListener");
        mActionListener = listener;
    }

    public void notifyWebViewClose() {
        WebviewActionResponse response = new WebviewActionResponse(1, "Webview Close Success");
        response.imsdkRetCode = 1;
        response.stateCode = 2;
        if (mActionListener != null) {
            try {
                IMLogger.d("notifyWebViewClose response=" + response.toUnityString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mActionListener.onAction(response);
        }
    }

    /* access modifiers changed from: private */
    public void notifyWebViewJS(String jsParasJson, int stateCode) {
        String retMsg = "";
        switch (stateCode) {
            case 3:
                retMsg = "js call IMSDK native.";
                break;
            case 4:
                retMsg = "return from CallJs";
                break;
        }
        WebviewActionResponse response = new WebviewActionResponse(1, retMsg);
        response.stateCode = stateCode;
        response.retExtraJson = jsParasJson;
        if (mActionListener != null) {
            try {
                IMLogger.d("notifyWebViewJS response=" + response.toUnityString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mActionListener.onAction(response);
        }
    }

    public void callJs(final String parasJson) {
        IMLogger.d("callJs parasJson : " + parasJson + ", mIsToolbar=" + this.mIsToolbar);
        if (this.mIsToolbar) {
            sendMessageToClient(24, parasJson);
        } else if (this.mWebView == null) {
            IMLogger.d("web view instance is null !");
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    StringBuilder js = new StringBuilder("IMSDKCallJs");
                    js.append("('").append(parasJson).append("')");
                    if (Build.VERSION.SDK_INT >= 19) {
                        WebViewManager.this.mWebView.evaluateJavascript(js.toString(), new ValueCallback<String>() {
                            public void onReceiveValue(String result) {
                                WebViewManager.this.notifyWebViewJS(result, 4);
                            }
                        });
                    } else {
                        WebViewManager.this.mWebView.loadUrl("javascript:" + js.toString());
                    }
                }
            });
        }
    }

    private void startFavActivity(Context currentContext, Intent intent) {
        IMLogger.d("startFavActivity start");
        getCurrentActivity().startActivity(intent);
    }

    @JavascriptInterface
    public void jsCallNative(String jsParasJson) {
        IMLogger.d("jsCallNative jsParasJson=" + jsParasJson);
        notifyWebViewJS(jsParasJson, 3);
    }

    class MsgHandler extends Handler {
        public MsgHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 21:
                    WebViewManager.this.notifyWebViewClose();
                    return;
                case 22:
                case 25:
                    Bundle data = msg.getData();
                    String parasJson = data != null ? data.getString(WebViewManager.KEY_JSON) : "";
                    if (msg.what == 22) {
                        WebViewManager.this.notifyWebViewJS(parasJson, 3);
                        return;
                    } else {
                        WebViewManager.this.notifyWebViewJS(parasJson, 4);
                        return;
                    }
                case 23:
                    IMLogger.d("WebViewManager server receive client messenger");
                    Messenger unused = WebViewManager.this.mClientMessenger = msg.replyTo;
                    return;
                default:
                    return;
            }
        }
    }

    private void sendMessageToClient(int what, String parasJson) {
        if (this.mClientMessenger == null) {
            IMLogger.e("mClientMessenger is null!");
        }
        Message msg = Message.obtain((Handler) null, what);
        Bundle data = new Bundle();
        if (!T.ckIsEmpty(parasJson)) {
            data.putString(KEY_JSON, parasJson);
        }
        msg.setData(data);
        try {
            this.mClientMessenger.send(msg);
        } catch (RemoteException e) {
            IMLogger.d(e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public boolean getX5WorkFlag(Context context, boolean isX5Set, boolean isX5Work) {
        IMLogger.d("getX5WorkFlag isX5Set = " + isX5Set + ", isX5Work = " + isX5Work);
        return isX5Set ? isX5Work : MetaDataUtil.readMetaDataFromApplication(this.act, X5_CORE, false);
    }
}
