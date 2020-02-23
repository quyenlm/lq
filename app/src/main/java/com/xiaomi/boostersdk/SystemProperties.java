package com.xiaomi.boostersdk;

public class SystemProperties {
    public static String get(String str) {
        return get(str, "");
    }

    public static String get(String str, String str2) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class, String.class}).invoke((Object) null, new Object[]{str, str2});
        } catch (Exception e) {
            e.printStackTrace();
            return str2;
        }
    }
}
