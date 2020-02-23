package com.google.android.gms.internal;

import java.io.IOException;

public final class abo extends adj<abo> {
    private static volatile abo[] zzcnV;
    public String zzbxU = "";
    public abm[] zzcnW = abm.zzKJ();

    public abo() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static abo[] zzKK() {
        if (zzcnV == null) {
            synchronized (adn.zzcsw) {
                if (zzcnV == null) {
                    zzcnV = new abo[0];
                }
            }
        }
        return zzcnV;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof abo)) {
            return false;
        }
        abo abo = (abo) obj;
        if (this.zzbxU == null) {
            if (abo.zzbxU != null) {
                return false;
            }
        } else if (!this.zzbxU.equals(abo.zzbxU)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcnW, (Object[]) abo.zzcnW)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? abo.zzcso == null || abo.zzcso.isEmpty() : this.zzcso.equals(abo.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzbxU == null ? 0 : this.zzbxU.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + adn.hashCode((Object[]) this.zzcnW)) * 31;
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
                    this.zzbxU = adg.readString();
                    continue;
                case 18:
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzcnW == null ? 0 : this.zzcnW.length;
                    abm[] abmArr = new abm[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcnW, 0, abmArr, 0, length);
                    }
                    while (length < abmArr.length - 1) {
                        abmArr[length] = new abm();
                        adg.zza(abmArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    abmArr[length] = new abm();
                    adg.zza(abmArr[length]);
                    this.zzcnW = abmArr;
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
        if (this.zzbxU != null && !this.zzbxU.equals("")) {
            adh.zzl(1, this.zzbxU);
        }
        if (this.zzcnW != null && this.zzcnW.length > 0) {
            for (abm abm : this.zzcnW) {
                if (abm != null) {
                    adh.zza(2, (adp) abm);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbxU != null && !this.zzbxU.equals("")) {
            zzn += adh.zzm(1, this.zzbxU);
        }
        if (this.zzcnW == null || this.zzcnW.length <= 0) {
            return zzn;
        }
        int i = zzn;
        for (abm abm : this.zzcnW) {
            if (abm != null) {
                i += adh.zzb(2, (adp) abm);
            }
        }
        return i;
    }
}
