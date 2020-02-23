package com.google.android.gms.internal;

import java.io.IOException;

public final class zzckb extends adj<zzckb> {
    private static volatile zzckb[] zzbwg;
    public String name = null;
    public String zzaIF = null;
    private Float zzbuA = null;
    public Double zzbuB = null;
    public Long zzbvA = null;
    public Long zzbwh = null;

    public zzckb() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzckb[] zzzE() {
        if (zzbwg == null) {
            synchronized (adn.zzcsw) {
                if (zzbwg == null) {
                    zzbwg = new zzckb[0];
                }
            }
        }
        return zzbwg;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzckb)) {
            return false;
        }
        zzckb zzckb = (zzckb) obj;
        if (this.zzbwh == null) {
            if (zzckb.zzbwh != null) {
                return false;
            }
        } else if (!this.zzbwh.equals(zzckb.zzbwh)) {
            return false;
        }
        if (this.name == null) {
            if (zzckb.name != null) {
                return false;
            }
        } else if (!this.name.equals(zzckb.name)) {
            return false;
        }
        if (this.zzaIF == null) {
            if (zzckb.zzaIF != null) {
                return false;
            }
        } else if (!this.zzaIF.equals(zzckb.zzaIF)) {
            return false;
        }
        if (this.zzbvA == null) {
            if (zzckb.zzbvA != null) {
                return false;
            }
        } else if (!this.zzbvA.equals(zzckb.zzbvA)) {
            return false;
        }
        if (this.zzbuA == null) {
            if (zzckb.zzbuA != null) {
                return false;
            }
        } else if (!this.zzbuA.equals(zzckb.zzbuA)) {
            return false;
        }
        if (this.zzbuB == null) {
            if (zzckb.zzbuB != null) {
                return false;
            }
        } else if (!this.zzbuB.equals(zzckb.zzbuB)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzckb.zzcso == null || zzckb.zzcso.isEmpty() : this.zzcso.equals(zzckb.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzbuB == null ? 0 : this.zzbuB.hashCode()) + (((this.zzbuA == null ? 0 : this.zzbuA.hashCode()) + (((this.zzbvA == null ? 0 : this.zzbvA.hashCode()) + (((this.zzaIF == null ? 0 : this.zzaIF.hashCode()) + (((this.name == null ? 0 : this.name.hashCode()) + (((this.zzbwh == null ? 0 : this.zzbwh.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31)) * 31)) * 31)) * 31)) * 31;
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
                    this.zzbwh = Long.valueOf(adg.zzLG());
                    continue;
                case 18:
                    this.name = adg.readString();
                    continue;
                case 26:
                    this.zzaIF = adg.readString();
                    continue;
                case 32:
                    this.zzbvA = Long.valueOf(adg.zzLG());
                    continue;
                case 45:
                    this.zzbuA = Float.valueOf(Float.intBitsToFloat(adg.zzLH()));
                    continue;
                case 49:
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
        if (this.zzbwh != null) {
            adh.zzb(1, this.zzbwh.longValue());
        }
        if (this.name != null) {
            adh.zzl(2, this.name);
        }
        if (this.zzaIF != null) {
            adh.zzl(3, this.zzaIF);
        }
        if (this.zzbvA != null) {
            adh.zzb(4, this.zzbvA.longValue());
        }
        if (this.zzbuA != null) {
            adh.zzc(5, this.zzbuA.floatValue());
        }
        if (this.zzbuB != null) {
            adh.zza(6, this.zzbuB.doubleValue());
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbwh != null) {
            zzn += adh.zze(1, this.zzbwh.longValue());
        }
        if (this.name != null) {
            zzn += adh.zzm(2, this.name);
        }
        if (this.zzaIF != null) {
            zzn += adh.zzm(3, this.zzaIF);
        }
        if (this.zzbvA != null) {
            zzn += adh.zze(4, this.zzbvA.longValue());
        }
        if (this.zzbuA != null) {
            this.zzbuA.floatValue();
            zzn += adh.zzct(5) + 4;
        }
        if (this.zzbuB == null) {
            return zzn;
        }
        this.zzbuB.doubleValue();
        return zzn + adh.zzct(6) + 8;
    }
}
