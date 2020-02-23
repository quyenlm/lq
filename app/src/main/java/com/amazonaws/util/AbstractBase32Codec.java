package com.amazonaws.util;

abstract class AbstractBase32Codec implements Codec {
    private static final int MASK_2BITS = 3;
    private static final int MASK_3BITS = 7;
    private static final int MASK_4BITS = 15;
    private static final int MASK_5BITS = 31;
    private static final byte PAD = 61;
    private final byte[] ALPAHBETS;

    /* access modifiers changed from: protected */
    public abstract int pos(byte b);

    protected AbstractBase32Codec(byte[] alphabets) {
        this.ALPAHBETS = alphabets;
    }

    public final byte[] encode(byte[] src) {
        int num5bytes = src.length / 5;
        int remainder = src.length % 5;
        if (remainder == 0) {
            byte[] dest = new byte[(num5bytes * 8)];
            int s = 0;
            int d = 0;
            while (s < src.length) {
                encode5bytes(src, s, dest, d);
                s += 5;
                d += 8;
            }
            byte[] bArr = dest;
            return dest;
        }
        byte[] dest2 = new byte[((num5bytes + 1) * 8)];
        int s2 = 0;
        int d2 = 0;
        while (s2 < src.length - remainder) {
            encode5bytes(src, s2, dest2, d2);
            s2 += 5;
            d2 += 8;
        }
        switch (remainder) {
            case 1:
                encode1byte(src, s2, dest2, d2);
                break;
            case 2:
                encode2bytes(src, s2, dest2, d2);
                break;
            case 3:
                encode3bytes(src, s2, dest2, d2);
                break;
            case 4:
                encode4bytes(src, s2, dest2, d2);
                break;
        }
        byte[] bArr2 = dest2;
        return dest2;
    }

    private final void encode5bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.ALPAHBETS;
        int s2 = s + 1;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        byte[] bArr2 = this.ALPAHBETS;
        int s3 = s2 + 1;
        byte p2 = src[s2];
        dest[d2] = bArr2[((p & 7) << 2) | ((p2 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = this.ALPAHBETS[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        byte[] bArr3 = this.ALPAHBETS;
        int s4 = s3 + 1;
        byte p3 = src[s3];
        dest[d4] = bArr3[((p2 & 1) << 4) | ((p3 >>> 4) & 15)];
        int d6 = d5 + 1;
        byte[] bArr4 = this.ALPAHBETS;
        byte p4 = src[s4];
        dest[d5] = bArr4[((p3 & 15) << 1) | ((p4 >>> 7) & 1)];
        int d7 = d6 + 1;
        dest[d6] = this.ALPAHBETS[(p4 >>> 2) & 31];
        byte[] bArr5 = this.ALPAHBETS;
        byte p5 = src[s4 + 1];
        dest[d7] = bArr5[((p4 & 3) << 3) | ((p5 >>> 5) & 7)];
        dest[d7 + 1] = this.ALPAHBETS[p5 & 31];
    }

    private final void encode4bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.ALPAHBETS;
        int s2 = s + 1;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        byte[] bArr2 = this.ALPAHBETS;
        int s3 = s2 + 1;
        byte p2 = src[s2];
        dest[d2] = bArr2[((p & 7) << 2) | ((p2 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = this.ALPAHBETS[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        byte[] bArr3 = this.ALPAHBETS;
        byte p3 = src[s3];
        dest[d4] = bArr3[((p2 & 1) << 4) | ((p3 >>> 4) & 15)];
        int d6 = d5 + 1;
        byte[] bArr4 = this.ALPAHBETS;
        byte p4 = src[s3 + 1];
        dest[d5] = bArr4[((p3 & 15) << 1) | ((p4 >>> 7) & 1)];
        int d7 = d6 + 1;
        dest[d6] = this.ALPAHBETS[(p4 >>> 2) & 31];
        dest[d7] = this.ALPAHBETS[(p4 & 3) << 3];
        dest[d7 + 1] = PAD;
    }

    private final void encode3bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.ALPAHBETS;
        int s2 = s + 1;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        byte[] bArr2 = this.ALPAHBETS;
        byte p2 = src[s2];
        dest[d2] = bArr2[((p & 7) << 2) | ((p2 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = this.ALPAHBETS[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        byte[] bArr3 = this.ALPAHBETS;
        byte p3 = src[s2 + 1];
        dest[d4] = bArr3[((p2 & 1) << 4) | ((p3 >>> 4) & 15)];
        int d6 = d5 + 1;
        dest[d5] = this.ALPAHBETS[(p3 & 15) << 1];
        int i = 0;
        while (i < 3) {
            dest[d6] = PAD;
            i++;
            d6++;
        }
    }

    private final void encode2bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.ALPAHBETS;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        byte[] bArr2 = this.ALPAHBETS;
        byte p2 = src[s + 1];
        dest[d2] = bArr2[((p & 7) << 2) | ((p2 >>> 6) & 3)];
        int d4 = d3 + 1;
        dest[d3] = this.ALPAHBETS[(p2 >>> 1) & 31];
        int d5 = d4 + 1;
        dest[d4] = this.ALPAHBETS[(p2 & 1) << 4];
        int i = 0;
        while (true) {
            int d6 = d5;
            if (i < 4) {
                d5 = d6 + 1;
                dest[d6] = PAD;
                i++;
            } else {
                return;
            }
        }
    }

    private final void encode1byte(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        byte[] bArr = this.ALPAHBETS;
        byte p = src[s];
        dest[d] = bArr[(p >>> 3) & 31];
        int d3 = d2 + 1;
        dest[d2] = this.ALPAHBETS[(p & 7) << 2];
        int i = 0;
        while (true) {
            int d4 = d3;
            if (i < 6) {
                d3 = d4 + 1;
                dest[d4] = PAD;
                i++;
            } else {
                return;
            }
        }
    }

    private final void decode5bytes(byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int s3 = s2 + 1;
        int p = pos(src[s2]);
        dest[d] = (byte) ((pos(src[s]) << 3) | ((p >>> 2) & 7));
        int d3 = d2 + 1;
        int s4 = s3 + 1;
        int pos = ((p & 3) << 6) | (pos(src[s3]) << 1);
        int s5 = s4 + 1;
        int p2 = pos(src[s4]);
        dest[d2] = (byte) (pos | ((p2 >>> 4) & 1));
        int d4 = d3 + 1;
        int s6 = s5 + 1;
        int p3 = pos(src[s5]);
        dest[d3] = (byte) (((p2 & 15) << 4) | ((p3 >>> 1) & 15));
        int s7 = s6 + 1;
        int pos2 = ((p3 & 1) << 7) | (pos(src[s6]) << 2);
        int p4 = pos(src[s7]);
        dest[d4] = (byte) (pos2 | ((p4 >>> 3) & 3));
        dest[d4 + 1] = (byte) (((p4 & 7) << 5) | pos(src[s7 + 1]));
    }

    private final void decode1to4bytes(int n, byte[] src, int s, byte[] dest, int d) {
        int d2 = d + 1;
        int s2 = s + 1;
        int s3 = s2 + 1;
        int p = pos(src[s2]);
        dest[d] = (byte) ((pos(src[s]) << 3) | ((p >>> 2) & 7));
        if (n == 1) {
            CodecUtils.sanityCheckLastPos(p, 3);
            int i = d2;
            return;
        }
        int d3 = d2 + 1;
        int s4 = s3 + 1;
        int pos = ((p & 3) << 6) | (pos(src[s3]) << 1);
        int s5 = s4 + 1;
        int p2 = pos(src[s4]);
        dest[d2] = (byte) (pos | ((p2 >>> 4) & 1));
        if (n == 2) {
            CodecUtils.sanityCheckLastPos(p2, 15);
            return;
        }
        int d4 = d3 + 1;
        int s6 = s5 + 1;
        int p3 = pos(src[s5]);
        dest[d3] = (byte) (((p2 & 15) << 4) | ((p3 >>> 1) & 15));
        if (n == 3) {
            CodecUtils.sanityCheckLastPos(p3, 1);
            int i2 = d4;
            int i3 = s6;
            return;
        }
        int pos2 = ((p3 & 1) << 7) | (pos(src[s6]) << 2);
        int p4 = pos(src[s6 + 1]);
        dest[d4] = (byte) (pos2 | ((p4 >>> 3) & 3));
        CodecUtils.sanityCheckLastPos(p4, 7);
        int i4 = d4;
    }

    public final byte[] decode(byte[] src, int length) {
        int fq;
        if (length % 8 != 0) {
            throw new IllegalArgumentException("Input is expected to be encoded in multiple of 8 bytes but found: " + length);
        }
        int pads = 0;
        int last = length - 1;
        while (pads < 6 && last > -1 && src[last] == 61) {
            last--;
            pads++;
        }
        switch (pads) {
            case 0:
                fq = 5;
                break;
            case 1:
                fq = 4;
                break;
            case 3:
                fq = 3;
                break;
            case 4:
                fq = 2;
                break;
            case 6:
                fq = 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid number of paddings " + pads);
        }
        byte[] dest = new byte[(((length / 8) * 5) - (5 - fq))];
        int s = 0;
        int d = 0;
        while (d < dest.length - (fq % 5)) {
            decode5bytes(src, s, dest, d);
            s += 8;
            d += 5;
        }
        if (fq < 5) {
            decode1to4bytes(fq, src, s, dest, d);
        }
        return dest;
    }
}
