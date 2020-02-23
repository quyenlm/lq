package org.apache.commons.codec.net;

import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;

@Deprecated
abstract class RFC1522Codec {
    /* access modifiers changed from: protected */
    public abstract byte[] doDecoding(byte[] bArr) throws DecoderException;

    /* access modifiers changed from: protected */
    public abstract byte[] doEncoding(byte[] bArr) throws EncoderException;

    /* access modifiers changed from: protected */
    public abstract String getEncoding();

    RFC1522Codec() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public String encodeText(String text, String charset) throws EncoderException, UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public String decodeText(String text) throws DecoderException, UnsupportedEncodingException {
        throw new RuntimeException("Stub!");
    }
}
