package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbe extends adj<zzbe> {
    public byte[] zzcE = null;
    public byte[][] zzcJ = ads.zzcsH;
    private Integer zzcK;
    private Integer zzcL;

    public zzbe() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzcJ == null ? 0 : this.zzcJ.length;
                    byte[][] bArr = new byte[(zzb + length)][];
                    if (length != 0) {
                        System.arraycopy(this.zzcJ, 0, bArr, 0, length);
                    }
                    while (length < bArr.length - 1) {
                        bArr[length] = adg.readBytes();
                        adg.zzLA();
                        length++;
                    }
                    bArr[length] = adg.readBytes();
                    this.zzcJ = bArr;
                    continue;
                case 18:
                    this.zzcE = adg.readBytes();
                    continue;
                case 24:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                            this.zzcK = Integer.valueOf(zzLF);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 32:
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcL = Integer.valueOf(zzLF2);
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
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
        if (this.zzcJ != null && this.zzcJ.length > 0) {
            for (byte[] bArr : this.zzcJ) {
                if (bArr != null) {
                    adh.zzb(1, bArr);
                }
            }
        }
        if (this.zzcE != null) {
            adh.zzb(2, this.zzcE);
        }
        if (this.zzcK != null) {
            adh.zzr(3, this.zzcK.intValue());
        }
        if (this.zzcL != null) {
            adh.zzr(4, this.zzcL.intValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int i2;
        int zzn = super.zzn();
        if (this.zzcJ == null || this.zzcJ.length <= 0) {
            i = zzn;
        } else {
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < this.zzcJ.length) {
                byte[] bArr = this.zzcJ[i3];
                if (bArr != null) {
                    i5++;
                    i2 = adh.zzJ(bArr) + i4;
                } else {
                    i2 = i4;
                }
                i3++;
                i4 = i2;
            }
            i = zzn + i4 + (i5 * 1);
        }
        if (this.zzcE != null) {
            i += adh.zzc(2, this.zzcE);
        }
        if (this.zzcK != null) {
            i += adh.zzs(3, this.zzcK.intValue());
        }
        return this.zzcL != null ? i + adh.zzs(4, this.zzcL.intValue()) : i;
    }
}
