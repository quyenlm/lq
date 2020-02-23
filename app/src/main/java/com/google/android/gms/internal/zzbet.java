package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

final class zzbet implements Runnable {
    private /* synthetic */ Result zzaFh;
    private /* synthetic */ zzbes zzaFi;

    zzbet(zzbes zzbes, Result result) {
        this.zzaFi = zzbes;
        this.zzaFh = result;
    }

    @WorkerThread
    public final void run() {
        try {
            zzbbe.zzaBV.set(true);
            this.zzaFi.zzaFf.sendMessage(this.zzaFi.zzaFf.obtainMessage(0, this.zzaFi.zzaFa.onSuccess(this.zzaFh)));
            zzbbe.zzaBV.set(false);
            zzbes.zzc(this.zzaFh);
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zzaFi.zzaBY.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzaFi);
            }
        } catch (RuntimeException e) {
            this.zzaFi.zzaFf.sendMessage(this.zzaFi.zzaFf.obtainMessage(1, e));
            zzbbe.zzaBV.set(false);
            zzbes.zzc(this.zzaFh);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zzaFi.zzaBY.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zzb(this.zzaFi);
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            zzbbe.zzaBV.set(false);
            zzbes.zzc(this.zzaFh);
            GoogleApiClient googleApiClient3 = (GoogleApiClient) this.zzaFi.zzaBY.get();
            if (googleApiClient3 != null) {
                googleApiClient3.zzb(this.zzaFi);
            }
            throw th2;
        }
    }
}
