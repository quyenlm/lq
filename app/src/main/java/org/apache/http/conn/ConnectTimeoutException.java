package org.apache.http.conn;

import java.io.InterruptedIOException;

public class ConnectTimeoutException extends InterruptedIOException {
    public ConnectTimeoutException() {
        throw new RuntimeException("Stub!");
    }

    public ConnectTimeoutException(String arg0) {
        throw new RuntimeException("Stub!");
    }
}
