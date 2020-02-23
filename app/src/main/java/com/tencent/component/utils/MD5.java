package com.tencent.component.utils;

import com.tencent.component.annotation.PluginApi;
import java.io.UnsupportedEncodingException;

@PluginApi(since = 7)
public final class MD5 {
    static final byte[] PADDING = {Byte.MIN_VALUE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static final int[][] S = {new int[]{7, 12, 17, 22}, new int[]{5, 9, 14, 20}, new int[]{4, 11, 16, 23}, new int[]{6, 10, 15, 21}};
    private byte[] buffer = new byte[64];
    private long[] count = new long[2];
    private byte[] digest = new byte[16];
    public String digestHexStr;
    private long[] state = new long[4];

    public byte[] getMD5(String inbuf) {
        md5Init();
        try {
            byte[] str = inbuf.getBytes("ISO8859_1");
            md5Update(str, str.length);
        } catch (UnsupportedEncodingException e) {
            md5Update(inbuf.getBytes(), inbuf.length());
        }
        md5Final();
        return this.digest;
    }

    public byte[] getMD5(byte[] inbuf) {
        md5Init();
        md5Update(inbuf, inbuf.length);
        md5Final();
        return this.digest;
    }

    public MD5() {
        md5Init();
    }

    private void md5Init() {
        this.count[0] = 0;
        this.count[1] = 0;
        this.state[0] = 1732584193;
        this.state[1] = 4023233417L;
        this.state[2] = 2562383102L;
        this.state[3] = 271733878;
    }

    private long F(long x, long y, long z) {
        return (x & y) | ((-1 ^ x) & z);
    }

    private long G(long x, long y, long z) {
        return (x & z) | ((-1 ^ z) & y);
    }

    private long H(long x, long y, long z) {
        return (x ^ y) ^ z;
    }

    private long I(long x, long y, long z) {
        return ((-1 ^ z) | x) ^ y;
    }

    private long FF(long a, long b, long c, long d, long x, long s, long ac) {
        long a2 = a + F(b, c, d) + x + ac;
        return ((long) ((((int) a2) << ((int) s)) | (((int) a2) >>> ((int) (32 - s))))) + b;
    }

    private long GG(long a, long b, long c, long d, long x, long s, long ac) {
        long a2 = a + G(b, c, d) + x + ac;
        return ((long) ((((int) a2) << ((int) s)) | (((int) a2) >>> ((int) (32 - s))))) + b;
    }

    private long HH(long a, long b, long c, long d, long x, long s, long ac) {
        long a2 = a + H(b, c, d) + x + ac;
        return ((long) ((((int) a2) << ((int) s)) | (((int) a2) >>> ((int) (32 - s))))) + b;
    }

    private long II(long a, long b, long c, long d, long x, long s, long ac) {
        long a2 = a + I(b, c, d) + x + ac;
        return ((long) ((((int) a2) << ((int) s)) | (((int) a2) >>> ((int) (32 - s))))) + b;
    }

    private void md5Update(byte[] inbuf, int inputLen) {
        int i;
        byte[] block = new byte[64];
        int index = ((int) (this.count[0] >>> 3)) & 63;
        long[] jArr = this.count;
        long j = jArr[0] + ((long) (inputLen << 3));
        jArr[0] = j;
        if (j < ((long) (inputLen << 3))) {
            long[] jArr2 = this.count;
            jArr2[1] = jArr2[1] + 1;
        }
        long[] jArr3 = this.count;
        jArr3[1] = jArr3[1] + ((long) (inputLen >>> 29));
        int partLen = 64 - index;
        if (inputLen >= partLen) {
            md5Memcpy(this.buffer, inbuf, index, 0, partLen);
            md5Transform(this.buffer);
            i = partLen;
            while (i + 63 < inputLen) {
                md5Memcpy(block, inbuf, 0, i, 64);
                md5Transform(block);
                i += 64;
            }
            index = 0;
        } else {
            i = 0;
        }
        md5Memcpy(this.buffer, inbuf, index, i, inputLen - i);
    }

    private void md5Final() {
        byte[] bits = new byte[8];
        Encode(bits, this.count, 8);
        int index = ((int) (this.count[0] >>> 3)) & 63;
        md5Update(PADDING, index < 56 ? 56 - index : 120 - index);
        md5Update(bits, 8);
        Encode(this.digest, this.state, 16);
    }

    private void md5Memcpy(byte[] output, byte[] input, int outpos, int inpos, int len) {
        for (int i = 0; i < len; i++) {
            output[outpos + i] = input[inpos + i];
        }
    }

    private void md5Transform(byte[] block) {
        long a = this.state[0];
        long b = this.state[1];
        long c = this.state[2];
        long d = this.state[3];
        long[] x = new long[16];
        Decode(x, block, 64);
        long a2 = FF(a, b, c, d, x[0], (long) S[0][0], 3614090360L);
        long d2 = FF(d, a2, b, c, x[1], (long) S[0][1], 3905402710L);
        long c2 = FF(c, d2, a2, b, x[2], (long) S[0][2], 606105819);
        long b2 = FF(b, c2, d2, a2, x[3], (long) S[0][3], 3250441966L);
        long a3 = FF(a2, b2, c2, d2, x[4], (long) S[0][0], 4118548399L);
        long d3 = FF(d2, a3, b2, c2, x[5], (long) S[0][1], 1200080426);
        long c3 = FF(c2, d3, a3, b2, x[6], (long) S[0][2], 2821735955L);
        long b3 = FF(b2, c3, d3, a3, x[7], (long) S[0][3], 4249261313L);
        long a4 = FF(a3, b3, c3, d3, x[8], (long) S[0][0], 1770035416);
        long d4 = FF(d3, a4, b3, c3, x[9], (long) S[0][1], 2336552879L);
        long c4 = FF(c3, d4, a4, b3, x[10], (long) S[0][2], 4294925233L);
        long b4 = FF(b3, c4, d4, a4, x[11], (long) S[0][3], 2304563134L);
        long a5 = FF(a4, b4, c4, d4, x[12], (long) S[0][0], 1804603682);
        long d5 = FF(d4, a5, b4, c4, x[13], (long) S[0][1], 4254626195L);
        long c5 = FF(c4, d5, a5, b4, x[14], (long) S[0][2], 2792965006L);
        long b5 = FF(b4, c5, d5, a5, x[15], (long) S[0][3], 1236535329);
        long a6 = GG(a5, b5, c5, d5, x[1], (long) S[1][0], 4129170786L);
        long d6 = GG(d5, a6, b5, c5, x[6], (long) S[1][1], 3225465664L);
        long c6 = GG(c5, d6, a6, b5, x[11], (long) S[1][2], 643717713);
        long b6 = GG(b5, c6, d6, a6, x[0], (long) S[1][3], 3921069994L);
        long a7 = GG(a6, b6, c6, d6, x[5], (long) S[1][0], 3593408605L);
        long d7 = GG(d6, a7, b6, c6, x[10], (long) S[1][1], 38016083);
        long c7 = GG(c6, d7, a7, b6, x[15], (long) S[1][2], 3634488961L);
        long b7 = GG(b6, c7, d7, a7, x[4], (long) S[1][3], 3889429448L);
        long a8 = GG(a7, b7, c7, d7, x[9], (long) S[1][0], 568446438);
        long d8 = GG(d7, a8, b7, c7, x[14], (long) S[1][1], 3275163606L);
        long c8 = GG(c7, d8, a8, b7, x[3], (long) S[1][2], 4107603335L);
        long b8 = GG(b7, c8, d8, a8, x[8], (long) S[1][3], 1163531501);
        long a9 = GG(a8, b8, c8, d8, x[13], (long) S[1][0], 2850285829L);
        long d9 = GG(d8, a9, b8, c8, x[2], (long) S[1][1], 4243563512L);
        long c9 = GG(c8, d9, a9, b8, x[7], (long) S[1][2], 1735328473);
        long b9 = GG(b8, c9, d9, a9, x[12], (long) S[1][3], 2368359562L);
        long a10 = HH(a9, b9, c9, d9, x[5], (long) S[2][0], 4294588738L);
        long d10 = HH(d9, a10, b9, c9, x[8], (long) S[2][1], 2272392833L);
        long c10 = HH(c9, d10, a10, b9, x[11], (long) S[2][2], 1839030562);
        long b10 = HH(b9, c10, d10, a10, x[14], (long) S[2][3], 4259657740L);
        long a11 = HH(a10, b10, c10, d10, x[1], (long) S[2][0], 2763975236L);
        long d11 = HH(d10, a11, b10, c10, x[4], (long) S[2][1], 1272893353);
        long c11 = HH(c10, d11, a11, b10, x[7], (long) S[2][2], 4139469664L);
        long b11 = HH(b10, c11, d11, a11, x[10], (long) S[2][3], 3200236656L);
        long a12 = HH(a11, b11, c11, d11, x[13], (long) S[2][0], 681279174);
        long d12 = HH(d11, a12, b11, c11, x[0], (long) S[2][1], 3936430074L);
        long c12 = HH(c11, d12, a12, b11, x[3], (long) S[2][2], 3572445317L);
        long b12 = HH(b11, c12, d12, a12, x[6], (long) S[2][3], 76029189);
        long a13 = HH(a12, b12, c12, d12, x[9], (long) S[2][0], 3654602809L);
        long d13 = HH(d12, a13, b12, c12, x[12], (long) S[2][1], 3873151461L);
        long c13 = HH(c12, d13, a13, b12, x[15], (long) S[2][2], 530742520);
        long b13 = HH(b12, c13, d13, a13, x[2], (long) S[2][3], 3299628645L);
        long a14 = II(a13, b13, c13, d13, x[0], (long) S[3][0], 4096336452L);
        long d14 = II(d13, a14, b13, c13, x[7], (long) S[3][1], 1126891415);
        long c14 = II(c13, d14, a14, b13, x[14], (long) S[3][2], 2878612391L);
        long b14 = II(b13, c14, d14, a14, x[5], (long) S[3][3], 4237533241L);
        long a15 = II(a14, b14, c14, d14, x[12], (long) S[3][0], 1700485571);
        long d15 = II(d14, a15, b14, c14, x[3], (long) S[3][1], 2399980690L);
        long c15 = II(c14, d15, a15, b14, x[10], (long) S[3][2], 4293915773L);
        long b15 = II(b14, c15, d15, a15, x[1], (long) S[3][3], 2240044497L);
        long a16 = II(a15, b15, c15, d15, x[8], (long) S[3][0], 1873313359);
        long d16 = II(d15, a16, b15, c15, x[15], (long) S[3][1], 4264355552L);
        long c16 = II(c15, d16, a16, b15, x[6], (long) S[3][2], 2734768916L);
        long b16 = II(b15, c16, d16, a16, x[13], (long) S[3][3], 1309151649);
        long a17 = II(a16, b16, c16, d16, x[4], (long) S[3][0], 4149444226L);
        long d17 = II(d16, a17, b16, c16, x[11], (long) S[3][1], 3174756917L);
        long c17 = II(c16, d17, a17, b16, x[2], (long) S[3][2], 718787259);
        long b17 = II(b16, c17, d17, a17, x[9], (long) S[3][3], 3951481745L);
        long[] jArr = this.state;
        jArr[0] = jArr[0] + a17;
        long[] jArr2 = this.state;
        jArr2[1] = jArr2[1] + b17;
        long[] jArr3 = this.state;
        jArr3[2] = jArr3[2] + c17;
        long[] jArr4 = this.state;
        jArr4[3] = jArr4[3] + d17;
    }

    private void Encode(byte[] output, long[] input, int len) {
        int i = 0;
        for (int j = 0; j < len; j += 4) {
            output[j] = (byte) ((int) (input[i] & 255));
            output[j + 1] = (byte) ((int) ((input[i] >>> 8) & 255));
            output[j + 2] = (byte) ((int) ((input[i] >>> 16) & 255));
            output[j + 3] = (byte) ((int) ((input[i] >>> 24) & 255));
            i++;
        }
    }

    private void Decode(long[] output, byte[] input, int len) {
        int i = 0;
        for (int j = 0; j < len; j += 4) {
            output[i] = b2iu(input[j]) | (b2iu(input[j + 1]) << 8) | (b2iu(input[j + 2]) << 16) | (b2iu(input[j + 3]) << 24);
            i++;
        }
    }

    public static long b2iu(byte b) {
        return b < 0 ? (long) (b & 255) : (long) b;
    }

    public static String byteHEX(byte ib) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return new String(new char[]{Digit[(ib >>> 4) & 15], Digit[ib & 15]});
    }

    @PluginApi(since = 7)
    public static byte[] toMD5(byte[] src) {
        return new MD5().getMD5(src);
    }

    @PluginApi(since = 7)
    public static byte[] toMD5(String source) {
        return new MD5().getMD5(source);
    }

    @PluginApi(since = 7)
    public static String StrMD5(String source) {
        byte[] dst = toMD5(source);
        String result = "";
        for (int i = 0; i < 16; i++) {
            result = result + byteHEX(dst[i]);
        }
        return result;
    }

    @PluginApi(since = 7)
    public static String md5BytesTOHexStr(byte[] md5Bytes) {
        if (md5Bytes == null || md5Bytes.length < 16) {
            return "";
        }
        byte[] dst = md5Bytes;
        String result = "";
        for (int i = 0; i < 16; i++) {
            result = result + byteHEX(dst[i]);
        }
        return result;
    }
}
