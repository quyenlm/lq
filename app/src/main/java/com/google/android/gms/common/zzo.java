package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.zzbd;
import com.google.android.gms.common.util.zzj;
import com.google.android.gms.common.util.zzw;
import com.google.android.gms.internal.zzbha;
import com.tencent.imsdk.sns.api.IMFriend;
import com.tencent.midas.oversea.comm.APDataReportManager;
import java.util.concurrent.atomic.AtomicBoolean;

public class zzo {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 11020000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean zzaAo = false;
    private static boolean zzaAp = false;
    private static boolean zzaAq = false;
    private static boolean zzaAr = false;
    static final AtomicBoolean zzaAs = new AtomicBoolean();
    private static final AtomicBoolean zzaAt = new AtomicBoolean();

    zzo() {
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return zze.zzoW().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.getStatusString(i);
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    @java.lang.Deprecated
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getOpenSourceSoftwareLicenseInfo(android.content.Context r4) {
        /*
            r1 = 0
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            java.lang.String r2 = "android.resource"
            android.net.Uri$Builder r0 = r0.scheme(r2)
            java.lang.String r2 = "com.google.android.gms"
            android.net.Uri$Builder r0 = r0.authority(r2)
            java.lang.String r2 = "raw"
            android.net.Uri$Builder r0 = r0.appendPath(r2)
            java.lang.String r2 = "oss_notice"
            android.net.Uri$Builder r0 = r0.appendPath(r2)
            android.net.Uri r0 = r0.build()
            android.content.ContentResolver r2 = r4.getContentResolver()     // Catch:{ Exception -> 0x004e }
            java.io.InputStream r2 = r2.openInputStream(r0)     // Catch:{ Exception -> 0x004e }
            java.util.Scanner r0 = new java.util.Scanner     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            r0.<init>(r2)     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            java.lang.String r3 = "\\A"
            java.util.Scanner r0 = r0.useDelimiter(r3)     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            java.lang.String r0 = r0.next()     // Catch:{ NoSuchElementException -> 0x003f, all -> 0x0047 }
            if (r2 == 0) goto L_0x003e
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x003e:
            return r0
        L_0x003f:
            r0 = move-exception
            if (r2 == 0) goto L_0x0045
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x0045:
            r0 = r1
            goto L_0x003e
        L_0x0047:
            r0 = move-exception
            if (r2 == 0) goto L_0x004d
            r2.close()     // Catch:{ Exception -> 0x004e }
        L_0x004d:
            throw r0     // Catch:{ Exception -> 0x004e }
        L_0x004e:
            r0 = move-exception
            r0 = r1
            goto L_0x003e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzo.getOpenSourceSoftwareLicenseInfo(android.content.Context):java.lang.String");
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            context.getResources().getString(R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!"com.google.android.gms".equals(context.getPackageName()) && !zzaAt.get()) {
            int zzaE = zzbd.zzaE(context);
            if (zzaE == 0) {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            } else if (zzaE != GOOGLE_PLAY_SERVICES_VERSION_CODE) {
                int i = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                String valueOf = String.valueOf("com.google.android.gms.version");
                throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 290).append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ").append(i).append(" but found ").append(zzaE).append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"").append(valueOf).append("\" android:value=\"@integer/google_play_services_version\" />").toString());
            }
        }
        boolean z = !zzj.zzaH(context) && !zzj.zzaJ(context);
        PackageInfo packageInfo = null;
        if (z) {
            try {
                packageInfo = packageManager.getPackageInfo("com.android.vending", 8256);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return 9;
            }
        }
        try {
            PackageInfo packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
            zzp.zzax(context);
            if (z) {
                zzg zza = zzp.zza(packageInfo, zzj.zzaAk);
                if (zza == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                    return 9;
                }
                if (zzp.zza(packageInfo2, zza) == null) {
                    Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                    return 9;
                }
            } else if (zzp.zza(packageInfo2, zzj.zzaAk) == null) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            }
            if (packageInfo2.versionCode / 1000 < GOOGLE_PLAY_SERVICES_VERSION_CODE / 1000) {
                Log.w("GooglePlayServicesUtil", new StringBuilder(77).append("Google Play services out of date.  Requires ").append(GOOGLE_PLAY_SERVICES_VERSION_CODE).append(" but found ").append(packageInfo2.versionCode).toString());
                return 2;
            }
            ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
            if (applicationInfo == null) {
                try {
                    applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                    return 1;
                }
            }
            return !applicationInfo.enabled ? 3 : 0;
        } catch (PackageManager.NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 9:
                return true;
            default:
                return false;
        }
    }

    @Deprecated
    public static void zzah(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = zze.zzoW().isGooglePlayServicesAvailable(context);
        if (isGooglePlayServicesAvailable != 0) {
            zze.zzoW();
            Intent zza = zze.zza(context, isGooglePlayServicesAvailable, APDataReportManager.ACCOUNTINPUT_PRE);
            Log.e("GooglePlayServicesUtil", new StringBuilder(57).append("GooglePlayServices not available due to error ").append(isGooglePlayServicesAvailable).toString());
            if (zza == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", zza);
        }
    }

    @Deprecated
    public static void zzat(Context context) {
        if (!zzaAs.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    @Deprecated
    public static int zzau(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    public static boolean zzaw(Context context) {
        if (!zzaAr) {
            try {
                PackageInfo packageInfo = zzbha.zzaP(context).getPackageInfo("com.google.android.gms", 64);
                if (packageInfo != null) {
                    zzp.zzax(context);
                    if (zzp.zza(packageInfo, zzj.zzaAk[1]) != null) {
                        zzaAq = true;
                    }
                }
                zzaAq = false;
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
            } finally {
                zzaAr = true;
            }
        }
        return zzaAq || !IMFriend.FriendPrarams.USER.equals(Build.TYPE);
    }

    @TargetApi(19)
    @Deprecated
    public static boolean zzb(Context context, int i, String str) {
        return zzw.zzb(context, i, str);
    }

    @Deprecated
    public static boolean zze(Context context, int i) {
        if (i == 18) {
            return true;
        }
        if (i == 1) {
            return zzy(context, "com.google.android.gms");
        }
        return false;
    }

    @Deprecated
    public static boolean zzf(Context context, int i) {
        return zzw.zzf(context, i);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004f, code lost:
        r0 = ((android.os.UserManager) r5.getSystemService(com.tencent.imsdk.sns.api.IMFriend.FriendPrarams.USER)).getApplicationRestrictions(r5.getPackageName());
     */
    @android.annotation.TargetApi(21)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean zzy(android.content.Context r5, java.lang.String r6) {
        /*
            r1 = 1
            r2 = 0
            java.lang.String r0 = "com.google.android.gms"
            boolean r3 = r6.equals(r0)
            boolean r0 = com.google.android.gms.common.util.zzq.zzse()
            if (r0 == 0) goto L_0x0036
            android.content.pm.PackageManager r0 = r5.getPackageManager()
            android.content.pm.PackageInstaller r0 = r0.getPackageInstaller()
            java.util.List r0 = r0.getAllSessions()
            java.util.Iterator r4 = r0.iterator()
        L_0x001e:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x0036
            java.lang.Object r0 = r4.next()
            android.content.pm.PackageInstaller$SessionInfo r0 = (android.content.pm.PackageInstaller.SessionInfo) r0
            java.lang.String r0 = r0.getAppPackageName()
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x001e
            r0 = r1
        L_0x0035:
            return r0
        L_0x0036:
            android.content.pm.PackageManager r0 = r5.getPackageManager()
            r4 = 8192(0x2000, float:1.14794E-41)
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r6, r4)     // Catch:{ NameNotFoundException -> 0x0078 }
            if (r3 == 0) goto L_0x0045
            boolean r0 = r0.enabled     // Catch:{ NameNotFoundException -> 0x0078 }
            goto L_0x0035
        L_0x0045:
            boolean r0 = r0.enabled     // Catch:{ NameNotFoundException -> 0x0078 }
            if (r0 == 0) goto L_0x0076
            boolean r0 = com.google.android.gms.common.util.zzq.zzsb()     // Catch:{ NameNotFoundException -> 0x0078 }
            if (r0 == 0) goto L_0x0074
            java.lang.String r0 = "user"
            java.lang.Object r0 = r5.getSystemService(r0)     // Catch:{ NameNotFoundException -> 0x0078 }
            android.os.UserManager r0 = (android.os.UserManager) r0     // Catch:{ NameNotFoundException -> 0x0078 }
            java.lang.String r3 = r5.getPackageName()     // Catch:{ NameNotFoundException -> 0x0078 }
            android.os.Bundle r0 = r0.getApplicationRestrictions(r3)     // Catch:{ NameNotFoundException -> 0x0078 }
            if (r0 == 0) goto L_0x0074
            java.lang.String r3 = "true"
            java.lang.String r4 = "restricted_profile"
            java.lang.String r0 = r0.getString(r4)     // Catch:{ NameNotFoundException -> 0x0078 }
            boolean r0 = r3.equals(r0)     // Catch:{ NameNotFoundException -> 0x0078 }
            if (r0 == 0) goto L_0x0074
            r0 = r1
        L_0x0070:
            if (r0 != 0) goto L_0x0076
            r0 = r1
            goto L_0x0035
        L_0x0074:
            r0 = r2
            goto L_0x0070
        L_0x0076:
            r0 = r2
            goto L_0x0035
        L_0x0078:
            r0 = move-exception
            r0 = r2
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.zzo.zzy(android.content.Context, java.lang.String):boolean");
    }
}
