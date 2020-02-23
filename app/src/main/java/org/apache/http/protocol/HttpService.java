package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.HttpServerConnection;
import org.apache.http.params.HttpParams;

@Deprecated
public class HttpService {
    public HttpService(HttpProcessor proc, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory) {
        throw new RuntimeException("Stub!");
    }

    public void setHttpProcessor(HttpProcessor processor) {
        throw new RuntimeException("Stub!");
    }

    public void setConnReuseStrategy(ConnectionReuseStrategy connStrategy) {
        throw new RuntimeException("Stub!");
    }

    public void setResponseFactory(HttpResponseFactory responseFactory) {
        throw new RuntimeException("Stub!");
    }

    public void setHandlerResolver(HttpRequestHandlerResolver handlerResolver) {
        throw new RuntimeException("Stub!");
    }

    public void setExpectationVerifier(HttpExpectationVerifier expectationVerifier) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams getParams() {
        throw new RuntimeException("Stub!");
    }

    public void setParams(HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    public void handleRequest(HttpServerConnection conn, HttpContext context) throws IOException, HttpException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void handleException(HttpException ex, HttpResponse response) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void doService(HttpRequest request, HttpResponse response, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }
}
