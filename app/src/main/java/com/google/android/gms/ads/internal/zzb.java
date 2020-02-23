package com.google.android.gms.ads.internal;

import android.os.Debug;
import com.google.android.gms.internal.zzafr;
import com.google.android.gms.internal.zzmo;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

final class zzb extends TimerTask {
    private /* synthetic */ CountDownLatch zzsT;
    private /* synthetic */ Timer zzsU;
    private /* synthetic */ zza zzsV;

    zzb(zza zza, CountDownLatch countDownLatch, Timer timer) {
        this.zzsV = zza;
        this.zzsT = countDownLatch;
        this.zzsU = timer;
    }

    public final void run() {
        if (((long) ((Integer) zzbs.zzbL().zzd(zzmo.zzFG)).intValue()) != this.zzsT.getCount()) {
            zzafr.zzaC("Stopping method tracing");
            Debug.stopMethodTracing();
            if (this.zzsT.getCount() == 0) {
                this.zzsU.cancel();
                return;
            }
        }
        String concat = String.valueOf(this.zzsV.zzsP.zzqD.getPackageName()).concat("_adsTrace_");
        try {
            zzafr.zzaC("Starting method tracing");
            this.zzsT.countDown();
            Debug.startMethodTracing(new StringBuilder(String.valueOf(concat).length() + 20).append(concat).append(zzbs.zzbF().currentTimeMillis()).toString(), ((Integer) zzbs.zzbL().zzd(zzmo.zzFH)).intValue());
        } catch (Exception e) {
            zzafr.zzc("Exception occurred while starting method tracing.", e);
        }
    }
}
