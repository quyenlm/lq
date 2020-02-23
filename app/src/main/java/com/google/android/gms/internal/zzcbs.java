package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import com.google.android.gms.common.zzp;

final class zzcbs {
    static boolean zzbg(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.google.android.gms", 64);
            if (zzp.zzax(context).zza(context.getPackageManager(), packageInfo)) {
                return true;
            }
            String valueOf = String.valueOf(packageInfo.packageName);
            Log.e("InstantAppsApi", valueOf.length() != 0 ? "Incorrect signature for package ".concat(valueOf) : new String("Incorrect signature for package "));
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
