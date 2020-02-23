package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;

class c implements ValueCallback<IX5JsValue> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ JsContext b;

    c(JsContext jsContext, ValueCallback valueCallback) {
        this.b = jsContext;
        this.a = valueCallback;
    }

    /* renamed from: a */
    public void onReceiveValue(IX5JsValue iX5JsValue) {
        this.a.onReceiveValue(iX5JsValue == null ? null : new JsValue(this.b, iX5JsValue));
    }
}
