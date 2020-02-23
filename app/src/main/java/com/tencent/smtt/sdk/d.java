package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;

class d implements ValueCallback<IX5JsError> {
    final /* synthetic */ JsContext a;

    d(JsContext jsContext) {
        this.a = jsContext;
    }

    /* renamed from: a */
    public void onReceiveValue(IX5JsError iX5JsError) {
        this.a.c.handleException(this.a, new JsError(iX5JsError));
    }
}
