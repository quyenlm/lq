package com.tencent.mtt.spcialcall;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ZoomButtonsController;
import com.tencent.component.debug.FileTracerConfig;
import com.tencent.mtt.common.utils.UrlUtility;
import com.tencent.mtt.engine.AppEngine;
import com.tencent.mtt.engine.IWebView;
import com.tencent.mtt.spcialcall.js.SdkJsBridge;
import com.tencent.mtt.spcialcall.sdk.MttApi;
import com.tencent.mtt.spcialcall.sdk.RecommendParams;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.json.JSONArray;
import org.json.JSONException;

public class WebViewSp extends WebViewSpBase implements IWebView, DownloadListener {
    protected static final WebSettings.TextSize[] FONT_SIZES = {WebSettings.TextSize.SMALLER, WebSettings.TextSize.NORMAL, WebSettings.TextSize.LARGER, WebSettings.TextSize.LARGEST};
    private static final String UA_PREFIX = "MTT_SDK/5.1/";
    long mId;
    String mJs;
    SdkJsBridge mJsBridge;
    RecommendParams mRecomParams;
    String mUrl;
    /* access modifiers changed from: private */
    public WebView mWebViewInternal;

    public WebViewSp(Context context, IWebViewClientSp pfc) {
        super(context, pfc);
    }

    public void initDownload() {
        this.mWebViewInternal.setDownloadListener(this);
    }

    public void initWebView() {
        if (this.mWebViewInternal == null) {
            this.mWebViewInternal = new WebView(AppEngine.getInstance().getContext());
        }
        if (Build.VERSION.SDK_INT >= 11) {
            this.mWebViewInternal.removeJavascriptInterface("searchBoxJavaBridge_");
        }
        this.mWebViewInternal.setFocusableInTouchMode(true);
        addView(this.mWebViewInternal, new FrameLayout.LayoutParams(-1, -1));
        initRecomParams();
    }

    public void setWebViewId(long id) {
        this.mId = id;
    }

    public void initRecomParams() {
        this.mRecomParams = new RecommendParams();
        this.mRecomParams.put("from", new StringBuilder(String.valueOf(MttApi.getFromType())).toString());
        this.mRecomParams.put(RecommendParams.KEY_UIN, MttApi.getStringExtra(RecommendParams.KEY_UIN));
    }

    public void initJs(String js) {
        if (TextUtils.isEmpty(js)) {
            this.mJs = null;
            return;
        }
        this.mJs = "javascript:" + "var mttsp_exec=function(service, action, args){var argsJson=args?JSON.stringify(args):'';return prompt(argsJson, 'mttsp:['+[service, action]+']');};" + js;
    }

    public SdkJsBridge getJsBridge() {
        if (this.mJsBridge == null) {
            this.mJsBridge = new SdkJsBridge(this);
        }
        return this.mJsBridge;
    }

    /* access modifiers changed from: protected */
    public void initWebViewClient() {
        this.mWebViewInternal.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (WebViewSp.this.mWebViewClient.shouldOverrideUrlLoading(WebViewSp.this, url)) {
                    return true;
                }
                if (UrlUtility.urlSupportedByX5CoreSp(url)) {
                    WebViewSp.this.mRecomParams.put(RecommendParams.KEY_REFFER, view.getUrl());
                    return false;
                }
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(url));
                    AppEngine.getInstance().getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return true;
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                WebViewSp.this.mUrl = url;
                if (WebViewSp.this.mJs != null) {
                    view.loadUrl(WebViewSp.this.mJs);
                }
                super.onPageStarted(view, url, favicon);
                WebViewSp.this.mWebViewClient.onPageStarted(WebViewSp.this, url, (Bitmap) null);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (WebViewSp.this.mJs != null) {
                    view.loadUrl(WebViewSp.this.mJs);
                }
                MttApi.insertRecommend(view, WebViewSp.this.mRecomParams);
                WebViewSp.this.mWebViewClient.onPageFinished(WebViewSp.this, url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                WebViewSp.this.mWebViewClient.onReceivedError(WebViewSp.this, errorCode, description, failingUrl);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initWebChromClient() {
        this.mWebViewInternal.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (WebViewSp.this.mRecomParams != null) {
                    WebViewSp.this.mRecomParams.put("title", title);
                }
                WebViewSp.this.getWebViewClient().onReceivedTitle(WebViewSp.this, title);
            }

            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                if (defaultValue != null) {
                    if (defaultValue.startsWith("mttsp:")) {
                        String ret = "";
                        try {
                            JSONArray array = new JSONArray(defaultValue.substring(6));
                            ret = WebViewProxyManager.getInstance().onJsCall(WebViewSp.this.mId, array.getString(0), array.getString(1), message);
                        } catch (JSONException e) {
                        }
                        result.confirm(ret);
                        return true;
                    } else if (defaultValue.startsWith("mtt_sdk:")) {
                        String ret2 = "";
                        try {
                            JSONArray array2 = new JSONArray(defaultValue.substring(8));
                            String callbackId = array2.optString(2);
                            ret2 = WebViewSp.this.getJsBridge().nativeExec(array2.getString(0), array2.getString(1), callbackId, message);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        result.confirm(ret2);
                        return true;
                    }
                }
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(AppEngine.getInstance().getContext()).setTitle("Alert").setMessage(message).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                }).show();
                return true;
            }

            public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
                ((SpecialCallActivity) WebViewSp.this.getContext()).showCustomView(view, callback);
            }

            @TargetApi(7)
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg);
            }

            @TargetApi(7)
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg);
            }

            @TargetApi(7)
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                SpecialCallActivity specialCallActivity = (SpecialCallActivity) WebViewSp.this.getContext();
                specialCallActivity.uploadFile = uploadMsg;
                Intent i = new Intent("android.intent.action.GET_CONTENT");
                i.addCategory("android.intent.category.OPENABLE");
                i.setType("*/*");
                specialCallActivity.startActivityForResult(Intent.createChooser(i, "请选择要上传的文件"), 1014);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initWebSetting() {
        setCookies();
        WebSettings webSetting = this.mWebViewInternal.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setSavePassword(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(false);
        webSetting.setSupportMultipleWindows(false);
        if (Build.VERSION.SDK_INT >= 11) {
            webSetting.setDisplayZoomControls(false);
        } else {
            setZoomControlGone(this.mWebViewInternal);
        }
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setDatabasePath(String.valueOf(AppEngine.getInstance().getContext().getFilesDir().getPath()) + "/app_database");
        webSetting.setGeolocationDatabasePath(String.valueOf(AppEngine.getInstance().getContext().getFilesDir().getPath()) + "/app_geolocationdatabase");
        webSetting.setAppCacheEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAppCacheMaxSize(Long.valueOf(FileTracerConfig.FOREVER).longValue());
        webSetting.setAppCachePath(String.valueOf(AppEngine.getInstance().getContext().getFilesDir().getPath()) + "/app_cache");
        Bundle extraParams = ExtraInfo.getExtraParams();
        StringBuilder ua = new StringBuilder();
        ua.append(UA_PREFIX);
        ua.append(webSetting.getUserAgentString());
        if (extraParams != null && extraParams.containsKey("ua")) {
            ua.append('/').append(extraParams.getString("ua"));
        }
        webSetting.setUserAgentString(ua.toString());
    }

    public void setZoomControlGone(View view) {
        try {
            Field field = WebView.class.getDeclaredField("mZoomButtonsController");
            field.setAccessible(true);
            ZoomButtonsController mZoomButtonsController = new ZoomButtonsController(view) {
                public void setVisible(boolean visible) {
                }
            };
            mZoomButtonsController.getZoomControls().setVisibility(8);
            try {
                field.set(view, mZoomButtonsController);
            } catch (IllegalAccessException | IllegalArgumentException e) {
            }
        } catch (NoSuchFieldException | SecurityException e2) {
        }
    }

    public boolean canGoBack() {
        return this.mWebViewInternal.canGoBack();
    }

    public boolean canGoForward() {
        return this.mWebViewInternal.canGoForward();
    }

    public void back(boolean isKeyBack) {
        this.mWebViewInternal.goBack();
    }

    public void forward() {
        this.mWebViewInternal.goForward();
    }

    public String getTitle() {
        return this.mWebViewInternal.getTitle();
    }

    public String getUrl() {
        return this.mUrl;
    }

    public void loadUrl(final String url) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            post(new Runnable() {
                public void run() {
                    WebViewSp.this.mWebViewInternal.loadUrl(url);
                }
            });
        } else {
            this.mWebViewInternal.loadUrl(url);
        }
    }

    public void postUrl(String url, byte[] postData) {
        this.mWebViewInternal.postUrl(url, postData);
    }

    public void reload() {
        this.mWebViewInternal.reload();
    }

    public void stopLoading() {
        this.mWebViewInternal.stopLoading();
    }

    public void deactive() {
        super.deactive();
        callHiddenWebViewMethod(this.mWebViewInternal, "onPause");
    }

    public void active() {
        super.active();
        callHiddenWebViewMethod(this.mWebViewInternal, "onResume");
    }

    public void destroy() {
        super.destroy();
        this.mWebViewInternal.destroy();
    }

    private void callHiddenWebViewMethod(WebView webview, String name) {
        if (webview != null) {
            try {
                WebView.class.getMethod(name, new Class[0]).invoke(webview, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            }
        }
    }

    public Picture snapshotWholePage(int recommendedWidth, int recommendedHeight, IWebView.RatioRespect respect, int flags) {
        return this.mWebViewInternal.capturePicture();
    }

    public void setCookies() {
        String domain;
        Bundle extraParams = ExtraInfo.getExtraParams();
        if (extraParams != null) {
            String uin = extraParams.getString(RecommendParams.KEY_UIN);
            String skey = extraParams.getString("skey");
            String url = extraParams.getString("url");
            if (!TextUtils.isEmpty(uin) && !TextUtils.isEmpty(skey)) {
                CookieSyncManager.createInstance(getContext());
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.setAcceptCookie(true);
                String host = Uri.parse(url).getHost();
                String[] arr = host.split("\\.");
                if (arr.length >= 2) {
                    domain = String.valueOf(arr[arr.length - 2]) + "." + arr[arr.length - 1];
                } else {
                    domain = host;
                }
                if (uin.length() < 10) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = uin.length(); i < 10; i++) {
                        sb.append("0");
                    }
                    uin = sb.append(uin).toString();
                }
                cookieManager.setCookie(url, String.format("uin=o%1$s; domain=%2$s; path=/", new Object[]{uin, domain}));
                cookieManager.setCookie(url, String.format("skey=%1$s; domain=%2$s; path=/", new Object[]{skey, domain}));
                CookieSyncManager.getInstance().sync();
            }
        }
    }

    public void configFontSize(int fontSize) {
        this.mWebViewInternal.getSettings().setTextSize(FONT_SIZES[fontSize]);
    }

    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        this.mWebViewClient.onDownload(url, userAgent, contentDisposition, mimetype, contentLength, this.mWebViewInternal.getUrl());
    }
}
