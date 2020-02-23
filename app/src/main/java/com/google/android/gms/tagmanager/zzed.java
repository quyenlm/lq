package com.google.android.gms.tagmanager;

final class zzed implements zzfx {
    private /* synthetic */ zzec zzbFA;

    zzed(zzec zzec) {
        this.zzbFA = zzec;
    }

    public final void zza(zzbx zzbx) {
        this.zzbFA.zzp(zzbx.zzBm());
    }

    public final void zzb(zzbx zzbx) {
        this.zzbFA.zzp(zzbx.zzBm());
        zzdj.v(new StringBuilder(57).append("Permanent failure dispatching hitId: ").append(zzbx.zzBm()).toString());
    }

    public final void zzc(zzbx zzbx) {
        long zzBn = zzbx.zzBn();
        if (zzBn == 0) {
            this.zzbFA.zzh(zzbx.zzBm(), this.zzbFA.zzvw.currentTimeMillis());
        } else if (zzBn + 14400000 < this.zzbFA.zzvw.currentTimeMillis()) {
            this.zzbFA.zzp(zzbx.zzBm());
            zzdj.v(new StringBuilder(47).append("Giving up on failed hitId: ").append(zzbx.zzBm()).toString());
        }
    }
}
