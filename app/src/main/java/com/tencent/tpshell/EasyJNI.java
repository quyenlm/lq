package com.tencent.tpshell;

import android.content.Context;
import com.appsflyer.share.Constants;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Vector;

public class EasyJNI {
    static final String SIG = "Lcom/tencent/tpshell/AAA;";
    static final String TAG = "EasyJNI";
    static ClassLoader sAppClassLoader = EasyJNI.class.getClassLoader();
    static Vector<ClassLoader> sClassLoaderCollector = new Vector<>(2, 2);

    public static Class<?> loadClass(String str) {
        if (str != null) {
            if (str.contains(Constants.URL_PATH_DELIMITER)) {
                str = str.replace('/', '.');
            }
            try {
                if (!sClassLoaderCollector.isEmpty()) {
                    return sClassLoaderCollector.lastElement().loadClass(str);
                }
                return sAppClassLoader.loadClass(str);
            } catch (Throwable th) {
            }
        }
        return null;
    }

    public static synchronized ClassLoader newDexClassLoader(Context context, String str, String str2) {
        ClassLoader classLoader;
        DexClassLoader dexClassLoader;
        synchronized (EasyJNI.class) {
            if (str == null) {
                dexClassLoader = null;
            } else {
                if (!sClassLoaderCollector.isEmpty()) {
                    classLoader = sClassLoaderCollector.lastElement();
                } else {
                    classLoader = sAppClassLoader;
                }
                String absolutePath = context.getFilesDir().getAbsolutePath();
                File file = new File(absolutePath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                if (!file.canWrite()) {
                }
                dexClassLoader = new DexClassLoader(str, absolutePath, str2, classLoader);
                if (dexClassLoader != null) {
                    sClassLoaderCollector.add(dexClassLoader);
                }
            }
        }
        return dexClassLoader;
    }

    public static Object invokeStaticMethod(String str, String str2, Class<?>[] clsArr, Object[] objArr) {
        Class<?> loadClass = loadClass(str);
        if (loadClass == null) {
            return null;
        }
        try {
            return loadClass.getDeclaredMethod(str2, clsArr).invoke((Object) null, objArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Object invokeMethod(Object obj, String str, String str2, Class<?>[] clsArr, Object[] objArr) {
        Class<?> loadClass;
        if (obj == null || (loadClass = loadClass(str)) == null) {
            return null;
        }
        try {
            return loadClass.getDeclaredMethod(str2, clsArr).invoke(obj, objArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static Object getFieldObject(String str, Object obj, String str2) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static void setFieldObject(String str, Object obj, String str2, Object obj2) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IllegalArgumentException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }
}
