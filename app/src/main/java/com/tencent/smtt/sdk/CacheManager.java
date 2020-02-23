package com.tencent.smtt.sdk;

import com.tencent.smtt.utils.v;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

public final class CacheManager {
    @Deprecated
    public static boolean cacheDisabled() {
        br a = br.a();
        if (a != null && a.b()) {
            return ((Boolean) a.c().c()).booleanValue();
        }
        Object a2 = v.a("android.webkit.CacheManager", "cacheDisabled");
        if (a2 == null) {
            return false;
        }
        return ((Boolean) a2).booleanValue();
    }

    public static InputStream getCacheFile(String str, boolean z) {
        br a = br.a();
        if (a == null || !a.b()) {
            return null;
        }
        return a.c().a(str, z);
    }

    public static Object getCacheFile(String str, Map<String, String> map) {
        br a = br.a();
        if (a != null && a.b()) {
            return a.c().g();
        }
        try {
            return v.a(Class.forName("android.webkit.CacheManager"), "getCacheFile", (Class<?>[]) new Class[]{String.class, Map.class}, str, map);
        } catch (Exception e) {
            return null;
        }
    }

    @Deprecated
    public static File getCacheFileBaseDir() {
        br a = br.a();
        return (a == null || !a.b()) ? (File) v.a("android.webkit.CacheManager", "getCacheFileBaseDir") : (File) a.c().g();
    }
}
