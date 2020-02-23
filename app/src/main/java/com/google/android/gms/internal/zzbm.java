package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbm extends adj<zzbm> {
    private static volatile zzbm[] zzkM;
    public int key = 0;
    public int value = 0;

    public zzbm() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzbm[] zzr() {
        if (zzkM == null) {
            synchronized (adn.zzcsw) {
                if (zzkM == null) {
                    zzkM = new zzbm[0];
                }
            }
        }
        return zzkM;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbm)) {
            return false;
        }
        zzbm zzbm = (zzbm) obj;
        if (this.key != zzbm.key) {
            return false;
        }
        if (this.value != zzbm.value) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbm.zzcso == null || zzbm.zzcso.isEmpty() : this.zzcso.equals(zzbm.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + this.key) * 31) + this.value) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.key = adg.zzLF();
                    continue;
                case 16:
                    this.value = adg.zzLF();
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
        adh.zzr(1, this.key);
        adh.zzr(2, this.value);
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        return super.zzn() + adh.zzs(1, this.key) + adh.zzs(2, this.value);
    }
}
