package org.apache.http.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;

@Deprecated
public class RequestDate implements HttpRequestInterceptor {
    public RequestDate() {
        throw new RuntimeException("Stub!");
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        throw new RuntimeException("Stub!");
    }
}
