package com.tencent.gsdk.api;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.gsdk.utils.JNIHelper;
import com.tencent.gsdk.utils.Logger;
import com.tencent.gsdk.utils.i;
import com.tencent.gsdk.utils.j;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.imsdk.sns.base.IMFriendInfoExViber;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class GSDKSystem {
    private static long A;
    private static long B;
    private static int C;
    private static int D;
    private static int E;
    /* access modifiers changed from: private */
    public static int F;
    private static int G;
    /* access modifiers changed from: private */
    public static HashMap<String, b> H;
    private static HashMap<String, i> I;
    private static boolean L = false;
    @SuppressLint({"StaticFieldLeak"})
    private static GSDKSystem M;
    public static List<Long> a;
    public static List<Long> b;
    public static List<Long> c;
    public static List<Long> d;
    public static List<Long> e;
    public static List<Long> f;
    public static List<Long> g;
    public static List<Long> h;
    public static List<Long> i;
    public static List<Long> j;
    public static List<Long> k;
    public static List<Long> l;
    public static List<Long> m;
    public static List<Long> n;
    /* access modifiers changed from: private */
    @SuppressLint({"StaticFieldLeak"})
    public static Context o;
    private static String p;
    private static int q;
    private static String s = "1.1.12";
    /* access modifiers changed from: private */
    public static boolean t = true;
    private static boolean u = true;
    /* access modifiers changed from: private */
    public static boolean v = true;
    private static boolean w = false;
    private static boolean x = false;
    private static long y;
    private static long z;
    private Application J;
    private f K;
    private i.a N;
    /* access modifiers changed from: private */
    public b O;
    private int P;
    private Activity Q;
    /* access modifiers changed from: private */
    public d R;
    private String S;
    private ScheduledExecutorService T = null;
    private int U;
    private volatile boolean V = false;
    private d W;
    private com.tencent.gsdk.utils.c.f X = com.tencent.gsdk.utils.c.f.a;
    private IEventObserver Y;
    private String Z = "";
    /* access modifiers changed from: private */
    public Handler r;

    public static GSDKSystem getInstance() {
        if (M == null) {
            synchronized (GSDKSystem.class) {
                if (M == null) {
                    M = new GSDKSystem();
                }
            }
        }
        return M;
    }

    public void a(Context context, String str) {
        if (context != null) {
            com.tencent.gsdk.utils.b.f.a(1, context.getApplicationContext(), str);
        }
    }

    public void a(Context context, String str, boolean z2, int i2, int i3) {
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gsdk_prefs", 0);
            int i4 = sharedPreferences.getInt("create_time", -1);
            if (i4 == -1) {
                i4 = (int) (System.currentTimeMillis() / 1000);
                sharedPreferences.edit().putInt("create_time", i4).apply();
            }
            this.U = i4;
            p = com.tencent.gsdk.utils.e.a();
            Logger.isDebug = z2;
            if (!L) {
                HandlerThread handlerThread = new HandlerThread("THREAD__100");
                handlerThread.start();
                this.r = new c(handlerThread.getLooper());
                H = new HashMap<>();
                I = new HashMap<>();
                o = context.getApplicationContext();
                this.J = ((Activity) context).getApplication();
                com.tencent.gsdk.utils.c.g(o);
                a.a(o);
                this.P = com.tencent.gsdk.utils.d.a();
                if (this.P == 2) {
                    this.Q = (Activity) context;
                }
                Logger.d("engine plat is:" + this.P);
                com.tencent.gsdk.utils.a.b.a.a(i2, str);
                com.tencent.gsdk.utils.b.f.a(i3);
                com.tencent.gsdk.utils.f.a(this.U, p);
                com.tencent.gsdk.utils.g.a(o);
                L = true;
            }
            if (this.J != null && Build.VERSION.SDK_INT >= 14) {
                if (this.K != null) {
                    m();
                }
                this.K = new f();
                this.J.registerActivityLifecycleCallbacks(this.K);
            }
            b();
            a(context, z2);
        }
    }

    public void a(final Context context, boolean z2) {
        if (context != null && z2) {
            try {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("GSDK已打开Debug模式，现网版本请务必关闭，否则将影响客户端的性能。即将Init接口中Debug参数设为false！");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.create().show();
                    }
                });
            } catch (Exception e2) {
                Logger.e("Debug alert dialog error:" + e2.getMessage());
            }
        }
    }

    public void a(int i2, String str) {
        q = i2;
        f.a(o, "gsdk_prefs", "open_id", str);
    }

    public void a(int i2, String str, String str2) {
        q = i2;
        if (str2 != null && str2.length() != 0) {
            f.a(o, "gsdk_prefs", "open_id", str);
            b(str2);
        }
    }

    public Context a() {
        return o;
    }

    class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        GSDKSystem.this.d(((e) message.obj).a);
                        return;
                    case 1:
                        GSDKSystem.this.a(GSDKSystem.this.O, (c) message.obj);
                        return;
                    case 2:
                        GSDKSystem.this.d((b) message.obj);
                        return;
                    case 3:
                        GSDKSystem.this.c((b) message.obj);
                        return;
                    case 4:
                        GSDKSystem.this.e((String) message.obj);
                        return;
                    case 5:
                        GSDKSystem.this.d();
                        return;
                    case 6:
                        GSDKSystem.this.a((d) message.obj);
                        return;
                    case 7:
                        GSDKSystem.this.a((a) message.obj);
                        return;
                    case 8:
                        GSDKSystem.this.a((HashMap<String, b>) GSDKSystem.H, 1);
                        return;
                    case 9:
                        GSDKSystem.this.n();
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
                Logger.e("MainHandler error:" + th.getMessage());
                th.printStackTrace();
            }
            Logger.e("MainHandler error:" + th.getMessage());
            th.printStackTrace();
        }
    }

    public void a(String str) {
        if (this.r != null) {
            e eVar = new e(str);
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = eVar;
            this.r.sendMessage(obtain);
        }
    }

    public void a(c cVar) {
        c cVar2;
        if (cVar == null) {
            cVar2 = new c(0.0f, 0, 0, 0, 0, 0, 0, 0, 0, 0, "0");
        } else {
            cVar2 = new c(cVar.a, cVar.b, cVar.c, cVar.d, cVar.e, cVar.f, cVar.g, cVar.h, cVar.i, cVar.j, cVar.k);
        }
        if (this.r != null) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = cVar2;
            this.r.sendMessage(obtain);
        }
    }

    public void a(b bVar) {
        if (this.r != null) {
            Message obtain = Message.obtain();
            obtain.what = 2;
            obtain.obj = bVar;
            this.r.sendMessage(obtain);
        }
    }

    public void b(b bVar) {
        if (this.r != null) {
            Message obtain = Message.obtain();
            obtain.what = 3;
            obtain.obj = bVar;
            this.r.sendMessage(obtain);
        }
    }

    public void b(String str) {
        if (this.r != null) {
            Message obtain = Message.obtain();
            obtain.what = 4;
            obtain.obj = str;
            this.r.sendMessage(obtain);
        }
    }

    public void b() {
        Message obtain = Message.obtain();
        obtain.what = 5;
        if (this.r != null) {
            this.r.sendMessage(obtain);
        }
    }

    public void a(int i2, boolean z2, String str, boolean z3, boolean z4) {
        d dVar = new d(i2, z2, str, z3, z4);
        Message obtain = Message.obtain();
        obtain.what = 6;
        obtain.obj = dVar;
        if (this.r != null) {
            this.r.sendMessage(obtain);
        }
    }

    public void a(int i2, int i3, boolean z2, String str) {
        a aVar = new a(i2, i3, z2, str);
        Message obtain = Message.obtain();
        obtain.what = 7;
        obtain.obj = aVar;
        if (this.r != null) {
            this.r.sendMessage(obtain);
        }
    }

    public void c() {
        if (!x) {
            Message obtain = Message.obtain();
            obtain.what = 9;
            if (this.r != null) {
                this.r.sendMessage(obtain);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(HashMap<String, b> hashMap, int i2) {
        if (hashMap != null) {
            HashMap hashMap2 = new HashMap();
            for (String str : hashMap.keySet()) {
                b bVar = hashMap.get(str);
                hashMap2.put("s" + bVar.a, bVar.b);
                hashMap2.put("t" + bVar.a, bVar.c + "");
            }
            hashMap2.put("isBack", i2 + "");
            if (hashMap != null) {
                hashMap.clear();
            }
            hashMap2.put("version", s);
            hashMap2.put("devices", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap2.put("wifi_num", String.valueOf(com.tencent.gsdk.utils.c.f(o)));
            hashMap2.put("wifi_rssi", String.valueOf(com.tencent.gsdk.utils.c.d(o)));
            hashMap2.put("gate_delay", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap2.put("wifi_speed", String.valueOf(com.tencent.gsdk.utils.c.e(o)));
            hashMap2.put("free_storage", String.valueOf(g.f(o)));
            hashMap2.put("total_storage", String.valueOf(g.e(o)));
            hashMap2.put("signal_level", String.valueOf(com.tencent.gsdk.utils.c.a()));
            Logger.w("login signal_level level is " + String.valueOf(com.tencent.gsdk.utils.c.a()));
            hashMap2.put("xg", com.tencent.gsdk.utils.c.b(o));
            hashMap2.put("uuid", p);
            hashMap2.put("totalmem", String.valueOf(g.a(o)));
            hashMap2.put("ldns", j.b());
            a("gsdk_report_login", System.currentTimeMillis(), true, (Map<String, String>) hashMap2);
        }
    }

    public void d() {
        boolean z2;
        boolean z3 = false;
        String b2 = com.tencent.gsdk.utils.a.b.a.b();
        Logger.d("switch url is:" + b2);
        try {
            HttpURLConnection a2 = com.tencent.gsdk.utils.a.a.a.a(b2);
            a2.connect();
            if (a2.getResponseCode() == 200) {
                InputStream inputStream = a2.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.close();
                inputStream.close();
                String str = new String(byteArrayOutputStream.toByteArray());
                if (str != null) {
                    String b3 = com.tencent.gsdk.utils.a.b(str);
                    Logger.d("login switch result is:" + b3);
                    JSONObject jSONObject = new JSONObject(b3);
                    if (jSONObject.optInt("errno", -1) == 0) {
                        int optInt = jSONObject.optInt(FirebaseAnalytics.Event.LOGIN, -1);
                        int optInt2 = jSONObject.optInt("pay", -1);
                        if (optInt != 1) {
                            t = false;
                        }
                        if (optInt2 != 1) {
                            u = false;
                        }
                        f.a(o, "gsdk_prefs", FirebaseAnalytics.Event.LOGIN, Boolean.valueOf(t));
                        f.a(o, "gsdk_prefs", "pay", Boolean.valueOf(u));
                        f.a(o, "gsdk_prefs", "domain1", jSONObject.optString("domain1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain2", jSONObject.optString("domain2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain3", jSONObject.optString("domain3", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain4", jSONObject.optString("domain4", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain5", jSONObject.optString("domain5", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain6", jSONObject.optString("domain6", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "errmsg", jSONObject.optString("errmsg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR));
                        f.a(o, "gsdk_prefs", "domain", jSONObject.optInt("domain", 0));
                        f.a(o, "gsdk_prefs", "errno", jSONObject.optInt("errno", -1));
                        f.a(o, "gsdk_prefs", "port1", jSONObject.optInt("port1", -1));
                        f.a(o, "gsdk_prefs", "port2", jSONObject.optInt("port2", -1));
                        f.a(o, "gsdk_prefs", "port3", jSONObject.optInt("port3", -1));
                        f.a(o, "gsdk_prefs", "port4", jSONObject.optInt("port4", -1));
                        f.a(o, "gsdk_prefs", "port5", jSONObject.optInt("port5", -1));
                        f.a(o, "gsdk_prefs", "port6", jSONObject.optInt("port6", -1));
                        f.a(o, "gsdk_prefs", "udp", jSONObject.optString("udp"));
                        f.a(o, "gsdk_prefs", "tcp", jSONObject.optString("tcp"));
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                } else {
                    z2 = false;
                }
            } else {
                z2 = false;
            }
            a2.disconnect();
            z3 = z2;
        } catch (Exception e2) {
            Logger.e("request login switch error:" + e2.getMessage());
        }
        com.tencent.gsdk.utils.c.d.a(f.b(o, "gsdk_prefs", "tcp"), f.b(o, "gsdk_prefs", "udp"));
        if (!z3) {
            t = f.a(o, "gsdk_prefs", FirebaseAnalytics.Event.LOGIN);
            u = f.a(o, "gsdk_prefs", "pay");
            Logger.d("login switch is:" + t + " pay switch is:" + u);
        }
        if (f.c(o, "gsdk_prefs", "domain") != 0) {
            HashMap hashMap = new HashMap();
            long currentTimeMillis = System.currentTimeMillis();
            hashMap.put("uuid", p);
            String a3 = a(f.b(o, "gsdk_prefs", "domain1"), f.c(o, "gsdk_prefs", "port1"));
            String a4 = a(f.b(o, "gsdk_prefs", "domain2"), f.c(o, "gsdk_prefs", "port2"));
            String a5 = a(f.b(o, "gsdk_prefs", "domain3"), f.c(o, "gsdk_prefs", "port3"));
            String a6 = a(f.b(o, "gsdk_prefs", "domain4"), f.c(o, "gsdk_prefs", "port4"));
            String a7 = a(f.b(o, "gsdk_prefs", "domain5"), f.c(o, "gsdk_prefs", "port5"));
            String a8 = a(f.b(o, "gsdk_prefs", "domain6"), f.c(o, "gsdk_prefs", "port6"));
            hashMap.put("d1", a3);
            hashMap.put("d2", a4);
            hashMap.put("d3", a5);
            hashMap.put("d4", a6);
            hashMap.put("d5", a7);
            hashMap.put("d6", a8);
            if (!z3) {
                hashMap.put("isCache", "1");
            } else {
                hashMap.put("isCache", "0");
            }
            a("gsdk_report_con", currentTimeMillis, true, (Map<String, String>) hashMap);
        }
    }

    private String a(String str, int i2) {
        String str2 = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        long j2 = -1;
        boolean z2 = false;
        if (str == null || (str != null && str.length() == 0)) {
            if (i2 == 0) {
                i2 = -1;
            }
            return "-1_-1_-1_-1_" + i2 + "_-1";
        }
        Socket socket = new Socket();
        String g2 = g(str);
        try {
            long currentTimeMillis = System.currentTimeMillis();
            str2 = InetAddress.getByName(str).getHostAddress();
            j2 = System.currentTimeMillis() - currentTimeMillis;
            socket.connect(new InetSocketAddress(str, i2), 3000);
            if (!socket.isClosed() && socket.isConnected()) {
                z2 = true;
            }
            socket.close();
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e2) {
                    Logger.w("getSocketConnectivity close socket error:" + e2.getMessage());
                }
            }
        } catch (Exception e3) {
            Logger.w("getSocketConnectivity error:" + e3.getMessage());
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e4) {
                    Logger.w("getSocketConnectivity close socket error:" + e4.getMessage());
                }
            }
        } catch (Throwable th) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e5) {
                    Logger.w("getSocketConnectivity close socket error:" + e5.getMessage());
                }
            }
            throw th;
        }
        return str + "_" + j2 + "_" + str2 + "_" + g2 + "_" + i2 + "_" + z2;
    }

    /* access modifiers changed from: private */
    public void a(d dVar) {
        this.R = dVar;
        if (this.R != null && t && H != null && !w) {
            long j2 = -1;
            long currentTimeMillis = System.currentTimeMillis();
            Logger.i("setEvent tag:" + this.R.a + ",status:" + this.R.b + ",msg:" + this.R.c + ",authorize:" + this.R.d + ",finish:" + this.R.e);
            String str = this.R.b + "_" + this.R.c;
            if (A != 0) {
                j2 = currentTimeMillis - A;
            }
            H.put(String.valueOf(this.R.a), new b(this.R.a, str, j2));
            A = currentTimeMillis;
            if (!this.R.b || this.R.e) {
                if (this.R.e) {
                    m();
                }
                a(H, 0);
            }
            if (!this.R.e || !this.R.b) {
                v = false;
                return;
            }
            v = true;
            w = true;
        }
    }

    private void m() {
        try {
            if (this.J != null && Build.VERSION.SDK_INT >= 14 && this.K != null) {
                this.J.unregisterActivityLifecycleCallbacks(this.K);
                this.K = null;
            }
        } catch (Exception e2) {
            Logger.w("unregisterLifecycleCallbacks error:" + e2.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void a(a aVar) {
        e eVar;
        if (aVar != null && u && I != null) {
            long currentTimeMillis = System.currentTimeMillis();
            try {
                Logger.i("setPay id:" + aVar.a + ",tag:" + aVar.b + ",status:" + aVar.c + ",msg:" + aVar.d);
                eVar = e.a(aVar.b);
            } catch (Exception e2) {
                Logger.e("gsdk setPay enum error:" + e2.getMessage());
                eVar = null;
            }
            if (eVar != e.PayStart) {
                I.put(String.valueOf(aVar.b), new i(aVar.b, aVar.a + "_" + aVar.c + "_" + aVar.d, currentTimeMillis - B));
            }
            B = currentTimeMillis;
            if (!aVar.c || eVar == e.PayDone) {
                b(aVar);
            }
        }
    }

    /* access modifiers changed from: private */
    public void n() {
        if (!x) {
            x = true;
            com.tencent.gsdk.utils.c.d.a();
            x = false;
        }
    }

    private void b(a aVar) {
        if (I != null) {
            try {
                HashMap hashMap = new HashMap();
                if (aVar.b == 1) {
                    hashMap.put("s1", I.get("1").b);
                    hashMap.put("t1", String.valueOf(I.get("1").c));
                    hashMap.put("s2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    hashMap.put("t2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                } else if (aVar.b == 2) {
                    if (I.containsKey("1")) {
                        hashMap.put("s1", I.get("1").b);
                        hashMap.put("t1", String.valueOf(I.get("1").c));
                    } else {
                        hashMap.put("s1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                        hashMap.put("t1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    }
                    hashMap.put("s2", I.get("2").b);
                    hashMap.put("t2", String.valueOf(I.get("2").c));
                } else {
                    if (I.containsKey("1")) {
                        hashMap.put("s1", I.get("1").b);
                        hashMap.put("t1", String.valueOf(I.get("1").c));
                    } else {
                        hashMap.put("s1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                        hashMap.put("t1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    }
                    if (I.containsKey("2")) {
                        hashMap.put("s2", I.get("2").b);
                        hashMap.put("t2", String.valueOf(I.get("2").c));
                    } else {
                        hashMap.put("s2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                        hashMap.put("t2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    }
                }
                for (String next : I.keySet()) {
                    if (!next.equals("1") && !next.equals("2")) {
                        hashMap.put("s" + next, I.get(next).b);
                        hashMap.put("t" + next, String.valueOf(I.get(next).c));
                    }
                }
                a("gsdk_report_pay", System.currentTimeMillis(), true, (Map<String, String>) hashMap);
                if (I != null) {
                    I.clear();
                }
            } catch (Exception e2) {
            }
        }
    }

    public String e() {
        try {
            return this.X.toString();
        } catch (Exception e2) {
            return "{\"cping\":\"-1\",\"clostpkg\":\"-1\",\"avg_ping\":\"-1\",\"lostpkgpct\":\"-1\"}";
        }
    }

    public int f() {
        return this.X.a();
    }

    /* access modifiers changed from: private */
    public void d(String str) {
        long j2;
        boolean z2;
        if (this.V) {
            o();
            this.V = false;
        }
        y = System.currentTimeMillis();
        a = new ArrayList();
        b = new ArrayList();
        c = new ArrayList();
        e = new ArrayList();
        d = new ArrayList();
        g = new ArrayList();
        f = new ArrayList();
        h = new ArrayList();
        i = new ArrayList();
        j = new ArrayList();
        k = new ArrayList();
        l = new ArrayList();
        m = new ArrayList();
        n = new ArrayList();
        this.S = str;
        String c2 = c(str);
        boolean z3 = false;
        if (c2 == null || c2.length() == 0) {
            if (f.c(o, "gsdk_prefs", "c_errno") == 0) {
                z3 = true;
            }
            Logger.e("request controller reponse is null");
        } else {
            try {
                JSONObject jSONObject = new JSONObject(c2);
                int optInt = jSONObject.optInt("errno", -1);
                if (optInt == 0) {
                    String optString = jSONObject.optString("errmsg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    String optString2 = jSONObject.optString("sip", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                    int optInt2 = jSONObject.optInt("sport", -1);
                    int optInt3 = jSONObject.optInt("fps", -1);
                    int optInt4 = jSONObject.optInt("ping", -1);
                    int optInt5 = jSONObject.optInt("cpu", -1);
                    int optInt6 = jSONObject.optInt("mem", -1);
                    int optInt7 = jSONObject.optInt("battery", -1);
                    int optInt8 = jSONObject.optInt("netflow", -1);
                    int optInt9 = jSONObject.optInt("frequency", -1);
                    int optInt10 = jSONObject.optInt("fcntx0", -1);
                    int optInt11 = jSONObject.optInt("pcntx00", -1);
                    int optInt12 = jSONObject.optInt("lfps1", -1);
                    int optInt13 = jSONObject.optInt("lfps2", -1);
                    int optInt14 = jSONObject.optInt("lfps3", -1);
                    int optInt15 = jSONObject.optInt("wifi", -1);
                    int optInt16 = jSONObject.optInt("fps_cycle", -1);
                    int optInt17 = jSONObject.optInt("cpu_cycle", -1);
                    int optInt18 = jSONObject.optInt("signal_cycle", -1);
                    this.O = new b(optInt, optString, optString2, optInt2, optInt3, optInt4, optInt5, optInt6, optInt7, optInt8, optInt9, optInt10, optInt11, optInt12, optInt13, optInt14, optInt15, optInt16, optInt17, optInt18);
                    f.a(o, "gsdk_prefs", "c_errno", optInt);
                    f.a(o, "gsdk_prefs", "c_errmsg", optString);
                    f.a(o, "gsdk_prefs", "sip", optString2);
                    f.a(o, "gsdk_prefs", "sport", optInt2);
                    f.a(o, "gsdk_prefs", "fps", optInt3);
                    f.a(o, "gsdk_prefs", "ping", optInt4);
                    f.a(o, "gsdk_prefs", "cpu", optInt5);
                    f.a(o, "gsdk_prefs", "mem", optInt6);
                    f.a(o, "gsdk_prefs", "battery", optInt7);
                    f.a(o, "gsdk_prefs", "netflow", optInt8);
                    f.a(o, "gsdk_prefs", "frequency", optInt9);
                    f.a(o, "gsdk_prefs", "fcntx0", optInt10);
                    f.a(o, "gsdk_prefs", "pcntx00", optInt11);
                    f.a(o, "gsdk_prefs", "0", optInt12);
                    f.a(o, "gsdk_prefs", "0", optInt13);
                    f.a(o, "gsdk_prefs", "0", optInt14);
                    f.a(o, "gsdk_prefs", "wifi", optInt15);
                    f.a(o, "gsdk_prefs", "fps_cycle", optInt16);
                    f.a(o, "gsdk_prefs", "fps_cycle", optInt17);
                    f.a(o, "gsdk_prefs", "fps_cycle", optInt18);
                } else {
                    f.a(o, "gsdk_prefs", "c_errno", optInt);
                    Logger.e("request controller error, errno is " + optInt);
                }
            } catch (JSONException e2) {
                JSONException jSONException = e2;
                if (f.c(o, "gsdk_prefs", "c_errno") == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                Logger.e("request controller JSONException, msg:" + jSONException.getMessage());
                z3 = z2;
            }
        }
        if (z3) {
            this.O = new b(f.c(o, "gsdk_prefs", "c_errno"), f.b(o, "gsdk_prefs", "c_errmsg"), f.b(o, "gsdk_prefs", "sip"), f.c(o, "gsdk_prefs", "sport"), f.c(o, "gsdk_prefs", "fps"), f.c(o, "gsdk_prefs", "ping"), f.c(o, "gsdk_prefs", "cpu"), f.c(o, "gsdk_prefs", "mem"), f.c(o, "gsdk_prefs", "battery"), f.c(o, "gsdk_prefs", "netflow"), f.c(o, "gsdk_prefs", "frequency"), f.c(o, "gsdk_prefs", "fcntx0"), f.c(o, "gsdk_prefs", "pcntx00"), f.c(o, "gsdk_prefs", "0"), f.c(o, "gsdk_prefs", "0"), f.c(o, "gsdk_prefs", "0"), f.c(o, "gsdk_prefs", "wifi"), f.c(o, "gsdk_prefs", "fps_cycle"), f.c(o, "gsdk_prefs", "fps_cycle"), f.c(o, "gsdk_prefs", "fps_cycle"));
        }
        a(c2, z3);
        if (this.O != null && this.O.a == 0) {
            this.T = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
                private AtomicInteger b = new AtomicInteger(0);

                public Thread newThread(Runnable runnable) {
                    return new Thread(runnable, "gem-scheduled-" + this.b.getAndIncrement());
                }
            });
            a(this.O);
            b(this.O);
            if (this.O.i > 0) {
                C = a.a();
                this.W = new d(o);
                this.T.scheduleAtFixedRate(this.W, 0, (long) this.O.u, TimeUnit.MILLISECONDS);
            }
            long j3 = (long) this.O.k;
            if (j3 > 0 && this.O.f > 0) {
                this.X = com.tencent.gsdk.utils.c.d.a(this.O.c, this.O.d);
                if (com.tencent.gsdk.utils.c.f.a(this.X)) {
                    this.T.scheduleAtFixedRate(this.X, 0, j3, TimeUnit.MILLISECONDS);
                }
            }
            long j4 = (long) this.O.s;
            if (j4 > 0) {
                this.T.scheduleAtFixedRate(new g(this.O), 0, j4, TimeUnit.MILLISECONDS);
            }
            int c3 = com.tencent.gsdk.utils.c.c(o);
            if (this.O.q > 0 && c3 == 4) {
                D = -1;
                E = com.tencent.gsdk.utils.c.f(o);
                try {
                    j2 = (long) j.a(o);
                } catch (Exception e3) {
                    j2 = -1;
                }
                if (k != null) {
                    k.add(Long.valueOf(j2));
                }
            }
            long j5 = (long) this.O.t;
            if (j5 > 0) {
                this.T.scheduleWithFixedDelay(new h(this.O, c3), 0, j5, TimeUnit.MILLISECONDS);
            }
        }
        this.N = com.tencent.gsdk.utils.i.a();
        this.V = true;
    }

    /* access modifiers changed from: private */
    public void c(b bVar) {
        if (bVar != null) {
            final int i2 = bVar.e;
            final int i3 = bVar.a;
            final int i4 = bVar.l;
            final int i5 = bVar.n;
            final int i6 = bVar.o;
            final int i7 = bVar.p;
            final int i8 = bVar.r;
            final String str = bVar.b;
            if (this.P == 1) {
                try {
                    Method method = Class.forName("com.unity3d.player.UnityPlayer").getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class});
                    if (i2 > 0) {
                        method.invoke((Object) null, new Object[]{"GSDKCallBackGameObejct", "GSDKStartCallback", "" + i3 + "|" + str + "|" + i4 + "|" + i5 + "|" + i6 + "|" + i7 + "|" + i8});
                        return;
                    }
                    method.invoke((Object) null, new Object[]{"GSDKCallBackGameObejct", "GSDKStartCallback", "" + i3 + "|" + str + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0});
                } catch (Exception e2) {
                    Logger.w("UnityPlayer startFPS error:" + e2.getMessage());
                }
            } else if (this.P == 2) {
                if (this.Q != null) {
                    try {
                        Class<?> cls = Class.forName("org.cocos2dx.lib.Cocos2dxActivity");
                        if (cls != null) {
                            Method method2 = cls.getMethod("runOnGLThread", new Class[]{Runnable.class});
                            AnonymousClass3 r0 = new Runnable() {
                                public void run() {
                                    if (i2 > 0) {
                                        JNIHelper.SendInfo(i3 + "|" + str + "|" + i4 + "|" + i5 + "|" + i6 + "|" + i7 + "|" + i8);
                                    } else {
                                        JNIHelper.SendInfo(i3 + "|" + str + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0);
                                    }
                                }
                            };
                            method2.invoke(this.Q, new Object[]{r0});
                        }
                    } catch (Exception e3) {
                        Logger.w("Cocos2dxActivity startFPS error:" + e3.getMessage());
                    }
                }
            } else if (this.P != 3) {
                Logger.d("startFPS other engine");
                if (i2 > 0) {
                    try {
                        JNIHelper.SendInfo(i3 + "|" + str + "|" + i4 + "|" + i5 + "|" + i6 + "|" + i7 + "|" + i8);
                    } catch (Throwable th) {
                        Logger.w("startFPS failed", th);
                    }
                } else {
                    JNIHelper.SendInfo(i3 + "|" + str + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0);
                }
            } else if (i2 > 0) {
                try {
                    JNIHelper.SendInfo(i3 + "|" + str + "|" + i4 + "|" + i5 + "|" + i6 + "|" + i7 + "|" + i8);
                } catch (Exception e4) {
                    Logger.w("GameActivity startFPS error:" + e4.toString());
                }
            } else {
                JNIHelper.SendInfo(i3 + "|" + str + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0 + "|" + 0);
            }
        }
    }

    /* access modifiers changed from: private */
    public void e(String str) {
        String f2 = f(str);
        Logger.i("request quality controller result is:" + f2);
        try {
            JSONObject jSONObject = new JSONObject(f2);
            int optInt = jSONObject.optInt("errno", -1);
            int optInt2 = jSONObject.optInt("switch", -1);
            if (optInt == 0 && optInt2 == 1) {
                final String optString = jSONObject.optString("suggest_quality", "-1_-1_-1_-1_-1_-1");
                Logger.i("request quality controller suggestQuality is:" + optString);
                if (this.P == 1) {
                    try {
                        Class.forName("com.unity3d.player.UnityPlayer").getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class}).invoke((Object) null, new Object[]{"GSDKCallBackGameObejct", "GSDKQualityCallback", optString});
                    } catch (Exception e2) {
                        Logger.w("UnityPlayer startQuality error:" + e2.getMessage());
                    }
                } else if (this.P == 2) {
                    if (this.Q != null) {
                        try {
                            Class<?> cls = Class.forName("org.cocos2dx.lib.Cocos2dxActivity");
                            if (cls != null) {
                                Method method = cls.getMethod("runOnGLThread", new Class[]{Runnable.class});
                                AnonymousClass4 r2 = new Runnable() {
                                    public void run() {
                                        JNIHelper.SendQualityInfo(optString);
                                    }
                                };
                                method.invoke(this.Q, new Object[]{r2});
                            }
                        } catch (Exception e3) {
                            Logger.w("Cocos2dxActivity startQuality error:" + e3.getMessage());
                        }
                    }
                } else if (this.P == 3) {
                    try {
                        JNIHelper.SendQualityInfo(optString);
                    } catch (Exception e4) {
                        Logger.w("GameActivity startQuality error:" + e4.toString());
                    }
                } else {
                    Logger.d("startQuality other engine");
                    try {
                        JNIHelper.SendQualityInfo(optString);
                    } catch (Throwable th) {
                        Logger.w("startQuality failed", th);
                    }
                }
            }
        } catch (JSONException e5) {
        }
    }

    /* access modifiers changed from: private */
    public void d(b bVar) {
        if (bVar.j > 0) {
            z = g.k();
        }
    }

    private void o() {
        A = 0;
        if (this.T != null) {
            this.T.shutdownNow();
            this.T = null;
        }
        if (this.W != null) {
            this.W = null;
        }
        if (com.tencent.gsdk.utils.c.f.a != this.X) {
            this.X = com.tencent.gsdk.utils.c.f.a;
        }
        if (a != null) {
            a.clear();
        }
        if (b != null) {
            b.clear();
        }
        if (c != null) {
            c.clear();
        }
        if (e != null) {
            e.clear();
        }
        if (d != null) {
            d.clear();
        }
        if (g != null) {
            g.clear();
        }
        if (f != null) {
            f.clear();
        }
        if (h != null) {
            h.clear();
        }
        if (i != null) {
            i.clear();
        }
        if (j != null) {
            j.clear();
        }
        if (k != null) {
            k.clear();
        }
        if (l != null) {
            l.clear();
        }
        if (m != null) {
            m.clear();
        }
        if (n != null) {
            n.clear();
        }
        this.O = null;
        a.a(0);
    }

    public void a(b bVar, c cVar) {
        int i2;
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        if (this.V && bVar != null && f.c(o, "gsdk_prefs", "c_errno") == 0) {
            x = false;
            if (this.T != null) {
                this.T.shutdownNow();
                this.T = null;
            }
            i.a a2 = com.tencent.gsdk.utils.i.a(com.tencent.gsdk.utils.i.a(), this.N);
            long currentTimeMillis = System.currentTimeMillis();
            if (d != null) {
                i2 = d.size();
            } else {
                i2 = 0;
            }
            String str = GSDKPlatform.mTag;
            HashMap hashMap = new HashMap();
            hashMap.put("version", s);
            hashMap.put("zoneid", this.S);
            hashMap.put("wsndpkt", String.valueOf(a2.h));
            hashMap.put("wrcvpkt", String.valueOf(a2.d));
            hashMap.put("wsnddrop", String.valueOf(a2.j));
            hashMap.put("wrcvdrop", String.valueOf(a2.f));
            hashMap.put("wsnderr", String.valueOf(a2.i));
            hashMap.put("wrcverr", String.valueOf(a2.e));
            hashMap.put("msndpkt", String.valueOf(a2.p));
            hashMap.put("mrcvpkt", String.valueOf(a2.l));
            hashMap.put("msnddrop", String.valueOf(a2.r));
            hashMap.put("mrcvdrop", String.valueOf(a2.n));
            hashMap.put("msnderr", String.valueOf(a2.q));
            hashMap.put("mrcverr", String.valueOf(a2.m));
            if (bVar.a != 0 || bVar.e <= 0) {
                hashMap.put("fmin", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("fmax", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("favg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("fheavy", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("flight", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("ftotal", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("fcntx0", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("lfps1", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("lfps2", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("lfps3", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                hashMap.put("fmin", String.valueOf(cVar.c));
                hashMap.put("fmax", String.valueOf(cVar.b));
                hashMap.put("favg", String.valueOf(cVar.a));
                hashMap.put("fheavy", String.valueOf(cVar.e));
                hashMap.put("flight", String.valueOf(cVar.f));
                hashMap.put("ftotal", String.valueOf(cVar.d));
                hashMap.put("fcntx0", String.valueOf(cVar.g));
                hashMap.put("lfps1", String.valueOf(cVar.h));
                hashMap.put("lfps2", String.valueOf(cVar.i));
                hashMap.put("lfps3", String.valueOf(cVar.j));
            }
            hashMap.put(IMFriendInfoExViber.TAG, str);
            String valueOf = String.valueOf(currentTimeMillis / 1000);
            String valueOf2 = String.valueOf((currentTimeMillis - y) / 1000);
            hashMap.put("etime", valueOf);
            hashMap.put("time", valueOf2);
            a(bVar, (HashMap<String, String>) hashMap);
            if (bVar.a != 0 || bVar.h <= 0) {
                hashMap.put("mem", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("availmem", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("max_mem ", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("start_mem", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("end_mem", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                double d2 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                double d3 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                long j7 = -1;
                long j8 = -1;
                long j9 = -1;
                if (d != null) {
                    for (Long next : d) {
                        if (next.longValue() > j7) {
                            j7 = next.longValue();
                        }
                    }
                    if (d.size() >= 1) {
                        j8 = d.get(0).longValue();
                        j9 = d.get(d.size() - 1).longValue();
                    }
                    int i3 = 0;
                    while (true) {
                        int i4 = i3;
                        if (i4 >= d.size()) {
                            break;
                        }
                        d2 += (double) d.get(i4).longValue();
                        i3 = i4 + 1;
                    }
                }
                if (e != null) {
                    int i5 = 0;
                    while (true) {
                        int i6 = i5;
                        if (i6 >= e.size()) {
                            break;
                        }
                        d3 += (double) e.get(i6).longValue();
                        i5 = i6 + 1;
                    }
                }
                double d4 = d3;
                long j10 = 0;
                if (i2 != 0) {
                    long round = Math.round(d2 / ((double) i2));
                    j6 = Math.round(d4 / ((double) i2));
                    j10 = round;
                } else {
                    j6 = 0;
                }
                hashMap.put("mem", String.valueOf(j10));
                hashMap.put("availmem", String.valueOf(j6));
                hashMap.put("max_mem ", String.valueOf(j7));
                hashMap.put("start_mem", String.valueOf(j8));
                hashMap.put("end_mem", String.valueOf(j9));
            }
            if (bVar.a != 0 || bVar.g <= 0) {
                hashMap.put("cpu", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("totcpu", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("cpu_temp_max", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("cpu_temp_avg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("gpu_temp_avg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("gpu_temp_max", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("gpu", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                double d5 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                double d6 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
                if (f != null) {
                    int i7 = 0;
                    while (true) {
                        int i8 = i7;
                        if (i8 >= f.size()) {
                            break;
                        }
                        d5 += (double) f.get(i8).longValue();
                        i7 = i8 + 1;
                    }
                }
                if (g != null) {
                    int i9 = 0;
                    while (true) {
                        int i10 = i9;
                        if (i10 >= g.size()) {
                            break;
                        }
                        d6 += (double) g.get(i10).longValue();
                        i9 = i10 + 1;
                    }
                }
                double d7 = d6;
                long j11 = 0;
                if (i2 != 0) {
                    long round2 = Math.round(d5 / ((double) i2));
                    j2 = Math.round(d7 / ((double) i2));
                    j11 = round2;
                } else {
                    j2 = 0;
                }
                long j12 = 0;
                long j13 = -1;
                if (h == null || h.size() == 0) {
                    j3 = -1;
                } else {
                    long longValue = h.get(0).longValue();
                    j13 = longValue;
                    for (Long next2 : h) {
                        if (next2.longValue() > j13) {
                            j13 = next2.longValue();
                        }
                        j12 = next2.longValue() + j12;
                    }
                    j3 = j12 / ((long) h.size());
                }
                long j14 = 0;
                long j15 = -1;
                if (i == null || i.size() == 0) {
                    j4 = -1;
                } else {
                    long longValue2 = i.get(0).longValue();
                    j15 = longValue2;
                    for (Long next3 : i) {
                        if (next3.longValue() > j15) {
                            j15 = next3.longValue();
                        }
                        j14 = next3.longValue() + j14;
                    }
                    j4 = j14 / ((long) i.size());
                }
                long j16 = -1;
                if (!(j == null || j.size() == 0)) {
                    long j17 = 0;
                    Iterator<Long> it = j.iterator();
                    while (true) {
                        j5 = j17;
                        if (!it.hasNext()) {
                            break;
                        }
                        j17 = it.next().longValue() + j5;
                    }
                    j16 = j5 / ((long) j.size());
                }
                hashMap.put("cpu", String.valueOf(a(j11)));
                hashMap.put("totcpu", String.valueOf(a(j2)));
                hashMap.put("cpu_temp_max", String.valueOf(j13));
                hashMap.put("cpu_temp_avg", String.valueOf(j3));
                hashMap.put("gpu_temp_avg", String.valueOf(j4));
                hashMap.put("gpu_temp_max", String.valueOf(j15));
                hashMap.put("gpu", String.valueOf(j16));
            }
            if (bVar.a != 0 || bVar.i <= 0) {
                hashMap.put("battery", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("start_battery", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("end_battery", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("bt", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("bs", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("max_battery_temp", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                int i11 = -1;
                int a3 = a.a();
                if (!(C == -1 || a3 == -1)) {
                    i11 = C - a3;
                }
                hashMap.put("battery", String.valueOf(i11));
                hashMap.put("start_battery", String.valueOf(C));
                hashMap.put("end_battery", String.valueOf(a3));
                hashMap.put("bt", String.valueOf(g.d(o)));
                hashMap.put("bs", String.valueOf(a.b()));
                if (this.W != null) {
                    hashMap.put("max_battery_temp", String.valueOf(this.W.b));
                    this.W = null;
                } else {
                    hashMap.put("max_battery_temp", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                }
            }
            if (bVar.a != 0 || bVar.j <= 0) {
                hashMap.put("netflow", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                hashMap.put("netflow", String.valueOf((g.k() - z) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID));
            }
            if (bVar.a != 0 || bVar.q <= 0) {
                hashMap.put("devices", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("wifi_num", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("wifi_rssi", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("wifi_speed", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                hashMap.put("gate_delay", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                if (!(k == null || k.size() == 0)) {
                    int i12 = 0;
                    int i13 = 0;
                    while (true) {
                        int i14 = i13;
                        if (i14 >= k.size()) {
                            break;
                        }
                        i12 = (int) (((long) i12) + k.get(i14).longValue());
                        i13 = i14 + 1;
                    }
                    G = i12 / k.size();
                }
                hashMap.put("devices", String.valueOf(D));
                hashMap.put("wifi_num", String.valueOf(E));
                hashMap.put("wifi_rssi", String.valueOf(F));
                if (n == null || n.size() == 0) {
                    hashMap.put("wifi_speed", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
                } else {
                    long j18 = 0;
                    int i15 = 0;
                    while (true) {
                        int i16 = i15;
                        if (i16 >= n.size()) {
                            break;
                        }
                        j18 += n.get(i16).longValue();
                        i15 = i16 + 1;
                    }
                    hashMap.put("wifi_speed", String.valueOf(j18 / ((long) n.size())));
                }
                hashMap.put("gate_delay", String.valueOf(G));
            }
            if (l == null || l.size() == 0) {
                hashMap.put("signal_level", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            } else {
                int i17 = 0;
                int i18 = 0;
                while (true) {
                    int i19 = i18;
                    if (i19 >= l.size()) {
                        break;
                    }
                    i17 = (int) (((long) i17) + l.get(i19).longValue());
                    i18 = i19 + 1;
                }
                hashMap.put("signal_level", String.valueOf(i17 / l.size()));
            }
            hashMap.put("xg", com.tencent.gsdk.utils.c.b(o));
            String str2 = GSDKPlatform.mRoomIp;
            if (str2 != null) {
                hashMap.put("roomip", str2);
            } else {
                hashMap.put("roomip", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            }
            hashMap.put("gpu_model", com.tencent.gsdk.utils.g.e);
            hashMap.put("cpu_model", com.tencent.gsdk.utils.g.c);
            hashMap.put("cpu_core", com.tencent.gsdk.utils.g.d);
            hashMap.put("cpu_freq", com.tencent.gsdk.utils.g.b);
            hashMap.put("manufacturer", Build.MANUFACTURER);
            hashMap.put("brand", Build.BRAND);
            hashMap.put("model", Build.MODEL);
            hashMap.put(HttpRequestParams.RESOLUTION, com.tencent.gsdk.utils.g.a);
            long f2 = g.f(a());
            hashMap.put("total_storage", com.tencent.gsdk.utils.g.g);
            hashMap.put("free_storage", String.valueOf(f2));
            hashMap.put("totalmem", com.tencent.gsdk.utils.g.f);
            hashMap.put("ldns", j.b());
            a("gsdk_report_1", y, true, (Map<String, String>) hashMap);
            a(bVar, str, valueOf2, valueOf, cVar);
            o();
            this.V = false;
        }
    }

    private void a(b bVar, HashMap<String, String> hashMap) {
        if (bVar.a != 0 || bVar.f <= 0 || !com.tencent.gsdk.utils.c.f.a(this.X)) {
            hashMap.put("pmin", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("pmax", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("pavg", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("pheavy", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("plost", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("ptotal", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            hashMap.put("pcntx00", UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
            return;
        }
        long j2 = 0;
        long j3 = 0;
        List<Short> f2 = this.X.f();
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 < f2.size() - 1) {
                long shortValue = (long) (f2.get(i3 + 1).shortValue() - f2.get(i3).shortValue());
                if (shortValue >= 100) {
                    j2++;
                }
                if (shortValue >= ((long) bVar.m)) {
                    j3++;
                }
                i2 = i3 + 1;
            } else {
                hashMap.put("pmin", String.valueOf(Collections.min(this.X.f())));
                hashMap.put("pmax", String.valueOf(Collections.max(this.X.f())));
                hashMap.put("pavg", String.valueOf(this.X.c()));
                hashMap.put("pheavy", String.valueOf(j2));
                hashMap.put("plost", String.valueOf(this.X.e().size() - this.X.f().size()));
                hashMap.put("ptotal", String.valueOf(this.X.e().size()));
                hashMap.put("pcntx00", String.valueOf(j3));
                return;
            }
        }
    }

    private int a(long j2) {
        if (j2 < 0) {
            j2 = 0;
        } else if (j2 > 10000) {
            j2 = 10000;
        }
        return (int) j2;
    }

    private void a(b bVar, String str, String str2, String str3, c cVar) {
        String[] strArr;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        try {
            if (bVar.g == 2) {
                HashMap hashMap = new HashMap();
                hashMap.put(IMFriendInfoExViber.TAG, str);
                hashMap.put("time", str2);
                hashMap.put("etime", str3);
                String str4 = "";
                if (f == null || f.size() == 0) {
                    hashMap.put(HttpRequestParams.NOTICE_LIST, str4);
                } else {
                    int i10 = 0;
                    Iterator<Long> it = f.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            i9 = i10;
                            break;
                        }
                        long longValue = it.next().longValue();
                        if (str4.length() + String.valueOf(longValue).length() > 1024) {
                            hashMap.put(HttpRequestParams.NOTICE_LIST + i10, str4);
                            str4 = String.valueOf(longValue) + ",";
                            i9 = i10 + 1;
                        } else {
                            str4 = str4 + String.valueOf(longValue) + ",";
                            i9 = i10;
                        }
                        if (i9 > 45) {
                            break;
                        }
                        i10 = i9;
                    }
                    if (str4.length() > 0 && i9 <= 45) {
                        hashMap.put(HttpRequestParams.NOTICE_LIST + i9, str4);
                    }
                }
                hashMap.put("listname", "cpu_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap);
                HashMap hashMap2 = new HashMap();
                hashMap2.put(IMFriendInfoExViber.TAG, str);
                hashMap2.put("time", str2);
                hashMap2.put("etime", str3);
                String str5 = "";
                if (h == null || h.size() == 0) {
                    hashMap2.put(HttpRequestParams.NOTICE_LIST, str5);
                } else {
                    int i11 = 0;
                    Iterator<Long> it2 = h.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            i8 = i11;
                            break;
                        }
                        long longValue2 = it2.next().longValue();
                        if (str5.length() + String.valueOf(longValue2).length() > 1024) {
                            hashMap2.put(HttpRequestParams.NOTICE_LIST + i11, str5);
                            str5 = String.valueOf(longValue2) + ",";
                            i8 = i11 + 1;
                        } else {
                            str5 = str5 + String.valueOf(longValue2) + ",";
                            i8 = i11;
                        }
                        if (i8 > 45) {
                            break;
                        }
                        i11 = i8;
                    }
                    if (str5.length() > 0 && i8 <= 45) {
                        hashMap2.put(HttpRequestParams.NOTICE_LIST + i8, str5);
                    }
                }
                hashMap2.put("listname", "cpu_temp_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap2);
                HashMap hashMap3 = new HashMap();
                hashMap3.put(IMFriendInfoExViber.TAG, str);
                hashMap3.put("time", str2);
                hashMap3.put("etime", str3);
                String str6 = "";
                if (i == null || i.size() == 0) {
                    hashMap3.put(HttpRequestParams.NOTICE_LIST, str6);
                } else {
                    int i12 = 0;
                    Iterator<Long> it3 = i.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            i7 = i12;
                            break;
                        }
                        long longValue3 = it3.next().longValue();
                        if (str6.length() + String.valueOf(longValue3).length() > 1024) {
                            hashMap3.put(HttpRequestParams.NOTICE_LIST + i12, str6);
                            str6 = String.valueOf(longValue3) + ",";
                            i7 = i12 + 1;
                        } else {
                            str6 = str6 + String.valueOf(longValue3) + ",";
                            i7 = i12;
                        }
                        if (i7 > 45) {
                            break;
                        }
                        i12 = i7;
                    }
                    if (str6.length() > 0 && i7 <= 45) {
                        hashMap3.put(HttpRequestParams.NOTICE_LIST + i7, str6);
                    }
                }
                hashMap3.put("listname", "gpu_temp_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap3);
                HashMap hashMap4 = new HashMap();
                hashMap4.put(IMFriendInfoExViber.TAG, str);
                hashMap4.put("time", str2);
                hashMap4.put("etime", str3);
                String str7 = "";
                if (j == null || j.size() == 0) {
                    hashMap4.put(HttpRequestParams.NOTICE_LIST, str7);
                } else {
                    int i13 = 0;
                    Iterator<Long> it4 = j.iterator();
                    while (true) {
                        if (!it4.hasNext()) {
                            i6 = i13;
                            break;
                        }
                        long longValue4 = it4.next().longValue();
                        if (str7.length() + String.valueOf(longValue4).length() > 1024) {
                            hashMap4.put(HttpRequestParams.NOTICE_LIST + i13, str7);
                            str7 = String.valueOf(longValue4) + ",";
                            i6 = i13 + 1;
                        } else {
                            str7 = str7 + String.valueOf(longValue4) + ",";
                            i6 = i13;
                        }
                        if (i6 > 45) {
                            break;
                        }
                        i13 = i6;
                    }
                    if (str7.length() > 0 && i6 <= 45) {
                        hashMap4.put(HttpRequestParams.NOTICE_LIST + i6, str7);
                    }
                }
                hashMap4.put("listname", "gpu_usage_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap4);
            }
            if (bVar.h == 2) {
                HashMap hashMap5 = new HashMap();
                hashMap5.put(IMFriendInfoExViber.TAG, str);
                hashMap5.put("time", str2);
                hashMap5.put("etime", str3);
                String str8 = "";
                if (d == null || d.size() == 0) {
                    hashMap5.put(HttpRequestParams.NOTICE_LIST, str8);
                } else {
                    int i14 = 0;
                    Iterator<Long> it5 = d.iterator();
                    while (true) {
                        if (!it5.hasNext()) {
                            i5 = i14;
                            break;
                        }
                        long longValue5 = it5.next().longValue();
                        if (str8.length() + String.valueOf(longValue5).length() > 1024) {
                            hashMap5.put(HttpRequestParams.NOTICE_LIST + i14, str8);
                            str8 = String.valueOf(longValue5) + ",";
                            i5 = i14 + 1;
                        } else {
                            str8 = str8 + String.valueOf(longValue5) + ",";
                            i5 = i14;
                        }
                        if (i5 > 45) {
                            break;
                        }
                        i14 = i5;
                    }
                    if (str8.length() > 0 && i5 <= 45) {
                        hashMap5.put(HttpRequestParams.NOTICE_LIST + i5, str8);
                    }
                }
                hashMap5.put("listname", "mem_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap5);
            }
            if (bVar.f == 2) {
                HashMap hashMap6 = new HashMap();
                hashMap6.put(IMFriendInfoExViber.TAG, str);
                hashMap6.put("time", str2);
                hashMap6.put("etime", str3);
                String str9 = "";
                List<Short> e2 = this.X.e();
                if (e2 == null || e2.size() == 0) {
                    hashMap6.put(HttpRequestParams.NOTICE_LIST, str9);
                } else {
                    int i15 = 0;
                    Iterator<Short> it6 = e2.iterator();
                    while (true) {
                        if (!it6.hasNext()) {
                            i4 = i15;
                            break;
                        }
                        short shortValue = it6.next().shortValue();
                        if (str9.length() + String.valueOf(shortValue).length() > 1024) {
                            hashMap6.put(HttpRequestParams.NOTICE_LIST + i15, str9);
                            str9 = String.valueOf(shortValue) + ",";
                            i4 = i15 + 1;
                        } else {
                            str9 = str9 + String.valueOf(shortValue) + ",";
                            i4 = i15;
                        }
                        if (i4 > 45) {
                            break;
                        }
                        i15 = i4;
                    }
                    if (str9.length() > 0 && i4 <= 45) {
                        hashMap6.put(HttpRequestParams.NOTICE_LIST + i4, str9);
                    }
                }
                hashMap6.put("listname", "udp_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap6);
                HashMap hashMap7 = new HashMap();
                hashMap7.put(IMFriendInfoExViber.TAG, str);
                hashMap7.put("time", str2);
                hashMap7.put("etime", str3);
                String str10 = "";
                if (m == null || m.size() == 0) {
                    hashMap7.put(HttpRequestParams.NOTICE_LIST, str10);
                } else {
                    int i16 = 0;
                    Iterator<Long> it7 = m.iterator();
                    while (true) {
                        if (!it7.hasNext()) {
                            i3 = i16;
                            break;
                        }
                        long longValue6 = it7.next().longValue();
                        if (str10.length() + String.valueOf(longValue6).length() > 1024) {
                            hashMap7.put(HttpRequestParams.NOTICE_LIST + i16, str10);
                            str10 = String.valueOf(longValue6) + ",";
                            i3 = i16 + 1;
                        } else {
                            str10 = str10 + String.valueOf(longValue6) + ",";
                            i3 = i16;
                        }
                        if (i3 > 45) {
                            break;
                        }
                        i16 = i3;
                    }
                    if (str10.length() > 0 && i3 <= 45) {
                        hashMap7.put(HttpRequestParams.NOTICE_LIST + i3, str10);
                    }
                }
                hashMap7.put("listname", "signal_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap7);
                HashMap hashMap8 = new HashMap();
                hashMap8.put(IMFriendInfoExViber.TAG, str);
                hashMap8.put("time", str2);
                hashMap8.put("etime", str3);
                String str11 = "";
                if (n == null || n.size() == 0) {
                    hashMap8.put(HttpRequestParams.NOTICE_LIST, str11);
                } else {
                    int i17 = 0;
                    Iterator<Long> it8 = n.iterator();
                    while (true) {
                        if (!it8.hasNext()) {
                            i2 = i17;
                            break;
                        }
                        long longValue7 = it8.next().longValue();
                        if (str11.length() + String.valueOf(longValue7).length() > 1024) {
                            hashMap8.put(HttpRequestParams.NOTICE_LIST + i17, str11);
                            str11 = String.valueOf(longValue7) + ",";
                            i2 = i17 + 1;
                        } else {
                            str11 = str11 + String.valueOf(longValue7) + ",";
                            i2 = i17;
                        }
                        if (i2 > 45) {
                            break;
                        }
                        i17 = i2;
                    }
                    if (str11.length() > 0 && i2 <= 45) {
                        hashMap8.put(HttpRequestParams.NOTICE_LIST + i2, str11);
                    }
                }
                hashMap8.put("listname", "link_speed_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap8);
            }
            if (bVar.e == 2) {
                HashMap hashMap9 = new HashMap();
                hashMap9.put(IMFriendInfoExViber.TAG, str);
                hashMap9.put("time", str2);
                hashMap9.put("etime", str3);
                String str12 = "";
                if (cVar.k == null || !cVar.k.contains(",")) {
                    strArr = null;
                } else {
                    strArr = cVar.k.split(",");
                }
                if (strArr == null || strArr.length == 0) {
                    hashMap9.put(HttpRequestParams.NOTICE_LIST, str12);
                } else {
                    int i18 = 0;
                    for (String str13 : strArr) {
                        if (str12.length() + str13.length() > 1024) {
                            hashMap9.put(HttpRequestParams.NOTICE_LIST + i18, str12);
                            str12 = str13 + ",";
                            i18++;
                        } else {
                            str12 = str12 + str13 + ",";
                        }
                        if (i18 > 45) {
                            break;
                        }
                    }
                    if (str12.length() > 0 && i18 <= 45) {
                        hashMap9.put(HttpRequestParams.NOTICE_LIST + i18, str12);
                    }
                }
                hashMap9.put("listname", "fps_list");
                a("gsdk_report_eventList", y, true, (Map<String, String>) hashMap9);
            }
        } catch (Exception e3) {
            Logger.e("reportGsdkList error:" + e3.getMessage());
        }
    }

    public static String g() {
        String b2 = f.b(o, "gsdk_prefs", "open_id");
        return b2.equals("NULL") ? "UNKNOWN" : b2;
    }

    public void a(IEventObserver iEventObserver) {
        this.Y = iEventObserver;
    }

    private void a(String str, boolean z2) {
        HashMap hashMap = new HashMap();
        hashMap.put("rcr", str);
        if (z2) {
            hashMap.put("isCache", "1");
        } else {
            hashMap.put("isCache", "0");
        }
        a("gsdk_report_0", y, true, (Map<String, String>) hashMap);
        if (this.Y != null) {
            this.Y.onEvent(new Event("GetPVPConfig", true, "getPVPConfig", this.Z, !z2 && (this.O == null || this.O.a == 0), str));
        }
    }

    private static void a(String str, long j2, boolean z2, Map<String, String> map) {
        map.put("openid", g());
        for (String next : map.keySet()) {
            Logger.i(next + ":" + map.get(next));
        }
        long currentTimeMillis = System.currentTimeMillis() - j2;
        com.tencent.gsdk.utils.b.f.a(3, str, map);
    }

    public String c(String str) {
        String str2 = "";
        int i2 = Build.VERSION.SDK_INT;
        try {
            String a2 = com.tencent.gsdk.utils.a.b.a.a();
            Logger.i("request controller url:" + a2);
            HttpURLConnection b2 = com.tencent.gsdk.utils.a.a.a.b(a2);
            b2.connect();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("platform", q);
            jSONObject.put("openid", g());
            jSONObject.put(HttpRequestParams.OS, 1);
            jSONObject.put("platapi", i2);
            jSONObject.put("version", s);
            jSONObject.put("zoneid", str);
            this.Z = jSONObject.toString();
            byte[] bytes = com.tencent.gsdk.utils.a.a(this.Z).getBytes();
            DataOutputStream dataOutputStream = new DataOutputStream(b2.getOutputStream());
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
            dataOutputStream.close();
            if (b2.getResponseCode() == 200) {
                InputStream inputStream = b2.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.close();
                inputStream.close();
                String str3 = new String(byteArrayOutputStream.toByteArray());
                if (str3 != null) {
                    try {
                        str2 = com.tencent.gsdk.utils.a.b(str3);
                    } catch (Exception e2) {
                        e = e2;
                        str2 = str3;
                    }
                } else {
                    str2 = str3;
                }
                Logger.i("request controller result:" + str2);
            } else {
                Logger.e("request controller response code error is:" + b2.getResponseCode());
            }
        } catch (Exception e3) {
            e = e3;
            Logger.e("request controller error, msg:" + e.getMessage());
            return str2;
        }
        return str2;
    }

    private String f(String str) {
        String str2 = "";
        int i2 = Build.VERSION.SDK_INT;
        try {
            String c2 = com.tencent.gsdk.utils.a.b.a.c();
            Logger.i("request quality controller url:" + c2);
            HttpURLConnection b2 = com.tencent.gsdk.utils.a.a.a.b(c2);
            b2.connect();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("openid", g());
            jSONObject.put(HttpRequestParams.OS, 1);
            jSONObject.put("version", s);
            jSONObject.put("quality", str);
            jSONObject.put("model", Build.MODEL);
            jSONObject.put("platapi", i2);
            jSONObject.put("quality", str);
            Logger.i("request quality json:" + jSONObject.toString());
            byte[] bytes = com.tencent.gsdk.utils.a.a(jSONObject.toString()).getBytes();
            DataOutputStream dataOutputStream = new DataOutputStream(b2.getOutputStream());
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
            dataOutputStream.close();
            if (b2.getResponseCode() == 200) {
                InputStream inputStream = b2.getInputStream();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, read);
                }
                byteArrayOutputStream.close();
                inputStream.close();
                String str3 = new String(byteArrayOutputStream.toByteArray());
                if (str3 != null) {
                    try {
                        str2 = com.tencent.gsdk.utils.a.b(str3);
                    } catch (Exception e2) {
                        e = e2;
                        str2 = str3;
                    }
                } else {
                    str2 = str3;
                }
                Logger.i("request quality controller result:" + str2);
            } else {
                Logger.e("request quality controller response code error is:" + b2.getResponseCode());
            }
        } catch (Exception e3) {
            e = e3;
            Logger.e("request quality controller error, msg:" + e.getMessage());
            return str2;
        }
        return str2;
    }

    static class h implements Runnable {
        private int a;
        private int b;

        public h(b bVar, int i) {
            this.a = bVar.q;
            this.b = i;
        }

        public void run() {
            if (this.a > 0 && this.b == 4) {
                int unused = GSDKSystem.F = com.tencent.gsdk.utils.c.d(GSDKSystem.o);
                if (GSDKSystem.m != null) {
                    GSDKSystem.m.add(Long.valueOf((long) GSDKSystem.F));
                }
                if (GSDKSystem.n != null) {
                    GSDKSystem.n.add(Long.valueOf((long) com.tencent.gsdk.utils.c.e(GSDKSystem.o)));
                }
            } else if (com.tencent.gsdk.utils.c.a(GSDKSystem.o) && this.b != 4) {
                long a2 = (long) com.tencent.gsdk.utils.c.a();
                if (GSDKSystem.l != null) {
                    GSDKSystem.l.add(Long.valueOf(a2));
                }
                if (GSDKSystem.m != null) {
                    GSDKSystem.m.add(Long.valueOf(a2));
                }
            }
        }
    }

    static class g implements Runnable {
        private int a;
        private int b;

        public g(b bVar) {
            this.a = bVar.h;
            this.b = bVar.g;
        }

        public void run() {
            if (this.a > 0) {
                GSDKSystem.e.add(Long.valueOf(g.b(GSDKSystem.o)));
                GSDKSystem.d.add(Long.valueOf(g.c(GSDKSystem.o)));
            }
            if (this.b > 0) {
                if (GSDKSystem.g != null) {
                    GSDKSystem.g.add(Long.valueOf((long) g.d()));
                }
                if (GSDKSystem.f != null) {
                    GSDKSystem.f.add(Long.valueOf((long) g.e()));
                }
                if (GSDKSystem.h != null) {
                    GSDKSystem.h.add(Long.valueOf(g.h()));
                }
                if (GSDKSystem.i != null) {
                    GSDKSystem.i.add(Long.valueOf(g.i()));
                }
                if (GSDKSystem.j != null) {
                    GSDKSystem.j.add(Long.valueOf((long) g.j()));
                }
            }
        }
    }

    private static class d implements Runnable {
        private Context a;
        /* access modifiers changed from: private */
        public volatile float b = Float.MIN_VALUE;

        public d(Context context) {
            this.a = context;
        }

        public void run() {
            this.b = Math.max(g.d(this.a), this.b);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00d0 A[SYNTHETIC, Splitter:B:22:0x00d0] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00f3 A[SYNTHETIC, Splitter:B:28:0x00f3] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String g(java.lang.String r7) {
        /*
            r6 = this;
            r2 = 0
            java.lang.String r1 = ">srW/8;&"
            java.lang.String r0 = com.tencent.gsdk.utils.h.a(r7, r1)
            java.net.URL r3 = new java.net.URL     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r4.<init>()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.String r5 = "http://182.254.116.117/d?dn="
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.String r4 = "&clientip=1&ttl=1&id=1"
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r0.<init>()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.String r4 = "GSDK HttpDns URL: "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.lang.String r0 = r0.toString()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            com.tencent.gsdk.utils.Logger.i(r0)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.net.URLConnection r0 = r3.openConnection()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r3 = 1000(0x3e8, float:1.401E-42)
            r0.setConnectTimeout(r3)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r3 = 1000(0x3e8, float:1.401E-42)
            r0.setReadTimeout(r3)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            java.io.InputStream r0 = r0.getInputStream()     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r4.<init>(r0)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00b1, all -> 0x00f0 }
            r0 = r2
        L_0x0058:
            java.lang.String r2 = r3.readLine()     // Catch:{ IOException -> 0x0116 }
            if (r2 == 0) goto L_0x008c
            java.lang.String r2 = com.tencent.gsdk.utils.h.b(r2, r1)     // Catch:{ IOException -> 0x0116 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0116 }
            r4.<init>()     // Catch:{ IOException -> 0x0116 }
            java.lang.String r5 = "HttpDnsServer response ips are "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x0116 }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ IOException -> 0x0116 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0116 }
            com.tencent.gsdk.utils.Logger.i(r4)     // Catch:{ IOException -> 0x0116 }
            java.lang.String r4 = "|"
            boolean r4 = r2.contains(r4)     // Catch:{ IOException -> 0x0116 }
            if (r4 == 0) goto L_0x0058
            r4 = 0
            java.lang.String r5 = "|"
            int r5 = r2.indexOf(r5)     // Catch:{ IOException -> 0x0116 }
            java.lang.String r0 = r2.substring(r4, r5)     // Catch:{ IOException -> 0x0116 }
            goto L_0x0058
        L_0x008c:
            r3.close()     // Catch:{ IOException -> 0x0116 }
            if (r3 == 0) goto L_0x0094
            r3.close()     // Catch:{ IOException -> 0x0095 }
        L_0x0094:
            return r0
        L_0x0095:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getHttpDns colse reader error:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.w(r1)
            goto L_0x0094
        L_0x00b1:
            r1 = move-exception
            r0 = r2
            r3 = r2
        L_0x00b4:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0113 }
            r2.<init>()     // Catch:{ all -> 0x0113 }
            java.lang.String r4 = "getHttpDns error:"
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ all -> 0x0113 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0113 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0113 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0113 }
            com.tencent.gsdk.utils.Logger.w(r1)     // Catch:{ all -> 0x0113 }
            if (r3 == 0) goto L_0x0094
            r3.close()     // Catch:{ IOException -> 0x00d4 }
            goto L_0x0094
        L_0x00d4:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getHttpDns colse reader error:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.w(r1)
            goto L_0x0094
        L_0x00f0:
            r0 = move-exception
        L_0x00f1:
            if (r2 == 0) goto L_0x00f6
            r2.close()     // Catch:{ IOException -> 0x00f7 }
        L_0x00f6:
            throw r0
        L_0x00f7:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "getHttpDns colse reader error:"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r1 = r1.getMessage()
            java.lang.StringBuilder r1 = r2.append(r1)
            java.lang.String r1 = r1.toString()
            com.tencent.gsdk.utils.Logger.w(r1)
            goto L_0x00f6
        L_0x0113:
            r0 = move-exception
            r2 = r3
            goto L_0x00f1
        L_0x0116:
            r1 = move-exception
            goto L_0x00b4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.gsdk.api.GSDKSystem.g(java.lang.String):java.lang.String");
    }

    class e {
        String a;

        public e(String str) {
            this.a = str;
        }
    }

    class a {
        int a = -1;
        int b = -1;
        boolean c = false;
        String d = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;

        public a(int i, int i2, boolean z, String str) {
            this.a = i;
            this.b = i2;
            this.c = z;
            this.d = str;
        }
    }

    class b {
        int a = -1;
        String b = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        long c = -1;

        public b(int i, String str, long j) {
            this.a = i;
            this.b = str;
            this.c = j;
        }
    }

    class i {
        int a = -1;
        String b = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        long c = -1;

        public i(int i, String str, long j) {
            this.a = i;
            this.b = str;
            this.c = j;
        }
    }

    class f implements Application.ActivityLifecycleCallbacks {
        f() {
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
            Logger.i("onActivityCreated");
        }

        public void onActivityStarted(Activity activity) {
            Logger.i("onActivityStarted");
        }

        public void onActivityResumed(Activity activity) {
            Logger.i("onActivityResumed");
        }

        public void onActivityPaused(Activity activity) {
            Logger.i("onActivityPaused");
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            Logger.i("onActivitySaveInstanceState");
        }

        public void onActivityStopped(Activity activity) {
            Logger.i("onActivityStopped");
            if (GSDKSystem.t && !GSDKSystem.v && GSDKSystem.this.R != null) {
                try {
                    if (!GSDKSystem.this.R.d && GSDKSystem.this.r != null) {
                        Message obtain = Message.obtain();
                        obtain.obj = GSDKSystem.this.R;
                        obtain.what = 8;
                        GSDKSystem.this.r.sendMessage(obtain);
                    }
                } catch (Exception e) {
                    Logger.e("gsdk onActivityStopped enum error:" + e.getMessage());
                }
            }
        }

        public void onActivityDestroyed(Activity activity) {
            Logger.i("onActivityDestroyed");
        }
    }
}
