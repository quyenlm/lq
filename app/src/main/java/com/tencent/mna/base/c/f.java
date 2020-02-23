package com.tencent.mna.base.c;

/* compiled from: ReporterFactory */
public class f {
    public static d a(c cVar) {
        if (cVar.getType() == c.NORMAL.getType()) {
            return new a(cVar);
        }
        return new g(cVar);
    }
}
