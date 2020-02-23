package org.apache.http.params;

@Deprecated
public final class DefaultedHttpParams extends AbstractHttpParams {
    public DefaultedHttpParams(HttpParams local, HttpParams defaults) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams copy() {
        throw new RuntimeException("Stub!");
    }

    public Object getParameter(String name) {
        throw new RuntimeException("Stub!");
    }

    public boolean removeParameter(String name) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams setParameter(String name, Object value) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams getDefaults() {
        throw new RuntimeException("Stub!");
    }
}
