package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzbha;

public final class zzd {
    public static int zzA(Context context, String str) {
        Bundle bundle;
        PackageInfo zzB = zzB(context, str);
        if (zzB == null || zzB.applicationInfo == null || (bundle = zzB.applicationInfo.metaData) == null) {
            return -1;
        }
        return bundle.getInt("com.google.android.gms.version", -1);
    }

    @Nullable
    private static PackageInfo zzB(Context context, String str) {
        try {
            return zzbha.zzaP(context).getPackageInfo(str, 128);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean zzC(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            return (zzbha.zzaP(context).getApplicationInfo(str, 0).flags & 2097152) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
