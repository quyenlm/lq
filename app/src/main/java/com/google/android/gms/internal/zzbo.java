package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbo extends adj<zzbo> {
    private static volatile zzbo[] zzld;
    public int[] zzle = ads.zzcsC;
    public int[] zzlf = ads.zzcsC;
    public int[] zzlg = ads.zzcsC;
    public int[] zzlh = ads.zzcsC;
    public int[] zzli = ads.zzcsC;
    public int[] zzlj = ads.zzcsC;
    public int[] zzlk = ads.zzcsC;
    public int[] zzll = ads.zzcsC;
    public int[] zzlm = ads.zzcsC;
    public int[] zzln = ads.zzcsC;

    public zzbo() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzbo[] zzs() {
        if (zzld == null) {
            synchronized (adn.zzcsw) {
                if (zzld == null) {
                    zzld = new zzbo[0];
                }
            }
        }
        return zzld;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbo)) {
            return false;
        }
        zzbo zzbo = (zzbo) obj;
        if (!adn.equals(this.zzle, zzbo.zzle)) {
            return false;
        }
        if (!adn.equals(this.zzlf, zzbo.zzlf)) {
            return false;
        }
        if (!adn.equals(this.zzlg, zzbo.zzlg)) {
            return false;
        }
        if (!adn.equals(this.zzlh, zzbo.zzlh)) {
            return false;
        }
        if (!adn.equals(this.zzli, zzbo.zzli)) {
            return false;
        }
        if (!adn.equals(this.zzlj, zzbo.zzlj)) {
            return false;
        }
        if (!adn.equals(this.zzlk, zzbo.zzlk)) {
            return false;
        }
        if (!adn.equals(this.zzll, zzbo.zzll)) {
            return false;
        }
        if (!adn.equals(this.zzlm, zzbo.zzlm)) {
            return false;
        }
        if (!adn.equals(this.zzln, zzbo.zzln)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbo.zzcso == null || zzbo.zzcso.isEmpty() : this.zzcso.equals(zzbo.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode(this.zzle)) * 31) + adn.hashCode(this.zzlf)) * 31) + adn.hashCode(this.zzlg)) * 31) + adn.hashCode(this.zzlh)) * 31) + adn.hashCode(this.zzli)) * 31) + adn.hashCode(this.zzlj)) * 31) + adn.hashCode(this.zzlk)) * 31) + adn.hashCode(this.zzll)) * 31) + adn.hashCode(this.zzlm)) * 31) + adn.hashCode(this.zzln)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    int zzb = ads.zzb(adg, 8);
                    int length = this.zzle == null ? 0 : this.zzle.length;
                    int[] iArr = new int[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzle, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = adg.zzLF();
                        adg.zzLA();
                        length++;
                    }
                    iArr[length] = adg.zzLF();
                    this.zzle = iArr;
                    continue;
                case 10:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzle == null ? 0 : this.zzle.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzle, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = adg.zzLF();
                        length2++;
                    }
                    this.zzle = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 16:
                    int zzb2 = ads.zzb(adg, 16);
                    int length3 = this.zzlf == null ? 0 : this.zzlf.length;
                    int[] iArr3 = new int[(zzb2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzlf, 0, iArr3, 0, length3);
                    }
                    while (length3 < iArr3.length - 1) {
                        iArr3[length3] = adg.zzLF();
                        adg.zzLA();
                        length3++;
                    }
                    iArr3[length3] = adg.zzLF();
                    this.zzlf = iArr3;
                    continue;
                case 18:
                    int zzcn2 = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i2 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i2++;
                    }
                    adg.zzcp(position2);
                    int length4 = this.zzlf == null ? 0 : this.zzlf.length;
                    int[] iArr4 = new int[(i2 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzlf, 0, iArr4, 0, length4);
                    }
                    while (length4 < iArr4.length) {
                        iArr4[length4] = adg.zzLF();
                        length4++;
                    }
                    this.zzlf = iArr4;
                    adg.zzco(zzcn2);
                    continue;
                case 24:
                    int zzb3 = ads.zzb(adg, 24);
                    int length5 = this.zzlg == null ? 0 : this.zzlg.length;
                    int[] iArr5 = new int[(zzb3 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzlg, 0, iArr5, 0, length5);
                    }
                    while (length5 < iArr5.length - 1) {
                        iArr5[length5] = adg.zzLF();
                        adg.zzLA();
                        length5++;
                    }
                    iArr5[length5] = adg.zzLF();
                    this.zzlg = iArr5;
                    continue;
                case 26:
                    int zzcn3 = adg.zzcn(adg.zzLF());
                    int position3 = adg.getPosition();
                    int i3 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i3++;
                    }
                    adg.zzcp(position3);
                    int length6 = this.zzlg == null ? 0 : this.zzlg.length;
                    int[] iArr6 = new int[(i3 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzlg, 0, iArr6, 0, length6);
                    }
                    while (length6 < iArr6.length) {
                        iArr6[length6] = adg.zzLF();
                        length6++;
                    }
                    this.zzlg = iArr6;
                    adg.zzco(zzcn3);
                    continue;
                case 32:
                    int zzb4 = ads.zzb(adg, 32);
                    int length7 = this.zzlh == null ? 0 : this.zzlh.length;
                    int[] iArr7 = new int[(zzb4 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzlh, 0, iArr7, 0, length7);
                    }
                    while (length7 < iArr7.length - 1) {
                        iArr7[length7] = adg.zzLF();
                        adg.zzLA();
                        length7++;
                    }
                    iArr7[length7] = adg.zzLF();
                    this.zzlh = iArr7;
                    continue;
                case 34:
                    int zzcn4 = adg.zzcn(adg.zzLF());
                    int position4 = adg.getPosition();
                    int i4 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i4++;
                    }
                    adg.zzcp(position4);
                    int length8 = this.zzlh == null ? 0 : this.zzlh.length;
                    int[] iArr8 = new int[(i4 + length8)];
                    if (length8 != 0) {
                        System.arraycopy(this.zzlh, 0, iArr8, 0, length8);
                    }
                    while (length8 < iArr8.length) {
                        iArr8[length8] = adg.zzLF();
                        length8++;
                    }
                    this.zzlh = iArr8;
                    adg.zzco(zzcn4);
                    continue;
                case 40:
                    int zzb5 = ads.zzb(adg, 40);
                    int length9 = this.zzli == null ? 0 : this.zzli.length;
                    int[] iArr9 = new int[(zzb5 + length9)];
                    if (length9 != 0) {
                        System.arraycopy(this.zzli, 0, iArr9, 0, length9);
                    }
                    while (length9 < iArr9.length - 1) {
                        iArr9[length9] = adg.zzLF();
                        adg.zzLA();
                        length9++;
                    }
                    iArr9[length9] = adg.zzLF();
                    this.zzli = iArr9;
                    continue;
                case 42:
                    int zzcn5 = adg.zzcn(adg.zzLF());
                    int position5 = adg.getPosition();
                    int i5 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i5++;
                    }
                    adg.zzcp(position5);
                    int length10 = this.zzli == null ? 0 : this.zzli.length;
                    int[] iArr10 = new int[(i5 + length10)];
                    if (length10 != 0) {
                        System.arraycopy(this.zzli, 0, iArr10, 0, length10);
                    }
                    while (length10 < iArr10.length) {
                        iArr10[length10] = adg.zzLF();
                        length10++;
                    }
                    this.zzli = iArr10;
                    adg.zzco(zzcn5);
                    continue;
                case 48:
                    int zzb6 = ads.zzb(adg, 48);
                    int length11 = this.zzlj == null ? 0 : this.zzlj.length;
                    int[] iArr11 = new int[(zzb6 + length11)];
                    if (length11 != 0) {
                        System.arraycopy(this.zzlj, 0, iArr11, 0, length11);
                    }
                    while (length11 < iArr11.length - 1) {
                        iArr11[length11] = adg.zzLF();
                        adg.zzLA();
                        length11++;
                    }
                    iArr11[length11] = adg.zzLF();
                    this.zzlj = iArr11;
                    continue;
                case 50:
                    int zzcn6 = adg.zzcn(adg.zzLF());
                    int position6 = adg.getPosition();
                    int i6 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i6++;
                    }
                    adg.zzcp(position6);
                    int length12 = this.zzlj == null ? 0 : this.zzlj.length;
                    int[] iArr12 = new int[(i6 + length12)];
                    if (length12 != 0) {
                        System.arraycopy(this.zzlj, 0, iArr12, 0, length12);
                    }
                    while (length12 < iArr12.length) {
                        iArr12[length12] = adg.zzLF();
                        length12++;
                    }
                    this.zzlj = iArr12;
                    adg.zzco(zzcn6);
                    continue;
                case 56:
                    int zzb7 = ads.zzb(adg, 56);
                    int length13 = this.zzlk == null ? 0 : this.zzlk.length;
                    int[] iArr13 = new int[(zzb7 + length13)];
                    if (length13 != 0) {
                        System.arraycopy(this.zzlk, 0, iArr13, 0, length13);
                    }
                    while (length13 < iArr13.length - 1) {
                        iArr13[length13] = adg.zzLF();
                        adg.zzLA();
                        length13++;
                    }
                    iArr13[length13] = adg.zzLF();
                    this.zzlk = iArr13;
                    continue;
                case 58:
                    int zzcn7 = adg.zzcn(adg.zzLF());
                    int position7 = adg.getPosition();
                    int i7 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i7++;
                    }
                    adg.zzcp(position7);
                    int length14 = this.zzlk == null ? 0 : this.zzlk.length;
                    int[] iArr14 = new int[(i7 + length14)];
                    if (length14 != 0) {
                        System.arraycopy(this.zzlk, 0, iArr14, 0, length14);
                    }
                    while (length14 < iArr14.length) {
                        iArr14[length14] = adg.zzLF();
                        length14++;
                    }
                    this.zzlk = iArr14;
                    adg.zzco(zzcn7);
                    continue;
                case 64:
                    int zzb8 = ads.zzb(adg, 64);
                    int length15 = this.zzll == null ? 0 : this.zzll.length;
                    int[] iArr15 = new int[(zzb8 + length15)];
                    if (length15 != 0) {
                        System.arraycopy(this.zzll, 0, iArr15, 0, length15);
                    }
                    while (length15 < iArr15.length - 1) {
                        iArr15[length15] = adg.zzLF();
                        adg.zzLA();
                        length15++;
                    }
                    iArr15[length15] = adg.zzLF();
                    this.zzll = iArr15;
                    continue;
                case 66:
                    int zzcn8 = adg.zzcn(adg.zzLF());
                    int position8 = adg.getPosition();
                    int i8 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i8++;
                    }
                    adg.zzcp(position8);
                    int length16 = this.zzll == null ? 0 : this.zzll.length;
                    int[] iArr16 = new int[(i8 + length16)];
                    if (length16 != 0) {
                        System.arraycopy(this.zzll, 0, iArr16, 0, length16);
                    }
                    while (length16 < iArr16.length) {
                        iArr16[length16] = adg.zzLF();
                        length16++;
                    }
                    this.zzll = iArr16;
                    adg.zzco(zzcn8);
                    continue;
                case 72:
                    int zzb9 = ads.zzb(adg, 72);
                    int length17 = this.zzlm == null ? 0 : this.zzlm.length;
                    int[] iArr17 = new int[(zzb9 + length17)];
                    if (length17 != 0) {
                        System.arraycopy(this.zzlm, 0, iArr17, 0, length17);
                    }
                    while (length17 < iArr17.length - 1) {
                        iArr17[length17] = adg.zzLF();
                        adg.zzLA();
                        length17++;
                    }
                    iArr17[length17] = adg.zzLF();
                    this.zzlm = iArr17;
                    continue;
                case 74:
                    int zzcn9 = adg.zzcn(adg.zzLF());
                    int position9 = adg.getPosition();
                    int i9 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i9++;
                    }
                    adg.zzcp(position9);
                    int length18 = this.zzlm == null ? 0 : this.zzlm.length;
                    int[] iArr18 = new int[(i9 + length18)];
                    if (length18 != 0) {
                        System.arraycopy(this.zzlm, 0, iArr18, 0, length18);
                    }
                    while (length18 < iArr18.length) {
                        iArr18[length18] = adg.zzLF();
                        length18++;
                    }
                    this.zzlm = iArr18;
                    adg.zzco(zzcn9);
                    continue;
                case 80:
                    int zzb10 = ads.zzb(adg, 80);
                    int length19 = this.zzln == null ? 0 : this.zzln.length;
                    int[] iArr19 = new int[(zzb10 + length19)];
                    if (length19 != 0) {
                        System.arraycopy(this.zzln, 0, iArr19, 0, length19);
                    }
                    while (length19 < iArr19.length - 1) {
                        iArr19[length19] = adg.zzLF();
                        adg.zzLA();
                        length19++;
                    }
                    iArr19[length19] = adg.zzLF();
                    this.zzln = iArr19;
                    continue;
                case 82:
                    int zzcn10 = adg.zzcn(adg.zzLF());
                    int position10 = adg.getPosition();
                    int i10 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i10++;
                    }
                    adg.zzcp(position10);
                    int length20 = this.zzln == null ? 0 : this.zzln.length;
                    int[] iArr20 = new int[(i10 + length20)];
                    if (length20 != 0) {
                        System.arraycopy(this.zzln, 0, iArr20, 0, length20);
                    }
                    while (length20 < iArr20.length) {
                        iArr20[length20] = adg.zzLF();
                        length20++;
                    }
                    this.zzln = iArr20;
                    adg.zzco(zzcn10);
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
        if (this.zzle != null && this.zzle.length > 0) {
            for (int zzr : this.zzle) {
                adh.zzr(1, zzr);
            }
        }
        if (this.zzlf != null && this.zzlf.length > 0) {
            for (int zzr2 : this.zzlf) {
                adh.zzr(2, zzr2);
            }
        }
        if (this.zzlg != null && this.zzlg.length > 0) {
            for (int zzr3 : this.zzlg) {
                adh.zzr(3, zzr3);
            }
        }
        if (this.zzlh != null && this.zzlh.length > 0) {
            for (int zzr4 : this.zzlh) {
                adh.zzr(4, zzr4);
            }
        }
        if (this.zzli != null && this.zzli.length > 0) {
            for (int zzr5 : this.zzli) {
                adh.zzr(5, zzr5);
            }
        }
        if (this.zzlj != null && this.zzlj.length > 0) {
            for (int zzr6 : this.zzlj) {
                adh.zzr(6, zzr6);
            }
        }
        if (this.zzlk != null && this.zzlk.length > 0) {
            for (int zzr7 : this.zzlk) {
                adh.zzr(7, zzr7);
            }
        }
        if (this.zzll != null && this.zzll.length > 0) {
            for (int zzr8 : this.zzll) {
                adh.zzr(8, zzr8);
            }
        }
        if (this.zzlm != null && this.zzlm.length > 0) {
            for (int zzr9 : this.zzlm) {
                adh.zzr(9, zzr9);
            }
        }
        if (this.zzln != null && this.zzln.length > 0) {
            for (int zzr10 : this.zzln) {
                adh.zzr(10, zzr10);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzle == null || this.zzle.length <= 0) {
            i = zzn;
        } else {
            int i2 = 0;
            for (int zzcr : this.zzle) {
                i2 += adh.zzcr(zzcr);
            }
            i = zzn + i2 + (this.zzle.length * 1);
        }
        if (this.zzlf != null && this.zzlf.length > 0) {
            int i3 = 0;
            for (int zzcr2 : this.zzlf) {
                i3 += adh.zzcr(zzcr2);
            }
            i = i + i3 + (this.zzlf.length * 1);
        }
        if (this.zzlg != null && this.zzlg.length > 0) {
            int i4 = 0;
            for (int zzcr3 : this.zzlg) {
                i4 += adh.zzcr(zzcr3);
            }
            i = i + i4 + (this.zzlg.length * 1);
        }
        if (this.zzlh != null && this.zzlh.length > 0) {
            int i5 = 0;
            for (int zzcr4 : this.zzlh) {
                i5 += adh.zzcr(zzcr4);
            }
            i = i + i5 + (this.zzlh.length * 1);
        }
        if (this.zzli != null && this.zzli.length > 0) {
            int i6 = 0;
            for (int zzcr5 : this.zzli) {
                i6 += adh.zzcr(zzcr5);
            }
            i = i + i6 + (this.zzli.length * 1);
        }
        if (this.zzlj != null && this.zzlj.length > 0) {
            int i7 = 0;
            for (int zzcr6 : this.zzlj) {
                i7 += adh.zzcr(zzcr6);
            }
            i = i + i7 + (this.zzlj.length * 1);
        }
        if (this.zzlk != null && this.zzlk.length > 0) {
            int i8 = 0;
            for (int zzcr7 : this.zzlk) {
                i8 += adh.zzcr(zzcr7);
            }
            i = i + i8 + (this.zzlk.length * 1);
        }
        if (this.zzll != null && this.zzll.length > 0) {
            int i9 = 0;
            for (int zzcr8 : this.zzll) {
                i9 += adh.zzcr(zzcr8);
            }
            i = i + i9 + (this.zzll.length * 1);
        }
        if (this.zzlm != null && this.zzlm.length > 0) {
            int i10 = 0;
            for (int zzcr9 : this.zzlm) {
                i10 += adh.zzcr(zzcr9);
            }
            i = i + i10 + (this.zzlm.length * 1);
        }
        if (this.zzln == null || this.zzln.length <= 0) {
            return i;
        }
        int i11 = 0;
        for (int zzcr10 : this.zzln) {
            i11 += adh.zzcr(zzcr10);
        }
        return i + i11 + (this.zzln.length * 1);
    }
}
