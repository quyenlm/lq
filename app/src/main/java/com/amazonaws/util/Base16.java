package com.amazonaws.util;

public enum Base16 {
    ;
    
    private static final Base16Codec codec = null;

    static {
        codec = new Base16Codec();
    }

    public static String encodeAsString(byte... bytes) {
        if (bytes == null) {
            return null;
        }
        return bytes.length == 0 ? "" : CodecUtils.toStringDirect(codec.encode(bytes));
    }

    public static byte[] encode(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? bytes : codec.encode(bytes);
    }

    public static byte[] decode(String b16) {
        if (b16 == null) {
            return null;
        }
        if (b16.length() == 0) {
            return new byte[0];
        }
        byte[] buf = new byte[b16.length()];
        return codec.decode(buf, CodecUtils.sanitize(b16, buf));
    }

    public static byte[] decode(byte[] b16) {
        return (b16 == null || b16.length == 0) ? b16 : codec.decode(b16, b16.length);
    }
}
