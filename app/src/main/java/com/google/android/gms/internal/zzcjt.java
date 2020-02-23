package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjt extends adj<zzcjt> {
    public String zzboQ = null;
    public Long zzbvl = null;
    private Integer zzbvm = null;
    public zzcju[] zzbvn = zzcju.zzzz();
    public zzcjs[] zzbvo = zzcjs.zzzy();
    public zzcjm[] zzbvp = zzcjm.zzzu();

    public zzcjt() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjt)) {
            return false;
        }
        zzcjt zzcjt = (zzcjt) obj;
        if (this.zzbvl == null) {
            if (zzcjt.zzbvl != null) {
                return false;
            }
        } else if (!this.zzbvl.equals(zzcjt.zzbvl)) {
            return false;
        }
        if (this.zzboQ == null) {
            if (zzcjt.zzboQ != null) {
                return false;
            }
        } else if (!this.zzboQ.equals(zzcjt.zzboQ)) {
            return false;
        }
        if (this.zzbvm == null) {
            if (zzcjt.zzbvm != null) {
                return false;
            }
        } else if (!this.zzbvm.equals(zzcjt.zzbvm)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbvn, (Object[]) zzcjt.zzbvn)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbvo, (Object[]) zzcjt.zzbvo)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbvp, (Object[]) zzcjt.zzbvp)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjt.zzcso == null || zzcjt.zzcso.isEmpty() : this.zzcso.equals(zzcjt.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((this.zzbvm == null ? 0 : this.zzbvm.hashCode()) + (((this.zzboQ == null ? 0 : this.zzboQ.hashCode()) + (((this.zzbvl == null ? 0 : this.zzbvl.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzbvn)) * 31) + adn.hashCode((Object[]) this.zzbvo)) * 31) + adn.hashCode((Object[]) this.zzbvp)) * 31;
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
                    this.zzbvl = Long.valueOf(adg.zzLG());
                    continue;
                case 18:
                    this.zzboQ = adg.readString();
                    continue;
                case 24:
                    this.zzbvm = Integer.valueOf(adg.zzLF());
                    continue;
                case 34:
                    int zzb = ads.zzb(adg, 34);
                    int length = this.zzbvn == null ? 0 : this.zzbvn.length;
                    zzcju[] zzcjuArr = new zzcju[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbvn, 0, zzcjuArr, 0, length);
                    }
                    while (length < zzcjuArr.length - 1) {
                        zzcjuArr[length] = new zzcju();
                        adg.zza(zzcjuArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcjuArr[length] = new zzcju();
                    adg.zza(zzcjuArr[length]);
                    this.zzbvn = zzcjuArr;
                    continue;
                case 42:
                    int zzb2 = ads.zzb(adg, 42);
                    int length2 = this.zzbvo == null ? 0 : this.zzbvo.length;
                    zzcjs[] zzcjsArr = new zzcjs[(zzb2 + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzbvo, 0, zzcjsArr, 0, length2);
                    }
                    while (length2 < zzcjsArr.length - 1) {
                        zzcjsArr[length2] = new zzcjs();
                        adg.zza(zzcjsArr[length2]);
                        adg.zzLA();
                        length2++;
                    }
                    zzcjsArr[length2] = new zzcjs();
                    adg.zza(zzcjsArr[length2]);
                    this.zzbvo = zzcjsArr;
                    continue;
                case 50:
                    int zzb3 = ads.zzb(adg, 50);
                    int length3 = this.zzbvp == null ? 0 : this.zzbvp.length;
                    zzcjm[] zzcjmArr = new zzcjm[(zzb3 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzbvp, 0, zzcjmArr, 0, length3);
                    }
                    while (length3 < zzcjmArr.length - 1) {
                        zzcjmArr[length3] = new zzcjm();
                        adg.zza(zzcjmArr[length3]);
                        adg.zzLA();
                        length3++;
                    }
                    zzcjmArr[length3] = new zzcjm();
                    adg.zza(zzcjmArr[length3]);
                    this.zzbvp = zzcjmArr;
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
        if (this.zzbvl != null) {
            adh.zzb(1, this.zzbvl.longValue());
        }
        if (this.zzboQ != null) {
            adh.zzl(2, this.zzboQ);
        }
        if (this.zzbvm != null) {
            adh.zzr(3, this.zzbvm.intValue());
        }
        if (this.zzbvn != null && this.zzbvn.length > 0) {
            for (zzcju zzcju : this.zzbvn) {
                if (zzcju != null) {
                    adh.zza(4, (adp) zzcju);
                }
            }
        }
        if (this.zzbvo != null && this.zzbvo.length > 0) {
            for (zzcjs zzcjs : this.zzbvo) {
                if (zzcjs != null) {
                    adh.zza(5, (adp) zzcjs);
                }
            }
        }
        if (this.zzbvp != null && this.zzbvp.length > 0) {
            for (zzcjm zzcjm : this.zzbvp) {
                if (zzcjm != null) {
                    adh.zza(6, (adp) zzcjm);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbvl != null) {
            zzn += adh.zze(1, this.zzbvl.longValue());
        }
        if (this.zzboQ != null) {
            zzn += adh.zzm(2, this.zzboQ);
        }
        if (this.zzbvm != null) {
            zzn += adh.zzs(3, this.zzbvm.intValue());
        }
        if (this.zzbvn != null && this.zzbvn.length > 0) {
            int i = zzn;
            for (zzcju zzcju : this.zzbvn) {
                if (zzcju != null) {
                    i += adh.zzb(4, (adp) zzcju);
                }
            }
            zzn = i;
        }
        if (this.zzbvo != null && this.zzbvo.length > 0) {
            int i2 = zzn;
            for (zzcjs zzcjs : this.zzbvo) {
                if (zzcjs != null) {
                    i2 += adh.zzb(5, (adp) zzcjs);
                }
            }
            zzn = i2;
        }
        if (this.zzbvp != null && this.zzbvp.length > 0) {
            for (zzcjm zzcjm : this.zzbvp) {
                if (zzcjm != null) {
                    zzn += adh.zzb(6, (adp) zzcjm);
                }
            }
        }
        return zzn;
    }
}
