package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

final class wv implements Iterator<xl> {
    private final Iterator<Map.Entry<wp, xm>> zzbZW;

    public wv(Iterator<Map.Entry<wp, xm>> it) {
        this.zzbZW = it;
    }

    public final boolean hasNext() {
        return this.zzbZW.hasNext();
    }

    public final /* synthetic */ Object next() {
        Map.Entry next = this.zzbZW.next();
        return new xl((wp) next.getKey(), (xm) next.getValue());
    }

    public final void remove() {
        this.zzbZW.remove();
    }
}
