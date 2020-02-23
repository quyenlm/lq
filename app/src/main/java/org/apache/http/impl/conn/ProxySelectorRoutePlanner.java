package org.apache.http.impl.conn;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class ProxySelectorRoutePlanner implements HttpRoutePlanner {
    protected ProxySelector proxySelector;
    protected SchemeRegistry schemeRegistry;

    public ProxySelectorRoutePlanner(SchemeRegistry schreg, ProxySelector prosel) {
        throw new RuntimeException("Stub!");
    }

    public ProxySelector getProxySelector() {
        throw new RuntimeException("Stub!");
    }

    public void setProxySelector(ProxySelector prosel) {
        throw new RuntimeException("Stub!");
    }

    public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public String getHost(InetSocketAddress isa) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Proxy chooseProxy(List<Proxy> list, HttpHost target, HttpRequest request, HttpContext context) {
        throw new RuntimeException("Stub!");
    }
}
