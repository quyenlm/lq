package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import java.lang.reflect.InvocationTargetException;

public final class zzds extends zzec {
    private long zzrq = -1;

    public zzds(zzdb zzdb, String str, String str2, zzax zzax, int i, int i2) {
        super(zzdb, str, str2, zzax, i, 12);
    }

    /* access modifiers changed from: protected */
    public final void zzT() throws IllegalAccessException, InvocationTargetException {
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzER)).booleanValue()) {
            this.zzro.zzbi = -1L;
            if (this.zzrq == -1) {
                this.zzrq = ((Long) this.zzrx.invoke((Object) null, new Object[]{this.zzpJ.getContext()})).longValue();
            }
            synchronized (this.zzro) {
                this.zzro.zzbi = Long.valueOf(this.zzrq);
            }
            return;
        }
        this.zzro.zzbi = -1L;
        this.zzro.zzbi = (Long) this.zzrx.invoke((Object) null, new Object[]{this.zzpJ.getContext()});
    }
}
