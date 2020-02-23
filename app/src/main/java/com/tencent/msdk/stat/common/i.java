package com.tencent.msdk.stat.common;

import com.tencent.component.net.download.multiplex.http.MttRequest;

class i extends g {
    static final /* synthetic */ boolean g = (!f.class.desiredAssertionStatus());
    private static final byte[] h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, MttRequest.REQUEST_NORMAL, MttRequest.REQUEST_DIRECT, MttRequest.REQUEST_BROKER, MttRequest.REQUEST_FILE_DOWNLOAD, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] i = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, MttRequest.REQUEST_NORMAL, MttRequest.REQUEST_DIRECT, MttRequest.REQUEST_BROKER, MttRequest.REQUEST_FILE_DOWNLOAD, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    int c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    private final byte[] j;
    private int k;
    private final byte[] l;

    public i(int i2, byte[] bArr) {
        boolean z = true;
        this.a = bArr;
        this.d = (i2 & 1) == 0;
        this.e = (i2 & 2) == 0;
        this.f = (i2 & 4) == 0 ? false : z;
        this.l = (i2 & 8) == 0 ? h : i;
        this.j = new byte[2];
        this.c = 0;
        this.k = this.e ? 19 : -1;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(byte[] r11, int r12, int r13, boolean r14) {
        /*
            r10 = this;
            byte[] r7 = r10.l
            byte[] r8 = r10.a
            r1 = 0
            int r0 = r10.k
            int r9 = r13 + r12
            r2 = -1
            int r3 = r10.c
            switch(r3) {
                case 0: goto L_0x00a7;
                case 1: goto L_0x00aa;
                case 2: goto L_0x00cd;
                default: goto L_0x000f;
            }
        L_0x000f:
            r3 = r12
        L_0x0010:
            r4 = -1
            if (r2 == r4) goto L_0x0238
            r4 = 1
            int r5 = r2 >> 18
            r5 = r5 & 63
            byte r5 = r7[r5]
            r8[r1] = r5
            r1 = 2
            int r5 = r2 >> 12
            r5 = r5 & 63
            byte r5 = r7[r5]
            r8[r4] = r5
            r4 = 3
            int r5 = r2 >> 6
            r5 = r5 & 63
            byte r5 = r7[r5]
            r8[r1] = r5
            r1 = 4
            r2 = r2 & 63
            byte r2 = r7[r2]
            r8[r4] = r2
            int r0 = r0 + -1
            if (r0 != 0) goto L_0x0238
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x023c
            r0 = 5
            r2 = 13
            r8[r1] = r2
        L_0x0042:
            int r1 = r0 + 1
            r2 = 10
            r8[r0] = r2
            r0 = 19
            r6 = r0
            r5 = r1
        L_0x004c:
            int r0 = r3 + 3
            if (r0 > r9) goto L_0x00f0
            byte r0 = r11[r3]
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 16
            int r1 = r3 + 1
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            int r1 = r1 << 8
            r0 = r0 | r1
            int r1 = r3 + 2
            byte r1 = r11[r1]
            r1 = r1 & 255(0xff, float:3.57E-43)
            r0 = r0 | r1
            int r1 = r0 >> 18
            r1 = r1 & 63
            byte r1 = r7[r1]
            r8[r5] = r1
            int r1 = r5 + 1
            int r2 = r0 >> 12
            r2 = r2 & 63
            byte r2 = r7[r2]
            r8[r1] = r2
            int r1 = r5 + 2
            int r2 = r0 >> 6
            r2 = r2 & 63
            byte r2 = r7[r2]
            r8[r1] = r2
            int r1 = r5 + 3
            r0 = r0 & 63
            byte r0 = r7[r0]
            r8[r1] = r0
            int r3 = r3 + 3
            int r1 = r5 + 4
            int r0 = r6 + -1
            if (r0 != 0) goto L_0x0238
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x0235
            int r0 = r1 + 1
            r2 = 13
            r8[r1] = r2
        L_0x009c:
            int r1 = r0 + 1
            r2 = 10
            r8[r0] = r2
            r0 = 19
            r6 = r0
            r5 = r1
            goto L_0x004c
        L_0x00a7:
            r3 = r12
            goto L_0x0010
        L_0x00aa:
            int r3 = r12 + 2
            if (r3 > r9) goto L_0x000f
            byte[] r2 = r10.j
            r3 = 0
            byte r2 = r2[r3]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 16
            int r3 = r12 + 1
            byte r4 = r11[r12]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << 8
            r2 = r2 | r4
            int r12 = r3 + 1
            byte r3 = r11[r3]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r2 = r2 | r3
            r3 = 0
            r10.c = r3
            r3 = r12
            goto L_0x0010
        L_0x00cd:
            int r3 = r12 + 1
            if (r3 > r9) goto L_0x000f
            byte[] r2 = r10.j
            r3 = 0
            byte r2 = r2[r3]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << 16
            byte[] r3 = r10.j
            r4 = 1
            byte r3 = r3[r4]
            r3 = r3 & 255(0xff, float:3.57E-43)
            int r3 = r3 << 8
            r2 = r2 | r3
            int r3 = r12 + 1
            byte r4 = r11[r12]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r2 = r2 | r4
            r4 = 0
            r10.c = r4
            goto L_0x0010
        L_0x00f0:
            if (r14 == 0) goto L_0x01fb
            int r0 = r10.c
            int r0 = r3 - r0
            int r1 = r9 + -1
            if (r0 != r1) goto L_0x015b
            r2 = 0
            int r0 = r10.c
            if (r0 <= 0) goto L_0x0154
            byte[] r0 = r10.j
            r1 = 1
            byte r0 = r0[r2]
        L_0x0104:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r2 = r0 << 4
            int r0 = r10.c
            int r0 = r0 - r1
            r10.c = r0
            int r1 = r5 + 1
            int r0 = r2 >> 6
            r0 = r0 & 63
            byte r0 = r7[r0]
            r8[r5] = r0
            int r0 = r1 + 1
            r2 = r2 & 63
            byte r2 = r7[r2]
            r8[r1] = r2
            boolean r1 = r10.d
            if (r1 == 0) goto L_0x012f
            int r1 = r0 + 1
            r2 = 61
            r8[r0] = r2
            int r0 = r1 + 1
            r2 = 61
            r8[r1] = r2
        L_0x012f:
            boolean r1 = r10.e
            if (r1 == 0) goto L_0x0145
            boolean r1 = r10.f
            if (r1 == 0) goto L_0x013e
            int r1 = r0 + 1
            r2 = 13
            r8[r0] = r2
            r0 = r1
        L_0x013e:
            int r1 = r0 + 1
            r2 = 10
            r8[r0] = r2
            r0 = r1
        L_0x0145:
            r5 = r0
        L_0x0146:
            boolean r0 = g
            if (r0 != 0) goto L_0x01ef
            int r0 = r10.c
            if (r0 == 0) goto L_0x01ef
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x0154:
            int r4 = r3 + 1
            byte r0 = r11[r3]
            r1 = r2
            r3 = r4
            goto L_0x0104
        L_0x015b:
            int r0 = r10.c
            int r0 = r3 - r0
            int r1 = r9 + -2
            if (r0 != r1) goto L_0x01d3
            r2 = 0
            int r0 = r10.c
            r1 = 1
            if (r0 <= r1) goto L_0x01c6
            byte[] r0 = r10.j
            r1 = 1
            byte r0 = r0[r2]
        L_0x016e:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r4 = r0 << 10
            int r0 = r10.c
            if (r0 <= 0) goto L_0x01cd
            byte[] r0 = r10.j
            int r2 = r1 + 1
            byte r0 = r0[r1]
            r1 = r2
        L_0x017d:
            r0 = r0 & 255(0xff, float:3.57E-43)
            int r0 = r0 << 2
            r0 = r0 | r4
            int r2 = r10.c
            int r1 = r2 - r1
            r10.c = r1
            int r1 = r5 + 1
            int r2 = r0 >> 12
            r2 = r2 & 63
            byte r2 = r7[r2]
            r8[r5] = r2
            int r2 = r1 + 1
            int r4 = r0 >> 6
            r4 = r4 & 63
            byte r4 = r7[r4]
            r8[r1] = r4
            int r1 = r2 + 1
            r0 = r0 & 63
            byte r0 = r7[r0]
            r8[r2] = r0
            boolean r0 = r10.d
            if (r0 == 0) goto L_0x0232
            int r0 = r1 + 1
            r2 = 61
            r8[r1] = r2
        L_0x01ae:
            boolean r1 = r10.e
            if (r1 == 0) goto L_0x01c4
            boolean r1 = r10.f
            if (r1 == 0) goto L_0x01bd
            int r1 = r0 + 1
            r2 = 13
            r8[r0] = r2
            r0 = r1
        L_0x01bd:
            int r1 = r0 + 1
            r2 = 10
            r8[r0] = r2
            r0 = r1
        L_0x01c4:
            r5 = r0
            goto L_0x0146
        L_0x01c6:
            int r4 = r3 + 1
            byte r0 = r11[r3]
            r1 = r2
            r3 = r4
            goto L_0x016e
        L_0x01cd:
            int r2 = r3 + 1
            byte r0 = r11[r3]
            r3 = r2
            goto L_0x017d
        L_0x01d3:
            boolean r0 = r10.e
            if (r0 == 0) goto L_0x0146
            if (r5 <= 0) goto L_0x0146
            r0 = 19
            if (r6 == r0) goto L_0x0146
            boolean r0 = r10.f
            if (r0 == 0) goto L_0x0230
            int r0 = r5 + 1
            r1 = 13
            r8[r5] = r1
        L_0x01e7:
            int r5 = r0 + 1
            r1 = 10
            r8[r0] = r1
            goto L_0x0146
        L_0x01ef:
            boolean r0 = g
            if (r0 != 0) goto L_0x020b
            if (r3 == r9) goto L_0x020b
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x01fb:
            int r0 = r9 + -1
            if (r3 != r0) goto L_0x0211
            byte[] r0 = r10.j
            int r1 = r10.c
            int r2 = r1 + 1
            r10.c = r2
            byte r2 = r11[r3]
            r0[r1] = r2
        L_0x020b:
            r10.b = r5
            r10.k = r6
            r0 = 1
            return r0
        L_0x0211:
            int r0 = r9 + -2
            if (r3 != r0) goto L_0x020b
            byte[] r0 = r10.j
            int r1 = r10.c
            int r2 = r1 + 1
            r10.c = r2
            byte r2 = r11[r3]
            r0[r1] = r2
            byte[] r0 = r10.j
            int r1 = r10.c
            int r2 = r1 + 1
            r10.c = r2
            int r2 = r3 + 1
            byte r2 = r11[r2]
            r0[r1] = r2
            goto L_0x020b
        L_0x0230:
            r0 = r5
            goto L_0x01e7
        L_0x0232:
            r0 = r1
            goto L_0x01ae
        L_0x0235:
            r0 = r1
            goto L_0x009c
        L_0x0238:
            r6 = r0
            r5 = r1
            goto L_0x004c
        L_0x023c:
            r0 = r1
            goto L_0x0042
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.msdk.stat.common.i.a(byte[], int, int, boolean):boolean");
    }
}
