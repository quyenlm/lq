package com.google.android.gms.internal;

public final class no<K, V> extends nu<K, V> {
    no(K k, V v, nq<K, V> nqVar, nq<K, V> nqVar2) {
        super(k, v, nqVar, nqVar2);
    }

    /* access modifiers changed from: protected */
    public final int zzFD() {
        return nr.zzbZZ;
    }

    public final boolean zzFE() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final nu<K, V> zza(K k, V v, nq<K, V> nqVar, nq<K, V> nqVar2) {
        if (k == null) {
            k = getKey();
        }
        if (v == null) {
            v = getValue();
        }
        if (nqVar == null) {
            nqVar = zzFG();
        }
        if (nqVar2 == null) {
            nqVar2 = zzFH();
        }
        return new no(k, v, nqVar, nqVar2);
    }
}
