package com.tencent.qqgamemi.mgc.pb;

public class cryptor {
    public static byte[] encrypt(byte[] var0, int var1, int var2, byte[] var3) {
        if (var0 == null || var3 == null) {
            return null;
        }
        byte[] var4 = new byte[var2];
        System.arraycopy(var0, var1, var4, 0, var2);
        byte[] var5 = new byte[var3.length];
        System.arraycopy(var3, 0, var5, 0, var3.length);
        return new ByteUtil().b(var4, var5);
    }

    public static byte[] decrypt(byte[] var0, int var1, int var2, byte[] var3) {
        if (var0 == null || var3 == null) {
            return null;
        }
        byte[] var4 = new byte[var2];
        System.arraycopy(var0, var1, var4, 0, var2);
        byte[] var5 = new byte[var3.length];
        System.arraycopy(var3, 0, var5, 0, var3.length);
        return new ByteUtil().a(var4, var5);
    }
}
