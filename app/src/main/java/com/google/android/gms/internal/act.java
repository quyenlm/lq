package com.google.android.gms.internal;

import java.io.IOException;

public final class act extends adj<act> {
    private int zzcqq = 0;
    private long zzcqr = 0;
    private int[] zzcrs = ads.zzcsC;
    private int[] zzcrt = ads.zzcsC;

    public act() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof act)) {
            return false;
        }
        act act = (act) obj;
        if (this.zzcqq != act.zzcqq) {
            return false;
        }
        if (this.zzcqr != act.zzcqr) {
            return false;
        }
        if (!adn.equals(this.zzcrs, act.zzcrs)) {
            return false;
        }
        if (!adn.equals(this.zzcrt, act.zzcrt)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? act.zzcso == null || act.zzcso.isEmpty() : this.zzcso.equals(act.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + adn.hashCode(this.zzcrs)) * 31) + adn.hashCode(this.zzcrt)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        int i;
        int i2;
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
                        case 4:
                        case 5:
                        case 6:
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
                    int i3 = 0;
                    int i4 = 0;
                    while (i3 < zzb) {
                        if (i3 != 0) {
                            adg.zzLA();
                        }
                        int position2 = adg.getPosition();
                        int zzLF2 = adg.zzLF();
                        switch (zzLF2) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                i2 = i4 + 1;
                                iArr[i4] = zzLF2;
                                break;
                            default:
                                adg.zzcp(position2);
                                zza(adg, zzLA);
                                i2 = i4;
                                break;
                        }
                        i3++;
                        i4 = i2;
                    }
                    if (i4 != 0) {
                        int length = this.zzcrs == null ? 0 : this.zzcrs.length;
                        if (length != 0 || i4 != iArr.length) {
                            int[] iArr2 = new int[(length + i4)];
                            if (length != 0) {
                                System.arraycopy(this.zzcrs, 0, iArr2, 0, length);
                            }
                            System.arraycopy(iArr, 0, iArr2, length, i4);
                            this.zzcrs = iArr2;
                            break;
                        } else {
                            this.zzcrs = iArr;
                            break;
                        }
                    } else {
                        continue;
                    }
                case 26:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i5 = 0;
                    while (adg.zzLK() > 0) {
                        switch (adg.zzLF()) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                i5++;
                                break;
                        }
                    }
                    if (i5 != 0) {
                        adg.zzcp(position3);
                        int length2 = this.zzcrs == null ? 0 : this.zzcrs.length;
                        int[] iArr3 = new int[(i5 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzcrs, 0, iArr3, 0, length2);
                        }
                        while (adg.zzLK() > 0) {
                            int position4 = adg.getPosition();
                            int zzLF3 = adg.zzLF();
                            switch (zzLF3) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                    iArr3[length2] = zzLF3;
                                    length2++;
                                    break;
                                default:
                                    adg.zzcp(position4);
                                    zza(adg, 24);
                                    break;
                            }
                        }
                        this.zzcrs = iArr3;
                    }
                    adg.zzco(zzcn);
                    continue;
                case 32:
                    int zzb2 = ads.zzb(adg, 32);
                    int[] iArr4 = new int[zzb2];
                    int i6 = 0;
                    int i7 = 0;
                    while (i6 < zzb2) {
                        if (i6 != 0) {
                            adg.zzLA();
                        }
                        int position5 = adg.getPosition();
                        int zzLF4 = adg.zzLF();
                        switch (zzLF4) {
                            case 0:
                            case 1:
                            case 2:
                                i = i7 + 1;
                                iArr4[i7] = zzLF4;
                                break;
                            default:
                                adg.zzcp(position5);
                                zza(adg, zzLA);
                                i = i7;
                                break;
                        }
                        i6++;
                        i7 = i;
                    }
                    if (i7 != 0) {
                        int length3 = this.zzcrt == null ? 0 : this.zzcrt.length;
                        if (length3 != 0 || i7 != iArr4.length) {
                            int[] iArr5 = new int[(length3 + i7)];
                            if (length3 != 0) {
                                System.arraycopy(this.zzcrt, 0, iArr5, 0, length3);
                            }
                            System.arraycopy(iArr4, 0, iArr5, length3, i7);
                            this.zzcrt = iArr5;
                            break;
                        } else {
                            this.zzcrt = iArr4;
                            break;
                        }
                    } else {
                        continue;
                    }
                case 34:
                    int zzcn2 = adg.zzcn(adg.zzLF());
                    int position6 = adg.getPosition();
                    int i8 = 0;
                    while (adg.zzLK() > 0) {
                        switch (adg.zzLF()) {
                            case 0:
                            case 1:
                            case 2:
                                i8++;
                                break;
                        }
                    }
                    if (i8 != 0) {
                        adg.zzcp(position6);
                        int length4 = this.zzcrt == null ? 0 : this.zzcrt.length;
                        int[] iArr6 = new int[(i8 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.zzcrt, 0, iArr6, 0, length4);
                        }
                        while (adg.zzLK() > 0) {
                            int position7 = adg.getPosition();
                            int zzLF5 = adg.zzLF();
                            switch (zzLF5) {
                                case 0:
                                case 1:
                                case 2:
                                    iArr6[length4] = zzLF5;
                                    length4++;
                                    break;
                                default:
                                    adg.zzcp(position7);
                                    zza(adg, 32);
                                    break;
                            }
                        }
                        this.zzcrt = iArr6;
                    }
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            adh.zzb(2, this.zzcqr);
        }
        if (this.zzcrs != null && this.zzcrs.length > 0) {
            for (int zzr : this.zzcrs) {
                adh.zzr(3, zzr);
            }
        }
        if (this.zzcrt != null && this.zzcrt.length > 0) {
            for (int zzr2 : this.zzcrt) {
                adh.zzr(4, zzr2);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(2, this.zzcqr);
        }
        if (this.zzcrs != null && this.zzcrs.length > 0) {
            int i = 0;
            for (int zzcr : this.zzcrs) {
                i += adh.zzcr(zzcr);
            }
            zzn = zzn + i + (this.zzcrs.length * 1);
        }
        if (this.zzcrt == null || this.zzcrt.length <= 0) {
            return zzn;
        }
        int i2 = 0;
        for (int zzcr2 : this.zzcrt) {
            i2 += adh.zzcr(zzcr2);
        }
        return zzn + i2 + (this.zzcrt.length * 1);
    }
}
