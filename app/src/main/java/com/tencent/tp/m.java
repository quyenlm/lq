package com.tencent.tp;

import java.io.UnsupportedEncodingException;

public class m {
    private static ITssNativeMethod a = null;

    static {
        e();
    }

    public static void a() {
        if (a != null) {
            a.setcancelupdaterootkit();
        }
    }

    public static void a(int i) {
        if (a != null) {
            a.setrootkittipstate(i);
        }
    }

    public static void a(Object obj) {
        if (a != null) {
            a.loadConfig(obj);
        }
    }

    public static void a(String str) {
        try {
            b(str);
        } catch (UnsupportedEncodingException e) {
        }
    }

    public static int b(int i) {
        if (a != null) {
            return a.hasMatchRate(i);
        }
        return 0;
    }

    public static void b() {
        if (a != null) {
            a.forceExit();
        }
    }

    public static void b(Object obj) {
        if (a != null) {
            a.loadRootkitTipStr(obj);
        }
    }

    public static void b(String str) throws UnsupportedEncodingException {
        if (a != null) {
            a.onRuntimeInfo(str);
        }
    }

    public static int c() {
        if (a != null) {
            return a.isToastEnabled();
        }
        return 0;
    }

    public static void c(Object obj) {
        if (a != null) {
            a.loadMessageBoxInfo(obj);
        }
    }

    public static void c(String str) throws UnsupportedEncodingException {
        if (a != null) {
            a.sendStringToSvr(str);
        }
    }

    public static int d() {
        if (a != null) {
            return a.isRookitRunning();
        }
        return 0;
    }

    private static void e() {
        if (a == null) {
            try {
                Class a2 = c.a("com.tencent.tp.TssNativeMethodImp");
                if (a2 != null) {
                    a = (ITssNativeMethod) a2.newInstance();
                }
            } catch (Exception e) {
            }
            if (a == null) {
            }
        }
    }
}
