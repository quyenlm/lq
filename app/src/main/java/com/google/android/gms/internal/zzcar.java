package com.google.android.gms.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.HandlerThread;
import android.support.v4.media.session.PlaybackStateCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

final class zzcar implements zzf, zzg {
    private final String packageName;
    private zzcas zzbfo;
    private final String zzbfp;
    private final LinkedBlockingQueue<zzax> zzbfq;
    private final HandlerThread zzbfr = new HandlerThread("GassClient");

    public zzcar(Context context, String str, String str2) {
        this.packageName = str;
        this.zzbfp = str2;
        this.zzbfr.start();
        this.zzbfo = new zzcas(context, this.zzbfr.getLooper(), this, this);
        this.zzbfq = new LinkedBlockingQueue<>();
        this.zzbfo.zzrb();
    }

    private final void zzgA() {
        if (this.zzbfo == null) {
            return;
        }
        if (this.zzbfo.isConnected() || this.zzbfo.isConnecting()) {
            this.zzbfo.disconnect();
        }
    }

    private final zzcax zzvx() {
        try {
            return this.zzbfo.zzvz();
        } catch (DeadObjectException | IllegalStateException e) {
            return null;
        }
    }

    private static zzax zzvy() {
        zzax zzax = new zzax();
        zzax.zzbq = Long.valueOf(PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID);
        return zzax;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0039, code lost:
        zzgA();
        r4.zzbfr.quit();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0041, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0038, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0038 A[ExcHandler:  FINALLY, Splitter:B:2:0x0006] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onConnected(android.os.Bundle r5) {
        /*
            r4 = this;
            com.google.android.gms.internal.zzcax r0 = r4.zzvx()
            if (r0 == 0) goto L_0x0024
            com.google.android.gms.internal.zzcat r1 = new com.google.android.gms.internal.zzcat     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            java.lang.String r2 = r4.packageName     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            java.lang.String r3 = r4.zzbfp     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            r1.<init>(r2, r3)     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            com.google.android.gms.internal.zzcav r0 = r0.zza(r1)     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            com.google.android.gms.internal.zzax r0 = r0.zzvA()     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            java.util.concurrent.LinkedBlockingQueue<com.google.android.gms.internal.zzax> r1 = r4.zzbfq     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            r1.put(r0)     // Catch:{ Throwable -> 0x0025, all -> 0x0038 }
            r4.zzgA()
            android.os.HandlerThread r0 = r4.zzbfr
            r0.quit()
        L_0x0024:
            return
        L_0x0025:
            r0 = move-exception
            java.util.concurrent.LinkedBlockingQueue<com.google.android.gms.internal.zzax> r0 = r4.zzbfq     // Catch:{ InterruptedException -> 0x0042, all -> 0x0038 }
            com.google.android.gms.internal.zzax r1 = zzvy()     // Catch:{ InterruptedException -> 0x0042, all -> 0x0038 }
            r0.put(r1)     // Catch:{ InterruptedException -> 0x0042, all -> 0x0038 }
        L_0x002f:
            r4.zzgA()
            android.os.HandlerThread r0 = r4.zzbfr
            r0.quit()
            goto L_0x0024
        L_0x0038:
            r0 = move-exception
            r4.zzgA()
            android.os.HandlerThread r1 = r4.zzbfr
            r1.quit()
            throw r0
        L_0x0042:
            r0 = move-exception
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcar.onConnected(android.os.Bundle):void");
    }

    public final void onConnectionFailed(ConnectionResult connectionResult) {
        try {
            this.zzbfq.put(zzvy());
        } catch (InterruptedException e) {
        }
    }

    public final void onConnectionSuspended(int i) {
        try {
            this.zzbfq.put(zzvy());
        } catch (InterruptedException e) {
        }
    }

    public final zzax zzbe(int i) {
        zzax zzax;
        try {
            zzax = this.zzbfq.poll(Constants.ACTIVE_THREAD_WATCHDOG, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            zzax = null;
        }
        return zzax == null ? zzvy() : zzax;
    }
}
