package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.drive.DriveFile;
import java.util.Iterator;
import java.util.List;
import java.util.MissingFormatArgumentException;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    static zza zzbfw;
    private final Context mContext;

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    static synchronized zza zzaX(Context context) {
        zza zza;
        synchronized (zza.class) {
            if (zzbfw == null) {
                zzbfw = new zza(context);
            }
            zza = zzbfw;
        }
        return zza;
    }

    static boolean zzaY(Context context) {
        if (((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int myPid = Process.myPid();
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo next : runningAppProcesses) {
                if (next.pid == myPid) {
                    return next.importance == 100;
                }
            }
        }
        return false;
    }

    static String zze(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private final String zzf(Bundle bundle, String str) {
        String zze = zze(bundle, str);
        if (!TextUtils.isEmpty(zze)) {
            return zze;
        }
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        String zze2 = zze(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(zze2)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(zze2, "string", this.mContext.getPackageName());
        if (identifier == 0) {
            String valueOf3 = String.valueOf(str);
            String valueOf4 = String.valueOf("_loc_key");
            String valueOf5 = String.valueOf((valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).substring(6));
            Log.w("GcmNotification", new StringBuilder(String.valueOf(valueOf5).length() + 49 + String.valueOf(zze2).length()).append(valueOf5).append(" resource not found: ").append(zze2).append(" Default value will be used.").toString());
            return null;
        }
        String valueOf6 = String.valueOf(str);
        String valueOf7 = String.valueOf("_loc_args");
        String zze3 = zze(bundle, valueOf7.length() != 0 ? valueOf6.concat(valueOf7) : new String(valueOf6));
        if (TextUtils.isEmpty(zze3)) {
            return resources.getString(identifier);
        }
        try {
            JSONArray jSONArray = new JSONArray(zze3);
            Object[] objArr = new String[jSONArray.length()];
            for (int i = 0; i < objArr.length; i++) {
                objArr[i] = jSONArray.opt(i);
            }
            return resources.getString(identifier, objArr);
        } catch (JSONException e) {
            String valueOf8 = String.valueOf(str);
            String valueOf9 = String.valueOf("_loc_args");
            String valueOf10 = String.valueOf((valueOf9.length() != 0 ? valueOf8.concat(valueOf9) : new String(valueOf8)).substring(6));
            Log.w("GcmNotification", new StringBuilder(String.valueOf(valueOf10).length() + 41 + String.valueOf(zze3).length()).append("Malformed ").append(valueOf10).append(": ").append(zze3).append("  Default value will be used.").toString());
            return null;
        } catch (MissingFormatArgumentException e2) {
            Log.w("GcmNotification", new StringBuilder(String.valueOf(zze2).length() + 58 + String.valueOf(zze3).length()).append("Missing format argument for ").append(zze2).append(": ").append(zze3).append(" Default value will be used.").toString(), e2);
            return null;
        }
    }

    static void zzu(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String string = bundle.getString(str);
            if (str.startsWith("gcm.notification.")) {
                str = str.replace("gcm.notification.", "gcm.n.");
            }
            if (str.startsWith("gcm.n.")) {
                if (!"gcm.n.e".equals(str)) {
                    bundle2.putString(str.substring(6), string);
                }
                it.remove();
            }
        }
        String string2 = bundle2.getString("sound2");
        if (string2 != null) {
            bundle2.remove("sound2");
            bundle2.putString("sound", string2);
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle("notification", bundle2);
        }
    }

    private final PendingIntent zzw(Bundle bundle) {
        Intent intent;
        String zze = zze(bundle, "gcm.n.click_action");
        if (!TextUtils.isEmpty(zze)) {
            Intent intent2 = new Intent(zze);
            intent2.setPackage(this.mContext.getPackageName());
            intent2.setFlags(DriveFile.MODE_READ_ONLY);
            intent = intent2;
        } else {
            Intent launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
            if (launchIntentForPackage == null) {
                Log.w("GcmNotification", "No activity found to launch app");
                return null;
            }
            intent = launchIntentForPackage;
        }
        Bundle bundle2 = new Bundle(bundle);
        GcmListenerService.zzt(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, (int) SystemClock.uptimeMillis(), intent, 1073741824);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0059  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzv(android.os.Bundle r11) {
        /*
            r10 = this;
            r9 = 1
            java.lang.String r0 = "gcm.n.title"
            java.lang.String r2 = r10.zzf(r11, r0)
            java.lang.String r0 = "gcm.n.body"
            java.lang.String r3 = r10.zzf(r11, r0)
            java.lang.String r0 = "gcm.n.icon"
            java.lang.String r1 = zze(r11, r0)
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 != 0) goto L_0x00fa
            android.content.Context r0 = r10.mContext
            android.content.res.Resources r4 = r0.getResources()
            java.lang.String r0 = "drawable"
            android.content.Context r5 = r10.mContext
            java.lang.String r5 = r5.getPackageName()
            int r0 = r4.getIdentifier(r1, r0, r5)
            if (r0 == 0) goto L_0x00c4
        L_0x002d:
            java.lang.String r1 = "gcm.n.color"
            java.lang.String r4 = zze(r11, r1)
            java.lang.String r1 = "gcm.n.sound2"
            java.lang.String r1 = zze(r11, r1)
            boolean r5 = android.text.TextUtils.isEmpty(r1)
            if (r5 == 0) goto L_0x0109
            r1 = 0
        L_0x0040:
            android.app.PendingIntent r5 = r10.zzw(r11)
            android.support.v4.app.NotificationCompat$Builder r6 = new android.support.v4.app.NotificationCompat$Builder
            android.content.Context r7 = r10.mContext
            r6.<init>(r7)
            android.support.v4.app.NotificationCompat$Builder r6 = r6.setAutoCancel(r9)
            android.support.v4.app.NotificationCompat$Builder r0 = r6.setSmallIcon(r0)
            boolean r6 = android.text.TextUtils.isEmpty(r2)
            if (r6 != 0) goto L_0x0179
            r0.setContentTitle(r2)
        L_0x005c:
            boolean r2 = android.text.TextUtils.isEmpty(r3)
            if (r2 != 0) goto L_0x0065
            r0.setContentText(r3)
        L_0x0065:
            boolean r2 = android.text.TextUtils.isEmpty(r4)
            if (r2 != 0) goto L_0x0072
            int r2 = android.graphics.Color.parseColor(r4)
            r0.setColor(r2)
        L_0x0072:
            if (r1 == 0) goto L_0x0077
            r0.setSound(r1)
        L_0x0077:
            if (r5 == 0) goto L_0x007c
            r0.setContentIntent(r5)
        L_0x007c:
            android.app.Notification r2 = r0.build()
            java.lang.String r0 = "gcm.n.tag"
            java.lang.String r1 = zze(r11, r0)
            java.lang.String r0 = "GcmNotification"
            r3 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r3)
            if (r0 == 0) goto L_0x0096
            java.lang.String r0 = "GcmNotification"
            java.lang.String r3 = "Showing notification"
            android.util.Log.d(r0, r3)
        L_0x0096:
            android.content.Context r0 = r10.mContext
            java.lang.String r3 = "notification"
            java.lang.Object r0 = r0.getSystemService(r3)
            android.app.NotificationManager r0 = (android.app.NotificationManager) r0
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 == 0) goto L_0x00bf
            long r4 = android.os.SystemClock.uptimeMillis()
            r1 = 37
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r1)
            java.lang.String r1 = "GCM-Notification:"
            java.lang.StringBuilder r1 = r3.append(r1)
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
        L_0x00bf:
            r3 = 0
            r0.notify(r1, r3, r2)
            return r9
        L_0x00c4:
            java.lang.String r0 = "mipmap"
            android.content.Context r5 = r10.mContext
            java.lang.String r5 = r5.getPackageName()
            int r0 = r4.getIdentifier(r1, r0, r5)
            if (r0 != 0) goto L_0x002d
            java.lang.String r0 = "GcmNotification"
            java.lang.String r4 = java.lang.String.valueOf(r1)
            int r4 = r4.length()
            int r4 = r4 + 57
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Icon resource "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r1 = r4.append(r1)
            java.lang.String r4 = " not found. Notification will use app icon."
            java.lang.StringBuilder r1 = r1.append(r4)
            java.lang.String r1 = r1.toString()
            android.util.Log.w(r0, r1)
        L_0x00fa:
            android.content.Context r0 = r10.mContext
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()
            int r0 = r0.icon
            if (r0 != 0) goto L_0x002d
            r0 = 17301651(0x1080093, float:2.4979667E-38)
            goto L_0x002d
        L_0x0109:
            java.lang.String r5 = "default"
            boolean r5 = r5.equals(r1)
            if (r5 != 0) goto L_0x0172
            android.content.Context r5 = r10.mContext
            android.content.res.Resources r5 = r5.getResources()
            java.lang.String r6 = "raw"
            android.content.Context r7 = r10.mContext
            java.lang.String r7 = r7.getPackageName()
            int r5 = r5.getIdentifier(r1, r6, r7)
            if (r5 == 0) goto L_0x0172
            java.lang.String r5 = "android.resource://"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            android.content.Context r6 = r10.mContext
            java.lang.String r6 = r6.getPackageName()
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r7 = java.lang.String.valueOf(r5)
            int r7 = r7.length()
            int r7 = r7 + 5
            java.lang.String r8 = java.lang.String.valueOf(r6)
            int r8 = r8.length()
            int r7 = r7 + r8
            java.lang.String r8 = java.lang.String.valueOf(r1)
            int r8 = r8.length()
            int r7 = r7 + r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r7)
            java.lang.StringBuilder r5 = r8.append(r5)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "/raw/"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r1 = r5.append(r1)
            java.lang.String r1 = r1.toString()
            android.net.Uri r1 = android.net.Uri.parse(r1)
            goto L_0x0040
        L_0x0172:
            r1 = 2
            android.net.Uri r1 = android.media.RingtoneManager.getDefaultUri(r1)
            goto L_0x0040
        L_0x0179:
            android.content.Context r2 = r10.mContext
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()
            android.content.Context r6 = r10.mContext
            android.content.pm.PackageManager r6 = r6.getPackageManager()
            java.lang.CharSequence r2 = r2.loadLabel(r6)
            r0.setContentTitle(r2)
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.gcm.zza.zzv(android.os.Bundle):boolean");
    }
}
