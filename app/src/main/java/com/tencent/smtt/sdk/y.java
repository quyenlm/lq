package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;

class y implements ValueCallback<String[]> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ SystemWebChromeClient b;

    y(SystemWebChromeClient systemWebChromeClient, ValueCallback valueCallback) {
        this.b = systemWebChromeClient;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(String[] strArr) {
        this.a.onReceiveValue(strArr);
    }
}
