package com.google.android.gms.internal;

import java.io.IOException;

public final class abn extends adj<abn> {
    public int zzcnS = 0;
    public boolean zzcnT = false;
    public long zzcnU = 0;

    public abn() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof abn)) {
            return false;
        }
        abn abn = (abn) obj;
        if (this.zzcnS != abn.zzcnS) {
            return false;
        }
        if (this.zzcnT != abn.zzcnT) {
            return false;
        }
        if (this.zzcnU != abn.zzcnU) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? abn.zzcso == null || abn.zzcso.isEmpty() : this.zzcso.equals(abn.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + (((((this.zzcnT ? 1231 : 1237) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzcnS) * 31)) * 31) + ((int) (this.zzcnU ^ (this.zzcnU >>> 32)))) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzcnS = adg.zzLF();
                    continue;
                case 16:
                    this.zzcnT = adg.zzLD();
                    continue;
                case 25:
                    this.zzcnU = adg.zzLI();
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
        if (this.zzcnS != 0) {
            adh.zzr(1, this.zzcnS);
        }
        if (this.zzcnT) {
            adh.zzk(2, this.zzcnT);
        }
        if (this.zzcnU != 0) {
            adh.zzc(3, this.zzcnU);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcnS != 0) {
            zzn += adh.zzs(1, this.zzcnS);
        }
        if (this.zzcnT) {
            zzn += adh.zzct(2) + 1;
        }
        return this.zzcnU != 0 ? zzn + adh.zzct(3) + 8 : zzn;
    }
}
