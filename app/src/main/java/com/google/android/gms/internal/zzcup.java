package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zzg;

final class zzcup {
    public static void zza(String str, Throwable th, Context context) {
        zzcvk.zzb(str, th);
        if (zzg.zza(context, th)) {
            zzcvk.v("Crash reported successfully.");
        } else {
            zzcvk.v("Failed to report crash");
        }
    }

    public static void zzc(String str, Context context) {
        zzcvk.e(str);
        if (zzg.zza(context, new RuntimeException(str))) {
            zzcvk.v("Crash reported successfully.");
        } else {
            zzcvk.v("Failed to report crash");
        }
    }

    public static void zzd(String str, Context context) {
        zzcvk.zzaT(str);
        if (zzg.zza(context, new RuntimeException(str))) {
            zzcvk.v("Crash reported successfully.");
        } else {
            zzcvk.v("Failed to report crash");
        }
    }
}
