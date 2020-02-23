package com.tencent.msdk.a.a;

import android.content.Context;

public class c {
    private static Context a = null;
    private static volatile c b = null;

    private c(Context context) {
        a = context.getApplicationContext();
    }

    public static c a(Context context) {
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    b = new c(context);
                }
            }
        }
        return b;
    }

    public String a() {
        return h.a(a).a().c;
    }
}
