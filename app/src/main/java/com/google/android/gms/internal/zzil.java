package com.google.android.gms.internal;

import java.io.IOException;

public final class zzil extends adj<zzil> {
    public String zzzK = null;

    public zzil() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 82:
                    this.zzzK = adg.readString();
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
        if (this.zzzK != null) {
            adh.zzl(10, this.zzzK);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        return this.zzzK != null ? zzn + adh.zzm(10, this.zzzK) : zzn;
    }
}
