package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public final class aei extends adj<aei> implements Cloneable {
    private byte[] zzctM = ads.zzcsI;
    private String zzctN = "";
    private byte[][] zzctO = ads.zzcsH;
    private boolean zzctP = false;

    public aei() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzMc */
    public aei clone() {
        try {
            aei aei = (aei) super.clone();
            if (this.zzctO != null && this.zzctO.length > 0) {
                aei.zzctO = (byte[][]) this.zzctO.clone();
            }
            return aei;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aei)) {
            return false;
        }
        aei aei = (aei) obj;
        if (!Arrays.equals(this.zzctM, aei.zzctM)) {
            return false;
        }
        if (this.zzctN == null) {
            if (aei.zzctN != null) {
                return false;
            }
        } else if (!this.zzctN.equals(aei.zzctN)) {
            return false;
        }
        if (!adn.zza(this.zzctO, aei.zzctO)) {
            return false;
        }
        if (this.zzctP != aei.zzctP) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aei.zzcso == null || aei.zzcso.isEmpty() : this.zzcso.equals(aei.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((this.zzctP ? 1231 : 1237) + (((((this.zzctN == null ? 0 : this.zzctN.hashCode()) + ((((getClass().getName().hashCode() + 527) * 31) + Arrays.hashCode(this.zzctM)) * 31)) * 31) + adn.zzc(this.zzctO)) * 31)) * 31;
        if (this.zzcso != null && !this.zzcso.isEmpty()) {
            i = this.zzcso.hashCode();
        }
        return hashCode + i;
    }

    public final /* synthetic */ adj zzLN() throws CloneNotSupportedException {
        return (aei) clone();
    }

    public final /* synthetic */ adp zzLO() throws CloneNotSupportedException {
        return (aei) clone();
    }

    public final /* synthetic */ adp zza(adg adg) throws IOException {
        while (true) {
            int zzLA = adg.zzLA();
            switch (zzLA) {
                case 0:
                    break;
                case 10:
                    this.zzctM = adg.readBytes();
                    continue;
                case 18:
                    int zzb = ads.zzb(adg, 18);
                    int length = this.zzctO == null ? 0 : this.zzctO.length;
                    byte[][] bArr = new byte[(zzb + length)][];
                    if (length != 0) {
                        System.arraycopy(this.zzctO, 0, bArr, 0, length);
                    }
                    while (length < bArr.length - 1) {
                        bArr[length] = adg.readBytes();
                        adg.zzLA();
                        length++;
                    }
                    bArr[length] = adg.readBytes();
                    this.zzctO = bArr;
                    continue;
                case 24:
                    this.zzctP = adg.zzLD();
                    continue;
                case 34:
                    this.zzctN = adg.readString();
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
        if (!Arrays.equals(this.zzctM, ads.zzcsI)) {
            adh.zzb(1, this.zzctM);
        }
        if (this.zzctO != null && this.zzctO.length > 0) {
            for (byte[] bArr : this.zzctO) {
                if (bArr != null) {
                    adh.zzb(2, bArr);
                }
            }
        }
        if (this.zzctP) {
            adh.zzk(3, this.zzctP);
        }
        if (this.zzctN != null && !this.zzctN.equals("")) {
            adh.zzl(4, this.zzctN);
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int i;
        int zzn = super.zzn();
        if (!Arrays.equals(this.zzctM, ads.zzcsI)) {
            zzn += adh.zzc(1, this.zzctM);
        }
        if (this.zzctO != null && this.zzctO.length > 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i2 < this.zzctO.length) {
                byte[] bArr = this.zzctO[i2];
                if (bArr != null) {
                    i4++;
                    i = adh.zzJ(bArr) + i3;
                } else {
                    i = i3;
                }
                i2++;
                i3 = i;
            }
            zzn = zzn + i3 + (i4 * 1);
        }
        if (this.zzctP) {
            zzn += adh.zzct(3) + 1;
        }
        return (this.zzctN == null || this.zzctN.equals("")) ? zzn : zzn + adh.zzm(4, this.zzctN);
    }
}
