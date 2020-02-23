package com.tencent.midas.oversea.comm;

import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.tool.etc.APNUtil;
import com.vk.sdk.api.model.VKApiPhotoSize;

public class APBase64 {
    private static final char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', VKApiPhotoSize.M, 'n', VKApiPhotoSize.O, VKApiPhotoSize.P, VKApiPhotoSize.Q, 'r', VKApiPhotoSize.S, 't', 'u', 'v', VKApiPhotoSize.W, VKApiPhotoSize.X, VKApiPhotoSize.Y, VKApiPhotoSize.Z, '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static byte[] b = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, APNUtil.APNTYPE_3GNET, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1};

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0072 A[LOOP:0: B:1:0x000e->B:28:0x0072, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x001e A[EDGE_INSN: B:34:0x001e->B:6:0x001e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x001e A[EDGE_INSN: B:35:0x001e->B:6:0x001e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x001e A[EDGE_INSN: B:37:0x001e->B:6:0x001e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x001e A[EDGE_INSN: B:38:0x001e->B:6:0x001e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024 A[LOOP:2: B:8:0x0024->B:7:0x0023, LOOP_START, PHI: r1 
  PHI: (r1v1 int) = (r1v0 int), (r1v11 int) binds: [B:5:0x001c, B:7:0x0023] A[DONT_GENERATE, DONT_INLINE]] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] decode(java.lang.String r9) {
        /*
            r8 = 61
            r7 = -1
            byte[] r2 = r9.getBytes()
            int r3 = r2.length
            java.io.ByteArrayOutputStream r4 = new java.io.ByteArrayOutputStream
            r4.<init>(r3)
            r0 = 0
        L_0x000e:
            if (r0 >= r3) goto L_0x001e
        L_0x0010:
            byte[] r5 = b
            int r1 = r0 + 1
            byte r0 = r2[r0]
            byte r5 = r5[r0]
            if (r1 >= r3) goto L_0x001c
            if (r5 == r7) goto L_0x007f
        L_0x001c:
            if (r5 != r7) goto L_0x0024
        L_0x001e:
            byte[] r0 = r4.toByteArray()
        L_0x0022:
            return r0
        L_0x0023:
            r1 = r0
        L_0x0024:
            byte[] r6 = b
            int r0 = r1 + 1
            byte r1 = r2[r1]
            byte r6 = r6[r1]
            if (r0 >= r3) goto L_0x0030
            if (r6 == r7) goto L_0x0023
        L_0x0030:
            if (r6 == r7) goto L_0x001e
            int r1 = r5 << 2
            r5 = r6 & 48
            int r5 = r5 >>> 4
            r1 = r1 | r5
            r4.write(r1)
        L_0x003c:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            if (r0 != r8) goto L_0x0047
            byte[] r0 = r4.toByteArray()
            goto L_0x0022
        L_0x0047:
            byte[] r5 = b
            byte r5 = r5[r0]
            if (r1 >= r3) goto L_0x004f
            if (r5 == r7) goto L_0x007d
        L_0x004f:
            if (r5 == r7) goto L_0x001e
            r0 = r6 & 15
            int r0 = r0 << 4
            r6 = r5 & 60
            int r6 = r6 >>> 2
            r0 = r0 | r6
            r4.write(r0)
        L_0x005d:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            if (r1 != r8) goto L_0x0068
            byte[] r0 = r4.toByteArray()
            goto L_0x0022
        L_0x0068:
            byte[] r6 = b
            byte r1 = r6[r1]
            if (r0 >= r3) goto L_0x0070
            if (r1 == r7) goto L_0x007b
        L_0x0070:
            if (r1 == r7) goto L_0x001e
            r5 = r5 & 3
            int r5 = r5 << 6
            r1 = r1 | r5
            r4.write(r1)
            goto L_0x000e
        L_0x007b:
            r1 = r0
            goto L_0x005d
        L_0x007d:
            r0 = r1
            goto L_0x003c
        L_0x007f:
            r0 = r1
            goto L_0x0010
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.comm.APBase64.decode(java.lang.String):byte[]");
    }

    public static String encode(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        int length = bArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            int i2 = i + 1;
            byte b2 = bArr[i] & 255;
            if (i2 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[(b2 & 3) << 4]);
                stringBuffer.append("==");
                break;
            }
            int i3 = i2 + 1;
            byte b3 = bArr[i2] & 255;
            if (i3 == length) {
                stringBuffer.append(a[b2 >>> 2]);
                stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
                stringBuffer.append(a[(b3 & 15) << 2]);
                stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                break;
            }
            i = i3 + 1;
            byte b4 = bArr[i3] & 255;
            stringBuffer.append(a[b2 >>> 2]);
            stringBuffer.append(a[((b2 & 3) << 4) | ((b3 & 240) >>> 4)]);
            stringBuffer.append(a[((b3 & 15) << 2) | ((b4 & 192) >>> 6)]);
            stringBuffer.append(a[b4 & 63]);
        }
        return stringBuffer.toString();
    }
}
