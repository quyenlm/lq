package com.tencent.kgvmp.report;

import com.beetalk.sdk.update.GPGameProviderContract;
import com.tencent.kgvmp.a.a;
import com.tencent.kgvmp.e.b;
import com.tencent.kgvmp.e.c;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class f {
    public static boolean a = false;
    public static boolean b = false;
    public static boolean c = false;
    public static boolean d = false;
    public static boolean e = false;
    public static String f = "";
    private static final String g = a.a;
    private static boolean h = false;
    private static String i = "";
    private static long j = 0;
    private static long k = c.d();
    private static long l = c.e();

    public static void a(String str) {
        f = str;
    }

    public static void a(String str, String str2) {
        String a2 = b.a(b.a.PATTERN2.getFormat());
        HashMap hashMap = new HashMap();
        hashMap.put("start_time", String.valueOf(d.c()));
        hashMap.put("end_time", b.a());
        hashMap.put("vmp_number", h());
        hashMap.put("scene_id", str2);
        hashMap.put("next_scene", str);
        hashMap.put("open", String.valueOf(b));
        hashMap.put("support", String.valueOf(e));
        hashMap.put("report_time", a2);
        hashMap.put("open_id", d.d());
        hashMap.put("sdk_type", com.tencent.kgvmp.socket.a.e.getName());
        b.a(a.VMP_SCENETIME, hashMap);
    }

    public static void a(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put(GPGameProviderContract.Column.VERSION_CODE, String.valueOf(3));
        hashMap.put("version_name", "1.0.3_72");
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        hashMap.put("cpu", String.valueOf(c.h()));
        hashMap.put("gpu", String.valueOf(d.h()));
        b.a(a.VMP_INIT, hashMap);
    }

    public static void a(boolean z) {
        a = z;
    }

    public static boolean a() {
        return a;
    }

    public static void b(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put(GPGameProviderContract.Column.VERSION_CODE, String.valueOf(3));
        hashMap.put("version_name", "1.0.3_72");
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.a(a.VMP_DEVICE_CHECK, hashMap);
    }

    public static void b(boolean z) {
        b = z;
    }

    public static boolean b() {
        return b;
    }

    public static void c(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put(GPGameProviderContract.Column.VERSION_CODE, String.valueOf(3));
        hashMap.put("version_name", "1.0.3_72");
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.a(a.VMP_EXCEPTION, hashMap);
    }

    public static void c(boolean z) {
        c = z;
    }

    public static boolean c() {
        return c;
    }

    public static void d(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.a(a.VMP_DOWNLOAD_CONFIG, hashMap);
    }

    public static void d(boolean z) {
        d = z;
    }

    public static boolean d() {
        return d;
    }

    public static void e(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.a(a.VMP_REGCALL, hashMap);
    }

    public static void e(boolean z) {
        e = z;
    }

    public static boolean e() {
        return h;
    }

    public static String f() {
        return f;
    }

    public static void f(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.a(a.VMP_CALLBACK, hashMap);
    }

    public static void g() {
        i = b.a(b.a.PATTERN1.getFormat()) + "_" + String.valueOf(b.a());
        j = System.currentTimeMillis();
    }

    public static void g(HashMap<String, String> hashMap) {
        hashMap.put("open_id", d.d());
        hashMap.put("vmp_number", h());
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put("init_time", String.valueOf(j / 1000));
        hashMap.put("open", String.valueOf(b));
        hashMap.put("support", String.valueOf(e));
        hashMap.put("sdk_type", com.tencent.kgvmp.socket.a.e.getName());
        hashMap.put(com.tencent.kgvmp.a.c.MAIN_VERCODE.getKeyStr(), String.valueOf(d.e()));
        hashMap.put(com.tencent.kgvmp.a.c.SUB_VERCODE.getKeyStr(), String.valueOf(d.f()));
        for (Map.Entry next : hashMap.entrySet()) {
            com.tencent.kgvmp.e.f.a(g, "VmpReport:reportSocketInfo: key: " + ((String) next.getKey()) + "   value: " + ((String) next.getValue()));
        }
        b.b(a.VMP_SOCKETINFO, hashMap);
    }

    public static String h() {
        return i;
    }

    public static void h(HashMap<String, String> hashMap) {
        hashMap.put("report_time", b.a(b.a.PATTERN2.getFormat()));
        hashMap.put("vmp_number", h());
        hashMap.put("open_id", d.d());
        b.b(a.VMP_HANDLE_EXCEPTION, hashMap);
    }

    public static String i() {
        return String.valueOf((System.currentTimeMillis() - j) / 1000);
    }

    public static String j() {
        if (c.c() > 25) {
            com.tencent.kgvmp.e.f.a(g, "getCPURate: sdk > 25, can not get cpu rate.");
            return "";
        }
        long d2 = c.d();
        long e2 = c.e();
        String format = String.format(Locale.CHINA, "%.2f", new Object[]{Double.valueOf(c.a(d2, e2, k, l))});
        l = e2;
        k = d2;
        return format;
    }

    public static String k() {
        return String.valueOf(c.f());
    }
}
