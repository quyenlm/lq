package com.appsflyer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class j {

    /* renamed from: ʻ  reason: contains not printable characters */
    private static final BitSet f114 = new BitSet(6);

    /* renamed from: ʼ  reason: contains not printable characters */
    private static volatile j f115;

    /* renamed from: ॱॱ  reason: contains not printable characters */
    private static final Handler f116 = new Handler(Looper.getMainLooper());

    /* renamed from: ʽ  reason: contains not printable characters */
    final Runnable f117 = new Runnable() {
        public final void run() {
            synchronized (j.this.f120) {
                if (j.this.f123) {
                    j.this.f122.removeCallbacks(j.this.f125);
                    j.this.f122.removeCallbacks(j.this.f118);
                    j.this.m89();
                    j.this.f123 = false;
                }
            }
        }
    };

    /* renamed from: ˊ  reason: contains not printable characters */
    final Runnable f118 = new AnonymousClass1();

    /* renamed from: ˊॱ  reason: contains not printable characters */
    private final Map<f, Map<String, Object>> f119 = new HashMap(f114.size());

    /* renamed from: ˋ  reason: contains not printable characters */
    final Object f120 = new Object();

    /* renamed from: ˋॱ  reason: contains not printable characters */
    private boolean f121;

    /* renamed from: ˎ  reason: contains not printable characters */
    final Handler f122;

    /* renamed from: ˏ  reason: contains not printable characters */
    boolean f123;

    /* renamed from: ˏॱ  reason: contains not printable characters */
    private final SensorManager f124;

    /* renamed from: ॱ  reason: contains not printable characters */
    final Runnable f125 = new AnonymousClass2();

    /* renamed from: ᐝ  reason: contains not printable characters */
    private final Map<f, f> f126 = new HashMap(f114.size());

    static {
        f114.set(1);
        f114.set(2);
        f114.set(4);
    }

    /* renamed from: com.appsflyer.j$1  reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        AnonymousClass1() {
        }

        public final void run() {
            synchronized (j.this.f120) {
                j.this.m89();
                j.this.f122.postDelayed(j.this.f125, 1800000);
            }
        }

        AnonymousClass1() {
        }

        /* renamed from: ˋ  reason: contains not printable characters */
        static boolean m91(Context context, String str) {
            int checkSelfPermission = ContextCompat.checkSelfPermission(context, str);
            AFLogger.afRDLog(new StringBuilder("is Permission Available: ").append(str).append("; res: ").append(checkSelfPermission).toString());
            return checkSelfPermission == 0;
        }
    }

    /* renamed from: com.appsflyer.j$2  reason: invalid class name */
    class AnonymousClass2 implements Runnable {

        /* renamed from: ˋ  reason: contains not printable characters */
        private static String f128;

        /* renamed from: ˏ  reason: contains not printable characters */
        private static String f129;

        AnonymousClass2() {
        }

        public final void run() {
            synchronized (j.this.f120) {
                j.this.m90();
                j.this.f122.postDelayed(j.this.f118, 500);
                j.this.f123 = true;
            }
        }

        AnonymousClass2() {
        }

        /* renamed from: ˏ  reason: contains not printable characters */
        static void m93(String str) {
            f128 = str;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (i == 0 || i == str.length() - 1) {
                    sb.append(str.charAt(i));
                } else {
                    sb.append("*");
                }
            }
            f129 = sb.toString();
        }

        /* renamed from: ˊ  reason: contains not printable characters */
        static void m92(String str) {
            if (f128 == null) {
                m93(AppsFlyerProperties.getInstance().getString(AppsFlyerProperties.AF_KEY));
            }
            if (f128 != null && str.contains(f128)) {
                AFLogger.afInfoLog(str.replace(f128, f129));
            }
        }
    }

    private j(@NonNull SensorManager sensorManager, Handler handler) {
        this.f124 = sensorManager;
        this.f122 = handler;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    static j m87(Context context) {
        return m86((SensorManager) context.getApplicationContext().getSystemService("sensor"), f116);
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    private static j m86(SensorManager sensorManager, Handler handler) {
        if (f115 == null) {
            synchronized (j.class) {
                if (f115 == null) {
                    f115 = new j(sensorManager, handler);
                }
            }
        }
        return f115;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m90() {
        boolean z;
        try {
            for (Sensor next : this.f124.getSensorList(-1)) {
                int type = next.getType();
                if (type < 0 || !f114.get(type)) {
                    z = false;
                } else {
                    z = true;
                }
                if (z) {
                    f r1 = f.m65(next);
                    if (!this.f126.containsKey(r1)) {
                        this.f126.put(r1, r1);
                    }
                    this.f124.registerListener(this.f126.get(r1), next, 0);
                }
            }
        } catch (Throwable th) {
        }
        this.f121 = true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: ˋ  reason: contains not printable characters */
    public final void m89() {
        try {
            if (!this.f126.isEmpty()) {
                for (f next : this.f126.values()) {
                    this.f124.unregisterListener(next);
                    next.m69(this.f119);
                }
            }
        } catch (Throwable th) {
        }
        this.f121 = false;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    /* renamed from: ˊ  reason: contains not printable characters */
    public final List<Map<String, Object>> m88() {
        List<Map<String, Object>> arrayList;
        synchronized (this.f120) {
            if (!this.f126.isEmpty() && this.f121) {
                for (f r0 : this.f126.values()) {
                    r0.m68(this.f119);
                }
            }
            if (this.f119.isEmpty()) {
                arrayList = Collections.emptyList();
            } else {
                arrayList = new ArrayList<>(this.f119.values());
            }
        }
        return arrayList;
    }
}
