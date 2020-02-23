package com.tencent.smtt.sdk;

import android.net.Uri;
import android.webkit.ValueCallback;

class s implements ValueCallback<Uri> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ q b;

    s(q qVar, ValueCallback valueCallback) {
        this.b = qVar;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(Uri uri) {
        this.a.onReceiveValue(new Uri[]{uri});
    }
}
