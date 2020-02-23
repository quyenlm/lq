package org.apache.http.protocol;

@Deprecated
public class BasicHttpContext implements HttpContext {
    public BasicHttpContext() {
        throw new RuntimeException("Stub!");
    }

    public BasicHttpContext(HttpContext parentContext) {
        throw new RuntimeException("Stub!");
    }

    public Object getAttribute(String id) {
        throw new RuntimeException("Stub!");
    }

    public void setAttribute(String id, Object obj) {
        throw new RuntimeException("Stub!");
    }

    public Object removeAttribute(String id) {
        throw new RuntimeException("Stub!");
    }
}
