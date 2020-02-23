package com.google.android.gms.tagmanager;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbq;

final class zzae implements zzdi<zzbq> {
    private /* synthetic */ zzy zzbDX;

    private zzae(zzy zzy) {
        this.zzbDX = zzy;
    }

    /* synthetic */ zzae(zzy zzy, zzz zzz) {
        this(zzy);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final /* synthetic */ void onSuccess(java.lang.Object r6) {
        /*
            r5 = this;
            com.google.android.gms.internal.zzbq r6 = (com.google.android.gms.internal.zzbq) r6
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX
            com.google.android.gms.tagmanager.zzai r0 = r0.zzbDO
            r0.zzAV()
            com.google.android.gms.tagmanager.zzy r1 = r5.zzbDX
            monitor-enter(r1)
            com.google.android.gms.internal.zzbn r0 = r6.zzlB     // Catch:{ all -> 0x0079 }
            if (r0 != 0) goto L_0x003c
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            com.google.android.gms.internal.zzbq r0 = r0.zzbDT     // Catch:{ all -> 0x0079 }
            com.google.android.gms.internal.zzbn r0 = r0.zzlB     // Catch:{ all -> 0x0079 }
            if (r0 != 0) goto L_0x0032
            java.lang.String r0 = "Current resource is null; network resource is also null"
            com.google.android.gms.tagmanager.zzdj.e(r0)     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzai r0 = r0.zzbDO     // Catch:{ all -> 0x0079 }
            long r2 = r0.zzAT()     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            r0.zzag(r2)     // Catch:{ all -> 0x0079 }
            monitor-exit(r1)     // Catch:{ all -> 0x0079 }
        L_0x0031:
            return
        L_0x0032:
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            com.google.android.gms.internal.zzbq r0 = r0.zzbDT     // Catch:{ all -> 0x0079 }
            com.google.android.gms.internal.zzbn r0 = r0.zzlB     // Catch:{ all -> 0x0079 }
            r6.zzlB = r0     // Catch:{ all -> 0x0079 }
        L_0x003c:
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzy r2 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            com.google.android.gms.common.util.zze r2 = r2.zzvw     // Catch:{ all -> 0x0079 }
            long r2 = r2.currentTimeMillis()     // Catch:{ all -> 0x0079 }
            r4 = 0
            r0.zza(r6, r2, r4)     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            long r2 = r0.zzbDB     // Catch:{ all -> 0x0079 }
            r0 = 58
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0079 }
            r4.<init>(r0)     // Catch:{ all -> 0x0079 }
            java.lang.String r0 = "setting refresh time to current time: "
            java.lang.StringBuilder r0 = r4.append(r0)     // Catch:{ all -> 0x0079 }
            java.lang.StringBuilder r0 = r0.append(r2)     // Catch:{ all -> 0x0079 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzdj.v(r0)     // Catch:{ all -> 0x0079 }
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            boolean r0 = r0.zzAQ()     // Catch:{ all -> 0x0079 }
            if (r0 != 0) goto L_0x0077
            com.google.android.gms.tagmanager.zzy r0 = r5.zzbDX     // Catch:{ all -> 0x0079 }
            r0.zza((com.google.android.gms.internal.zzbq) r6)     // Catch:{ all -> 0x0079 }
        L_0x0077:
            monitor-exit(r1)     // Catch:{ all -> 0x0079 }
            goto L_0x0031
        L_0x0079:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0079 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.tagmanager.zzae.onSuccess(java.lang.Object):void");
    }

    public final void zzbw(int i) {
        if (i == zzda.zzbFk) {
            this.zzbDX.zzbDO.zzAU();
        }
        synchronized (this.zzbDX) {
            if (!this.zzbDX.isReady()) {
                if (this.zzbDX.zzbDR != null) {
                    this.zzbDX.setResult(this.zzbDX.zzbDR);
                } else {
                    this.zzbDX.setResult(this.zzbDX.zzb(Status.zzaBp));
                }
            }
        }
        this.zzbDX.zzag(this.zzbDX.zzbDO.zzAT());
    }
}
