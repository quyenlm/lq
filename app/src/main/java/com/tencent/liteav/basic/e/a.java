package com.tencent.liteav.basic.e;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/* compiled from: RSAUtils */
public final class a {
    private static String a = "RSA";

    public static byte[] a(byte[] bArr, PrivateKey privateKey) throws Exception {
        byte[] doFinal;
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(2, privateKey);
        int i = 0;
        int length = bArr.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int i2 = i;
            if (length - i2 > 0) {
                if (length - i2 >= instance.getBlockSize()) {
                    doFinal = instance.doFinal(bArr, i2, instance.getBlockSize());
                } else {
                    doFinal = instance.doFinal(bArr, i2, length - i2);
                }
                byteArrayOutputStream.write(doFinal);
                i = instance.getBlockSize() + i2;
            } else {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
        }
    }

    public static PrivateKey a(byte[] bArr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return KeyFactory.getInstance(a).generatePrivate(new PKCS8EncodedKeySpec(bArr));
    }
}
