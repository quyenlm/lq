package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.WorkSource;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.internal.zzbha;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class zzx {
    private static final Method zzaJZ = zzsg();
    private static final Method zzaKa = zzsh();
    private static final Method zzaKb = zzsi();
    private static final Method zzaKc = zzsj();
    private static final Method zzaKd = zzsk();

    @Nullable
    public static WorkSource zzD(Context context, String str) {
        if (context == null || context.getPackageManager() == null) {
            return null;
        }
        try {
            ApplicationInfo applicationInfo = zzbha.zzaP(context).getApplicationInfo(str, 0);
            if (applicationInfo != null) {
                return zze(applicationInfo.uid, str);
            }
            String valueOf = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf.length() != 0 ? "Could not get applicationInfo from package: ".concat(valueOf) : new String("Could not get applicationInfo from package: "));
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            String valueOf2 = String.valueOf(str);
            Log.e("WorkSourceUtil", valueOf2.length() != 0 ? "Could not find package: ".concat(valueOf2) : new String("Could not find package: "));
            return null;
        }
    }

    private static int zza(WorkSource workSource) {
        if (zzaKb != null) {
            try {
                return ((Integer) zzaKb.invoke(workSource, new Object[0])).intValue();
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return 0;
    }

    @Nullable
    private static String zza(WorkSource workSource, int i) {
        if (zzaKd != null) {
            try {
                return (String) zzaKd.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        }
        return null;
    }

    public static boolean zzaM(Context context) {
        return (context == null || context.getPackageManager() == null || zzbha.zzaP(context).checkPermission("android.permission.UPDATE_DEVICE_STATS", context.getPackageName()) != 0) ? false : true;
    }

    public static List<String> zzb(@Nullable WorkSource workSource) {
        int zza = workSource == null ? 0 : zza(workSource);
        if (zza == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < zza; i++) {
            String zza2 = zza(workSource, i);
            if (!zzt.zzcL(zza2)) {
                arrayList.add(zza2);
            }
        }
        return arrayList;
    }

    private static WorkSource zze(int i, String str) {
        WorkSource workSource = new WorkSource();
        if (zzaKa != null) {
            if (str == null) {
                str = "";
            }
            try {
                zzaKa.invoke(workSource, new Object[]{Integer.valueOf(i), str});
            } catch (Exception e) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e);
            }
        } else if (zzaJZ != null) {
            try {
                zzaJZ.invoke(workSource, new Object[]{Integer.valueOf(i)});
            } catch (Exception e2) {
                Log.wtf("WorkSourceUtil", "Unable to assign blame through WorkSource", e2);
            }
        }
        return workSource;
    }

    private static Method zzsg() {
        try {
            return WorkSource.class.getMethod(ProductAction.ACTION_ADD, new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzsh() {
        if (!zzq.zzsb()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod(ProductAction.ACTION_ADD, new Class[]{Integer.TYPE, String.class});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzsi() {
        try {
            return WorkSource.class.getMethod("size", new Class[0]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzsj() {
        try {
            return WorkSource.class.getMethod("get", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }

    private static Method zzsk() {
        if (!zzq.zzsb()) {
            return null;
        }
        try {
            return WorkSource.class.getMethod("getName", new Class[]{Integer.TYPE});
        } catch (Exception e) {
            return null;
        }
    }
}
