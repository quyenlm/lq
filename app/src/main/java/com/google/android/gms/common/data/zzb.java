package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzbo;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zzaFu;
    protected int zzaFv = -1;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zzaFu = (DataBuffer) zzbo.zzu(dataBuffer);
    }

    public boolean hasNext() {
        return this.zzaFv < this.zzaFu.getCount() + -1;
    }

    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException(new StringBuilder(46).append("Cannot advance the iterator beyond ").append(this.zzaFv).toString());
        }
        DataBuffer<T> dataBuffer = this.zzaFu;
        int i = this.zzaFv + 1;
        this.zzaFv = i;
        return dataBuffer.get(i);
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
