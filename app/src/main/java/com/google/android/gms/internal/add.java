package com.google.android.gms.internal;

import java.io.IOException;

public final class add extends adj<add> {
    public long zzaTb = 0;
    public int zzcqq = 0;
    public String zzcrF = "";
    public long zzcrG = 0;
    private acp zzcrH = null;
    public boolean zzcrI = false;

    public add() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof add)) {
            return false;
        }
        add add = (add) obj;
        if (this.zzcqq != add.zzcqq) {
            return false;
        }
        if (this.zzcrF == null) {
            if (add.zzcrF != null) {
                return false;
            }
        } else if (!this.zzcrF.equals(add.zzcrF)) {
            return false;
        }
        if (this.zzaTb != add.zzaTb) {
            return false;
        }
        if (this.zzcrG != add.zzcrG) {
            return false;
        }
        if (this.zzcrH == null) {
            if (add.zzcrH != null) {
                return false;
            }
        } else if (!this.zzcrH.equals(add.zzcrH)) {
            return false;
        }
        if (this.zzcrI != add.zzcrI) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? add.zzcso == null || add.zzcso.isEmpty() : this.zzcso.equals(add.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcrI ? 1231 : 1237) + (((this.zzcrH == null ? 0 : this.zzcrH.hashCode()) + (((((((this.zzcrF == null ? 0 : this.zzcrF.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31)) * 31) + ((int) (this.zzaTb ^ (this.zzaTb >>> 32)))) * 31) + ((int) (this.zzcrG ^ (this.zzcrG >>> 32)))) * 31)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
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
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    this.zzcrF = adg.readString();
                    continue;
                case 24:
                    this.zzaTb = adg.zzLG();
                    continue;
                case 32:
                    this.zzcrG = adg.zzLG();
                    continue;
                case 42:
                    if (this.zzcrH == null) {
                        this.zzcrH = new acp();
                    }
                    adg.zza(this.zzcrH);
                    continue;
                case 48:
                    this.zzcrI = adg.zzLD();
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.zzcrF != null && !this.zzcrF.equals("")) {
            adh.zzl(2, this.zzcrF);
        }
        if (this.zzaTb != 0) {
            adh.zzb(3, this.zzaTb);
        }
        if (this.zzcrG != 0) {
            adh.zzb(4, this.zzcrG);
        }
        if (this.zzcrH != null) {
            adh.zza(5, (adp) this.zzcrH);
        }
        if (this.zzcrI) {
            adh.zzk(6, this.zzcrI);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcrF != null && !this.zzcrF.equals("")) {
            zzn += adh.zzm(2, this.zzcrF);
        }
        if (this.zzaTb != 0) {
            zzn += adh.zze(3, this.zzaTb);
        }
        if (this.zzcrG != 0) {
            zzn += adh.zze(4, this.zzcrG);
        }
        if (this.zzcrH != null) {
            zzn += adh.zzb(5, (adp) this.zzcrH);
        }
        return this.zzcrI ? zzn + adh.zzct(6) + 1 : zzn;
    }
}
