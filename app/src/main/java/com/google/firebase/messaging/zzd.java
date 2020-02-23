package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.analytics.FirebaseAnalytics;

final class zzd {
    private static AppMeasurement zzaQ(Context context) {
        try {
            return AppMeasurement.getInstance(context);
        } catch (NoClassDefFoundError e) {
            return null;
        }
    }

    private static void zzb(Context context, String str, Intent intent) {
        Bundle bundle = new Bundle();
        String stringExtra = intent.getStringExtra("google.c.a.c_id");
        if (stringExtra != null) {
            bundle.putString("_nmid", stringExtra);
        }
        String stringExtra2 = intent.getStringExtra("google.c.a.c_l");
        if (stringExtra2 != null) {
            bundle.putString("_nmn", stringExtra2);
        }
        String stringExtra3 = intent.getStringExtra("from");
        if (stringExtra3 == null || !stringExtra3.startsWith("/topics/")) {
            stringExtra3 = null;
        }
        if (stringExtra3 != null) {
            bundle.putString("_nt", stringExtra3);
        }
        try {
            bundle.putInt("_nmt", Integer.valueOf(intent.getStringExtra("google.c.a.ts")).intValue());
        } catch (NumberFormatException e) {
            Log.w("FirebaseMessaging", "Error while parsing timestamp in GCM event", e);
        }
        if (intent.hasExtra("google.c.a.udt")) {
            try {
                bundle.putInt("_ndt", Integer.valueOf(intent.getStringExtra("google.c.a.udt")).intValue());
            } catch (NumberFormatException e2) {
                Log.w("FirebaseMessaging", "Error while parsing use_device_time in GCM event", e2);
            }
        }
        if (Log.isLoggable("FirebaseMessaging", 3)) {
            String valueOf = String.valueOf(bundle);
            Log.d("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 22 + String.valueOf(valueOf).length()).append("Sending event=").append(str).append(" params=").append(valueOf).toString());
        }
        AppMeasurement zzaQ = zzaQ(context);
        if (zzaQ != null) {
            zzaQ.logEventInternal(AppMeasurement.FCM_ORIGIN, str, bundle);
        } else {
            Log.w("FirebaseMessaging", "Unable to log event: analytics library is missing");
        }
    }

    public static void zzg(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("google.c.a.abt");
        if (stringExtra != null) {
            zzc.zza(context, AppMeasurement.FCM_ORIGIN, Base64.decode(stringExtra, 0), new zzb(), 1);
        }
        zzb(context, "_nr", intent);
    }

    public static void zzh(Context context, Intent intent) {
        if (intent != null) {
            if ("1".equals(intent.getStringExtra("google.c.a.tc"))) {
                AppMeasurement zzaQ = zzaQ(context);
                if (Log.isLoggable("FirebaseMessaging", 3)) {
                    Log.d("FirebaseMessaging", "Received event with track-conversion=true. Setting user property and reengagement event");
                }
                if (zzaQ != null) {
                    String stringExtra = intent.getStringExtra("google.c.a.c_id");
                    zzaQ.setUserPropertyInternal(AppMeasurement.FCM_ORIGIN, AppMeasurement.UserProperty.FIREBASE_LAST_NOTIFICATION, stringExtra);
                    Bundle bundle = new Bundle();
                    bundle.putString("source", "Firebase");
                    bundle.putString(FirebaseAnalytics.Param.MEDIUM, "notification");
                    bundle.putString(FirebaseAnalytics.Param.CAMPAIGN, stringExtra);
                    zzaQ.logEventInternal(AppMeasurement.FCM_ORIGIN, "_cmp", bundle);
                } else {
                    Log.w("FirebaseMessaging", "Unable to set user property for conversion tracking:  analytics library is missing");
                }
            } else if (Log.isLoggable("FirebaseMessaging", 3)) {
                Log.d("FirebaseMessaging", "Received event with track-conversion=false. Do not set user property");
            }
        }
        zzb(context, "_no", intent);
    }

    public static void zzi(Context context, Intent intent) {
        zzb(context, "_nd", intent);
    }

    public static void zzj(Context context, Intent intent) {
        zzb(context, "_nf", intent);
    }
}
