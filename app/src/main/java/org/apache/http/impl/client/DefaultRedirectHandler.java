package org.apache.http.impl.client;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.RedirectHandler;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultRedirectHandler implements RedirectHandler {
    public DefaultRedirectHandler() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public URI getLocationURI(HttpResponse response, HttpContext context) throws ProtocolException {
        throw new RuntimeException("Stub!");
    }
}
