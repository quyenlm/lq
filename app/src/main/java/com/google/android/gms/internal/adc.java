package com.google.android.gms.internal;

import java.io.IOException;

public final class adc extends adj<adc> {
    private int type = 3;
    private long zzaTb = 0;
    private long zzaTc = 0;

    public adc() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof adc)) {
            return false;
        }
        adc adc = (adc) obj;
        if (this.type != adc.type) {
            return false;
        }
        if (this.zzaTb != adc.zzaTb) {
            return false;
        }
        if (this.zzaTc != adc.zzaTc) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? adc.zzcso == null || adc.zzcso.isEmpty() : this.zzcso.equals(adc.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.type) * 31) + ((int) (this.zzaTb ^ (this.zzaTb >>> 32)))) * 31) + ((int) (this.zzaTc ^ (this.zzaTc >>> 32)))) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    int position = adg.getPosition();
                    int zzLF = adg.zzLF();
                    switch (zzLF) {
                        case 1:
                        case 2:
                        case 3:
                            this.type = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 16:
                    this.zzaTb = adg.zzLG();
                    continue;
                case 24:
                    this.zzaTc = adg.zzLG();
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
        if (this.type != 3) {
            adh.zzr(1, this.type);
        }
        if (this.zzaTb != 0) {
            adh.zzb(2, this.zzaTb);
        }
        if (this.zzaTc != 0) {
            adh.zzb(3, this.zzaTc);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.type != 3) {
            zzn += adh.zzs(1, this.type);
        }
        if (this.zzaTb != 0) {
            zzn += adh.zze(2, this.zzaTb);
        }
        return this.zzaTc != 0 ? zzn + adh.zze(3, this.zzaTc) : zzn;
    }
}
