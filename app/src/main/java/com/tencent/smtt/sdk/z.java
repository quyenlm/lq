package com.tencent.smtt.sdk;

import android.os.Message;
import android.webkit.WebView;
import com.tencent.smtt.sdk.WebView;

class z implements Runnable {
    final /* synthetic */ WebView.WebViewTransport a;
    final /* synthetic */ Message b;
    final /* synthetic */ SystemWebChromeClient c;

    z(SystemWebChromeClient systemWebChromeClient, WebView.WebViewTransport webViewTransport, Message message) {
        this.c = systemWebChromeClient;
        this.a = webViewTransport;
        this.b = message;
    }

    public void run() {
        WebView webView = this.a.getWebView();
        if (webView != null) {
            ((WebView.WebViewTransport) this.b.obj).setWebView(webView.a());
        }
        this.b.sendToTarget();
    }
}
