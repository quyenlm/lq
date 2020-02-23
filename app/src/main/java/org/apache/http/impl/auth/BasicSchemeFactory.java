package org.apache.http.impl.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

@Deprecated
public class BasicSchemeFactory implements AuthSchemeFactory {
    public BasicSchemeFactory() {
        throw new RuntimeException("Stub!");
    }

    public AuthScheme newInstance(HttpParams params) {
        throw new RuntimeException("Stub!");
    }
}
