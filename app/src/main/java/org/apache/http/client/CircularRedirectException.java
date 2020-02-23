package org.apache.http.client;

@Deprecated
public class CircularRedirectException extends RedirectException {
    public CircularRedirectException() {
        throw new RuntimeException("Stub!");
    }

    public CircularRedirectException(String message) {
        throw new RuntimeException("Stub!");
    }

    public CircularRedirectException(String message, Throwable cause) {
        throw new RuntimeException("Stub!");
    }
}
