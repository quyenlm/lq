package com.tencent.mna.base.utils;

import java.io.UnsupportedEncodingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: AESUtils */
public final class a {
    public static final byte[] a = {49, 50, 51, 52, 53, 54, 55, 56, 49, 50, 51, 52, 53, 53, 55, 56};
    private static final byte[] b = {77, 78, 65, 64, 50, 48, 49, 55, 71, 79, 72, 69, 65, 68, 33, 33};

    public static byte[] a(byte[] bArr, String str) {
        try {
            return a(str.getBytes("UTF-8"), bArr, 1);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr2, bArr, 2);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, int i) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, "AES");
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(i, secretKeySpec, new IvParameterSpec(b));
            return instance.doFinal(bArr);
        } catch (Exception e) {
            h.d("docrypt exception:" + e.getMessage());
            return null;
        }
    }
}
