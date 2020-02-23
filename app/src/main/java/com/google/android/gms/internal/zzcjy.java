package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjy extends adj<zzcjy> {
    public zzcjz[] zzbvB = zzcjz.zzzD();

    public zzcjy() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjy)) {
            return false;
        }
        zzcjy zzcjy = (zzcjy) obj;
        if (!adn.equals((Object[]) this.zzbvB, (Object[]) zzcjy.zzbvB)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjy.zzcso == null || zzcjy.zzcso.isEmpty() : this.zzcso.equals(zzcjy.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzbvB)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzbvB == null ? 0 : this.zzbvB.length;
                    zzcjz[] zzcjzArr = new zzcjz[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbvB, 0, zzcjzArr, 0, length);
                    }
                    while (length < zzcjzArr.length - 1) {
                        zzcjzArr[length] = new zzcjz();
                        adg.zza(zzcjzArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcjzArr[length] = new zzcjz();
                    adg.zza(zzcjzArr[length]);
                    this.zzbvB = zzcjzArr;
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
        if (this.zzbvB != null && this.zzbvB.length > 0) {
            for (zzcjz zzcjz : this.zzbvB) {
                if (zzcjz != null) {
                    adh.zza(1, (adp) zzcjz);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbvB != null && this.zzbvB.length > 0) {
            for (zzcjz zzcjz : this.zzbvB) {
                if (zzcjz != null) {
                    zzn += adh.zzb(1, (adp) zzcjz);
                }
            }
        }
        return zzn;
    }
}
