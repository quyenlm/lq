package com.google.android.gms.internal;

import java.io.IOException;

public final class aec extends adj<aec> {
    public String zzctu = null;
    public Long zzctv = null;

    public aec() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzctu = adg.readString();
                    continue;
                case 16:
                    this.zzctv = Long.valueOf(adg.zzLB());
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
        if (this.zzctu != null) {
            adh.zzl(1, this.zzctu);
        }
        if (this.zzctv != null) {
            adh.zzb(2, this.zzctv.longValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzctu != null) {
            zzn += adh.zzm(1, this.zzctu);
        }
        return this.zzctv != null ? zzn + adh.zze(2, this.zzctv.longValue()) : zzn;
    }
}
