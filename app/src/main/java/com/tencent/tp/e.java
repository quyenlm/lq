package com.tencent.tp;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

public class e {
    public static boolean a() {
        return !b() && !c() && !d();
    }

    public static boolean b() {
        return MessageDigest.isEqual("123456".getBytes(), "456".getBytes());
    }

    public static boolean c() {
        try {
            Signature instance = Signature.getInstance("SHA1withRSA");
            instance.initVerify(KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(new BigInteger("123456", 16), new BigInteger("11", 16))));
            instance.update("367".getBytes());
            return instance.verify("123098".getBytes());
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e) {
            return false;
        }
    }

    public static boolean d() {
        try {
            Signature instance = Signature.getInstance("SHA1withRSA");
            instance.initVerify(KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(new BigInteger("12345678", 16), new BigInteger("11", 16))));
            instance.update("367".getBytes());
            return instance.verify("123908".getBytes(), 1, 5);
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException e) {
            return false;
        }
    }
}
