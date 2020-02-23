package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcjx extends adj<zzcjx> {
    private static volatile zzcjx[] zzbvz;
    public String name = null;
    public String zzaIF = null;
    private Float zzbuA = null;
    public Double zzbuB = null;
    public Long zzbvA = null;

    public zzcjx() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcjx[] zzzC() {
        if (zzbvz == null) {
            synchronized (adn.zzcsw) {
                if (zzbvz == null) {
                    zzbvz = new zzcjx[0];
                }
            }
        }
        return zzbvz;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcjx)) {
            return false;
        }
        zzcjx zzcjx = (zzcjx) obj;
        if (this.name == null) {
            if (zzcjx.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzcjx.name)) {
            return false;
        }
        if (this.zzaIF == null) {
            if (zzcjx.zzaIF != null) {
                return false;
            }
        } else if (!this.zzaIF.equals(zzcjx.zzaIF)) {
            return false;
        }
        if (this.zzbvA == null) {
            if (zzcjx.zzbvA != null) {
                return false;
            }
        } else if (!this.zzbvA.equals(zzcjx.zzbvA)) {
            return false;
        }
        if (this.zzbuA == null) {
            if (zzcjx.zzbuA != null) {
                return false;
            }
        } else if (!this.zzbuA.equals(zzcjx.zzbuA)) {
            return false;
        }
        if (this.zzbuB == null) {
            if (zzcjx.zzbuB != null) {
                return false;
            }
        } else if (!this.zzbuB.equals(zzcjx.zzbuB)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcjx.zzcso == null || zzcjx.zzcso.isEmpty() : this.zzcso.equals(zzcjx.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbuB == null ? 0 : this.zzbuB.hashCode()) + (((this.zzbuA == null ? 0 : this.zzbuA.hashCode()) + (((this.zzbvA == null ? 0 : this.zzbvA.hashCode()) + (((this.zzaIF == null ? 0 : this.zzaIF.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
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
                    this.name = adg.readString();
                    continue;
                case 18:
                    this.zzaIF = adg.readString();
                    continue;
                case 24:
                    this.zzbvA = Long.valueOf(adg.zzLG());
                    continue;
                case 37:
                    this.zzbuA = Float.valueOf(Float.intBitsToFloat(adg.zzLH()));
                    continue;
                case 41:
                    this.zzbuB = Double.valueOf(Double.longBitsToDouble(adg.zzLI()));
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
        if (this.name != null) {
            adh.zzl(1, this.name);
        }
        if (this.zzaIF != null) {
            adh.zzl(2, this.zzaIF);
        }
        if (this.zzbvA != null) {
            adh.zzb(3, this.zzbvA.longValue());
        }
        if (this.zzbuA != null) {
            adh.zzc(4, this.zzbuA.floatValue());
        }
        if (this.zzbuB != null) {
            adh.zza(5, this.zzbuB.doubleValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.name != null) {
            zzn += adh.zzm(1, this.name);
        }
        if (this.zzaIF != null) {
            zzn += adh.zzm(2, this.zzaIF);
        }
        if (this.zzbvA != null) {
            zzn += adh.zze(3, this.zzbvA.longValue());
        }
        if (this.zzbuA != null) {
            this.zzbuA.floatValue();
            zzn += adh.zzct(4) + 4;
        }
        if (this.zzbuB == null) {
            return zzn;
        }
        this.zzbuB.doubleValue();
        return zzn + adh.zzct(5) + 8;
    }
}
