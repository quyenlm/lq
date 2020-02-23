package com.google.android.gms.internal;

import java.io.IOException;

public final class ade extends adj<ade> {
    public int zzcqq = 0;
    public int zzcrJ = 0;

    public ade() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ade)) {
            return false;
        }
        ade ade = (ade) obj;
        if (this.zzcqq != ade.zzcqq) {
            return false;
        }
        if (this.zzcrJ != ade.zzcrJ) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? ade.zzcso == null || ade.zzcso.isEmpty() : this.zzcso.equals(ade.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + this.zzcrJ) * 31);
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
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            this.zzcrJ = zzLF2;
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
        if (this.zzcrJ != 0) {
            adh.zzr(2, this.zzcrJ);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        return this.zzcrJ != 0 ? zzn + adh.zzs(2, this.zzcrJ) : zzn;
    }
}
