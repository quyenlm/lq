package com.google.android.gms.internal;

import java.io.IOException;

public final class adx extends adj<adx> {
    private byte[] zzcte = null;
    private byte[] zzctf = null;
    private byte[] zzctg = null;

    public adx() {
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
                    this.zzcte = adg.readBytes();
                    continue;
                case 18:
                    this.zzctf = adg.readBytes();
                    continue;
                case 26:
                    this.zzctg = adg.readBytes();
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
        if (this.zzcte != null) {
            adh.zzb(1, this.zzcte);
        }
        if (this.zzctf != null) {
            adh.zzb(2, this.zzctf);
        }
        if (this.zzctg != null) {
            adh.zzb(3, this.zzctg);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcte != null) {
            zzn += adh.zzc(1, this.zzcte);
        }
        if (this.zzctf != null) {
            zzn += adh.zzc(2, this.zzctf);
        }
        return this.zzctg != null ? zzn + adh.zzc(3, this.zzctg) : zzn;
    }
}
