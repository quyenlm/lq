package com.tencent.gsdk.utils;

import android.annotation.SuppressLint;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: HttpDnsUtils */
public class h {
    protected static final char[] a = "0123456789abcdef".toCharArray();
    private static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    @SuppressLint({"TrulyRandom"})
    public static String a(String str, String str2) {
        byte[] bArr = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("utf-8"), "DES");
            Cipher instance = Cipher.getInstance("DES/ECB/PKCS5Padding");
            instance.init(1, secretKeySpec);
            if (str != null) {
                bArr = instance.doFinal(str.getBytes("utf-8"));
            }
        } catch (Exception e) {
            Logger.w("HttpDnsUtils encrypt error:" + e.getMessage());
        }
        return a(bArr);
    }

    public static String b(String str, String str2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes("utf-8"), "DES");
            Cipher instance = Cipher.getInstance("DES/ECB/PKCS5Padding");
            instance.init(2, secretKeySpec);
            byte[] doFinal = instance.doFinal(a(str));
            if (doFinal != null) {
                return new String(doFinal);
            }
        } catch (Exception e) {
            Logger.w("HttpDnsUtils decrypt error:" + e.getMessage());
        }
        return "";
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b2 = bArr[i];
            cArr[(i * 2) + 1] = b[b2 & 15];
            cArr[(i * 2) + 0] = b[((byte) (b2 >>> 4)) & 15];
        }
        return new String(cArr);
    }

    public static byte[] a(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4) + Character.digit(str.charAt(i + 1), 16));
        }
        return bArr;
    }
}
