package com.google.android.gms.internal;

import java.io.IOException;

public final class ach extends adj<ach> {
    public int zzcqq = 0;
    public long zzcqr = 0;
    public int[] zzcqs = ads.zzcsC;

    public ach() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ach)) {
            return false;
        }
        ach ach = (ach) obj;
        if (this.zzcqq != ach.zzcqq) {
            return false;
        }
        if (this.zzcqr != ach.zzcqr) {
            return false;
        }
        if (!adn.equals(this.zzcqs, ach.zzcqs)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? ach.zzcso == null || ach.zzcso.isEmpty() : this.zzcso.equals(ach.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + adn.hashCode(this.zzcqs)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        int i;
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 16:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 24:
                    int zzb = ads.zzb(adg, 24);
                    int[] iArr = new int[zzb];
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < zzb) {
                        if (i2 != 0) {
                            adg.zzLA();
                        }
                        int position2 = adg.getPosition();
                        int zzLF2 = adg.zzLF();
                        switch (zzLF2) {
                            case -1000:
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                                i = i3 + 1;
                                iArr[i3] = zzLF2;
                                break;
                            default:
                                adg.zzcp(position2);
                                zza(adg, zzLA);
                                i = i3;
                                break;
                        }
                        i2++;
                        i3 = i;
                    }
                    if (i3 != 0) {
                        int length = this.zzcqs == null ? 0 : this.zzcqs.length;
                        if (length != 0 || i3 != iArr.length) {
                            int[] iArr2 = new int[(length + i3)];
                            if (length != 0) {
                                System.arraycopy(this.zzcqs, 0, iArr2, 0, length);
                            }
                            System.arraycopy(iArr, 0, iArr2, length, i3);
                            this.zzcqs = iArr2;
                            break;
                        } else {
                            this.zzcqs = iArr;
                            break;
                        }
                    } else {
                        continue;
                    }
                case 26:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i4 = 0;
                    while (adg.zzLK() > 0) {
                        switch (adg.zzLF()) {
                            case -1000:
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                                i4++;
                                break;
                        }
                    }
                    if (i4 != 0) {
                        adg.zzcp(position3);
                        int length2 = this.zzcqs == null ? 0 : this.zzcqs.length;
                        int[] iArr3 = new int[(i4 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzcqs, 0, iArr3, 0, length2);
                        }
                        while (adg.zzLK() > 0) {
                            int position4 = adg.getPosition();
                            int zzLF3 = adg.zzLF();
                            switch (zzLF3) {
                                case -1000:
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                case 9:
                                case 10:
                                case 11:
                                case 12:
                                case 13:
                                case 14:
                                case 15:
                                    iArr3[length2] = zzLF3;
                                    length2++;
                                    break;
                                default:
                                    adg.zzcp(position4);
                                    zza(adg, 24);
                                    break;
                            }
                        }
                        this.zzcqs = iArr3;
                    }
                    adg.zzco(zzcn);
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            adh.zzb(2, this.zzcqr);
        }
        if (this.zzcqs != null && this.zzcqs.length > 0) {
            for (int zzr : this.zzcqs) {
                adh.zzr(3, zzr);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i = 0;
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(2, this.zzcqr);
        }
        if (this.zzcqs == null || this.zzcqs.length <= 0) {
            return zzn;
        }
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= this.zzcqs.length) {
                return zzn + i3 + (this.zzcqs.length * 1);
            }
            i = adh.zzcr(this.zzcqs[i2]) + i3;
            i2++;
        }
    }
}
