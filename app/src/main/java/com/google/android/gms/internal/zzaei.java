package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzbs;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.json.JSONException;

final class zzaei implements Runnable {
    private /* synthetic */ zzaeg zzXf;
    private /* synthetic */ zzajm zzXg;

    zzaei(zzaeg zzaeg, zzajm zzajm) {
        this.zzXf = zzaeg;
        this.zzXg = zzajm;
    }

    public final void run() {
        Throwable th;
        try {
            this.zzXf.zzi((Map) this.zzXg.get());
            if (this.zzXf.zzXa) {
                synchronized (this.zzXf.mLock) {
                    this.zzXf.zzWX.zzcsJ = 9;
                }
            }
            this.zzXf.send();
            return;
        } catch (InterruptedException e) {
            th = e;
        } catch (ExecutionException e2) {
            th = e2;
        } catch (JSONException e3) {
            th = e3;
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzGe)).booleanValue()) {
            zzafr.zza("Failed to get SafeBrowsing metadata", th);
        }
    }
}
