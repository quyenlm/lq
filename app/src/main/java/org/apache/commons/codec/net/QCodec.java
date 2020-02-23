package org.apache.commons.codec.net;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.StringEncoder;

@Deprecated
public class QCodec extends RFC1522Codec implements StringDecoder, StringEncoder {
    public QCodec() {
        throw new RuntimeException("Stub!");
    }

    public QCodec(String charset) {
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

    public String encode(String pString, String charset) throws EncoderException {
        throw new RuntimeException("Stub!");
    }

    public String encode(String pString) throws EncoderException {
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

    public boolean isEncodeBlanks() {
        throw new RuntimeException("Stub!");
    }

    public void setEncodeBlanks(boolean b) {
        throw new RuntimeException("Stub!");
    }
}
