package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcaz extends adj<zzcaz> {
    public zzcba[] zzbgp = zzcba.zzvH();

    public zzcaz() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcaz)) {
            return false;
        }
        zzcaz zzcaz = (zzcaz) obj;
        if (!adn.equals((Object[]) this.zzbgp, (Object[]) zzcaz.zzbgp)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcaz.zzcso == null || zzcaz.zzcso.isEmpty() : this.zzcso.equals(zzcaz.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzbgp)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzbgp == null ? 0 : this.zzbgp.length;
                    zzcba[] zzcbaArr = new zzcba[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbgp, 0, zzcbaArr, 0, length);
                    }
                    while (length < zzcbaArr.length - 1) {
                        zzcbaArr[length] = new zzcba();
                        adg.zza(zzcbaArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcbaArr[length] = new zzcba();
                    adg.zza(zzcbaArr[length]);
                    this.zzbgp = zzcbaArr;
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
        if (this.zzbgp != null && this.zzbgp.length > 0) {
            for (zzcba zzcba : this.zzbgp) {
                if (zzcba != null) {
                    adh.zza(1, (adp) zzcba);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbgp != null && this.zzbgp.length > 0) {
            for (zzcba zzcba : this.zzbgp) {
                if (zzcba != null) {
                    zzn += adh.zzb(1, (adp) zzcba);
                }
            }
        }
        return zzn;
    }
}
