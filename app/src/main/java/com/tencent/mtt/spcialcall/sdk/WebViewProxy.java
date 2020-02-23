package com.tencent.mtt.spcialcall.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.engine.IWebView;
import com.tencent.mtt.spcialcall.JsUtils;
import com.tencent.mtt.spcialcall.WebViewProxyManager;
import com.tencent.mtt.spcialcall.WebViewSpManager;
import java.util.HashMap;
import java.util.Map;

public class WebViewProxy {
    private static final int POST_SUPPORT_BROWSER_VER = 450000;
    private static final int SKEY_SUPPORT_BROWSER_VER = 500000;
    private static final String TAG = "MttApi";
    private Activity activity;
    private long id = -1;
    private boolean isX5 = false;
    private HashMap<String, Object> mJsInterfaces;
    private boolean mStarted = false;
    private WebViewClientProxy mWebViewClient;

    public WebViewProxy(Activity context) {
        this.activity = context;
        this.id = System.currentTimeMillis();
    }

    public long getId() {
        return this.id;
    }

    public void setIsX5(boolean isX52) {
        this.isX5 = isX52;
    }

    public void postUrl(String url, byte[] postData) {
        if (!this.mStarted) {
            openInNewWebView(url, postData);
            this.mStarted = true;
            return;
        }
        openInExistWebView(url);
    }

    private void openInNewWebView(String url, byte[] postData) {
        boolean canUseX5 = true;
        MttApi.intent.putExtra(MttApi.WEBVIEW_ID, this.id);
        if (this.mJsInterfaces != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.mJsInterfaces.entrySet()) {
                sb.append(JsUtils.generateJs(entry.getKey(), entry.getValue()));
            }
            MttApi.intent.putExtra("js", sb.toString());
        }
        if (this.mJsInterfaces != null && !WebViewProxyManager.getInstance().bindServiceIfNeed(this.activity)) {
            Log.d(TAG, "Cannot bind remote call service in x5; will use system webview");
            canUseX5 = false;
        }
        if (!canUseX5 || !isMttSupportPost(postData) || !isSkeySupport() || !MttApi.loadUrlInMbWnd(this.activity, url, postData)) {
            MttApi.loadUrlInLiteMbWnd(this.activity, url, postData);
            this.isX5 = false;
            return;
        }
        this.isX5 = true;
    }

    private boolean isMttSupportPost(byte[] postData) {
        if (postData == null || postData.length == 0 || MttLoader.getBrowserInfo(this.activity).ver >= POST_SUPPORT_BROWSER_VER) {
            return true;
        }
        return false;
    }

    private boolean isSkeySupport() {
        Bundle extras = MttApi.intent.getExtras();
        if (extras == null || TextUtils.isEmpty(extras.getString("skey")) || MttLoader.getBrowserInfo(this.activity).ver >= 500000) {
            return true;
        }
        return false;
    }

    private void openInExistWebView(String url) {
        if (this.isX5) {
            WebViewProxyManager.getInstance().loadUrlRemote(this.id, url);
            return;
        }
        IWebView webview = WebViewSpManager.getWebView(this.id);
        if (webview != null) {
            webview.loadUrl(url);
        }
    }

    public void loadUrl(String url) {
        postUrl(url, (byte[]) null);
    }

    public void addJavascriptInterface(Object object, String name) {
        if (this.mJsInterfaces == null) {
            this.mJsInterfaces = new HashMap<>();
        }
        this.mJsInterfaces.put(name, object);
    }

    public void setWebViewClient(String urlPrefix, WebViewClientProxy client) {
        MttApi.intent.putExtra("notifyUrlPrefix", urlPrefix);
        this.mWebViewClient = client;
    }

    public Object getJavascriptInterface(String name) {
        if (this.mJsInterfaces == null) {
            return null;
        }
        return this.mJsInterfaces.get(name);
    }

    public WebViewClientProxy getWebViewClient() {
        return this.mWebViewClient;
    }

    public void configFontSize(int fontSize) {
        if (!this.isX5) {
            WebViewSpManager.getWebView(this.id).configFontSize(fontSize);
        }
    }
}
