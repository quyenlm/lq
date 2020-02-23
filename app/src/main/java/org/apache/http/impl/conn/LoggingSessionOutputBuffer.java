package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public class LoggingSessionOutputBuffer implements SessionOutputBuffer {
    public LoggingSessionOutputBuffer(SessionOutputBuffer out, Wire wire) {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] b, int off, int len) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(int b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void write(byte[] b) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void flush() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeLine(CharArrayBuffer buffer) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeLine(String s) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public HttpTransportMetrics getMetrics() {
        throw new RuntimeException("Stub!");
    }
}
