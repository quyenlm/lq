package org.apache.http.impl.client;

import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HttpContext;

@Deprecated
public class DefaultTargetAuthenticationHandler extends AbstractAuthenticationHandler {
    public DefaultTargetAuthenticationHandler() {
        throw new RuntimeException("Stub!");
    }

    public boolean isAuthenticationRequested(HttpResponse response, HttpContext context) {
        throw new RuntimeException("Stub!");
    }

    public Map<String, Header> getChallenges(HttpResponse response, HttpContext context) throws MalformedChallengeException {
        throw new RuntimeException("Stub!");
    }
}
