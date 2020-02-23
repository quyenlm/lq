package com.google.android.gms.internal;

import java.io.IOException;

public final class adb extends adj<adb> {
    public int zzcqq = 0;
    public long zzcrD = 0;
    public long zzcrE = 0;

    public adb() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof adb)) {
            return false;
        }
        adb adb = (adb) obj;
        if (this.zzcqq != adb.zzcqq) {
            return false;
        }
        if (this.zzcrD != adb.zzcrD) {
            return false;
        }
        if (this.zzcrE != adb.zzcrE) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? adb.zzcso == null || adb.zzcso.isEmpty() : this.zzcso.equals(adb.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcrD ^ (this.zzcrD >>> 32)))) * 31) + ((int) (this.zzcrE ^ (this.zzcrE >>> 32)))) * 31);
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
                            this.zzcqq = zzLF;
                            break;
                        default:
                            adg.zzcp(position);
                            zza(adg, zzLA);
                            continue;
                    }
                case 24:
                    this.zzcrD = adg.zzLG();
                    continue;
                case 32:
                    this.zzcrE = adg.zzLG();
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
        if (this.zzcrD != 0) {
            adh.zzb(3, this.zzcrD);
        }
        if (this.zzcrE != 0) {
            adh.zzb(4, this.zzcrE);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcqq != 0) {
            zzn += adh.zzs(1, this.zzcqq);
        }
        if (this.zzcrD != 0) {
            zzn += adh.zze(3, this.zzcrD);
        }
        return this.zzcrE != 0 ? zzn + adh.zze(4, this.zzcrE) : zzn;
    }
}
