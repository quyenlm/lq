package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjv extends adj<zzcjv> {
    private static volatile zzcjv[] zzbvr;
    public Integer zzbuI = null;
    public zzcka zzbvs = null;
    public zzcka zzbvt = null;
    public Boolean zzbvu = null;

    public zzcjv() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcjv[] zzzA() {
        if (zzbvr == null) {
            synchronized (adn.zzcsw) {
                if (zzbvr == null) {
                    zzbvr = new zzcjv[0];
                }
            }
        }
        return zzbvr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjv)) {
            return false;
        }
        zzcjv zzcjv = (zzcjv) obj;
        if (this.zzbuI == null) {
            if (zzcjv.zzbuI != null) {
                return false;
            }
        } else if (!this.zzbuI.equals(zzcjv.zzbuI)) {
            return false;
        }
        if (this.zzbvs == null) {
            if (zzcjv.zzbvs != null) {
                return false;
            }
        } else if (!this.zzbvs.equals(zzcjv.zzbvs)) {
            return false;
        }
        if (this.zzbvt == null) {
            if (zzcjv.zzbvt != null) {
                return false;
            }
        } else if (!this.zzbvt.equals(zzcjv.zzbvt)) {
            return false;
        }
        if (this.zzbvu == null) {
            if (zzcjv.zzbvu != null) {
                return false;
            }
        } else if (!this.zzbvu.equals(zzcjv.zzbvu)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjv.zzcso == null || zzcjv.zzcso.isEmpty() : this.zzcso.equals(zzcjv.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbvu == null ? 0 : this.zzbvu.hashCode()) + (((this.zzbvt == null ? 0 : this.zzbvt.hashCode()) + (((this.zzbvs == null ? 0 : this.zzbvs.hashCode()) + (((this.zzbuI == null ? 0 : this.zzbuI.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
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
                    this.zzbuI = Integer.valueOf(adg.zzLF());
                    continue;
                case 18:
                    if (this.zzbvs == null) {
                        this.zzbvs = new zzcka();
                    }
                    adg.zza(this.zzbvs);
                    continue;
                case 26:
                    if (this.zzbvt == null) {
                        this.zzbvt = new zzcka();
                    }
                    adg.zza(this.zzbvt);
                    continue;
                case 32:
                    this.zzbvu = Boolean.valueOf(adg.zzLD());
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
        if (this.zzbuI != null) {
            adh.zzr(1, this.zzbuI.intValue());
        }
        if (this.zzbvs != null) {
            adh.zza(2, (adp) this.zzbvs);
        }
        if (this.zzbvt != null) {
            adh.zza(3, (adp) this.zzbvt);
        }
        if (this.zzbvu != null) {
            adh.zzk(4, this.zzbvu.booleanValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbuI != null) {
            zzn += adh.zzs(1, this.zzbuI.intValue());
        }
        if (this.zzbvs != null) {
            zzn += adh.zzb(2, (adp) this.zzbvs);
        }
        if (this.zzbvt != null) {
            zzn += adh.zzb(3, (adp) this.zzbvt);
        }
        if (this.zzbvu == null) {
            return zzn;
        }
        this.zzbvu.booleanValue();
        return zzn + adh.zzct(4) + 1;
    }
}
