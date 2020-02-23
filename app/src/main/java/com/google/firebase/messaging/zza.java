package com.google.firebase.messaging;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.R;
import com.google.android.gms.common.util.zzq;
import com.google.android.gms.drive.DriveFile;
import com.tencent.smtt.sdk.TbsListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.MissingFormatArgumentException;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;

final class zza {
    private static zza zzckS;
    private final Context mContext;
    private Bundle zzaFE;
    private Method zzckT;
    private Method zzckU;
    private final AtomicInteger zzckV = new AtomicInteger((int) SystemClock.elapsedRealtime());

    private zza(Context context) {
        this.mContext = context.getApplicationContext();
    }

    static boolean zzG(Bundle bundle) {
        return "1".equals(zze(bundle, "gcm.n.e")) || zze(bundle, "gcm.n.icon") != null;
    }

    @Nullable
    static Uri zzH(@NonNull Bundle bundle) {
        String zze = zze(bundle, "gcm.n.link_android");
        if (TextUtils.isEmpty(zze)) {
            zze = zze(bundle, "gcm.n.link");
        }
        if (!TextUtils.isEmpty(zze)) {
            return Uri.parse(zze);
        }
        return null;
    }

    static String zzI(Bundle bundle) {
        String zze = zze(bundle, "gcm.n.sound2");
        return TextUtils.isEmpty(zze) ? zze(bundle, "gcm.n.sound") : zze;
    }

    private final Bundle zzKb() {
        if (this.zzaFE != null) {
            return this.zzaFE;
        }
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
        } catch (PackageManager.NameNotFoundException e) {
        }
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return Bundle.EMPTY;
        }
        this.zzaFE = applicationInfo.metaData;
        return this.zzaFE;
    }

    @TargetApi(26)
    private final Notification zza(CharSequence charSequence, String str, int i, Integer num, Uri uri, PendingIntent pendingIntent, PendingIntent pendingIntent2, String str2) {
        Notification.Builder smallIcon = new Notification.Builder(this.mContext).setAutoCancel(true).setSmallIcon(i);
        if (!TextUtils.isEmpty(charSequence)) {
            smallIcon.setContentTitle(charSequence);
        }
        if (!TextUtils.isEmpty(str)) {
            smallIcon.setContentText(str);
            smallIcon.setStyle(new Notification.BigTextStyle().bigText(str));
        }
        if (num != null) {
            smallIcon.setColor(num.intValue());
        }
        if (uri != null) {
            smallIcon.setSound(uri);
        }
        if (pendingIntent != null) {
            smallIcon.setContentIntent(pendingIntent);
        }
        if (pendingIntent2 != null) {
            smallIcon.setDeleteIntent(pendingIntent2);
        }
        if (str2 != null) {
            try {
                if (this.zzckT == null) {
                    this.zzckT = smallIcon.getClass().getMethod("setChannel", new Class[]{String.class});
                }
                this.zzckT.invoke(smallIcon, new Object[]{str2});
            } catch (NoSuchMethodException e) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
            } catch (IllegalAccessException e2) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
            } catch (InvocationTargetException e3) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e3);
            } catch (SecurityException e4) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e4);
            } catch (IllegalArgumentException e5) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e5);
            } catch (LinkageError e6) {
                Log.e("FirebaseMessaging", "Error while setting the notification channel", e6);
            }
        }
        return smallIcon.build();
    }

    private static void zza(Intent intent, Bundle bundle) {
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    static synchronized zza zzbM(Context context) {
        zza zza;
        synchronized (zza.class) {
            if (zzckS == null) {
                zzckS = new zza(context);
            }
            zza = zzckS;
        }
        return zza;
    }

    static String zze(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    static String zzh(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_key");
        return zze(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
    }

    private final Integer zzhr(String str) {
        if (Build.VERSION.SDK_INT < 21) {
            return null;
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                return Integer.valueOf(Color.parseColor(str));
            } catch (IllegalArgumentException e) {
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + 54).append("Color ").append(str).append(" not valid. Notification will use default color.").toString());
            }
        }
        int i = zzKb().getInt("com.google.firebase.messaging.default_notification_color", 0);
        if (i == 0) {
            return null;
        }
        try {
            return Integer.valueOf(ContextCompat.getColor(this.mContext, i));
        } catch (Resources.NotFoundException e2) {
            Log.w("FirebaseMessaging", "Cannot find the color resource referenced in AndroidManifest.");
            return null;
        }
    }

    @TargetApi(26)
    private final String zzhs(String str) {
        if (!zzq.isAtLeastO()) {
            return null;
        }
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        try {
            if (this.zzckU == null) {
                this.zzckU = notificationManager.getClass().getMethod("getNotificationChannel", new Class[]{String.class});
            }
            if (!TextUtils.isEmpty(str)) {
                if (this.zzckU.invoke(notificationManager, new Object[]{str}) != null) {
                    return str;
                }
                Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(str).length() + TbsListener.ErrorCode.DOWNLOAD_HAS_COPY_TBS_ERROR).append("Notification Channel requested (").append(str).append(") has not been created by the app. Manifest configuration, or default, value will be used.").toString());
            }
            String string = zzKb().getString("com.google.firebase.messaging.default_notification_channel_id");
            if (!TextUtils.isEmpty(string)) {
                if (this.zzckU.invoke(notificationManager, new Object[]{string}) != null) {
                    return string;
                }
                Log.w("FirebaseMessaging", "Notification Channel set in AndroidManifest.xml has not been created by the app. Default value will be used.");
            } else {
                Log.w("FirebaseMessaging", "Missing Default Notification Channel metadata in AndroidManifest. Default value will be used.");
            }
            if (this.zzckU.invoke(notificationManager, new Object[]{"fcm_fallback_notification_channel"}) == null) {
                Class<?> cls = Class.forName("android.app.NotificationChannel");
                Object newInstance = cls.getConstructor(new Class[]{String.class, CharSequence.class, Integer.TYPE}).newInstance(new Object[]{"fcm_fallback_notification_channel", this.mContext.getString(R.string.fcm_fallback_notification_channel_label), 3});
                notificationManager.getClass().getMethod("createNotificationChannel", new Class[]{cls}).invoke(notificationManager, new Object[]{newInstance});
            }
            return "fcm_fallback_notification_channel";
        } catch (InstantiationException e) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e);
            return null;
        } catch (InvocationTargetException e2) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e2);
            return null;
        } catch (NoSuchMethodException e3) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e3);
            return null;
        } catch (IllegalAccessException e4) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e4);
            return null;
        } catch (ClassNotFoundException e5) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e5);
            return null;
        } catch (SecurityException e6) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e6);
            return null;
        } catch (IllegalArgumentException e7) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e7);
            return null;
        } catch (LinkageError e8) {
            Log.e("FirebaseMessaging", "Error while setting the notification channel", e8);
            return null;
        }
    }

    static Object[] zzi(Bundle bundle, String str) {
        String valueOf = String.valueOf(str);
        String valueOf2 = String.valueOf("_loc_args");
        String zze = zze(bundle, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (TextUtils.isEmpty(zze)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray(zze);
            Object[] objArr = new String[jSONArray.length()];
            for (int i = 0; i < objArr.length; i++) {
                objArr[i] = jSONArray.opt(i);
            }
            return objArr;
        } catch (JSONException e) {
            String valueOf3 = String.valueOf(str);
            String valueOf4 = String.valueOf("_loc_args");
            String valueOf5 = String.valueOf((valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3)).substring(6));
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(valueOf5).length() + 41 + String.valueOf(zze).length()).append("Malformed ").append(valueOf5).append(": ").append(zze).append("  Default value will be used.").toString());
            return null;
        }
    }

    private final String zzj(Bundle bundle, String str) {
        String zze = zze(bundle, str);
        if (!TextUtils.isEmpty(zze)) {
            return zze;
        }
        String zzh = zzh(bundle, str);
        if (TextUtils.isEmpty(zzh)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(zzh, "string", this.mContext.getPackageName());
        if (identifier == 0) {
            String valueOf = String.valueOf(str);
            String valueOf2 = String.valueOf("_loc_key");
            String valueOf3 = String.valueOf((valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).substring(6));
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(valueOf3).length() + 49 + String.valueOf(zzh).length()).append(valueOf3).append(" resource not found: ").append(zzh).append(" Default value will be used.").toString());
            return null;
        }
        Object[] zzi = zzi(bundle, str);
        if (zzi == null) {
            return resources.getString(identifier);
        }
        try {
            return resources.getString(identifier, zzi);
        } catch (MissingFormatArgumentException e) {
            String valueOf4 = String.valueOf(Arrays.toString(zzi));
            Log.w("FirebaseMessaging", new StringBuilder(String.valueOf(zzh).length() + 58 + String.valueOf(valueOf4).length()).append("Missing format argument for ").append(zzh).append(": ").append(valueOf4).append(" Default value will be used.").toString(), e);
            return null;
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
            Uri zzH = zzH(bundle);
            if (zzH != null) {
                Intent intent3 = new Intent("android.intent.action.VIEW");
                intent3.setPackage(this.mContext.getPackageName());
                intent3.setData(zzH);
                intent = intent3;
            } else {
                Intent launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
                if (launchIntentForPackage == null) {
                    Log.w("FirebaseMessaging", "No activity found to launch app");
                }
                intent = launchIntentForPackage;
            }
        }
        if (intent == null) {
            return null;
        }
        intent.addFlags(67108864);
        Bundle bundle2 = new Bundle(bundle);
        FirebaseMessagingService.zzt(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, this.zzckV.incrementAndGet(), intent, 1073741824);
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x012a  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0141  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01b2  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0222  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzv(android.os.Bundle r14) {
        /*
            r13 = this;
            r7 = 0
            r12 = 1073741824(0x40000000, float:2.0)
            r9 = 1
            r10 = 0
            java.lang.String r0 = "1"
            java.lang.String r1 = "gcm.n.noui"
            java.lang.String r1 = zze(r14, r1)
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0015
            r0 = r9
        L_0x0014:
            return r0
        L_0x0015:
            android.content.Context r0 = r13.mContext
            java.lang.String r1 = "keyguard"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.app.KeyguardManager r0 = (android.app.KeyguardManager) r0
            boolean r0 = r0.inKeyguardRestrictedInputMode()
            if (r0 != 0) goto L_0x0065
            boolean r0 = com.google.android.gms.common.util.zzq.zzse()
            if (r0 != 0) goto L_0x0030
            r0 = 10
            android.os.SystemClock.sleep(r0)
        L_0x0030:
            int r1 = android.os.Process.myPid()
            android.content.Context r0 = r13.mContext
            java.lang.String r2 = "activity"
            java.lang.Object r0 = r0.getSystemService(r2)
            android.app.ActivityManager r0 = (android.app.ActivityManager) r0
            java.util.List r0 = r0.getRunningAppProcesses()
            if (r0 == 0) goto L_0x0065
            java.util.Iterator r2 = r0.iterator()
        L_0x0048:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0065
            java.lang.Object r0 = r2.next()
            android.app.ActivityManager$RunningAppProcessInfo r0 = (android.app.ActivityManager.RunningAppProcessInfo) r0
            int r3 = r0.pid
            if (r3 != r1) goto L_0x0048
            int r0 = r0.importance
            r1 = 100
            if (r0 != r1) goto L_0x0063
            r0 = r9
        L_0x005f:
            if (r0 == 0) goto L_0x0067
            r0 = r10
            goto L_0x0014
        L_0x0063:
            r0 = r10
            goto L_0x005f
        L_0x0065:
            r0 = r10
            goto L_0x005f
        L_0x0067:
            java.lang.String r0 = "gcm.n.title"
            java.lang.String r1 = r13.zzj(r14, r0)
            boolean r0 = android.text.TextUtils.isEmpty(r1)
            if (r0 == 0) goto L_0x0083
            android.content.Context r0 = r13.mContext
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()
            android.content.Context r1 = r13.mContext
            android.content.pm.PackageManager r1 = r1.getPackageManager()
            java.lang.CharSequence r1 = r0.loadLabel(r1)
        L_0x0083:
            java.lang.String r0 = "gcm.n.body"
            java.lang.String r2 = r13.zzj(r14, r0)
            java.lang.String r0 = "gcm.n.icon"
            java.lang.String r0 = zze(r14, r0)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 != 0) goto L_0x0196
            android.content.Context r3 = r13.mContext
            android.content.res.Resources r4 = r3.getResources()
            java.lang.String r3 = "drawable"
            android.content.Context r5 = r13.mContext
            java.lang.String r5 = r5.getPackageName()
            int r3 = r4.getIdentifier(r0, r3, r5)
            if (r3 == 0) goto L_0x0160
        L_0x00a9:
            java.lang.String r0 = "gcm.n.color"
            java.lang.String r0 = zze(r14, r0)
            java.lang.Integer r4 = r13.zzhr(r0)
            java.lang.String r0 = zzI(r14)
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 == 0) goto L_0x01b2
            r5 = r7
        L_0x00be:
            android.app.PendingIntent r6 = r13.zzw(r14)
            boolean r0 = com.google.firebase.messaging.FirebaseMessagingService.zzJ(r14)
            if (r0 == 0) goto L_0x00f9
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r7 = "com.google.firebase.messaging.NOTIFICATION_OPEN"
            r0.<init>(r7)
            zza(r0, r14)
            java.lang.String r7 = "pending_intent"
            r0.putExtra(r7, r6)
            android.content.Context r6 = r13.mContext
            java.util.concurrent.atomic.AtomicInteger r7 = r13.zzckV
            int r7 = r7.incrementAndGet()
            android.app.PendingIntent r6 = com.google.firebase.iid.zzq.zzb(r6, r7, r0, r12)
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r7 = "com.google.firebase.messaging.NOTIFICATION_DISMISS"
            r0.<init>(r7)
            zza(r0, r14)
            android.content.Context r7 = r13.mContext
            java.util.concurrent.atomic.AtomicInteger r8 = r13.zzckV
            int r8 = r8.incrementAndGet()
            android.app.PendingIntent r7 = com.google.firebase.iid.zzq.zzb(r7, r8, r0, r12)
        L_0x00f9:
            boolean r0 = com.google.android.gms.common.util.zzq.isAtLeastO()
            if (r0 == 0) goto L_0x0222
            android.content.Context r0 = r13.mContext
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()
            int r0 = r0.targetSdkVersion
            r8 = 25
            if (r0 <= r8) goto L_0x0222
            java.lang.String r0 = "gcm.n.android_channel_id"
            java.lang.String r0 = zze(r14, r0)
            java.lang.String r8 = r13.zzhs(r0)
            r0 = r13
            android.app.Notification r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8)
            r1 = r0
        L_0x011b:
            java.lang.String r0 = "gcm.n.tag"
            java.lang.String r2 = zze(r14, r0)
            java.lang.String r0 = "FirebaseMessaging"
            r3 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r3)
            if (r0 == 0) goto L_0x0131
            java.lang.String r0 = "FirebaseMessaging"
            java.lang.String r3 = "Showing notification"
            android.util.Log.d(r0, r3)
        L_0x0131:
            android.content.Context r0 = r13.mContext
            java.lang.String r3 = "notification"
            java.lang.Object r0 = r0.getSystemService(r3)
            android.app.NotificationManager r0 = (android.app.NotificationManager) r0
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L_0x015a
            long r2 = android.os.SystemClock.uptimeMillis()
            r4 = 37
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "FCM-Notification:"
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
        L_0x015a:
            r0.notify(r2, r10, r1)
            r0 = r9
            goto L_0x0014
        L_0x0160:
            java.lang.String r3 = "mipmap"
            android.content.Context r5 = r13.mContext
            java.lang.String r5 = r5.getPackageName()
            int r3 = r4.getIdentifier(r0, r3, r5)
            if (r3 != 0) goto L_0x00a9
            java.lang.String r3 = "FirebaseMessaging"
            java.lang.String r4 = java.lang.String.valueOf(r0)
            int r4 = r4.length()
            int r4 = r4 + 61
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>(r4)
            java.lang.String r4 = "Icon resource "
            java.lang.StringBuilder r4 = r5.append(r4)
            java.lang.StringBuilder r0 = r4.append(r0)
            java.lang.String r4 = " not found. Notification will use default icon."
            java.lang.StringBuilder r0 = r0.append(r4)
            java.lang.String r0 = r0.toString()
            android.util.Log.w(r3, r0)
        L_0x0196:
            android.os.Bundle r0 = r13.zzKb()
            java.lang.String r3 = "com.google.firebase.messaging.default_notification_icon"
            int r0 = r0.getInt(r3, r10)
            if (r0 != 0) goto L_0x01aa
            android.content.Context r0 = r13.mContext
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo()
            int r0 = r0.icon
        L_0x01aa:
            if (r0 != 0) goto L_0x01af
            r0 = 17301651(0x1080093, float:2.4979667E-38)
        L_0x01af:
            r3 = r0
            goto L_0x00a9
        L_0x01b2:
            java.lang.String r5 = "default"
            boolean r5 = r5.equals(r0)
            if (r5 != 0) goto L_0x021b
            android.content.Context r5 = r13.mContext
            android.content.res.Resources r5 = r5.getResources()
            java.lang.String r6 = "raw"
            android.content.Context r8 = r13.mContext
            java.lang.String r8 = r8.getPackageName()
            int r5 = r5.getIdentifier(r0, r6, r8)
            if (r5 == 0) goto L_0x021b
            java.lang.String r5 = "android.resource://"
            java.lang.String r5 = java.lang.String.valueOf(r5)
            android.content.Context r6 = r13.mContext
            java.lang.String r6 = r6.getPackageName()
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.lang.String r8 = java.lang.String.valueOf(r5)
            int r8 = r8.length()
            int r8 = r8 + 5
            java.lang.String r11 = java.lang.String.valueOf(r6)
            int r11 = r11.length()
            int r8 = r8 + r11
            java.lang.String r11 = java.lang.String.valueOf(r0)
            int r11 = r11.length()
            int r8 = r8 + r11
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>(r8)
            java.lang.StringBuilder r5 = r11.append(r5)
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.String r6 = "/raw/"
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r0 = r5.append(r0)
            java.lang.String r0 = r0.toString()
            android.net.Uri r5 = android.net.Uri.parse(r0)
            goto L_0x00be
        L_0x021b:
            r0 = 2
            android.net.Uri r5 = android.media.RingtoneManager.getDefaultUri(r0)
            goto L_0x00be
        L_0x0222:
            android.support.v4.app.NotificationCompat$Builder r0 = new android.support.v4.app.NotificationCompat$Builder
            android.content.Context r8 = r13.mContext
            r0.<init>(r8)
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setAutoCancel(r9)
            android.support.v4.app.NotificationCompat$Builder r0 = r0.setSmallIcon(r3)
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x023a
            r0.setContentTitle(r1)
        L_0x023a:
            boolean r1 = android.text.TextUtils.isEmpty(r2)
            if (r1 != 0) goto L_0x024f
            r0.setContentText(r2)
            android.support.v4.app.NotificationCompat$BigTextStyle r1 = new android.support.v4.app.NotificationCompat$BigTextStyle
            r1.<init>()
            android.support.v4.app.NotificationCompat$BigTextStyle r1 = r1.bigText(r2)
            r0.setStyle(r1)
        L_0x024f:
            if (r4 == 0) goto L_0x0258
            int r1 = r4.intValue()
            r0.setColor(r1)
        L_0x0258:
            if (r5 == 0) goto L_0x025d
            r0.setSound(r5)
        L_0x025d:
            if (r6 == 0) goto L_0x0262
            r0.setContentIntent(r6)
        L_0x0262:
            if (r7 == 0) goto L_0x0267
            r0.setDeleteIntent(r7)
        L_0x0267:
            android.app.Notification r0 = r0.build()
            r1 = r0
            goto L_0x011b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.messaging.zza.zzv(android.os.Bundle):boolean");
    }
}
