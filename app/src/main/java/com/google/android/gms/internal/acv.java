package com.google.android.gms.internal;

import java.io.IOException;

public final class acv extends adj<acv> {
    private int zzcqq = 0;
    private long zzcqr = 0;
    private int zzcru = 0;

    public acv() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acv)) {
            return false;
        }
        acv acv = (acv) obj;
        if (this.zzcqq != acv.zzcqq) {
            return false;
        }
        if (this.zzcqr != acv.zzcqr) {
            return false;
        }
        if (this.zzcru != acv.zzcru) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acv.zzcso == null || acv.zzcso.isEmpty() : this.zzcso.equals(acv.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + this.zzcru) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 16:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 24:
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcru = zzLF2;
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            adh.zzb(2, this.zzcqr);
        }
        if (this.zzcru != 0) {
            adh.zzr(3, this.zzcru);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(2, this.zzcqr);
        }
        return this.zzcru != 0 ? zzn + adh.zzs(3, this.zzcru) : zzn;
    }
}
