package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.SessionInputBuffer;

@Deprecated
public class ContentLengthInputStream extends InputStream {
    public ContentLengthInputStream(SessionInputBuffer in, long contentLength) {
        throw new RuntimeException("Stub!");
    }

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b, int off, int len) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public long skip(long n) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
