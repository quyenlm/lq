package com.tencent.mna.base.a;

import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.jni.entity.CloudRet;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import java.util.List;

/* compiled from: CloudHelper */
class b {
    private static String a = "0.0.0.0";
    private static String b = "0.0.0.0";

    /* compiled from: CloudHelper */
    public enum a {
        Def_Acc,
        Def_Dgn,
        Def_Xml
    }

    static CloudRet a(a aVar, String str) {
        return a(aVar, str, 0, 0);
    }

    static CloudRet a(a aVar, String str, int i, int i2) {
        String str2;
        int i3 = 1;
        switch (aVar) {
            case Def_Dgn:
                i3 = 2;
                break;
            case Def_Xml:
                i3 = 3;
                break;
        }
        if (i2 != 0) {
            String a2 = f.a(com.tencent.mna.a.a.f, i2);
            if (a2 != null && a2.length() > 0) {
                b = a2;
            }
            str2 = b;
        } else {
            String f = f.f(com.tencent.mna.a.a.f);
            if (f != null && f.length() > 0) {
                a = f;
            }
            str2 = a;
        }
        boolean c = f.c(str2);
        h.a("request to " + str2 + ", useIpv6:" + c + ", netId:" + i2);
        return e.a(c, i3, str2, com.tencent.mna.a.a.g, str, i);
    }

    static String a() {
        return a;
    }

    static List<String> b() {
        return f.e(com.tencent.mna.a.a.f);
    }
}
