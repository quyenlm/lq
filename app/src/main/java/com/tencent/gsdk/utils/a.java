package com.tencent.gsdk.utils;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: AesUtils */
public class a {
    private static String a = "ToBeOrNot0123456";
    private static String b = "1234567812345678";

    @SuppressLint({"TrulyRandom"})
    public static String a(String str) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = instance.getBlockSize();
            byte[] bytes = str.getBytes();
            int length = bytes.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] bArr = new byte[length];
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
            instance.init(1, new SecretKeySpec(a.getBytes(), "AES"), new IvParameterSpec(b.getBytes()));
            return b.a(instance.doFinal(bArr));
        } catch (Exception e) {
            Logger.w("AesUtils encrypt error:" + e.getMessage());
            return null;
        }
    }

    public static String b(String str) {
        int i;
        int i2 = 0;
        try {
            byte[] a2 = b.a(str);
            Cipher instance = Cipher.getInstance("AES/CBC/NoPadding");
            instance.init(2, new SecretKeySpec(a.getBytes(), "AES"), new IvParameterSpec(b.getBytes()));
            byte[] doFinal = instance.doFinal(a2);
            while (true) {
                i = i2;
                if (i < doFinal.length && doFinal[(doFinal.length - 1) - i] == 0) {
                    i2 = i + 1;
                }
            }
            return new String(doFinal, 0, doFinal.length - i);
        } catch (Exception e) {
            Logger.w("AesUtils decrypt error:" + e.getMessage());
            return null;
        }
    }
}
