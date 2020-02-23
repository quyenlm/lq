package com.garena.android.beepost.service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    public static final String HMAC_SHA256 = "HMACSHA256";

    public static String getHTTPRequestSignature(String key, String url, String paramBody) {
        return HMAC256Digest(key, "POST|" + url + "|" + paramBody);
    }

    public static String toHexString(byte[] keyData) {
        if (keyData == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(keyData.length * 2);
        for (byte aKeyData : keyData) {
            String hexStr = Integer.toString(aKeyData & 255, 16);
            if (hexStr.length() == 1) {
                hexStr = "0" + hexStr;
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }

    public static String HMAC256Digest(String key, String message) {
        return HMACDigest(message, key, "HMACSHA256");
    }

    private static String HMACDigest(String msg, String keyString, String algo) {
        try {
            SecretKeySpec key = new SecretKeySpec(keyString.getBytes("UTF-8"), algo);
            Mac mac = Mac.getInstance(algo);
            mac.init(key);
            byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));
            StringBuilder hash = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(aByte & 255);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            return hash.toString();
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateNonce() {
        String randString = UUID.randomUUID().toString().replace("-", "");
        if (randString.length() > 32) {
            return randString.substring(0, 32);
        }
        return randString;
    }
}
