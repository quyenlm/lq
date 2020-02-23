package com.google.android.gms.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.google.android.gms.drive.DriveFile;
import java.util.ArrayList;
import java.util.List;

public final class zzrr {
    private final zzaka zzJH;

    public zzrr(zzaka zzaka) {
        this.zzJH = zzaka;
    }

    private static Intent zza(Intent intent, ResolveInfo resolveInfo) {
        Intent intent2 = new Intent(intent);
        intent2.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
        return intent2;
    }

    private static ResolveInfo zza(Context context, Intent intent) {
        return zza(context, intent, new ArrayList());
    }

    private static ResolveInfo zza(Context context, Intent intent, ArrayList<ResolveInfo> arrayList) {
        ResolveInfo resolveInfo;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 65536);
        if (queryIntentActivities != null && resolveActivity != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= queryIntentActivities.size()) {
                    break;
                }
                ResolveInfo resolveInfo2 = queryIntentActivities.get(i2);
                if (resolveActivity != null && resolveActivity.activityInfo.name.equals(resolveInfo2.activityInfo.name)) {
                    resolveInfo = resolveActivity;
                    break;
                }
                i = i2 + 1;
            }
        }
        resolveInfo = null;
        arrayList.addAll(queryIntentActivities);
        return resolveInfo;
    }

    private static Intent zzf(Uri uri) {
        if (uri == null) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        intent.setData(uri);
        intent.setAction("android.intent.action.VIEW");
        return intent;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00c4 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.content.Intent zza(android.content.Context r13, java.util.Map<java.lang.String, java.lang.String> r14) {
        /*
            r12 = this;
            r3 = 0
            r4 = 0
            java.lang.String r0 = "activity"
            java.lang.Object r0 = r13.getSystemService(r0)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            java.lang.String r1 = "u"
            java.lang.Object r1 = r14.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L_0x0019
        L_0x0018:
            return r3
        L_0x0019:
            com.google.android.gms.internal.zzaka r2 = r12.zzJH
            if (r2 == 0) goto L_0x0026
            com.google.android.gms.ads.internal.zzbs.zzbz()
            com.google.android.gms.internal.zzaka r2 = r12.zzJH
            java.lang.String r1 = com.google.android.gms.internal.zzagz.zzb((com.google.android.gms.internal.zzaka) r2, (java.lang.String) r1)
        L_0x0026:
            android.net.Uri r5 = android.net.Uri.parse(r1)
            java.lang.String r1 = "use_first_package"
            java.lang.Object r1 = r14.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r7 = java.lang.Boolean.parseBoolean(r1)
            java.lang.String r1 = "use_running_process"
            java.lang.Object r1 = r14.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r6 = java.lang.Boolean.parseBoolean(r1)
            java.lang.String r1 = "use_custom_tabs"
            java.lang.Object r1 = r14.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r1 = java.lang.Boolean.parseBoolean(r1)
            if (r1 != 0) goto L_0x0062
            com.google.android.gms.internal.zzme<java.lang.Boolean> r1 = com.google.android.gms.internal.zzmo.zzGp
            com.google.android.gms.internal.zzmm r2 = com.google.android.gms.ads.internal.zzbs.zzbL()
            java.lang.Object r1 = r2.zzd(r1)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x00a6
        L_0x0062:
            r1 = 1
            r2 = r1
        L_0x0064:
            java.lang.String r1 = "http"
            java.lang.String r8 = r5.getScheme()
            boolean r1 = r1.equalsIgnoreCase(r8)
            if (r1 == 0) goto L_0x00a8
            android.net.Uri$Builder r1 = r5.buildUpon()
            java.lang.String r3 = "https"
            android.net.Uri$Builder r1 = r1.scheme(r3)
            android.net.Uri r1 = r1.build()
            r3 = r1
        L_0x007f:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.content.Intent r5 = zzf(r5)
            android.content.Intent r3 = zzf(r3)
            if (r2 == 0) goto L_0x009a
            com.google.android.gms.ads.internal.zzbs.zzbz()
            com.google.android.gms.internal.zzagz.zzc(r13, r5)
            com.google.android.gms.ads.internal.zzbs.zzbz()
            com.google.android.gms.internal.zzagz.zzc(r13, r3)
        L_0x009a:
            android.content.pm.ResolveInfo r2 = zza(r13, r5, r1)
            if (r2 == 0) goto L_0x00c4
            android.content.Intent r3 = zza((android.content.Intent) r5, (android.content.pm.ResolveInfo) r2)
            goto L_0x0018
        L_0x00a6:
            r2 = r4
            goto L_0x0064
        L_0x00a8:
            java.lang.String r1 = "https"
            java.lang.String r8 = r5.getScheme()
            boolean r1 = r1.equalsIgnoreCase(r8)
            if (r1 == 0) goto L_0x007f
            android.net.Uri$Builder r1 = r5.buildUpon()
            java.lang.String r3 = "http"
            android.net.Uri$Builder r1 = r1.scheme(r3)
            android.net.Uri r1 = r1.build()
            r3 = r1
            goto L_0x007f
        L_0x00c4:
            if (r3 == 0) goto L_0x00d6
            android.content.pm.ResolveInfo r2 = zza((android.content.Context) r13, (android.content.Intent) r3)
            if (r2 == 0) goto L_0x00d6
            android.content.Intent r3 = zza((android.content.Intent) r5, (android.content.pm.ResolveInfo) r2)
            android.content.pm.ResolveInfo r2 = zza((android.content.Context) r13, (android.content.Intent) r3)
            if (r2 != 0) goto L_0x0018
        L_0x00d6:
            int r2 = r1.size()
            if (r2 != 0) goto L_0x00df
            r3 = r5
            goto L_0x0018
        L_0x00df:
            if (r6 == 0) goto L_0x011f
            if (r0 == 0) goto L_0x011f
            java.util.List r8 = r0.getRunningAppProcesses()
            if (r8 == 0) goto L_0x011f
            r0 = r1
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r9 = r0.size()
            r3 = r4
        L_0x00f1:
            if (r3 >= r9) goto L_0x011f
            java.lang.Object r2 = r0.get(r3)
            int r6 = r3 + 1
            android.content.pm.ResolveInfo r2 = (android.content.pm.ResolveInfo) r2
            java.util.Iterator r10 = r8.iterator()
        L_0x00ff:
            boolean r3 = r10.hasNext()
            if (r3 == 0) goto L_0x011d
            java.lang.Object r3 = r10.next()
            android.app.ActivityManager$RunningAppProcessInfo r3 = (android.app.ActivityManager.RunningAppProcessInfo) r3
            java.lang.String r3 = r3.processName
            android.content.pm.ActivityInfo r11 = r2.activityInfo
            java.lang.String r11 = r11.packageName
            boolean r3 = r3.equals(r11)
            if (r3 == 0) goto L_0x00ff
            android.content.Intent r3 = zza((android.content.Intent) r5, (android.content.pm.ResolveInfo) r2)
            goto L_0x0018
        L_0x011d:
            r3 = r6
            goto L_0x00f1
        L_0x011f:
            if (r7 == 0) goto L_0x012d
            java.lang.Object r0 = r1.get(r4)
            android.content.pm.ResolveInfo r0 = (android.content.pm.ResolveInfo) r0
            android.content.Intent r3 = zza((android.content.Intent) r5, (android.content.pm.ResolveInfo) r0)
            goto L_0x0018
        L_0x012d:
            r3 = r5
            goto L_0x0018
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrr.zza(android.content.Context, java.util.Map):android.content.Intent");
    }
}
