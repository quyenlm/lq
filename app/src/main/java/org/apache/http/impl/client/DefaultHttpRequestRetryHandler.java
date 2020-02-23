package org.apache.http.impl.client;

import java.io.IOException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultHttpRequestRetryHandler implements HttpRequestRetryHandler {
    public DefaultHttpRequestRetryHandler(int retryCount, boolean requestSentRetryEnabled) {
        throw new RuntimeException("Stub!");
    }

    public DefaultHttpRequestRetryHandler() {
        throw new RuntimeException("Stub!");
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public boolean isRequestSentRetryEnabled() {
        throw new RuntimeException("Stub!");
    }

    public int getRetryCount() {
        throw new RuntimeException("Stub!");
    }
}
