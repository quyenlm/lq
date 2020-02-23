package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbqz extends adj<zzbqz> {
    public long sequenceNumber = -1;
    public int versionCode = 1;
    public long zzaPw = -1;
    public long zzaPx = -1;

    public zzbqz() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbqz)) {
            return false;
        }
        zzbqz zzbqz = (zzbqz) obj;
        if (this.versionCode != zzbqz.versionCode) {
            return false;
        }
        if (this.sequenceNumber != zzbqz.sequenceNumber) {
            return false;
        }
        if (this.zzaPw != zzbqz.zzaPw) {
            return false;
        }
        if (this.zzaPx != zzbqz.zzaPx) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbqz.zzcso == null || zzbqz.zzcso.isEmpty() : this.zzcso.equals(zzbqz.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((getClass().getName().hashCode() + 527) * 31) + this.versionCode) * 31) + ((int) (this.sequenceNumber ^ (this.sequenceNumber >>> 32)))) * 31) + ((int) (this.zzaPw ^ (this.zzaPw >>> 32)))) * 31) + ((int) (this.zzaPx ^ (this.zzaPx >>> 32)))) * 31);
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
                case 16:
                    long zzLG = adg.zzLG();
                    this.sequenceNumber = (-(zzLG & 1)) ^ (zzLG >>> 1);
                    continue;
                case 24:
                    long zzLG2 = adg.zzLG();
                    this.zzaPw = (-(zzLG2 & 1)) ^ (zzLG2 >>> 1);
                    continue;
                case 32:
                    long zzLG3 = adg.zzLG();
                    this.zzaPx = (-(zzLG3 & 1)) ^ (zzLG3 >>> 1);
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
        adh.zzd(2, this.sequenceNumber);
        adh.zzd(3, this.zzaPw);
        adh.zzd(4, this.zzaPx);
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        return super.zzn() + adh.zzs(1, this.versionCode) + adh.zzf(2, this.sequenceNumber) + adh.zzf(3, this.zzaPw) + adh.zzf(4, this.zzaPx);
    }
}
