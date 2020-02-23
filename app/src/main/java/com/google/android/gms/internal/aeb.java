package com.google.android.gms.internal;

import java.io.IOException;

public final class aeb extends adj<aeb> {
    private static volatile aeb[] zzctm;
    public String url = null;
    public Integer zzbuM = null;
    public adw zzctn = null;
    private ady zzcto = null;
    private Integer zzctp = null;
    private int[] zzctq = ads.zzcsC;
    private String zzctr = null;
    public Integer zzcts = null;
    public String[] zzctt = ads.EMPTY_STRING_ARRAY;

    public aeb() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static aeb[] zzLX() {
        if (zzctm == null) {
            synchronized (adn.zzcsw) {
                if (zzctm == null) {
                    zzctm = new aeb[0];
                }
            }
        }
        return zzctm;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzbuM = Integer.valueOf(adg.zzLC());
                    continue;
                case 18:
                    this.url = adg.readString();
                    continue;
                case 26:
                    if (this.zzctn == null) {
                        this.zzctn = new adw();
                    }
                    adg.zza(this.zzctn);
                    continue;
                case 34:
                    if (this.zzcto == null) {
                        this.zzcto = new ady();
                    }
                    adg.zza(this.zzcto);
                    continue;
                case 40:
                    this.zzctp = Integer.valueOf(adg.zzLC());
                    continue;
                case 48:
                    int zzb = ads.zzb(adg, 48);
                    int length = this.zzctq == null ? 0 : this.zzctq.length;
                    int[] iArr = new int[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzctq, 0, iArr, 0, length);
                    }
                    while (length < iArr.length - 1) {
                        iArr[length] = adg.zzLC();
                        adg.zzLA();
                        length++;
                    }
                    iArr[length] = adg.zzLC();
                    this.zzctq = iArr;
                    continue;
                case 50:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLC();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzctq == null ? 0 : this.zzctq.length;
                    int[] iArr2 = new int[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzctq, 0, iArr2, 0, length2);
                    }
                    while (length2 < iArr2.length) {
                        iArr2[length2] = adg.zzLC();
                        length2++;
                    }
                    this.zzctq = iArr2;
                    adg.zzco(zzcn);
                    continue;
                case 58:
                    this.zzctr = adg.readString();
                    continue;
                case 64:
                    int position2 = adg.getPosition();
                    int zzLC = adg.zzLC();
                    switch (zzLC) {
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.zzcts = Integer.valueOf(zzLC);
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 74:
                    int zzb2 = ads.zzb(adg, 74);
                    int length3 = this.zzctt == null ? 0 : this.zzctt.length;
                    String[] strArr = new String[(zzb2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzctt, 0, strArr, 0, length3);
                    }
                    while (length3 < strArr.length - 1) {
                        strArr[length3] = adg.readString();
                        adg.zzLA();
                        length3++;
                    }
                    strArr[length3] = adg.readString();
                    this.zzctt = strArr;
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
        adh.zzr(1, this.zzbuM.intValue());
        if (this.url != null) {
            adh.zzl(2, this.url);
        }
        if (this.zzctn != null) {
            adh.zza(3, (adp) this.zzctn);
        }
        if (this.zzcto != null) {
            adh.zza(4, (adp) this.zzcto);
        }
        if (this.zzctp != null) {
            adh.zzr(5, this.zzctp.intValue());
        }
        if (this.zzctq != null && this.zzctq.length > 0) {
            for (int zzr : this.zzctq) {
                adh.zzr(6, zzr);
            }
        }
        if (this.zzctr != null) {
            adh.zzl(7, this.zzctr);
        }
        if (this.zzcts != null) {
            adh.zzr(8, this.zzcts.intValue());
        }
        if (this.zzctt != null && this.zzctt.length > 0) {
            for (String str : this.zzctt) {
                if (str != null) {
                    adh.zzl(9, str);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn() + adh.zzs(1, this.zzbuM.intValue());
        if (this.url != null) {
            zzn += adh.zzm(2, this.url);
        }
        if (this.zzctn != null) {
            zzn += adh.zzb(3, (adp) this.zzctn);
        }
        if (this.zzcto != null) {
            zzn += adh.zzb(4, (adp) this.zzcto);
        }
        if (this.zzctp != null) {
            zzn += adh.zzs(5, this.zzctp.intValue());
        }
        if (this.zzctq != null && this.zzctq.length > 0) {
            int i = 0;
            for (int zzcr : this.zzctq) {
                i += adh.zzcr(zzcr);
            }
            zzn = zzn + i + (this.zzctq.length * 1);
        }
        if (this.zzctr != null) {
            zzn += adh.zzm(7, this.zzctr);
        }
        if (this.zzcts != null) {
            zzn += adh.zzs(8, this.zzcts.intValue());
        }
        if (this.zzctt == null || this.zzctt.length <= 0) {
            return zzn;
        }
        int i2 = 0;
        int i3 = 0;
        for (String str : this.zzctt) {
            if (str != null) {
                i3++;
                i2 += adh.zzhQ(str);
            }
        }
        return zzn + i2 + (i3 * 1);
    }
}
