package com.tencent.tdm.Utils;

public class HexUtil {
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final byte[] emptybytes = new byte[0];

    public static String byte2HexStr(byte b) {
        char[] buf = new char[2];
        buf[1] = digits[b & 15];
        buf[0] = digits[((byte) (b >>> 4)) & 15];
        return new String(buf);
    }

    public static String bytes2HexStr(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        char[] buf = new char[(bytes.length * 2)];
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf[(i * 2) + 1] = digits[b & 15];
            buf[(i * 2) + 0] = digits[((byte) (b >>> 4)) & 15];
        }
        return new String(buf);
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

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String input = "234" + i;
            if (!new String(hexStr2Bytes(bytes2HexStr(input.getBytes()))).equals(input)) {
                System.out.println("error:" + input);
            }
        }
        System.out.println("use:" + (System.currentTimeMillis() - begin));
    }
}
