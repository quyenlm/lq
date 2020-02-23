package com.tencent.qqgamemi.mgc.pb;

public final class Base16 {
    private static final int[] fromDigit = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15};
    private static final char[] toDigit = "0123456789ABCDEF".toCharArray();

    private Base16() {
    }

    public static byte[] decode(String s) {
        return decode(s, Integer.MAX_VALUE);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0050, code lost:
        if (r5 == r0.length) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0052, code lost:
        r1 = new byte[r5];
        java.lang.System.arraycopy(r0, 0, r1, 0, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r12, int r13) {
        /*
            int r7 = r12.length()
            int r10 = r7 % 2
            if (r10 == 0) goto L_0x000a
            r0 = 0
        L_0x0009:
            return r0
        L_0x000a:
            int r10 = r7 / 2
            byte[] r0 = new byte[r10]
            r5 = 0
            r4 = 0
            r6 = r5
        L_0x0011:
            if (r4 >= r7) goto L_0x005f
            char r2 = r12.charAt(r4)
            int r10 = r4 + 1
            char r3 = r12.charAt(r10)
            r10 = 48
            if (r2 < r10) goto L_0x002d
            r10 = 102(0x66, float:1.43E-43)
            if (r2 > r10) goto L_0x002d
            int[] r10 = fromDigit
            int r11 = r2 + -48
            r8 = r10[r11]
            if (r8 >= 0) goto L_0x002f
        L_0x002d:
            r0 = 0
            goto L_0x0009
        L_0x002f:
            r10 = 48
            if (r3 < r10) goto L_0x003f
            r10 = 102(0x66, float:1.43E-43)
            if (r3 > r10) goto L_0x003f
            int[] r10 = fromDigit
            int r11 = r3 + -48
            r9 = r10[r11]
            if (r9 >= 0) goto L_0x0041
        L_0x003f:
            r0 = 0
            goto L_0x0009
        L_0x0041:
            int r5 = r6 + 1
            r10 = r8 & 15
            int r10 = r10 << 4
            r11 = r9 & 15
            r10 = r10 | r11
            byte r10 = (byte) r10
            r0[r6] = r10
            if (r5 < r13) goto L_0x005b
        L_0x004f:
            int r10 = r0.length
            if (r5 == r10) goto L_0x0009
            byte[] r1 = new byte[r5]
            r10 = 0
            r11 = 0
            java.lang.System.arraycopy(r0, r10, r1, r11, r5)
            r0 = r1
            goto L_0x0009
        L_0x005b:
            int r4 = r4 + 2
            r6 = r5
            goto L_0x0011
        L_0x005f:
            r5 = r6
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.qqgamemi.mgc.pb.Base16.decode(java.lang.String, int):byte[]");
    }

    public static String encode(byte[] b) {
        if (b == null) {
            return null;
        }
        char[] chars = new char[(b.length * 2)];
        int j = 0;
        for (byte bits : b) {
            int j2 = j + 1;
            chars[j] = toDigit[(bits >>> 4) & 15];
            j = j2 + 1;
            chars[j2] = toDigit[bits & 15];
        }
        return new String(chars);
    }
}
