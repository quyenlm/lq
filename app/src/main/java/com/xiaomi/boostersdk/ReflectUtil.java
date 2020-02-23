package com.xiaomi.boostersdk;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    public static <T> T callObjectMethod(Object obj, Class<T> cls, String str, Class<?>[] clsArr, Object... objArr) {
        Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }

    public static Object callObjectMethod(Object obj, String str, Class<?> cls, Class<?>[] clsArr, Object... objArr) {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }

    public static Object callObjectMethod(Object obj, String str, Class<?>[] clsArr, Object... objArr) {
        Method declaredMethod = obj.getClass().getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke(obj, objArr);
    }

    public static <T> T callStaticObjectMethod(Class<?> cls, Class<T> cls2, String str, Class<?>[] clsArr, Object... objArr) {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke((Object) null, objArr);
    }

    public static Object callStaticObjectMethod(Class<?> cls, String str, Class<?>[] clsArr, Object... objArr) {
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        declaredMethod.setAccessible(true);
        return declaredMethod.invoke((Object) null, objArr);
    }

    public static Object getObjectField(Object obj, String str) {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static <T> T getObjectField(Object obj, String str, Class<T> cls) {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get(obj);
    }

    public static Object getStaticObjectField(Class<?> cls, String str) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get((Object) null);
    }

    public static <T> T getStaticObjectField(Class<?> cls, String str, Class<T> cls2) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        return declaredField.get((Object) null);
    }

    public static void setObjectField(Object obj, String str, Object obj2) {
        Field declaredField = obj.getClass().getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set(obj, obj2);
    }

    public static void setStaticObjectField(Class<?> cls, String str, Object obj) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        declaredField.set((Object) null, obj);
    }
}
