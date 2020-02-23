package com.tencent.mtt.spcialcall.sdk;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tencent.mtt.spcialcall.WebViewClientWrapper;

public class MttWebView extends WebView {
    private WebViewClientWrapper mWebViewClient;

    public MttWebView(Context context) {
        super(context);
        init();
    }

    public MttWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.mWebViewClient = new WebViewClientWrapper();
        super.setWebViewClient(this.mWebViewClient);
        if (Build.VERSION.SDK_INT >= 11) {
            removeJavascriptInterface("searchBoxJavaBridge_");
        }
    }

    public void setWebViewClient(WebViewClient client) {
        if (client != null) {
            this.mWebViewClient.setWebViewClient(client);
        }
    }

    public void setDownloadListener(DownloadListener listener) {
        this.mWebViewClient.setDownloadListener(listener);
        super.setDownloadListener(listener);
    }

    public void setInjectRecommend(boolean inject) {
        this.mWebViewClient.setInjectRecommend(inject);
    }

    public void setFromType(int fromType) {
        this.mWebViewClient.setFromType(fromType);
    }

    public void setUin(String uin) {
        this.mWebViewClient.setUin(uin);
    }

    public void setPublicAccountUin(String pubUin) {
        this.mWebViewClient.setPublicAccountUin(pubUin);
    }
}
