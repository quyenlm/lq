package com.subao.common;

import android.util.Log;

/* compiled from: Logger */
public class d {
    private static b a = new a();

    /* compiled from: Logger */
    public interface b {
        boolean a(String str, int i);
    }

    public static void a(String str, int i, String str2) {
        if (str2 != null && a(str, i)) {
            Log.println(i, str, str2);
        }
    }

    public static boolean a(String str, int i) {
        return a.a(str, i);
    }

    public static boolean a(String str) {
        return a(str, 3);
    }

    public static void a(String str, String str2) {
        a(str, 3, str2);
    }

    public static void b(String str, String str2) {
        a(str, 5, str2);
    }

    public static void c(String str, String str2) {
        a(str, 6, str2);
    }

    /* compiled from: Logger */
    private static class a implements b {
        private a() {
        }

        public boolean a(String str, int i) {
            return com.subao.common.f.b.b() || Log.isLoggable(str, i);
        }
    }
}
