package com.subao.common.j;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.util.Log;
import com.subao.common.d;
import com.subao.common.e.n;
import com.subao.common.j.j;
import java.util.Locale;

/* compiled from: NetManager */
public class h implements j {
    @SuppressLint({"StaticFieldLeak"})
    private static h a;
    private final Context b;
    private boolean c;
    private boolean d;
    private boolean e;
    private j.a f;
    private a g;

    /* compiled from: NetManager */
    public interface a {
        void a(Context context, j.a aVar);
    }

    private h(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.b = applicationContext;
        applicationContext.registerReceiver(new b(), new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        d(applicationContext);
    }

    public static h a(Context context) {
        h hVar = a;
        if (hVar == null) {
            synchronized (h.class) {
                hVar = a;
                if (hVar == null) {
                    hVar = new h(context);
                    a = hVar;
                }
            }
        }
        return hVar;
    }

    private static boolean b(NetworkInfo networkInfo) {
        return networkInfo != null && NetworkInfo.State.CONNECTED == networkInfo.getState();
    }

    @NonNull
    static j.a b(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return j.a.UNKNOWN;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            return a(activeNetworkInfo);
        }
        d.b("SubaoNet", "getActiveNetworkInfo() return null");
        return j.a.DISCONNECT;
    }

    public static j.a a(@NonNull NetworkInfo networkInfo) {
        if (!networkInfo.isConnectedOrConnecting()) {
            return j.a.DISCONNECT;
        }
        switch (networkInfo.getType()) {
            case 0:
                return f.a(networkInfo.getSubtype());
            case 1:
                return j.a.WIFI;
            default:
                d.b("SubaoNet", "NetworkInfo.getType() return: " + networkInfo.getType());
                return j.a.UNKNOWN;
        }
    }

    /* access modifiers changed from: package-private */
    public void c(Context context) {
        d(context);
        j.a g2 = g();
        if (g2 != this.f) {
            if (d.a("SubaoNet")) {
                Locale locale = n.b;
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(this.f == null ? -1 : this.f.g);
                objArr[1] = Integer.valueOf(g2.g);
                Log.d("SubaoNet", String.format(locale, "Connection Changed: %d -> %d", objArr));
            }
            this.f = g2;
            a aVar = this.g;
            if (aVar != null) {
                aVar.a(context, this.f);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void d(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                f();
            } else if (b(connectivityManager.getNetworkInfo(1))) {
                e();
            } else {
                if (b(connectivityManager.getNetworkInfo(0))) {
                    d();
                    return;
                }
                f();
            }
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        }
    }

    private void d() {
        this.c = true;
        this.e = true;
        this.d = false;
    }

    private void e() {
        this.c = true;
        this.d = true;
        this.e = false;
    }

    private void f() {
        this.c = false;
        this.d = false;
        this.e = false;
    }

    @NonNull
    private j.a g() {
        if (!b()) {
            return j.a.DISCONNECT;
        }
        if (c()) {
            return j.a.WIFI;
        }
        return b(this.b);
    }

    public void a(a aVar) {
        this.g = aVar;
    }

    @NonNull
    public j.a a() {
        return g();
    }

    public boolean b() {
        return this.c;
    }

    public boolean c() {
        return this.d;
    }

    /* compiled from: NetManager */
    class b extends BroadcastReceiver {
        b() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                h.this.c(context);
            }
        }
    }
}
