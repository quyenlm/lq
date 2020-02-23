package com.google.android.gms.internal;

public final class adl implements Cloneable {
    private static final adm zzcsq = new adm();
    private int mSize;
    private boolean zzcsr;
    private int[] zzcss;
    private adm[] zzcst;

    adl() {
        this(10);
    }

    private adl(int i) {
        this.zzcsr = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzcss = new int[idealIntArraySize];
        this.zzcst = new adm[idealIntArraySize];
        this.mSize = 0;
    }

    private static int idealIntArraySize(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            } else if (i2 <= (1 << i3) - 12) {
                i2 = (1 << i3) - 12;
                break;
            } else {
                i3++;
            }
        }
        return i2 / 4;
    }

    private final int zzcz(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzcss[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return i2 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int i = this.mSize;
        adl adl = new adl(i);
        System.arraycopy(this.zzcss, 0, adl.zzcss, 0, i);
        for (int i2 = 0; i2 < i; i2++) {
            if (this.zzcst[i2] != null) {
                adl.zzcst[i2] = (adm) this.zzcst[i2].clone();
            }
        }
        adl.mSize = i;
        return adl;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof adl)) {
            return false;
        }
        adl adl = (adl) obj;
        if (this.mSize != adl.mSize) {
            return false;
        }
        int[] iArr = this.zzcss;
        int[] iArr2 = adl.zzcss;
        int i = this.mSize;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            } else if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            adm[] admArr = this.zzcst;
            adm[] admArr2 = adl.zzcst;
            int i3 = this.mSize;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!admArr[i4].equals(admArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzcss[i2]) * 31) + this.zzcst[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return this.mSize == 0;
    }

    /* access modifiers changed from: package-private */
    public final int size() {
        return this.mSize;
    }

    /* access modifiers changed from: package-private */
    public final void zza(int i, adm adm) {
        int zzcz = zzcz(i);
        if (zzcz >= 0) {
            this.zzcst[zzcz] = adm;
            return;
        }
        int i2 = zzcz ^ -1;
        if (i2 >= this.mSize || this.zzcst[i2] != zzcsq) {
            if (this.mSize >= this.zzcss.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                int[] iArr = new int[idealIntArraySize];
                adm[] admArr = new adm[idealIntArraySize];
                System.arraycopy(this.zzcss, 0, iArr, 0, this.zzcss.length);
                System.arraycopy(this.zzcst, 0, admArr, 0, this.zzcst.length);
                this.zzcss = iArr;
                this.zzcst = admArr;
            }
            if (this.mSize - i2 != 0) {
                System.arraycopy(this.zzcss, i2, this.zzcss, i2 + 1, this.mSize - i2);
                System.arraycopy(this.zzcst, i2, this.zzcst, i2 + 1, this.mSize - i2);
            }
            this.zzcss[i2] = i;
            this.zzcst[i2] = adm;
            this.mSize++;
            return;
        }
        this.zzcss[i2] = i;
        this.zzcst[i2] = adm;
    }

    /* access modifiers changed from: package-private */
    public final adm zzcx(int i) {
        int zzcz = zzcz(i);
        if (zzcz < 0 || this.zzcst[zzcz] == zzcsq) {
            return null;
        }
        return this.zzcst[zzcz];
    }

    /* access modifiers changed from: package-private */
    public final adm zzcy(int i) {
        return this.zzcst[i];
    }
}
