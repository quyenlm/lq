package com.tencent.gsdk.utils.c;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.gsdk.api.GSDKSystem;
import com.tencent.gsdk.utils.Logger;
import com.tencent.gsdk.utils.b.f;
import com.tencent.gsdk.utils.c;
import com.tencent.gsdk.utils.j;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: SpeedTestManager */
public final class d {
    private static List<a> a = Collections.emptyList();
    private static List<a> b = Collections.emptyList();
    private static c c = new g();
    private static c d = new h();

    public static void a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            String[] split = str.split(";");
            a = new ArrayList(split.length);
            for (String aVar : split) {
                try {
                    a.add(new a(aVar));
                } catch (Exception e) {
                }
            }
            String[] split2 = str2.split(";");
            b = new ArrayList(split2.length);
            for (String aVar2 : split2) {
                try {
                    b.add(new a(aVar2));
                } catch (Exception e2) {
                }
            }
        }
    }

    public static void a() {
        HashMap hashMap = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < a.size()) {
                try {
                    a aVar = a.get(i2);
                    short b2 = c.b(aVar.a(), aVar.b, 1000);
                    Logger.d("testTcpSpeed " + aVar.a + ":" + aVar.b + ", delay:" + b2);
                    hashMap.put(APDataReportManager.GOODSANDMONTHSINPUT_PRE + (i2 + 1), aVar.a + "_" + b2);
                } catch (Exception e) {
                }
                i = i2 + 1;
            } else {
                a("gsdk_report_tcpdelay", (Map<String, String>) hashMap);
                return;
            }
        }
    }

    public static String b() {
        JSONObject jSONObject = new JSONObject();
        HashMap hashMap = new HashMap();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < b.size()) {
                try {
                    a aVar = b.get(i2);
                    int a2 = (int) a(aVar.a(), aVar.b, 1000, 3);
                    Logger.d("testUdpSpeed " + aVar.a + ":" + aVar.b + ", delay:" + a2);
                    String str = APDataReportManager.GOODSANDMONTHSINPUT_PRE + (i2 + 1);
                    String str2 = aVar.a + ":" + aVar.b + "_" + a2;
                    hashMap.put(str, str2);
                    jSONObject.put(str, str2);
                } catch (Exception e) {
                }
                i = i2 + 1;
            } else {
                a("gsdk_report_udpdelay", (Map<String, String>) hashMap);
                return jSONObject.toString();
            }
        }
    }

    public static f a(InetAddress inetAddress, int i, int i2) {
        if (!b(i)) {
            return f.b;
        }
        return new f(new h(), inetAddress, i, i2);
    }

    public static f a(InetAddress inetAddress, int i) {
        return a(inetAddress, i, 1000);
    }

    public static f a(String str, int i) {
        try {
            return a(InetAddress.getByName(str), i);
        } catch (UnknownHostException e) {
            return f.b;
        }
    }

    private static long a(InetAddress inetAddress, int i, int i2, int i3) {
        long j = 0;
        for (int i4 = 0; i4 < i3; i4++) {
            j += (long) d.b(inetAddress, i, i2);
        }
        return j / ((long) i3);
    }

    /* access modifiers changed from: private */
    public static boolean b(int i) {
        return i >= 0 && i <= 65535;
    }

    private static void a(String str, Map<String, String> map) {
        Context a2 = GSDKSystem.getInstance().a();
        map.put("signalLevel", String.valueOf(c.a()));
        map.put("xg", c.b(a2));
        map.put("gateway", String.valueOf(j.a(a2)));
        for (Map.Entry next : map.entrySet()) {
            Logger.d(str + " " + ((String) next.getKey()) + ":" + ((String) next.getValue()));
        }
        f.a(3, str, map);
    }

    /* compiled from: SpeedTestManager */
    private static final class a {
        public String a;
        public int b;
        private InetAddress c = null;

        public a(String str) {
            String[] split = str.split(":");
            if (split.length != 2) {
                throw new IllegalArgumentException();
            }
            this.a = split[0];
            this.b = Integer.parseInt(split[1]);
            if (!d.b(this.b)) {
                throw new IllegalArgumentException();
            }
        }

        public InetAddress a() {
            if (this.c == null) {
                this.c = InetAddress.getByName(this.a);
            }
            return this.c;
        }
    }
}
