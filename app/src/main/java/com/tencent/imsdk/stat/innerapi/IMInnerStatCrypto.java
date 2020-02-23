package com.tencent.imsdk.stat.innerapi;

import com.tencent.imsdk.tool.encrypt.Base64;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class IMInnerStatCrypto {
    private static final String publicKey = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAz8aoT1TUVtZEtpuN4hyT\n6nSXHTljIW+plP87shul1PseGP8iEShO0t59zZUWtrbKi15WrYGpsnDKiw88ch+J\nZVrgjdSomtSQyn9x39Gx/+K1YuWc3Jq54yniPon0tzWqbYHcYer99g3mj6v1vfiZ\nDoXFFfbkkBobXwzz8g1ygWYytOGsEsXRXtGHY4TDtJ3GzaKujb7KWOe32MCD7UkB\nxFtVefw25Mg0rldTrnGGnwPKty1erbKy+Mz/GMVp+HgztuzoqThg8ZjzpQoTxbgD\nIKEMc2i3dZ8gqgKtKKIHnmtMgg/Qqm55nvM7Q76Hk6j6P7SC0FgB/5brWCKHGMGM\n3QIDAQAB\n-----END PUBLIC KEY-----";
    private static Cipher rsaCipher;
    private static String secretKey;

    protected static synchronized Cipher getRsaCipher() {
        Cipher cipher;
        synchronized (IMInnerStatCrypto.class) {
            if (rsaCipher == null) {
                try {
                    PublicKey encodeKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(publicKey.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("-----END PUBLIC KEY-----", ""), 2)));
                    rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    rsaCipher.init(1, encodeKey);
                } catch (InvalidKeySpecException e) {
                    IMLogger.e(e.getMessage());
                } catch (InvalidKeyException e2) {
                    IMLogger.e(e2.getMessage());
                } catch (NoSuchAlgorithmException e3) {
                    IMLogger.e(e3.getMessage());
                } catch (NoSuchPaddingException e4) {
                    IMLogger.e(e4.getMessage());
                }
            }
            cipher = rsaCipher;
        }
        return cipher;
    }

    public static String getKey() {
        try {
            if (rsaCipher == null) {
                getRsaCipher();
            }
            if (secretKey == null) {
                KeyGenerator instance = KeyGenerator.getInstance("AES");
                secretKey = UUID.randomUUID().toString().substring(0, 16);
            }
            return new String(Base64.encode(rsaCipher.doFinal(secretKey.getBytes(Charset.forName("UTF-8"))), 2), Charset.forName("UTF-8"));
        } catch (BadPaddingException e) {
            IMLogger.e(e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e2) {
            IMLogger.e(e2.getMessage());
            return null;
        } catch (IllegalBlockSizeException e3) {
            IMLogger.e(e3.getMessage());
            return null;
        }
    }

    public static String encrypt(String str) {
        if (secretKey == null) {
            getKey();
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(Charset.forName("UTF-8")), "AES");
            Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            aesCipher.init(1, secretKeySpec);
            return Base64.encodeToString(aesCipher.doFinal(str.getBytes(Charset.forName("UTF-8"))), 2);
        } catch (IllegalBlockSizeException e) {
            IMLogger.e(e.getMessage());
        } catch (InvalidKeyException e2) {
            IMLogger.e(e2.getMessage());
        } catch (BadPaddingException e3) {
            IMLogger.e(e3.getMessage());
        } catch (NoSuchAlgorithmException e4) {
            IMLogger.e(e4.getMessage());
        } catch (NoSuchPaddingException e5) {
            IMLogger.e(e5.getMessage());
        }
        return null;
    }
}
