package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzbo;

public final class zzaoj {
    static zzctz zzads;
    private static Boolean zzadt;
    static Object zzuF = new Object();

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static void onReceive(Context context, Intent intent) {
        zzaoc zzkr = zzamj.zzaf(context).zzkr();
        if (intent == null) {
            zzkr.zzbr("AnalyticsReceiver called with null intent");
            return;
        }
        String action = intent.getAction();
        zzkr.zza("Local AnalyticsReceiver got", action);
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zzad = zzaok.zzad(context);
            Intent intent2 = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent2.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zzuF) {
                context.startService(intent2);
                if (zzad) {
                    try {
                        if (zzads == null) {
                            zzctz zzctz = new zzctz(context, 1, "Analytics WakeLock");
                            zzads = zzctz;
                            zzctz.setReferenceCounted(false);
                        }
                        zzads.acquire(1000);
                    } catch (SecurityException e) {
                        zzkr.zzbr("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }

    public static boolean zzac(Context context) {
        zzbo.zzu(context);
        if (zzadt != null) {
            return zzadt.booleanValue();
        }
        boolean zza = zzaos.zza(context, "com.google.android.gms.analytics.AnalyticsReceiver", false);
        zzadt = Boolean.valueOf(zza);
        return zza;
    }
}
