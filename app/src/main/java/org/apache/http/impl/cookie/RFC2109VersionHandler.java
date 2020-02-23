package org.apache.http.impl.cookie;

import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Deprecated
public class RFC2109VersionHandler extends AbstractCookieAttributeHandler {
    public RFC2109VersionHandler() {
        throw new RuntimeException("Stub!");
    }

    public void parse(SetCookie cookie, String value) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }

    public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
        throw new RuntimeException("Stub!");
    }
}
