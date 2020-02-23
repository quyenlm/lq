package com.google.android.gms.internal;

import java.io.IOException;

public final class aea extends adj<aea> {
    public String mimeType = null;
    public Integer zzcsJ = null;
    public byte[] zzctl = null;

    public aea() {
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
                    int position = adg.getPosition();
                    int zzLC = adg.zzLC();
                    switch (zzLC) {
                        case 0:
                        case 1:
                            this.zzcsJ = Integer.valueOf(zzLC);
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 18:
                    this.mimeType = adg.readString();
                    continue;
                case 26:
                    this.zzctl = adg.readBytes();
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
        if (this.zzcsJ != null) {
            adh.zzr(1, this.zzcsJ.intValue());
        }
        if (this.mimeType != null) {
            adh.zzl(2, this.mimeType);
        }
        if (this.zzctl != null) {
            adh.zzb(3, this.zzctl);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcsJ != null) {
            zzn += adh.zzs(1, this.zzcsJ.intValue());
        }
        if (this.mimeType != null) {
            zzn += adh.zzm(2, this.mimeType);
        }
        return this.zzctl != null ? zzn + adh.zzc(3, this.zzctl) : zzn;
    }
}
