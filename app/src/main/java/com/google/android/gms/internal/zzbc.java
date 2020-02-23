package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbc extends adj<zzbc> {
    public byte[] data = null;
    public byte[] zzcE = null;
    public byte[] zzcF = null;
    public byte[] zzcG = null;

    public zzbc() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.data = adg.readBytes();
                    continue;
                case 18:
                    this.zzcE = adg.readBytes();
                    continue;
                case 26:
                    this.zzcF = adg.readBytes();
                    continue;
                case 34:
                    this.zzcG = adg.readBytes();
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
        if (this.data != null) {
            adh.zzb(1, this.data);
        }
        if (this.zzcE != null) {
            adh.zzb(2, this.zzcE);
        }
        if (this.zzcF != null) {
            adh.zzb(3, this.zzcF);
        }
        if (this.zzcG != null) {
            adh.zzb(4, this.zzcG);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.data != null) {
            zzn += adh.zzc(1, this.data);
        }
        if (this.zzcE != null) {
            zzn += adh.zzc(2, this.zzcE);
        }
        if (this.zzcF != null) {
            zzn += adh.zzc(3, this.zzcF);
        }
        return this.zzcG != null ? zzn + adh.zzc(4, this.zzcG) : zzn;
    }
}
