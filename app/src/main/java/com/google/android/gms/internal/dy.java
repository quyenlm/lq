package com.google.android.gms.internal;

import java.util.Iterator;

final class dy implements Iterator<dp<?>> {
    private /* synthetic */ Iterator zzbLA;
    private /* synthetic */ Iterator zzbLB;

    dy(dw dwVar, Iterator it, Iterator it2) {
        this.zzbLA = it;
        this.zzbLB = it2;
    }

    public final boolean hasNext() {
        return this.zzbLA.hasNext() || this.zzbLB.hasNext();
    }

    public final /* synthetic */ Object next() {
        return this.zzbLA.hasNext() ? (dp) this.zzbLA.next() : (dp) this.zzbLB.next();
    }

    public final void remove() {
        throw new UnsupportedOperationException();
    }
}
