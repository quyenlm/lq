package com.google.android.gms.internal;

import java.io.IOException;

public final class acw extends adj<acw> {
    private int zzcqq = 0;
    private long zzcqr = 0;
    private long zzcrr = 0;
    private int[] zzcrv = ads.zzcsC;
    private String[] zzcrw = ads.EMPTY_STRING_ARRAY;
    private String[] zzcrx = ads.EMPTY_STRING_ARRAY;
    private String zzcry = "";

    public acw() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acw)) {
            return false;
        }
        acw acw = (acw) obj;
        if (this.zzcqq != acw.zzcqq) {
            return false;
        }
        if (this.zzcqr != acw.zzcqr) {
            return false;
        }
        if (!adn.equals(this.zzcrv, acw.zzcrv)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcrw, (Object[]) acw.zzcrw)) {
            return false;
        }
        if (this.zzcrr != acw.zzcrr) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcrx, (Object[]) acw.zzcrx)) {
            return false;
        }
        if (this.zzcry == null) {
            if (acw.zzcry != null) {
                return false;
            }
        } else if (!this.zzcry.equals(acw.zzcry)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acw.zzcso == null || acw.zzcso.isEmpty() : this.zzcso.equals(acw.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcry == null ? 0 : this.zzcry.hashCode()) + ((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + adn.hashCode(this.zzcrv)) * 31) + adn.hashCode((Object[]) this.zzcrw)) * 31) + ((int) (this.zzcrr ^ (this.zzcrr >>> 32)))) * 31) + adn.hashCode((Object[]) this.zzcrx)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
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
                    int length = this.zzcrv == null ? 0 : this.zzcrv.length;
                    int[] iArr = new int[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcrv, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = adg.zzLF();
                        adg.zzLA();
                        length++;
                    }
                    iArr[length] = adg.zzLF();
                    this.zzcrv = iArr;
                    continue;
                case 26:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLF();
                        i++;
                    }
                    adg.zzcp(position2);
                    int length2 = this.zzcrv == null ? 0 : this.zzcrv.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcrv, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = adg.zzLF();
                        length2++;
                    }
                    this.zzcrv = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 34:
                    int zzb2 = ads.zzb(adg, 34);
                    int length3 = this.zzcrw == null ? 0 : this.zzcrw.length;
                    String[] strArr = new String[(zzb2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzcrw, 0, strArr, 0, length3);
                    }
                    while (length3 < strArr.length - 1) {
                        strArr[length3] = adg.readString();
                        adg.zzLA();
                        length3++;
                    }
                    strArr[length3] = adg.readString();
                    this.zzcrw = strArr;
                    continue;
                case 40:
                    this.zzcrr = adg.zzLG();
                    continue;
                case 50:
                    int zzb3 = ads.zzb(adg, 50);
                    int length4 = this.zzcrx == null ? 0 : this.zzcrx.length;
                    String[] strArr2 = new String[(zzb3 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzcrx, 0, strArr2, 0, length4);
                    }
                    while (length4 < strArr2.length - 1) {
                        strArr2[length4] = adg.readString();
                        adg.zzLA();
                        length4++;
                    }
                    strArr2[length4] = adg.readString();
                    this.zzcrx = strArr2;
                    continue;
                case 58:
                    this.zzcry = adg.readString();
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
        if (this.zzcrv != null && this.zzcrv.length > 0) {
            for (int zzr : this.zzcrv) {
                adh.zzr(3, zzr);
            }
        }
        if (this.zzcrw != null && this.zzcrw.length > 0) {
            for (String str : this.zzcrw) {
                if (str != null) {
                    adh.zzl(4, str);
                }
            }
        }
        if (this.zzcrr != 0) {
            adh.zzb(5, this.zzcrr);
        }
        if (this.zzcrx != null && this.zzcrx.length > 0) {
            for (String str2 : this.zzcrx) {
                if (str2 != null) {
                    adh.zzl(6, str2);
                }
            }
        }
        if (this.zzcry != null && !this.zzcry.equals("")) {
            adh.zzl(7, this.zzcry);
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
        if (this.zzcrv != null && this.zzcrv.length > 0) {
            int i = 0;
            for (int zzcr : this.zzcrv) {
                i += adh.zzcr(zzcr);
            }
            zzn = zzn + i + (this.zzcrv.length * 1);
        }
        if (this.zzcrw != null && this.zzcrw.length > 0) {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzcrw) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            zzn = zzn + i2 + (i3 * 1);
        }
        if (this.zzcrr != 0) {
            zzn += adh.zze(5, this.zzcrr);
        }
        if (this.zzcrx != null && this.zzcrx.length > 0) {
            int i4 = 0;
            int i5 = 0;
            for (String str2 : this.zzcrx) {
                if (str2 != null) {
                    i5++;
                    i4 += adh.zzhQ(str2);
                }
            }
            zzn = zzn + i4 + (i5 * 1);
        }
        return (this.zzcry == null || this.zzcry.equals("")) ? zzn : zzn + adh.zzm(7, this.zzcry);
    }
}
