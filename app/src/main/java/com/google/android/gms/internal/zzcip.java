package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

final class zzcip implements Runnable {
    private /* synthetic */ boolean zzbtw;
    private /* synthetic */ zzcid zzbua;
    private /* synthetic */ AtomicReference zzbub;

    zzcip(zzcid zzcid, AtomicReference atomicReference, boolean z) {
        this.zzbua = zzcid;
        this.zzbub = atomicReference;
        this.zzbtw = z;
    }

    /* JADX INFO: finally extract failed */
    public final void run() {
        synchronized (this.zzbub) {
            try {
                zzcfd zzd = this.zzbua.zzbtU;
                if (zzd == null) {
                    this.zzbua.zzwF().zzyx().log("Failed to get user properties");
                    this.zzbub.notify();
                    return;
                }
                this.zzbub.set(zzd.zza(this.zzbua.zzwu().zzdV((String) null), this.zzbtw));
                this.zzbua.zzkP();
                this.zzbub.notify();
            } catch (RemoteException e) {
                this.zzbua.zzwF().zzyx().zzj("Failed to get user properties", e);
                this.zzbub.notify();
            } catch (Throwable th) {
                this.zzbub.notify();
                throw th;
            }
        }
    }
}
