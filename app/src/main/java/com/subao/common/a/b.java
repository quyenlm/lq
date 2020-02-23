package com.subao.common.a;

/* compiled from: AccelEngineInstance */
public class b {
    private static a a;

    public static synchronized a a(a aVar) {
        a aVar2;
        synchronized (b.class) {
            aVar2 = a;
            a = aVar;
        }
        return aVar2;
    }

    public static synchronized a a() {
        a aVar;
        synchronized (b.class) {
            aVar = a;
        }
        return aVar;
    }
}
