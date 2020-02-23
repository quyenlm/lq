package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcbc extends adj<zzcbc> {
    public String type = "";
    public zzcbb[] zzbgv = zzcbb.zzvI();

    public zzcbc() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcbc)) {
            return false;
        }
        zzcbc zzcbc = (zzcbc) obj;
        if (this.type == null) {
            if (zzcbc.type != null) {
                return false;
            }
        } else if (!this.type.equals(zzcbc.type)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzbgv, (Object[]) zzcbc.zzbgv)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcbc.zzcso == null || zzcbc.zzcso.isEmpty() : this.zzcso.equals(zzcbc.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.type == null ? 0 : this.type.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + adn.hashCode((Object[]) this.zzbgv)) * 31;
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
                case 10:
                    this.type = adg.readString();
                    continue;
                case 18:
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzbgv == null ? 0 : this.zzbgv.length;
                    zzcbb[] zzcbbArr = new zzcbb[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbgv, 0, zzcbbArr, 0, length);
                    }
                    while (length < zzcbbArr.length - 1) {
                        zzcbbArr[length] = new zzcbb();
                        adg.zza(zzcbbArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzcbbArr[length] = new zzcbb();
                    adg.zza(zzcbbArr[length]);
                    this.zzbgv = zzcbbArr;
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
        if (this.type != null && !this.type.equals("")) {
            adh.zzl(1, this.type);
        }
        if (this.zzbgv != null && this.zzbgv.length > 0) {
            for (zzcbb zzcbb : this.zzbgv) {
                if (zzcbb != null) {
                    adh.zza(2, (adp) zzcbb);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.type != null && !this.type.equals("")) {
            zzn += adh.zzm(1, this.type);
        }
        if (this.zzbgv == null || this.zzbgv.length <= 0) {
            return zzn;
        }
        int i = zzn;
        for (zzcbb zzcbb : this.zzbgv) {
            if (zzcbb != null) {
                i += adh.zzb(2, (adp) zzcbb);
            }
        }
        return i;
    }
}
