package com.google.android.gms.internal;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;

public final class nl<K, V> implements Iterator<Map.Entry<K, V>> {
    private final Stack<nu<K, V>> zzbZT = new Stack<>();
    private final boolean zzbZU;

    nl(nq<K, V> nqVar, K k, Comparator<K> comparator, boolean z) {
        this.zzbZU = z;
        nq<K, V> nqVar2 = nqVar;
        while (!nqVar2.isEmpty()) {
            this.zzbZT.push((nu) nqVar2);
            nqVar2 = z ? nqVar2.zzFH() : nqVar2.zzFG();
        }
    }

    /* access modifiers changed from: private */
    public final Map.Entry<K, V> next() {
        try {
            nu pop = this.zzbZT.pop();
            AbstractMap.SimpleEntry simpleEntry = new AbstractMap.SimpleEntry(pop.getKey(), pop.getValue());
            if (this.zzbZU) {
                for (nq zzFG = pop.zzFG(); !zzFG.isEmpty(); zzFG = zzFG.zzFH()) {
                    this.zzbZT.push((nu) zzFG);
                }
            } else {
                for (nq zzFH = pop.zzFH(); !zzFH.isEmpty(); zzFH = zzFH.zzFG()) {
                    this.zzbZT.push((nu) zzFH);
                }
            }
            return simpleEntry;
        } catch (EmptyStackException e) {
            throw new NoSuchElementException();
        }
    }

    public final boolean hasNext() {
        return this.zzbZT.size() > 0;
    }

    public final void remove() {
        throw new UnsupportedOperationException("remove called on immutable collection");
    }
}
