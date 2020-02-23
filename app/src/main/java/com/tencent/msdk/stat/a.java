package com.tencent.msdk.stat;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.msdk.stat.common.StatLogger;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.p;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.http.HttpHost;

public class a {
    private static a g = null;
    private List<String> a = new ArrayList(10);
    private volatile int b = 2;
    private volatile String c = "";
    private volatile HttpHost d = null;
    /* access modifiers changed from: private */
    public Handler e = null;
    private int f = 0;
    private Context h = null;
    private StatLogger i = null;

    private a(Context context) {
        this.h = context.getApplicationContext();
        HandlerThread handlerThread = new HandlerThread("StatNetworkMan");
        handlerThread.start();
        this.e = new Handler(handlerThread.getLooper());
        k.a(context);
        this.i = j.b();
        j();
        i();
        g();
    }

    public static a a(Context context) {
        if (g == null) {
            synchronized (a.class) {
                if (g == null) {
                    g = new a(context);
                }
            }
        }
        return g;
    }

    private void i() {
        this.a.add("117.135.169.101");
        this.a.add("140.207.54.125");
        this.a.add("180.153.8.53");
        this.a.add("120.198.203.175");
        this.a.add("14.17.43.18");
        this.a.add("163.177.71.186");
        this.a.add("111.30.131.31");
        this.a.add("123.126.121.167");
        this.a.add("123.151.152.111");
        this.a.add("113.142.45.79");
        this.a.add("123.138.162.90");
        this.a.add("103.7.30.94");
    }

    private void j() {
        this.b = 0;
        this.d = null;
        this.c = null;
    }

    public HttpHost a() {
        return this.d;
    }

    public void a(String str) {
        if (StatConfig.isDebugEnable()) {
            this.i.i("updateIpList " + str);
        }
        this.f = new Random().nextInt(this.a.size());
    }

    public String b() {
        return this.c;
    }

    public int c() {
        return this.b;
    }

    public void d() {
        if (this.a != null && this.a.size() > 0) {
            this.f = (this.f + 1) % this.a.size();
        }
    }

    public boolean e() {
        return this.b == 1;
    }

    public boolean f() {
        return this.b != 0;
    }

    /* access modifiers changed from: package-private */
    public void g() {
        if (p.f(this.h)) {
            this.c = j.k(this.h);
            if (StatConfig.isDebugEnable()) {
                this.i.i("NETWORK name:" + this.c);
            }
            if (j.c(this.c)) {
                if ("WIFI".equalsIgnoreCase(this.c)) {
                    this.b = 1;
                } else {
                    this.b = 2;
                }
                this.d = j.a(this.h);
            }
            if (StatServiceImpl.a()) {
                StatServiceImpl.e(this.h);
                return;
            }
            return;
        }
        if (StatConfig.isDebugEnable()) {
            this.i.i("NETWORK TYPE: network is close.");
        }
        j();
    }

    public void h() {
        this.h.getApplicationContext().registerReceiver(new b(this), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
}
