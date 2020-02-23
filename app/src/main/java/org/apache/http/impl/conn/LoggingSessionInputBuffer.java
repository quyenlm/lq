package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public class LoggingSessionInputBuffer implements SessionInputBuffer {
    public LoggingSessionInputBuffer(SessionInputBuffer in, Wire wire) {
        throw new RuntimeException("Stub!");
    }

    public boolean isDataAvailable(int timeout) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b, int off, int len) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int read(byte[] b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public String readLine() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public int readLine(CharArrayBuffer buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public HttpTransportMetrics getMetrics() {
        throw new RuntimeException("Stub!");
    }
}
