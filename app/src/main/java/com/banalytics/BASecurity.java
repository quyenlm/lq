package com.banalytics;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class BASecurity {
    BASecurity() {
    }

    static String sha256(byte[] data) {
        try {
            MessageDigest shaDigest = MessageDigest.getInstance("SHA-256");
            shaDigest.update(data);
            return toHex(shaDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String toHex(byte[] tmp) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] str = new char[(tmp.length * 2)];
        int k = 0;
        for (byte byte0 : tmp) {
            int k2 = k + 1;
            str[k] = hexDigits[(byte0 >>> 4) & 15];
            k = k2 + 1;
            str[k2] = hexDigits[byte0 & 15];
        }
        return new String(str);
    }
}
