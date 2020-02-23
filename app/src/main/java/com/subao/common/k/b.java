package com.subao.common.k;

import android.content.Context;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.net.DatagramSocket;

/* compiled from: NetworkWatcher */
public class b {
    private static c a = new c(1000);

    /* compiled from: NetworkWatcher */
    public interface a {
        void b(C0018b bVar);

        void c(C0018b bVar);
    }

    /* renamed from: com.subao.common.k.b$b  reason: collision with other inner class name */
    /* compiled from: NetworkWatcher */
    public interface C0018b {
        NetworkInfo a(Context context);

        void a(DatagramSocket datagramSocket);
    }

    /* compiled from: NetworkWatcher */
    public enum e {
        CELLULAR,
        WIFI,
        BLUETOOTH,
        ETHERNET,
        VPN
    }

    private static synchronized void a(c cVar) {
        synchronized (b.class) {
            if (a != null) {
                a.a();
            }
            a = cVar;
        }
    }

    public static void a(Context context) {
        int i;
        if (!a()) {
            i = 2000;
        } else if (!b(context)) {
            i = 2001;
        } else {
            a((c) new d(context));
            return;
        }
        a((c) new c(i));
        throw new d(i);
    }

    private static boolean a() {
        boolean z = Build.VERSION.SDK_INT >= 21;
        if (!z && com.subao.common.d.a("SubaoParallel")) {
            Log.d("SubaoParallel", "WiFi-Accel not supported on Android version " + Build.VERSION.SDK_INT);
        }
        return z;
    }

    private static boolean b(Context context) {
        boolean z = context.getPackageManager().checkPermission("android.permission.CHANGE_NETWORK_STATE", context.getPackageName()) == 0;
        if (!z) {
            com.subao.common.d.a("SubaoParallel", "Has not required permission: CHANGE_NETWORK_STATE");
        }
        return z;
    }

    public static Object a(e eVar, a aVar) {
        if (aVar != null) {
            return a.a(eVar, aVar);
        }
        throw new NullPointerException("Callback cannot be null");
    }

    public static void a(Object obj) {
        a.a(obj);
    }

    /* compiled from: NetworkWatcher */
    public static class d extends IOException {
        private final int a;

        public d(int i) {
            super("Cellular Operation Exception, Error " + i);
            this.a = i;
        }

        public int a() {
            return this.a;
        }
    }

    /* compiled from: NetworkWatcher */
    private static class c implements c {
        private final int a;

        c(int i) {
            this.a = i;
        }

        public Object a(e eVar, a aVar) {
            throw new d(this.a);
        }

        public void a(Object obj) {
        }

        public void a() {
        }
    }
}
