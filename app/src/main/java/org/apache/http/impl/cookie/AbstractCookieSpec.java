package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.cookie.CookieAttributeHandler;
import org.apache.http.cookie.CookieSpec;

@Deprecated
public abstract class AbstractCookieSpec implements CookieSpec {
    public AbstractCookieSpec() {
        throw new RuntimeException("Stub!");
    }

    public void registerAttribHandler(String name, CookieAttributeHandler handler) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public CookieAttributeHandler findAttribHandler(String name) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public CookieAttributeHandler getAttribHandler(String name) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public Collection<CookieAttributeHandler> getAttribHandlers() {
        throw new RuntimeException("Stub!");
    }
}
