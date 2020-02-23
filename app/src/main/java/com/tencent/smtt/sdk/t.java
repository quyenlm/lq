package com.tencent.smtt.sdk;

import android.net.Uri;
import android.webkit.ValueCallback;

class t implements ValueCallback<Uri[]> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ q b;

    t(q qVar, ValueCallback valueCallback) {
        this.b = qVar;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(Uri[] uriArr) {
        this.a.onReceiveValue(uriArr);
    }
}
