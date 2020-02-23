package com.google.android.gms.internal;

import java.io.IOException;

public final class acp extends adj<acp> {
    private int day = 0;
    private int hour = 0;
    private int minutes = 0;
    private int month = 0;
    private int seconds = 0;
    private int year = 0;

    public acp() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acp)) {
            return false;
        }
        acp acp = (acp) obj;
        if (this.year != acp.year) {
            return false;
        }
        if (this.month != acp.month) {
            return false;
        }
        if (this.day != acp.day) {
            return false;
        }
        if (this.hour != acp.hour) {
            return false;
        }
        if (this.minutes != acp.minutes) {
            return false;
        }
        if (this.seconds != acp.seconds) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acp.zzcso == null || acp.zzcso.isEmpty() : this.zzcso.equals(acp.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.year) * 31) + this.month) * 31) + this.day) * 31) + this.hour) * 31) + this.minutes) * 31) + this.seconds) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.year = adg.zzLF();
                    continue;
                case 16:
                    this.month = adg.zzLF();
                    continue;
                case 24:
                    this.day = adg.zzLF();
                    continue;
                case 32:
                    this.hour = adg.zzLF();
                    continue;
                case 40:
                    this.minutes = adg.zzLF();
                    continue;
                case 48:
                    this.seconds = adg.zzLF();
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
        if (this.year != 0) {
            adh.zzr(1, this.year);
        }
        if (this.month != 0) {
            adh.zzr(2, this.month);
        }
        if (this.day != 0) {
            adh.zzr(3, this.day);
        }
        if (this.hour != 0) {
            adh.zzr(4, this.hour);
        }
        if (this.minutes != 0) {
            adh.zzr(5, this.minutes);
        }
        if (this.seconds != 0) {
            adh.zzr(6, this.seconds);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.year != 0) {
            zzn += adh.zzs(1, this.year);
        }
        if (this.month != 0) {
            zzn += adh.zzs(2, this.month);
        }
        if (this.day != 0) {
            zzn += adh.zzs(3, this.day);
        }
        if (this.hour != 0) {
            zzn += adh.zzs(4, this.hour);
        }
        if (this.minutes != 0) {
            zzn += adh.zzs(5, this.minutes);
        }
        return this.seconds != 0 ? zzn + adh.zzs(6, this.seconds) : zzn;
    }
}
