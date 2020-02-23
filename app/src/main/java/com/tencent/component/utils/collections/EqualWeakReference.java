package com.tencent.component.utils.collections;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class EqualWeakReference<T> extends WeakReference<T> {
    public EqualWeakReference(T r, ReferenceQueue<? super T> q) {
        super(r, q);
    }

    public EqualWeakReference(T r) {
        super(r);
    }

    public boolean equals(Object o) {
        if (o != null) {
            T b = ((EqualWeakReference) o).get();
            T a = get();
            if (a != null) {
                return a.equals(b);
            }
            if (b == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        T a = get();
        if (a != null) {
            return a.hashCode();
        }
        return 0;
    }
}
