package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbj extends adj<zzbj> {
    private static volatile zzbj[] zzkz;
    private int name = 0;
    public int[] zzkA = ads.zzcsC;
    private int zzkB = 0;
    private boolean zzkC = false;
    private boolean zzkD = false;

    public zzbj() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzbj[] zzp() {
        if (zzkz == null) {
            synchronized (adn.zzcsw) {
                if (zzkz == null) {
                    zzkz = new zzbj[0];
                }
            }
        }
        return zzkz;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbj)) {
            return false;
        }
        zzbj zzbj = (zzbj) obj;
        if (!adn.equals(this.zzkA, zzbj.zzkA)) {
            return false;
        }
        if (this.zzkB != zzbj.zzkB) {
            return false;
        }
        if (this.name != zzbj.name) {
            return false;
        }
        if (this.zzkC != zzbj.zzkC) {
            return false;
        }
        if (this.zzkD != zzbj.zzkD) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbj.zzcso == null || zzbj.zzcso.isEmpty() : this.zzcso.equals(zzbj.zzcso);
    }

    public final int hashCode() {
        int i = 1231;
        int hashCode = ((this.zzkC ? 1231 : 1237) + ((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode(this.zzkA)) * 31) + this.zzkB) * 31) + this.name) * 31)) * 31;
        if (!this.zzkD) {
            i = 1237;
        }
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((hashCode + i) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzkD = adg.zzLD();
                    continue;
                case 16:
                    this.zzkB = adg.zzLF();
                    continue;
                case 24:
                    int zzb = ads.zzb(adg, 24);
                    int length = this.zzkA == null ? 0 : this.zzkA.length;
                    int[] iArr = new int[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzkA, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = adg.zzLF();
                        adg.zzLA();
                        length++;
                    }
                    iArr[length] = adg.zzLF();
                    this.zzkA = iArr;
                    continue;
                case 26:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzkA == null ? 0 : this.zzkA.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzkA, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = adg.zzLF();
                        length2++;
                    }
                    this.zzkA = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 32:
                    this.name = adg.zzLF();
                    continue;
                case 48:
                    this.zzkC = adg.zzLD();
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
        if (this.zzkD) {
            adh.zzk(1, this.zzkD);
        }
        adh.zzr(2, this.zzkB);
        if (this.zzkA != null && this.zzkA.length > 0) {
            for (int zzr : this.zzkA) {
                adh.zzr(3, zzr);
            }
        }
        if (this.name != 0) {
            adh.zzr(4, this.name);
        }
        if (this.zzkC) {
            adh.zzk(6, this.zzkC);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int i2;
        int i3 = 0;
        int zzn = super.zzn();
        if (this.zzkD) {
            zzn += adh.zzct(1) + 1;
        }
        int zzs = zzn + adh.zzs(2, this.zzkB);
        if (this.zzkA == null || this.zzkA.length <= 0) {
            i = zzs;
        } else {
            int i4 = 0;
            while (true) {
                i2 = i3;
                if (i4 >= this.zzkA.length) {
                    break;
                }
                i3 = adh.zzcr(this.zzkA[i4]) + i2;
                i4++;
            }
            i = zzs + i2 + (this.zzkA.length * 1);
        }
        if (this.name != 0) {
            i += adh.zzs(4, this.name);
        }
        return this.zzkC ? i + adh.zzct(6) + 1 : i;
    }
}
