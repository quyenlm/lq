package com.google.android.gms.internal;

import java.io.IOException;

public final class adv extends adj<adv> {
    private static volatile adv[] zzcsY;
    public byte[] zzcnR = null;
    public byte[] zzcsZ = null;

    public adv() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static adv[] zzLW() {
        if (zzcsY == null) {
            synchronized (adn.zzcsw) {
                if (zzcsY == null) {
                    zzcsY = new adv[0];
                }
            }
        }
        return zzcsY;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzcsZ = adg.readBytes();
                    continue;
                case 18:
                    this.zzcnR = adg.readBytes();
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
        adh.zzb(1, this.zzcsZ);
        if (this.zzcnR != null) {
            adh.zzb(2, this.zzcnR);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn() + adh.zzc(1, this.zzcsZ);
        return this.zzcnR != null ? zzn + adh.zzc(2, this.zzcnR) : zzn;
    }
}
