package org.apache.http.impl;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpResponse;
import org.apache.http.TokenIterator;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultConnectionReuseStrategy implements ConnectionReuseStrategy {
    public DefaultConnectionReuseStrategy() {
        throw new RuntimeException("Stub!");
    }

    public boolean keepAlive(HttpResponse response, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public TokenIterator createTokenIterator(HeaderIterator hit) {
        throw new RuntimeException("Stub!");
    }
}
