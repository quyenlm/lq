package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzamj;
import com.google.android.gms.internal.zzank;
import com.google.android.gms.internal.zzaoc;
import com.google.android.gms.internal.zzaos;
import com.google.android.gms.internal.zzctz;

public class CampaignTrackingService extends Service {
    private static Boolean zzadu;
    private Handler mHandler;

    public static boolean zzad(Context context) {
        zzbo.zzu(context);
        if (zzadu != null) {
            return zzadu.booleanValue();
        }
        boolean zzw = zzaos.zzw(context, "com.google.android.gms.analytics.CampaignTrackingService");
        zzadu = Boolean.valueOf(zzw);
        return zzw;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        super.onCreate();
        zzamj.zzaf(this).zzkr().zzbo("CampaignTrackingService is starting up");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzamj.zzaf(this).zzkr().zzbo("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int i, int i2) {
        try {
            synchronized (CampaignTrackingReceiver.zzuF) {
                zzctz zzctz = CampaignTrackingReceiver.zzads;
                if (zzctz != null && zzctz.isHeld()) {
                    zzctz.release();
                }
            }
        } catch (SecurityException e) {
        }
        zzamj zzaf = zzamj.zzaf(this);
        zzaoc zzkr = zzaf.zzkr();
        String str = null;
        if (intent != null) {
            str = intent.getStringExtra("referrer");
        }
        Handler handler = this.mHandler;
        if (handler == null) {
            handler = new Handler(getMainLooper());
            this.mHandler = handler;
        }
        if (TextUtils.isEmpty(str)) {
            if (intent == null) {
                zzkr.zzbr("CampaignTrackingService received null intent");
            } else {
                zzkr.zzbr("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            }
            zzaf.zzkt().zzf(new zzc(this, zzkr, handler, i2));
        } else {
            int zzlp = zzank.zzlp();
            if (str.length() > zzlp) {
                zzkr.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(str.length()), Integer.valueOf(zzlp));
                str = str.substring(0, zzlp);
            }
            zzkr.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(i2), str);
            zzaf.zzkv().zza(str, new zzd(this, zzkr, handler, i2));
        }
        return 2;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaoc zzaoc, Handler handler, int i) {
        handler.post(new zze(this, i, zzaoc));
    }
}
