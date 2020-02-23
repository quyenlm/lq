package com.google.android.gms.internal;

import java.nio.charset.Charset;
import java.util.Arrays;

public final class adn {
    private static Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    protected static final Charset UTF_8 = Charset.forName("UTF-8");
    public static final Object zzcsw = new Object();

    public static boolean equals(float[] fArr, float[] fArr2) {
        return (fArr == null || fArr.length == 0) ? fArr2 == null || fArr2.length == 0 : Arrays.equals(fArr, fArr2);
    }

    public static boolean equals(int[] iArr, int[] iArr2) {
        return (iArr == null || iArr.length == 0) ? iArr2 == null || iArr2.length == 0 : Arrays.equals(iArr, iArr2);
    }

    public static boolean equals(long[] jArr, long[] jArr2) {
        return (jArr == null || jArr.length == 0) ? jArr2 == null || jArr2.length == 0 : Arrays.equals(jArr, jArr2);
    }

    public static boolean equals(Object[] objArr, Object[] objArr2) {
        int length = objArr == null ? 0 : objArr.length;
        int length2 = objArr2 == null ? 0 : objArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length || objArr[i2] != null) {
                int i3 = i;
                while (i3 < length2 && objArr2[i3] == null) {
                    i3++;
                }
                boolean z = i2 >= length;
                boolean z2 = i3 >= length2;
                if (z && z2) {
                    return true;
                }
                if (z != z2 || !objArr[i2].equals(objArr2[i3])) {
                    return false;
                }
                i = i3 + 1;
                i2++;
            } else {
                i2++;
            }
        }
    }

    public static boolean equals(boolean[] zArr, boolean[] zArr2) {
        return (zArr == null || zArr.length == 0) ? zArr2 == null || zArr2.length == 0 : Arrays.equals(zArr, zArr2);
    }

    public static int hashCode(float[] fArr) {
        if (fArr == null || fArr.length == 0) {
            return 0;
        }
        return Arrays.hashCode(fArr);
    }

    public static int hashCode(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return 0;
        }
        return Arrays.hashCode(iArr);
    }

    public static int hashCode(long[] jArr) {
        if (jArr == null || jArr.length == 0) {
            return 0;
        }
        return Arrays.hashCode(jArr);
    }

    public static int hashCode(Object[] objArr) {
        int length = objArr == null ? 0 : objArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Object obj = objArr[i];
            i++;
            i2 = obj != null ? obj.hashCode() + (i2 * 31) : i2;
        }
        return i2;
    }

    public static int hashCode(boolean[] zArr) {
        if (zArr == null || zArr.length == 0) {
            return 0;
        }
        return Arrays.hashCode(zArr);
    }

    public static void zza(adj adj, adj adj2) {
        if (adj.zzcso != null) {
            adj2.zzcso = (adl) adj.zzcso.clone();
        }
    }

    public static boolean zza(byte[][] bArr, byte[][] bArr2) {
        int length = bArr == null ? 0 : bArr.length;
        int length2 = bArr2 == null ? 0 : bArr2.length;
        int i = 0;
        int i2 = 0;
        while (true) {
            if (i2 >= length || bArr[i2] != null) {
                int i3 = i;
                while (i3 < length2 && bArr2[i3] == null) {
                    i3++;
                }
                boolean z = i2 >= length;
                boolean z2 = i3 >= length2;
                if (z && z2) {
                    return true;
                }
                if (z != z2 || !Arrays.equals(bArr[i2], bArr2[i3])) {
                    return false;
                }
                i = i3 + 1;
                i2++;
            } else {
                i2++;
            }
        }
    }

    public static int zzc(byte[][] bArr) {
        int length = bArr == null ? 0 : bArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            byte[] bArr2 = bArr[i];
            i++;
            i2 = bArr2 != null ? Arrays.hashCode(bArr2) + (i2 * 31) : i2;
        }
        return i2;
    }
}
