package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;

@Deprecated
public class BasicEofSensorWatcher implements EofSensorWatcher {
    protected boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicEofSensorWatcher(ManagedClientConnection conn, boolean reuse) {
        throw new RuntimeException("Stub!");
    }

    public boolean eofDetected(InputStream wrapped) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean streamClosed(InputStream wrapped) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean streamAbort(InputStream wrapped) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
