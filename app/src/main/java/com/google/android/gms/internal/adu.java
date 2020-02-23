package com.google.android.gms.internal;

import java.io.IOException;

public final class adu extends adj<adu> {
    public String zzXr = null;

    public adu() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzXr = adg.readString();
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
        if (this.zzXr != null) {
            adh.zzl(1, this.zzXr);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        return this.zzXr != null ? zzn + adh.zzm(1, this.zzXr) : zzn;
    }
}
