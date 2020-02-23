package com.google.android.gms.internal;

import java.io.IOException;

public final class zzbq extends adj<zzbq> {
    public zzbp[] zzlA = zzbp.zzt();
    public zzbn zzlB = null;
    public String zzlC = "";

    public zzbq() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbq)) {
            return false;
        }
        zzbq zzbq = (zzbq) obj;
        if (!adn.equals((Object[]) this.zzlA, (Object[]) zzbq.zzlA)) {
            return false;
        }
        if (this.zzlB == null) {
            if (zzbq.zzlB != null) {
                return false;
            }
        } else if (!this.zzlB.equals(zzbq.zzlB)) {
            return false;
        }
        if (this.zzlC == null) {
            if (zzbq.zzlC != null) {
                return false;
            }
        } else if (!this.zzlC.equals(zzbq.zzlC)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzbq.zzcso == null || zzbq.zzcso.isEmpty() : this.zzcso.equals(zzbq.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzlC == null ? 0 : this.zzlC.hashCode()) + (((this.zzlB == null ? 0 : this.zzlB.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzlA)) * 31)) * 31)) * 31;
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
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzlA == null ? 0 : this.zzlA.length;
                    zzbp[] zzbpArr = new zzbp[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzlA, 0, zzbpArr, 0, length);
                    }
                    while (length < zzbpArr.length - 1) {
                        zzbpArr[length] = new zzbp();
                        adg.zza(zzbpArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    zzbpArr[length] = new zzbp();
                    adg.zza(zzbpArr[length]);
                    this.zzlA = zzbpArr;
                    continue;
                case 18:
                    if (this.zzlB == null) {
                        this.zzlB = new zzbn();
                    }
                    adg.zza(this.zzlB);
                    continue;
                case 26:
                    this.zzlC = adg.readString();
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
        if (this.zzlA != null && this.zzlA.length > 0) {
            for (zzbp zzbp : this.zzlA) {
                if (zzbp != null) {
                    adh.zza(1, (adp) zzbp);
                }
            }
        }
        if (this.zzlB != null) {
            adh.zza(2, (adp) this.zzlB);
        }
        if (this.zzlC != null && !this.zzlC.equals("")) {
            adh.zzl(3, this.zzlC);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzlA != null && this.zzlA.length > 0) {
            for (zzbp zzbp : this.zzlA) {
                if (zzbp != null) {
                    zzn += adh.zzb(1, (adp) zzbp);
                }
            }
        }
        if (this.zzlB != null) {
            zzn += adh.zzb(2, (adp) this.zzlB);
        }
        return (this.zzlC == null || this.zzlC.equals("")) ? zzn : zzn + adh.zzm(3, this.zzlC);
    }
}
