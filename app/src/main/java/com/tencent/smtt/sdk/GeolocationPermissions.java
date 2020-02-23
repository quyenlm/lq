package com.tencent.smtt.sdk;

import android.webkit.ValueCallback;
import java.util.Set;

public class GeolocationPermissions {
    private static GeolocationPermissions a;

    private static synchronized GeolocationPermissions a() {
        GeolocationPermissions geolocationPermissions;
        synchronized (GeolocationPermissions.class) {
            if (a == null) {
                a = new GeolocationPermissions();
            }
            geolocationPermissions = a;
        }
        return geolocationPermissions;
    }

    public static GeolocationPermissions getInstance() {
        return a();
    }

    public void allow(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.GeolocationPermissions.getInstance().allow(str);
        } else {
            a2.c().g(str);
        }
    }

    public void clear(String str) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.GeolocationPermissions.getInstance().clear(str);
        } else {
            a2.c().f(str);
        }
    }

    public void clearAll() {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.GeolocationPermissions.getInstance().clearAll();
        } else {
            a2.c().o();
        }
    }

    public void getAllowed(String str, ValueCallback<Boolean> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.GeolocationPermissions.getInstance().getAllowed(str, valueCallback);
        } else {
            a2.c().c(str, valueCallback);
        }
    }

    public void getOrigins(ValueCallback<Set<String>> valueCallback) {
        br a2 = br.a();
        if (a2 == null || !a2.b()) {
            android.webkit.GeolocationPermissions.getInstance().getOrigins(valueCallback);
        } else {
            a2.c().b((ValueCallback<Set<String>>) valueCallback);
        }
    }
}
