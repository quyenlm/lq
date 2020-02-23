package org.apache.http.protocol;

import java.util.Map;

@Deprecated
public class UriPatternMatcher {
    public UriPatternMatcher() {
        throw new RuntimeException("Stub!");
    }

    public void register(String pattern, Object handler) {
        throw new RuntimeException("Stub!");
    }

    public void unregister(String pattern) {
        throw new RuntimeException("Stub!");
    }

    public void setHandlers(Map map) {
        throw new RuntimeException("Stub!");
    }

    public Object lookup(String requestURI) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public boolean matchUriRequestPattern(String pattern, String requestUri) {
        throw new RuntimeException("Stub!");
    }
}
