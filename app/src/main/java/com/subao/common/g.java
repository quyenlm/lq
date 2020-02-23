package com.subao.common;

import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: SuBaoObservable */
public abstract class g<T> {
    private final List<T> a = new ArrayList();

    public boolean a(T t) {
        if (t != null) {
            synchronized (this.a) {
                if (!this.a.contains(t)) {
                    boolean add = this.a.add(t);
                    return add;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final List<T> a() {
        synchronized (this.a) {
            if (this.a.isEmpty()) {
                return null;
            }
            ArrayList arrayList = new ArrayList(this.a);
            return arrayList;
        }
    }
}
