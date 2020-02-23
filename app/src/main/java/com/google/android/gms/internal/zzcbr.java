package com.google.android.gms.internal;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.instantapps.PackageManagerWrapper;

public final class zzcbr implements PackageManagerWrapper {
    private static zzcbr zzbhs;
    private final boolean zzbht;
    private final Context zzqD;

    private zzcbr(Context context, boolean z) {
        this.zzqD = context;
        this.zzbht = z;
    }

    public static synchronized zzcbr zzi(Context context, boolean z) {
        zzcbr zzcbr;
        synchronized (zzcbr.class) {
            Context applicationContext = context.getApplicationContext();
            if (!(zzbhs != null && zzbhs.zzqD == applicationContext && zzbhs.zzbht == z)) {
                zzbhs = new zzcbr(applicationContext, z);
            }
            zzcbr = zzbhs;
        }
        return zzcbr;
    }

    public final ApplicationInfo getApplicationInfo(String str, int i) throws PackageManager.NameNotFoundException {
        if (this.zzbht) {
            try {
                return this.zzqD.getPackageManager().getApplicationInfo(str, i);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf != null) {
            try {
                ApplicationInfo applicationInfo = zzbf.getApplicationInfo(str, i);
                if (applicationInfo != null) {
                    return applicationInfo;
                }
            } catch (RemoteException e2) {
                Log.e("InstantAppsPMW", "Error getting application info", e2);
            }
        }
        throw new PackageManager.NameNotFoundException();
    }

    public final CharSequence getApplicationLabel(ApplicationInfo applicationInfo) {
        if (this.zzbht && this.zzqD.getPackageManager().getPackagesForUid(applicationInfo.uid) != null) {
            return this.zzqD.getPackageManager().getApplicationLabel(applicationInfo);
        }
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf != null) {
            try {
                return zzbf.zzdt(applicationInfo.packageName);
            } catch (RemoteException e) {
                Log.e("InstantAppsPMW", "Error getting application label", e);
            }
        }
        return null;
    }

    public final ComponentName getCallingActivity(Activity activity) {
        return new zzcbk(activity).getCallingActivity();
    }

    public final String getCallingPackage(Activity activity) {
        return new zzcbk(activity).getCallingPackage();
    }

    public final PackageInfo getPackageInfo(String str, int i) throws PackageManager.NameNotFoundException {
        if (this.zzbht) {
            try {
                return this.zzqD.getPackageManager().getPackageInfo(str, i);
            } catch (PackageManager.NameNotFoundException e) {
            }
        }
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf != null) {
            try {
                PackageInfo packageInfo = zzbf.getPackageInfo(str, i);
                if (packageInfo != null) {
                    return packageInfo;
                }
            } catch (RemoteException e2) {
                Log.e("InstantAppsPMW", "Error getting package info", e2);
            }
        }
        throw new PackageManager.NameNotFoundException();
    }

    public final String[] getPackagesForUid(int i) {
        String[] packagesForUid;
        if (this.zzbht && (packagesForUid = this.zzqD.getPackageManager().getPackagesForUid(i)) != null) {
            return packagesForUid;
        }
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf == null) {
            return null;
        }
        try {
            String zzbi = zzbf.zzbi(i);
            if (zzbi == null) {
                return null;
            }
            return new String[]{zzbi};
        } catch (RemoteException e) {
            Log.e("InstantAppsPMW", "Error getting app package for UID", e);
            return null;
        }
    }

    public final boolean isInstantApp() {
        return isInstantApp(this.zzqD.getPackageName());
    }

    public final boolean isInstantApp(int i) {
        if (Process.myUid() == i) {
            return zzbgy.zzaN(this.zzqD);
        }
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf == null) {
            return false;
        }
        try {
            return zzbf.zzbi(i) != null;
        } catch (RemoteException e) {
            Log.e("InstantAppsPMW", "Error checking if app is instant app", e);
            return false;
        }
    }

    public final boolean isInstantApp(String str) {
        zzcbp zzbf = zzcbp.zzbf(this.zzqD);
        if (zzbf != null) {
            try {
                return zzbf.isInstantApp(str);
            } catch (RemoteException e) {
                Log.e("InstantAppsPMW", "Error checking if app is instant app", e);
            }
        }
        return false;
    }
}
