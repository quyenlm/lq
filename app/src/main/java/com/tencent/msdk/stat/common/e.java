package com.tencent.msdk.stat.common;

public class e {
    static final byte[] a = "03a976511e2cbe3a7f26808fb7af3c05".getBytes();

    public static byte[] a(byte[] bArr) {
        return a(bArr, a);
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [byte[]] */
    /* JADX WARNING: type inference failed for: r5v5, types: [byte] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static byte[] a(byte[] r8, byte[] r9) {
        /*
            r7 = 256(0x100, float:3.59E-43)
            r1 = 0
            int[] r4 = new int[r7]
            int[] r3 = new int[r7]
            int r2 = r9.length
            r0 = 1
            if (r2 < r0) goto L_0x000d
            if (r2 <= r7) goto L_0x0015
        L_0x000d:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "key must be between 1 and 256 bytes"
            r0.<init>(r1)
            throw r0
        L_0x0015:
            r0 = r1
        L_0x0016:
            if (r0 >= r7) goto L_0x0023
            r4[r0] = r0
            int r5 = r0 % r2
            byte r5 = r9[r5]
            r3[r0] = r5
            int r0 = r0 + 1
            goto L_0x0016
        L_0x0023:
            r0 = r1
            r2 = r1
        L_0x0025:
            if (r0 >= r7) goto L_0x003a
            r5 = r4[r0]
            int r2 = r2 + r5
            r5 = r3[r0]
            int r2 = r2 + r5
            r2 = r2 & 255(0xff, float:3.57E-43)
            r5 = r4[r0]
            r6 = r4[r2]
            r4[r0] = r6
            r4[r2] = r5
            int r0 = r0 + 1
            goto L_0x0025
        L_0x003a:
            int r0 = r8.length
            byte[] r5 = new byte[r0]
            r0 = r1
            r2 = r1
            r3 = r1
        L_0x0040:
            int r1 = r8.length
            if (r0 >= r1) goto L_0x0067
            int r1 = r3 + 1
            r3 = r1 & 255(0xff, float:3.57E-43)
            r1 = r4[r3]
            int r1 = r1 + r2
            r1 = r1 & 255(0xff, float:3.57E-43)
            r2 = r4[r3]
            r6 = r4[r1]
            r4[r3] = r6
            r4[r1] = r2
            r2 = r4[r3]
            r6 = r4[r1]
            int r2 = r2 + r6
            r2 = r2 & 255(0xff, float:3.57E-43)
            r2 = r4[r2]
            byte r6 = r8[r0]
            r2 = r2 ^ r6
            byte r2 = (byte) r2
            r5[r0] = r2
            int r0 = r0 + 1
            r2 = r1
            goto L_0x0040
        L_0x0067:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.common.e.a(byte[], byte[]):byte[]");
    }

    public static byte[] b(byte[] bArr) {
        return b(bArr, a);
    }

    static byte[] b(byte[] bArr, byte[] bArr2) {
        return a(bArr, bArr2);
    }
}
