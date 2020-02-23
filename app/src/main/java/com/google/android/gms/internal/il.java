package com.google.android.gms.internal;

import java.io.IOException;

public final class il extends adj<il> {
    public String[] zzbUS = ads.EMPTY_STRING_ARRAY;
    public int[] zzbUT = ads.zzcsC;
    public byte[][] zzbUU = ads.zzcsH;

    public il() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static il zzz(byte[] bArr) throws ado {
        return (il) adp.zza(new il(), bArr);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof il)) {
            return false;
        }
        il ilVar = (il) obj;
        if (!adn.equals((Object[]) this.zzbUS, (Object[]) ilVar.zzbUS)) {
            return false;
        }
        if (!adn.equals(this.zzbUT, ilVar.zzbUT)) {
            return false;
        }
        if (!adn.zza(this.zzbUU, ilVar.zzbUU)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? ilVar.zzcso == null || ilVar.zzcso.isEmpty() : this.zzcso.equals(ilVar.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzbUS)) * 31) + adn.hashCode(this.zzbUT)) * 31) + adn.zzc(this.zzbUU)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzbUS == null ? 0 : this.zzbUS.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbUS, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = adg.readString();
                        adg.zzLA();
                        length++;
                    }
                    strArr[length] = adg.readString();
                    this.zzbUS = strArr;
                    continue;
                case 16:
                    int zzb2 = ads.zzb(adg, 16);
                    int length2 = this.zzbUT == null ? 0 : this.zzbUT.length;
                    int[] iArr = new int[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzbUT, 0, iArr, 0, length2);
                    }
                    while (length2 < iArr.length - 1) {
                        iArr[length2] = adg.zzLF();
                        adg.zzLA();
                        length2++;
                    }
                    iArr[length2] = adg.zzLF();
                    this.zzbUT = iArr;
                    continue;
                case 18:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i++;
                    }
                    adg.zzcp(position);
                    int length3 = this.zzbUT == null ? 0 : this.zzbUT.length;
                    int[] iArr2 = new int[(i + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzbUT, 0, iArr2, 0, length3);
                    }
                    while (length3 < iArr2.length) {
                        iArr2[length3] = adg.zzLF();
                        length3++;
                    }
                    this.zzbUT = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 26:
                    int zzb3 = ads.zzb(adg, 26);
                    int length4 = this.zzbUU == null ? 0 : this.zzbUU.length;
                    byte[][] bArr = new byte[(zzb3 + length4)][];
                    if (length4 != 0) {
                        System.arraycopy(this.zzbUU, 0, bArr, 0, length4);
                    }
                    while (length4 < bArr.length - 1) {
                        bArr[length4] = adg.readBytes();
                        adg.zzLA();
                        length4++;
                    }
                    bArr[length4] = adg.readBytes();
                    this.zzbUU = bArr;
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
        if (this.zzbUS != null && this.zzbUS.length > 0) {
            for (String str : this.zzbUS) {
                if (str != null) {
                    adh.zzl(1, str);
                }
            }
        }
        if (this.zzbUT != null && this.zzbUT.length > 0) {
            for (int zzr : this.zzbUT) {
                adh.zzr(2, zzr);
            }
        }
        if (this.zzbUU != null && this.zzbUU.length > 0) {
            for (byte[] bArr : this.zzbUU) {
                if (bArr != null) {
                    adh.zzb(3, bArr);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzbUS == null || this.zzbUS.length <= 0) {
            i = zzn;
        } else {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzbUS) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            i = zzn + i2 + (i3 * 1);
        }
        if (this.zzbUT != null && this.zzbUT.length > 0) {
            int i4 = 0;
            for (int zzcr : this.zzbUT) {
                i4 += adh.zzcr(zzcr);
            }
            i = i + i4 + (this.zzbUT.length * 1);
        }
        if (this.zzbUU == null || this.zzbUU.length <= 0) {
            return i;
        }
        int i5 = 0;
        int i6 = 0;
        for (byte[] bArr : this.zzbUU) {
            if (bArr != null) {
                i6++;
                i5 += adh.zzJ(bArr);
            }
        }
        return i + i5 + (i6 * 1);
    }
}
