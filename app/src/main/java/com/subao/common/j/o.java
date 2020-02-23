package com.subao.common.j;

import android.content.Context;

/* compiled from: SignalWatcher */
public abstract class o {
    private final a a;

    /* compiled from: SignalWatcher */
    public interface a {
        void a(int i);
    }

    public abstract void a();

    public abstract void a(Context context);

    protected o(a aVar) {
        if (aVar == null) {
            throw new NullPointerException("Callback can not be null");
        }
        this.a = aVar;
    }

    /* access modifiers changed from: protected */
    public final void a(int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 100) {
            i = 100;
        }
        this.a.a(i);
    }
}
