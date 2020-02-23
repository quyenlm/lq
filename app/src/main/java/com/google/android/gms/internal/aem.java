package com.google.android.gms.internal;

import java.io.IOException;

public final class aem extends adp {
    private static volatile aem[] zzcum;
    public String zzcun = "";

    public aem() {
        this.zzcsx = -1;
    }

    public static aem[] zzMh() {
        if (zzcum == null) {
            synchronized (adn.zzcsw) {
                if (zzcum == null) {
                    zzcum = new aem[0];
                }
            }
        }
        return zzcum;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzcun = adg.readString();
                    continue;
                default:
                    if (!adg.zzcm(zzLA)) {
                        break;
                    } else {
                        continue;
                    }
            }
        }
        return this;
    }

    public final void zza(adh adh) throws IOException {
        if (this.zzcun != null && !this.zzcun.equals("")) {
            adh.zzl(1, this.zzcun);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        return (this.zzcun == null || this.zzcun.equals("")) ? zzn : zzn + adh.zzm(1, this.zzcun);
    }
}
