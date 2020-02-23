package com.tencent.wetest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.view.InputDeviceCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class U3DAutomation {
    protected static String a = "wetest";
    protected static View b = null;
    protected static boolean c = false;
    protected static Class d = null;
    protected static Field e = null;
    protected static int f = 0;
    protected static Method g = null;
    protected static Activity h = null;
    protected static Context i = null;
    protected static WindowManager j = null;
    protected static f k = null;

    public static int GetHeight() {
        f f2 = f();
        if (f2 == null) {
            return -1;
        }
        return f2.b;
    }

    public static Activity GetPlayerActivity() {
        if (h != null) {
            return h;
        }
        try {
            Class d2 = d();
            if (d2 == null) {
                Log.e(a, "GetPlayerActivity: can't find com.unity3d.player.UnityPlayer");
                return null;
            }
            Field a2 = a(d2, "currentActivity");
            if (a2 == null) {
                Log.e(a, "GetPlayerActivity: can't find currentActivity");
                return null;
            }
            Object obj = a2.get((Object) null);
            if (obj == null) {
                Log.e(a, "GetPlayerActivity: can't get currentActivity");
                return null;
            }
            h = (Activity) obj;
            return h;
        } catch (Exception e2) {
            Log.e(a, e2.getMessage(), e2);
        }
    }

    public static int GetWidth() {
        Log.i(a, "GetWidth( )");
        f f2 = f();
        if (f2 == null) {
            return -1;
        }
        return f2.a;
    }

    @SuppressLint({"NewApi", "Recycle"})
    public static void InjectTouchEvent(int i2, float f2, float f3) {
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, i2, f2, f3, i2 == 1 ? 0.0f : 1.0f, 1.0f, 0, 1.0f, 1.0f, 0, 0);
        if (Build.VERSION.SDK_INT >= 12) {
            obtain.setSource(InputDeviceCompat.SOURCE_TOUCHSCREEN);
        }
        View e2 = e();
        if (e2 == null) {
            Log.e(a, "Unable to get UnityPlayer object! please check the Unity3D version.");
            return;
        }
        if (f == 0) {
            f = b();
            Log.i(a, "Unity version = " + f);
        }
        e2.post(new e(obtain, e2));
    }

    private static Field a(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (NoSuchFieldException e2) {
            Class superclass = cls.getSuperclass();
            if (superclass != null) {
                return a(superclass, str);
            }
            throw e2;
        }
    }

    private static int b() {
        try {
            Field a2 = a(d, "l");
            if (a2 != null) {
                a2.setAccessible(true);
                if (a2.getGenericType().equals(Boolean.TYPE)) {
                    return 4;
                }
            }
            Field a3 = a(d, "k");
            if (a3 == null) {
                return 0;
            }
            a3.setAccessible(true);
            return a3.getGenericType().equals(Boolean.TYPE) ? 5 : 0;
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    private static Field b(Class cls, String str) {
        for (Field field : cls.getDeclaredFields()) {
            if (field.getGenericType().toString().equals(str)) {
                Log.i(a, "get field ok, type=" + str);
                return field;
            }
        }
        Class superclass = cls.getSuperclass();
        if (superclass == null) {
            return null;
        }
        return b(superclass, str);
    }

    /* access modifiers changed from: private */
    public static void b(boolean z) {
        try {
            if (f == 4) {
                if (e == null) {
                    e = a(d, "l");
                    if (e == null) {
                        Log.e(a, "can't find forwardNative!");
                        return;
                    }
                    e.setAccessible(true);
                }
                e.setBoolean(b, z);
            } else if (f == 5) {
                if (g == null) {
                    g = d.getDeclaredMethod("nativeForwardEventsToDalvik", new Class[]{Boolean.TYPE});
                    if (g == null) {
                        Log.e(a, "can't find nativeForwardEventsToDalvik!");
                        return;
                    }
                    g.setAccessible(true);
                }
                g.invoke(b, new Object[]{Boolean.valueOf(z)});
            } else {
                Log.e(a, "Unknown Unity Version.");
            }
        } catch (NoSuchFieldException e2) {
            Log.e(a, "setForward", e2);
        } catch (IllegalAccessException e3) {
            Log.e(a, "setForward", e3);
        } catch (IllegalArgumentException e4) {
            Log.e(a, "setForward", e4);
        } catch (NoSuchMethodException e5) {
            Log.e(a, "setForward", e5);
        } catch (InvocationTargetException e6) {
            Log.e(a, "setForward", e6);
        }
    }

    /* access modifiers changed from: private */
    public static boolean c() {
        String str;
        try {
            if (e == null) {
                if (f == 5) {
                    str = "k";
                } else if (f != 4) {
                    return false;
                } else {
                    str = "l";
                }
                e = a(d, str);
                if (e == null) {
                    Log.e(a, "can't find forwardNative!");
                    return false;
                }
                e.setAccessible(true);
            }
            return e.getBoolean(b);
        } catch (NoSuchFieldException e2) {
            Log.e(a, "getForward", e2);
            return false;
        } catch (IllegalAccessException e3) {
            Log.e(a, "getForward", e3);
            return false;
        } catch (IllegalArgumentException e4) {
            Log.e(a, "getForward", e4);
            return false;
        }
    }

    private static Class d() {
        if (d != null) {
            return d;
        }
        try {
            d = Class.forName("com.unity3d.player.UnityPlayer");
            return d;
        } catch (ClassNotFoundException e2) {
            Log.e(a, "can't find com.unity3d.player.UnityPlayer");
            return null;
        }
    }

    private static View e() {
        if (b != null) {
            return b;
        }
        try {
            Class d2 = d();
            if (d2 == null) {
                Log.e(a, "can't find com.unity3d.player.UnityPlayer");
                return null;
            }
            Field a2 = a(d2, "currentActivity");
            if (a2 == null) {
                Log.e(a, "can't find currentActivity");
                return null;
            }
            Object obj = a2.get((Object) null);
            if (obj == null) {
                Log.e(a, "can't get currentActivity");
                return null;
            }
            Class<?> cls = obj.getClass();
            Log.i(a, "currentActivity = " + cls.toString());
            Field b2 = b(cls, "class com.unity3d.player.UnityPlayer");
            if (b2 == null) {
                Log.e(a, "com.unity3d.player.UnityPlayer not get.");
                return null;
            }
            b2.setAccessible(true);
            b = (View) b2.get(obj);
            return b;
        } catch (NoSuchFieldException e2) {
            Log.e(a, e2.getMessage(), e2);
            return null;
        } catch (IllegalAccessException e3) {
            Log.e(a, e3.getMessage(), e3);
            return null;
        } catch (IllegalArgumentException e4) {
            Log.e(a, e4.getMessage(), e4);
            return null;
        } catch (NullPointerException e5) {
            Log.e(a, e5.getMessage(), e5);
            return null;
        }
    }

    private static f f() {
        Log.i(a, "GetMResolution Enter");
        Activity GetPlayerActivity = GetPlayerActivity();
        if (GetPlayerActivity == null) {
            Log.e(a, "GetContext: get activity is null");
            return null;
        }
        Rect rect = new Rect();
        try {
            GetPlayerActivity.getWindow().getDecorView().getLocalVisibleRect(rect);
            k = new f(rect.width(), rect.height());
            return k;
        } catch (Exception e2) {
            Log.e(a, e2.getMessage());
            return null;
        }
    }
}
