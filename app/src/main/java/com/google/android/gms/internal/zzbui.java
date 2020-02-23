package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.util.zzj;

public final class zzbui {
    private static int zzaVq = -1;

    public static int zzaU(Context context) {
        boolean z = false;
        if (zzaVq == -1) {
            if (zzj.zzaG(context)) {
                zzaVq = 3;
            } else {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback")) {
                    zzaVq = 0;
                } else {
                    if (zzj.zza(context.getResources()) && !zzaV(context)) {
                        zzaVq = 2;
                    } else {
                        if (!TextUtils.isEmpty(Build.PRODUCT) && Build.PRODUCT.startsWith("glass_")) {
                            z = true;
                        }
                        if (z) {
                            zzaVq = 6;
                        } else {
                            zzaVq = 1;
                        }
                    }
                }
            }
        }
        return zzaVq;
    }

    private static boolean zzaV(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService("phone")).getPhoneType() != 0;
        } catch (Resources.NotFoundException e) {
            Log.wtf("Fitness", "Unable to determine type of device, assuming phone.", e);
            return true;
        }
    }
}
