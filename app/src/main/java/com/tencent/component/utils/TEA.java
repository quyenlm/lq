package com.tencent.component.utils;

public class TEA {
    static final /* synthetic */ boolean $assertionsDisabled = (!TEA.class.desiredAssertionStatus());
    private static final int CUPS = 32;
    private static final int SUGAR = -1640531527;
    private static final int UNSUGAR = -957401312;
    private int[] S = new int[4];

    public TEA(byte[] key) {
        if (key == null) {
            throw new RuntimeException("Invalid key: Key was null");
        } else if (key.length < 16) {
            throw new RuntimeException("Invalid key: Length was less than 16 bytes");
        } else {
            int off = 0;
            for (int i = 0; i < 4; i++) {
                int off2 = off + 1;
                int off3 = off2 + 1;
                int off4 = off3 + 1;
                off = off4 + 1;
                this.S[i] = (key[off] & 255) | ((key[off2] & 255) << 8) | ((key[off3] & 255) << 16) | ((key[off4] & 255) << 24);
            }
        }
    }

    public TEA(int[] key) {
        this.S = key;
    }

    public byte[] encrypt(byte[] clear) {
        int i;
        int length = clear.length / 8;
        if (clear.length % 8 == 0) {
            i = 0;
        } else {
            i = 1;
        }
        int[] buffer = new int[(((i + length) * 2) + 1)];
        buffer[0] = clear.length;
        pack(clear, buffer, 1);
        brew(buffer);
        return unpack(buffer, 0, buffer.length * 4);
    }

    public byte[] encrypt(String s) {
        return encrypt(s.getBytes());
    }

    public byte[] decrypt(byte[] crypt) {
        if (!$assertionsDisabled && crypt.length % 4 != 0) {
            throw new AssertionError();
        } else if ($assertionsDisabled || (crypt.length / 4) % 2 == 1) {
            int[] buffer = new int[(crypt.length / 4)];
            pack(crypt, buffer, 0);
            unbrew(buffer);
            return unpack(buffer, 1, buffer[0]);
        } else {
            throw new AssertionError();
        }
    }

    public String decryptToString(byte[] crypt) {
        return new String(decrypt(crypt));
    }

    /* access modifiers changed from: package-private */
    public void brew(int[] buf) {
        if ($assertionsDisabled || buf.length % 2 == 1) {
            for (int i = 1; i < buf.length; i += 2) {
                int n = 32;
                int v0 = buf[i];
                int v1 = buf[i + 1];
                int sum = 0;
                while (true) {
                    int n2 = n;
                    n = n2 - 1;
                    if (n2 <= 0) {
                        break;
                    }
                    sum -= 1640531527;
                    v0 += (((v1 << 4) + this.S[0]) ^ v1) + ((v1 >>> 5) ^ sum) + this.S[1];
                    v1 += (((v0 << 4) + this.S[2]) ^ v0) + ((v0 >>> 5) ^ sum) + this.S[3];
                }
                buf[i] = v0;
                buf[i + 1] = v1;
            }
            return;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void unbrew(int[] buf) {
        if ($assertionsDisabled || buf.length % 2 == 1) {
            for (int i = 1; i < buf.length; i += 2) {
                int n = 32;
                int v0 = buf[i];
                int v1 = buf[i + 1];
                int sum = UNSUGAR;
                while (true) {
                    int n2 = n;
                    n = n2 - 1;
                    if (n2 <= 0) {
                        break;
                    }
                    v1 -= ((((v0 << 4) + this.S[2]) ^ v0) + ((v0 >>> 5) ^ sum)) + this.S[3];
                    v0 -= ((((v1 << 4) + this.S[0]) ^ v1) + ((v1 >>> 5) ^ sum)) + this.S[1];
                    sum += 1640531527;
                }
                buf[i] = v0;
                buf[i + 1] = v1;
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: byte} */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void pack(byte[] r7, int[] r8, int r9) {
        /*
            r6 = this;
            r5 = 0
            boolean r3 = $assertionsDisabled
            if (r3 != 0) goto L_0x0012
            int r3 = r7.length
            int r3 = r3 / 4
            int r3 = r3 + r9
            int r4 = r8.length
            if (r3 <= r4) goto L_0x0012
            java.lang.AssertionError r3 = new java.lang.AssertionError
            r3.<init>()
            throw r3
        L_0x0012:
            r0 = 0
            r2 = 24
            r1 = r9
            r8[r1] = r5
        L_0x0018:
            int r3 = r7.length
            if (r0 >= r3) goto L_0x0036
            r3 = r8[r1]
            byte r4 = r7[r0]
            r4 = r4 & 255(0xff, float:3.57E-43)
            int r4 = r4 << r2
            r3 = r3 | r4
            r8[r1] = r3
            if (r2 != 0) goto L_0x0033
            r2 = 24
            int r1 = r1 + 1
            int r3 = r8.length
            if (r1 >= r3) goto L_0x0030
            r8[r1] = r5
        L_0x0030:
            int r0 = r0 + 1
            goto L_0x0018
        L_0x0033:
            int r2 = r2 + -8
            goto L_0x0030
        L_0x0036:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.component.utils.TEA.pack(byte[], int[], int):void");
    }

    /* access modifiers changed from: package-private */
    public byte[] unpack(int[] src, int srcOffset, int destLength) {
        if ($assertionsDisabled || destLength <= (src.length - srcOffset) * 4) {
            byte[] dest = new byte[destLength];
            int i = srcOffset;
            int count = 0;
            for (int j = 0; j < destLength; j++) {
                dest[j] = (byte) ((src[i] >> (24 - (count * 8))) & 255);
                count++;
                if (count == 4) {
                    count = 0;
                    i++;
                }
            }
            return dest;
        }
        throw new AssertionError();
    }
}
