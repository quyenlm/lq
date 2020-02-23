package com.google.android.gms.internal;

public final class tw extends tx {
    private final pz zzcfF;

    public tw(tz tzVar, qr qrVar, pz pzVar) {
        super(ty.Merge, tzVar, qrVar);
        this.zzcfF = pzVar;
    }

    public final String toString() {
        return String.format("Merge { path=%s, source=%s, children=%s }", new Object[]{this.zzbZf, this.zzcfH, this.zzcfF});
    }

    public final pz zzHD() {
        return this.zzcfF;
    }

    public final tx zzc(wp wpVar) {
        if (this.zzbZf.isEmpty()) {
            pz zzg = this.zzcfF.zzg(new qr(wpVar));
            if (zzg.isEmpty()) {
                return null;
            }
            return zzg.zzGJ() != null ? new ub(this.zzcfH, qr.zzGZ(), zzg.zzGJ()) : new tw(this.zzcfH, qr.zzGZ(), zzg);
        } else if (this.zzbZf.zzHc().equals(wpVar)) {
            return new tw(this.zzcfH, this.zzbZf.zzHd(), this.zzcfF);
        } else {
            return null;
        }
    }
}
