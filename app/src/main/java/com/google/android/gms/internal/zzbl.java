package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbl extends adj<zzbl> {
    public zzbr[] zzkJ = zzbr.zzu();
    public zzbr[] zzkK = zzbr.zzu();
    public zzbk[] zzkL = zzbk.zzq();

    public zzbl() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbl)) {
            return false;
        }
        zzbl zzbl = (zzbl) obj;
        if (!adn.equals((Object[]) this.zzkJ, (Object[]) zzbl.zzkJ)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkK, (Object[]) zzbl.zzkK)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkL, (Object[]) zzbl.zzkL)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbl.zzcso == null || zzbl.zzcso.isEmpty() : this.zzcso.equals(zzbl.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzkJ)) * 31) + adn.hashCode((Object[]) this.zzkK)) * 31) + adn.hashCode((Object[]) this.zzkL)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzkJ == null ? 0 : this.zzkJ.length;
                    zzbr[] zzbrArr = new zzbr[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzkJ, 0, zzbrArr, 0, length);
                    }
                    while (length < zzbrArr.length - 1) {
                        zzbrArr[length] = new zzbr();
                        adg.zza(zzbrArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzbrArr[length] = new zzbr();
                    adg.zza(zzbrArr[length]);
                    this.zzkJ = zzbrArr;
                    continue;
                case 18:
                    int zzb2 = ads.zzb(adg, 18);
                    int length2 = this.zzkK == null ? 0 : this.zzkK.length;
                    zzbr[] zzbrArr2 = new zzbr[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzkK, 0, zzbrArr2, 0, length2);
                    }
                    while (length2 < zzbrArr2.length - 1) {
                        zzbrArr2[length2] = new zzbr();
                        adg.zza(zzbrArr2[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    zzbrArr2[length2] = new zzbr();
                    adg.zza(zzbrArr2[length2]);
                    this.zzkK = zzbrArr2;
                    continue;
                case 26:
                    int zzb3 = ads.zzb(adg, 26);
                    int length3 = this.zzkL == null ? 0 : this.zzkL.length;
                    zzbk[] zzbkArr = new zzbk[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzkL, 0, zzbkArr, 0, length3);
                    }
                    while (length3 < zzbkArr.length - 1) {
                        zzbkArr[length3] = new zzbk();
                        adg.zza(zzbkArr[length3]);
                        adg.zzLA();
                        length3++;
                    }
                    zzbkArr[length3] = new zzbk();
                    adg.zza(zzbkArr[length3]);
                    this.zzkL = zzbkArr;
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
        if (this.zzkJ != null && this.zzkJ.length > 0) {
            for (zzbr zzbr : this.zzkJ) {
                if (zzbr != null) {
                    adh.zza(1, (adp) zzbr);
                }
            }
        }
        if (this.zzkK != null && this.zzkK.length > 0) {
            for (zzbr zzbr2 : this.zzkK) {
                if (zzbr2 != null) {
                    adh.zza(2, (adp) zzbr2);
                }
            }
        }
        if (this.zzkL != null && this.zzkL.length > 0) {
            for (zzbk zzbk : this.zzkL) {
                if (zzbk != null) {
                    adh.zza(3, (adp) zzbk);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzkJ != null && this.zzkJ.length > 0) {
            for (zzbr zzbr : this.zzkJ) {
                if (zzbr != null) {
                    zzn += adh.zzb(1, (adp) zzbr);
                }
            }
        }
        if (this.zzkK != null && this.zzkK.length > 0) {
            for (zzbr zzbr2 : this.zzkK) {
                if (zzbr2 != null) {
                    zzn += adh.zzb(2, (adp) zzbr2);
                }
            }
        }
        if (this.zzkL != null && this.zzkL.length > 0) {
            for (zzbk zzbk : this.zzkL) {
                if (zzbk != null) {
                    zzn += adh.zzb(3, (adp) zzbk);
                }
            }
        }
        return zzn;
    }
}
