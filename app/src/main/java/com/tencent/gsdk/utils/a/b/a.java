package com.tencent.gsdk.utils.a.b;

import android.text.TextUtils;

/* compiled from: ConfigUrlManager */
public final class a {
    private static d a;
    private static String b = "NULL";

    public static void a(int i, String str) {
        a = b.a(i);
        if (!TextUtils.isEmpty(str)) {
            b = str;
        }
    }

    public static String a() {
        if (a == null) {
            return "";
        }
        return a.a(b);
    }

    public static String b() {
        if (a == null) {
            return "";
        }
        return a.b(b);
    }

    public static String c() {
        if (a == null) {
            return "";
        }
        return a.c(b);
    }
}
