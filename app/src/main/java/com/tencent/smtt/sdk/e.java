package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;
import com.tencent.smtt.sdk.JsVirtualMachine;

class e implements ValueCallback<String> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ JsVirtualMachine.a b;

    e(JsVirtualMachine.a aVar, ValueCallback valueCallback) {
        this.b = aVar;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(String str) {
        this.a.onReceiveValue(str);
    }
}
