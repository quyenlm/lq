package com.tencent.kgvmp.report;

import com.tencent.kgvmp.a.c;
import com.tencent.kgvmp.e.g;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class d {
    private static long A = 0;
    private static String B = "";
    public static String a = "";
    public static String b = "";
    public static String c = "";
    public static String d = "";
    public static String e = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String f = "";
    public static String g = "";
    public static String h = "";
    public static String i = "";
    public static String j = "";
    public static String k = "";
    public static String l = "";
    public static String m = "";
    public static String n = "1";
    public static String o = "0";
    public static String p = "1";
    public static String q = "";
    public static HashMap<String, String> r = new HashMap<>();
    public static HashMap<String, String> s = new HashMap<>();
    public static String t = "1";
    public static String u = "1";
    public static String v = "0";
    public static String w = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String x = null;
    private static ArrayList<Integer> y = new ArrayList<>();
    private static ArrayList<Integer> z = new ArrayList<>();

    public static String a() {
        if (y.size() == 0) {
            return null;
        }
        int i2 = 0;
        Iterator<Integer> it = y.iterator();
        while (true) {
            int i3 = i2;
            if (!it.hasNext()) {
                return String.valueOf(((double) i3) / 5.0d);
            }
            i2 = it.next().intValue() + i3;
        }
    }

    public static void a(int i2, String str) {
        switch (i2) {
            case 0:
                h(str);
                return;
            case 1:
                i(str);
                return;
            case 2:
                j(str);
                return;
            case 6:
                b(str);
                return;
            case 11:
                d(str);
                return;
            case 12:
                c(str);
                return;
            case 13:
                f(str);
                return;
            case 16:
                e(str);
                return;
            default:
                s.put(String.valueOf(i2), str);
                return;
        }
    }

    public static void a(long j2) {
        A = j2;
    }

    public static void a(String str) {
        char c2 = 65535;
        switch (str.hashCode()) {
            case 54:
                if (str.equals("6")) {
                    c2 = 0;
                    break;
                }
                break;
            case 1568:
                if (str.equals("11")) {
                    c2 = 4;
                    break;
                }
                break;
            case 1569:
                if (str.equals("12")) {
                    c2 = 2;
                    break;
                }
                break;
            case 1570:
                if (str.equals("13")) {
                    c2 = 6;
                    break;
                }
                break;
            case 1573:
                if (str.equals("16")) {
                    c2 = 5;
                    break;
                }
                break;
            case 1600:
                if (str.equals("22")) {
                    c2 = 1;
                    break;
                }
                break;
            case 1601:
                if (str.equals("23")) {
                    c2 = 3;
                    break;
                }
                break;
        }
        switch (c2) {
            case 0:
                s.put(String.valueOf(str), "");
                return;
            case 1:
                s.put(String.valueOf(str), "");
                y.clear();
                return;
            case 2:
                s.put(String.valueOf(str), "");
                return;
            case 3:
                s.put(String.valueOf(str), "");
                z.clear();
                return;
            case 4:
                s.put(String.valueOf(str), t);
                return;
            case 5:
                s.put(String.valueOf(str), u);
                return;
            case 6:
                s.put(String.valueOf(str), v);
                return;
            default:
                return;
        }
    }

    public static String b() {
        int size = z.size();
        if (size == 0) {
            return null;
        }
        int i2 = 0;
        Iterator<Integer> it = z.iterator();
        while (true) {
            int i3 = i2;
            if (!it.hasNext()) {
                return String.valueOf(i3 / size);
            }
            i2 = it.next().intValue() + i3;
        }
    }

    public static void b(String str) {
        y.add(Integer.valueOf(str));
        String str2 = s.get(c.FRAME_MISS.getKeyStr());
        if (g.b(str2)) {
            l = str;
        } else {
            l = str2 + "_" + str;
        }
        s.put(c.FRAME_MISS.getKeyStr(), l);
    }

    public static long c() {
        return A;
    }

    public static void c(String str) {
        z.add(Integer.valueOf(str));
        String str2 = s.get(c.NET_LATENCY.getKeyStr());
        if (g.b(str2)) {
            m = str;
        } else {
            m = str2 + "_" + str;
        }
        s.put(c.NET_LATENCY.getKeyStr(), m);
    }

    public static String d() {
        return a;
    }

    public static void d(String str) {
        t = str;
        String str2 = s.get(c.USERS_COUNT.getKeyStr());
        if (str2 != null) {
            n = str + "_" + str2;
        } else {
            n = str;
        }
        s.put(c.USERS_COUNT.getKeyStr(), n);
    }

    public static String e() {
        return b;
    }

    public static void e(String str) {
        u = str;
        String str2 = s.get(c.ROLE_STATUS.getKeyStr());
        if (str2 != null) {
            p = str2 + "_" + str;
        } else {
            p = str;
        }
        s.put(c.ROLE_STATUS.getKeyStr(), p);
    }

    public static String f() {
        return c;
    }

    public static void f(String str) {
        v = str;
        String str2 = s.get(c.ROLE_STATUS.getKeyStr());
        if (str2 != null) {
            o = str2 + "_" + str;
        } else {
            o = str;
        }
        s.put(c.RECORDING.getKeyStr(), o);
    }

    public static String g() {
        return B;
    }

    public static void g(String str) {
        w = str;
    }

    public static String h() {
        return x;
    }

    public static void h(String str) {
        a = str;
    }

    public static void i(String str) {
        b = str;
    }

    public static void j(String str) {
        c = str;
    }

    public static void k(String str) {
        B = str;
    }

    public static void l(String str) {
        x = str;
    }
}
