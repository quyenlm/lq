package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.HttpHost;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteTracker;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
public abstract class AbstractPoolEntry {
    protected final ClientConnectionOperator connOperator;
    protected final OperatedClientConnection connection;
    protected volatile HttpRoute route;
    protected volatile Object state;
    protected volatile RouteTracker tracker;

    protected AbstractPoolEntry(ClientConnectionOperator connOperator2, HttpRoute route2) {
        throw new RuntimeException("Stub!");
    }

    public Object getState() {
        throw new RuntimeException("Stub!");
    }

    public void setState(Object state2) {
        throw new RuntimeException("Stub!");
    }

    public void open(HttpRoute route2, HttpContext context, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void tunnelTarget(boolean secure, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void tunnelProxy(HttpHost next, boolean secure, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void layerProtocol(HttpContext context, HttpParams params) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void shutdownEntry() {
        throw new RuntimeException("Stub!");
    }
}
