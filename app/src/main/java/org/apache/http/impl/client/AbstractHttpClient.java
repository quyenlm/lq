package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.cookie.CookieSpecRegistry;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpProcessor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

@Deprecated
public abstract class AbstractHttpClient implements HttpClient {
    /* access modifiers changed from: protected */
    public abstract AuthSchemeRegistry createAuthSchemeRegistry();

    /* access modifiers changed from: protected */
    public abstract ClientConnectionManager createClientConnectionManager();

    /* access modifiers changed from: protected */
    public abstract ConnectionKeepAliveStrategy createConnectionKeepAliveStrategy();

    /* access modifiers changed from: protected */
    public abstract ConnectionReuseStrategy createConnectionReuseStrategy();

    /* access modifiers changed from: protected */
    public abstract CookieSpecRegistry createCookieSpecRegistry();

    /* access modifiers changed from: protected */
    public abstract CookieStore createCookieStore();

    /* access modifiers changed from: protected */
    public abstract CredentialsProvider createCredentialsProvider();

    /* access modifiers changed from: protected */
    public abstract HttpContext createHttpContext();

    /* access modifiers changed from: protected */
    public abstract HttpParams createHttpParams();

    /* access modifiers changed from: protected */
    public abstract BasicHttpProcessor createHttpProcessor();

    /* access modifiers changed from: protected */
    public abstract HttpRequestRetryHandler createHttpRequestRetryHandler();

    /* access modifiers changed from: protected */
    public abstract HttpRoutePlanner createHttpRoutePlanner();

    /* access modifiers changed from: protected */
    public abstract AuthenticationHandler createProxyAuthenticationHandler();

    /* access modifiers changed from: protected */
    public abstract RedirectHandler createRedirectHandler();

    /* access modifiers changed from: protected */
    public abstract HttpRequestExecutor createRequestExecutor();

    /* access modifiers changed from: protected */
    public abstract AuthenticationHandler createTargetAuthenticationHandler();

    /* access modifiers changed from: protected */
    public abstract UserTokenHandler createUserTokenHandler();

    protected AbstractHttpClient(ClientConnectionManager conman, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized HttpParams getParams() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setParams(HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized ClientConnectionManager getConnectionManager() {
        throw new RuntimeException("Stub!");
    }

    public final synchronized HttpRequestExecutor getRequestExecutor() {
        throw new RuntimeException("Stub!");
    }

    public final synchronized AuthSchemeRegistry getAuthSchemes() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setAuthSchemes(AuthSchemeRegistry authSchemeRegistry) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized CookieSpecRegistry getCookieSpecs() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setCookieSpecs(CookieSpecRegistry cookieSpecRegistry) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized ConnectionReuseStrategy getConnectionReuseStrategy() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setReuseStrategy(ConnectionReuseStrategy reuseStrategy) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized ConnectionKeepAliveStrategy getConnectionKeepAliveStrategy() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setKeepAliveStrategy(ConnectionKeepAliveStrategy keepAliveStrategy) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized HttpRequestRetryHandler getHttpRequestRetryHandler() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setHttpRequestRetryHandler(HttpRequestRetryHandler retryHandler) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized RedirectHandler getRedirectHandler() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setRedirectHandler(RedirectHandler redirectHandler) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized AuthenticationHandler getTargetAuthenticationHandler() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setTargetAuthenticationHandler(AuthenticationHandler targetAuthHandler) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized AuthenticationHandler getProxyAuthenticationHandler() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setProxyAuthenticationHandler(AuthenticationHandler proxyAuthHandler) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized CookieStore getCookieStore() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setCookieStore(CookieStore cookieStore) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized CredentialsProvider getCredentialsProvider() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setCredentialsProvider(CredentialsProvider credsProvider) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized HttpRoutePlanner getRoutePlanner() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setRoutePlanner(HttpRoutePlanner routePlanner) {
        throw new RuntimeException("Stub!");
    }

    public final synchronized UserTokenHandler getUserTokenHandler() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void setUserTokenHandler(UserTokenHandler userTokenHandler) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public final synchronized BasicHttpProcessor getHttpProcessor() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addResponseInterceptor(HttpResponseInterceptor itcp, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized HttpResponseInterceptor getResponseInterceptor(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getResponseInterceptorCount() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void clearResponseInterceptors() {
        throw new RuntimeException("Stub!");
    }

    public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> cls) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp) {
        throw new RuntimeException("Stub!");
    }

    public synchronized void addRequestInterceptor(HttpRequestInterceptor itcp, int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized HttpRequestInterceptor getRequestInterceptor(int index) {
        throw new RuntimeException("Stub!");
    }

    public synchronized int getRequestInterceptorCount() {
        throw new RuntimeException("Stub!");
    }

    public synchronized void clearRequestInterceptors() {
        throw new RuntimeException("Stub!");
    }

    public void removeRequestInterceptorByClass(Class<? extends HttpRequestInterceptor> cls) {
        throw new RuntimeException("Stub!");
    }

    public final HttpResponse execute(HttpUriRequest request) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public final HttpResponse execute(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public final HttpResponse execute(HttpHost target, HttpRequest request) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public final HttpResponse execute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public RequestDirector createClientRequestDirector(HttpRequestExecutor requestExec, ClientConnectionManager conman, ConnectionReuseStrategy reustrat, ConnectionKeepAliveStrategy kastrat, HttpRoutePlanner rouplan, HttpProcessor httpProcessor, HttpRequestRetryHandler retryHandler, RedirectHandler redirectHandler, AuthenticationHandler targetAuthHandler, AuthenticationHandler proxyAuthHandler, UserTokenHandler stateHandler, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public HttpParams determineParams(HttpRequest req) {
        throw new RuntimeException("Stub!");
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public <T> T execute(HttpUriRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }

    public <T> T execute(HttpHost target, HttpRequest request, ResponseHandler<? extends T> responseHandler, HttpContext context) throws IOException, ClientProtocolException {
        throw new RuntimeException("Stub!");
    }
}
