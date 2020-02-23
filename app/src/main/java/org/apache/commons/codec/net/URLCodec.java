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
public class URLCodec implements BinaryDecoder, BinaryEncoder, StringDecoder, StringEncoder {
    protected static byte ESCAPE_CHAR;
    protected static final BitSet WWW_FORM_URL = null;
    protected String charset;

    public URLCodec() {
        throw new RuntimeException("Stub!");
    }

    public URLCodec(String charset2) {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] encodeUrl(BitSet urlsafe, byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    public static final byte[] decodeUrl(byte[] bytes) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public byte[] encode(byte[] bytes) {
        throw new RuntimeException("Stub!");
    }

    public byte[] decode(byte[] bytes) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String pString, String charset2) throws UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String pString) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public String decode(String pString, String charset2) throws DecoderException, UnsupportedEncodingException {
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

    @Deprecated
    public String getEncoding() {
        throw new RuntimeException("Stub!");
    }

    public String getDefaultCharset() {
        throw new RuntimeException("Stub!");
    }
}
