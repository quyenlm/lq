package com.google.android.gms.internal;

import java.io.IOException;

public final class adz extends adj<adz> {
    private byte[] zzctg = null;
    private Integer zzctj = null;
    private byte[] zzctk = null;

    public adz() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzctj = Integer.valueOf(adg.zzLC());
                    continue;
                case 18:
                    this.zzctk = adg.readBytes();
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
        if (this.zzctj != null) {
            adh.zzr(1, this.zzctj.intValue());
        }
        if (this.zzctk != null) {
            adh.zzb(2, this.zzctk);
        }
        if (this.zzctg != null) {
            adh.zzb(3, this.zzctg);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzctj != null) {
            zzn += adh.zzs(1, this.zzctj.intValue());
        }
        if (this.zzctk != null) {
            zzn += adh.zzc(2, this.zzctk);
        }
        return this.zzctg != null ? zzn + adh.zzc(3, this.zzctg) : zzn;
    }
}
