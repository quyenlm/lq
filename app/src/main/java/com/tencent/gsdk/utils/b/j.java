package com.tencent.gsdk.utils.b;

import android.content.Context;
import com.tencent.gsdk.utils.Logger;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: UserAction */
public final class j {
    private static Class a;
    private static Method b;

    static {
        try {
            a = Class.forName("com.tencent.beacon.event.UserAction");
        } catch (Exception e) {
            Logger.d("Can not find UserAction class", e);
            a = null;
        }
    }

    public static boolean a(String str) {
        if (a == null) {
            return false;
        }
        try {
            a.getMethod("setAppkey", new Class[]{String.class}).invoke((Object) null, new Object[]{str});
            return true;
        } catch (Exception e) {
            Logger.d("Call UserAction.setAppkey() failed", e);
            return false;
        }
    }

    public static boolean a(Context context) {
        if (a == null) {
            return false;
        }
        try {
            a.getMethod("initUserAction", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
            return true;
        } catch (Exception e) {
            Logger.d("Call UserAction.initUserAction() failed", e);
            return false;
        }
    }

    public static boolean a(String str, boolean z, long j, long j2, Map<String, String> map, boolean z2) {
        if (a == null) {
            return false;
        }
        try {
            if (b == null) {
                b = a.getMethod("onUserAction", new Class[]{String.class, Boolean.TYPE, Long.TYPE, Long.TYPE, Map.class, Boolean.TYPE, Boolean.TYPE});
            }
            return ((Boolean) b.invoke((Object) null, new Object[]{str, Boolean.valueOf(z), Long.valueOf(j), Long.valueOf(j2), map, Boolean.valueOf(z2), true})).booleanValue();
        } catch (Exception e) {
            try {
                if (b == null) {
                    b = a.getMethod("onUserAction", new Class[]{String.class, Boolean.TYPE, Long.TYPE, Long.TYPE, Map.class, Boolean.TYPE});
                }
                return ((Boolean) b.invoke((Object) null, new Object[]{str, Boolean.valueOf(z), Long.valueOf(j), Long.valueOf(j2), map, Boolean.valueOf(z2)})).booleanValue();
            } catch (Exception e2) {
                Logger.d("Call UserAction.onUserAction() failed", e2);
                return false;
            }
        }
    }
}
