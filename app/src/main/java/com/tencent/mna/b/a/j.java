package com.tencent.mna.b.a;

import com.tencent.mna.b.c.a;
import com.tencent.mna.b.c.b;
import com.tencent.mna.base.c.a;
import com.tencent.mna.base.utils.h;
import com.tencent.smtt.sdk.TbsListener;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: VivoAccManager */
public class j {
    private static volatile b a = null;
    private static volatile boolean b = false;
    private static a c = null;

    public static synchronized void a(a aVar) {
        synchronized (j.class) {
            if (a == null) {
                a = new b();
                c = aVar;
                b = a.a((a.C0026a) new a.C0026a() {
                    public void a(String str) {
                        try {
                            j.c(str);
                        } catch (Throwable th) {
                        }
                    }
                });
                h.a("Vivo init:" + b);
            }
        }
    }

    /* access modifiers changed from: private */
    public static void c(String str) {
        String d;
        int indexOf;
        boolean z;
        boolean z2 = false;
        if (b && (d = d(str)) != null && !d.isEmpty() && (indexOf = d.indexOf(58)) >= 0 && indexOf < d.length()) {
            String substring = d.substring(0, indexOf);
            String substring2 = d.substring(indexOf + 1);
            h.a("Vivo handleRecvMsg key:" + substring + " value:" + substring2);
            if ("\"0\"".equals(substring)) {
                h.a("Vivo handleRecvMsg VivoW2mSwitch:" + com.tencent.mna.base.a.a.aV());
                if (com.tencent.mna.base.a.a.aV()) {
                    if ("\"0\"".equals(substring2)) {
                        z = true;
                    } else if ("\"1\"".equals(substring2)) {
                        z = false;
                    } else {
                        z = false;
                    }
                    int j = b.j();
                    if (j == 1) {
                        z2 = true;
                    }
                    if (z != z2) {
                        b.a(Integer.MAX_VALUE, true);
                        return;
                    }
                    if (z && j == 1) {
                        b("6", "1");
                    }
                    if (!z && j == 2) {
                        b("6", "3");
                    }
                }
            } else if ("\"2\"".equals(substring)) {
                if (c != null) {
                    c.a(a.C0030a.VIVODIAGNOSE, System.currentTimeMillis() + "_" + substring2.replace("_", "").replace(";", ","));
                }
            } else if (!"\"4\"".equals(substring)) {
            } else {
                if (b.j() == 2) {
                    b("8", "wifi");
                } else if (b.j() == 1) {
                    b("8", "4g");
                } else {
                    b("8", "idle");
                }
            }
        }
    }

    public static void a(String str, String str2) {
        if (b) {
            h.a("Vivo sendMsgWhenGameStart ip:" + str + ",port:" + str2);
            b("7", "1");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("IP", str);
                jSONObject.put("port", str2);
                jSONObject.put("protocol", "UDP");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            b("4", jSONObject.toString());
        }
    }

    public static void a(String str) {
        if (b) {
            h.a("Vivo sendMsgWhenJumpDiagnose jumpDelay:" + str);
            b("2", str);
            b("3", "1");
        }
    }

    public static void a(int i, int i2) {
        if (b) {
            h.a("Vivo sendMsgWhenNetworkSwitch bindNetRet:" + i);
            if (i == 1) {
                b("6", "1");
            } else if (i == 0) {
                b("6", "3");
            } else if (i2 == 1) {
                b("6", "2");
            } else {
                b("6", "0");
            }
        }
    }

    public static void a() {
        if (b) {
            h.a("Vivo sendMsgWhenGameEnd");
            b("7", "2");
        }
    }

    private static synchronized void b(String str, String str2) {
        JSONObject jSONObject;
        synchronized (j.class) {
            if (a != null) {
                try {
                    jSONObject = new JSONObject();
                    jSONObject.put(str, str2);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                }
                h.a("Vivo sendMsg res:" + ("send jsonMsg = [" + jSONObject.toString() + "]"));
                a.a(jSONObject.toString());
            }
        }
    }

    public static synchronized void b() {
        synchronized (j.class) {
            if (a != null) {
                h.a("Vivo close");
                a.a();
                a = null;
            }
            b = false;
            c = null;
        }
    }

    private static String d(String str) {
        if (str == null || !str.contains("{") || !str.contains("}")) {
            return "";
        }
        int indexOf = str.indexOf(TbsListener.ErrorCode.DOWNLOAD_RETRYTIMES302_EXCEED);
        int lastIndexOf = str.lastIndexOf(TbsListener.ErrorCode.DOWNLOAD_THROWABLE);
        if (indexOf < 0 || indexOf + 1 >= lastIndexOf || lastIndexOf >= str.length()) {
            return "";
        }
        return str.substring(indexOf + 1, lastIndexOf);
    }
}
