package com.google.android.gms.internal;

import java.io.IOException;

public final class aed extends adj<aed> {
    public int score = 0;
    public boolean zzctw = false;
    public String zzctx = "";
    private aee[] zzcty = aee.zzLY();

    public aed() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aed)) {
            return false;
        }
        aed aed = (aed) obj;
        if (this.zzctw != aed.zzctw) {
            return false;
        }
        if (this.score != aed.score) {
            return false;
        }
        if (this.zzctx == null) {
            if (aed.zzctx != null) {
                return false;
            }
        } else if (!this.zzctx.equals(aed.zzctx)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzcty, (Object[]) aed.zzcty)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aed.zzcso == null || aed.zzcso.isEmpty() : this.zzcso.equals(aed.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.zzctx == null ? 0 : this.zzctx.hashCode()) + (((((this.zzctw ? 1231 : 1237) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + this.score) * 31)) * 31) + adn.hashCode((Object[]) this.zzcty)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 8:
                    this.zzctw = adg.zzLD();
                    continue;
                case 16:
                    this.score = adg.zzLC();
                    continue;
                case 26:
                    this.zzctx = adg.readString();
                    continue;
                case 34:
                    int zzb = ads.zzb(adg, 34);
                    int length = this.zzcty == null ? 0 : this.zzcty.length;
                    aee[] aeeArr = new aee[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcty, 0, aeeArr, 0, length);
                    }
                    while (length < aeeArr.length - 1) {
                        aeeArr[length] = new aee();
                        adg.zza(aeeArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    aeeArr[length] = new aee();
                    adg.zza(aeeArr[length]);
                    this.zzcty = aeeArr;
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
        if (this.zzctw) {
            adh.zzk(1, this.zzctw);
        }
        if (this.score != 0) {
            adh.zzr(2, this.score);
        }
        if (this.zzctx != null && !this.zzctx.equals("")) {
            adh.zzl(3, this.zzctx);
        }
        if (this.zzcty != null && this.zzcty.length > 0) {
            for (aee aee : this.zzcty) {
                if (aee != null) {
                    adh.zza(4, (adp) aee);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzctw) {
            zzn += adh.zzct(1) + 1;
        }
        if (this.score != 0) {
            zzn += adh.zzs(2, this.score);
        }
        if (this.zzctx != null && !this.zzctx.equals("")) {
            zzn += adh.zzm(3, this.zzctx);
        }
        if (this.zzcty == null || this.zzcty.length <= 0) {
            return zzn;
        }
        int i = zzn;
        for (aee aee : this.zzcty) {
            if (aee != null) {
                i += adh.zzb(4, (adp) aee);
            }
        }
        return i;
    }
}
