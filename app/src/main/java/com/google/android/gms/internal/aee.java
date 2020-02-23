package com.google.android.gms.internal;

import java.io.IOException;

public final class aee extends adj<aee> {
    private static volatile aee[] zzctz;
    private String name = "";
    private boolean[] zzctA = ads.zzcsG;
    private long[] zzctB = ads.zzcsD;
    private String[] zzctC = ads.EMPTY_STRING_ARRAY;
    private aef[] zzctD = aef.zzLZ();

    public aee() {
        this.zzcso = null;
        this.zzcsx = -1;
    }

    public static aee[] zzLY() {
        if (zzctz == null) {
            synchronized (adn.zzcsw) {
                if (zzctz == null) {
                    zzctz = new aee[0];
                }
            }
        }
        return zzctz;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aee)) {
            return false;
        }
        aee aee = (aee) obj;
        if (this.name == null) {
            if (aee.name != null) {
                return false;
            }
        } else if (!this.name.equals(aee.name)) {
            return false;
        }
        if (!adn.equals(this.zzctA, aee.zzctA)) {
            return false;
        }
        if (!adn.equals(this.zzctB, aee.zzctB)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzctC, (Object[]) aee.zzctC)) {
            return false;
        }
        if (!adn.equals((Object[]) this.zzctD, (Object[]) aee.zzctD)) {
            return false;
        }
        return (this.zzcso == null || this.zzcso.isEmpty()) ? aee.zzcso == null || aee.zzcso.isEmpty() : this.zzcso.equals(aee.zzcso);
    }

    public final int hashCode() {
        int i = 0;
        int hashCode = ((((((((((this.name == null ? 0 : this.name.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + adn.hashCode(this.zzctA)) * 31) + adn.hashCode(this.zzctB)) * 31) + adn.hashCode((Object[]) this.zzctC)) * 31) + adn.hashCode((Object[]) this.zzctD)) * 31;
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
                case 16:
                    int zzb = ads.zzb(adg, 16);
                    int length = this.zzctA == null ? 0 : this.zzctA.length;
                    boolean[] zArr = new boolean[(zzb + length)];
                    if (length != 0) {
                        System.arraycopy(this.zzctA, 0, zArr, 0, length);
                    }
                    while (length < zArr.length - 1) {
                        zArr[length] = adg.zzLD();
                        adg.zzLA();
                        length++;
                    }
                    zArr[length] = adg.zzLD();
                    this.zzctA = zArr;
                    continue;
                case 18:
                    int zzcn = adg.zzcn(adg.zzLF());
                    int position = adg.getPosition();
                    int i = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLD();
                        i++;
                    }
                    adg.zzcp(position);
                    int length2 = this.zzctA == null ? 0 : this.zzctA.length;
                    boolean[] zArr2 = new boolean[(i + length2)];
                    if (length2 != 0) {
                        System.arraycopy(this.zzctA, 0, zArr2, 0, length2);
                    }
                    while (length2 < zArr2.length) {
                        zArr2[length2] = adg.zzLD();
                        length2++;
                    }
                    this.zzctA = zArr2;
                    adg.zzco(zzcn);
                    continue;
                case 24:
                    int zzb2 = ads.zzb(adg, 24);
                    int length3 = this.zzctB == null ? 0 : this.zzctB.length;
                    long[] jArr = new long[(zzb2 + length3)];
                    if (length3 != 0) {
                        System.arraycopy(this.zzctB, 0, jArr, 0, length3);
                    }
                    while (length3 < jArr.length - 1) {
                        jArr[length3] = adg.zzLB();
                        adg.zzLA();
                        length3++;
                    }
                    jArr[length3] = adg.zzLB();
                    this.zzctB = jArr;
                    continue;
                case 26:
                    int zzcn2 = adg.zzcn(adg.zzLF());
                    int position2 = adg.getPosition();
                    int i2 = 0;
                    while (adg.zzLK() > 0) {
                        adg.zzLB();
                        i2++;
                    }
                    adg.zzcp(position2);
                    int length4 = this.zzctB == null ? 0 : this.zzctB.length;
                    long[] jArr2 = new long[(i2 + length4)];
                    if (length4 != 0) {
                        System.arraycopy(this.zzctB, 0, jArr2, 0, length4);
                    }
                    while (length4 < jArr2.length) {
                        jArr2[length4] = adg.zzLB();
                        length4++;
                    }
                    this.zzctB = jArr2;
                    adg.zzco(zzcn2);
                    continue;
                case 34:
                    int zzb3 = ads.zzb(adg, 34);
                    int length5 = this.zzctC == null ? 0 : this.zzctC.length;
                    String[] strArr = new String[(zzb3 + length5)];
                    if (length5 != 0) {
                        System.arraycopy(this.zzctC, 0, strArr, 0, length5);
                    }
                    while (length5 < strArr.length - 1) {
                        strArr[length5] = adg.readString();
                        adg.zzLA();
                        length5++;
                    }
                    strArr[length5] = adg.readString();
                    this.zzctC = strArr;
                    continue;
                case 42:
                    int zzb4 = ads.zzb(adg, 42);
                    int length6 = this.zzctD == null ? 0 : this.zzctD.length;
                    aef[] aefArr = new aef[(zzb4 + length6)];
                    if (length6 != 0) {
                        System.arraycopy(this.zzctD, 0, aefArr, 0, length6);
                    }
                    while (length6 < aefArr.length - 1) {
                        aefArr[length6] = new aef();
                        adg.zza(aefArr[length6]);
                        adg.zzLA();
                        length6++;
                    }
                    aefArr[length6] = new aef();
                    adg.zza(aefArr[length6]);
                    this.zzctD = aefArr;
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
        if (this.name != null && !this.name.equals("")) {
            adh.zzl(1, this.name);
        }
        if (this.zzctA != null && this.zzctA.length > 0) {
            for (boolean zzk : this.zzctA) {
                adh.zzk(2, zzk);
            }
        }
        if (this.zzctB != null && this.zzctB.length > 0) {
            for (long zzb : this.zzctB) {
                adh.zzb(3, zzb);
            }
        }
        if (this.zzctC != null && this.zzctC.length > 0) {
            for (String str : this.zzctC) {
                if (str != null) {
                    adh.zzl(4, str);
                }
            }
        }
        if (this.zzctD != null && this.zzctD.length > 0) {
            for (aef aef : this.zzctD) {
                if (aef != null) {
                    adh.zza(5, (adp) aef);
                }
            }
        }
        super.zza(adh);
    }

    /* access modifiers changed from: protected */
    public final int zzn() {
        int zzn = super.zzn();
        if (this.name != null && !this.name.equals("")) {
            zzn += adh.zzm(1, this.name);
        }
        if (this.zzctA != null && this.zzctA.length > 0) {
            zzn = zzn + (this.zzctA.length * 1) + (this.zzctA.length * 1);
        }
        if (this.zzctB != null && this.zzctB.length > 0) {
            int i = 0;
            for (long zzaP : this.zzctB) {
                i += adh.zzaP(zzaP);
            }
            zzn = zzn + i + (this.zzctB.length * 1);
        }
        if (this.zzctC != null && this.zzctC.length > 0) {
            int i2 = 0;
            int i3 = 0;
            for (String str : this.zzctC) {
                if (str != null) {
                    i3++;
                    i2 += adh.zzhQ(str);
                }
            }
            zzn = zzn + i2 + (i3 * 1);
        }
        if (this.zzctD != null && this.zzctD.length > 0) {
            for (aef aef : this.zzctD) {
                if (aef != null) {
                    zzn += adh.zzb(5, (adp) aef);
                }
            }
        }
        return zzn;
    }
}
