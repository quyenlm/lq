package com.amazonaws.services.s3.internal;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5DigestCalculatingInputStream extends SdkFilterInputStream {
    private MessageDigest digest;
    private MessageDigest digestLastMarked;

    public MD5DigestCalculatingInputStream(InputStream in) {
        super(in);
        try {
            this.digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("unexpected", e);
        }
    }

    public byte[] getMd5Digest() {
        return this.digest.digest();
    }

    public void mark(int readlimit) {
        super.mark(readlimit);
        if (markSupported()) {
            try {
                this.digestLastMarked = (MessageDigest) this.digest.clone();
            } catch (CloneNotSupportedException e) {
                throw new IllegalStateException("unexpected", e);
            }
        }
    }

    public void reset() throws IOException {
        super.reset();
        if (this.digestLastMarked != null) {
            try {
                this.digest = (MessageDigest) this.digestLastMarked.clone();
            } catch (CloneNotSupportedException e) {
                throw new IllegalStateException("unexpected", e);
            }
        }
    }

    public int read() throws IOException {
        int ch = super.read();
        if (ch != -1) {
            this.digest.update((byte) ch);
        }
        return ch;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        if (result != -1) {
            this.digest.update(b, off, result);
        }
        return result;
    }
}
