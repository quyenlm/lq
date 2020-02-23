package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbrb extends adj<zzbrb> {
    public long zzaPw = -1;
    public long zzaPz = -1;

    public zzbrb() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbrb)) {
            return false;
        }
        zzbrb zzbrb = (zzbrb) obj;
        if (this.zzaPz != zzbrb.zzaPz) {
            return false;
        }
        if (this.zzaPw != zzbrb.zzaPw) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbrb.zzcso == null || zzbrb.zzcso.isEmpty() : this.zzcso.equals(zzbrb.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzaPz ^ (this.zzaPz >>> 32)))) * 31) + ((int) (this.zzaPw ^ (this.zzaPw >>> 32)))) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    long zzLG = adg.zzLG();
                    this.zzaPz = (-(zzLG & 1)) ^ (zzLG >>> 1);
                    continue;
                case 16:
                    long zzLG2 = adg.zzLG();
                    this.zzaPw = (-(zzLG2 & 1)) ^ (zzLG2 >>> 1);
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
        adh.zzd(1, this.zzaPz);
        adh.zzd(2, this.zzaPw);
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        return super.zzn() + adh.zzf(1, this.zzaPz) + adh.zzf(2, this.zzaPw);
    }
}
