package org.apache.http.conn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.entity.HttpEntityWrapper;

@Deprecated
public class BasicManagedEntity extends HttpEntityWrapper implements ConnectionReleaseTrigger, EofSensorWatcher {
    protected final boolean attemptReuse;
    protected ManagedClientConnection managedConn;

    public BasicManagedEntity(HttpEntity entity, ManagedClientConnection conn, boolean reuse) {
        super((HttpEntity) null);
        throw new RuntimeException("Stub!");
    }

    public boolean isRepeatable() {
        throw new RuntimeException("Stub!");
    }

    public InputStream getContent() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void consumeContent() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void writeTo(OutputStream outstream) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void releaseConnection() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void abortConnection() throws IOException {
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

    /* access modifiers changed from: protected */
    public void releaseManagedConnection() throws IOException {
        throw new RuntimeException("Stub!");
    }
}
