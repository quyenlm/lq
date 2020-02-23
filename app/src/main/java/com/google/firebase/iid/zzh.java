package com.google.firebase.iid;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class zzh implements ServiceConnection {
    private final Intent zzckp;
    private final ScheduledExecutorService zzckq;
    private final Queue<zzd> zzckr;
    private zzf zzcks;
    private boolean zzckt;
    private final Context zzqD;

    public zzh(Context context, String str) {
        this(context, str, new ScheduledThreadPoolExecutor(0));
    }

    @VisibleForTesting
    private zzh(Context context, String str, ScheduledExecutorService scheduledExecutorService) {
        this.zzckr = new LinkedList();
        this.zzckt = false;
        this.zzqD = context.getApplicationContext();
        this.zzckp = new Intent(str).setPackage(this.zzqD.getPackageName());
        this.zzckq = scheduledExecutorService;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005e, code lost:
        if (android.util.Log.isLoggable("EnhancedIntentService", 3) == false) goto L_0x007f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0064, code lost:
        if (r4.zzckt != false) goto L_0x0098;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0066, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0067, code lost:
        android.util.Log.d("EnhancedIntentService", new java.lang.StringBuilder(39).append("binder is dead. start connection? ").append(r0).toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0081, code lost:
        if (r4.zzckt != false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0083, code lost:
        r4.zzckt = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0094, code lost:
        if (com.google.android.gms.common.stats.zza.zzrU().zza(r4.zzqD, r4.zzckp, r4, 65) == false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0098, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        android.util.Log.e("EnhancedIntentService", "binding to the service failed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b5, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b6, code lost:
        android.util.Log.e("EnhancedIntentService", "Exception while binding the service", r0);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final synchronized void zzJO() {
        /*
            r4 = this;
            r1 = 1
            monitor-enter(r4)
            java.lang.String r0 = "EnhancedIntentService"
            r2 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r2)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0012
            java.lang.String r0 = "EnhancedIntentService"
            java.lang.String r2 = "flush queue called"
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x0054 }
        L_0x0012:
            java.util.Queue<com.google.firebase.iid.zzd> r0 = r4.zzckr     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0096
            java.lang.String r0 = "EnhancedIntentService"
            r2 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r2)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x002a
            java.lang.String r0 = "EnhancedIntentService"
            java.lang.String r2 = "found intent to be delivered"
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x0054 }
        L_0x002a:
            com.google.firebase.iid.zzf r0 = r4.zzcks     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0057
            com.google.firebase.iid.zzf r0 = r4.zzcks     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.isBinderAlive()     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0057
            java.lang.String r0 = "EnhancedIntentService"
            r2 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r2)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x0046
            java.lang.String r0 = "EnhancedIntentService"
            java.lang.String r2 = "binder is alive, sending the intent."
            android.util.Log.d(r0, r2)     // Catch:{ all -> 0x0054 }
        L_0x0046:
            java.util.Queue<com.google.firebase.iid.zzd> r0 = r4.zzckr     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0054 }
            com.google.firebase.iid.zzd r0 = (com.google.firebase.iid.zzd) r0     // Catch:{ all -> 0x0054 }
            com.google.firebase.iid.zzf r2 = r4.zzcks     // Catch:{ all -> 0x0054 }
            r2.zza((com.google.firebase.iid.zzd) r0)     // Catch:{ all -> 0x0054 }
            goto L_0x0012
        L_0x0054:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x0057:
            java.lang.String r0 = "EnhancedIntentService"
            r2 = 3
            boolean r0 = android.util.Log.isLoggable(r0, r2)     // Catch:{ all -> 0x0054 }
            if (r0 == 0) goto L_0x007f
            java.lang.String r2 = "EnhancedIntentService"
            boolean r0 = r4.zzckt     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0098
            r0 = r1
        L_0x0067:
            r1 = 39
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r3.<init>(r1)     // Catch:{ all -> 0x0054 }
            java.lang.String r1 = "binder is dead. start connection? "
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ all -> 0x0054 }
            java.lang.StringBuilder r0 = r1.append(r0)     // Catch:{ all -> 0x0054 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0054 }
            android.util.Log.d(r2, r0)     // Catch:{ all -> 0x0054 }
        L_0x007f:
            boolean r0 = r4.zzckt     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0096
            r0 = 1
            r4.zzckt = r0     // Catch:{ all -> 0x0054 }
            com.google.android.gms.common.stats.zza r0 = com.google.android.gms.common.stats.zza.zzrU()     // Catch:{ SecurityException -> 0x00b5 }
            android.content.Context r1 = r4.zzqD     // Catch:{ SecurityException -> 0x00b5 }
            android.content.Intent r2 = r4.zzckp     // Catch:{ SecurityException -> 0x00b5 }
            r3 = 65
            boolean r0 = r0.zza(r1, r2, r4, r3)     // Catch:{ SecurityException -> 0x00b5 }
            if (r0 == 0) goto L_0x009a
        L_0x0096:
            monitor-exit(r4)
            return
        L_0x0098:
            r0 = 0
            goto L_0x0067
        L_0x009a:
            java.lang.String r0 = "EnhancedIntentService"
            java.lang.String r1 = "binding to the service failed"
            android.util.Log.e(r0, r1)     // Catch:{ SecurityException -> 0x00b5 }
        L_0x00a1:
            java.util.Queue<com.google.firebase.iid.zzd> r0 = r4.zzckr     // Catch:{ all -> 0x0054 }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x0054 }
            if (r0 != 0) goto L_0x0096
            java.util.Queue<com.google.firebase.iid.zzd> r0 = r4.zzckr     // Catch:{ all -> 0x0054 }
            java.lang.Object r0 = r0.poll()     // Catch:{ all -> 0x0054 }
            com.google.firebase.iid.zzd r0 = (com.google.firebase.iid.zzd) r0     // Catch:{ all -> 0x0054 }
            r0.finish()     // Catch:{ all -> 0x0054 }
            goto L_0x00a1
        L_0x00b5:
            r0 = move-exception
            java.lang.String r1 = "EnhancedIntentService"
            java.lang.String r2 = "Exception while binding the service"
            android.util.Log.e(r1, r2, r0)     // Catch:{ all -> 0x0054 }
            goto L_0x00a1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.firebase.iid.zzh.zzJO():void");
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this) {
            this.zzckt = false;
            this.zzcks = (zzf) iBinder;
            if (Log.isLoggable("EnhancedIntentService", 3)) {
                String valueOf = String.valueOf(componentName);
                Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 20).append("onServiceConnected: ").append(valueOf).toString());
            }
            zzJO();
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            String valueOf = String.valueOf(componentName);
            Log.d("EnhancedIntentService", new StringBuilder(String.valueOf(valueOf).length() + 23).append("onServiceDisconnected: ").append(valueOf).toString());
        }
        zzJO();
    }

    public final synchronized void zza(Intent intent, BroadcastReceiver.PendingResult pendingResult) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "new intent queued in the bind-strategy delivery");
        }
        this.zzckr.add(new zzd(intent, pendingResult, this.zzckq));
        zzJO();
    }
}
