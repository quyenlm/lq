package com.google.android.gms.internal;

import java.io.IOException;

public final class hg extends adj<hg> {
    private static volatile hg[] zzbTK;
    public int type = 1;
    public hh zzbTL = null;

    public hg() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static hg[] zzEb() {
        if (zzbTK == null) {
            synchronized (adn.zzcsw) {
                if (zzbTK == null) {
                    zzbTK = new hg[0];
                }
            }
        }
        return zzbTK;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof hg)) {
            return false;
        }
        hg hgVar = (hg) obj;
        if (this.type != hgVar.type) {
            return false;
        }
        if (this.zzbTL == null) {
            if (hgVar.zzbTL != null) {
                return false;
            }
        } else if (!this.zzbTL.equals(hgVar.zzbTL)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? hgVar.zzcso == null || hgVar.zzcso.isEmpty() : this.zzcso.equals(hgVar.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbTL == null ? 0 : this.zzbTL.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31)) * 31;
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
                            this.type = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    if (this.zzbTL == null) {
                        this.zzbTL = new hh();
                    }
                    adg.zza(this.zzbTL);
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
        adh.zzr(1, this.type);
        if (this.zzbTL != null) {
            adh.zza(2, (adp) this.zzbTL);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn() + adh.zzs(1, this.type);
        return this.zzbTL != null ? zzn + adh.zzb(2, (adp) this.zzbTL) : zzn;
    }
}
