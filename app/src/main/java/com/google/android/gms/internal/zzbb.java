package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbb extends adj<zzbb> {
    public byte[] zzcC = null;
    public byte[] zzcD = null;

    public zzbb() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzcC = adg.readBytes();
                    continue;
                case 18:
                    this.zzcD = adg.readBytes();
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
        if (this.zzcC != null) {
            adh.zzb(1, this.zzcC);
        }
        if (this.zzcD != null) {
            adh.zzb(2, this.zzcD);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcC != null) {
            zzn += adh.zzc(1, this.zzcC);
        }
        return this.zzcD != null ? zzn + adh.zzc(2, this.zzcD) : zzn;
    }
}
