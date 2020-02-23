package com.google.android.gms.internal;

import java.io.IOException;

public final class acj extends adj<acj> {
    public int zzcqq = 0;
    public long zzcqr = 0;
    public ack[] zzcqz = ack.zzLo();

    public acj() {
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof acj)) {
            return false;
        }
        acj acj = (acj) obj;
        if (this.zzcqq != acj.zzcqq) {
            return false;
        }
        if (this.zzcqr != acj.zzcqr) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcqz, (Object[]) acj.zzcqz)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? acj.zzcso == null || acj.zzcso.isEmpty() : this.zzcso.equals(acj.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + this.zzcqq) * 31) + ((int) (this.zzcqr ^ (this.zzcqr >>> 32)))) * 31) + adn.hashCode((Object[]) this.zzcqz)) * 31);
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
                case 24:
                    this.zzcqr = adg.zzLG();
                    continue;
                case 34:
                    int zzb = ads.zzb(adg, 34);
                    int length = this.zzcqz == null ? 0 : this.zzcqz.length;
                    ack[] ackArr = new ack[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcqz, 0, ackArr, 0, length);
                    }
                    while (length < ackArr.length - 1) {
                        ackArr[length] = new ack();
                        adg.zza(ackArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    ackArr[length] = new ack();
                    adg.zza(ackArr[length]);
                    this.zzcqz = ackArr;
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
            adh.zzb(3, this.zzcqr);
        }
        if (this.zzcqz != null && this.zzcqz.length > 0) {
            for (ack ack : this.zzcqz) {
                if (ack != null) {
                    adh.zza(4, (adp) ack);
                }
            }
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
            zzn += adh.zze(3, this.zzcqr);
        }
        if (this.zzcqz == null || this.zzcqz.length <= 0) {
            return zzn;
        }
        int i = zzn;
        for (ack ack : this.zzcqz) {
            if (ack != null) {
                i += adh.zzb(4, (adp) ack);
            }
        }
        return i;
    }
}
