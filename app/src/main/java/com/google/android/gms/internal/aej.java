package com.google.android.gms.internal;

import com.beetalk.sdk.ShareConstants;
import com.tencent.imsdk.expansion.downloader.impl.DownloaderService;
import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;
import java.util.Arrays;

public final class aej extends adj<aej> implements Cloneable {
    private String tag = "";
    private boolean zzccZ = false;
    private ael zzcmX = null;
    public long zzctQ = 0;
    public long zzctR = 0;
    private long zzctS = 0;
    public int zzctT = 0;
    private aek[] zzctU = aek.zzMe();
    private byte[] zzctV = ads.zzcsI;
    private aeh zzctW = null;
    public byte[] zzctX = ads.zzcsI;
    private String zzctY = "";
    private String zzctZ = "";
    private aeg zzcua = null;
    private String zzcub = "";
    public long zzcuc = 180000;
    private aei zzcud = null;
    public byte[] zzcue = ads.zzcsI;
    private String zzcuf = "";
    private int zzcug = 0;
    private int[] zzcuh = ads.zzcsC;
    private long zzcui = 0;
    public int zzrB = 0;

    public aej() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMd */
    public final aej clone() {
        try {
            aej aej = (aej) super.clone();
            if (this.zzctU != null && this.zzctU.length > 0) {
                aej.zzctU = new aek[this.zzctU.length];
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= this.zzctU.length) {
                        break;
                    }
                    if (this.zzctU[i2] != null) {
                        aej.zzctU[i2] = (aek) this.zzctU[i2].clone();
                    }
                    i = i2 + 1;
                }
            }
            if (this.zzctW != null) {
                aej.zzctW = (aeh) this.zzctW.clone();
            }
            if (this.zzcua != null) {
                aej.zzcua = (aeg) this.zzcua.clone();
            }
            if (this.zzcud != null) {
                aej.zzcud = (aei) this.zzcud.clone();
            }
            if (this.zzcuh != null && this.zzcuh.length > 0) {
                aej.zzcuh = (int[]) this.zzcuh.clone();
            }
            if (this.zzcmX != null) {
                aej.zzcmX = (ael) this.zzcmX.clone();
            }
            return aej;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aej)) {
            return false;
        }
        aej aej = (aej) obj;
        if (this.zzctQ != aej.zzctQ) {
            return false;
        }
        if (this.zzctR != aej.zzctR) {
            return false;
        }
        if (this.zzctS != aej.zzctS) {
            return false;
        }
        if (this.tag == null) {
            if (aej.tag != null) {
                return false;
            }
        } else if (!this.tag.equals(aej.tag)) {
            return false;
        }
        if (this.zzctT != aej.zzctT) {
            return false;
        }
        if (this.zzrB != aej.zzrB) {
            return false;
        }
        if (this.zzccZ != aej.zzccZ) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzctU, (Object[]) aej.zzctU)) {
            return false;
        }
        if (!Arrays.equals(this.zzctV, aej.zzctV)) {
            return false;
        }
        if (this.zzctW == null) {
            if (aej.zzctW != null) {
                return false;
            }
        } else if (!this.zzctW.equals(aej.zzctW)) {
            return false;
        }
        if (!Arrays.equals(this.zzctX, aej.zzctX)) {
            return false;
        }
        if (this.zzctY == null) {
            if (aej.zzctY != null) {
                return false;
            }
        } else if (!this.zzctY.equals(aej.zzctY)) {
            return false;
        }
        if (this.zzctZ == null) {
            if (aej.zzctZ != null) {
                return false;
            }
        } else if (!this.zzctZ.equals(aej.zzctZ)) {
            return false;
        }
        if (this.zzcua == null) {
            if (aej.zzcua != null) {
                return false;
            }
        } else if (!this.zzcua.equals(aej.zzcua)) {
            return false;
        }
        if (this.zzcub == null) {
            if (aej.zzcub != null) {
                return false;
            }
        } else if (!this.zzcub.equals(aej.zzcub)) {
            return false;
        }
        if (this.zzcuc != aej.zzcuc) {
            return false;
        }
        if (this.zzcud == null) {
            if (aej.zzcud != null) {
                return false;
            }
        } else if (!this.zzcud.equals(aej.zzcud)) {
            return false;
        }
        if (!Arrays.equals(this.zzcue, aej.zzcue)) {
            return false;
        }
        if (this.zzcuf == null) {
            if (aej.zzcuf != null) {
                return false;
            }
        } else if (!this.zzcuf.equals(aej.zzcuf)) {
            return false;
        }
        if (this.zzcug != aej.zzcug) {
            return false;
        }
        if (!adn.equals(this.zzcuh, aej.zzcuh)) {
            return false;
        }
        if (this.zzcui != aej.zzcui) {
            return false;
        }
        if (this.zzcmX == null) {
            if (aej.zzcmX != null) {
                return false;
            }
        } else if (!this.zzcmX.equals(aej.zzcmX)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aej.zzcso == null || aej.zzcso.isEmpty() : this.zzcso.equals(aej.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcmX == null ? 0 : this.zzcmX.hashCode()) + (((((((((this.zzcuf == null ? 0 : this.zzcuf.hashCode()) + (((((this.zzcud == null ? 0 : this.zzcud.hashCode()) + (((((this.zzcub == null ? 0 : this.zzcub.hashCode()) + (((this.zzcua == null ? 0 : this.zzcua.hashCode()) + (((this.zzctZ == null ? 0 : this.zzctZ.hashCode()) + (((this.zzctY == null ? 0 : this.zzctY.hashCode()) + (((((this.zzctW == null ? 0 : this.zzctW.hashCode()) + (((((((this.zzccZ ? 1231 : 1237) + (((((((this.tag == null ? 0 : this.tag.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + ((int) (this.zzctQ ^ (this.zzctQ >>> 32)))) * 31) + ((int) (this.zzctR ^ (this.zzctR >>> 32)))) * 31) + ((int) (this.zzctS ^ (this.zzctS >>> 32)))) * 31)) * 31) + this.zzctT) * 31) + this.zzrB) * 31)) * 31) + adn.hashCode((Object[]) this.zzctU)) * 31) + Arrays.hashCode(this.zzctV)) * 31)) * 31) + Arrays.hashCode(this.zzctX)) * 31)) * 31)) * 31)) * 31)) * 31) + ((int) (this.zzcuc ^ (this.zzcuc >>> 32)))) * 31)) * 31) + Arrays.hashCode(this.zzcue)) * 31)) * 31) + this.zzcug) * 31) + adn.hashCode(this.zzcuh)) * 31) + ((int) (this.zzcui ^ (this.zzcui >>> 32)))) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adj zzLN() throws CloneNotSupportedException {
        return (aej) clone();
    }

    public final /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (aej) clone();
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzctQ = adg.zzLB();
                    continue;
                case 18:
                    this.tag = adg.readString();
                    continue;
                case 26:
                    int zzb = ads.zzb(adg, 26);
                    int length = this.zzctU == null ? 0 : this.zzctU.length;
                    aek[] aekArr = new aek[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzctU, 0, aekArr, 0, length);
                    }
                    while (length < aekArr.length - 1) {
                        aekArr[length] = new aek();
                        adg.zza(aekArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    aekArr[length] = new aek();
                    adg.zza(aekArr[length]);
                    this.zzctU = aekArr;
                    continue;
                case 34:
                    this.zzctV = adg.readBytes();
                    continue;
                case 50:
                    this.zzctX = adg.readBytes();
                    continue;
                case 58:
                    if (this.zzcua == null) {
                        this.zzcua = new aeg();
                    }
                    adg.zza(this.zzcua);
                    continue;
                case 66:
                    this.zzctY = adg.readString();
                    continue;
                case 74:
                    if (this.zzctW == null) {
                        this.zzctW = new aeh();
                    }
                    adg.zza(this.zzctW);
                    continue;
                case 80:
                    this.zzccZ = adg.zzLD();
                    continue;
                case 88:
                    this.zzctT = adg.zzLC();
                    continue;
                case 96:
                    this.zzrB = adg.zzLC();
                    continue;
                case 106:
                    this.zzctZ = adg.readString();
                    continue;
                case 114:
                    this.zzcub = adg.readString();
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR:
                    this.zzcuc = adg.zzLE();
                    continue;
                case 130:
                    if (this.zzcud == null) {
                        this.zzcud = new aei();
                    }
                    adg.zza(this.zzcud);
                    continue;
                case 136:
                    this.zzctR = adg.zzLB();
                    continue;
                case 146:
                    this.zzcue = adg.readBytes();
                    continue;
                case 152:
                    int position = adg.getPosition();
                    int zzLC = adg.zzLC();
                    switch (zzLC) {
                        case 0:
                        case 1:
                        case 2:
                            this.zzcug = zzLC;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 160:
                    int zzb2 = ads.zzb(adg, 160);
                    int length2 = this.zzcuh == null ? 0 : this.zzcuh.length;
                    int[] iArr = new int[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcuh, 0, iArr, 0, length2);
                    }
                    while (length2 < iArr.length - 1) {
                        iArr[length2] = adg.zzLC();
                        adg.zzLA();
                        length2++;
                    }
                    iArr[length2] = adg.zzLC();
                    this.zzcuh = iArr;
                    continue;
                case 162:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLC();
                        i++;
                    }
                    adg.zzcp(position2);
                    int length3 = this.zzcuh == null ? 0 : this.zzcuh.length;
                    int[] iArr2 = new int[(i + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzcuh, 0, iArr2, 0, length3);
                    }
                    while (length3 < iArr2.length) {
                        iArr2[length3] = adg.zzLC();
                        length3++;
                    }
                    this.zzcuh = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 168:
                    this.zzctS = adg.zzLB();
                    continue;
                case 176:
                    this.zzcui = adg.zzLB();
                    continue;
                case ShareConstants.ERROR_CODE.GG_RESULT_REFRESH_FAIL_ERROR:
                    if (this.zzcmX == null) {
                        this.zzcmX = new ael();
                    }
                    adg.zza(this.zzcmX);
                    continue;
                case DownloaderService.STATUS_WAITING_TO_RETRY:
                    this.zzcuf = adg.readString();
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
        if (this.zzctQ != 0) {
            adh.zzb(1, this.zzctQ);
        }
        if (this.tag != null && !this.tag.equals("")) {
            adh.zzl(2, this.tag);
        }
        if (this.zzctU != null && this.zzctU.length > 0) {
            for (aek aek : this.zzctU) {
                if (aek != null) {
                    adh.zza(3, (adp) aek);
                }
            }
        }
        if (!Arrays.equals(this.zzctV, ads.zzcsI)) {
            adh.zzb(4, this.zzctV);
        }
        if (!Arrays.equals(this.zzctX, ads.zzcsI)) {
            adh.zzb(6, this.zzctX);
        }
        if (this.zzcua != null) {
            adh.zza(7, (adp) this.zzcua);
        }
        if (this.zzctY != null && !this.zzctY.equals("")) {
            adh.zzl(8, this.zzctY);
        }
        if (this.zzctW != null) {
            adh.zza(9, (adp) this.zzctW);
        }
        if (this.zzccZ) {
            adh.zzk(10, this.zzccZ);
        }
        if (this.zzctT != 0) {
            adh.zzr(11, this.zzctT);
        }
        if (this.zzrB != 0) {
            adh.zzr(12, this.zzrB);
        }
        if (this.zzctZ != null && !this.zzctZ.equals("")) {
            adh.zzl(13, this.zzctZ);
        }
        if (this.zzcub != null && !this.zzcub.equals("")) {
            adh.zzl(14, this.zzcub);
        }
        if (this.zzcuc != 180000) {
            adh.zzd(15, this.zzcuc);
        }
        if (this.zzcud != null) {
            adh.zza(16, (adp) this.zzcud);
        }
        if (this.zzctR != 0) {
            adh.zzb(17, this.zzctR);
        }
        if (!Arrays.equals(this.zzcue, ads.zzcsI)) {
            adh.zzb(18, this.zzcue);
        }
        if (this.zzcug != 0) {
            adh.zzr(19, this.zzcug);
        }
        if (this.zzcuh != null && this.zzcuh.length > 0) {
            for (int zzr : this.zzcuh) {
                adh.zzr(20, zzr);
            }
        }
        if (this.zzctS != 0) {
            adh.zzb(21, this.zzctS);
        }
        if (this.zzcui != 0) {
            adh.zzb(22, this.zzcui);
        }
        if (this.zzcmX != null) {
            adh.zza(23, (adp) this.zzcmX);
        }
        if (this.zzcuf != null && !this.zzcuf.equals("")) {
            adh.zzl(24, this.zzcuf);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzctQ != 0) {
            zzn += adh.zze(1, this.zzctQ);
        }
        if (this.tag != null && !this.tag.equals("")) {
            zzn += adh.zzm(2, this.tag);
        }
        if (this.zzctU != null && this.zzctU.length > 0) {
            int i = zzn;
            for (aek aek : this.zzctU) {
                if (aek != null) {
                    i += adh.zzb(3, (adp) aek);
                }
            }
            zzn = i;
        }
        if (!Arrays.equals(this.zzctV, ads.zzcsI)) {
            zzn += adh.zzc(4, this.zzctV);
        }
        if (!Arrays.equals(this.zzctX, ads.zzcsI)) {
            zzn += adh.zzc(6, this.zzctX);
        }
        if (this.zzcua != null) {
            zzn += adh.zzb(7, (adp) this.zzcua);
        }
        if (this.zzctY != null && !this.zzctY.equals("")) {
            zzn += adh.zzm(8, this.zzctY);
        }
        if (this.zzctW != null) {
            zzn += adh.zzb(9, (adp) this.zzctW);
        }
        if (this.zzccZ) {
            zzn += adh.zzct(10) + 1;
        }
        if (this.zzctT != 0) {
            zzn += adh.zzs(11, this.zzctT);
        }
        if (this.zzrB != 0) {
            zzn += adh.zzs(12, this.zzrB);
        }
        if (this.zzctZ != null && !this.zzctZ.equals("")) {
            zzn += adh.zzm(13, this.zzctZ);
        }
        if (this.zzcub != null && !this.zzcub.equals("")) {
            zzn += adh.zzm(14, this.zzcub);
        }
        if (this.zzcuc != 180000) {
            zzn += adh.zzf(15, this.zzcuc);
        }
        if (this.zzcud != null) {
            zzn += adh.zzb(16, (adp) this.zzcud);
        }
        if (this.zzctR != 0) {
            zzn += adh.zze(17, this.zzctR);
        }
        if (!Arrays.equals(this.zzcue, ads.zzcsI)) {
            zzn += adh.zzc(18, this.zzcue);
        }
        if (this.zzcug != 0) {
            zzn += adh.zzs(19, this.zzcug);
        }
        if (this.zzcuh != null && this.zzcuh.length > 0) {
            int i2 = 0;
            for (int zzcr : this.zzcuh) {
                i2 += adh.zzcr(zzcr);
            }
            zzn = zzn + i2 + (this.zzcuh.length * 2);
        }
        if (this.zzctS != 0) {
            zzn += adh.zze(21, this.zzctS);
        }
        if (this.zzcui != 0) {
            zzn += adh.zze(22, this.zzcui);
        }
        if (this.zzcmX != null) {
            zzn += adh.zzb(23, (adp) this.zzcmX);
        }
        return (this.zzcuf == null || this.zzcuf.equals("")) ? zzn : zzn + adh.zzm(24, this.zzcuf);
    }
}
