package com.google.android.gms.internal;

import java.io.IOException;

public final class zzaz extends adj<zzaz> {
    private Long zzbM = null;
    private Long zzbN = null;
    public Long zzcu = null;
    public Long zzcv = null;
    public Long zzcw = null;

    public zzaz() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzbM = Long.valueOf(adg.zzLG());
                    continue;
                case 16:
                    this.zzbN = Long.valueOf(adg.zzLG());
                    continue;
                case 24:
                    this.zzcu = Long.valueOf(adg.zzLG());
                    continue;
                case 32:
                    this.zzcv = Long.valueOf(adg.zzLG());
                    continue;
                case 40:
                    this.zzcw = Long.valueOf(adg.zzLG());
                    continue;
                default:
                    if (!super.zza(adg, zzLA)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public final void zza(adh adh) throws IOException {
        if (this.zzbM != null) {
            adh.zzb(1, this.zzbM.longValue());
        }
        if (this.zzbN != null) {
            adh.zzb(2, this.zzbN.longValue());
        }
        if (this.zzcu != null) {
            adh.zzb(3, this.zzcu.longValue());
        }
        if (this.zzcv != null) {
            adh.zzb(4, this.zzcv.longValue());
        }
        if (this.zzcw != null) {
            adh.zzb(5, this.zzcw.longValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbM != null) {
            zzn += adh.zze(1, this.zzbM.longValue());
        }
        if (this.zzbN != null) {
            zzn += adh.zze(2, this.zzbN.longValue());
        }
        if (this.zzcu != null) {
            zzn += adh.zze(3, this.zzcu.longValue());
        }
        if (this.zzcv != null) {
            zzn += adh.zze(4, this.zzcv.longValue());
        }
        return this.zzcw != null ? zzn + adh.zze(5, this.zzcw.longValue()) : zzn;
    }
}
