package com.amazonaws.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BinaryUtils {
    public static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte hexString : data) {
            String hex = Integer.toHexString(hexString);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return StringUtils.lowerCase(sb.toString());
    }

    public static byte[] fromHex(String hexData) {
        byte[] result = new byte[((hexData.length() + 1) / 2)];
        int stringOffset = 0;
        int byteOffset = 0;
        while (stringOffset < hexData.length()) {
            String hexNumber = hexData.substring(stringOffset, stringOffset + 2);
            stringOffset += 2;
            result[byteOffset] = (byte) Integer.parseInt(hexNumber, 16);
            byteOffset++;
        }
        return result;
    }

    public static String toBase64(byte[] data) {
        return Base64.encodeAsString(data);
    }

    public static byte[] fromBase64(String b64Data) {
        if (b64Data == null) {
            return null;
        }
        return Base64.decode(b64Data);
    }

    public static InputStream toStream(ByteBuffer byteBuffer) {
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);
        return new ByteArrayInputStream(bytes);
    }
}
