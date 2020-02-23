package com.google.android.gms.internal;

import java.io.IOException;

public final class zzcju extends adj<zzcju> {
    private static volatile zzcju[] zzbvq;
    public String key = null;
    public String value = null;

    public zzcju() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static zzcju[] zzzz() {
        if (zzbvq == null) {
            synchronized (adn.zzcsw) {
                if (zzbvq == null) {
                    zzbvq = new zzcju[0];
                }
            }
        }
        return zzbvq;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzcju)) {
            return false;
        }
        zzcju zzcju = (zzcju) obj;
        if (this.key == null) {
            if (zzcju.key != null) {
                return false;
            }
        } else if (!this.key.equals(zzcju.key)) {
            return false;
        }
        if (this.value == null) {
            if (zzcju.value != null) {
                return false;
            }
        } else if (!this.value.equals(zzcju.value)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? zzcju.zzcso == null || zzcju.zzcso.isEmpty() : this.zzcso.equals(zzcju.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.value == null ? 0 : this.value.hashCode()) + (((this.key == null ? 0 : this.key.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31;
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
                    this.key = adg.readString();
                    continue;
                case 18:
                    this.value = adg.readString();
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
        if (this.key != null) {
            adh.zzl(1, this.key);
        }
        if (this.value != null) {
            adh.zzl(2, this.value);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.key != null) {
            zzn += adh.zzm(1, this.key);
        }
        return this.value != null ? zzn + adh.zzm(2, this.value) : zzn;
    }
}
