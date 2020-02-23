package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Looper;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsContext;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsError;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsValue;
import com.tencent.smtt.export.external.jscore.interfaces.IX5JsVirtualMachine;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;

public final class JsVirtualMachine {
    private final Context a;
    private final IX5JsVirtualMachine b;
    private final HashSet<WeakReference<a>> c;

    private static class a implements IX5JsContext {
        private WebView a;

        public a(Context context) {
            this.a = new WebView(context);
            this.a.getSettings().setJavaScriptEnabled(true);
        }

        public void a() {
            if (this.a != null) {
                this.a.onPause();
            }
        }

        public void addJavascriptInterface(Object obj, String str) {
            if (this.a != null) {
                this.a.addJavascriptInterface(obj, str);
                this.a.loadUrl("about:blank");
            }
        }

        public void b() {
            if (this.a != null) {
                this.a.onResume();
            }
        }

        public void destroy() {
            if (this.a != null) {
                this.a.clearHistory();
                this.a.clearCache(true);
                this.a.loadUrl("about:blank");
                this.a.freeMemory();
                this.a.pauseTimers();
                this.a.destroy();
                this.a = null;
            }
        }

        public void evaluateJavascript(String str, ValueCallback<String> valueCallback, URL url) {
            if (this.a != null) {
                this.a.evaluateJavascript(str, valueCallback == null ? null : new e(this, valueCallback));
            }
        }

        public IX5JsValue evaluateScript(String str, URL url) {
            if (this.a != null) {
                this.a.evaluateJavascript(str, (ValueCallback<String>) null);
            }
            return null;
        }

        public void evaluateScriptAsync(String str, ValueCallback<IX5JsValue> valueCallback, URL url) {
            if (this.a != null) {
                this.a.evaluateJavascript(str, valueCallback == null ? null : new f(this, valueCallback));
            }
        }

        public void removeJavascriptInterface(String str) {
            if (this.a != null) {
                this.a.removeJavascriptInterface(str);
            }
        }

        public void setExceptionHandler(ValueCallback<IX5JsError> valueCallback) {
        }

        public void setName(String str) {
        }

        public void setPerContextData(Object obj) {
        }

        public void stealValueFromOtherCtx(String str, IX5JsContext iX5JsContext, String str2) {
        }
    }

    public JsVirtualMachine(Context context) {
        this(context, (Looper) null);
    }

    public JsVirtualMachine(Context context, Looper looper) {
        this.c = new HashSet<>();
        this.a = context;
        this.b = X5JsCore.a(context, looper);
    }

    /* access modifiers changed from: protected */
    public IX5JsContext a() {
        if (this.b != null) {
            return this.b.createJsContext();
        }
        a aVar = new a(this.a);
        this.c.add(new WeakReference(aVar));
        return aVar;
    }

    public void destroy() {
        if (this.b != null) {
            this.b.destroy();
            return;
        }
        Iterator<WeakReference<a>> it = this.c.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            if (next.get() != null) {
                ((a) next.get()).destroy();
            }
        }
    }

    public Looper getLooper() {
        return this.b != null ? this.b.getLooper() : Looper.myLooper();
    }

    public boolean isFallback() {
        return this.b == null;
    }

    public void onPause() {
        if (this.b != null) {
            this.b.onPause();
            return;
        }
        Iterator<WeakReference<a>> it = this.c.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            if (next.get() != null) {
                ((a) next.get()).a();
            }
        }
    }

    public void onResume() {
        if (this.b != null) {
            this.b.onResume();
            return;
        }
        Iterator<WeakReference<a>> it = this.c.iterator();
        while (it.hasNext()) {
            WeakReference next = it.next();
            if (next.get() != null) {
                ((a) next.get()).b();
            }
        }
    }
}
