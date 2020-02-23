package com.google.android.gms.internal;

import java.io.IOException;

public final class zzba extends adj<zzba> {
    private int[] zzcA = ads.zzcsC;
    private Long zzcB = null;
    private Long zzcx = null;
    private Integer zzcy = null;
    private Boolean zzcz = null;

    public zzba() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzcx = Long.valueOf(adg.zzLG());
                    continue;
                case 16:
                    this.zzcy = Integer.valueOf(adg.zzLF());
                    continue;
                case 24:
                    this.zzcz = Boolean.valueOf(adg.zzLD());
                    continue;
                case 32:
                    int zzb = ads.zzb(adg, 32);
                    int length = this.zzcA == null ? 0 : this.zzcA.length;
                    int[] iArr = new int[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcA, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = adg.zzLF();
                        adg.zzLA();
                        length++;
                    }
                    iArr[length] = adg.zzLF();
                    this.zzcA = iArr;
                    continue;
                case 34:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzcA == null ? 0 : this.zzcA.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcA, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = adg.zzLF();
                        length2++;
                    }
                    this.zzcA = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 40:
                    this.zzcB = Long.valueOf(adg.zzLG());
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
        if (this.zzcx != null) {
            adh.zzb(1, this.zzcx.longValue());
        }
        if (this.zzcy != null) {
            adh.zzr(2, this.zzcy.intValue());
        }
        if (this.zzcz != null) {
            adh.zzk(3, this.zzcz.booleanValue());
        }
        if (this.zzcA != null && this.zzcA.length > 0) {
            for (int zzr : this.zzcA) {
                adh.zzr(4, zzr);
            }
        }
        if (this.zzcB != null) {
            adh.zza(5, this.zzcB.longValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int i2 = 0;
        int zzn = super.zzn();
        if (this.zzcx != null) {
            zzn += adh.zze(1, this.zzcx.longValue());
        }
        if (this.zzcy != null) {
            zzn += adh.zzs(2, this.zzcy.intValue());
        }
        if (this.zzcz != null) {
            this.zzcz.booleanValue();
            zzn += adh.zzct(3) + 1;
        }
        if (this.zzcA != null && this.zzcA.length > 0) {
            int i3 = 0;
            while (true) {
                i = i2;
                if (i3 >= this.zzcA.length) {
                    break;
                }
                i2 = adh.zzcr(this.zzcA[i3]) + i;
                i3++;
            }
            zzn = zzn + i + (this.zzcA.length * 1);
        }
        if (this.zzcB == null) {
            return zzn;
        }
        return zzn + adh.zzct(5) + adh.zzaP(this.zzcB.longValue());
    }
}
