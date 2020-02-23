package com.google.android.gms.internal;

public final class nt<K, V> extends nu<K, V> {
    nt(K k, V v) {
        super(k, v, np.zzFF(), np.zzFF());
    }

    nt(K k, V v, nq<K, V> nqVar, nq<K, V> nqVar2) {
        super(k, v, nqVar, nqVar2);
    }

    /* access modifiers changed from: protected */
    public final int zzFD() {
        return nr.zzbZY;
    }

    public final boolean zzFE() {
        return true;
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
        return new nt(k, v, nqVar, nqVar2);
    }
}
