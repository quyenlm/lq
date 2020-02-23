package com.google.android.gms.internal;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.IOException;

public final class acs extends adj<acs> {
    public int zzcqq = 0;
    public long zzcqr = 0;
    public int zzcrm = 0;
    public int zzcrn = 0;
    public int zzcro = 0;
    public double zzcrp = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    public double zzcrq = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    public long zzcrr = 0;

    public acs() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acs)) {
            return false;
        }
        acs acs = (acs) obj;
        if (this.zzcqq != acs.zzcqq) {
            return false;
        }
        if (this.zzcrm != acs.zzcrm) {
            return false;
        }
        if (this.zzcqr != acs.zzcqr) {
            return false;
        }
        if (this.zzcrn != acs.zzcrn) {
            return false;
        }
        if (this.zzcro != acs.zzcro) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzcrp) != Double.doubleToLongBits(acs.zzcrp)) {
            return false;
        }
        if (Double.doubleToLongBits(this.zzcrq) != Double.doubleToLongBits(acs.zzcrq)) {
            return false;
        }
        if (this.zzcrr != acs.zzcrr) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acs.zzcso == null || acs.zzcso.isEmpty() : this.zzcso.equals(acs.zzcso);
    }

    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzcrp);
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzcrq);
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((((((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + this.zzcrm) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + this.zzcrn) * 31) + this.zzcro) * 31) + ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32)))) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + ((int) (this.zzcrr ^ (this.zzcrr >>> 32)))) * 31);
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
                    int position2 = adg.getPosition();
                    int zzLF2 = adg.zzLF();
                    switch (zzLF2) {
                        case 0:
                        case 1:
                            this.zzcrm = zzLF2;
                            break;
                        default:
                            adg.zzcp(position2);
                            zza(adg, zzLA);
                            continue;
                    }
                case 24:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 32:
                    this.zzcrn = adg.zzLF();
                    continue;
                case 40:
                    this.zzcro = adg.zzLF();
                    continue;
                case 49:
                    this.zzcrp = Double.longBitsToDouble(adg.zzLI());
                    continue;
                case 57:
                    this.zzcrq = Double.longBitsToDouble(adg.zzLI());
                    continue;
                case 64:
                    this.zzcrr = adg.zzLG();
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
        if (this.zzcrm != 0) {
            adh.zzr(2, this.zzcrm);
        }
        if (this.zzcqr != 0) {
            adh.zzb(3, this.zzcqr);
        }
        if (this.zzcrn != 0) {
            adh.zzr(4, this.zzcrn);
        }
        if (this.zzcro != 0) {
            adh.zzr(5, this.zzcro);
        }
        if (Double.doubleToLongBits(this.zzcrp) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            adh.zza(6, this.zzcrp);
        }
        if (Double.doubleToLongBits(this.zzcrq) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            adh.zza(7, this.zzcrq);
        }
        if (this.zzcrr != 0) {
            adh.zzb(8, this.zzcrr);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcrm != 0) {
            zzn += adh.zzs(2, this.zzcrm);
        }
        if (this.zzcqr != 0) {
            zzn += adh.zze(3, this.zzcqr);
        }
        if (this.zzcrn != 0) {
            zzn += adh.zzs(4, this.zzcrn);
        }
        if (this.zzcro != 0) {
            zzn += adh.zzs(5, this.zzcro);
        }
        if (Double.doubleToLongBits(this.zzcrp) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            zzn += adh.zzct(6) + 8;
        }
        if (Double.doubleToLongBits(this.zzcrq) != Double.doubleToLongBits(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) {
            zzn += adh.zzct(7) + 8;
        }
        return this.zzcrr != 0 ? zzn + adh.zze(8, this.zzcrr) : zzn;
    }
}
