package com.subao.gamemaster;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.VpnService;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.subao.common.a.a;
import com.subao.common.a.b;
import com.subao.common.a.e;
import com.subao.common.d;
import com.subao.common.e.n;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Locale;

@TargetApi(14)
public class GameMasterVpnService extends e {
    private static final byte[] b = {10, 0, 0, 2};
    private static final byte[] c = {0, 0, 0, 0};
    private static final byte[] d = {8, 8, 8, 8};
    private static String e;
    private static GameMasterVpnService f;
    protected ParcelFileDescriptor a;

    private static void b(String str) {
        Log.d("SubaoGame", "GameVpn: " + str);
    }

    static String a(Context context) {
        Configuration configuration;
        String str = e;
        if (!TextUtils.isEmpty(str)) {
            return str;
        }
        Locale locale = null;
        Resources resources = context.getResources();
        if (!(resources == null || (configuration = resources.getConfiguration()) == null)) {
            locale = configuration.locale;
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        if (locale == null || "CN".equals(locale.getCountry())) {
            return "游戏加速服务";
        }
        if ("TW".equals(locale.getCountry())) {
            return "遊戲加速服務";
        }
        return "Game Acceleration Service";
    }

    public static synchronized GameMasterVpnService c() {
        GameMasterVpnService gameMasterVpnService;
        synchronized (GameMasterVpnService.class) {
            gameMasterVpnService = f;
        }
        return gameMasterVpnService;
    }

    public static boolean b(Context context) {
        return context.startService(new Intent(context, GameMasterVpnService.class)) != null;
    }

    public static void c(Context context) {
        context.stopService(new Intent(context, GameMasterVpnService.class));
    }

    @TargetApi(21)
    static void a(VpnService.Builder builder, Iterable<String> iterable) {
        if (iterable != null) {
            for (String a2 : iterable) {
                a(builder, a2);
            }
        }
    }

    @TargetApi(21)
    static boolean a(VpnService.Builder builder, String str) {
        boolean z;
        try {
            builder.addAllowedApplication(str);
            z = true;
        } catch (PackageManager.NameNotFoundException | RuntimeException e2) {
            z = false;
        }
        b(String.format("add allowed app (%s) return %b", new Object[]{str, Boolean.valueOf(z)}));
        return z;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        int i3;
        int onStartCommand = super.onStartCommand(intent, i, i2);
        PackageManager packageManager = getPackageManager();
        if (packageManager != null) {
            try {
                Bundle bundle = packageManager.getServiceInfo(new ComponentName(this, GameMasterVpnService.class), 128).metaData;
                if (bundle == null || (i3 = bundle.getInt("start_command_result", -1)) < 0) {
                    i3 = onStartCommand;
                }
                onStartCommand = i3;
            } catch (PackageManager.NameNotFoundException e2) {
            }
        }
        b(String.format(n.b, "onStartCommand(%s, %d, %d) return %d", new Object[]{intent, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(onStartCommand)}));
        return onStartCommand;
    }

    public void onCreate() {
        b("service create");
        super.onCreate();
        synchronized (GameMasterVpnService.class) {
            f = this;
        }
        a a2 = b.a();
        if (a2 != null) {
            b("Notify AccelEngine instance when service create");
            a2.b();
            return;
        }
        b("AccelEngine instance is null");
    }

    public void onRevoke() {
        if (d.a("SubaoGame")) {
            d.a("SubaoGame", "service revoked");
        }
        a();
        super.onRevoke();
    }

    public void onDestroy() {
        b("service destroy");
        a();
        super.onDestroy();
        synchronized (GameMasterVpnService.class) {
            if (f == this) {
                f = null;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
        return d();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int a(java.lang.Iterable<java.lang.String> r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            android.os.ParcelFileDescriptor r0 = r3.a     // Catch:{ all -> 0x0033 }
            if (r0 != 0) goto L_0x002d
            java.lang.String r0 = "establish ..."
            b((java.lang.String) r0)     // Catch:{ all -> 0x0033 }
            android.net.VpnService$Builder r0 = new android.net.VpnService$Builder     // Catch:{ all -> 0x0033 }
            r0.<init>(r3)     // Catch:{ all -> 0x0033 }
            int r0 = r3.a((java.lang.Iterable<java.lang.String>) r4, (android.net.VpnService.Builder) r0)     // Catch:{ all -> 0x0033 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0033 }
            r1.<init>()     // Catch:{ all -> 0x0033 }
            java.lang.String r2 = "establish return: "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ all -> 0x0033 }
            java.lang.StringBuilder r1 = r1.append(r0)     // Catch:{ all -> 0x0033 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0033 }
            b((java.lang.String) r1)     // Catch:{ all -> 0x0033 }
            if (r0 == 0) goto L_0x002d
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
        L_0x002c:
            return r0
        L_0x002d:
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            int r0 = r3.d()
            goto L_0x002c
        L_0x0033:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0033 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.subao.gamemaster.GameMasterVpnService.a(java.lang.Iterable):int");
    }

    public boolean b() {
        return this.a != null;
    }

    /* access modifiers changed from: package-private */
    public int d() {
        ParcelFileDescriptor parcelFileDescriptor = this.a;
        if (parcelFileDescriptor == null) {
            return 8006;
        }
        return b.a().a(parcelFileDescriptor.getFd());
    }

    /* access modifiers changed from: package-private */
    public int a(Iterable<String> iterable, VpnService.Builder builder) {
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                a(builder, iterable);
                builder.addDnsServer(InetAddress.getByAddress(d));
            }
            builder.addAddress(InetAddress.getByAddress(b), 32);
            builder.addRoute(InetAddress.getByAddress(c), 0);
            builder.setSession(a(getApplicationContext()));
            builder.setConfigureIntent((PendingIntent) null);
            builder.setMtu(20000);
            this.a = builder.establish();
            if (this.a == null) {
                return ConnectionsStatusCodes.STATUS_NOT_CONNECTED_TO_ENDPOINT;
            }
            return 0;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 8000;
        } catch (Error e3) {
            e3.printStackTrace();
            return ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED;
        }
    }

    public void a() {
        synchronized (this) {
            if (this.a != null) {
                b("stop VPN");
                b.a().d();
                b("close interface");
                try {
                    this.a.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                this.a = null;
            }
        }
    }

    public static void a(String str) {
        e = str;
    }
}
