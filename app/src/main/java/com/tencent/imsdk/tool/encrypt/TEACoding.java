package com.tencent.imsdk.tool.encrypt;

import com.tencent.imsdk.tool.etc.HexUtil;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class TEACoding {
    private static Random random = new Random();
    private int contextStart;
    private int crypt;
    private boolean header;
    private byte[] key;
    private byte[] out;
    private int padding;
    private byte[] plain;
    private int pos;
    private int preCrypt;
    private byte[] prePlain;

    public TEACoding(byte[] k) {
        if (k == null || k.length != 16) {
            throw new IllegalArgumentException("Key length must be 16!");
        }
        this.header = true;
        this.key = k;
    }

    public byte[] encode(byte[] in) {
        return encode(in, 0, in.length);
    }

    public String encode2HexStr(byte[] in) {
        return HexUtil.bytes2HexStr(encode(in));
    }

    public String encode2HexBase64(byte[] in) {
        return Base64.encodeToString(encode(in), 0);
    }

    private byte[] encode(byte[] in, int offset, int len) {
        int i;
        this.plain = new byte[8];
        this.prePlain = new byte[8];
        this.pos = 1;
        this.padding = 0;
        this.preCrypt = 0;
        this.crypt = 0;
        this.header = true;
        this.pos = (len + 10) % 8;
        if (this.pos != 0) {
            this.pos = 8 - this.pos;
        }
        this.out = new byte[(this.pos + len + 10)];
        this.plain[0] = (byte) ((rand() & 248) | this.pos);
        for (int i2 = 1; i2 <= this.pos; i2++) {
            this.plain[i2] = (byte) (rand() & 255);
        }
        this.pos++;
        for (int i3 = 0; i3 < 8; i3++) {
            this.prePlain[i3] = 0;
        }
        this.padding = 1;
        while (this.padding <= 2) {
            if (this.pos < 8) {
                byte[] bArr = this.plain;
                int i4 = this.pos;
                this.pos = i4 + 1;
                bArr[i4] = (byte) (rand() & 255);
                this.padding++;
            }
            if (this.pos == 8) {
                encrypt8Bytes();
            }
        }
        int i5 = offset;
        while (len > 0) {
            if (this.pos < 8) {
                byte[] bArr2 = this.plain;
                int i6 = this.pos;
                this.pos = i6 + 1;
                i = i5 + 1;
                bArr2[i6] = in[i5];
                len--;
            } else {
                i = i5;
            }
            if (this.pos == 8) {
                encrypt8Bytes();
                i5 = i;
            } else {
                i5 = i;
            }
        }
        this.padding = 1;
        while (this.padding <= 7) {
            if (this.pos < 8) {
                byte[] bArr3 = this.plain;
                int i7 = this.pos;
                this.pos = i7 + 1;
                bArr3[i7] = 0;
                this.padding++;
            }
            if (this.pos == 8) {
                encrypt8Bytes();
            }
        }
        return this.out;
    }

    public byte[] decode(byte[] code) {
        return decode(code, 0, code.length);
    }

    public byte[] decodeFromHexStr(String code) {
        return decode(HexUtil.hexStr2Bytes(code));
    }

    public byte[] decodeFromBase64Str(String code) {
        return decode(Base64.decode(code, 0));
    }

    private byte[] decode(byte[] in, int offset, int len) {
        this.preCrypt = 0;
        this.crypt = 0;
        byte[] m = new byte[(offset + 8)];
        if (len % 8 != 0 || len < 16) {
            return null;
        }
        this.prePlain = decipher(in, offset);
        if (this.prePlain == null) {
            return null;
        }
        this.pos = this.prePlain[0] & 7;
        int count = (len - this.pos) - 10;
        if (count < 0) {
            return null;
        }
        for (int i = offset; i < m.length; i++) {
            m[i] = 0;
        }
        this.out = new byte[count];
        this.preCrypt = 0;
        this.crypt = 8;
        this.contextStart = 8;
        this.pos++;
        this.padding = 1;
        while (this.padding <= 2) {
            if (this.pos < 8) {
                this.pos++;
                this.padding++;
            }
            if (this.pos == 8) {
                m = in;
                if (!decrypt8Bytes(in, offset, len)) {
                    return null;
                }
            }
        }
        int i2 = 0;
        while (count != 0) {
            if (this.pos < 8) {
                this.out[i2] = (byte) (m[(this.preCrypt + offset) + this.pos] ^ this.prePlain[this.pos]);
                i2++;
                count--;
                this.pos++;
            }
            if (this.pos == 8) {
                m = in;
                this.preCrypt = this.crypt - 8;
                if (!decrypt8Bytes(in, offset, len)) {
                    return null;
                }
            }
        }
        this.padding = 1;
        while (this.padding < 8) {
            if (this.pos < 8) {
                if ((m[(this.preCrypt + offset) + this.pos] ^ this.prePlain[this.pos]) != 0) {
                    return null;
                }
                this.pos++;
            }
            if (this.pos == 8) {
                m = in;
                this.preCrypt = this.crypt;
                if (!decrypt8Bytes(in, offset, len)) {
                    return null;
                }
            }
            this.padding++;
        }
        return this.out;
    }

    private byte[] encipher(byte[] in) {
        try {
            long y = getUnsignedInt(in, 0, 4);
            long z = getUnsignedInt(in, 4, 4);
            long a = getUnsignedInt(this.key, 0, 4);
            long b = getUnsignedInt(this.key, 4, 4);
            long c = getUnsignedInt(this.key, 8, 4);
            long d = getUnsignedInt(this.key, 12, 4);
            long sum = 0;
            long delta = -1640531527 & 4294967295L;
            int loop = 16;
            while (true) {
                int loop2 = loop - 1;
                if (loop > 0) {
                    sum = (sum + delta) & 4294967295L;
                    y = (y + ((((z << 4) + a) ^ (z + sum)) ^ ((z >>> 5) + b))) & 4294967295L;
                    z = (z + ((((y << 4) + c) ^ (y + sum)) ^ ((y >>> 5) + d))) & 4294967295L;
                    loop = loop2;
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(8);
                    DataOutputStream dos = new DataOutputStream(baos);
                    dos.writeInt((int) y);
                    dos.writeInt((int) z);
                    dos.close();
                    return baos.toByteArray();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] decipher(byte[] in, int offset) {
        try {
            long y = getUnsignedInt(in, offset, 4);
            long z = getUnsignedInt(in, offset + 4, 4);
            long a = getUnsignedInt(this.key, 0, 4);
            long b = getUnsignedInt(this.key, 4, 4);
            long c = getUnsignedInt(this.key, 8, 4);
            long d = getUnsignedInt(this.key, 12, 4);
            long sum = -478700656 & 4294967295L;
            long delta = -1640531527 & 4294967295L;
            int loop = 16;
            while (true) {
                int loop2 = loop - 1;
                if (loop > 0) {
                    z = (z - ((((y << 4) + c) ^ (y + sum)) ^ ((y >>> 5) + d))) & 4294967295L;
                    y = (y - ((((z << 4) + a) ^ (z + sum)) ^ ((z >>> 5) + b))) & 4294967295L;
                    sum = (sum - delta) & 4294967295L;
                    loop = loop2;
                } else {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(8);
                    DataOutputStream dos = new DataOutputStream(baos);
                    dos.writeInt((int) y);
                    dos.writeInt((int) z);
                    dos.close();
                    return baos.toByteArray();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    private byte[] decipher(byte[] in) {
        return decipher(in, 0);
    }

    private void encrypt8Bytes() {
        this.pos = 0;
        while (this.pos < 8) {
            if (this.header) {
                byte[] bArr = this.plain;
                int i = this.pos;
                bArr[i] = (byte) (bArr[i] ^ this.prePlain[this.pos]);
            } else {
                byte[] bArr2 = this.plain;
                int i2 = this.pos;
                bArr2[i2] = (byte) (bArr2[i2] ^ this.out[this.preCrypt + this.pos]);
            }
            this.pos++;
        }
        System.arraycopy(encipher(this.plain), 0, this.out, this.crypt, 8);
        this.pos = 0;
        while (this.pos < 8) {
            byte[] bArr3 = this.out;
            int i3 = this.crypt + this.pos;
            bArr3[i3] = (byte) (bArr3[i3] ^ this.prePlain[this.pos]);
            this.pos++;
        }
        System.arraycopy(this.plain, 0, this.prePlain, 0, 8);
        this.preCrypt = this.crypt;
        this.crypt += 8;
        this.pos = 0;
        this.header = false;
    }

    private boolean decrypt8Bytes(byte[] in, int offset, int len) {
        this.pos = 0;
        while (this.pos < 8) {
            if (this.contextStart + this.pos >= len) {
                return true;
            }
            byte[] bArr = this.prePlain;
            int i = this.pos;
            bArr[i] = (byte) (bArr[i] ^ in[(this.crypt + offset) + this.pos]);
            this.pos++;
        }
        this.prePlain = decipher(this.prePlain);
        if (this.prePlain == null) {
            return false;
        }
        this.contextStart += 8;
        this.crypt += 8;
        this.pos = 0;
        return true;
    }

    private int rand() {
        return random.nextInt();
    }

    public static long getUnsignedInt(byte[] in, int offset, int len) {
        int end;
        long ret = 0;
        if (len > 8) {
            end = offset + 8;
        } else {
            end = offset + len;
        }
        for (int i = offset; i < end; i++) {
            ret = (ret << 8) | ((long) (in[i] & 255));
        }
        return (4294967295L & ret) | (ret >>> 32);
    }

    public static String decryptValue(String key2, String value) {
        if (T.ckIsEmpty(value)) {
            return "";
        }
        try {
            byte[] result = new TEACoding(key2.getBytes("UTF-8")).decodeFromHexStr(value);
            if (result != null) {
                return new String(result, Charset.forName("UTF-8"));
            }
            IMLogger.w("decrypt Value failed！");
            return "";
        } catch (Exception e) {
            IMLogger.e("decrypt value failed : " + e.getMessage());
            return "";
        }
    }

    public static String encryptValue(String key2, String value) {
        if (T.ckIsEmpty(value)) {
            return "";
        }
        try {
            String result = new TEACoding(key2.getBytes("UTF-8")).encode2HexStr(value.getBytes("UTF-8"));
            if (result != null) {
                return result;
            }
            IMLogger.w("encrypt Value failed！");
            return "";
        } catch (Exception e) {
            IMLogger.e("encrypt value failed : " + e.getMessage());
            return "";
        }
    }
}
