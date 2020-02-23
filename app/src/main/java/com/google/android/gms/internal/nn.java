package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

final class nn<T> implements Iterator<T> {
    private Iterator<Map.Entry<T, Void>> zzbZW;

    public nn(Iterator<Map.Entry<T, Void>> it) {
        this.zzbZW = it;
    }

    public final boolean hasNext() {
        return this.zzbZW.hasNext();
    }

    public final T next() {
        return this.zzbZW.next().getKey();
    }

    public final void remove() {
        this.zzbZW.remove();
    }
}
