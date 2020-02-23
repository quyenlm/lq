package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.Header;
import org.apache.http.io.SessionInputBuffer;

@Deprecated
public class ChunkedInputStream extends InputStream {
    public ChunkedInputStream(SessionInputBuffer in) {
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

    public void close() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public Header[] getFooters() {
        throw new RuntimeException("Stub!");
    }
}
