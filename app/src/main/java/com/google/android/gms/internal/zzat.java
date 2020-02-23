package com.google.android.gms.internal;

import java.io.IOException;

public final class zzat extends adj<zzat> {
    private String stackTrace = null;
    public String zzaH = null;
    public Long zzaI = null;
    private String zzaJ = null;
    private String zzaK = null;
    private Long zzaL = null;
    private Long zzaM = null;
    private String zzaN = null;
    private Long zzaO = null;
    private String zzaP = null;

    public zzat() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzaH = adg.readString();
                    continue;
                case 16:
                    this.zzaI = Long.valueOf(adg.zzLG());
                    continue;
                case 26:
                    this.stackTrace = adg.readString();
                    continue;
                case 34:
                    this.zzaJ = adg.readString();
                    continue;
                case 42:
                    this.zzaK = adg.readString();
                    continue;
                case 48:
                    this.zzaL = Long.valueOf(adg.zzLG());
                    continue;
                case 56:
                    this.zzaM = Long.valueOf(adg.zzLG());
                    continue;
                case 66:
                    this.zzaN = adg.readString();
                    continue;
                case 72:
                    this.zzaO = Long.valueOf(adg.zzLG());
                    continue;
                case 82:
                    this.zzaP = adg.readString();
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
        if (this.zzaH != null) {
            adh.zzl(1, this.zzaH);
        }
        if (this.zzaI != null) {
            adh.zzb(2, this.zzaI.longValue());
        }
        if (this.stackTrace != null) {
            adh.zzl(3, this.stackTrace);
        }
        if (this.zzaJ != null) {
            adh.zzl(4, this.zzaJ);
        }
        if (this.zzaK != null) {
            adh.zzl(5, this.zzaK);
        }
        if (this.zzaL != null) {
            adh.zzb(6, this.zzaL.longValue());
        }
        if (this.zzaM != null) {
            adh.zzb(7, this.zzaM.longValue());
        }
        if (this.zzaN != null) {
            adh.zzl(8, this.zzaN);
        }
        if (this.zzaO != null) {
            adh.zzb(9, this.zzaO.longValue());
        }
        if (this.zzaP != null) {
            adh.zzl(10, this.zzaP);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzaH != null) {
            zzn += adh.zzm(1, this.zzaH);
        }
        if (this.zzaI != null) {
            zzn += adh.zze(2, this.zzaI.longValue());
        }
        if (this.stackTrace != null) {
            zzn += adh.zzm(3, this.stackTrace);
        }
        if (this.zzaJ != null) {
            zzn += adh.zzm(4, this.zzaJ);
        }
        if (this.zzaK != null) {
            zzn += adh.zzm(5, this.zzaK);
        }
        if (this.zzaL != null) {
            zzn += adh.zze(6, this.zzaL.longValue());
        }
        if (this.zzaM != null) {
            zzn += adh.zze(7, this.zzaM.longValue());
        }
        if (this.zzaN != null) {
            zzn += adh.zzm(8, this.zzaN);
        }
        if (this.zzaO != null) {
            zzn += adh.zze(9, this.zzaO.longValue());
        }
        return this.zzaP != null ? zzn + adh.zzm(10, this.zzaP) : zzn;
    }
}
