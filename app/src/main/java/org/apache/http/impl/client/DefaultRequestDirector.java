package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ManagedClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

@Deprecated
public class DefaultRequestDirector implements RequestDirector {
    protected final ClientConnectionManager connManager;
    protected final HttpProcessor httpProcessor;
    protected final ConnectionKeepAliveStrategy keepAliveStrategy;
    protected ManagedClientConnection managedConn;
    protected final HttpParams params;
    protected final RedirectHandler redirectHandler;
    protected final HttpRequestExecutor requestExec;
    protected final HttpRequestRetryHandler retryHandler;
    protected final ConnectionReuseStrategy reuseStrategy;
    protected final HttpRoutePlanner routePlanner;

    public DefaultRequestDirector(HttpRequestExecutor requestExec2, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor2, HttpRequestRetryHandler retryHandler2, RedirectHandler redirectHandler2, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler userTokenHandler, HttpParams params2) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void rewriteRequestURI(RequestWrapper request, HttpRoute route) throws ProtocolException {
        throw new RuntimeException("Stub!");
    }

    public HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void releaseConnection() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void establishRoute(HttpRoute route, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean createTunnelToTarget(HttpRoute route, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean createTunnelToProxy(HttpRoute route, int hop, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HttpRequest createConnectRequest(HttpRoute route, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public RoutedRequest handleResponse(RoutedRequest roureq, HttpResponse response, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }
}
