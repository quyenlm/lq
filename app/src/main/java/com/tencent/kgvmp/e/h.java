package com.tencent.kgvmp.e;

import com.amazonaws.services.s3.internal.Constants;
import com.tencent.kgvmp.a.a;
import java.lang.reflect.Method;

public class h {
    private static final String a = a.a;
    private static Class b;
    private static Method c;

    public static String a(String str) {
        try {
            if (b == null) {
                b = Class.forName("android.os.SystemProperties");
            }
            if (c == null) {
                c = b.getMethod("get", new Class[]{String.class, String.class});
            }
            return (String) c.invoke((Object) null, new Object[]{str, Constants.NULL_VERSION_ID});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
