package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjo extends adj<zzcjo> {
    private static volatile zzcjo[] zzbuR;
    public zzcjr zzbuS = null;
    public zzcjp zzbuT = null;
    public Boolean zzbuU = null;
    public String zzbuV = null;

    public zzcjo() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcjo[] zzzw() {
        if (zzbuR == null) {
            synchronized (adn.zzcsw) {
                if (zzbuR == null) {
                    zzbuR = new zzcjo[0];
                }
            }
        }
        return zzbuR;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjo)) {
            return false;
        }
        zzcjo zzcjo = (zzcjo) obj;
        if (this.zzbuS == null) {
            if (zzcjo.zzbuS != null) {
                return false;
            }
        } else if (!this.zzbuS.equals(zzcjo.zzbuS)) {
            return false;
        }
        if (this.zzbuT == null) {
            if (zzcjo.zzbuT != null) {
                return false;
            }
        } else if (!this.zzbuT.equals(zzcjo.zzbuT)) {
            return false;
        }
        if (this.zzbuU == null) {
            if (zzcjo.zzbuU != null) {
                return false;
            }
        } else if (!this.zzbuU.equals(zzcjo.zzbuU)) {
            return false;
        }
        if (this.zzbuV == null) {
            if (zzcjo.zzbuV != null) {
                return false;
            }
        } else if (!this.zzbuV.equals(zzcjo.zzbuV)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjo.zzcso == null || zzcjo.zzcso.isEmpty() : this.zzcso.equals(zzcjo.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbuV == null ? 0 : this.zzbuV.hashCode()) + (((this.zzbuU == null ? 0 : this.zzbuU.hashCode()) + (((this.zzbuT == null ? 0 : this.zzbuT.hashCode()) + (((this.zzbuS == null ? 0 : this.zzbuS.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31;
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
                    if (this.zzbuS == null) {
                        this.zzbuS = new zzcjr();
                    }
                    adg.zza(this.zzbuS);
                    continue;
                case 18:
                    if (this.zzbuT == null) {
                        this.zzbuT = new zzcjp();
                    }
                    adg.zza(this.zzbuT);
                    continue;
                case 24:
                    this.zzbuU = Boolean.valueOf(adg.zzLD());
                    continue;
                case 34:
                    this.zzbuV = adg.readString();
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
        if (this.zzbuS != null) {
            adh.zza(1, (adp) this.zzbuS);
        }
        if (this.zzbuT != null) {
            adh.zza(2, (adp) this.zzbuT);
        }
        if (this.zzbuU != null) {
            adh.zzk(3, this.zzbuU.booleanValue());
        }
        if (this.zzbuV != null) {
            adh.zzl(4, this.zzbuV);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbuS != null) {
            zzn += adh.zzb(1, (adp) this.zzbuS);
        }
        if (this.zzbuT != null) {
            zzn += adh.zzb(2, (adp) this.zzbuT);
        }
        if (this.zzbuU != null) {
            this.zzbuU.booleanValue();
            zzn += adh.zzct(3) + 1;
        }
        return this.zzbuV != null ? zzn + adh.zzm(4, this.zzbuV) : zzn;
    }
}
