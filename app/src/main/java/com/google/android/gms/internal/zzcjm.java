package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjm extends adj<zzcjm> {
    private static volatile zzcjm[] zzbuH;
    public Integer zzbuI = null;
    public zzcjq[] zzbuJ = zzcjq.zzzx();
    public zzcjn[] zzbuK = zzcjn.zzzv();

    public zzcjm() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcjm[] zzzu() {
        if (zzbuH == null) {
            synchronized (adn.zzcsw) {
                if (zzbuH == null) {
                    zzbuH = new zzcjm[0];
                }
            }
        }
        return zzbuH;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjm)) {
            return false;
        }
        zzcjm zzcjm = (zzcjm) obj;
        if (this.zzbuI == null) {
            if (zzcjm.zzbuI != null) {
                return false;
            }
        } else if (!this.zzbuI.equals(zzcjm.zzbuI)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbuJ, (Object[]) zzcjm.zzbuJ)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbuK, (Object[]) zzcjm.zzbuK)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjm.zzcso == null || zzcjm.zzcso.isEmpty() : this.zzcso.equals(zzcjm.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((this.zzbuI == null ? 0 : this.zzbuI.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + adn.hashCode((Object[]) this.zzbuJ)) * 31) + adn.hashCode((Object[]) this.zzbuK)) * 31;
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
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzbuJ == null ? 0 : this.zzbuJ.length;
                    zzcjq[] zzcjqArr = new zzcjq[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbuJ, 0, zzcjqArr, 0, length);
                    }
                    while (length < zzcjqArr.length - 1) {
                        zzcjqArr[length] = new zzcjq();
                        adg.zza(zzcjqArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcjqArr[length] = new zzcjq();
                    adg.zza(zzcjqArr[length]);
                    this.zzbuJ = zzcjqArr;
                    continue;
                case 26:
                    int zzb2 = ads.zzb(adg, 26);
                    int length2 = this.zzbuK == null ? 0 : this.zzbuK.length;
                    zzcjn[] zzcjnArr = new zzcjn[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzbuK, 0, zzcjnArr, 0, length2);
                    }
                    while (length2 < zzcjnArr.length - 1) {
                        zzcjnArr[length2] = new zzcjn();
                        adg.zza(zzcjnArr[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    zzcjnArr[length2] = new zzcjn();
                    adg.zza(zzcjnArr[length2]);
                    this.zzbuK = zzcjnArr;
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
        if (this.zzbuJ != null && this.zzbuJ.length > 0) {
            for (zzcjq zzcjq : this.zzbuJ) {
                if (zzcjq != null) {
                    adh.zza(2, (adp) zzcjq);
                }
            }
        }
        if (this.zzbuK != null && this.zzbuK.length > 0) {
            for (zzcjn zzcjn : this.zzbuK) {
                if (zzcjn != null) {
                    adh.zza(3, (adp) zzcjn);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbuI != null) {
            zzn += adh.zzs(1, this.zzbuI.intValue());
        }
        if (this.zzbuJ != null && this.zzbuJ.length > 0) {
            int i = zzn;
            for (zzcjq zzcjq : this.zzbuJ) {
                if (zzcjq != null) {
                    i += adh.zzb(2, (adp) zzcjq);
                }
            }
            zzn = i;
        }
        if (this.zzbuK != null && this.zzbuK.length > 0) {
            for (zzcjn zzcjn : this.zzbuK) {
                if (zzcjn != null) {
                    zzn += adh.zzb(3, (adp) zzcjn);
                }
            }
        }
        return zzn;
    }
}
