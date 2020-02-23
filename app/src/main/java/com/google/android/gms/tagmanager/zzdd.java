package com.google.android.gms.tagmanager;

import android.util.LruCache;

final class zzdd extends LruCache<K, V> {
    private /* synthetic */ zzs zzbFn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdd(zzdc zzdc, int i, zzs zzs) {
        super(i);
        this.zzbFn = zzs;
    }

    /* access modifiers changed from: protected */
    public final int sizeOf(K k, V v) {
        return this.zzbFn.sizeOf(k, v);
    }
}
