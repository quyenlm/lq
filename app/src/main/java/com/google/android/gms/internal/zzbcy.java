package com.google.android.gms.internal;

abstract class zzbcy {
    private final zzbcw zzaDZ;

    protected zzbcy(zzbcw zzbcw) {
        this.zzaDZ = zzbcw;
    }

    public final void zzc(zzbcx zzbcx) {
        zzbcx.zzaCv.lock();
        try {
            if (zzbcx.zzaDV == this.zzaDZ) {
                zzpV();
                zzbcx.zzaCv.unlock();
            }
        } finally {
            zzbcx.zzaCv.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zzpV();
}
