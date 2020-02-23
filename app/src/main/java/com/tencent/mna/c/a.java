package com.tencent.mna.c;

import com.tencent.mna.b.a.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.c.c.c;

/* compiled from: PlatformFactory */
public class a {
    public static f a(int i) {
        h.b("createAccelerator type:" + i);
        switch (i) {
            case 1:
                return new c().a();
            case 2:
                return new com.tencent.mna.c.e.c().a();
            case 3:
                return new com.tencent.mna.c.a.c().a();
            case 4:
                return new com.tencent.mna.c.d.c().a();
            case 5:
                return new com.tencent.mna.c.b.c().a();
            default:
                return null;
        }
    }

    public static com.tencent.mna.b.d.a b(int i) {
        switch (i) {
            case 1:
                return new c().b();
            case 2:
                return new com.tencent.mna.c.e.c().b();
            case 3:
                return new com.tencent.mna.c.a.c().b();
            case 4:
                return new com.tencent.mna.c.d.c().b();
            case 5:
                return new com.tencent.mna.c.b.c().b();
            default:
                return null;
        }
    }
}
