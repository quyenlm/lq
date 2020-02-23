package org.apache.http.impl.cookie;

import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Deprecated
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler {
    public BasicMaxAgeHandler() {
        throw new RuntimeException("Stub!");
    }

    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }
}
