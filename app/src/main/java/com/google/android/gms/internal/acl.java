package com.google.android.gms.internal;

import com.google.android.gms.games.GamesActivityResultCodes;
import com.tencent.midas.oversea.comm.APGlobalInfo;
import java.io.IOException;

public final class acl extends adj<acl> {
    private static adk<Object, acl> zzcqB = adk.zza(11, acl.class, 855033290);
    private static final acl[] zzcqC = new acl[0];
    public String zzcqD = "";
    public aco zzcqE = null;
    private int zzcqF = 0;
    private int zzcqG = 0;
    private int zzcqH = 0;
    private adc zzcqI = null;
    private acm zzcqJ = null;
    private String[] zzcqK = ads.EMPTY_STRING_ARRAY;
    private acr zzcqL = null;

    public acl() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acl)) {
            return false;
        }
        acl acl = (acl) obj;
        if (this.zzcqD == null) {
            if (acl.zzcqD != null) {
                return false;
            }
        } else if (!this.zzcqD.equals(acl.zzcqD)) {
            return false;
        }
        if (this.zzcqE == null) {
            if (acl.zzcqE != null) {
                return false;
            }
        } else if (!this.zzcqE.equals(acl.zzcqE)) {
            return false;
        }
        if (this.zzcqF != acl.zzcqF) {
            return false;
        }
        if (this.zzcqG != acl.zzcqG) {
            return false;
        }
        if (this.zzcqH != acl.zzcqH) {
            return false;
        }
        if (this.zzcqI == null) {
            if (acl.zzcqI != null) {
                return false;
            }
        } else if (!this.zzcqI.equals(acl.zzcqI)) {
            return false;
        }
        if (this.zzcqJ == null) {
            if (acl.zzcqJ != null) {
                return false;
            }
        } else if (!this.zzcqJ.equals(acl.zzcqJ)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcqK, (Object[]) acl.zzcqK)) {
            return false;
        }
        if (this.zzcqL == null) {
            if (acl.zzcqL != null) {
                return false;
            }
        } else if (!this.zzcqL.equals(acl.zzcqL)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acl.zzcso == null || acl.zzcso.isEmpty() : this.zzcso.equals(acl.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcqL == null ? 0 : this.zzcqL.hashCode()) + (((((this.zzcqJ == null ? 0 : this.zzcqJ.hashCode()) + (((this.zzcqI == null ? 0 : this.zzcqI.hashCode()) + (((((((((this.zzcqE == null ? 0 : this.zzcqE.hashCode()) + (((this.zzcqD == null ? 0 : this.zzcqD.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + this.zzcqF) * 31) + this.zzcqG) * 31) + this.zzcqH) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzcqK)) * 31)) * 31;
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
                    this.zzcqD = adg.readString();
                    continue;
                case 18:
                    if (this.zzcqE == null) {
                        this.zzcqE = new aco();
                    }
                    adg.zza(this.zzcqE);
                    continue;
                case 32:
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
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            this.zzcqF = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 40:
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
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
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 34:
                        case 35:
                        case 36:
                        case 37:
                        case 38:
                        case 39:
                        case 40:
                        case 41:
                        case 42:
                        case 43:
                        case 44:
                        case 45:
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                        case 10000:
                        case 10001:
                        case 10002:
                        case 10003:
                        case 10004:
                        case 10005:
                        case 10006:
                        case GamesActivityResultCodes.RESULT_SEND_REQUEST_FAILED:
                        case 10008:
                        case APGlobalInfo.RET_PAYSESSIONVALID:
                        case 99999:
                            this.zzcqG = zzLF2;
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 48:
                    int position3 = adg.getPosition();
                    int zzLF3 = adg.zzLF();
                    switch (zzLF3) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.zzcqH = zzLF3;
                            break;
                        default:
                            adg.zzcp(position3);
                            zza(adg, zzLA);
                            continue;
                    }
                case 58:
                    if (this.zzcqI == null) {
                        this.zzcqI = new adc();
                    }
                    adg.zza(this.zzcqI);
                    continue;
                case 66:
                    if (this.zzcqJ == null) {
                        this.zzcqJ = new acm();
                    }
                    adg.zza(this.zzcqJ);
                    continue;
                case 74:
                    int zzb = ads.zzb(adg, 74);
                    int length = this.zzcqK == null ? 0 : this.zzcqK.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcqK, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = adg.readString();
                        adg.zzLA();
                        length++;
                    }
                    strArr[length] = adg.readString();
                    this.zzcqK = strArr;
                    continue;
                case 82:
                    if (this.zzcqL == null) {
                        this.zzcqL = new acr();
                    }
                    adg.zza(this.zzcqL);
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
        if (this.zzcqD != null && !this.zzcqD.equals("")) {
            adh.zzl(1, this.zzcqD);
        }
        if (this.zzcqE != null) {
            adh.zza(2, (adp) this.zzcqE);
        }
        if (this.zzcqF != 0) {
            adh.zzr(4, this.zzcqF);
        }
        if (this.zzcqG != 0) {
            adh.zzr(5, this.zzcqG);
        }
        if (this.zzcqH != 0) {
            adh.zzr(6, this.zzcqH);
        }
        if (this.zzcqI != null) {
            adh.zza(7, (adp) this.zzcqI);
        }
        if (this.zzcqJ != null) {
            adh.zza(8, (adp) this.zzcqJ);
        }
        if (this.zzcqK != null && this.zzcqK.length > 0) {
            for (String str : this.zzcqK) {
                if (str != null) {
                    adh.zzl(9, str);
                }
            }
        }
        if (this.zzcqL != null) {
            adh.zza(10, (adp) this.zzcqL);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzcqD != null && !this.zzcqD.equals("")) {
            zzn += adh.zzm(1, this.zzcqD);
        }
        if (this.zzcqE != null) {
            zzn += adh.zzb(2, (adp) this.zzcqE);
        }
        if (this.zzcqF != 0) {
            zzn += adh.zzs(4, this.zzcqF);
        }
        if (this.zzcqG != 0) {
            zzn += adh.zzs(5, this.zzcqG);
        }
        if (this.zzcqH != 0) {
            zzn += adh.zzs(6, this.zzcqH);
        }
        if (this.zzcqI != null) {
            zzn += adh.zzb(7, (adp) this.zzcqI);
        }
        if (this.zzcqJ != null) {
            zzn += adh.zzb(8, (adp) this.zzcqJ);
        }
        if (this.zzcqK != null && this.zzcqK.length > 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i2 < this.zzcqK.length) {
                String str = this.zzcqK[i2];
                if (str != null) {
                    i4++;
                    i = adh.zzhQ(str) + i3;
                } else {
                    i = i3;
                }
                i2++;
                i3 = i;
            }
            zzn = zzn + i3 + (i4 * 1);
        }
        return this.zzcqL != null ? zzn + adh.zzb(10, (adp) this.zzcqL) : zzn;
    }
}
