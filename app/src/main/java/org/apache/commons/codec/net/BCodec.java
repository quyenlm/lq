package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

@Deprecated
public class BCodec extends RFC1522Codec implements StringDecoder, StringEncoder {
    public BCodec() {
        throw new RuntimeException("Stub!");
    }

    public BCodec(String charset) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public String getEncoding() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public byte[] doEncoding(byte[] bytes) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public byte[] doDecoding(byte[] bytes) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String value, String charset) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String value) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public String decode(String value) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public Object encode(Object value) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public Object decode(Object value) throws DecoderException {
        throw new RuntimeException("Stub!");
    }

    public String getDefaultCharset() {
        throw new RuntimeException("Stub!");
    }
}
