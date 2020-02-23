package com.google.android.gms.internal;

import java.io.IOException;

public final class zzau extends adj<zzau> {
    public zzav zzaQ = null;
    public zzaw zzaR = null;

    public zzau() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    if (this.zzaQ == null) {
                        this.zzaQ = new zzav();
                    }
                    adg.zza(this.zzaQ);
                    continue;
                case 18:
                    if (this.zzaR == null) {
                        this.zzaR = new zzaw();
                    }
                    adg.zza(this.zzaR);
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
        if (this.zzaQ != null) {
            adh.zza(1, (adp) this.zzaQ);
        }
        if (this.zzaR != null) {
            adh.zza(2, (adp) this.zzaR);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzaQ != null) {
            zzn += adh.zzb(1, (adp) this.zzaQ);
        }
        return this.zzaR != null ? zzn + adh.zzb(2, (adp) this.zzaR) : zzn;
    }
}
