package com.google.android.gms.internal;

import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;

public final class zzbn extends adj<zzbn> {
    public String version = "";
    private String[] zzkN = ads.EMPTY_STRING_ARRAY;
    public String[] zzkO = ads.EMPTY_STRING_ARRAY;
    public zzbr[] zzkP = zzbr.zzu();
    public zzbm[] zzkQ = zzbm.zzr();
    public zzbj[] zzkR = zzbj.zzp();
    public zzbj[] zzkS = zzbj.zzp();
    public zzbj[] zzkT = zzbj.zzp();
    public zzbo[] zzkU = zzbo.zzs();
    private String zzkV = "";
    private String zzkW = "";
    private String zzkX = "0";
    private zzbi zzkY = null;
    private float zzkZ = 0.0f;
    private boolean zzla = false;
    private String[] zzlb = ads.EMPTY_STRING_ARRAY;
    public int zzlc = 0;

    public zzbn() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbn)) {
            return false;
        }
        zzbn zzbn = (zzbn) obj;
        if (!adn.equals((Object[]) this.zzkN, (Object[]) zzbn.zzkN)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkO, (Object[]) zzbn.zzkO)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkP, (Object[]) zzbn.zzkP)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkQ, (Object[]) zzbn.zzkQ)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkR, (Object[]) zzbn.zzkR)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkS, (Object[]) zzbn.zzkS)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkT, (Object[]) zzbn.zzkT)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzkU, (Object[]) zzbn.zzkU)) {
            return false;
        }
        if (this.zzkV == null) {
            if (zzbn.zzkV != null) {
                return false;
            }
        } else if (!this.zzkV.equals(zzbn.zzkV)) {
            return false;
        }
        if (this.zzkW == null) {
            if (zzbn.zzkW != null) {
                return false;
            }
        } else if (!this.zzkW.equals(zzbn.zzkW)) {
            return false;
        }
        if (this.zzkX == null) {
            if (zzbn.zzkX != null) {
                return false;
            }
        } else if (!this.zzkX.equals(zzbn.zzkX)) {
            return false;
        }
        if (this.version == null) {
            if (zzbn.version != null) {
                return false;
            }
        } else if (!this.version.equals(zzbn.version)) {
            return false;
        }
        if (this.zzkY == null) {
            if (zzbn.zzkY != null) {
                return false;
            }
        } else if (!this.zzkY.equals(zzbn.zzkY)) {
            return false;
        }
        if (Float.floatToIntBits(this.zzkZ) != Float.floatToIntBits(zzbn.zzkZ)) {
            return false;
        }
        if (this.zzla != zzbn.zzla) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzlb, (Object[]) zzbn.zzlb)) {
            return false;
        }
        if (this.zzlc != zzbn.zzlc) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbn.zzcso == null || zzbn.zzcso.isEmpty() : this.zzcso.equals(zzbn.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((this.zzla ? 1231 : 1237) + (((((this.zzkY == null ? 0 : this.zzkY.hashCode()) + (((this.version == null ? 0 : this.version.hashCode()) + (((this.zzkX == null ? 0 : this.zzkX.hashCode()) + (((this.zzkW == null ? 0 : this.zzkW.hashCode()) + (((this.zzkV == null ? 0 : this.zzkV.hashCode()) + ((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzkN)) * 31) + adn.hashCode((Object[]) this.zzkO)) * 31) + adn.hashCode((Object[]) this.zzkP)) * 31) + adn.hashCode((Object[]) this.zzkQ)) * 31) + adn.hashCode((Object[]) this.zzkR)) * 31) + adn.hashCode((Object[]) this.zzkS)) * 31) + adn.hashCode((Object[]) this.zzkT)) * 31) + adn.hashCode((Object[]) this.zzkU)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31) + Float.floatToIntBits(this.zzkZ)) * 31)) * 31) + adn.hashCode((Object[]) this.zzlb)) * 31) + this.zzlc) * 31;
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
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzkO == null ? 0 : this.zzkO.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzkO, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = adg.readString();
                        adg.zzLA();
                        length++;
                    }
                    strArr[length] = adg.readString();
                    this.zzkO = strArr;
                    continue;
                case 18:
                    int zzb2 = ads.zzb(adg, 18);
                    int length2 = this.zzkP == null ? 0 : this.zzkP.length;
                    zzbr[] zzbrArr = new zzbr[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzkP, 0, zzbrArr, 0, length2);
                    }
                    while (length2 < zzbrArr.length - 1) {
                        zzbrArr[length2] = new zzbr();
                        adg.zza(zzbrArr[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    zzbrArr[length2] = new zzbr();
                    adg.zza(zzbrArr[length2]);
                    this.zzkP = zzbrArr;
                    continue;
                case 26:
                    int zzb3 = ads.zzb(adg, 26);
                    int length3 = this.zzkQ == null ? 0 : this.zzkQ.length;
                    zzbm[] zzbmArr = new zzbm[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzkQ, 0, zzbmArr, 0, length3);
                    }
                    while (length3 < zzbmArr.length - 1) {
                        zzbmArr[length3] = new zzbm();
                        adg.zza(zzbmArr[length3]);
                        adg.zzLA();
                        length3++;
                    }
                    zzbmArr[length3] = new zzbm();
                    adg.zza(zzbmArr[length3]);
                    this.zzkQ = zzbmArr;
                    continue;
                case 34:
                    int zzb4 = ads.zzb(adg, 34);
                    int length4 = this.zzkR == null ? 0 : this.zzkR.length;
                    zzbj[] zzbjArr = new zzbj[(zzb4 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzkR, 0, zzbjArr, 0, length4);
                    }
                    while (length4 < zzbjArr.length - 1) {
                        zzbjArr[length4] = new zzbj();
                        adg.zza(zzbjArr[length4]);
                        adg.zzLA();
                        length4++;
                    }
                    zzbjArr[length4] = new zzbj();
                    adg.zza(zzbjArr[length4]);
                    this.zzkR = zzbjArr;
                    continue;
                case 42:
                    int zzb5 = ads.zzb(adg, 42);
                    int length5 = this.zzkS == null ? 0 : this.zzkS.length;
                    zzbj[] zzbjArr2 = new zzbj[(zzb5 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzkS, 0, zzbjArr2, 0, length5);
                    }
                    while (length5 < zzbjArr2.length - 1) {
                        zzbjArr2[length5] = new zzbj();
                        adg.zza(zzbjArr2[length5]);
                        adg.zzLA();
                        length5++;
                    }
                    zzbjArr2[length5] = new zzbj();
                    adg.zza(zzbjArr2[length5]);
                    this.zzkS = zzbjArr2;
                    continue;
                case 50:
                    int zzb6 = ads.zzb(adg, 50);
                    int length6 = this.zzkT == null ? 0 : this.zzkT.length;
                    zzbj[] zzbjArr3 = new zzbj[(zzb6 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzkT, 0, zzbjArr3, 0, length6);
                    }
                    while (length6 < zzbjArr3.length - 1) {
                        zzbjArr3[length6] = new zzbj();
                        adg.zza(zzbjArr3[length6]);
                        adg.zzLA();
                        length6++;
                    }
                    zzbjArr3[length6] = new zzbj();
                    adg.zza(zzbjArr3[length6]);
                    this.zzkT = zzbjArr3;
                    continue;
                case 58:
                    int zzb7 = ads.zzb(adg, 58);
                    int length7 = this.zzkU == null ? 0 : this.zzkU.length;
                    zzbo[] zzboArr = new zzbo[(zzb7 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzkU, 0, zzboArr, 0, length7);
                    }
                    while (length7 < zzboArr.length - 1) {
                        zzboArr[length7] = new zzbo();
                        adg.zza(zzboArr[length7]);
                        adg.zzLA();
                        length7++;
                    }
                    zzboArr[length7] = new zzbo();
                    adg.zza(zzboArr[length7]);
                    this.zzkU = zzboArr;
                    continue;
                case 74:
                    this.zzkV = adg.readString();
                    continue;
                case 82:
                    this.zzkW = adg.readString();
                    continue;
                case 98:
                    this.zzkX = adg.readString();
                    continue;
                case 106:
                    this.version = adg.readString();
                    continue;
                case 114:
                    if (this.zzkY == null) {
                        this.zzkY = new zzbi();
                    }
                    adg.zza(this.zzkY);
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_THROWABLE:
                    this.zzkZ = Float.intBitsToFloat(adg.zzLH());
                    continue;
                case 130:
                    int zzb8 = ads.zzb(adg, 130);
                    int length8 = this.zzlb == null ? 0 : this.zzlb.length;
                    String[] strArr2 = new String[(zzb8 + length8)];
                    if (length8 != 0) {
                        System.arraycopy(this.zzlb, 0, strArr2, 0, length8);
                    }
                    while (length8 < strArr2.length - 1) {
                        strArr2[length8] = adg.readString();
                        adg.zzLA();
                        length8++;
                    }
                    strArr2[length8] = adg.readString();
                    this.zzlb = strArr2;
                    continue;
                case 136:
                    this.zzlc = adg.zzLF();
                    continue;
                case 144:
                    this.zzla = adg.zzLD();
                    continue;
                case 154:
                    int zzb9 = ads.zzb(adg, 154);
                    int length9 = this.zzkN == null ? 0 : this.zzkN.length;
                    String[] strArr3 = new String[(zzb9 + length9)];
                    if (length9 != 0) {
                        System.arraycopy(this.zzkN, 0, strArr3, 0, length9);
                    }
                    while (length9 < strArr3.length - 1) {
                        strArr3[length9] = adg.readString();
                        adg.zzLA();
                        length9++;
                    }
                    strArr3[length9] = adg.readString();
                    this.zzkN = strArr3;
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
        if (this.zzkO != null && this.zzkO.length > 0) {
            for (String str : this.zzkO) {
                if (str != null) {
                    adh.zzl(1, str);
                }
            }
        }
        if (this.zzkP != null && this.zzkP.length > 0) {
            for (zzbr zzbr : this.zzkP) {
                if (zzbr != null) {
                    adh.zza(2, (adp) zzbr);
                }
            }
        }
        if (this.zzkQ != null && this.zzkQ.length > 0) {
            for (zzbm zzbm : this.zzkQ) {
                if (zzbm != null) {
                    adh.zza(3, (adp) zzbm);
                }
            }
        }
        if (this.zzkR != null && this.zzkR.length > 0) {
            for (zzbj zzbj : this.zzkR) {
                if (zzbj != null) {
                    adh.zza(4, (adp) zzbj);
                }
            }
        }
        if (this.zzkS != null && this.zzkS.length > 0) {
            for (zzbj zzbj2 : this.zzkS) {
                if (zzbj2 != null) {
                    adh.zza(5, (adp) zzbj2);
                }
            }
        }
        if (this.zzkT != null && this.zzkT.length > 0) {
            for (zzbj zzbj3 : this.zzkT) {
                if (zzbj3 != null) {
                    adh.zza(6, (adp) zzbj3);
                }
            }
        }
        if (this.zzkU != null && this.zzkU.length > 0) {
            for (zzbo zzbo : this.zzkU) {
                if (zzbo != null) {
                    adh.zza(7, (adp) zzbo);
                }
            }
        }
        if (this.zzkV != null && !this.zzkV.equals("")) {
            adh.zzl(9, this.zzkV);
        }
        if (this.zzkW != null && !this.zzkW.equals("")) {
            adh.zzl(10, this.zzkW);
        }
        if (this.zzkX != null && !this.zzkX.equals("0")) {
            adh.zzl(12, this.zzkX);
        }
        if (this.version != null && !this.version.equals("")) {
            adh.zzl(13, this.version);
        }
        if (this.zzkY != null) {
            adh.zza(14, (adp) this.zzkY);
        }
        if (Float.floatToIntBits(this.zzkZ) != Float.floatToIntBits(0.0f)) {
            adh.zzc(15, this.zzkZ);
        }
        if (this.zzlb != null && this.zzlb.length > 0) {
            for (String str2 : this.zzlb) {
                if (str2 != null) {
                    adh.zzl(16, str2);
                }
            }
        }
        if (this.zzlc != 0) {
            adh.zzr(17, this.zzlc);
        }
        if (this.zzla) {
            adh.zzk(18, this.zzla);
        }
        if (this.zzkN != null && this.zzkN.length > 0) {
            for (String str3 : this.zzkN) {
                if (str3 != null) {
                    adh.zzl(19, str3);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzkO == null || this.zzkO.length <= 0) {
            i = zzn;
        } else {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzkO) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            i = zzn + i2 + (i3 * 1);
        }
        if (this.zzkP != null && this.zzkP.length > 0) {
            int i4 = i;
            for (zzbr zzbr : this.zzkP) {
                if (zzbr != null) {
                    i4 += adh.zzb(2, (adp) zzbr);
                }
            }
            i = i4;
        }
        if (this.zzkQ != null && this.zzkQ.length > 0) {
            int i5 = i;
            for (zzbm zzbm : this.zzkQ) {
                if (zzbm != null) {
                    i5 += adh.zzb(3, (adp) zzbm);
                }
            }
            i = i5;
        }
        if (this.zzkR != null && this.zzkR.length > 0) {
            int i6 = i;
            for (zzbj zzbj : this.zzkR) {
                if (zzbj != null) {
                    i6 += adh.zzb(4, (adp) zzbj);
                }
            }
            i = i6;
        }
        if (this.zzkS != null && this.zzkS.length > 0) {
            int i7 = i;
            for (zzbj zzbj2 : this.zzkS) {
                if (zzbj2 != null) {
                    i7 += adh.zzb(5, (adp) zzbj2);
                }
            }
            i = i7;
        }
        if (this.zzkT != null && this.zzkT.length > 0) {
            int i8 = i;
            for (zzbj zzbj3 : this.zzkT) {
                if (zzbj3 != null) {
                    i8 += adh.zzb(6, (adp) zzbj3);
                }
            }
            i = i8;
        }
        if (this.zzkU != null && this.zzkU.length > 0) {
            int i9 = i;
            for (zzbo zzbo : this.zzkU) {
                if (zzbo != null) {
                    i9 += adh.zzb(7, (adp) zzbo);
                }
            }
            i = i9;
        }
        if (this.zzkV != null && !this.zzkV.equals("")) {
            i += adh.zzm(9, this.zzkV);
        }
        if (this.zzkW != null && !this.zzkW.equals("")) {
            i += adh.zzm(10, this.zzkW);
        }
        if (this.zzkX != null && !this.zzkX.equals("0")) {
            i += adh.zzm(12, this.zzkX);
        }
        if (this.version != null && !this.version.equals("")) {
            i += adh.zzm(13, this.version);
        }
        if (this.zzkY != null) {
            i += adh.zzb(14, (adp) this.zzkY);
        }
        if (Float.floatToIntBits(this.zzkZ) != Float.floatToIntBits(0.0f)) {
            i += adh.zzct(15) + 4;
        }
        if (this.zzlb != null && this.zzlb.length > 0) {
            int i10 = 0;
            int i11 = 0;
            for (String str2 : this.zzlb) {
                if (str2 != null) {
                    i11++;
                    i10 += adh.zzhQ(str2);
                }
            }
            i = i + i10 + (i11 * 2);
        }
        if (this.zzlc != 0) {
            i += adh.zzs(17, this.zzlc);
        }
        if (this.zzla) {
            i += adh.zzct(18) + 1;
        }
        if (this.zzkN == null || this.zzkN.length <= 0) {
            return i;
        }
        int i12 = 0;
        int i13 = 0;
        for (String str3 : this.zzkN) {
            if (str3 != null) {
                i13++;
                i12 += adh.zzhQ(str3);
            }
        }
        return i + i12 + (i13 * 2);
    }
}
