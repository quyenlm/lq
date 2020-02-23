package com.google.android.gms.internal;

import java.io.IOException;

public final class zzaw extends adj<zzaw> {
    public String zzaT = null;
    private String zzaU = null;
    private String zzaV = null;
    private String zzaW = null;
    private String zzaX = null;

    public zzaw() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzaT = adg.readString();
                    continue;
                case 18:
                    this.zzaU = adg.readString();
                    continue;
                case 26:
                    this.zzaV = adg.readString();
                    continue;
                case 34:
                    this.zzaW = adg.readString();
                    continue;
                case 42:
                    this.zzaX = adg.readString();
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
        if (this.zzaT != null) {
            adh.zzl(1, this.zzaT);
        }
        if (this.zzaU != null) {
            adh.zzl(2, this.zzaU);
        }
        if (this.zzaV != null) {
            adh.zzl(3, this.zzaV);
        }
        if (this.zzaW != null) {
            adh.zzl(4, this.zzaW);
        }
        if (this.zzaX != null) {
            adh.zzl(5, this.zzaX);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzaT != null) {
            zzn += adh.zzm(1, this.zzaT);
        }
        if (this.zzaU != null) {
            zzn += adh.zzm(2, this.zzaU);
        }
        if (this.zzaV != null) {
            zzn += adh.zzm(3, this.zzaV);
        }
        if (this.zzaW != null) {
            zzn += adh.zzm(4, this.zzaW);
        }
        return this.zzaX != null ? zzn + adh.zzm(5, this.zzaX) : zzn;
    }
}
