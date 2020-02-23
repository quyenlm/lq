package com.tencent.smtt.sdk;

import android.content.Context;

public class WebViewDatabase {
    private static WebViewDatabase a;
    private Context b;

    protected WebViewDatabase(Context context) {
        this.b = context;
    }

    private static synchronized WebViewDatabase a(Context context) {
        WebViewDatabase webViewDatabase;
        synchronized (WebViewDatabase.class) {
            if (a == null) {
                a = new WebViewDatabase(context);
            }
            webViewDatabase = a;
        }
        return webViewDatabase;
    }

    public static WebViewDatabase getInstance(Context context) {
        return a(context);
    }

    public void clearFormData() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebViewDatabase.getInstance(this.b).clearFormData();
        } else {
            a2.c().g(this.b);
        }
    }

    public void clearHttpAuthUsernamePassword() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebViewDatabase.getInstance(this.b).clearHttpAuthUsernamePassword();
        } else {
            a2.c().e(this.b);
        }
    }

    @Deprecated
    public void clearUsernamePassword() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.WebViewDatabase.getInstance(this.b).clearUsernamePassword();
        } else {
            a2.c().c(this.b);
        }
    }

    public boolean hasFormData() {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.WebViewDatabase.getInstance(this.b).hasFormData() : a2.c().f(this.b);
    }

    public boolean hasHttpAuthUsernamePassword() {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.WebViewDatabase.getInstance(this.b).hasHttpAuthUsernamePassword() : a2.c().d(this.b);
    }

    @Deprecated
    public boolean hasUsernamePassword() {
        br a2 = br.a();
        return (a2 == null || !a2.b()) ? android.webkit.WebViewDatabase.getInstance(this.b).hasUsernamePassword() : a2.c().b(this.b);
    }
}
