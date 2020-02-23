package com.google.android.gms.internal;

import java.io.IOException;

public final class acy extends adj<acy> {
    private int zzcqq = 0;

    public acy() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acy)) {
            return false;
        }
        acy acy = (acy) obj;
        if (this.zzcqq != acy.zzcqq) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acy.zzcso == null || acy.zzcso.isEmpty() : this.zzcso.equals(acy.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31);
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
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        return this.zzcqq != 0 ? zzn + adh.zzs(1, this.zzcqq) : zzn;
    }
}
