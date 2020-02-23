package com.tencent.smtt.sdk;

import android.net.Uri;
import android.webkit.ValueCallback;

class ab implements ValueCallback<Uri[]> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ SystemWebChromeClient b;

    ab(SystemWebChromeClient systemWebChromeClient, ValueCallback valueCallback) {
        this.b = systemWebChromeClient;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(Uri[] uriArr) {
        this.a.onReceiveValue(uriArr);
    }
}
