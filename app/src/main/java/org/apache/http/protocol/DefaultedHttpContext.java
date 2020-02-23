package org.apache.http.protocol;

@Deprecated
public final class DefaultedHttpContext implements HttpContext {
    public DefaultedHttpContext(HttpContext local, HttpContext defaults) {
        throw new RuntimeException("Stub!");
    }

    public Object getAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    public Object removeAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    public void setAttribute(String id, Object obj) {
        throw new RuntimeException("Stub!");
    }

    public HttpContext getDefaults() {
        throw new RuntimeException("Stub!");
    }
}
