package com.google.android.gms.internal;

import java.util.concurrent.ThreadFactory;

final class qo extends yu {
    private /* synthetic */ wl zzbZz;

    qo(ql qlVar, wl wlVar) {
        this.zzbZz = wlVar;
    }

    /* access modifiers changed from: protected */
    public final ThreadFactory getThreadFactory() {
        return ql.zzcdb;
    }

    /* access modifiers changed from: protected */
    public final ti zzGY() {
        return ql.zzcdc;
    }

    public final void zzf(Throwable th) {
        this.zzbZz.zzd(yu.zzh(th), th);
    }
}
