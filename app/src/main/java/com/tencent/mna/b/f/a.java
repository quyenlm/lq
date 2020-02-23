package com.tencent.mna.b.f;

import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.b;
import com.tencent.mna.b.a.d;
import com.tencent.mna.b.f.d;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QosHelper */
public class a {
    public static int a = 0;
    public static int b = -8;
    public static int c = 2;
    public static String d = "-2";
    public static String e = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
    public static String f = "0";
    /* access modifiers changed from: private */
    public static String g;
    /* access modifiers changed from: private */
    public static String h;
    /* access modifiers changed from: private */
    public static String i = "http://qos.game.qq.com:8080/qos/register/?version=100";
    /* access modifiers changed from: private */
    public static String j;
    /* access modifiers changed from: private */
    public static d.c k = null;
    private static d.C0029d l = null;
    /* access modifiers changed from: private */
    public static d.e m = null;
    /* access modifiers changed from: private */
    public static volatile boolean n = false;
    /* access modifiers changed from: private */
    public static volatile boolean o = false;
    private static boolean p = true;
    private static c q = null;

    public static void a(String str, String str2) {
        g = str;
        h = str2;
    }

    public static void a(String str) {
        j = str;
    }

    public static synchronized void a() {
        synchronized (a.class) {
            c = 2;
            d = "-2";
            e = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
            f = "0";
            a = 0;
            k = null;
            l = null;
            m = null;
            p = true;
            b = -8;
        }
    }

    public static synchronized void a(final int i2, final int i3, final List<String> list, final d.b bVar) {
        synchronized (a.class) {
            if (!n) {
                if (p) {
                    if (m != null && ((long) m.e) > (System.currentTimeMillis() - m.f) / 1000) {
                        c("session存在且没有超时，不再重复请求保障");
                    } else if (k.a(b.g()) != 3) {
                        c("当前网络不为4G，不满足保障条件");
                        a = -1;
                        c = 0;
                        p = false;
                    } else {
                        if (k != null) {
                            if (k.c != 1) {
                                a = -3;
                            } else if (b(k, i2, i3)) {
                                a = -2;
                            } else if (k.l > 0) {
                                d.c cVar = k;
                                cVar.l--;
                            } else {
                                c("发起保障次数大于阈值，不再请求保障");
                                p = false;
                            }
                        }
                        new Thread(new Runnable() {
                            public void run() {
                                if (!a.n) {
                                    try {
                                        boolean unused = a.n = true;
                                        if (a.k != null || a.b(i2, bVar.getFlag(), i3)) {
                                            if (a.k.h == 1) {
                                                a.d = a.k.k;
                                            } else {
                                                a.d = "0";
                                            }
                                            if (a.b(a.k, i2, i3)) {
                                                a.c = 0;
                                                a.a = -2;
                                                a.c("不满足保障条件 curDelay[" + i2 + "]小于ensureAdj1[" + a.k.i + "], 或者大于ensureAdj2[" + a.k.j + "]");
                                                boolean unused2 = a.n = false;
                                                return;
                                            }
                                            if (a.k.e.length() > 0) {
                                                boolean unused3 = a.l();
                                            }
                                            a.a((List<String>) list, bVar.isInPvp());
                                            h.b("[N]4G-QOS错误码：" + a.a);
                                            boolean unused4 = a.n = false;
                                        }
                                    } catch (Throwable th) {
                                    } finally {
                                        boolean unused5 = a.n = false;
                                    }
                                }
                            }
                        }).start();
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static boolean b(d.c cVar, int i2, int i3) {
        boolean z = true;
        b = i2;
        if (cVar.h != 1 || i3 < 4) {
            return false;
        }
        if (cVar.j <= 0 || cVar.j <= cVar.i) {
            if (i2 >= cVar.i) {
                z = false;
            }
            return z;
        } else if (i2 < cVar.i || i2 > cVar.j) {
            return true;
        } else {
            return false;
        }
    }

    public static void b() {
        new Thread(new Runnable() {
            public void run() {
                if (!a.o) {
                    try {
                        boolean unused = a.o = true;
                        a.c("取消QOS保障...");
                        if (a.m == null || a.m.a != 0) {
                            a.c("启动保障不成功或已取消保障，无需再次取消QOS保障");
                            h.b("[N]4G QOS取消保障: 启动保障不成功或已取消保障，无需再次取消QOS保障");
                            boolean unused2 = a.o = false;
                            return;
                        }
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("appid", a.g);
                        jSONObject.put("sdkver", a.h);
                        jSONObject.put("type", 3);
                        jSONObject.put(UnityPayHelper.SESSIONID, a.m.d);
                        jSONObject.put("openid", a.j);
                        a.c("请求结果：" + b.a(a.i, jSONObject.toString()));
                        d.e unused3 = a.m = null;
                        boolean unused4 = a.o = false;
                    } catch (JSONException e) {
                        h.a("doUnQos json exception");
                    } catch (Throwable th) {
                        try {
                            d.e unused5 = a.m = null;
                        } finally {
                            boolean unused6 = a.o = false;
                        }
                    }
                }
            }
        }).start();
    }

    /* access modifiers changed from: private */
    public static boolean b(int i2, int i3, int i4) {
        c("\n开始请求URL...");
        String a2 = b.a(i, new d.a(g, h, 1, j, i2, i3, i4).a());
        c("请求结果：" + a2);
        try {
            k = d.c.a(new JSONObject(a2));
            if (k.a != 0) {
                c("请求URL失败，errno: " + k.a + ", errmsg: " + k.b);
                a = -4;
                return false;
            } else if (k.c == 1) {
                return true;
            } else {
                c("请求URL失败，原因：不满足保障条件，isQos=" + k.c);
                a = -3;
                c = 0;
                d = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
                return false;
            }
        } catch (Exception e2) {
            c("请求结果解析为JSON格式失败：" + a2);
            a = -5;
            return false;
        }
    }

    /* access modifiers changed from: private */
    public static boolean l() {
        c("\n请求手机号码...");
        if (k == null) {
            c("\n请求手机号码失败，无req1返回值...");
            return false;
        }
        String str = k.e;
        if (str == null || str.length() == 0) {
            c("请求手机号码失败，url为空...");
            return false;
        }
        String a2 = b.a(str);
        c("请求结果G：" + a2);
        try {
            l = d.C0029d.a(new JSONObject(a2));
            if (l.c.length() != 0) {
                c("请求手机号码成功, result:" + l.c);
            } else {
                c("请求手机号码失败, error: " + l.a + ", errmsg: " + l.b);
            }
        } catch (JSONException e2) {
            c("请求结果解析为JSON格式失败：" + a2);
        }
        return true;
    }

    static boolean a(List<String> list, boolean z) {
        String a2;
        int i2 = 2;
        c("\n启动QOS保障...");
        StringBuilder sb = new StringBuilder();
        for (String next : list) {
            if (sb.length() > 0) {
                sb.append(',').append(next);
            } else {
                sb.append(next);
            }
        }
        h.b("[N]4G QOS vip: " + sb.toString());
        String str = l == null ? "" : l.c;
        if (l == null || l.d == null || l.d.length() <= 0 || !b.b(l.d)) {
            a2 = b.a();
        } else {
            a2 = l.d;
        }
        if (!f.a(a2)) {
            c("获取本地ip失败");
            a = -6;
            return false;
        }
        f = a2;
        String a3 = b.a(i, new d.b(g, h, 2, a2, j, str, sb.toString()).a());
        c("请求结果P：" + a3);
        try {
            m = d.e.a(new JSONObject(a3));
            if (m.a == 0) {
                c = 1;
                if (z) {
                    i2 = 3;
                }
                a = i2;
                e = m.d;
                c("启动QOS保障成功");
                return true;
            }
            c("启动QOS保障失败 errno:" + m.a + ", errmsg" + m.b);
            a = -7;
            return false;
        } catch (JSONException e2) {
            a = -8;
            c("请求结果解析为JSON格式失败：" + a3);
        }
    }

    /* access modifiers changed from: private */
    public static void c(String str) {
        h.a("4G QOS:" + str);
        if (q != null) {
            q.a(str);
        }
    }
}
