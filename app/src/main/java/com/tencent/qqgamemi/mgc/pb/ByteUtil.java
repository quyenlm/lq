package com.tencent.qqgamemi.mgc.pb;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

public class ByteUtil {
    private byte[] a;
    private byte[] b;
    private byte[] c;
    private int d;
    private int e;
    private int f;
    private int g;
    private byte[] h;
    private boolean i = true;
    private int j;
    private Random k = new Random();

    private static long a(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        int m;
        long l = 0;
        if (paramInt2 > 4) {
            m = paramInt1 + 4;
        } else {
            m = paramInt1 + paramInt2;
        }
        for (int n = paramInt1; n < m; n++) {
            l = (l << 8) | ((long) (paramArrayOfByte[n] & 255));
        }
        return -1 & l;
    }

    /* access modifiers changed from: protected */
    public byte[] a(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2) {
        this.e = 0;
        this.d = 0;
        this.h = paramArrayOfByte2;
        byte[] arrayOfByte = new byte[(paramInt1 + 8)];
        if (paramInt2 % 8 != 0 || paramInt2 < 16) {
            return null;
        }
        this.b = a(paramArrayOfByte1, paramInt1);
        this.f = this.b[0] & 7;
        int m = (paramInt2 - this.f) - 10;
        if (m < 0) {
            return null;
        }
        for (int n = paramInt1; n < arrayOfByte.length; n++) {
            arrayOfByte[n] = 0;
        }
        this.c = new byte[m];
        this.e = 0;
        this.d = 8;
        this.j = 8;
        this.f++;
        this.g = 1;
        while (this.g <= 2) {
            if (this.f < 8) {
                this.f++;
                this.g++;
            }
            if (this.f == 8) {
                arrayOfByte = paramArrayOfByte1;
                if (!b(paramArrayOfByte1, paramInt1, paramInt2)) {
                    return null;
                }
            }
        }
        int n2 = 0;
        while (m != 0) {
            if (this.f < 8) {
                this.c[n2] = (byte) (arrayOfByte[(this.e + paramInt1) + this.f] ^ this.b[this.f]);
                n2++;
                m--;
                this.f++;
            }
            if (this.f == 8) {
                arrayOfByte = paramArrayOfByte1;
                this.e = this.d - 8;
                if (!b(paramArrayOfByte1, paramInt1, paramInt2)) {
                    return null;
                }
            }
        }
        this.g = 1;
        while (this.g < 8) {
            if (this.f < 8) {
                if ((arrayOfByte[(this.e + paramInt1) + this.f] ^ this.b[this.f]) != 0) {
                    return null;
                }
                this.f++;
            }
            if (this.f == 8) {
                arrayOfByte = paramArrayOfByte1;
                this.e = this.d;
                if (!b(paramArrayOfByte1, paramInt1, paramInt2)) {
                    return null;
                }
            }
            this.g++;
        }
        return this.c;
    }

    /* access modifiers changed from: protected */
    public byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        return a(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
    }

    private byte[] b(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2) {
        int m;
        this.a = new byte[8];
        this.b = new byte[8];
        this.f = 1;
        this.g = 0;
        this.e = 0;
        this.d = 0;
        this.h = paramArrayOfByte2;
        this.i = true;
        this.f = (paramInt2 + 10) % 8;
        if (this.f != 0) {
            this.f = 8 - this.f;
        }
        this.c = new byte[(this.f + paramInt2 + 10)];
        this.a[0] = (byte) ((b() & 248) | this.f);
        for (int m2 = 1; m2 <= this.f; m2++) {
            this.a[m2] = (byte) (b() & 255);
        }
        this.f++;
        for (int m3 = 0; m3 < 8; m3++) {
            this.b[m3] = 0;
        }
        this.g = 1;
        while (this.g <= 2) {
            if (this.f < 8) {
                byte[] bArr = this.a;
                int i2 = this.f;
                this.f = i2 + 1;
                bArr[i2] = (byte) (b() & 255);
                this.g++;
            }
            if (this.f == 8) {
                a();
            }
        }
        int m4 = paramInt1;
        while (paramInt2 > 0) {
            if (this.f < 8) {
                byte[] bArr2 = this.a;
                int i3 = this.f;
                this.f = i3 + 1;
                m = m4 + 1;
                bArr2[i3] = paramArrayOfByte1[m4];
                paramInt2--;
            } else {
                m = m4;
            }
            if (this.f == 8) {
                a();
                m4 = m;
            } else {
                m4 = m;
            }
        }
        this.g = 1;
        while (this.g <= 7) {
            if (this.f < 8) {
                byte[] bArr3 = this.a;
                int i4 = this.f;
                this.f = i4 + 1;
                bArr3[i4] = 0;
                this.g++;
            }
            if (this.f == 8) {
                a();
            }
        }
        return this.c;
    }

    /* access modifiers changed from: protected */
    public byte[] b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2) {
        return b(paramArrayOfByte1, 0, paramArrayOfByte1.length, paramArrayOfByte2);
    }

    private byte[] a(byte[] paramArrayOfByte) {
        try {
            long l1 = a(paramArrayOfByte, 0, 4);
            long l2 = a(paramArrayOfByte, 4, 4);
            long l3 = a(this.h, 0, 4);
            long l4 = a(this.h, 4, 4);
            long l5 = a(this.h, 8, 4);
            long l6 = a(this.h, 12, 4);
            long l7 = 0;
            long l8 = -1640531527 & 4294967295L;
            int m = 16;
            while (true) {
                int m2 = m - 1;
                if (m > 0) {
                    l7 = (l7 + l8) & 4294967295L;
                    l1 = (l1 + ((((l2 << 4) + l3) ^ (l2 + l7)) ^ ((l2 >>> 5) + l4))) & 4294967295L;
                    l2 = (l2 + ((((l1 << 4) + l5) ^ (l1 + l7)) ^ ((l1 >>> 5) + l6))) & 4294967295L;
                    m = m2;
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeInt((int) l1);
                    dataOutputStream.writeInt((int) l2);
                    dataOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e2) {
            return null;
        }
    }

    private byte[] a(byte[] paramArrayOfByte, int paramInt) {
        try {
            long l1 = a(paramArrayOfByte, paramInt, 4);
            long l2 = a(paramArrayOfByte, paramInt + 4, 4);
            long l3 = a(this.h, 0, 4);
            long l4 = a(this.h, 4, 4);
            long l5 = a(this.h, 8, 4);
            long l6 = a(this.h, 12, 4);
            long l7 = -478700656 & 4294967295L;
            long l8 = -1640531527 & 4294967295L;
            int m = 16;
            while (true) {
                int m2 = m - 1;
                if (m > 0) {
                    l2 = (l2 - ((((l1 << 4) + l5) ^ (l1 + l7)) ^ ((l1 >>> 5) + l6))) & 4294967295L;
                    l1 = (l1 - ((((l2 << 4) + l3) ^ (l2 + l7)) ^ ((l2 >>> 5) + l4))) & 4294967295L;
                    l7 = (l7 - l8) & 4294967295L;
                    m = m2;
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(8);
                    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeInt((int) l1);
                    dataOutputStream.writeInt((int) l2);
                    dataOutputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e2) {
            return null;
        }
    }

    private byte[] b(byte[] paramArrayOfByte) {
        return a(paramArrayOfByte, 0);
    }

    private void a() {
        this.f = 0;
        while (this.f < 8) {
            if (this.i) {
                int tmp29_26 = this.f;
                byte[] tmp29_22 = this.a;
                tmp29_22[tmp29_26] = (byte) (tmp29_22[tmp29_26] ^ this.b[this.f]);
            } else {
                int tmp54_51 = this.f;
                byte[] tmp54_47 = this.a;
                tmp54_47[tmp54_51] = (byte) (tmp54_47[tmp54_51] ^ this.c[this.e + this.f]);
            }
            this.f++;
        }
        System.arraycopy(a(this.a), 0, this.c, this.d, 8);
        this.f = 0;
        while (this.f < 8) {
            int tmp137_136 = this.d + this.f;
            byte[] tmp137_125 = this.c;
            tmp137_125[tmp137_136] = (byte) (tmp137_125[tmp137_136] ^ this.b[this.f]);
            this.f++;
        }
        System.arraycopy(this.a, 0, this.b, 0, 8);
        this.e = this.d;
        this.d += 8;
        this.f = 0;
        this.i = false;
    }

    private boolean b(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        this.f = 0;
        while (this.f < 8) {
            if (this.j + this.f >= paramInt2) {
                return true;
            }
            int tmp37_34 = this.f;
            byte[] tmp37_30 = this.b;
            tmp37_30[tmp37_34] = (byte) (tmp37_30[tmp37_34] ^ paramArrayOfByte[(this.d + paramInt1) + this.f]);
            this.f++;
        }
        this.b = b(this.b);
        if (this.b == null) {
            return false;
        }
        this.j += 8;
        this.d += 8;
        this.f = 0;
        return true;
    }

    private int b() {
        return this.k.nextInt();
    }
}
