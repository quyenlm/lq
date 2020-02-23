package com.google.android.gms.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Base64;
import android.util.Log;
import com.appsflyer.share.Constants;
import com.google.android.gms.common.util.zzq;
import com.google.firebase.iid.zzh;

public class GcmReceiver extends WakefulBroadcastReceiver {
    private static String zzbfA = "gcm.googleapis.com/refresh";
    private static boolean zzbfB = false;
    private zzh zzbfC;
    private zzh zzbfD;

    private final void doStartService(Context context, Intent intent) {
        ComponentName startService;
        if (isOrderedBroadcast()) {
            setResultCode(500);
        }
        ResolveInfo resolveService = context.getPackageManager().resolveService(intent, 0);
        if (resolveService == null || resolveService.serviceInfo == null) {
            Log.e("GcmReceiver", "Failed to resolve target intent service, skipping classname enforcement");
        } else {
            ServiceInfo serviceInfo = resolveService.serviceInfo;
            if (!context.getPackageName().equals(serviceInfo.packageName) || serviceInfo.name == null) {
                String valueOf = String.valueOf(serviceInfo.packageName);
                String valueOf2 = String.valueOf(serviceInfo.name);
                Log.e("GcmReceiver", new StringBuilder(String.valueOf(valueOf).length() + 94 + String.valueOf(valueOf2).length()).append("Error resolving target intent service, skipping classname enforcement. Resolved service was: ").append(valueOf).append(Constants.URL_PATH_DELIMITER).append(valueOf2).toString());
            } else {
                String str = serviceInfo.name;
                if (str.startsWith(".")) {
                    String valueOf3 = String.valueOf(context.getPackageName());
                    String valueOf4 = String.valueOf(str);
                    str = valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3);
                }
                if (Log.isLoggable("GcmReceiver", 3)) {
                    String valueOf5 = String.valueOf(str);
                    Log.d("GcmReceiver", valueOf5.length() != 0 ? "Restricting intent to a specific service: ".concat(valueOf5) : new String("Restricting intent to a specific service: "));
                }
                intent.setClassName(context.getPackageName(), str);
            }
        }
        try {
            if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
                startService = startWakefulService(context, intent);
            } else {
                startService = context.startService(intent);
                Log.d("GcmReceiver", "Missing wake lock permission, service start may be delayed");
            }
            if (startService == null) {
                Log.e("GcmReceiver", "Error while delivering the message: ServiceIntent not found.");
                if (isOrderedBroadcast()) {
                    setResultCode(404);
                }
            } else if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
        } catch (SecurityException e) {
            Log.e("GcmReceiver", "Error while delivering the message to the serviceIntent", e);
            if (isOrderedBroadcast()) {
                setResultCode(401);
            }
        }
    }

    private final synchronized zzh zzH(Context context, String str) {
        zzh zzh;
        if ("com.google.android.c2dm.intent.RECEIVE".equals(str)) {
            if (this.zzbfD == null) {
                this.zzbfD = new zzh(context, str);
            }
            zzh = this.zzbfD;
        } else {
            if (this.zzbfC == null) {
                this.zzbfC = new zzh(context, str);
            }
            zzh = this.zzbfC;
        }
        return zzh;
    }

    public void onReceive(Context context, Intent intent) {
        if (Log.isLoggable("GcmReceiver", 3)) {
            Log.d("GcmReceiver", "received new intent");
        }
        intent.setComponent((ComponentName) null);
        intent.setPackage(context.getPackageName());
        if (Build.VERSION.SDK_INT <= 18) {
            intent.removeCategory(context.getPackageName());
        }
        String stringExtra = intent.getStringExtra("from");
        if ("google.com/iid".equals(stringExtra) || zzbfA.equals(stringExtra)) {
            intent.setAction("com.google.android.gms.iid.InstanceID");
        }
        String stringExtra2 = intent.getStringExtra("gcm.rawData64");
        if (stringExtra2 != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra2, 0));
            intent.removeExtra("gcm.rawData64");
        }
        if (zzq.isAtLeastO()) {
            if (isOrderedBroadcast()) {
                setResultCode(-1);
            }
            zzH(context, intent.getAction()).zza(intent, goAsync());
            return;
        }
        if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            doStartService(context, intent);
        } else {
            doStartService(context, intent);
        }
        if (isOrderedBroadcast() && getResultCode() == 0) {
            setResultCode(-1);
        }
    }
}
