package com.google.android.gms.internal;

import java.io.IOException;

public final class he extends adj<he> {
    public hf[] zzbTH = hf.zzEa();

    public he() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static he zzy(byte[] bArr) throws ado {
        return (he) adp.zza(new he(), bArr);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof he)) {
            return false;
        }
        he heVar = (he) obj;
        if (!adn.equals((Object[]) this.zzbTH, (Object[]) heVar.zzbTH)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? heVar.zzcso == null || heVar.zzcso.isEmpty() : this.zzcso.equals(heVar.zzcso);
    }

    public final int hashCode() {
        return ((this.zzcso == null || this.zzcso.isEmpty()) ? 0 : this.zzcso.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + adn.hashCode((Object[]) this.zzbTH)) * 31);
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    int zzb = ads.zzb(adg, 10);
                    int length = this.zzbTH == null ? 0 : this.zzbTH.length;
                    hf[] hfVarArr = new hf[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzbTH, 0, hfVarArr, 0, length);
                    }
                    while (length < hfVarArr.length - 1) {
                        hfVarArr[length] = new hf();
                        adg.zza(hfVarArr[length]);
                        adg.zzLA();
                        length++;
                    }
                    hfVarArr[length] = new hf();
                    adg.zza(hfVarArr[length]);
                    this.zzbTH = hfVarArr;
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
        if (this.zzbTH != null && this.zzbTH.length > 0) {
            for (hf hfVar : this.zzbTH) {
                if (hfVar != null) {
                    adh.zza(1, (adp) hfVar);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.zzbTH != null && this.zzbTH.length > 0) {
            for (hf hfVar : this.zzbTH) {
                if (hfVar != null) {
                    zzn += adh.zzb(1, (adp) hfVar);
                }
            }
        }
        return zzn;
    }
}
