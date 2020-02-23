package com.tencent.tp;

import com.appsflyer.share.Constants;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public class c {
    static final String a = "EasyJNI";
    static ClassLoader b = c.class.getClassLoader();
    static Vector c = new Vector(2, 2);

    public static Class a(String str) {
        if (str != null) {
            if (str.contains(Constants.URL_PATH_DELIMITER)) {
                str = str.replace('/', '.');
            }
            try {
                return !c.isEmpty() ? ((ClassLoader) c.lastElement()).loadClass(str) : b.loadClass(str);
            } catch (ClassNotFoundException e) {
            }
        }
        return null;
    }

    public static synchronized ClassLoader a(String str, String str2) {
        DexClassLoader dexClassLoader;
        synchronized (c.class) {
            if (str == null) {
                dexClassLoader = null;
            } else {
                ClassLoader classLoader = !c.isEmpty() ? (ClassLoader) c.lastElement() : b;
                if (TssSdkRuntime.getPackageInfo() == null) {
                    dexClassLoader = null;
                } else {
                    String str3 = TssSdkRuntime.getPackageInfo().applicationInfo.dataDir + File.separatorChar + "files";
                    File file = new File(str3);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    if (!file.canWrite()) {
                    }
                    dexClassLoader = new DexClassLoader(str, str3, str2, classLoader);
                    if (dexClassLoader != null) {
                        c.add(dexClassLoader);
                    }
                }
            }
        }
        return dexClassLoader;
    }

    public static Object a(Object obj, String str, String str2, Class[] clsArr, Object[] objArr) {
        Class a2;
        if (obj == null || (a2 = a(str)) == null) {
            return null;
        }
        try {
            return a2.getDeclaredMethod(str2, clsArr).invoke(obj, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public static Object a(String str, Object obj, String str2) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object a(String str, String str2, Class[] clsArr, Object[] objArr) {
        Class a2 = a(str);
        if (a2 == null) {
            return null;
        }
        try {
            return a2.getDeclaredMethod(str2, clsArr).invoke((Object) null, objArr);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }
}
