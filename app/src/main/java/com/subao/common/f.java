package com.subao.common;

/* compiled from: ProxyEngineCommunicator */
public interface f {

    /* compiled from: ProxyEngineCommunicator */
    public static class a {
        private static f a;

        public static synchronized void a(f fVar) {
            synchronized (a.class) {
                a = fVar;
            }
        }
    }
}
