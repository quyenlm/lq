package com.google.android.gms.internal;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;
import java.util.Arrays;

public final class hh extends adj<hh> {
    public byte[] zzbTM = ads.zzcsI;
    public String zzbTN = "";
    public double zzbTO = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    public float zzbTP = 0.0f;
    public long zzbTQ = 0;
    public int zzbTR = 0;
    public int zzbTS = 0;
    public boolean zzbTT = false;
    public hf[] zzbTU = hf.zzEa();
    public hg[] zzbTV = hg.zzEb();
    public String[] zzbTW = ads.EMPTY_STRING_ARRAY;
    public long[] zzbTX = ads.zzcsD;
    public float[] zzbTY = ads.zzcsE;
    public long zzbTZ = 0;

    public hh() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof hh)) {
            return false;
        }
        hh hhVar = (hh) obj;
        if (!Arrays.equals(this.zzbTM, hhVar.zzbTM)) {
            return false;
        }
        if (this.zzbTN == null) {
            if (hhVar.zzbTN != null) {
                return false;
            }
        } else if (!this.zzbTN.equals(hhVar.zzbTN)) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzbTO) != Double.doubleToLongBits(hhVar.zzbTO)) {
            return false;
        }
        if (Float.floatToIntBits(this.zzbTP) != Float.floatToIntBits(hhVar.zzbTP)) {
            return false;
        }
        if (this.zzbTQ != hhVar.zzbTQ) {
            return false;
        }
        if (this.zzbTR != hhVar.zzbTR) {
            return false;
        }
        if (this.zzbTS != hhVar.zzbTS) {
            return false;
        }
        if (this.zzbTT != hhVar.zzbTT) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbTU, (Object[]) hhVar.zzbTU)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbTV, (Object[]) hhVar.zzbTV)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbTW, (Object[]) hhVar.zzbTW)) {
            return false;
        }
        if (!adn.equals(this.zzbTX, hhVar.zzbTX)) {
            return false;
        }
        if (!adn.equals(this.zzbTY, hhVar.zzbTY)) {
            return false;
        }
        if (this.zzbTZ != hhVar.zzbTZ) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? hhVar.zzcso == null || hhVar.zzcso.isEmpty() : this.zzcso.equals(hhVar.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = (this.zzbTN == null ? 0 : this.zzbTN.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzbTM)) * 31);
        long doubleToLongBits = Double.doubleToLongBits(this.zzbTO);
        int floatToIntBits = ((((((((((((((this.zzbTT ? 1231 : 1237) + (((((((((((hashCode * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + Float.floatToIntBits(this.zzbTP)) * 31) + ((int) (this.zzbTQ ^ (this.zzbTQ >>> 32)))) * 31) + this.zzbTR) * 31) + this.zzbTS) * 31)) * 31) + adn.hashCode((Object[]) this.zzbTU)) * 31) + adn.hashCode((Object[]) this.zzbTV)) * 31) + adn.hashCode((Object[]) this.zzbTW)) * 31) + adn.hashCode(this.zzbTX)) * 31) + adn.hashCode(this.zzbTY)) * 31) + ((int) (this.zzbTZ ^ (this.zzbTZ >>> 32)))) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return floatToIntBits + i;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzbTM = adg.readBytes();
                    continue;
                case 18:
                    this.zzbTN = adg.readString();
                    continue;
                case 25:
                    this.zzbTO = Double.longBitsToDouble(adg.zzLI());
                    continue;
                case 37:
                    this.zzbTP = Float.intBitsToFloat(adg.zzLH());
                    continue;
                case 40:
                    this.zzbTQ = adg.zzLG();
                    continue;
                case 48:
                    this.zzbTR = adg.zzLF();
                    continue;
                case 56:
                    int zzLF = adg.zzLF();
                    this.zzbTS = (-(zzLF & 1)) ^ (zzLF >>> 1);
                    continue;
                case 64:
                    this.zzbTT = adg.zzLD();
                    continue;
                case 74:
                    int zzb = ads.zzb(adg, 74);
                    int length = this.zzbTU == null ? 0 : this.zzbTU.length;
                    hf[] hfVarArr = new hf[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbTU, 0, hfVarArr, 0, length);
                    }
                    while (length < hfVarArr.length - 1) {
                        hfVarArr[length] = new hf();
                        adg.zza(hfVarArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    hfVarArr[length] = new hf();
                    adg.zza(hfVarArr[length]);
                    this.zzbTU = hfVarArr;
                    continue;
                case 82:
                    int zzb2 = ads.zzb(adg, 82);
                    int length2 = this.zzbTV == null ? 0 : this.zzbTV.length;
                    hg[] hgVarArr = new hg[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzbTV, 0, hgVarArr, 0, length2);
                    }
                    while (length2 < hgVarArr.length - 1) {
                        hgVarArr[length2] = new hg();
                        adg.zza(hgVarArr[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    hgVarArr[length2] = new hg();
                    adg.zza(hgVarArr[length2]);
                    this.zzbTV = hgVarArr;
                    continue;
                case 90:
                    int zzb3 = ads.zzb(adg, 90);
                    int length3 = this.zzbTW == null ? 0 : this.zzbTW.length;
                    String[] strArr = new String[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzbTW, 0, strArr, 0, length3);
                    }
                    while (length3 < strArr.length - 1) {
                        strArr[length3] = adg.readString();
                        adg.zzLA();
                        length3++;
                    }
                    strArr[length3] = adg.readString();
                    this.zzbTW = strArr;
                    continue;
                case 96:
                    int zzb4 = ads.zzb(adg, 96);
                    int length4 = this.zzbTX == null ? 0 : this.zzbTX.length;
                    long[] jArr = new long[(zzb4 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzbTX, 0, jArr, 0, length4);
                    }
                    while (length4 < jArr.length - 1) {
                        jArr[length4] = adg.zzLG();
                        adg.zzLA();
                        length4++;
                    }
                    jArr[length4] = adg.zzLG();
                    this.zzbTX = jArr;
                    continue;
                case 98:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLG();
                        i++;
                    }
                    adg.zzcp(position);
                    int length5 = this.zzbTX == null ? 0 : this.zzbTX.length;
                    long[] jArr2 = new long[(i + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzbTX, 0, jArr2, 0, length5);
                    }
                    while (length5 < jArr2.length) {
                        jArr2[length5] = adg.zzLG();
                        length5++;
                    }
                    this.zzbTX = jArr2;
                    adg.zzco(zzcn);
                    continue;
                case 104:
                    this.zzbTZ = adg.zzLG();
                    continue;
                case 114:
                    int zzLF2 = adg.zzLF();
                    int zzcn2 = adg.zzcn(zzLF2);
                    int i2 = zzLF2 / 4;
                    int length6 = this.zzbTY == null ? 0 : this.zzbTY.length;
                    float[] fArr = new float[(i2 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzbTY, 0, fArr, 0, length6);
                    }
                    while (length6 < fArr.length) {
                        fArr[length6] = Float.intBitsToFloat(adg.zzLH());
                        length6++;
                    }
                    this.zzbTY = fArr;
                    adg.zzco(zzcn2);
                    continue;
                case 117:
                    int zzb5 = ads.zzb(adg, 117);
                    int length7 = this.zzbTY == null ? 0 : this.zzbTY.length;
                    float[] fArr2 = new float[(zzb5 + length7)];
                    if (length7 != 0) {
                        System.arraycopy(this.zzbTY, 0, fArr2, 0, length7);
                    }
                    while (length7 < fArr2.length - 1) {
                        fArr2[length7] = Float.intBitsToFloat(adg.zzLH());
                        adg.zzLA();
                        length7++;
                    }
                    fArr2[length7] = Float.intBitsToFloat(adg.zzLH());
                    this.zzbTY = fArr2;
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
        if (!Arrays.equals(this.zzbTM, ads.zzcsI)) {
            adh.zzb(1, this.zzbTM);
        }
        if (this.zzbTN != null && !this.zzbTN.equals("")) {
            adh.zzl(2, this.zzbTN);
        }
        if (Double.doubleToLongBits(this.zzbTO) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            adh.zza(3, this.zzbTO);
        }
        if (Float.floatToIntBits(this.zzbTP) != Float.floatToIntBits(0.0f)) {
            adh.zzc(4, this.zzbTP);
        }
        if (this.zzbTQ != 0) {
            adh.zzb(5, this.zzbTQ);
        }
        if (this.zzbTR != 0) {
            adh.zzr(6, this.zzbTR);
        }
        if (this.zzbTS != 0) {
            int i = this.zzbTS;
            adh.zzt(7, 0);
            adh.zzcu(adh.zzcw(i));
        }
        if (this.zzbTT) {
            adh.zzk(8, this.zzbTT);
        }
        if (this.zzbTU != null && this.zzbTU.length > 0) {
            for (hf hfVar : this.zzbTU) {
                if (hfVar != null) {
                    adh.zza(9, (adp) hfVar);
                }
            }
        }
        if (this.zzbTV != null && this.zzbTV.length > 0) {
            for (hg hgVar : this.zzbTV) {
                if (hgVar != null) {
                    adh.zza(10, (adp) hgVar);
                }
            }
        }
        if (this.zzbTW != null && this.zzbTW.length > 0) {
            for (String str : this.zzbTW) {
                if (str != null) {
                    adh.zzl(11, str);
                }
            }
        }
        if (this.zzbTX != null && this.zzbTX.length > 0) {
            for (long zzb : this.zzbTX) {
                adh.zzb(12, zzb);
            }
        }
        if (this.zzbTZ != 0) {
            adh.zzb(13, this.zzbTZ);
        }
        if (this.zzbTY != null && this.zzbTY.length > 0) {
            for (float zzc : this.zzbTY) {
                adh.zzc(14, zzc);
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (!Arrays.equals(this.zzbTM, ads.zzcsI)) {
            zzn += adh.zzc(1, this.zzbTM);
        }
        if (this.zzbTN != null && !this.zzbTN.equals("")) {
            zzn += adh.zzm(2, this.zzbTN);
        }
        if (Double.doubleToLongBits(this.zzbTO) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            zzn += adh.zzct(3) + 8;
        }
        if (Float.floatToIntBits(this.zzbTP) != Float.floatToIntBits(0.0f)) {
            zzn += adh.zzct(4) + 4;
        }
        if (this.zzbTQ != 0) {
            zzn += adh.zze(5, this.zzbTQ);
        }
        if (this.zzbTR != 0) {
            zzn += adh.zzs(6, this.zzbTR);
        }
        if (this.zzbTS != 0) {
            zzn += adh.zzcv(adh.zzcw(this.zzbTS)) + adh.zzct(7);
        }
        if (this.zzbTT) {
            zzn += adh.zzct(8) + 1;
        }
        if (this.zzbTU != null && this.zzbTU.length > 0) {
            int i = zzn;
            for (hf hfVar : this.zzbTU) {
                if (hfVar != null) {
                    i += adh.zzb(9, (adp) hfVar);
                }
            }
            zzn = i;
        }
        if (this.zzbTV != null && this.zzbTV.length > 0) {
            int i2 = zzn;
            for (hg hgVar : this.zzbTV) {
                if (hgVar != null) {
                    i2 += adh.zzb(10, (adp) hgVar);
                }
            }
            zzn = i2;
        }
        if (this.zzbTW != null && this.zzbTW.length > 0) {
            int i3 = 0;
            int i4 = 0;
            for (String str : this.zzbTW) {
                if (str != null) {
                    i4++;
                    i3 += adh.zzhQ(str);
                }
            }
            zzn = zzn + i3 + (i4 * 1);
        }
        if (this.zzbTX != null && this.zzbTX.length > 0) {
            int i5 = 0;
            for (long zzaP : this.zzbTX) {
                i5 += adh.zzaP(zzaP);
            }
            zzn = zzn + i5 + (this.zzbTX.length * 1);
        }
        if (this.zzbTZ != 0) {
            zzn += adh.zze(13, this.zzbTZ);
        }
        return (this.zzbTY == null || this.zzbTY.length <= 0) ? zzn : zzn + (this.zzbTY.length * 4) + (this.zzbTY.length * 1);
    }
}
