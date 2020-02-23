package com.tencent.smtt.sdk;

import android.net.Uri;
import android.webkit.ValueCallback;

class aa implements ValueCallback<Uri> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ SystemWebChromeClient b;

    aa(SystemWebChromeClient systemWebChromeClient, ValueCallback valueCallback) {
        this.b = systemWebChromeClient;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(Uri uri) {
        this.a.onReceiveValue(uri);
    }
}
