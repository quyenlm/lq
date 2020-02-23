package com.tencent.gsdk.utils.b;

import android.content.Context;
import com.tencent.gsdk.utils.Logger;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: TDataMaster */
final class i {
    private static Class a;
    private static Object b;
    private static Method c;

    static {
        try {
            a = Class.forName("com.tencent.tdm.TDataMaster");
            b = a.getMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance() failed", e);
            a = null;
            b = null;
        }
    }

    public static boolean a(Context context) {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("initialize", new Class[]{Context.class}).invoke(b, new Object[]{context});
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().initialize() failed", e);
            return false;
        }
    }

    public static boolean a() {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("onResume", new Class[0]).invoke(b, new Object[0]);
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().onResume() failed", e);
            return false;
        }
    }

    public static boolean b() {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("onStart", new Class[0]).invoke(b, new Object[0]);
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().onStart() failed", e);
            return false;
        }
    }

    public static boolean c() {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("onPause", new Class[0]).invoke(b, new Object[0]);
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().onPause() failed", e);
            return false;
        }
    }

    public static boolean d() {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("onStop", new Class[0]).invoke(b, new Object[0]);
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().onStop() failed", e);
            return false;
        }
    }

    public static boolean e() {
        if (b == null) {
            return false;
        }
        try {
            a.getMethod("onDestroy", new Class[0]).invoke(b, new Object[0]);
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().onDestroy() failed", e);
            return false;
        }
    }

    public static boolean a(String str, Map<String, String> map) {
        if (b == null) {
            return false;
        }
        try {
            if (c == null) {
                c = a.getMethod("reportEvent", new Class[]{Integer.TYPE, String.class, Map.class});
            }
            c.invoke(b, new Object[]{1002, str, map});
            return true;
        } catch (Exception e) {
            Logger.d("Call TDataMaster.getInstance().reportEvent() failed", e);
            return false;
        }
    }
}
