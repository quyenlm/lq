package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbi extends adj<zzbi> {
    private int level = 1;
    private int zzkx = 0;
    private int zzky = 0;

    public zzbi() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbi)) {
            return false;
        }
        zzbi zzbi = (zzbi) obj;
        if (this.level != zzbi.level) {
            return false;
        }
        if (this.zzkx != zzbi.zzkx) {
            return false;
        }
        if (this.zzky != zzbi.zzky) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbi.zzcso == null || zzbi.zzcso.isEmpty() : this.zzcso.equals(zzbi.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.level) * 31) + this.zzkx) * 31) + this.zzky) * 31);
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
                        case 1:
                        case 2:
                        case 3:
                            this.level = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 16:
                    this.zzkx = adg.zzLF();
                    continue;
                case 24:
                    this.zzky = adg.zzLF();
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
        if (this.level != 1) {
            adh.zzr(1, this.level);
        }
        if (this.zzkx != 0) {
            adh.zzr(2, this.zzkx);
        }
        if (this.zzky != 0) {
            adh.zzr(3, this.zzky);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.level != 1) {
            zzn += adh.zzs(1, this.level);
        }
        if (this.zzkx != 0) {
            zzn += adh.zzs(2, this.zzkx);
        }
        return this.zzky != 0 ? zzn + adh.zzs(3, this.zzky) : zzn;
    }
}
