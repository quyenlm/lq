package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcka extends adj<zzcka> {
    public long[] zzbwe = ads.zzcsD;
    public long[] zzbwf = ads.zzcsD;

    public zzcka() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcka)) {
            return false;
        }
        zzcka zzcka = (zzcka) obj;
        if (!adn.equals(this.zzbwe, zzcka.zzbwe)) {
            return false;
        }
        if (!adn.equals(this.zzbwf, zzcka.zzbwf)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcka.zzcso == null || zzcka.zzcso.isEmpty() : this.zzcso.equals(zzcka.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode(this.zzbwe)) * 31) + adn.hashCode(this.zzbwf)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    int zzb = ads.zzb(adg, 8);
                    int length = this.zzbwe == null ? 0 : this.zzbwe.length;
                    long[] jArr = new long[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbwe, 0, jArr, 0, length);
                    }
                    while (length < jArr.length - 1) {
                        jArr[length] = adg.zzLG();
                        adg.zzLA();
                        length++;
                    }
                    jArr[length] = adg.zzLG();
                    this.zzbwe = jArr;
                    continue;
                case 10:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLG();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzbwe == null ? 0 : this.zzbwe.length;
                    long[] jArr2 = new long[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzbwe, 0, jArr2, 0, length2);
                    }
                    while (length2 < jArr2.length) {
                        jArr2[length2] = adg.zzLG();
                        length2++;
                    }
                    this.zzbwe = jArr2;
                    adg.zzco(zzcn);
                    continue;
                case 16:
                    int zzb2 = ads.zzb(adg, 16);
                    int length3 = this.zzbwf == null ? 0 : this.zzbwf.length;
                    long[] jArr3 = new long[(zzb2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzbwf, 0, jArr3, 0, length3);
                    }
                    while (length3 < jArr3.length - 1) {
                        jArr3[length3] = adg.zzLG();
                        adg.zzLA();
                        length3++;
                    }
                    jArr3[length3] = adg.zzLG();
                    this.zzbwf = jArr3;
                    continue;
                case 18:
                    int zzcn2 = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i2 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLG();
                        i2++;
                    }
                    adg.zzcp(position2);
                    int length4 = this.zzbwf == null ? 0 : this.zzbwf.length;
                    long[] jArr4 = new long[(i2 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzbwf, 0, jArr4, 0, length4);
                    }
                    while (length4 < jArr4.length) {
                        jArr4[length4] = adg.zzLG();
                        length4++;
                    }
                    this.zzbwf = jArr4;
                    adg.zzco(zzcn2);
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
        if (this.zzbwe != null && this.zzbwe.length > 0) {
            for (long zza : this.zzbwe) {
                adh.zza(1, zza);
            }
        }
        if (this.zzbwf != null && this.zzbwf.length > 0) {
            for (long zza2 : this.zzbwf) {
                adh.zza(2, zza2);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzbwe == null || this.zzbwe.length <= 0) {
            i = zzn;
        } else {
            int i2 = 0;
            for (long zzaP : this.zzbwe) {
                i2 += adh.zzaP(zzaP);
            }
            i = zzn + i2 + (this.zzbwe.length * 1);
        }
        if (this.zzbwf == null || this.zzbwf.length <= 0) {
            return i;
        }
        int i3 = 0;
        for (long zzaP2 : this.zzbwf) {
            i3 += adh.zzaP(zzaP2);
        }
        return i + i3 + (this.zzbwf.length * 1);
    }
}
