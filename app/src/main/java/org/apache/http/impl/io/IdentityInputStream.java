package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.SessionInputBuffer;

@Deprecated
public class IdentityInputStream extends InputStream {
    public IdentityInputStream(SessionInputBuffer in) {
        throw new RuntimeException("Stub!");
    }

    public int available() throws IOException {
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
}
