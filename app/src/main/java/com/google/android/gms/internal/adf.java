package com.google.android.gms.internal;

import java.io.IOException;

public final class adf extends adj<adf> {
    private int zzcqq = 0;
    private long zzcqr = 0;
    private float zzcrK = 0.0f;
    private int zzcrL = 0;

    public adf() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof adf)) {
            return false;
        }
        adf adf = (adf) obj;
        if (this.zzcqq != adf.zzcqq) {
            return false;
        }
        if (this.zzcqr != adf.zzcqr) {
            return false;
        }
        if (Float.floatToIntBits(this.zzcrK) != Float.floatToIntBits(adf.zzcrK)) {
            return false;
        }
        if (this.zzcrL != adf.zzcrL) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? adf.zzcso == null || adf.zzcso.isEmpty() : this.zzcso.equals(adf.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + Float.floatToIntBits(this.zzcrK)) * 31) + this.zzcrL) * 31);
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
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 16:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 29:
                    this.zzcrK = Float.intBitsToFloat(adg.zzLH());
                    continue;
                case 32:
                    this.zzcrL = adg.zzLF();
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
        if (this.zzcqq != 0) {
            adh.zzr(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            adh.zzb(2, this.zzcqr);
        }
        if (Float.floatToIntBits(this.zzcrK) != Float.floatToIntBits(0.0f)) {
            adh.zzc(3, this.zzcrK);
        }
        if (this.zzcrL != 0) {
            adh.zzr(4, this.zzcrL);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(2, this.zzcqr);
        }
        if (Float.floatToIntBits(this.zzcrK) != Float.floatToIntBits(0.0f)) {
            zzn += adh.zzct(3) + 4;
        }
        return this.zzcrL != 0 ? zzn + adh.zzs(4, this.zzcrL) : zzn;
    }
}
