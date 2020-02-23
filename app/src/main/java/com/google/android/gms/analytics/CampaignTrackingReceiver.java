package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzamj;
import com.google.android.gms.internal.zzaoc;
import com.google.android.gms.internal.zzaos;
import com.google.android.gms.internal.zzctz;

public class CampaignTrackingReceiver extends BroadcastReceiver {
    static zzctz zzads;
    private static Boolean zzadt;
    static Object zzuF = new Object();

    public static boolean zzac(Context context) {
        zzbo.zzu(context);
        if (zzadt != null) {
            return zzadt.booleanValue();
        }
        boolean zza = zzaos.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        zzadt = Boolean.valueOf(zza);
        return zza;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzaoc zzkr = zzamj.zzaf(context).zzkr();
        if (intent == null) {
            zzkr.zzbr("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzkr.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzkr.zzbr("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zzad = CampaignTrackingService.zzad(context);
        if (!zzad) {
            zzkr.zzbr("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzu(context, stringExtra);
        Class<? extends CampaignTrackingService> zzjm = zzjm();
        zzbo.zzu(zzjm);
        Intent intent2 = new Intent(context, zzjm);
        intent2.putExtra("referrer", stringExtra);
        synchronized (zzuF) {
            context.startService(intent2);
            if (zzad) {
                try {
                    if (zzads == null) {
                        zzctz zzctz = new zzctz(context, 1, "Analytics campaign WakeLock");
                        zzads = zzctz;
                        zzctz.setReferenceCounted(false);
                    }
                    zzads.acquire(1000);
                } catch (SecurityException e) {
                    zzkr.zzbr("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public Class<? extends CampaignTrackingService> zzjm() {
        return CampaignTrackingService.class;
    }

    /* access modifiers changed from: protected */
    public void zzu(Context context, String str) {
    }
}
