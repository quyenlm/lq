package com.google.android.gms.internal;

import java.io.IOException;

public final class aef extends adj<aef> {
    private static volatile aef[] zzctE;
    private String type = "";
    private aed zzctF = null;
    private aee[] zzcty = aee.zzLY();
    private String zzkv = "";

    public aef() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static aef[] zzLZ() {
        if (zzctE == null) {
            synchronized (adn.zzcsw) {
                if (zzctE == null) {
                    zzctE = new aef[0];
                }
            }
        }
        return zzctE;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aef)) {
            return false;
        }
        aef aef = (aef) obj;
        if (this.type == null) {
            if (aef.type != null) {
                return false;
            }
        } else if (!this.type.equals(aef.type)) {
            return false;
        }
        if (this.zzkv == null) {
            if (aef.zzkv != null) {
                return false;
            }
        } else if (!this.zzkv.equals(aef.zzkv)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcty, (Object[]) aef.zzcty)) {
            return false;
        }
        if (this.zzctF == null) {
            if (aef.zzctF != null) {
                return false;
            }
        } else if (!this.zzctF.equals(aef.zzctF)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aef.zzcso == null || aef.zzcso.isEmpty() : this.zzcso.equals(aef.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzctF == null ? 0 : this.zzctF.hashCode()) + (((((this.zzkv == null ? 0 : this.zzkv.hashCode()) + (((this.type == null ? 0 : this.type.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + adn.hashCode((Object[]) this.zzcty)) * 31)) * 31;
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
                    this.type = adg.readString();
                    continue;
                case 18:
                    this.zzkv = adg.readString();
                    continue;
                case 26:
                    int zzb = ads.zzb(adg, 26);
                    int length = this.zzcty == null ? 0 : this.zzcty.length;
                    aee[] aeeArr = new aee[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcty, 0, aeeArr, 0, length);
                    }
                    while (length < aeeArr.length - 1) {
                        aeeArr[length] = new aee();
                        adg.zza(aeeArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    aeeArr[length] = new aee();
                    adg.zza(aeeArr[length]);
                    this.zzcty = aeeArr;
                    continue;
                case 34:
                    if (this.zzctF == null) {
                        this.zzctF = new aed();
                    }
                    adg.zza(this.zzctF);
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
        if (this.type != null && !this.type.equals("")) {
            adh.zzl(1, this.type);
        }
        if (this.zzkv != null && !this.zzkv.equals("")) {
            adh.zzl(2, this.zzkv);
        }
        if (this.zzcty != null && this.zzcty.length > 0) {
            for (aee aee : this.zzcty) {
                if (aee != null) {
                    adh.zza(3, (adp) aee);
                }
            }
        }
        if (this.zzctF != null) {
            adh.zza(4, (adp) this.zzctF);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.type != null && !this.type.equals("")) {
            zzn += adh.zzm(1, this.type);
        }
        if (this.zzkv != null && !this.zzkv.equals("")) {
            zzn += adh.zzm(2, this.zzkv);
        }
        if (this.zzcty != null && this.zzcty.length > 0) {
            int i = zzn;
            for (aee aee : this.zzcty) {
                if (aee != null) {
                    i += adh.zzb(3, (adp) aee);
                }
            }
            zzn = i;
        }
        return this.zzctF != null ? zzn + adh.zzb(4, (adp) this.zzctF) : zzn;
    }
}
