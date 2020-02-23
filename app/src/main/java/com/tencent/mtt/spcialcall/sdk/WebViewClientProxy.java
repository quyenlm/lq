package com.tencent.mtt.spcialcall.sdk;

public class WebViewClientProxy {
    public void onPageStarted(WebViewProxy view, String url) {
    }

    public void onPageFinished(WebViewProxy view, String url) {
    }

    public void onReceivedError(WebViewProxy view, int errorCode, String description, String failingUrl) {
    }

    public boolean shouldOverrideUrlLoading(WebViewProxy view, String url) {
        return false;
    }
}
