package com.google.android.gms.common.data;

import java.util.NoSuchElementException;

public final class zzh<T> extends zzb<T> {
    private T zzaFQ;

    public zzh(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zzaFv).toString());
        }
        this.zzaFv++;
        if (this.zzaFv == 0) {
            this.zzaFQ = this.zzaFu.get(0);
            if (!(this.zzaFQ instanceof zzc)) {
                String valueOf = String.valueOf(this.zzaFQ.getClass());
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 44).append("DataBuffer reference of type ").append(valueOf).append(" is not movable").toString());
            }
        } else {
            ((zzc) this.zzaFQ).zzar(this.zzaFv);
        }
        return this.zzaFQ;
    }
}
