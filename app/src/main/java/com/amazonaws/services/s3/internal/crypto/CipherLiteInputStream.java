package com.amazonaws.services.s3.internal.crypto;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public final class CipherLiteInputStream extends SdkFilterInputStream {
    private static final int DEFAULT_IN_BUFFER_SIZE = 512;
    private static final int MAX_RETRY = 1000;
    private byte[] bufin;
    private byte[] bufout;
    private final CipherLite cipherLite;
    private int curr_pos;
    private boolean eof;
    private final boolean lastMultiPart;
    private int max_pos;
    private final boolean multipart;

    public CipherLiteInputStream(InputStream is, CipherLite cipherLite2) {
        this(is, cipherLite2, 512, false, false);
    }

    public CipherLiteInputStream(InputStream is, CipherLite c, int buffsize) {
        this(is, c, buffsize, false, false);
    }

    public CipherLiteInputStream(InputStream is, CipherLite c, int buffsize, boolean multipart2, boolean lastMultiPart2) {
        super(is);
        this.eof = false;
        this.curr_pos = 0;
        this.max_pos = 0;
        if (!lastMultiPart2 || multipart2) {
            this.multipart = multipart2;
            this.lastMultiPart = lastMultiPart2;
            this.cipherLite = c;
            if (buffsize <= 0 || buffsize % 512 != 0) {
                throw new IllegalArgumentException("buffsize (" + buffsize + ") must be a positive multiple of " + 512);
            }
            this.bufin = new byte[buffsize];
            return;
        }
        throw new IllegalArgumentException("lastMultiPart can only be true if multipart is true");
    }

    protected CipherLiteInputStream(InputStream is) {
        this(is, CipherLite.Null, 512, false, false);
    }

    public int read() throws IOException {
        if (this.curr_pos >= this.max_pos) {
            if (this.eof) {
                return -1;
            }
            int count = 0;
            while (count <= 1000) {
                int len = nextChunk();
                count++;
                if (len != 0) {
                    if (len == -1) {
                        return -1;
                    }
                }
            }
            throw new IOException("exceeded maximum number of attempts to read next chunk of data");
        }
        byte[] bArr = this.bufout;
        int i = this.curr_pos;
        this.curr_pos = i + 1;
        return bArr[i] & 255;
    }

    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    public int read(byte[] buf, int off, int target_len) throws IOException {
        if (this.curr_pos >= this.max_pos) {
            if (this.eof) {
                return -1;
            }
            int count = 0;
            while (count <= 1000) {
                int len = nextChunk();
                count++;
                if (len != 0) {
                    if (len == -1) {
                        return -1;
                    }
                }
            }
            throw new IOException("exceeded maximum number of attempts to read next chunk of data");
        }
        if (target_len <= 0) {
            return 0;
        }
        int len2 = this.max_pos - this.curr_pos;
        if (target_len < len2) {
            len2 = target_len;
        }
        System.arraycopy(this.bufout, this.curr_pos, buf, off, len2);
        this.curr_pos += len2;
        return len2;
    }

    public long skip(long n) throws IOException {
        abortIfNeeded();
        int available = this.max_pos - this.curr_pos;
        if (n > ((long) available)) {
            n = (long) available;
        }
        if (n < 0) {
            return 0;
        }
        this.curr_pos = (int) (((long) this.curr_pos) + n);
        return n;
    }

    public int available() {
        abortIfNeeded();
        return this.max_pos - this.curr_pos;
    }

    public void close() throws IOException {
        this.in.close();
        if (!this.multipart && !S3CryptoScheme.isAesGcm(this.cipherLite.getCipherAlgorithm())) {
            try {
                this.cipherLite.doFinal();
            } catch (BadPaddingException | IllegalBlockSizeException e) {
            }
        }
        this.max_pos = 0;
        this.curr_pos = 0;
        abortIfNeeded();
    }

    public boolean markSupported() {
        abortIfNeeded();
        return this.in.markSupported() && this.cipherLite.markSupported();
    }

    public void mark(int readlimit) {
        abortIfNeeded();
        this.in.mark(readlimit);
        this.cipherLite.mark();
    }

    public void reset() throws IOException {
        abortIfNeeded();
        this.in.reset();
        this.cipherLite.reset();
        if (markSupported()) {
            this.max_pos = 0;
            this.curr_pos = 0;
            this.eof = false;
        }
    }

    private int nextChunk() throws IOException {
        int i = 0;
        abortIfNeeded();
        if (this.eof) {
            return -1;
        }
        this.bufout = null;
        int len = this.in.read(this.bufin);
        if (len == -1) {
            this.eof = true;
            if (!this.multipart || this.lastMultiPart) {
                try {
                    this.bufout = this.cipherLite.doFinal();
                    if (this.bufout == null) {
                        return -1;
                    }
                    this.curr_pos = 0;
                    int length = this.bufout.length;
                    this.max_pos = length;
                    return length;
                } catch (IllegalBlockSizeException e) {
                } catch (BadPaddingException e2) {
                    if (S3CryptoScheme.isAesGcm(this.cipherLite.getCipherAlgorithm())) {
                        throw new SecurityException(e2);
                    }
                }
            }
            return -1;
        }
        this.bufout = this.cipherLite.update(this.bufin, 0, len);
        this.curr_pos = 0;
        if (this.bufout != null) {
            i = this.bufout.length;
        }
        this.max_pos = i;
        return i;
    }
}
