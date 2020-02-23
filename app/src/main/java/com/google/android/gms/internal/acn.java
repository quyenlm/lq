package com.google.android.gms.internal;

import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;

public final class acn extends adj<acn> {
    private static volatile acn[] zzcqM;
    public int type = 0;
    public acn[] zzcqN = zzLp();
    public add zzcqO = null;
    public acs zzcqP = null;
    private acw zzcqQ = null;
    public ach zzcqR = null;
    private acz zzcqS = null;
    private acx zzcqT = null;
    private acv zzcqU = null;
    public aci zzcqV = null;
    public acj zzcqW = null;
    private act zzcqX = null;
    private ada zzcqY = null;
    private adf zzcqZ = null;
    public ade zzcra = null;
    private acq zzcrb = null;
    private acu zzcrc = null;
    private acy zzcrd = null;
    public adb zzcre = null;
    public add zzcrf = null;

    public acn() {
        this.zzcsx = -1;
    }

    private static acn[] zzLp() {
        if (zzcqM == null) {
            synchronized (adn.zzcsw) {
                if (zzcqM == null) {
                    zzcqM = new acn[0];
                }
            }
        }
        return zzcqM;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acn)) {
            return false;
        }
        acn acn = (acn) obj;
        if (this.type != acn.type) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcqN, (Object[]) acn.zzcqN)) {
            return false;
        }
        if (this.zzcqO == null) {
            if (acn.zzcqO != null) {
                return false;
            }
        } else if (!this.zzcqO.equals(acn.zzcqO)) {
            return false;
        }
        if (this.zzcqP == null) {
            if (acn.zzcqP != null) {
                return false;
            }
        } else if (!this.zzcqP.equals(acn.zzcqP)) {
            return false;
        }
        if (this.zzcqQ == null) {
            if (acn.zzcqQ != null) {
                return false;
            }
        } else if (!this.zzcqQ.equals(acn.zzcqQ)) {
            return false;
        }
        if (this.zzcqR == null) {
            if (acn.zzcqR != null) {
                return false;
            }
        } else if (!this.zzcqR.equals(acn.zzcqR)) {
            return false;
        }
        if (this.zzcqS == null) {
            if (acn.zzcqS != null) {
                return false;
            }
        } else if (!this.zzcqS.equals(acn.zzcqS)) {
            return false;
        }
        if (this.zzcqT == null) {
            if (acn.zzcqT != null) {
                return false;
            }
        } else if (!this.zzcqT.equals(acn.zzcqT)) {
            return false;
        }
        if (this.zzcqU == null) {
            if (acn.zzcqU != null) {
                return false;
            }
        } else if (!this.zzcqU.equals(acn.zzcqU)) {
            return false;
        }
        if (this.zzcqV == null) {
            if (acn.zzcqV != null) {
                return false;
            }
        } else if (!this.zzcqV.equals(acn.zzcqV)) {
            return false;
        }
        if (this.zzcqW == null) {
            if (acn.zzcqW != null) {
                return false;
            }
        } else if (!this.zzcqW.equals(acn.zzcqW)) {
            return false;
        }
        if (this.zzcqX == null) {
            if (acn.zzcqX != null) {
                return false;
            }
        } else if (!this.zzcqX.equals(acn.zzcqX)) {
            return false;
        }
        if (this.zzcqY == null) {
            if (acn.zzcqY != null) {
                return false;
            }
        } else if (!this.zzcqY.equals(acn.zzcqY)) {
            return false;
        }
        if (this.zzcqZ == null) {
            if (acn.zzcqZ != null) {
                return false;
            }
        } else if (!this.zzcqZ.equals(acn.zzcqZ)) {
            return false;
        }
        if (this.zzcra == null) {
            if (acn.zzcra != null) {
                return false;
            }
        } else if (!this.zzcra.equals(acn.zzcra)) {
            return false;
        }
        if (this.zzcrb == null) {
            if (acn.zzcrb != null) {
                return false;
            }
        } else if (!this.zzcrb.equals(acn.zzcrb)) {
            return false;
        }
        if (this.zzcrc == null) {
            if (acn.zzcrc != null) {
                return false;
            }
        } else if (!this.zzcrc.equals(acn.zzcrc)) {
            return false;
        }
        if (this.zzcrd == null) {
            if (acn.zzcrd != null) {
                return false;
            }
        } else if (!this.zzcrd.equals(acn.zzcrd)) {
            return false;
        }
        if (this.zzcre == null) {
            if (acn.zzcre != null) {
                return false;
            }
        } else if (!this.zzcre.equals(acn.zzcre)) {
            return false;
        }
        if (this.zzcrf == null) {
            if (acn.zzcrf != null) {
                return false;
            }
        } else if (!this.zzcrf.equals(acn.zzcrf)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acn.zzcso == null || acn.zzcso.isEmpty() : this.zzcso.equals(acn.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzcrf == null ? 0 : this.zzcrf.hashCode()) + (((this.zzcre == null ? 0 : this.zzcre.hashCode()) + (((this.zzcrd == null ? 0 : this.zzcrd.hashCode()) + (((this.zzcrc == null ? 0 : this.zzcrc.hashCode()) + (((this.zzcrb == null ? 0 : this.zzcrb.hashCode()) + (((this.zzcra == null ? 0 : this.zzcra.hashCode()) + (((this.zzcqZ == null ? 0 : this.zzcqZ.hashCode()) + (((this.zzcqY == null ? 0 : this.zzcqY.hashCode()) + (((this.zzcqX == null ? 0 : this.zzcqX.hashCode()) + (((this.zzcqW == null ? 0 : this.zzcqW.hashCode()) + (((this.zzcqV == null ? 0 : this.zzcqV.hashCode()) + (((this.zzcqU == null ? 0 : this.zzcqU.hashCode()) + (((this.zzcqT == null ? 0 : this.zzcqT.hashCode()) + (((this.zzcqS == null ? 0 : this.zzcqS.hashCode()) + (((this.zzcqR == null ? 0 : this.zzcqR.hashCode()) + (((this.zzcqQ == null ? 0 : this.zzcqQ.hashCode()) + (((this.zzcqP == null ? 0 : this.zzcqP.hashCode()) + (((this.zzcqO == null ? 0 : this.zzcqO.hashCode()) + ((((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31) + adn.hashCode((Object[]) this.zzcqN)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
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
                            this.type = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzcqN == null ? 0 : this.zzcqN.length;
                    acn[] acnArr = new acn[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcqN, 0, acnArr, 0, length);
                    }
                    while (length < acnArr.length - 1) {
                        acnArr[length] = new acn();
                        adg.zza(acnArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    acnArr[length] = new acn();
                    adg.zza(acnArr[length]);
                    this.zzcqN = acnArr;
                    continue;
                case 26:
                    if (this.zzcqO == null) {
                        this.zzcqO = new add();
                    }
                    adg.zza(this.zzcqO);
                    continue;
                case 34:
                    if (this.zzcqP == null) {
                        this.zzcqP = new acs();
                    }
                    adg.zza(this.zzcqP);
                    continue;
                case 42:
                    if (this.zzcqQ == null) {
                        this.zzcqQ = new acw();
                    }
                    adg.zza(this.zzcqQ);
                    continue;
                case 50:
                    if (this.zzcqR == null) {
                        this.zzcqR = new ach();
                    }
                    adg.zza(this.zzcqR);
                    continue;
                case 58:
                    if (this.zzcqS == null) {
                        this.zzcqS = new acz();
                    }
                    adg.zza(this.zzcqS);
                    continue;
                case 66:
                    if (this.zzcqT == null) {
                        this.zzcqT = new acx();
                    }
                    adg.zza(this.zzcqT);
                    continue;
                case 74:
                    if (this.zzcqU == null) {
                        this.zzcqU = new acv();
                    }
                    adg.zza(this.zzcqU);
                    continue;
                case 82:
                    if (this.zzcqV == null) {
                        this.zzcqV = new aci();
                    }
                    adg.zza(this.zzcqV);
                    continue;
                case 90:
                    if (this.zzcqW == null) {
                        this.zzcqW = new acj();
                    }
                    adg.zza(this.zzcqW);
                    continue;
                case 98:
                    if (this.zzcqX == null) {
                        this.zzcqX = new act();
                    }
                    adg.zza(this.zzcqX);
                    continue;
                case 106:
                    if (this.zzcqY == null) {
                        this.zzcqY = new ada();
                    }
                    adg.zza(this.zzcqY);
                    continue;
                case 114:
                    if (this.zzcqZ == null) {
                        this.zzcqZ = new adf();
                    }
                    adg.zza(this.zzcqZ);
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR:
                    if (this.zzcra == null) {
                        this.zzcra = new ade();
                    }
                    adg.zza(this.zzcra);
                    continue;
                case 130:
                    if (this.zzcrb == null) {
                        this.zzcrb = new acq();
                    }
                    adg.zza(this.zzcrb);
                    continue;
                case 138:
                    if (this.zzcrc == null) {
                        this.zzcrc = new acu();
                    }
                    adg.zza(this.zzcrc);
                    continue;
                case 146:
                    if (this.zzcrd == null) {
                        this.zzcrd = new acy();
                    }
                    adg.zza(this.zzcrd);
                    continue;
                case 154:
                    if (this.zzcre == null) {
                        this.zzcre = new adb();
                    }
                    adg.zza(this.zzcre);
                    continue;
                case 162:
                    if (this.zzcrf == null) {
                        this.zzcrf = new add();
                    }
                    adg.zza(this.zzcrf);
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
        if (this.type != 0) {
            adh.zzr(1, this.type);
        }
        if (this.zzcqN != null && this.zzcqN.length > 0) {
            for (acn acn : this.zzcqN) {
                if (acn != null) {
                    adh.zza(2, (adp) acn);
                }
            }
        }
        if (this.zzcqO != null) {
            adh.zza(3, (adp) this.zzcqO);
        }
        if (this.zzcqP != null) {
            adh.zza(4, (adp) this.zzcqP);
        }
        if (this.zzcqQ != null) {
            adh.zza(5, (adp) this.zzcqQ);
        }
        if (this.zzcqR != null) {
            adh.zza(6, (adp) this.zzcqR);
        }
        if (this.zzcqS != null) {
            adh.zza(7, (adp) this.zzcqS);
        }
        if (this.zzcqT != null) {
            adh.zza(8, (adp) this.zzcqT);
        }
        if (this.zzcqU != null) {
            adh.zza(9, (adp) this.zzcqU);
        }
        if (this.zzcqV != null) {
            adh.zza(10, (adp) this.zzcqV);
        }
        if (this.zzcqW != null) {
            adh.zza(11, (adp) this.zzcqW);
        }
        if (this.zzcqX != null) {
            adh.zza(12, (adp) this.zzcqX);
        }
        if (this.zzcqY != null) {
            adh.zza(13, (adp) this.zzcqY);
        }
        if (this.zzcqZ != null) {
            adh.zza(14, (adp) this.zzcqZ);
        }
        if (this.zzcra != null) {
            adh.zza(15, (adp) this.zzcra);
        }
        if (this.zzcrb != null) {
            adh.zza(16, (adp) this.zzcrb);
        }
        if (this.zzcrc != null) {
            adh.zza(17, (adp) this.zzcrc);
        }
        if (this.zzcrd != null) {
            adh.zza(18, (adp) this.zzcrd);
        }
        if (this.zzcre != null) {
            adh.zza(19, (adp) this.zzcre);
        }
        if (this.zzcrf != null) {
            adh.zza(20, (adp) this.zzcrf);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.type != 0) {
            zzn += adh.zzs(1, this.type);
        }
        if (this.zzcqN != null && this.zzcqN.length > 0) {
            int i = zzn;
            for (acn acn : this.zzcqN) {
                if (acn != null) {
                    i += adh.zzb(2, (adp) acn);
                }
            }
            zzn = i;
        }
        if (this.zzcqO != null) {
            zzn += adh.zzb(3, (adp) this.zzcqO);
        }
        if (this.zzcqP != null) {
            zzn += adh.zzb(4, (adp) this.zzcqP);
        }
        if (this.zzcqQ != null) {
            zzn += adh.zzb(5, (adp) this.zzcqQ);
        }
        if (this.zzcqR != null) {
            zzn += adh.zzb(6, (adp) this.zzcqR);
        }
        if (this.zzcqS != null) {
            zzn += adh.zzb(7, (adp) this.zzcqS);
        }
        if (this.zzcqT != null) {
            zzn += adh.zzb(8, (adp) this.zzcqT);
        }
        if (this.zzcqU != null) {
            zzn += adh.zzb(9, (adp) this.zzcqU);
        }
        if (this.zzcqV != null) {
            zzn += adh.zzb(10, (adp) this.zzcqV);
        }
        if (this.zzcqW != null) {
            zzn += adh.zzb(11, (adp) this.zzcqW);
        }
        if (this.zzcqX != null) {
            zzn += adh.zzb(12, (adp) this.zzcqX);
        }
        if (this.zzcqY != null) {
            zzn += adh.zzb(13, (adp) this.zzcqY);
        }
        if (this.zzcqZ != null) {
            zzn += adh.zzb(14, (adp) this.zzcqZ);
        }
        if (this.zzcra != null) {
            zzn += adh.zzb(15, (adp) this.zzcra);
        }
        if (this.zzcrb != null) {
            zzn += adh.zzb(16, (adp) this.zzcrb);
        }
        if (this.zzcrc != null) {
            zzn += adh.zzb(17, (adp) this.zzcrc);
        }
        if (this.zzcrd != null) {
            zzn += adh.zzb(18, (adp) this.zzcrd);
        }
        if (this.zzcre != null) {
            zzn += adh.zzb(19, (adp) this.zzcre);
        }
        return this.zzcrf != null ? zzn + adh.zzb(20, (adp) this.zzcrf) : zzn;
    }
}
