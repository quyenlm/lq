package org.apache.http.protocol;

import java.util.Map;

@Deprecated
public class HttpRequestHandlerRegistry implements HttpRequestHandlerResolver {
    public HttpRequestHandlerRegistry() {
        throw new RuntimeException("Stub!");
    }

    public void register(String pattern, HttpRequestHandler handler) {
        throw new RuntimeException("Stub!");
    }

    public void unregister(String pattern) {
        throw new RuntimeException("Stub!");
    }

    public void setHandlers(Map map) {
        throw new RuntimeException("Stub!");
    }

    public HttpRequestHandler lookup(String requestURI) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public boolean matchUriRequestPattern(String pattern, String requestUri) {
        throw new RuntimeException("Stub!");
    }
}
