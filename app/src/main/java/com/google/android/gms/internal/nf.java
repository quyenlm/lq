package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class nf<K, V> extends nh<K, V> {
    /* access modifiers changed from: private */
    public final K[] zzbZL;
    /* access modifiers changed from: private */
    public final V[] zzbZM;
    private final Comparator<K> zzbZN;

    public nf(Comparator<K> comparator) {
        this.zzbZL = new Object[0];
        this.zzbZM = new Object[0];
        this.zzbZN = comparator;
    }

    private nf(Comparator<K> comparator, K[] kArr, V[] vArr) {
        this.zzbZL = kArr;
        this.zzbZM = vArr;
        this.zzbZN = comparator;
    }

    private final int zzZ(K k) {
        int i = 0;
        while (i < this.zzbZL.length && this.zzbZN.compare(this.zzbZL[i], k) < 0) {
            i++;
        }
        return i;
    }

    public static <A, B, C> nf<A, C> zza(List<A> list, Map<B, C> map, nk<A, B> nkVar, Comparator<A> comparator) {
        Collections.sort(list, comparator);
        int size = list.size();
        Object[] objArr = new Object[size];
        Object[] objArr2 = new Object[size];
        int i = 0;
        for (A next : list) {
            objArr[i] = next;
            objArr2[i] = map.get(nkVar.zzab(next));
            i++;
        }
        return new nf<>(comparator, objArr, objArr2);
    }

    private static <T> T[] zza(T[] tArr, int i) {
        int length = tArr.length - 1;
        T[] tArr2 = new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, i);
        System.arraycopy(tArr, i + 1, tArr2, i, length - i);
        return tArr2;
    }

    private static <T> T[] zza(T[] tArr, int i, T t) {
        int length = tArr.length + 1;
        T[] tArr2 = new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, i);
        tArr2[i] = t;
        System.arraycopy(tArr, i, tArr2, i + 1, (length - i) - 1);
        return tArr2;
    }

    private final int zzaa(K k) {
        int i = 0;
        for (K compare : this.zzbZL) {
            if (this.zzbZN.compare(k, compare) == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private static <T> T[] zzb(T[] tArr, int i, T t) {
        int length = tArr.length;
        T[] tArr2 = new Object[length];
        System.arraycopy(tArr, 0, tArr2, 0, length);
        tArr2[i] = t;
        return tArr2;
    }

    private final Iterator<Map.Entry<K, V>> zzi(int i, boolean z) {
        return new ng(this, i, z);
    }

    public final boolean containsKey(K k) {
        return zzaa(k) != -1;
    }

    public final V get(K k) {
        int zzaa = zzaa(k);
        if (zzaa != -1) {
            return this.zzbZM[zzaa];
        }
        return null;
    }

    public final Comparator<K> getComparator() {
        return this.zzbZN;
    }

    public final boolean isEmpty() {
        return this.zzbZL.length == 0;
    }

    public final Iterator<Map.Entry<K, V>> iterator() {
        return zzi(0, false);
    }

    public final int size() {
        return this.zzbZL.length;
    }

    public final K zzFx() {
        if (this.zzbZL.length > 0) {
            return this.zzbZL[0];
        }
        return null;
    }

    public final K zzFy() {
        if (this.zzbZL.length > 0) {
            return this.zzbZL[this.zzbZL.length - 1];
        }
        return null;
    }

    public final Iterator<Map.Entry<K, V>> zzFz() {
        return zzi(this.zzbZL.length - 1, true);
    }

    public final nh<K, V> zzX(K k) {
        int zzaa = zzaa(k);
        if (zzaa == -1) {
            return this;
        }
        return new nf(this.zzbZN, zza(this.zzbZL, zzaa), zza(this.zzbZM, zzaa));
    }

    public final K zzY(K k) {
        int zzaa = zzaa(k);
        if (zzaa == -1) {
            throw new IllegalArgumentException("Can't find predecessor of nonexistent key");
        } else if (zzaa > 0) {
            return this.zzbZL[zzaa - 1];
        } else {
            return null;
        }
    }

    public final void zza(ns<K, V> nsVar) {
        for (int i = 0; i < this.zzbZL.length; i++) {
            nsVar.zzh(this.zzbZL[i], this.zzbZM[i]);
        }
    }

    public final nh<K, V> zzg(K k, V v) {
        int zzaa = zzaa(k);
        if (zzaa != -1) {
            if (this.zzbZL[zzaa] == k && this.zzbZM[zzaa] == v) {
                return this;
            }
            return new nf(this.zzbZN, zzb(this.zzbZL, zzaa, k), zzb(this.zzbZM, zzaa, v));
        } else if (this.zzbZL.length > 25) {
            HashMap hashMap = new HashMap(this.zzbZL.length + 1);
            for (int i = 0; i < this.zzbZL.length; i++) {
                hashMap.put(this.zzbZL[i], this.zzbZM[i]);
            }
            hashMap.put(k, v);
            return nv.zzb(hashMap, this.zzbZN);
        } else {
            int zzZ = zzZ(k);
            return new nf(this.zzbZN, zza(this.zzbZL, zzZ, k), zza(this.zzbZM, zzZ, v));
        }
    }
}
