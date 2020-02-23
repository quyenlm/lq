package com.google.android.gms.internal;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;

public final class acx extends adj<acx> {
    private int zzcqq = 0;
    private long zzcqr = 0;
    private double zzcrA = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    private int[] zzcrB = ads.zzcsC;
    private double zzcrz = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    public acx() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acx)) {
            return false;
        }
        acx acx = (acx) obj;
        if (this.zzcqq != acx.zzcqq) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzcrz) != Double.doubleToLongBits(acx.zzcrz)) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzcrA) != Double.doubleToLongBits(acx.zzcrA)) {
            return false;
        }
        if (this.zzcqr != acx.zzcqr) {
            return false;
        }
        if (!adn.equals(this.zzcrB, acx.zzcrB)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acx.zzcso == null || acx.zzcso.isEmpty() : this.zzcso.equals(acx.zzcso);
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzcrz);
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzcrA);
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + adn.hashCode(this.zzcrB)) * 31);
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
                case 17:
                    this.zzcrz = Double.longBitsToDouble(adg.zzLI());
                    continue;
                case 25:
                    this.zzcrA = Double.longBitsToDouble(adg.zzLI());
                    continue;
                case 32:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 40:
                    int zzb = ads.zzb(adg, 40);
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
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
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
                        int length = this.zzcrB == null ? 0 : this.zzcrB.length;
                        if (length != 0 || i3 != iArr.length) {
                            int[] iArr2 = new int[(length + i3)];
                            if (length != 0) {
                                System.arraycopy(this.zzcrB, 0, iArr2, 0, length);
                            }
                            System.arraycopy(iArr, 0, iArr2, length, i3);
                            this.zzcrB = iArr2;
                            break;
                        } else {
                            this.zzcrB = iArr;
                            break;
                        }
                    } else {
                        continue;
                    }
                case 42:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i4 = 0;
                    while (adg.zzLK() > 0) {
                        switch (adg.zzLF()) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                                i4++;
                                break;
                        }
                    }
                    if (i4 != 0) {
                        adg.zzcp(position3);
                        int length2 = this.zzcrB == null ? 0 : this.zzcrB.length;
                        int[] iArr3 = new int[(i4 + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzcrB, 0, iArr3, 0, length2);
                        }
                        while (adg.zzLK() > 0) {
                            int position4 = adg.getPosition();
                            int zzLF3 = adg.zzLF();
                            switch (zzLF3) {
                                case 0:
                                case 1:
                                case 2:
                                case 3:
                                case 4:
                                    iArr3[length2] = zzLF3;
                                    length2++;
                                    break;
                                default:
                                    adg.zzcp(position4);
                                    zza(adg, 40);
                                    break;
                            }
                        }
                        this.zzcrB = iArr3;
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
        if (Double.doubleToLongBits(this.zzcrz) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            adh.zza(2, this.zzcrz);
        }
        if (Double.doubleToLongBits(this.zzcrA) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            adh.zza(3, this.zzcrA);
        }
        if (this.zzcqr != 0) {
            adh.zzb(4, this.zzcqr);
        }
        if (this.zzcrB != null && this.zzcrB.length > 0) {
            for (int zzr : this.zzcrB) {
                adh.zzr(5, zzr);
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
        if (Double.doubleToLongBits(this.zzcrz) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            zzn += adh.zzct(2) + 8;
        }
        if (Double.doubleToLongBits(this.zzcrA) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            zzn += adh.zzct(3) + 8;
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(4, this.zzcqr);
        }
        if (this.zzcrB == null || this.zzcrB.length <= 0) {
            return zzn;
        }
        int i2 = 0;
        while (true) {
            int i3 = i;
            if (i2 >= this.zzcrB.length) {
                return zzn + i3 + (this.zzcrB.length * 1);
            }
            i = adh.zzcr(this.zzcrB[i2]) + i3;
            i2++;
        }
    }
}
