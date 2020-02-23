package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class ack extends adj<ack> {
    private static volatile ack[] zzcqA;
    public byte[] content = ads.zzcsI;
    public String type = "";
    public String zzbxU = "";

    public ack() {
        this.zzcsx = -1;
    }

    public static ack[] zzLo() {
        if (zzcqA == null) {
            synchronized (adn.zzcsw) {
                if (zzcqA == null) {
                    zzcqA = new ack[0];
                }
            }
        }
        return zzcqA;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ack)) {
            return false;
        }
        ack ack = (ack) obj;
        if (this.zzbxU == null) {
            if (ack.zzbxU != null) {
                return false;
            }
        } else if (!this.zzbxU.equals(ack.zzbxU)) {
            return false;
        }
        if (this.type == null) {
            if (ack.type != null) {
                return false;
            }
        } else if (!this.type.equals(ack.type)) {
            return false;
        }
        if (!Arrays.equals(this.content, ack.content)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? ack.zzcso == null || ack.zzcso.isEmpty() : this.zzcso.equals(ack.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((this.type == null ? 0 : this.type.hashCode()) + (((this.zzbxU == null ? 0 : this.zzbxU.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31)) * 31) + Arrays.hashCode(this.content)) * 31;
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
                    this.zzbxU = adg.readString();
                    continue;
                case 18:
                    this.type = adg.readString();
                    continue;
                case 26:
                    this.content = adg.readBytes();
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
        if (this.zzbxU != null && !this.zzbxU.equals("")) {
            adh.zzl(1, this.zzbxU);
        }
        if (this.type != null && !this.type.equals("")) {
            adh.zzl(2, this.type);
        }
        if (!Arrays.equals(this.content, ads.zzcsI)) {
            adh.zzb(3, this.content);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbxU != null && !this.zzbxU.equals("")) {
            zzn += adh.zzm(1, this.zzbxU);
        }
        if (this.type != null && !this.type.equals("")) {
            zzn += adh.zzm(2, this.type);
        }
        return !Arrays.equals(this.content, ads.zzcsI) ? zzn + adh.zzc(3, this.content) : zzn;
    }
}
