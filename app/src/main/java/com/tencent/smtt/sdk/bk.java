package com.tencent.smtt.sdk;

import android.graphics.Bitmap;
import android.webkit.WebIconDatabase;
import com.tencent.smtt.sdk.WebIconDatabase;

class bk implements WebIconDatabase.IconListener {
    final /* synthetic */ WebIconDatabase.a a;
    final /* synthetic */ WebIconDatabase b;

    bk(WebIconDatabase webIconDatabase, WebIconDatabase.a aVar) {
        this.b = webIconDatabase;
        this.a = aVar;
    }

    public void onReceivedIcon(String str, Bitmap bitmap) {
        this.a.a(str, bitmap);
    }
}
