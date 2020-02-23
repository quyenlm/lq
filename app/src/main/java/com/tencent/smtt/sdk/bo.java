package com.tencent.smtt.sdk;

import android.graphics.Picture;
import android.webkit.WebView;
import com.tencent.smtt.sdk.WebView;

class bo implements WebView.PictureListener {
    final /* synthetic */ WebView.PictureListener a;
    final /* synthetic */ WebView b;

    bo(WebView webView, WebView.PictureListener pictureListener) {
        this.b = webView;
        this.a = pictureListener;
    }

    public void onNewPicture(android.webkit.WebView webView, Picture picture) {
        this.b.a(webView);
        this.a.onNewPicture(this.b, picture);
    }
}
