package com.tencent.qqgamemi.mgc.pb;

import com.squareup.wire.Message;
import com.squareup.wire.Wire;
import java.io.IOException;
import okio.ByteString;

public class ProtoUtils {
    public static ByteString encodeString(String value) {
        if (value == null) {
            return null;
        }
        return ByteString.encodeUtf8(value);
    }

    public static String decodeString(ByteString stream) {
        if (stream == null) {
            return null;
        }
        return stream.utf8();
    }

    public static <M extends Message> M parseFrom(byte[] bytes, Class<M> messageClass) throws IOException {
        if (bytes == null || messageClass == null) {
            return null;
        }
        return getWire().parseFrom(bytes, messageClass);
    }

    public static Wire getWire() {
        return WireHolder.getWire();
    }

    public static ByteString safeEncodeUtf8(String s) {
        return safeEncodeUtf8(s, ByteString.EMPTY);
    }

    public static ByteString safeEncodeUtf8(String s, ByteString def) {
        return s == null ? def : ByteString.encodeUtf8(s);
    }

    public static String safeDecodeUtf8(ByteString bs) {
        return safeDecodeUtf8(bs, (String) null);
    }

    public static String safeDecodeUtf8(ByteString bs, String def) {
        return bs == null ? def : bs.utf8();
    }

    public static int safeInteger2Int(Integer value, int def) {
        return value == null ? def : value.intValue();
    }

    public static long safeBigLong2Long(Long value, long def) {
        return value == null ? def : value.longValue();
    }
}
