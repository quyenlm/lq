package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.NoSuchElementException;

final class qs implements Iterator<wp> {
    private int offset = this.zzcdi.start;
    private /* synthetic */ qr zzcdi;

    qs(qr qrVar) {
        this.zzcdi = qrVar;
    }

    public final boolean hasNext() {
        return this.offset < this.zzcdi.end;
    }

    public final /* synthetic */ Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements.");
        }
        wp wpVar = this.zzcdi.zzcdg[this.offset];
        this.offset++;
        return wpVar;
    }

    public final void remove() {
        throw new UnsupportedOperationException("Can't remove component from immutable Path!");
    }
}
