package com.subao.common.e;

import android.support.annotation.Nullable;
import android.util.SparseArray;

/* compiled from: UniqueKeyContainer */
public class am<T> {
    private int a;
    private final SparseArray<T> b;

    public am() {
        this(4);
    }

    public am(int i) {
        this(i, 0);
    }

    public am(int i, int i2) {
        this.b = new SparseArray<>(i);
        this.a = i2;
    }

    public int a(@Nullable T t) {
        int i;
        synchronized (this.b) {
            i = this.a + 1;
            this.a = i;
            if (t != null) {
                this.b.put(i, t);
            }
        }
        return i;
    }

    @Nullable
    public T a(int i) {
        T t = null;
        synchronized (this.b) {
            int indexOfKey = this.b.indexOfKey(i);
            if (indexOfKey >= 0) {
                t = this.b.valueAt(indexOfKey);
                this.b.removeAt(indexOfKey);
            }
        }
        return t;
    }
}
