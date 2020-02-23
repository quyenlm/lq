package org.apache.http.impl;

import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseFactory;
import org.apache.http.ProtocolVersion;
import org.apache.http.ReasonPhraseCatalog;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultHttpResponseFactory implements HttpResponseFactory {
    protected final ReasonPhraseCatalog reasonCatalog;

    public DefaultHttpResponseFactory(ReasonPhraseCatalog catalog) {
        throw new RuntimeException("Stub!");
    }

    public DefaultHttpResponseFactory() {
        throw new RuntimeException("Stub!");
    }

    public HttpResponse newHttpResponse(ProtocolVersion ver, int status, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public HttpResponse newHttpResponse(StatusLine statusline, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Locale determineLocale(HttpContext context) {
        throw new RuntimeException("Stub!");
    }
}
