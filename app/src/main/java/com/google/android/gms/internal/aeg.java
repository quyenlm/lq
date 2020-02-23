package com.google.android.gms.internal;

import java.io.IOException;

public final class aeg extends adj<aeg> implements Cloneable {
    private String[] zzctG = ads.EMPTY_STRING_ARRAY;
    private String[] zzctH = ads.EMPTY_STRING_ARRAY;
    private int[] zzctI = ads.zzcsC;
    private long[] zzctJ = ads.zzcsD;
    private long[] zzctK = ads.zzcsD;

    public aeg() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMa */
    public aeg clone() {
        try {
            aeg aeg = (aeg) super.clone();
            if (this.zzctG != null && this.zzctG.length > 0) {
                aeg.zzctG = (String[]) this.zzctG.clone();
            }
            if (this.zzctH != null && this.zzctH.length > 0) {
                aeg.zzctH = (String[]) this.zzctH.clone();
            }
            if (this.zzctI != null && this.zzctI.length > 0) {
                aeg.zzctI = (int[]) this.zzctI.clone();
            }
            if (this.zzctJ != null && this.zzctJ.length > 0) {
                aeg.zzctJ = (long[]) this.zzctJ.clone();
            }
            if (this.zzctK != null && this.zzctK.length > 0) {
                aeg.zzctK = (long[]) this.zzctK.clone();
            }
            return aeg;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aeg)) {
            return false;
        }
        aeg aeg = (aeg) obj;
        if (!adn.equals((Object[]) this.zzctG, (Object[]) aeg.zzctG)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzctH, (Object[]) aeg.zzctH)) {
            return false;
        }
        if (!adn.equals(this.zzctI, aeg.zzctI)) {
            return false;
        }
        if (!adn.equals(this.zzctJ, aeg.zzctJ)) {
            return false;
        }
        if (!adn.equals(this.zzctK, aeg.zzctK)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aeg.zzcso == null || aeg.zzcso.isEmpty() : this.zzcso.equals(aeg.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzctG)) * 31) + adn.hashCode((Object[]) this.zzctH)) * 31) + adn.hashCode(this.zzctI)) * 31) + adn.hashCode(this.zzctJ)) * 31) + adn.hashCode(this.zzctK)) * 31);
    }

    public final /* synthetic */ adj zzLN() throws CloneNotSupportedException {
        return (aeg) clone();
    }

    public final /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (aeg) clone();
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzctG == null ? 0 : this.zzctG.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzctG, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = adg.readString();
                        adg.zzLA();
                        length++;
                    }
                    strArr[length] = adg.readString();
                    this.zzctG = strArr;
                    continue;
                case 18:
                    int zzb2 = ads.zzb(adg, 18);
                    int length2 = this.zzctH == null ? 0 : this.zzctH.length;
                    String[] strArr2 = new String[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzctH, 0, strArr2, 0, length2);
                    }
                    while (length2 < strArr2.length - 1) {
                        strArr2[length2] = adg.readString();
                        adg.zzLA();
                        length2++;
                    }
                    strArr2[length2] = adg.readString();
                    this.zzctH = strArr2;
                    continue;
                case 24:
                    int zzb3 = ads.zzb(adg, 24);
                    int length3 = this.zzctI == null ? 0 : this.zzctI.length;
                    int[] iArr = new int[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzctI, 0, iArr, 0, length3);
                    }
                    while (length3 < iArr.length - 1) {
                        iArr[length3] = adg.zzLC();
                        adg.zzLA();
                        length3++;
                    }
                    iArr[length3] = adg.zzLC();
                    this.zzctI = iArr;
                    continue;
                case 26:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLC();
                        i++;
                    }
                    adg.zzcp(position);
                    int length4 = this.zzctI == null ? 0 : this.zzctI.length;
                    int[] iArr2 = new int[(i + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzctI, 0, iArr2, 0, length4);
                    }
                    while (length4 < iArr2.length) {
                        iArr2[length4] = adg.zzLC();
                        length4++;
                    }
                    this.zzctI = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 32:
                    int zzb4 = ads.zzb(adg, 32);
                    int length5 = this.zzctJ == null ? 0 : this.zzctJ.length;
                    long[] jArr = new long[(zzb4 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzctJ, 0, jArr, 0, length5);
                    }
                    while (length5 < jArr.length - 1) {
                        jArr[length5] = adg.zzLB();
                        adg.zzLA();
                        length5++;
                    }
                    jArr[length5] = adg.zzLB();
                    this.zzctJ = jArr;
                    continue;
                case 34:
                    int zzcn2 = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i2 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLB();
                        i2++;
                    }
                    adg.zzcp(position2);
                    int length6 = this.zzctJ == null ? 0 : this.zzctJ.length;
                    long[] jArr2 = new long[(i2 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzctJ, 0, jArr2, 0, length6);
                    }
                    while (length6 < jArr2.length) {
                        jArr2[length6] = adg.zzLB();
                        length6++;
                    }
                    this.zzctJ = jArr2;
                    adg.zzco(zzcn2);
                    continue;
                case 40:
                    int zzb5 = ads.zzb(adg, 40);
                    int length7 = this.zzctK == null ? 0 : this.zzctK.length;
                    long[] jArr3 = new long[(zzb5 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzctK, 0, jArr3, 0, length7);
                    }
                    while (length7 < jArr3.length - 1) {
                        jArr3[length7] = adg.zzLB();
                        adg.zzLA();
                        length7++;
                    }
                    jArr3[length7] = adg.zzLB();
                    this.zzctK = jArr3;
                    continue;
                case 42:
                    int zzcn3 = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i3 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLB();
                        i3++;
                    }
                    adg.zzcp(position3);
                    int length8 = this.zzctK == null ? 0 : this.zzctK.length;
                    long[] jArr4 = new long[(i3 + length8)];
                    if (length8 != 0) {
                        System.arraycopy(this.zzctK, 0, jArr4, 0, length8);
                    }
                    while (length8 < jArr4.length) {
                        jArr4[length8] = adg.zzLB();
                        length8++;
                    }
                    this.zzctK = jArr4;
                    adg.zzco(zzcn3);
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
        if (this.zzctG != null && this.zzctG.length > 0) {
            for (String str : this.zzctG) {
                if (str != null) {
                    adh.zzl(1, str);
                }
            }
        }
        if (this.zzctH != null && this.zzctH.length > 0) {
            for (String str2 : this.zzctH) {
                if (str2 != null) {
                    adh.zzl(2, str2);
                }
            }
        }
        if (this.zzctI != null && this.zzctI.length > 0) {
            for (int zzr : this.zzctI) {
                adh.zzr(3, zzr);
            }
        }
        if (this.zzctJ != null && this.zzctJ.length > 0) {
            for (long zzb : this.zzctJ) {
                adh.zzb(4, zzb);
            }
        }
        if (this.zzctK != null && this.zzctK.length > 0) {
            for (long zzb2 : this.zzctK) {
                adh.zzb(5, zzb2);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzctG == null || this.zzctG.length <= 0) {
            i = zzn;
        } else {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzctG) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            i = zzn + i2 + (i3 * 1);
        }
        if (this.zzctH != null && this.zzctH.length > 0) {
            int i4 = 0;
            int i5 = 0;
            for (String str2 : this.zzctH) {
                if (str2 != null) {
                    i5++;
                    i4 += adh.zzhQ(str2);
                }
            }
            i = i + i4 + (i5 * 1);
        }
        if (this.zzctI != null && this.zzctI.length > 0) {
            int i6 = 0;
            for (int zzcr : this.zzctI) {
                i6 += adh.zzcr(zzcr);
            }
            i = i + i6 + (this.zzctI.length * 1);
        }
        if (this.zzctJ != null && this.zzctJ.length > 0) {
            int i7 = 0;
            for (long zzaP : this.zzctJ) {
                i7 += adh.zzaP(zzaP);
            }
            i = i + i7 + (this.zzctJ.length * 1);
        }
        if (this.zzctK == null || this.zzctK.length <= 0) {
            return i;
        }
        int i8 = 0;
        for (long zzaP2 : this.zzctK) {
            i8 += adh.zzaP(zzaP2);
        }
        return i + i8 + (this.zzctK.length * 1);
    }
}
