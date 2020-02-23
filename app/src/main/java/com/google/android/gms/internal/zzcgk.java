package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;

final class zzcgk extends Thread {
    private /* synthetic */ zzcgg zzbsh;
    private final Object zzbsk = new Object();
    private final BlockingQueue<FutureTask<?>> zzbsl;

    public zzcgk(zzcgg zzcgg, String str, BlockingQueue<FutureTask<?>> blockingQueue) {
        this.zzbsh = zzcgg;
        zzbo.zzu(str);
        zzbo.zzu(blockingQueue);
        this.zzbsl = blockingQueue;
        setName(str);
    }

    private final void zza(InterruptedException interruptedException) {
        this.zzbsh.zzwF().zzyz().zzj(String.valueOf(getName()).concat(" was interrupted"), interruptedException);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0078, code lost:
        r1 = r4.zzbsh.zzbsc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007e, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        r4.zzbsh.zzbsd.release();
        r4.zzbsh.zzbsc.notifyAll();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0097, code lost:
        if (r4 != r4.zzbsh.zzbrW) goto L_0x00a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0099, code lost:
        com.google.android.gms.internal.zzcgk unused = r4.zzbsh.zzbrW = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x009f, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a0, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00af, code lost:
        if (r4 != r4.zzbsh.zzbrX) goto L_0x00bb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b1, code lost:
        com.google.android.gms.internal.zzcgk unused = r4.zzbsh.zzbrX = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r4.zzbsh.zzwF().zzyx().log("Current scheduler thread is neither worker nor network");
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r4 = this;
            r0 = 0
            r1 = r0
        L_0x0002:
            if (r1 != 0) goto L_0x0015
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ InterruptedException -> 0x0010 }
            java.util.concurrent.Semaphore r0 = r0.zzbsd     // Catch:{ InterruptedException -> 0x0010 }
            r0.acquire()     // Catch:{ InterruptedException -> 0x0010 }
            r0 = 1
            r1 = r0
            goto L_0x0002
        L_0x0010:
            r0 = move-exception
            r4.zza(r0)
            goto L_0x0002
        L_0x0015:
            java.util.concurrent.BlockingQueue<java.util.concurrent.FutureTask<?>> r0 = r4.zzbsl     // Catch:{ all -> 0x0023 }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0023 }
            java.util.concurrent.FutureTask r0 = (java.util.concurrent.FutureTask) r0     // Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x004d
            r0.run()     // Catch:{ all -> 0x0023 }
            goto L_0x0015
        L_0x0023:
            r0 = move-exception
            com.google.android.gms.internal.zzcgg r1 = r4.zzbsh
            java.lang.Object r1 = r1.zzbsc
            monitor-enter(r1)
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            java.util.concurrent.Semaphore r2 = r2.zzbsd     // Catch:{ all -> 0x00e1 }
            r2.release()     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            java.lang.Object r2 = r2.zzbsc     // Catch:{ all -> 0x00e1 }
            r2.notifyAll()     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcgk r2 = r2.zzbrW     // Catch:{ all -> 0x00e1 }
            if (r4 != r2) goto L_0x00d1
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            r3 = 0
            com.google.android.gms.internal.zzcgk unused = r2.zzbrW = null     // Catch:{ all -> 0x00e1 }
        L_0x004b:
            monitor-exit(r1)     // Catch:{ all -> 0x00e1 }
            throw r0
        L_0x004d:
            java.lang.Object r1 = r4.zzbsk     // Catch:{ all -> 0x0023 }
            monitor-enter(r1)     // Catch:{ all -> 0x0023 }
            java.util.concurrent.BlockingQueue<java.util.concurrent.FutureTask<?>> r0 = r4.zzbsl     // Catch:{ all -> 0x00a6 }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x00a6 }
            if (r0 != 0) goto L_0x0067
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00a6 }
            boolean r0 = r0.zzbse     // Catch:{ all -> 0x00a6 }
            if (r0 != 0) goto L_0x0067
            java.lang.Object r0 = r4.zzbsk     // Catch:{ InterruptedException -> 0x00a1 }
            r2 = 30000(0x7530, double:1.4822E-319)
            r0.wait(r2)     // Catch:{ InterruptedException -> 0x00a1 }
        L_0x0067:
            monitor-exit(r1)     // Catch:{ all -> 0x00a6 }
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x0023 }
            java.lang.Object r1 = r0.zzbsc     // Catch:{ all -> 0x0023 }
            monitor-enter(r1)     // Catch:{ all -> 0x0023 }
            java.util.concurrent.BlockingQueue<java.util.concurrent.FutureTask<?>> r0 = r4.zzbsl     // Catch:{ all -> 0x00ce }
            java.lang.Object r0 = r0.peek()     // Catch:{ all -> 0x00ce }
            if (r0 != 0) goto L_0x00cb
            monitor-exit(r1)     // Catch:{ all -> 0x00ce }
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh
            java.lang.Object r1 = r0.zzbsc
            monitor-enter(r1)
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            java.util.concurrent.Semaphore r0 = r0.zzbsd     // Catch:{ all -> 0x00b8 }
            r0.release()     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            java.lang.Object r0 = r0.zzbsc     // Catch:{ all -> 0x00b8 }
            r0.notifyAll()     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcgk r0 = r0.zzbrW     // Catch:{ all -> 0x00b8 }
            if (r4 != r0) goto L_0x00a9
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            r2 = 0
            com.google.android.gms.internal.zzcgk unused = r0.zzbrW = null     // Catch:{ all -> 0x00b8 }
        L_0x009f:
            monitor-exit(r1)     // Catch:{ all -> 0x00b8 }
            return
        L_0x00a1:
            r0 = move-exception
            r4.zza(r0)     // Catch:{ all -> 0x00a6 }
            goto L_0x0067
        L_0x00a6:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00a6 }
            throw r0     // Catch:{ all -> 0x0023 }
        L_0x00a9:
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcgk r0 = r0.zzbrX     // Catch:{ all -> 0x00b8 }
            if (r4 != r0) goto L_0x00bb
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            r2 = 0
            com.google.android.gms.internal.zzcgk unused = r0.zzbrX = null     // Catch:{ all -> 0x00b8 }
            goto L_0x009f
        L_0x00b8:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00b8 }
            throw r0
        L_0x00bb:
            com.google.android.gms.internal.zzcgg r0 = r4.zzbsh     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcfl r0 = r0.zzwF()     // Catch:{ all -> 0x00b8 }
            com.google.android.gms.internal.zzcfn r0 = r0.zzyx()     // Catch:{ all -> 0x00b8 }
            java.lang.String r2 = "Current scheduler thread is neither worker nor network"
            r0.log(r2)     // Catch:{ all -> 0x00b8 }
            goto L_0x009f
        L_0x00cb:
            monitor-exit(r1)     // Catch:{ all -> 0x00ce }
            goto L_0x0015
        L_0x00ce:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ce }
            throw r0     // Catch:{ all -> 0x0023 }
        L_0x00d1:
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcgk r2 = r2.zzbrX     // Catch:{ all -> 0x00e1 }
            if (r4 != r2) goto L_0x00e4
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            r3 = 0
            com.google.android.gms.internal.zzcgk unused = r2.zzbrX = null     // Catch:{ all -> 0x00e1 }
            goto L_0x004b
        L_0x00e1:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00e1 }
            throw r0
        L_0x00e4:
            com.google.android.gms.internal.zzcgg r2 = r4.zzbsh     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcfl r2 = r2.zzwF()     // Catch:{ all -> 0x00e1 }
            com.google.android.gms.internal.zzcfn r2 = r2.zzyx()     // Catch:{ all -> 0x00e1 }
            java.lang.String r3 = "Current scheduler thread is neither worker nor network"
            r2.log(r3)     // Catch:{ all -> 0x00e1 }
            goto L_0x004b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcgk.run():void");
    }

    public final void zzfF() {
        synchronized (this.zzbsk) {
            this.zzbsk.notifyAll();
        }
    }
}
