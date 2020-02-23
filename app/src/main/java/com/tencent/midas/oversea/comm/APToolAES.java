package com.tencent.midas.oversea.comm;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class APToolAES {
    public static String decPress(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 5; i < bArr.length; i++) {
            stringBuffer.append((byte) ((bArr[i] >> 4) & 15));
            stringBuffer.append((byte) (bArr[i] & 15));
        }
        return stringBuffer.toString();
    }

    public static String decryptAES(byte[] bArr, String str) {
        byte[] bArr2 = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(2, secretKeySpec);
            bArr2 = instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArr2 != null ? new String(bArr2) : "";
    }

    public static byte[] decryptAES2(byte[] bArr, String str) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(2, secretKeySpec);
            return instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptDES(byte[] bArr, String str) {
        byte[] bArr2 = null;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(2, secretKeySpec);
            bArr2 = instance.doFinal(bArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bArr2 != null ? new String(bArr2) : "";
    }

    public static String doDecode(String str, String str2) {
        return decryptAES(parseHexStr2Byte(str), str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0009, code lost:
        r1 = decryptAES2(r0, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String doDecode(java.lang.String r3, java.lang.String r4, int r5) {
        /*
            byte[] r0 = parseHexStr2Byte(r3)
            if (r0 != 0) goto L_0x0009
            java.lang.String r0 = ""
        L_0x0008:
            return r0
        L_0x0009:
            byte[] r1 = decryptAES2(r0, r4)
            if (r1 != 0) goto L_0x0012
            java.lang.String r0 = ""
            goto L_0x0008
        L_0x0012:
            java.lang.String r0 = new java.lang.String
            r2 = 0
            r0.<init>(r1, r2, r5)
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.midas.oversea.comm.APToolAES.doDecode(java.lang.String, java.lang.String, int):java.lang.String");
    }

    public static String doEncode(String str, String str2) {
        String str3 = new String(str);
        while (str3.length() % 16 != 0) {
            str3 = str3 + APDataReportManager.GAMEANDMONTHSLIST_PRE;
        }
        return encryptAES(str3, str2);
    }

    public static String encryptAES(String str, String str2) {
        byte[] bArr;
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher instance = Cipher.getInstance("AES/ECB/NoPadding");
            instance.init(1, secretKeySpec);
            bArr = instance.doFinal(str.getBytes());
        } catch (Exception e) {
            APLog.w("encryptAESTools", String.valueOf(e));
            bArr = null;
        }
        return bArr != null ? new String(parseByte2HexStr(bArr)) : str;
    }

    public static String parseByte2HexStr(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                hexString = '0' + hexString;
            }
            stringBuffer.append(hexString.toUpperCase());
        }
        return stringBuffer.toString();
    }

    public static byte[] parseHexStr2Byte(String str) {
        if (str.length() < 1) {
            return null;
        }
        byte[] bArr = new byte[(str.length() / 2)];
        for (int i = 0; i < str.length() / 2; i++) {
            bArr[i] = (byte) ((Integer.parseInt(str.substring(i * 2, (i * 2) + 1), 16) * 16) + Integer.parseInt(str.substring((i * 2) + 1, (i * 2) + 2), 16));
        }
        return bArr;
    }
}
