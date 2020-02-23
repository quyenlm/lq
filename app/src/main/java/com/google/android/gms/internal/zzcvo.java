package com.google.android.gms.internal;

final class zzcvo implements zzcwl {
    private /* synthetic */ zzcvn zzbIB;

    zzcvo(zzcvn zzcvn) {
        this.zzbIB = zzcvn;
    }

    public final void zza(zzcuw zzcuw) {
        this.zzbIB.zzp(zzcuw.zzBm());
    }

    public final void zzb(zzcuw zzcuw) {
        this.zzbIB.zzp(zzcuw.zzBm());
        zzcvk.v(new StringBuilder(57).append("Permanent failure dispatching hitId: ").append(zzcuw.zzBm()).toString());
    }

    public final void zzc(zzcuw zzcuw) {
        long zzBn = zzcuw.zzBn();
        if (zzBn == 0) {
            this.zzbIB.zzh(zzcuw.zzBm(), this.zzbIB.zzvw.currentTimeMillis());
        } else if (zzBn + 14400000 < this.zzbIB.zzvw.currentTimeMillis()) {
            this.zzbIB.zzp(zzcuw.zzBm());
            zzcvk.v(new StringBuilder(47).append("Giving up on failed hitId: ").append(zzcuw.zzBm()).toString());
        }
    }
}
