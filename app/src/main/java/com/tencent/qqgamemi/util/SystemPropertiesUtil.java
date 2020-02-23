package com.tencent.qqgamemi.util;

import java.lang.reflect.InvocationTargetException;

public class SystemPropertiesUtil {
    public static String get(String prop, String defaultValue) {
        String value = defaultValue;
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class, String.class}).invoke((Object) null, new Object[]{prop, defaultValue});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return value;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return value;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return value;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return value;
        } catch (Exception e5) {
            e5.printStackTrace();
            return value;
        }
    }

    public static String get(String prop) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class}).invoke((Object) null, new Object[]{prop});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        } catch (Exception e5) {
            e5.printStackTrace();
            return null;
        }
    }
}
