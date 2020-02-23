package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbd extends adj<zzbd> {
    private String zzcH = null;
    private byte[] zzcI = null;
    public Long zzcx = null;

    public zzbd() {
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzcx = Long.valueOf(adg.zzLG());
                    continue;
                case 26:
                    this.zzcH = adg.readString();
                    continue;
                case 34:
                    this.zzcI = adg.readBytes();
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
        if (this.zzcx != null) {
            adh.zzb(1, this.zzcx.longValue());
        }
        if (this.zzcH != null) {
            adh.zzl(3, this.zzcH);
        }
        if (this.zzcI != null) {
            adh.zzb(4, this.zzcI);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcx != null) {
            zzn += adh.zze(1, this.zzcx.longValue());
        }
        if (this.zzcH != null) {
            zzn += adh.zzm(3, this.zzcH);
        }
        return this.zzcI != null ? zzn + adh.zzc(4, this.zzcI) : zzn;
    }
}
