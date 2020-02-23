package com.tencent.smtt.sdk;

import android.os.HandlerThread;

class al extends HandlerThread {
    private static al a;

    public al(String str) {
        super(str);
    }

    public static synchronized al a() {
        al alVar;
        synchronized (al.class) {
            if (a == null) {
                a = new al("TbsHandlerThread");
                a.start();
            }
            alVar = a;
        }
        return alVar;
    }
}
