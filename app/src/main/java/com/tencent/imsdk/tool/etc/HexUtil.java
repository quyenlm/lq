package com.tencent.imsdk.tool.etc;

import java.util.Locale;

public class HexUtil {
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final byte[] emptybytes = new byte[0];

    public static String byte2HexStr(byte b) {
        StringBuilder sb = new StringBuilder();
        sb.append(digits[(b & 255) >> 4]);
        sb.append(digits[b & 15]);
        sb.append(' ');
        return sb.toString().trim().toUpperCase(Locale.US);
    }

    public static String bytes2HexStr(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        for (byte b : bytes) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte hexStr2Byte(String str) {
        if (str == null || str.length() != 1) {
            return 0;
        }
        return char2Byte(str.charAt(0));
    }

    public static byte char2Byte(char ch) {
        if (ch >= '0' && ch <= '9') {
            return (byte) (ch - '0');
        }
        if (ch >= 'a' && ch <= 'f') {
            return (byte) ((ch - 'a') + 10);
        }
        if (ch < 'A' || ch > 'F') {
            return 0;
        }
        return (byte) ((ch - 'A') + 10);
    }

    public static byte[] hexStr2Bytes(String str) {
        if (str == null || str.equals("")) {
            return emptybytes;
        }
        byte[] bytes = new byte[(str.length() / 2)];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ((char2Byte(str.charAt(i * 2)) * 16) + char2Byte(str.charAt((i * 2) + 1)));
        }
        return bytes;
    }
}
