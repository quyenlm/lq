package com.tencent.tp;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class g {
    public static Object a(Class cls, String str, Object obj, Class[] clsArr, Object[] objArr) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, IllegalAccessException {
        return cls.getMethod(str, clsArr).invoke(obj, objArr);
    }

    public static Object a(String str, Object obj, String str2) throws SecurityException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object a(String str, String str2) throws SecurityException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        return declaredField.get((Object) null);
    }

    public static Object a(String str, String str2, Object obj, Class[] clsArr, Object[] objArr) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, IllegalAccessException {
        return Class.forName(str).getMethod(str2, clsArr).invoke(obj, objArr);
    }

    public static Object a(String str, String str2, Class[] clsArr, Object[] objArr) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException, IllegalAccessException {
        return Class.forName(str).getMethod(str2, clsArr).invoke((Object) null, objArr);
    }

    public static void a(String str, String str2, Object obj) throws SecurityException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        declaredField.set((Object) null, obj);
    }

    public static void a(String str, String str2, Object obj, Object obj2) throws SecurityException, ClassNotFoundException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field declaredField = Class.forName(str).getDeclaredField(str2);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }
}
