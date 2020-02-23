package com.amazonaws.services.s3.internal;

import com.amazonaws.internal.SdkFilterInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractRepeatableCipherInputStream<T> extends SdkFilterInputStream {
    private final T cipherFactory;
    private boolean hasBeenAccessed;
    private final InputStream unencryptedDataStream;

    /* access modifiers changed from: protected */
    public abstract FilterInputStream createCipherInputStream(InputStream inputStream, T t);

    protected AbstractRepeatableCipherInputStream(InputStream input, FilterInputStream cipherInputStream, T cipherFactory2) {
        super(cipherInputStream);
        this.unencryptedDataStream = input;
        this.cipherFactory = cipherFactory2;
    }

    public boolean markSupported() {
        abortIfNeeded();
        return this.unencryptedDataStream.markSupported();
    }

    public void mark(int readlimit) {
        abortIfNeeded();
        if (this.hasBeenAccessed) {
            throw new UnsupportedOperationException("Marking is only supported before your first call to read or skip.");
        }
        this.unencryptedDataStream.mark(readlimit);
    }

    public void reset() throws IOException {
        abortIfNeeded();
        this.unencryptedDataStream.reset();
        this.in = createCipherInputStream(this.unencryptedDataStream, this.cipherFactory);
        this.hasBeenAccessed = false;
    }

    public int read() throws IOException {
        this.hasBeenAccessed = true;
        return super.read();
    }

    public int read(byte[] b) throws IOException {
        this.hasBeenAccessed = true;
        return super.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        this.hasBeenAccessed = true;
        return super.read(b, off, len);
    }

    public long skip(long n) throws IOException {
        this.hasBeenAccessed = true;
        return super.skip(n);
    }
}
