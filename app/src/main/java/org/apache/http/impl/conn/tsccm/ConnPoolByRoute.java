package org.apache.http.impl.conn.tsccm;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

@Deprecated
public class ConnPoolByRoute extends AbstractConnPool {
    protected Queue<BasicPoolEntry> freeConnections;
    protected final int maxTotalConnections;
    protected final ClientConnectionOperator operator;
    protected final Map<HttpRoute, RouteSpecificPool> routeToPool;
    protected Queue<WaitingThread> waitingThreads;

    public ConnPoolByRoute(ClientConnectionOperator operator2, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Queue<BasicPoolEntry> createFreeConnQueue() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Queue<WaitingThread> createWaitingThreadQueue() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Map<HttpRoute, RouteSpecificPool> createRouteToPoolMap() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public RouteSpecificPool newRouteSpecificPool(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public WaitingThread newWaitingThread(Condition cond, RouteSpecificPool rospl) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public RouteSpecificPool getRoutePool(HttpRoute route, boolean create) {
        throw new RuntimeException("Stub!");
    }

    public int getConnectionsInPool(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    public PoolEntryRequest requestPoolEntry(HttpRoute route, Object state) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public BasicPoolEntry getEntryBlocking(HttpRoute route, Object state, long timeout, TimeUnit tunit, WaitingThreadAborter aborter) throws ConnectionPoolTimeoutException, InterruptedException {
        throw new RuntimeException("Stub!");
    }

    public void freeEntry(BasicPoolEntry entry, boolean reusable, long validDuration, TimeUnit timeUnit) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public BasicPoolEntry getFreeEntry(RouteSpecificPool rospl, Object state) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public BasicPoolEntry createEntry(RouteSpecificPool rospl, ClientConnectionOperator op) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void deleteEntry(BasicPoolEntry entry) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void deleteLeastUsedEntry() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void handleLostEntry(HttpRoute route) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void notifyWaitingThread(RouteSpecificPool rospl) {
        throw new RuntimeException("Stub!");
    }

    public void deleteClosedConnections() {
        throw new RuntimeException("Stub!");
    }

    public void shutdown() {
        throw new RuntimeException("Stub!");
    }
}
