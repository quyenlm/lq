package com.google.android.gms.internal;

import java.io.IOException;

public final class abl extends adj<abl> {
    public long timestamp = 0;
    public abo[] zzcnO = abo.zzKK();
    public byte[][] zzcnP = ads.zzcsH;

    public abl() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof abl)) {
            return false;
        }
        abl abl = (abl) obj;
        if (!adn.equals((Object[]) this.zzcnO, (Object[]) abl.zzcnO)) {
            return false;
        }
        if (this.timestamp != abl.timestamp) {
            return false;
        }
        if (!adn.zza(this.zzcnP, abl.zzcnP)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? abl.zzcso == null || abl.zzcso.isEmpty() : this.zzcso.equals(abl.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzcnO)) * 31) + ((int) (this.timestamp ^ (this.timestamp >>> 32)))) * 31) + adn.zzc(this.zzcnP)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzcnO == null ? 0 : this.zzcnO.length;
                    abo[] aboArr = new abo[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzcnO, 0, aboArr, 0, length);
                    }
                    while (length < aboArr.length - 1) {
                        aboArr[length] = new abo();
                        adg.zza(aboArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    aboArr[length] = new abo();
                    adg.zza(aboArr[length]);
                    this.zzcnO = aboArr;
                    continue;
                case 17:
                    this.timestamp = adg.zzLI();
                    continue;
                case 26:
                    int zzb2 = ads.zzb(adg, 26);
                    int length2 = this.zzcnP == null ? 0 : this.zzcnP.length;
                    byte[][] bArr = new byte[(zzb2 + length2)][];
                    if (length2 != 0) {
                        System.arraycopy(this.zzcnP, 0, bArr, 0, length2);
                    }
                    while (length2 < bArr.length - 1) {
                        bArr[length2] = adg.readBytes();
                        adg.zzLA();
                        length2++;
                    }
                    bArr[length2] = adg.readBytes();
                    this.zzcnP = bArr;
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
        if (this.zzcnO != null && this.zzcnO.length > 0) {
            for (abo abo : this.zzcnO) {
                if (abo != null) {
                    adh.zza(1, (adp) abo);
                }
            }
        }
        if (this.timestamp != 0) {
            adh.zzc(2, this.timestamp);
        }
        if (this.zzcnP != null && this.zzcnP.length > 0) {
            for (byte[] bArr : this.zzcnP) {
                if (bArr != null) {
                    adh.zzb(3, bArr);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzcnO != null && this.zzcnO.length > 0) {
            for (abo abo : this.zzcnO) {
                if (abo != null) {
                    zzn += adh.zzb(1, (adp) abo);
                }
            }
        }
        if (this.timestamp != 0) {
            zzn += adh.zzct(2) + 8;
        }
        if (this.zzcnP == null || this.zzcnP.length <= 0) {
            return zzn;
        }
        int i = 0;
        int i2 = 0;
        for (byte[] bArr : this.zzcnP) {
            if (bArr != null) {
                i2++;
                i += adh.zzJ(bArr);
            }
        }
        return zzn + i + (i2 * 1);
    }
}
