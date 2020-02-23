package com.google.android.gms.internal;

import com.tencent.smtt.sdk.TbsListener;
import java.io.IOException;

public final class adt extends adj<adt> {
    public String url = null;
    public Integer zzcsJ = null;
    private Integer zzcsK = null;
    public String zzcsL = null;
    private String zzcsM = null;
    public adu zzcsN = null;
    public aeb[] zzcsO = aeb.zzLX();
    public String zzcsP = null;
    public aea zzcsQ = null;
    private Boolean zzcsR = null;
    private String[] zzcsS = ads.EMPTY_STRING_ARRAY;
    private String zzcsT = null;
    private Boolean zzcsU = null;
    private Boolean zzcsV = null;
    private byte[] zzcsW = null;
    public aec zzcsX = null;

    public adt() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.url = adg.readString();
                    continue;
                case 18:
                    this.zzcsL = adg.readString();
                    continue;
                case 26:
                    this.zzcsM = adg.readString();
                    continue;
                case 34:
                    int zzb = ads.zzb(adg, 34);
                    int length = this.zzcsO == null ? 0 : this.zzcsO.length;
                    aeb[] aebArr = new aeb[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcsO, 0, aebArr, 0, length);
                    }
                    while (length < aebArr.length - 1) {
                        aebArr[length] = new aeb();
                        adg.zza(aebArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    aebArr[length] = new aeb();
                    adg.zza(aebArr[length]);
                    this.zzcsO = aebArr;
                    continue;
                case 40:
                    this.zzcsR = Boolean.valueOf(adg.zzLD());
                    continue;
                case 50:
                    int zzb2 = ads.zzb(adg, 50);
                    int length2 = this.zzcsS == null ? 0 : this.zzcsS.length;
                    String[] strArr = new String[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcsS, 0, strArr, 0, length2);
                    }
                    while (length2 < strArr.length - 1) {
                        strArr[length2] = adg.readString();
                        adg.zzLA();
                        length2++;
                    }
                    strArr[length2] = adg.readString();
                    this.zzcsS = strArr;
                    continue;
                case 58:
                    this.zzcsT = adg.readString();
                    continue;
                case 64:
                    this.zzcsU = Boolean.valueOf(adg.zzLD());
                    continue;
                case 72:
                    this.zzcsV = Boolean.valueOf(adg.zzLD());
                    continue;
                case 80:
                    int position = adg.getPosition();
                    int zzLC = adg.zzLC();
                    switch (zzLC) {
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
                            this.zzcsJ = Integer.valueOf(zzLC);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 88:
                    int position2 = adg.getPosition();
                    int zzLC2 = adg.zzLC();
                    switch (zzLC2) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            this.zzcsK = Integer.valueOf(zzLC2);
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 98:
                    if (this.zzcsN == null) {
                        this.zzcsN = new adu();
                    }
                    adg.zza(this.zzcsN);
                    continue;
                case 106:
                    this.zzcsP = adg.readString();
                    continue;
                case 114:
                    if (this.zzcsQ == null) {
                        this.zzcsQ = new aea();
                    }
                    adg.zza(this.zzcsQ);
                    continue;
                case TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR:
                    this.zzcsW = adg.readBytes();
                    continue;
                case 138:
                    if (this.zzcsX == null) {
                        this.zzcsX = new aec();
                    }
                    adg.zza(this.zzcsX);
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
        if (this.url != null) {
            adh.zzl(1, this.url);
        }
        if (this.zzcsL != null) {
            adh.zzl(2, this.zzcsL);
        }
        if (this.zzcsM != null) {
            adh.zzl(3, this.zzcsM);
        }
        if (this.zzcsO != null && this.zzcsO.length > 0) {
            for (aeb aeb : this.zzcsO) {
                if (aeb != null) {
                    adh.zza(4, (adp) aeb);
                }
            }
        }
        if (this.zzcsR != null) {
            adh.zzk(5, this.zzcsR.booleanValue());
        }
        if (this.zzcsS != null && this.zzcsS.length > 0) {
            for (String str : this.zzcsS) {
                if (str != null) {
                    adh.zzl(6, str);
                }
            }
        }
        if (this.zzcsT != null) {
            adh.zzl(7, this.zzcsT);
        }
        if (this.zzcsU != null) {
            adh.zzk(8, this.zzcsU.booleanValue());
        }
        if (this.zzcsV != null) {
            adh.zzk(9, this.zzcsV.booleanValue());
        }
        if (this.zzcsJ != null) {
            adh.zzr(10, this.zzcsJ.intValue());
        }
        if (this.zzcsK != null) {
            adh.zzr(11, this.zzcsK.intValue());
        }
        if (this.zzcsN != null) {
            adh.zza(12, (adp) this.zzcsN);
        }
        if (this.zzcsP != null) {
            adh.zzl(13, this.zzcsP);
        }
        if (this.zzcsQ != null) {
            adh.zza(14, (adp) this.zzcsQ);
        }
        if (this.zzcsW != null) {
            adh.zzb(15, this.zzcsW);
        }
        if (this.zzcsX != null) {
            adh.zza(17, (adp) this.zzcsX);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.url != null) {
            zzn += adh.zzm(1, this.url);
        }
        if (this.zzcsL != null) {
            zzn += adh.zzm(2, this.zzcsL);
        }
        if (this.zzcsM != null) {
            zzn += adh.zzm(3, this.zzcsM);
        }
        if (this.zzcsO != null && this.zzcsO.length > 0) {
            int i = zzn;
            for (aeb aeb : this.zzcsO) {
                if (aeb != null) {
                    i += adh.zzb(4, (adp) aeb);
                }
            }
            zzn = i;
        }
        if (this.zzcsR != null) {
            this.zzcsR.booleanValue();
            zzn += adh.zzct(5) + 1;
        }
        if (this.zzcsS != null && this.zzcsS.length > 0) {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzcsS) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            zzn = zzn + i2 + (i3 * 1);
        }
        if (this.zzcsT != null) {
            zzn += adh.zzm(7, this.zzcsT);
        }
        if (this.zzcsU != null) {
            this.zzcsU.booleanValue();
            zzn += adh.zzct(8) + 1;
        }
        if (this.zzcsV != null) {
            this.zzcsV.booleanValue();
            zzn += adh.zzct(9) + 1;
        }
        if (this.zzcsJ != null) {
            zzn += adh.zzs(10, this.zzcsJ.intValue());
        }
        if (this.zzcsK != null) {
            zzn += adh.zzs(11, this.zzcsK.intValue());
        }
        if (this.zzcsN != null) {
            zzn += adh.zzb(12, (adp) this.zzcsN);
        }
        if (this.zzcsP != null) {
            zzn += adh.zzm(13, this.zzcsP);
        }
        if (this.zzcsQ != null) {
            zzn += adh.zzb(14, (adp) this.zzcsQ);
        }
        if (this.zzcsW != null) {
            zzn += adh.zzc(15, this.zzcsW);
        }
        return this.zzcsX != null ? zzn + adh.zzb(17, (adp) this.zzcsX) : zzn;
    }
}
