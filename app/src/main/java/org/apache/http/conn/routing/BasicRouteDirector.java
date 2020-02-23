package org.apache.http.conn.routing;

@Deprecated
public class BasicRouteDirector implements HttpRouteDirector {
    public BasicRouteDirector() {
        throw new RuntimeException("Stub!");
    }

    public int nextStep(RouteInfo plan, RouteInfo fact) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int firstStep(RouteInfo plan) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int directStep(RouteInfo plan, RouteInfo fact) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public int proxiedStep(RouteInfo plan, RouteInfo fact) {
        throw new RuntimeException("Stub!");
    }
}
