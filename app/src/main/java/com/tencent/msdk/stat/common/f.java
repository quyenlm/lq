package com.tencent.msdk.stat.common;

public class f {
    static final /* synthetic */ boolean a = (!f.class.desiredAssertionStatus());

    private f() {
    }

    public static byte[] a(byte[] bArr, int i) {
        return a(bArr, 0, bArr.length, i);
    }

    public static byte[] a(byte[] bArr, int i, int i2, int i3) {
        h hVar = new h(i3, new byte[((i2 * 3) / 4)]);
        if (!hVar.a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (hVar.b == hVar.a.length) {
            return hVar.a;
        } else {
            byte[] bArr2 = new byte[hVar.b];
            System.arraycopy(hVar.a, 0, bArr2, 0, hVar.b);
            return bArr2;
        }
    }

    public static byte[] b(byte[] bArr, int i) {
        return b(bArr, 0, bArr.length, i);
    }

    public static byte[] b(byte[] bArr, int i, int i2, int i3) {
        i iVar = new i(i3, (byte[]) null);
        int i4 = (i2 / 3) * 4;
        if (!iVar.d) {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (iVar.e && i2 > 0) {
            i4 += (iVar.f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        iVar.a = new byte[i4];
        iVar.a(bArr, i, i2, true);
        if (a || iVar.b == i4) {
            return iVar.a;
        }
        throw new AssertionError();
    }
}
