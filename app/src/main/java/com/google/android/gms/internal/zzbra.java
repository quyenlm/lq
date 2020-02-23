package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbra extends adj<zzbra> {
    public int versionCode = 1;
    public int zzaPA = -1;
    public long zzaPw = -1;
    public String zzaPy = "";
    public long zzaPz = -1;

    public zzbra() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbra)) {
            return false;
        }
        zzbra zzbra = (zzbra) obj;
        if (this.versionCode != zzbra.versionCode) {
            return false;
        }
        if (this.zzaPy == null) {
            if (zzbra.zzaPy != null) {
                return false;
            }
        } else if (!this.zzaPy.equals(zzbra.zzaPy)) {
            return false;
        }
        if (this.zzaPz != zzbra.zzaPz) {
            return false;
        }
        if (this.zzaPw != zzbra.zzaPw) {
            return false;
        }
        if (this.zzaPA != zzbra.zzaPA) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbra.zzcso == null || zzbra.zzcso.isEmpty() : this.zzcso.equals(zzbra.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.zzaPy == null ? 0 : this.zzaPy.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.versionCode) * 31)) * 31) + ((int) (this.zzaPz ^ (this.zzaPz >>> 32)))) * 31) + ((int) (this.zzaPw ^ (this.zzaPw >>> 32)))) * 31) + this.zzaPA) * 31;
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
                    this.versionCode = adg.zzLF();
                    continue;
                case 18:
                    this.zzaPy = adg.readString();
                    continue;
                case 24:
                    long zzLG = adg.zzLG();
                    this.zzaPz = (-(zzLG & 1)) ^ (zzLG >>> 1);
                    continue;
                case 32:
                    long zzLG2 = adg.zzLG();
                    this.zzaPw = (-(zzLG2 & 1)) ^ (zzLG2 >>> 1);
                    continue;
                case 40:
                    this.zzaPA = adg.zzLF();
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
        adh.zzr(1, this.versionCode);
        adh.zzl(2, this.zzaPy);
        adh.zzd(3, this.zzaPz);
        adh.zzd(4, this.zzaPw);
        if (this.zzaPA != -1) {
            adh.zzr(5, this.zzaPA);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn() + adh.zzs(1, this.versionCode) + adh.zzm(2, this.zzaPy) + adh.zzf(3, this.zzaPz) + adh.zzf(4, this.zzaPw);
        return this.zzaPA != -1 ? zzn + adh.zzs(5, this.zzaPA) : zzn;
    }
}
