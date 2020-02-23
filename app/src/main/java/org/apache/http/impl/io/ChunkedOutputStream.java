package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

@Deprecated
public class ChunkedOutputStream extends OutputStream {
    public ChunkedOutputStream(SessionOutputBuffer out, int bufferSize) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public ChunkedOutputStream(SessionOutputBuffer out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void flushCache() throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void flushCacheWithAppend(byte[] bufferToAppend, int off, int len) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void writeClosingChunk() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void finish() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(int b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] src, int off, int len) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void flush() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
