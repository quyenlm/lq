package com.subao.common.i;

import android.util.Log;

/* compiled from: MessageEvent */
public class d {

    /* compiled from: MessageEvent */
    public interface b {
        void a(String str, String str2);
    }

    /* compiled from: MessageEvent */
    public static class a {
        private static boolean a;
        private static boolean b;
        private static boolean c;
        private static boolean d;

        public static void a(boolean z, boolean z2, boolean z3, boolean z4) {
            a = z;
            b = z2;
            c = z3;
            d = z4;
            if (com.subao.common.d.a("SubaoMessage")) {
                Log.d("SubaoMessage", String.format("ReportAllow set: tg=%b, auth=%b, missedLink=%b, wifiAccelSwitch=%b", new Object[]{Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4)}));
            }
        }

        public static boolean a() {
            return a;
        }

        public static boolean b() {
            return d;
        }
    }
}
