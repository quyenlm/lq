package com.google.android.gms.internal;

import java.io.IOException;

public final class abp extends adj<abp> {
    public abl zzcnX = null;
    public abl zzcnY = null;
    public abl zzcnZ = null;
    public abn zzcoa = null;
    public abq[] zzcob = abq.zzKL();

    public abp() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof abp)) {
            return false;
        }
        abp abp = (abp) obj;
        if (this.zzcnX == null) {
            if (abp.zzcnX != null) {
                return false;
            }
        } else if (!this.zzcnX.equals(abp.zzcnX)) {
            return false;
        }
        if (this.zzcnY == null) {
            if (abp.zzcnY != null) {
                return false;
            }
        } else if (!this.zzcnY.equals(abp.zzcnY)) {
            return false;
        }
        if (this.zzcnZ == null) {
            if (abp.zzcnZ != null) {
                return false;
            }
        } else if (!this.zzcnZ.equals(abp.zzcnZ)) {
            return false;
        }
        if (this.zzcoa == null) {
            if (abp.zzcoa != null) {
                return false;
            }
        } else if (!this.zzcoa.equals(abp.zzcoa)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcob, (Object[]) abp.zzcob)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? abp.zzcso == null || abp.zzcso.isEmpty() : this.zzcso.equals(abp.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzcoa == null ? 0 : this.zzcoa.hashCode()) + (((this.zzcnZ == null ? 0 : this.zzcnZ.hashCode()) + (((this.zzcnY == null ? 0 : this.zzcnY.hashCode()) + (((this.zzcnX == null ? 0 : this.zzcnX.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzcob)) * 31;
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
                    if (this.zzcnX == null) {
                        this.zzcnX = new abl();
                    }
                    adg.zza(this.zzcnX);
                    continue;
                case 18:
                    if (this.zzcnY == null) {
                        this.zzcnY = new abl();
                    }
                    adg.zza(this.zzcnY);
                    continue;
                case 26:
                    if (this.zzcnZ == null) {
                        this.zzcnZ = new abl();
                    }
                    adg.zza(this.zzcnZ);
                    continue;
                case 34:
                    if (this.zzcoa == null) {
                        this.zzcoa = new abn();
                    }
                    adg.zza(this.zzcoa);
                    continue;
                case 42:
                    int zzb = ads.zzb(adg, 42);
                    int length = this.zzcob == null ? 0 : this.zzcob.length;
                    abq[] abqArr = new abq[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcob, 0, abqArr, 0, length);
                    }
                    while (length < abqArr.length - 1) {
                        abqArr[length] = new abq();
                        adg.zza(abqArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    abqArr[length] = new abq();
                    adg.zza(abqArr[length]);
                    this.zzcob = abqArr;
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
        if (this.zzcnX != null) {
            adh.zza(1, (adp) this.zzcnX);
        }
        if (this.zzcnY != null) {
            adh.zza(2, (adp) this.zzcnY);
        }
        if (this.zzcnZ != null) {
            adh.zza(3, (adp) this.zzcnZ);
        }
        if (this.zzcoa != null) {
            adh.zza(4, (adp) this.zzcoa);
        }
        if (this.zzcob != null && this.zzcob.length > 0) {
            for (abq abq : this.zzcob) {
                if (abq != null) {
                    adh.zza(5, (adp) abq);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcnX != null) {
            zzn += adh.zzb(1, (adp) this.zzcnX);
        }
        if (this.zzcnY != null) {
            zzn += adh.zzb(2, (adp) this.zzcnY);
        }
        if (this.zzcnZ != null) {
            zzn += adh.zzb(3, (adp) this.zzcnZ);
        }
        if (this.zzcoa != null) {
            zzn += adh.zzb(4, (adp) this.zzcoa);
        }
        if (this.zzcob == null || this.zzcob.length <= 0) {
            return zzn;
        }
        int i = zzn;
        for (abq abq : this.zzcob) {
            if (abq != null) {
                i += adh.zzb(5, (adp) abq);
            }
        }
        return i;
    }
}
