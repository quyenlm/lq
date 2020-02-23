package com.tencent.smtt.sdk;

import com.tencent.smtt.export.external.interfaces.WebResourceError;

class af extends WebResourceError {
    final /* synthetic */ android.webkit.WebResourceError a;
    final /* synthetic */ ad b;

    af(ad adVar, android.webkit.WebResourceError webResourceError) {
        this.b = adVar;
        this.a = webResourceError;
    }

    public CharSequence getDescription() {
        return this.a.getDescription();
    }

    public int getErrorCode() {
        return this.a.getErrorCode();
    }
}
