package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

@Deprecated
public class QuotedPrintableCodec implements BinaryDecoder, BinaryEncoder, StringDecoder, StringEncoder {
    public QuotedPrintableCodec() {
        throw new RuntimeException("Stub!");
    }

    public QuotedPrintableCodec(String charset) {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] encodeQuotedPrintable(BitSet printable, byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] decodeQuotedPrintable(byte[] bytes) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode(byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    public byte[] decode(byte[] bytes) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String pString) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public String decode(String pString, String charset) throws DecoderException, UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }

    public String decode(String pString) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public Object encode(Object pObject) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public Object decode(Object pObject) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public String getDefaultCharset() {
        throw new RuntimeException("Stub!");
    }

    public String encode(String pString, String charset) throws UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }
}
