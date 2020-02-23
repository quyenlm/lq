package org.apache.http.impl.cookie;

import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Deprecated
public class BasicExpiresHandler extends AbstractCookieAttributeHandler {
    public BasicExpiresHandler(String[] datepatterns) {
        throw new RuntimeException("Stub!");
    }

    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }
}
