package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjr extends adj<zzcjr> {
    public Integer zzbve = null;
    public String zzbvf = null;
    public Boolean zzbvg = null;
    public String[] zzbvh = ads.EMPTY_STRING_ARRAY;

    public zzcjr() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjr)) {
            return false;
        }
        zzcjr zzcjr = (zzcjr) obj;
        if (this.zzbve == null) {
            if (zzcjr.zzbve != null) {
                return false;
            }
        } else if (!this.zzbve.equals(zzcjr.zzbve)) {
            return false;
        }
        if (this.zzbvf == null) {
            if (zzcjr.zzbvf != null) {
                return false;
            }
        } else if (!this.zzbvf.equals(zzcjr.zzbvf)) {
            return false;
        }
        if (this.zzbvg == null) {
            if (zzcjr.zzbvg != null) {
                return false;
            }
        } else if (!this.zzbvg.equals(zzcjr.zzbvg)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbvh, (Object[]) zzcjr.zzbvh)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjr.zzcso == null || zzcjr.zzcso.isEmpty() : this.zzcso.equals(zzcjr.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzbvg == null ? 0 : this.zzbvg.hashCode()) + (((this.zzbvf == null ? 0 : this.zzbvf.hashCode()) + (((this.zzbve == null ? 0 : this.zzbve.intValue()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzbvh)) * 31;
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
                            this.zzbve = Integer.valueOf(zzLF);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    this.zzbvf = adg.readString();
                    continue;
                case 24:
                    this.zzbvg = Boolean.valueOf(adg.zzLD());
                    continue;
                case 34:
                    int zzb = ads.zzb(adg, 34);
                    int length = this.zzbvh == null ? 0 : this.zzbvh.length;
                    String[] strArr = new String[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbvh, 0, strArr, 0, length);
                    }
                    while (length < strArr.length - 1) {
                        strArr[length] = adg.readString();
                        adg.zzLA();
                        length++;
                    }
                    strArr[length] = adg.readString();
                    this.zzbvh = strArr;
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
        if (this.zzbve != null) {
            adh.zzr(1, this.zzbve.intValue());
        }
        if (this.zzbvf != null) {
            adh.zzl(2, this.zzbvf);
        }
        if (this.zzbvg != null) {
            adh.zzk(3, this.zzbvg.booleanValue());
        }
        if (this.zzbvh != null && this.zzbvh.length > 0) {
            for (String str : this.zzbvh) {
                if (str != null) {
                    adh.zzl(4, str);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (this.zzbve != null) {
            zzn += adh.zzs(1, this.zzbve.intValue());
        }
        if (this.zzbvf != null) {
            zzn += adh.zzm(2, this.zzbvf);
        }
        if (this.zzbvg != null) {
            this.zzbvg.booleanValue();
            zzn += adh.zzct(3) + 1;
        }
        if (this.zzbvh == null || this.zzbvh.length <= 0) {
            return zzn;
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < this.zzbvh.length) {
            String str = this.zzbvh[i2];
            if (str != null) {
                i4++;
                i = adh.zzhQ(str) + i3;
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        return zzn + i3 + (i4 * 1);
    }
}
