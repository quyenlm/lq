package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

@Deprecated
public class Hex implements BinaryDecoder, BinaryEncoder {
    public Hex() {
        throw new RuntimeException("Stub!");
    }

    public static byte[] decodeHex(char[] data) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    protected static int toDigit(char ch, int index) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public static char[] encodeHex(byte[] data) {
        throw new RuntimeException("Stub!");
    }

    public byte[] decode(byte[] array) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public Object decode(Object object) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode(byte[] array) {
        throw new RuntimeException("Stub!");
    }

    public Object encode(Object object) throws EncoderException {
        throw new RuntimeException("Stub!");
    }
}
