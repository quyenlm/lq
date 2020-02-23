package org.apache.http.impl.auth;

import java.util.Map;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
public abstract class RFC2617Scheme extends AuthSchemeBase {
    public RFC2617Scheme() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void parseChallenge(CharArrayBuffer buffer, int pos, int len) throws MalformedChallengeException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Map<String, String> getParameters() {
        throw new RuntimeException("Stub!");
    }

    public String getParameter(String name) {
        throw new RuntimeException("Stub!");
    }

    public String getRealm() {
        throw new RuntimeException("Stub!");
    }
}
