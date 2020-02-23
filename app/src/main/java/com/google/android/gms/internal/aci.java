package com.google.android.gms.internal;

import java.io.IOException;

public final class aci extends adj<aci> {
    public int zzcqq = 0;
    public long zzcqr = 0;
    public int zzcqt = 0;
    public int zzcqu = 0;
    public int zzcqv = 0;
    public int zzcqw = 0;
    public int zzcqx = 0;
    public int zzcqy = 0;

    public aci() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aci)) {
            return false;
        }
        aci aci = (aci) obj;
        if (this.zzcqq != aci.zzcqq) {
            return false;
        }
        if (this.zzcqr != aci.zzcqr) {
            return false;
        }
        if (this.zzcqt != aci.zzcqt) {
            return false;
        }
        if (this.zzcqu != aci.zzcqu) {
            return false;
        }
        if (this.zzcqv != aci.zzcqv) {
            return false;
        }
        if (this.zzcqw != aci.zzcqw) {
            return false;
        }
        if (this.zzcqx != aci.zzcqx) {
            return false;
        }
        if (this.zzcqy != aci.zzcqy) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aci.zzcso == null || aci.zzcso.isEmpty() : this.zzcso.equals(aci.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + this.zzcqt) * 31) + this.zzcqu) * 31) + this.zzcqv) * 31) + this.zzcqw) * 31) + this.zzcqx) * 31) + this.zzcqy) * 31);
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
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
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
                            this.zzcqt = zzLF2;
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 32:
                    int position3 = adg.getPosition();
                    int zzLF3 = adg.zzLF();
                    switch (zzLF3) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqu = zzLF3;
                            break;
                        default:
                            adg.zzcp(position3);
                            zza(adg, zzLA);
                            continue;
                    }
                case 40:
                    int position4 = adg.getPosition();
                    int zzLF4 = adg.zzLF();
                    switch (zzLF4) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqv = zzLF4;
                            break;
                        default:
                            adg.zzcp(position4);
                            zza(adg, zzLA);
                            continue;
                    }
                case 48:
                    int position5 = adg.getPosition();
                    int zzLF5 = adg.zzLF();
                    switch (zzLF5) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqw = zzLF5;
                            break;
                        default:
                            adg.zzcp(position5);
                            zza(adg, zzLA);
                            continue;
                    }
                case 56:
                    int position6 = adg.getPosition();
                    int zzLF6 = adg.zzLF();
                    switch (zzLF6) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqx = zzLF6;
                            break;
                        default:
                            adg.zzcp(position6);
                            zza(adg, zzLA);
                            continue;
                    }
                case 64:
                    int position7 = adg.getPosition();
                    int zzLF7 = adg.zzLF();
                    switch (zzLF7) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcqy = zzLF7;
                            break;
                        default:
                            adg.zzcp(position7);
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
        if (this.zzcqt != 0) {
            adh.zzr(3, this.zzcqt);
        }
        if (this.zzcqu != 0) {
            adh.zzr(4, this.zzcqu);
        }
        if (this.zzcqv != 0) {
            adh.zzr(5, this.zzcqv);
        }
        if (this.zzcqw != 0) {
            adh.zzr(6, this.zzcqw);
        }
        if (this.zzcqx != 0) {
            adh.zzr(7, this.zzcqx);
        }
        if (this.zzcqy != 0) {
            adh.zzr(8, this.zzcqy);
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
        if (this.zzcqt != 0) {
            zzn += adh.zzs(3, this.zzcqt);
        }
        if (this.zzcqu != 0) {
            zzn += adh.zzs(4, this.zzcqu);
        }
        if (this.zzcqv != 0) {
            zzn += adh.zzs(5, this.zzcqv);
        }
        if (this.zzcqw != 0) {
            zzn += adh.zzs(6, this.zzcqw);
        }
        if (this.zzcqx != 0) {
            zzn += adh.zzs(7, this.zzcqx);
        }
        return this.zzcqy != 0 ? zzn + adh.zzs(8, this.zzcqy) : zzn;
    }
}
