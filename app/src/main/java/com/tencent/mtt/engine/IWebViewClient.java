package com.tencent.mtt.engine;

import android.graphics.Bitmap;

public interface IWebViewClient {
    void onBackOrForwardChanged(IWebView iWebView);

    void onPageFinished(IWebView iWebView, String str);

    void onPageStarted(IWebView iWebView, String str, Bitmap bitmap);

    void onReceivedError(IWebView iWebView, int i, String str, String str2);

    void onReceivedTitle(IWebView iWebView, String str);

    boolean shouldOverrideUrlLoading(IWebView iWebView, String str);
}
