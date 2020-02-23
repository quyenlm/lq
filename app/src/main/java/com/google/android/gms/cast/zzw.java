package com.google.android.gms.cast;

import com.google.android.gms.cast.CastRemoteDisplay;
import com.google.android.gms.common.api.ResultCallback;

final class zzw implements ResultCallback<CastRemoteDisplay.CastRemoteDisplaySessionResult> {
    private /* synthetic */ CastRemoteDisplayLocalService zzapJ;

    zzw(CastRemoteDisplayLocalService castRemoteDisplayLocalService) {
        this.zzapJ = castRemoteDisplayLocalService;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004c, code lost:
        r0 = r6.getPresentationDisplay();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0050, code lost:
        if (r0 == null) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0052, code lost:
        r5.zzapJ.zza(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0057, code lost:
        com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapt.set(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0064, code lost:
        if (r5.zzapJ.zzapC == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x006c, code lost:
        if (r5.zzapJ.zzapD == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r5.zzapJ.zzapC.unbindService(r5.zzapJ.zzapD);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0088, code lost:
        com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq.zzc("Cast Remote Display session created without display", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0095, code lost:
        com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq.zzb("No need to unbind service, already unbound", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onResult(com.google.android.gms.common.api.Result r6) {
        /*
            r5 = this;
            r4 = 0
            r3 = 0
            com.google.android.gms.cast.CastRemoteDisplay$CastRemoteDisplaySessionResult r6 = (com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplaySessionResult) r6
            com.google.android.gms.common.api.Status r0 = r6.getStatus()
            boolean r0 = r0.isSuccess()
            if (r0 != 0) goto L_0x001f
            com.google.android.gms.internal.zzayo r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq
            java.lang.String r1 = "Connection was not successful"
            java.lang.Object[] r2 = new java.lang.Object[r3]
            r0.zzc(r1, r2)
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ
            r0.zzne()
        L_0x001e:
            return
        L_0x001f:
            com.google.android.gms.internal.zzayo r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq
            java.lang.String r1 = "startRemoteDisplay successful"
            java.lang.Object[] r2 = new java.lang.Object[r3]
            r0.zzb(r1, r2)
            java.lang.Object r1 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzaps
            monitor-enter(r1)
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapH     // Catch:{ all -> 0x0048 }
            if (r0 != 0) goto L_0x004b
            com.google.android.gms.internal.zzayo r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq     // Catch:{ all -> 0x0048 }
            java.lang.String r2 = "Remote Display started but session already cancelled"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0048 }
            r0.zzb(r2, r3)     // Catch:{ all -> 0x0048 }
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ     // Catch:{ all -> 0x0048 }
            r0.zzne()     // Catch:{ all -> 0x0048 }
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            goto L_0x001e
        L_0x0048:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            throw r0
        L_0x004b:
            monitor-exit(r1)     // Catch:{ all -> 0x0048 }
            android.view.Display r0 = r6.getPresentationDisplay()
            if (r0 == 0) goto L_0x0088
            com.google.android.gms.cast.CastRemoteDisplayLocalService r1 = r5.zzapJ
            r1.zza((android.view.Display) r0)
        L_0x0057:
            java.util.concurrent.atomic.AtomicBoolean r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapt
            r0.set(r3)
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ
            android.content.Context r0 = r0.zzapC
            if (r0 == 0) goto L_0x001e
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ
            android.content.ServiceConnection r0 = r0.zzapD
            if (r0 == 0) goto L_0x001e
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ     // Catch:{ IllegalArgumentException -> 0x0094 }
            android.content.Context r0 = r0.zzapC     // Catch:{ IllegalArgumentException -> 0x0094 }
            com.google.android.gms.cast.CastRemoteDisplayLocalService r1 = r5.zzapJ     // Catch:{ IllegalArgumentException -> 0x0094 }
            android.content.ServiceConnection r1 = r1.zzapD     // Catch:{ IllegalArgumentException -> 0x0094 }
            r0.unbindService(r1)     // Catch:{ IllegalArgumentException -> 0x0094 }
        L_0x007d:
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ
            android.content.ServiceConnection unused = r0.zzapD = null
            com.google.android.gms.cast.CastRemoteDisplayLocalService r0 = r5.zzapJ
            android.content.Context unused = r0.zzapC = null
            goto L_0x001e
        L_0x0088:
            com.google.android.gms.internal.zzayo r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq
            java.lang.String r1 = "Cast Remote Display session created without display"
            java.lang.Object[] r2 = new java.lang.Object[r3]
            r0.zzc(r1, r2)
            goto L_0x0057
        L_0x0094:
            r0 = move-exception
            com.google.android.gms.internal.zzayo r0 = com.google.android.gms.cast.CastRemoteDisplayLocalService.zzapq
            java.lang.String r1 = "No need to unbind service, already unbound"
            java.lang.Object[] r2 = new java.lang.Object[r3]
            r0.zzb(r1, r2)
            goto L_0x007d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzw.onResult(com.google.android.gms.common.api.Result):void");
    }
}
