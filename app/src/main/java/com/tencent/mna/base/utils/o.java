package com.tencent.mna.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/* compiled from: TMSDKWifiHelper */
public final class o {
    private static boolean a = false;
    private static Class<?> b;
    private static Class<?> c;
    private static Class<?> d;
    private static Object e;
    private static Object f;
    private static int g = -3;
    private static int h = 2;
    private static int i = 1;
    private static int j = 0;

    public static synchronized int a(Context context, boolean z) {
        int i2 = 0;
        synchronized (o.class) {
            h.a("TMSDKWifiHelper init");
            if (!a) {
                if (!z) {
                    i2 = -1;
                } else {
                    try {
                        b = Class.forName("com.wifisdk.ui.TMSDKWifiManager");
                        c = Class.forName("com.wifisdk.ui.TMSDKWifiManager$TMSDKWifiResultListener");
                        d = Class.forName("com.wifisdk.ui.WifiSDKUIActivity");
                        b.getDeclaredMethod("setEnableLog", new Class[]{Boolean.TYPE}).invoke((Object) null, new Object[]{true});
                        e = b.getDeclaredMethod("getInstance", new Class[0]).invoke((Object) null, new Object[0]);
                        b.getDeclaredMethod("init", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
                        a aVar = new a();
                        f = Proxy.newProxyInstance(o.class.getClassLoader(), new Class[]{c}, aVar);
                        h = b.getField("FILTER_LATENCY").getInt((Object) null);
                        i = b.getField("FILTER_SUCCESS_RATE").getInt((Object) null);
                        j = b.getField("FILTER_SIGNAL_STRENGTH").getInt((Object) null);
                        a = true;
                        h.a("TMSDKWifiHelper init succeed.");
                    } catch (Exception e2) {
                        h.a("TMSDKWifiHelper init failed, exception:" + e2.getMessage());
                        i2 = -2;
                    }
                }
            }
        }
        return i2;
    }

    public static synchronized int a(Activity activity) {
        int i2 = -2;
        synchronized (o.class) {
            if (!a) {
                i2 = -1;
            } else if (activity != null) {
                try {
                    if (d != null && "com.wifisdk.ui.WifiSDKUIActivity".equals(d.getName())) {
                        activity.startActivity(new Intent(activity, d));
                        i2 = 0;
                    }
                } catch (Exception e2) {
                    h.a("TMSDKWifiHelper startWifiActivity failed, exception:" + e2.getMessage());
                }
            }
        }
        return i2;
    }

    public static int a() {
        try {
            Activity b2 = d.b();
            if (b2 != null) {
                return a(b2);
            }
        } catch (Exception e2) {
        }
        return -3;
    }

    public static synchronized void a(double d2, double d3, double d4) {
        synchronized (o.class) {
            if (a) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put(Integer.valueOf(h), new Double[]{Double.valueOf(Double.NaN), Double.valueOf(Double.NaN), Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE), Double.valueOf(d2)});
                    hashMap.put(Integer.valueOf(i), new Double[]{Double.valueOf(Double.NaN), Double.valueOf(Double.NaN), Double.valueOf(d3), Double.valueOf(Double.NaN)});
                    hashMap.put(Integer.valueOf(j), new Double[]{Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE), Double.valueOf(4.0d), Double.valueOf(d4), Double.valueOf(4.0d)});
                    b.getDeclaredMethod("setThresholds", new Class[]{HashMap.class, Boolean.TYPE}).invoke(e, new Object[]{hashMap, true});
                    h.a("TMSDKWifiHelper setThres:" + System.currentTimeMillis() + ", delay:" + d2 + ", succRate:" + d3 + ", minSignal:" + d4);
                } catch (Exception e2) {
                    h.a("TMSDKWifiHelper setThres failed, exception:" + e2.getMessage());
                }
            }
        }
        return;
    }

    public static synchronized void b() {
        synchronized (o.class) {
            if (a) {
                try {
                    g = -101;
                    h.a("TMSDKWifiHelper update start:" + System.currentTimeMillis());
                    b.getDeclaredMethod("startCheckFreeWifi", new Class[]{c}).invoke(e, new Object[]{f});
                } catch (Exception e2) {
                    h.a("TMSDKWifiHelper update start failed, exception:" + e2.getMessage());
                }
            }
        }
        return;
    }

    public static synchronized int c() {
        int i2;
        synchronized (o.class) {
            if (!a) {
                i2 = -102;
            } else {
                h.a("TMSDKWifiHelper update stop:" + System.currentTimeMillis() + ", wifinum:" + g);
                i2 = g;
            }
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public static synchronized void b(int i2) {
        synchronized (o.class) {
            g = i2;
        }
    }

    /* compiled from: TMSDKWifiHelper */
    private static class a implements InvocationHandler {
        private a() {
        }

        public Object invoke(Object obj, Method method, Object[] objArr) {
            boolean z = true;
            if (method != null) {
                try {
                    if ("onWifiListCheckFinish".equals(method.getName())) {
                        if (objArr.length == 1) {
                            int intValue = objArr[0].intValue();
                            o.b(intValue);
                            h.a("TMSDKWifiHelper onWifiListCheckFinish, updateRet:" + intValue);
                        }
                    } else if ("onConnectionFinish".equals(method.getName())) {
                        h.a("TMSDKWifiHelper onConnectionFinish");
                    } else if ("equals".equals(method.getName()) && objArr.length == 1) {
                        if (obj == null || obj != objArr[0]) {
                            z = false;
                        }
                        return Boolean.valueOf(z);
                    }
                } catch (Throwable th) {
                    h.a("TMSDKWifiHelper Dynamic proxy invoke failed, throwable:" + th.getMessage());
                }
            }
            return null;
        }
    }
}
