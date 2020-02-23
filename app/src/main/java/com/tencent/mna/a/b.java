package com.tencent.mna.a;

import java.util.List;
import java.util.Vector;

/* compiled from: GameSetting */
public class b {
    public static String a;
    public static boolean b = true;
    public static String c = "";
    public static String d = "";
    public static String e = "";
    public static String f = "";
    public static int g = -1;
    public static int h = -1;
    public static boolean i = false;
    private static String j = "0.0.0.0";
    private static int k = 0;
    private static List<String> l = new Vector();
    private static String m = "0.0.0.0";

    public static String a() {
        return j;
    }

    public static int b() {
        return k;
    }

    public static List<String> c() {
        return l;
    }

    public static void a(String str) {
        j = str;
    }

    public static void a(int i2) {
        k = i2;
    }

    public static void a(List<String> list) {
        l = list;
    }

    public static String d() {
        return m;
    }

    public static void b(String str) {
        m = str;
    }

    public static void e() {
        m = "0.0.0.0";
    }
}
