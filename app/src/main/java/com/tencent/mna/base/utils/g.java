package com.tencent.mna.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.tencent.tp.a.h;

/* compiled from: LocateUtil */
public final class g {
    /* access modifiers changed from: private */
    public static LocationManager a;
    /* access modifiers changed from: private */
    public static a b;
    /* access modifiers changed from: private */
    public static boolean c = false;
    /* access modifiers changed from: private */
    public static HandlerThread d;
    /* access modifiers changed from: private */
    public static Handler e;
    /* access modifiers changed from: private */
    public static Runnable f = new Runnable() {
        public void run() {
            try {
                if (!(g.a == null || g.b == null)) {
                    g.a.removeUpdates(g.b);
                }
                if (g.d != null) {
                    g.d.quit();
                }
            } catch (Throwable th) {
            }
        }
    };
    /* access modifiers changed from: private */
    public static double g = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* access modifiers changed from: private */
    public static double h = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* access modifiers changed from: private */
    public static double i = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
    /* access modifiers changed from: private */
    public static double j = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

    public static void a(final Context context) {
        d = new HandlerThread("locate");
        d.start();
        e = new Handler(d.getLooper());
        e.postDelayed(f, 20000);
        e.post(new Runnable() {
            @SuppressLint({"MissingPermission"})
            public void run() {
                try {
                    LocationManager unused = g.a = (LocationManager) context.getSystemService("location");
                    if (k.a(context) != 0) {
                        a unused2 = g.b = new a();
                        g.a.requestLocationUpdates("network", 3000, 0.0f, g.b);
                    }
                } catch (Exception e) {
                    g.e.removeCallbacks(g.f);
                    g.e.post(g.f);
                } catch (Throwable th) {
                    h.c("LocateUtil, error:" + th.getMessage());
                }
            }
        });
    }

    public static double a() {
        return g;
    }

    public static double b() {
        return h;
    }

    /* compiled from: LocateUtil */
    private static class a implements LocationListener {
        private a() {
        }

        public void onLocationChanged(Location location) {
            try {
                h.a("LocateUtil locate location:(" + location.getLongitude() + "," + location.getLatitude() + h.b);
                double unused = g.g = location.getLongitude();
                double unused2 = g.h = location.getLatitude();
                double unused3 = g.i = location.getAltitude();
                double unused4 = g.j = (double) location.getAccuracy();
                boolean unused5 = g.c = true;
                g.e.removeCallbacks(g.f);
                g.e.post(g.f);
            } catch (Exception e) {
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onProviderDisabled(String str) {
        }
    }
}
