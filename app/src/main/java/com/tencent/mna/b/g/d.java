package com.tencent.mna.b.g;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import com.tencent.mna.b;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.q;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: RouterProtocol */
public class d {
    public static long A = 0;
    public static int B = 0;
    public static int C = 0;
    public static JSONObject D = null;
    public static boolean E = false;
    public static String F = "";
    public static boolean G = false;
    @SuppressLint({"StaticFieldLeak"})
    private static Context H;
    private static a I = null;
    public static int a = 0;
    public static String b = "";
    public static int c = 0;
    public static int d = 0;
    public static int e = 10;
    public static int f = 0;
    public static int g = 0;
    public static int h = 0;
    public static int i = 0;
    public static int j = 0;
    public static int k = 0;
    public static int l = 0;
    public static int m = 0;
    public static int n = 0;
    public static int o = 0;
    public static int p = 0;
    public static int q = 0;
    public static int r = 0;
    public static int s = 2000;
    public static int t = 5000;
    public static int u = 500;
    public static int v;
    public static String w = "";
    public static String x = "";
    public static int y = -1;
    public static long z = 0;

    /* compiled from: RouterProtocol */
    public static class a {
        public int a;
        public int b = -103;
        public int c = -103;
        public int d = -103;
        public int e;
        public String f = "";
        public int g;
    }

    public static void a(int i2, Context context) {
        v = i2;
        H = context.getApplicationContext();
        w = q.l(H);
        G = true;
    }

    public static void a() {
        a = 0;
        f = 0;
        g = 0;
        h = 0;
        i = 0;
        c = 0;
        d = 0;
    }

    public static String a(String str) {
        try {
            return b.g().getSharedPreferences("gsdkrouterinfo", 0).getString(str, "");
        } catch (Exception e2) {
            return "";
        }
    }

    private static void a(String str, String str2) {
        b.g().getSharedPreferences("gsdkrouterinfo", 0).edit().putString(str, str2).apply();
    }

    public static c b() {
        b("\n> RouterProtocol 启动 获取版本信息");
        String d2 = q.d(H);
        c cVar = new c();
        if (!G) {
            b("RouterProtocol getVersion失败，init:" + G + ",rport:" + a);
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("phonemac", w);
                int i2 = 1;
                while (true) {
                    int i3 = i2 - 1;
                    if (i2 <= 0) {
                        break;
                    }
                    cVar = a(1001, 1002, jSONObject);
                    if (cVar.a == 0 && cVar.c != null) {
                        JSONObject jSONObject2 = new JSONObject(cVar.c.e);
                        x = jSONObject2.optString("routername", "");
                        y = jSONObject2.optInt("allowgamemode", -1);
                        b("RouterProtocol 获取版本信息成功：" + x);
                        a(d2, x);
                        break;
                    }
                    i2 = i3;
                }
            } catch (Exception e2) {
                h.a("RouterProtocol getVersion failed, exception:" + e2.getMessage());
                cVar.a = -10;
            }
            a(cVar);
        }
        return cVar;
    }

    public static c c() {
        b("\n> RouterProtocol 启动 认证");
        c cVar = new c();
        if (!G || a <= 0) {
            b("RouterProtocol auth失败，init:" + G + ",rport:" + a);
            cVar.a = -99;
        } else if (F == null || F.length() <= 1 || !F.equals(q.d(H))) {
            b("RouterProtocol auth失败，路由bssid不一致");
            cVar.a = -98;
        } else {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("phonemac", w);
                jSONObject.put("checksum", "");
                jSONObject.put("timestamp", System.currentTimeMillis() / 1000);
                cVar = a(1003, 1004, jSONObject);
                if (cVar.a == 0 && cVar.c != null) {
                    JSONObject jSONObject2 = new JSONObject(cVar.c.e);
                    int optInt = jSONObject2.optInt("errno", -10);
                    if (optInt == 0) {
                        z = jSONObject2.optLong("token");
                        B = jSONObject2.optInt("expiredtime");
                        b("RouterProtocol 认证成功");
                    } else {
                        cVar.a = optInt;
                    }
                }
            } catch (Exception e2) {
                h.a("RouterProtocol auth failed, exception:" + e2.getMessage());
                cVar.a = -10;
            }
            a(cVar);
        }
        return cVar;
    }

    private static c a(JSONArray jSONArray) {
        b("\n> RouterProtocol 启动 带宽qos");
        c cVar = new c();
        if (!G || a <= 0) {
            b("RouterProtocol startQos失败，init:" + G + ",rport:" + a);
            cVar.a = -99;
        } else {
            int i2 = c > 0 ? 1 : 0;
            if (i2 == 0 && o == 0 && p == 0 && q == 0 && r == 0) {
                b("RouterProtocol startQos失败，开关关闭，brandqos:" + i2 + ",glmode:" + o + ",lowloadmode:" + p + ",signalmode:" + q + ",promode:" + r);
                cVar.a = -99;
            } else if (F == null || F.length() <= 1 || !F.equals(q.d(H))) {
                b("RouterProtocol startQos失败，路由bssid不一致");
                cVar.a = -98;
            } else {
                int i3 = c;
                JSONObject jSONObject = new JSONObject();
                if (jSONArray == null) {
                    jSONArray = new JSONArray();
                }
                try {
                    jSONObject.put("phonemac", w);
                    jSONObject.put("token", z);
                    jSONObject.put("validtime", i3);
                    jSONObject.put("brandqos", i2);
                    jSONObject.put("brandlimit", m);
                    jSONObject.put("glmode", o);
                    jSONObject.put("lowloadmode", p);
                    jSONObject.put("signalmode", q);
                    jSONObject.put("signallimit", n);
                    jSONObject.put("promode", r);
                    jSONObject.put("viparray", jSONArray);
                    cVar = a(1005, 1006, jSONObject);
                    if (cVar.a == 0 && cVar.c != null) {
                        int optInt = new JSONObject(cVar.c.e).optInt("errno", -10);
                        if (optInt == 0) {
                            b("RouterProtocol 带宽qos启动成功");
                        } else {
                            cVar.a = optInt;
                        }
                    }
                } catch (Exception e2) {
                    h.a("RouterProtocol startQos failed, exception:" + e2.getMessage());
                    cVar.a = -10;
                }
                a(cVar);
            }
        }
        return cVar;
    }

    private static c b(JSONArray jSONArray) {
        b("\n> RouterProtocol 启动 双发去重");
        c cVar = new c();
        if (!G || a <= 0 || d <= 0 || (f <= 0 && j <= 0)) {
            b("RouterProtocol startDoubleSend失败，init:" + G + ",doublegap:" + f + ",rport:" + a + ",rds:" + d + ",rdoublegap:" + j);
            cVar.a = -99;
        } else if (F == null || F.length() <= 1 || !F.equals(q.d(H))) {
            b("RouterProtocol startQos失败，路由bssid不一致");
            cVar.a = -98;
        } else {
            int i2 = f;
            int i3 = g;
            int i4 = h;
            int i5 = e;
            int i6 = j;
            int i7 = k;
            int i8 = l;
            if (i6 > 0 && !E) {
                e.a(true, e);
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("token", z);
                jSONObject.put("phonemac", w);
                jSONObject.put("viparray", jSONArray);
                jSONObject.put("doublegap", i6);
                jSONObject.put("thirdgap", i7);
                jSONObject.put("fourgap", i8);
                jSONObject.put("dupgap", i5);
                cVar = a(1007, 1008, jSONObject);
                if (cVar.a == 0 && cVar.c != null) {
                    int optInt = new JSONObject(cVar.c.e).optInt("errno", -10);
                    if (optInt == 0) {
                        b("RouterProtocol 路由器启动双发去重成功，启动本地双发");
                        e.a(i2, i3, i4, i);
                    } else {
                        cVar.a = optInt;
                    }
                }
                a(cVar);
            } catch (Exception e2) {
                h.a("RouterProtocol startDoubleSend failed, exception:" + e2.getMessage());
                cVar.a = -10;
                e.a(false, i5);
                a(cVar);
            }
        }
        return cVar;
    }

    public static c d() {
        b("\n> RouterProtocol 启动 结束路由双发去重");
        c cVar = new c();
        if (!G || a <= 0) {
            b("RouterProtocol endSpeed失败，init:" + G + ",rport:" + a);
            cVar.a = -99;
        } else if (F == null || F.length() <= 1 || !F.equals(q.d(H))) {
            b("RouterProtocol endSpeed失败，路由bssid不一致");
            cVar.a = -98;
        } else {
            e.a(0, 0, 0, 0);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("phonemac", w);
                jSONObject.put("token", z);
                cVar = a(1009, 1010, jSONObject);
                if (cVar.a == 0 && cVar.c != null) {
                    int optInt = new JSONObject(cVar.c.e).optInt("errno", -10);
                    if (optInt == 0) {
                        b("RouterProtocol 结束路由双发去重成功");
                    } else {
                        cVar.a = optInt;
                    }
                }
            } catch (Exception e2) {
                h.a("RouterProtocol endSpeed failed, exception:" + e2.getMessage());
                cVar.a = -10;
            }
            e.a(false, e);
            a();
            a(cVar);
        }
        return cVar;
    }

    private static c a(short s2, short s3, JSONObject jSONObject) {
        c b2 = b(s2, s3, jSONObject);
        if (b2.a != 0) {
            return b(s2, s3, jSONObject);
        }
        return b2;
    }

    private static c b(short s2, short s3, JSONObject jSONObject) {
        c cVar = new c();
        int i2 = s;
        if (s2 == 1009) {
            i2 = u;
        } else if (s2 == 1007 || s2 == 1005) {
            i2 = t;
        } else if (s2 == 1003) {
            i2 = s;
        }
        DatagramSocket a2 = e.a(i2);
        if (a2 == null) {
            cVar.a = -1;
            return cVar;
        }
        try {
            b a3 = b.a(v, 1, s2, jSONObject.toString());
            a(a3);
            int i3 = a;
            if (s2 == 1001) {
                i3 = 17777;
            }
            if (a3 == null) {
                cVar.a = -3;
                try {
                    a2.close();
                } catch (Exception e2) {
                }
                return cVar;
            }
            DatagramPacket a4 = e.a(e(), i3, a3.a());
            if (a4 == null) {
                cVar.a = -9;
                try {
                    a2.close();
                } catch (Exception e3) {
                }
                return cVar;
            }
            a2.send(a4);
            DatagramPacket datagramPacket = new DatagramPacket(new byte[4096], 4096);
            long currentTimeMillis = System.currentTimeMillis();
            while (true) {
                if (System.currentTimeMillis() - currentTimeMillis < ((long) i2)) {
                    a2.receive(datagramPacket);
                    b a5 = b.a(datagramPacket.getData());
                    if (a5 != null && s3 == a5.c) {
                        cVar.c = a5;
                        cVar.a = 0;
                        break;
                    }
                }
            }
            try {
                a2.close();
            } catch (Exception e4) {
            }
            return cVar;
        } catch (Exception e5) {
            cVar.a = -3;
            try {
                a2.close();
            } catch (Exception e6) {
            }
            return cVar;
        } catch (Throwable th) {
            try {
                a2.close();
            } catch (Exception e7) {
            }
            throw th;
        }
    }

    private static String e() {
        WifiManager wifiManager;
        if (k.a(H) != 4 || (wifiManager = (WifiManager) H.getApplicationContext().getSystemService("wifi")) == null) {
            return "";
        }
        return f.a(wifiManager.getDhcpInfo().gateway);
    }

    private static void b(String str) {
        if (str != null) {
            h.b("[N]路由Qos " + str.replace("RouterProtocol", "").trim());
            if (I != null) {
                I.a(str);
            }
        }
    }

    private static void a(b bVar) {
        if (bVar != null) {
            h.a(bVar.toString());
            if (I != null) {
                I.a(bVar);
            }
        }
    }

    private static void a(c cVar) {
        if (cVar != null) {
            h.a(cVar.toString());
            if (I != null) {
                I.a(cVar);
            }
        }
    }

    public static void a(int i2, String str, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21) {
        a = i2;
        b = str;
        c = i3;
        f = i4;
        g = i5;
        h = i6;
        j = i7;
        k = i8;
        l = i9;
        d = i10;
        e = i11;
        i = i12;
        m = i13;
        o = i14;
        p = i15;
        q = i16;
        n = i17;
        r = i18;
        s = i19;
        t = i20;
        u = i21;
    }

    public static a a(List<String> list) {
        c b2 = b();
        a aVar = new a();
        aVar.g = a;
        aVar.a = y;
        if (b2.a == 0) {
            aVar.e = 1;
            aVar.f = x;
        } else {
            aVar.e = 0;
        }
        c c2 = c();
        aVar.b = c2.a;
        if (c2.a != 0) {
            aVar.c = -102;
            aVar.d = -102;
        } else if (list == null || list.size() <= 0) {
            h.d("RouterProtocol 解析游戏域名失败，传入列表为空");
            aVar.c = -101;
            aVar.d = -101;
        } else {
            JSONArray jSONArray = new JSONArray();
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                jSONArray.put(list.get(i2));
            }
            aVar.c = a(jSONArray).a;
            aVar.d = b(jSONArray).a;
        }
        b(String.format(com.tencent.mna.a.a.a, "错误码：%d_%d_%d", new Object[]{Integer.valueOf(aVar.b), Integer.valueOf(aVar.c), Integer.valueOf(aVar.d)}));
        return aVar;
    }
}
