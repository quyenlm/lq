package com.google.android.gms.internal;

import java.io.IOException;

public final class adw extends adj<adw> {
    private byte[] body = null;
    private adx zzcta = null;
    public adv[] zzctb = adv.zzLW();
    private byte[] zzctc = null;
    private Integer zzctd = null;

    public adw() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    if (this.zzcta == null) {
                        this.zzcta = new adx();
                    }
                    adg.zza(this.zzcta);
                    continue;
                case 18:
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzctb == null ? 0 : this.zzctb.length;
                    adv[] advArr = new adv[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzctb, 0, advArr, 0, length);
                    }
                    while (length < advArr.length - 1) {
                        advArr[length] = new adv();
                        adg.zza(advArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    advArr[length] = new adv();
                    adg.zza(advArr[length]);
                    this.zzctb = advArr;
                    continue;
                case 26:
                    this.body = adg.readBytes();
                    continue;
                case 34:
                    this.zzctc = adg.readBytes();
                    continue;
                case 40:
                    this.zzctd = Integer.valueOf(adg.zzLC());
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
        if (this.zzcta != null) {
            adh.zza(1, (adp) this.zzcta);
        }
        if (this.zzctb != null && this.zzctb.length > 0) {
            for (adv adv : this.zzctb) {
                if (adv != null) {
                    adh.zza(2, (adp) adv);
                }
            }
        }
        if (this.body != null) {
            adh.zzb(3, this.body);
        }
        if (this.zzctc != null) {
            adh.zzb(4, this.zzctc);
        }
        if (this.zzctd != null) {
            adh.zzr(5, this.zzctd.intValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcta != null) {
            zzn += adh.zzb(1, (adp) this.zzcta);
        }
        if (this.zzctb != null && this.zzctb.length > 0) {
            int i = zzn;
            for (adv adv : this.zzctb) {
                if (adv != null) {
                    i += adh.zzb(2, (adp) adv);
                }
            }
            zzn = i;
        }
        if (this.body != null) {
            zzn += adh.zzc(3, this.body);
        }
        if (this.zzctc != null) {
            zzn += adh.zzc(4, this.zzctc);
        }
        return this.zzctd != null ? zzn + adh.zzs(5, this.zzctd.intValue()) : zzn;
    }
}
