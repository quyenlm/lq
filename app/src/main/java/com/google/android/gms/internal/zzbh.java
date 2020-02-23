package com.google.android.gms.internal;

import java.io.IOException;

public interface zzbh {

    public static final class zza extends adj<zza> {
        public static final adk<zzbr, zza> zzlo = adk.zza(11, zza.class, 810);
        private static final zza[] zzlp = new zza[0];
        public int[] zzlq = ads.zzcsC;
        public int[] zzlr = ads.zzcsC;
        public int[] zzls = ads.zzcsC;
        private int zzlt = 0;
        public int[] zzlu = ads.zzcsC;
        public int zzlv = 0;
        private int zzlw = 0;

        public zza() {
            this.zzcso = null;
            this.zzcsx = -1;
        }

        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zza)) {
                return false;
            }
            zza zza = (zza) obj;
            if (!adn.equals(this.zzlq, zza.zzlq)) {
                return false;
            }
            if (!adn.equals(this.zzlr, zza.zzlr)) {
                return false;
            }
            if (!adn.equals(this.zzls, zza.zzls)) {
                return false;
            }
            if (this.zzlt != zza.zzlt) {
                return false;
            }
            if (!adn.equals(this.zzlu, zza.zzlu)) {
                return false;
            }
            if (this.zzlv != zza.zzlv) {
                return false;
            }
            if (this.zzlw != zza.zzlw) {
                return false;
            }
            return (this.zzcso == null || this.zzcso.isEmpty()) ? zza.zzcso == null || zza.zzcso.isEmpty() : this.zzcso.equals(zza.zzcso);
        }

        public final int hashCode() {
            return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode(this.zzlq)) * 31) + adn.hashCode(this.zzlr)) * 31) + adn.hashCode(this.zzls)) * 31) + this.zzlt) * 31) + adn.hashCode(this.zzlu)) * 31) + this.zzlv) * 31) + this.zzlw) * 31);
        }

        public final /* synthetic */ adp zza(adg adg) throws IOException {
            while (true) {
                int zzLA = adg.zzLA();
                switch (zzLA) {
                    case 0:
                        break;
                    case 8:
                        int zzb = ads.zzb(adg, 8);
                        int length = this.zzlq == null ? 0 : this.zzlq.length;
                        int[] iArr = new int[(zzb + length)];
                        if (length != 0) {
                            System.arraycopy(this.zzlq, 0, iArr, 0, length);
                        }
                        while (length < iArr.length - 1) {
                            iArr[length] = adg.zzLF();
                            adg.zzLA();
                            length++;
                        }
                        iArr[length] = adg.zzLF();
                        this.zzlq = iArr;
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
                        int length2 = this.zzlq == null ? 0 : this.zzlq.length;
                        int[] iArr2 = new int[(i + length2)];
                        if (length2 != 0) {
                            System.arraycopy(this.zzlq, 0, iArr2, 0, length2);
                        }
                        while (length2 < iArr2.length) {
                            iArr2[length2] = adg.zzLF();
                            length2++;
                        }
                        this.zzlq = iArr2;
                        adg.zzco(zzcn);
                        continue;
                    case 16:
                        int zzb2 = ads.zzb(adg, 16);
                        int length3 = this.zzlr == null ? 0 : this.zzlr.length;
                        int[] iArr3 = new int[(zzb2 + length3)];
                        if (length3 != 0) {
                            System.arraycopy(this.zzlr, 0, iArr3, 0, length3);
                        }
                        while (length3 < iArr3.length - 1) {
                            iArr3[length3] = adg.zzLF();
                            adg.zzLA();
                            length3++;
                        }
                        iArr3[length3] = adg.zzLF();
                        this.zzlr = iArr3;
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
                        int length4 = this.zzlr == null ? 0 : this.zzlr.length;
                        int[] iArr4 = new int[(i2 + length4)];
                        if (length4 != 0) {
                            System.arraycopy(this.zzlr, 0, iArr4, 0, length4);
                        }
                        while (length4 < iArr4.length) {
                            iArr4[length4] = adg.zzLF();
                            length4++;
                        }
                        this.zzlr = iArr4;
                        adg.zzco(zzcn2);
                        continue;
                    case 24:
                        int zzb3 = ads.zzb(adg, 24);
                        int length5 = this.zzls == null ? 0 : this.zzls.length;
                        int[] iArr5 = new int[(zzb3 + length5)];
                        if (length5 != 0) {
                            System.arraycopy(this.zzls, 0, iArr5, 0, length5);
                        }
                        while (length5 < iArr5.length - 1) {
                            iArr5[length5] = adg.zzLF();
                            adg.zzLA();
                            length5++;
                        }
                        iArr5[length5] = adg.zzLF();
                        this.zzls = iArr5;
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
                        int length6 = this.zzls == null ? 0 : this.zzls.length;
                        int[] iArr6 = new int[(i3 + length6)];
                        if (length6 != 0) {
                            System.arraycopy(this.zzls, 0, iArr6, 0, length6);
                        }
                        while (length6 < iArr6.length) {
                            iArr6[length6] = adg.zzLF();
                            length6++;
                        }
                        this.zzls = iArr6;
                        adg.zzco(zzcn3);
                        continue;
                    case 32:
                        this.zzlt = adg.zzLF();
                        continue;
                    case 40:
                        int zzb4 = ads.zzb(adg, 40);
                        int length7 = this.zzlu == null ? 0 : this.zzlu.length;
                        int[] iArr7 = new int[(zzb4 + length7)];
                        if (length7 != 0) {
                            System.arraycopy(this.zzlu, 0, iArr7, 0, length7);
                        }
                        while (length7 < iArr7.length - 1) {
                            iArr7[length7] = adg.zzLF();
                            adg.zzLA();
                            length7++;
                        }
                        iArr7[length7] = adg.zzLF();
                        this.zzlu = iArr7;
                        continue;
                    case 42:
                        int zzcn4 = adg.zzcn(adg.zzLF());
                        int position4 = adg.getPosition();
                        int i4 = 0;
                        while (adg.zzLK() > 0) {
                            adg.zzLF();
                            i4++;
                        }
                        adg.zzcp(position4);
                        int length8 = this.zzlu == null ? 0 : this.zzlu.length;
                        int[] iArr8 = new int[(i4 + length8)];
                        if (length8 != 0) {
                            System.arraycopy(this.zzlu, 0, iArr8, 0, length8);
                        }
                        while (length8 < iArr8.length) {
                            iArr8[length8] = adg.zzLF();
                            length8++;
                        }
                        this.zzlu = iArr8;
                        adg.zzco(zzcn4);
                        continue;
                    case 48:
                        this.zzlv = adg.zzLF();
                        continue;
                    case 56:
                        this.zzlw = adg.zzLF();
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
            if (this.zzlq != null && this.zzlq.length > 0) {
                for (int zzr : this.zzlq) {
                    adh.zzr(1, zzr);
                }
            }
            if (this.zzlr != null && this.zzlr.length > 0) {
                for (int zzr2 : this.zzlr) {
                    adh.zzr(2, zzr2);
                }
            }
            if (this.zzls != null && this.zzls.length > 0) {
                for (int zzr3 : this.zzls) {
                    adh.zzr(3, zzr3);
                }
            }
            if (this.zzlt != 0) {
                adh.zzr(4, this.zzlt);
            }
            if (this.zzlu != null && this.zzlu.length > 0) {
                for (int zzr4 : this.zzlu) {
                    adh.zzr(5, zzr4);
                }
            }
            if (this.zzlv != 0) {
                adh.zzr(6, this.zzlv);
            }
            if (this.zzlw != 0) {
                adh.zzr(7, this.zzlw);
            }
            super.zza(adh);
        }

        /* access modifiers changed from: protected */
        public final int zzn() {
            int i;
            int zzn = super.zzn();
            if (this.zzlq == null || this.zzlq.length <= 0) {
                i = zzn;
            } else {
                int i2 = 0;
                for (int zzcr : this.zzlq) {
                    i2 += adh.zzcr(zzcr);
                }
                i = zzn + i2 + (this.zzlq.length * 1);
            }
            if (this.zzlr != null && this.zzlr.length > 0) {
                int i3 = 0;
                for (int zzcr2 : this.zzlr) {
                    i3 += adh.zzcr(zzcr2);
                }
                i = i + i3 + (this.zzlr.length * 1);
            }
            if (this.zzls != null && this.zzls.length > 0) {
                int i4 = 0;
                for (int zzcr3 : this.zzls) {
                    i4 += adh.zzcr(zzcr3);
                }
                i = i + i4 + (this.zzls.length * 1);
            }
            if (this.zzlt != 0) {
                i += adh.zzs(4, this.zzlt);
            }
            if (this.zzlu != null && this.zzlu.length > 0) {
                int i5 = 0;
                for (int zzcr4 : this.zzlu) {
                    i5 += adh.zzcr(zzcr4);
                }
                i = i + i5 + (this.zzlu.length * 1);
            }
            if (this.zzlv != 0) {
                i += adh.zzs(6, this.zzlv);
            }
            return this.zzlw != 0 ? i + adh.zzs(7, this.zzlw) : i;
        }
    }
}
