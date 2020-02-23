package com.tencent.kgvmp.report;

import com.tencent.beacon.event.UserAction;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.e.f;
import java.util.Map;

public class b {
    public static boolean a = false;
    private static final String b = a.a;

    public static String a() {
        if (!a) {
            return null;
        }
        try {
            return UserAction.getQIMEI();
        } catch (Throwable th) {
            f.a(b, "beacon: get qimei exception.");
            return null;
        }
    }

    public static void a(a aVar, Map<String, String> map) {
        if (a) {
            UserAction.onUserAction(aVar.getValue(), true, -1, -1, map, true, true);
        } else {
            f.a(b, "beacon: not available.");
        }
    }

    public static void b(a aVar, Map<String, String> map) {
        if (a) {
            UserAction.onUserAction(aVar.getValue(), true, -1, -1, map, false, false);
        } else {
            f.a(b, "beacon: not available.");
        }
    }

    public static boolean b() {
        try {
            UserAction.getQIMEI();
            a = true;
            return true;
        } catch (Throwable th) {
            f.a(b, "beacon: can not found tencent beacon.");
            return false;
        }
    }
}
