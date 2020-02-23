package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbr extends adj<zzbr> {
    private static volatile zzbr[] zzlD;
    public String string = "";
    public int type = 1;
    public zzbr[] zzlE = zzu();
    public zzbr[] zzlF = zzu();
    public zzbr[] zzlG = zzu();
    public String zzlH = "";
    public String zzlI = "";
    public long zzlJ = 0;
    public boolean zzlK = false;
    public zzbr[] zzlL = zzu();
    public int[] zzlM = ads.zzcsC;
    public boolean zzlN = false;

    public zzbr() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzbr[] zzu() {
        if (zzlD == null) {
            synchronized (adn.zzcsw) {
                if (zzlD == null) {
                    zzlD = new zzbr[0];
                }
            }
        }
        return zzlD;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbr)) {
            return false;
        }
        zzbr zzbr = (zzbr) obj;
        if (this.type != zzbr.type) {
            return false;
        }
        if (this.string == null) {
            if (zzbr.string != null) {
                return false;
            }
        } else if (!this.string.equals(zzbr.string)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzlE, (Object[]) zzbr.zzlE)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzlF, (Object[]) zzbr.zzlF)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzlG, (Object[]) zzbr.zzlG)) {
            return false;
        }
        if (this.zzlH == null) {
            if (zzbr.zzlH != null) {
                return false;
            }
        } else if (!this.zzlH.equals(zzbr.zzlH)) {
            return false;
        }
        if (this.zzlI == null) {
            if (zzbr.zzlI != null) {
                return false;
            }
        } else if (!this.zzlI.equals(zzbr.zzlI)) {
            return false;
        }
        if (this.zzlJ != zzbr.zzlJ) {
            return false;
        }
        if (this.zzlK != zzbr.zzlK) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzlL, (Object[]) zzbr.zzlL)) {
            return false;
        }
        if (!adn.equals(this.zzlM, zzbr.zzlM)) {
            return false;
        }
        if (this.zzlN != zzbr.zzlN) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbr.zzcso == null || zzbr.zzcso.isEmpty() : this.zzcso.equals(zzbr.zzcso);
    }

    public final int hashCode() {
        int i = 1231;
        int i2 = 0;
        int hashCode = ((((((this.zzlK ? 1231 : 1237) + (((((this.zzlI == null ? 0 : this.zzlI.hashCode()) + (((this.zzlH == null ? 0 : this.zzlH.hashCode()) + (((((((((this.string == null ? 0 : this.string.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31)) * 31) + adn.hashCode((Object[]) this.zzlE)) * 31) + adn.hashCode((Object[]) this.zzlF)) * 31) + adn.hashCode((Object[]) this.zzlG)) * 31)) * 31)) * 31) + ((int) (this.zzlJ ^ (this.zzlJ >>> 32)))) * 31)) * 31) + adn.hashCode((Object[]) this.zzlL)) * 31) + adn.hashCode(this.zzlM)) * 31;
        if (!this.zzlN) {
            i = 1237;
        }
        int i3 = (hashCode + i) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i2 = this.zzcso.hashCode();
        }
        return i3 + i2;
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
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            this.type = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    this.string = adg.readString();
                    continue;
                case 26:
                    int zzb = ads.zzb(adg, 26);
                    int length = this.zzlE == null ? 0 : this.zzlE.length;
                    zzbr[] zzbrArr = new zzbr[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzlE, 0, zzbrArr, 0, length);
                    }
                    while (length < zzbrArr.length - 1) {
                        zzbrArr[length] = new zzbr();
                        adg.zza(zzbrArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzbrArr[length] = new zzbr();
                    adg.zza(zzbrArr[length]);
                    this.zzlE = zzbrArr;
                    continue;
                case 34:
                    int zzb2 = ads.zzb(adg, 34);
                    int length2 = this.zzlF == null ? 0 : this.zzlF.length;
                    zzbr[] zzbrArr2 = new zzbr[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzlF, 0, zzbrArr2, 0, length2);
                    }
                    while (length2 < zzbrArr2.length - 1) {
                        zzbrArr2[length2] = new zzbr();
                        adg.zza(zzbrArr2[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    zzbrArr2[length2] = new zzbr();
                    adg.zza(zzbrArr2[length2]);
                    this.zzlF = zzbrArr2;
                    continue;
                case 42:
                    int zzb3 = ads.zzb(adg, 42);
                    int length3 = this.zzlG == null ? 0 : this.zzlG.length;
                    zzbr[] zzbrArr3 = new zzbr[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzlG, 0, zzbrArr3, 0, length3);
                    }
                    while (length3 < zzbrArr3.length - 1) {
                        zzbrArr3[length3] = new zzbr();
                        adg.zza(zzbrArr3[length3]);
                        adg.zzLA();
                        length3++;
                    }
                    zzbrArr3[length3] = new zzbr();
                    adg.zza(zzbrArr3[length3]);
                    this.zzlG = zzbrArr3;
                    continue;
                case 50:
                    this.zzlH = adg.readString();
                    continue;
                case 58:
                    this.zzlI = adg.readString();
                    continue;
                case 64:
                    this.zzlJ = adg.zzLG();
                    continue;
                case 72:
                    this.zzlN = adg.zzLD();
                    continue;
                case 80:
                    int zzb4 = ads.zzb(adg, 80);
                    int[] iArr = new int[zzb4];
                    int i2 = 0;
                    int i3 = 0;
                    while (i2 < zzb4) {
                        if (i2 != 0) {
                            adg.zzLA();
                        }
                        int position2 = adg.getPosition();
                        int zzLF2 = adg.zzLF();
                        switch (zzLF2) {
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
                            case 16:
                            case 17:
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
                        int length4 = this.zzlM == null ? 0 : this.zzlM.length;
                        if (length4 != 0 || i3 != iArr.length) {
                            int[] iArr2 = new int[(length4 + i3)];
                            if (length4 != 0) {
                                System.arraycopy(this.zzlM, 0, iArr2, 0, length4);
                            }
                            System.arraycopy(iArr, 0, iArr2, length4, i3);
                            this.zzlM = iArr2;
                            break;
                        } else {
                            this.zzlM = iArr;
                            break;
                        }
                    } else {
                        continue;
                    }
                case 82:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i4 = 0;
                    while (adg.zzLK() > 0) {
                        switch (adg.zzLF()) {
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
                            case 16:
                            case 17:
                                i4++;
                                break;
                        }
                    }
                    if (i4 != 0) {
                        adg.zzcp(position3);
                        int length5 = this.zzlM == null ? 0 : this.zzlM.length;
                        int[] iArr3 = new int[(i4 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzlM, 0, iArr3, 0, length5);
                        }
                        while (adg.zzLK() > 0) {
                            int position4 = adg.getPosition();
                            int zzLF3 = adg.zzLF();
                            switch (zzLF3) {
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
                                case 16:
                                case 17:
                                    iArr3[length5] = zzLF3;
                                    length5++;
                                    break;
                                default:
                                    adg.zzcp(position4);
                                    zza(adg, 80);
                                    break;
                            }
                        }
                        this.zzlM = iArr3;
                    }
                    adg.zzco(zzcn);
                    continue;
                case 90:
                    int zzb5 = ads.zzb(adg, 90);
                    int length6 = this.zzlL == null ? 0 : this.zzlL.length;
                    zzbr[] zzbrArr4 = new zzbr[(zzb5 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzlL, 0, zzbrArr4, 0, length6);
                    }
                    while (length6 < zzbrArr4.length - 1) {
                        zzbrArr4[length6] = new zzbr();
                        adg.zza(zzbrArr4[length6]);
                        adg.zzLA();
                        length6++;
                    }
                    zzbrArr4[length6] = new zzbr();
                    adg.zza(zzbrArr4[length6]);
                    this.zzlL = zzbrArr4;
                    continue;
                case 96:
                    this.zzlK = adg.zzLD();
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
        adh.zzr(1, this.type);
        if (this.string != null && !this.string.equals("")) {
            adh.zzl(2, this.string);
        }
        if (this.zzlE != null && this.zzlE.length > 0) {
            for (zzbr zzbr : this.zzlE) {
                if (zzbr != null) {
                    adh.zza(3, (adp) zzbr);
                }
            }
        }
        if (this.zzlF != null && this.zzlF.length > 0) {
            for (zzbr zzbr2 : this.zzlF) {
                if (zzbr2 != null) {
                    adh.zza(4, (adp) zzbr2);
                }
            }
        }
        if (this.zzlG != null && this.zzlG.length > 0) {
            for (zzbr zzbr3 : this.zzlG) {
                if (zzbr3 != null) {
                    adh.zza(5, (adp) zzbr3);
                }
            }
        }
        if (this.zzlH != null && !this.zzlH.equals("")) {
            adh.zzl(6, this.zzlH);
        }
        if (this.zzlI != null && !this.zzlI.equals("")) {
            adh.zzl(7, this.zzlI);
        }
        if (this.zzlJ != 0) {
            adh.zzb(8, this.zzlJ);
        }
        if (this.zzlN) {
            adh.zzk(9, this.zzlN);
        }
        if (this.zzlM != null && this.zzlM.length > 0) {
            for (int zzr : this.zzlM) {
                adh.zzr(10, zzr);
            }
        }
        if (this.zzlL != null && this.zzlL.length > 0) {
            for (zzbr zzbr4 : this.zzlL) {
                if (zzbr4 != null) {
                    adh.zza(11, (adp) zzbr4);
                }
            }
        }
        if (this.zzlK) {
            adh.zzk(12, this.zzlK);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn() + adh.zzs(1, this.type);
        if (this.string != null && !this.string.equals("")) {
            zzn += adh.zzm(2, this.string);
        }
        if (this.zzlE != null && this.zzlE.length > 0) {
            int i = zzn;
            for (zzbr zzbr : this.zzlE) {
                if (zzbr != null) {
                    i += adh.zzb(3, (adp) zzbr);
                }
            }
            zzn = i;
        }
        if (this.zzlF != null && this.zzlF.length > 0) {
            int i2 = zzn;
            for (zzbr zzbr2 : this.zzlF) {
                if (zzbr2 != null) {
                    i2 += adh.zzb(4, (adp) zzbr2);
                }
            }
            zzn = i2;
        }
        if (this.zzlG != null && this.zzlG.length > 0) {
            int i3 = zzn;
            for (zzbr zzbr3 : this.zzlG) {
                if (zzbr3 != null) {
                    i3 += adh.zzb(5, (adp) zzbr3);
                }
            }
            zzn = i3;
        }
        if (this.zzlH != null && !this.zzlH.equals("")) {
            zzn += adh.zzm(6, this.zzlH);
        }
        if (this.zzlI != null && !this.zzlI.equals("")) {
            zzn += adh.zzm(7, this.zzlI);
        }
        if (this.zzlJ != 0) {
            zzn += adh.zze(8, this.zzlJ);
        }
        if (this.zzlN) {
            zzn += adh.zzct(9) + 1;
        }
        if (this.zzlM != null && this.zzlM.length > 0) {
            int i4 = 0;
            for (int zzcr : this.zzlM) {
                i4 += adh.zzcr(zzcr);
            }
            zzn = zzn + i4 + (this.zzlM.length * 1);
        }
        if (this.zzlL != null && this.zzlL.length > 0) {
            for (zzbr zzbr4 : this.zzlL) {
                if (zzbr4 != null) {
                    zzn += adh.zzb(11, (adp) zzbr4);
                }
            }
        }
        return this.zzlK ? zzn + adh.zzct(12) + 1 : zzn;
    }
}
