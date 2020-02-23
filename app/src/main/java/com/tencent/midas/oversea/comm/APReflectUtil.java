package com.tencent.midas.oversea.comm;

import android.app.Activity;
import java.lang.reflect.Method;

public class APReflectUtil {
    public static Object createObj(String str) {
        return Class.forName(str).newInstance();
    }

    public static Object createObj(String str, Object[] objArr) {
        return Class.forName(str).getConstructor(getParamsType(objArr)).newInstance(objArr);
    }

    public static Class<?>[] getParamsType(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            if (Activity.class.isAssignableFrom(objArr[i].getClass())) {
                clsArr[i] = Activity.class;
            } else {
                clsArr[i] = objArr[i].getClass();
            }
        }
        return clsArr;
    }

    public static Object invoke(Object obj, String str, Object[] objArr) {
        Class<?> cls = obj.getClass();
        invokeStatic(cls, str, objArr);
        Method method = cls.getMethod(str, getParamsType(objArr));
        method.setAccessible(true);
        return method.invoke(obj, objArr);
    }

    public static Object invokeStatic(Class<?> cls, String str, Object[] objArr) {
        Method method = cls.getMethod(str, getParamsType(objArr));
        method.setAccessible(true);
        return method.invoke((Class) null, objArr);
    }

    public static Object invokeStatic(Object obj, String str, Object[] objArr) {
        return invokeStatic(obj.getClass(), str, objArr);
    }

    public static Object invokeStatic(String str, String str2, Object[] objArr) {
        return invokeStatic(Class.forName(str), str2, objArr);
    }
}
