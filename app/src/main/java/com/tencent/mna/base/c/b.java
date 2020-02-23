package com.tencent.mna.base.c;

import android.content.Context;
import com.tencent.mna.base.utils.h;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: ReportHelper */
public class b {
    private static String a = "com.tencent.beacon.event.UserAction";

    public static boolean a(String str, boolean z, long j, long j2, Map<String, String> map, boolean z2) {
        boolean z3;
        if (map == null) {
            h.c("onUserAction failed, map is null");
            return false;
        }
        try {
            Class<?> cls = Class.forName(a);
            if (cls == null) {
                return false;
            }
            try {
                Method method = cls.getMethod("onUserAction", new Class[]{String.class, Boolean.TYPE, Long.TYPE, Long.TYPE, Map.class, Boolean.TYPE, Boolean.TYPE});
                if (method != null) {
                    try {
                        z3 = ((Boolean) method.invoke(cls, new Object[]{str, Boolean.valueOf(z), Long.valueOf(j), Long.valueOf(j2), map, Boolean.valueOf(z2), true})).booleanValue();
                    } catch (Exception e) {
                        h.a("onUserAction1 call method exception:" + e.getMessage());
                    }
                    return z3;
                }
                z3 = false;
                return z3;
            } catch (NoSuchMethodException e2) {
                h.a("onUserAction1 no such method exception");
                try {
                    Method method2 = cls.getMethod("onUserAction", new Class[]{String.class, Boolean.TYPE, Long.TYPE, Long.TYPE, Map.class, Boolean.TYPE});
                    if (method2 == null) {
                        return false;
                    }
                    try {
                        return ((Boolean) method2.invoke(cls, new Object[]{str, Boolean.valueOf(z), Long.valueOf(j), Long.valueOf(j2), map, Boolean.valueOf(z2)})).booleanValue();
                    } catch (Exception e3) {
                        h.a("onUserAction2 call method exception:" + e3.getMessage());
                        return false;
                    }
                } catch (NoSuchMethodException e4) {
                    h.a("onUserAction2 not suchmethod exception");
                    return false;
                }
            }
        } catch (ClassNotFoundException e5) {
            h.a("UserAction ClassNotFoundException, msg:" + e5.getMessage());
            return false;
        }
    }

    public static boolean a(String str, Context context) {
        try {
            Class<?> cls = Class.forName("com.tencent.beacongsdk.event.UserAction");
            if (cls != null) {
                try {
                    Method method = cls.getMethod("setAppkey", new Class[]{String.class});
                    if (method != null) {
                        method.invoke(cls, new Object[]{"0S000JK3HD2N4GEH"});
                        cls.getMethod("initUserAction", new Class[]{Context.class}).invoke(cls, new Object[]{context});
                        a = "com.tencent.beacongsdk.event.UserAction";
                        h.b("init local beacon suss");
                        return true;
                    }
                } catch (Exception e) {
                    h.a("onUserAction1 call method exception:" + e.getMessage());
                } catch (Throwable th) {
                    h.a("onUserAction1 no such method exception");
                }
            }
            return false;
        } catch (ClassNotFoundException e2) {
            h.a("UserAction ClassNotFoundException, msg:" + e2.getMessage());
            return false;
        }
    }
}
