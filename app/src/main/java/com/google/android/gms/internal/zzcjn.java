package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjn extends adj<zzcjn> {
    private static volatile zzcjn[] zzbuL;
    public Integer zzbuM = null;
    public String zzbuN = null;
    public zzcjo[] zzbuO = zzcjo.zzzw();
    private Boolean zzbuP = null;
    public zzcjp zzbuQ = null;

    public zzcjn() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcjn[] zzzv() {
        if (zzbuL == null) {
            synchronized (adn.zzcsw) {
                if (zzbuL == null) {
                    zzbuL = new zzcjn[0];
                }
            }
        }
        return zzbuL;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjn)) {
            return false;
        }
        zzcjn zzcjn = (zzcjn) obj;
        if (this.zzbuM == null) {
            if (zzcjn.zzbuM != null) {
                return false;
            }
        } else if (!this.zzbuM.equals(zzcjn.zzbuM)) {
            return false;
        }
        if (this.zzbuN == null) {
            if (zzcjn.zzbuN != null) {
                return false;
            }
        } else if (!this.zzbuN.equals(zzcjn.zzbuN)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbuO, (Object[]) zzcjn.zzbuO)) {
            return false;
        }
        if (this.zzbuP == null) {
            if (zzcjn.zzbuP != null) {
                return false;
            }
        } else if (!this.zzbuP.equals(zzcjn.zzbuP)) {
            return false;
        }
        if (this.zzbuQ == null) {
            if (zzcjn.zzbuQ != null) {
                return false;
            }
        } else if (!this.zzbuQ.equals(zzcjn.zzbuQ)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjn.zzcso == null || zzcjn.zzcso.isEmpty() : this.zzcso.equals(zzcjn.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbuQ == null ? 0 : this.zzbuQ.hashCode()) + (((this.zzbuP == null ? 0 : this.zzbuP.hashCode()) + (((((this.zzbuN == null ? 0 : this.zzbuN.hashCode()) + (((this.zzbuM == null ? 0 : this.zzbuM.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzbuO)) * 31)) * 31)) * 31;
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
                    this.zzbuM = Integer.valueOf(adg.zzLF());
                    continue;
                case 18:
                    this.zzbuN = adg.readString();
                    continue;
                case 26:
                    int zzb = ads.zzb(adg, 26);
                    int length = this.zzbuO == null ? 0 : this.zzbuO.length;
                    zzcjo[] zzcjoArr = new zzcjo[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbuO, 0, zzcjoArr, 0, length);
                    }
                    while (length < zzcjoArr.length - 1) {
                        zzcjoArr[length] = new zzcjo();
                        adg.zza(zzcjoArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcjoArr[length] = new zzcjo();
                    adg.zza(zzcjoArr[length]);
                    this.zzbuO = zzcjoArr;
                    continue;
                case 32:
                    this.zzbuP = Boolean.valueOf(adg.zzLD());
                    continue;
                case 42:
                    if (this.zzbuQ == null) {
                        this.zzbuQ = new zzcjp();
                    }
                    adg.zza(this.zzbuQ);
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
        if (this.zzbuM != null) {
            adh.zzr(1, this.zzbuM.intValue());
        }
        if (this.zzbuN != null) {
            adh.zzl(2, this.zzbuN);
        }
        if (this.zzbuO != null && this.zzbuO.length > 0) {
            for (zzcjo zzcjo : this.zzbuO) {
                if (zzcjo != null) {
                    adh.zza(3, (adp) zzcjo);
                }
            }
        }
        if (this.zzbuP != null) {
            adh.zzk(4, this.zzbuP.booleanValue());
        }
        if (this.zzbuQ != null) {
            adh.zza(5, (adp) this.zzbuQ);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbuM != null) {
            zzn += adh.zzs(1, this.zzbuM.intValue());
        }
        if (this.zzbuN != null) {
            zzn += adh.zzm(2, this.zzbuN);
        }
        if (this.zzbuO != null && this.zzbuO.length > 0) {
            int i = zzn;
            for (zzcjo zzcjo : this.zzbuO) {
                if (zzcjo != null) {
                    i += adh.zzb(3, (adp) zzcjo);
                }
            }
            zzn = i;
        }
        if (this.zzbuP != null) {
            this.zzbuP.booleanValue();
            zzn += adh.zzct(4) + 1;
        }
        return this.zzbuQ != null ? zzn + adh.zzb(5, (adp) this.zzbuQ) : zzn;
    }
}
