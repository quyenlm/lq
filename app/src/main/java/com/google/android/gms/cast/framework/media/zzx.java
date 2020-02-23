package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzx extends RemoteMediaClient.zzb {
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ int zzarm;
    private /* synthetic */ int zzarn;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzx(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, int i, int i2, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzarm = i;
        this.zzarn = i2;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0099, code lost:
        setResult((com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) zzb(new com.google.android.gms.common.api.Status(2100)));
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaxx r9) {
        /*
            r8 = this;
            r0 = 0
            com.google.android.gms.cast.framework.media.RemoteMediaClient r1 = r8.zzauy
            java.lang.Object r2 = r1.mLock
            monitor-enter(r2)
            com.google.android.gms.cast.framework.media.RemoteMediaClient r1 = r8.zzauy     // Catch:{ all -> 0x004e }
            int r3 = r8.zzarm     // Catch:{ all -> 0x004e }
            int r1 = r1.zzX(r3)     // Catch:{ all -> 0x004e }
            r3 = -1
            if (r1 != r3) goto L_0x0024
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.framework.media.RemoteMediaClient$MediaChannelResult r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r8.setResult(r0)     // Catch:{ all -> 0x004e }
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
        L_0x0023:
            return
        L_0x0024:
            int r3 = r8.zzarn     // Catch:{ all -> 0x004e }
            if (r3 >= 0) goto L_0x0051
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 2001(0x7d1, float:2.804E-42)
            java.util.Locale r3 = java.util.Locale.ROOT     // Catch:{ all -> 0x004e }
            java.lang.String r4 = "Invalid request: Invalid newIndex %d."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ all -> 0x004e }
            r6 = 0
            int r7 = r8.zzarn     // Catch:{ all -> 0x004e }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ all -> 0x004e }
            r5[r6] = r7     // Catch:{ all -> 0x004e }
            java.lang.String r3 = java.lang.String.format(r3, r4, r5)     // Catch:{ all -> 0x004e }
            r0.<init>(r1, r3)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.framework.media.RemoteMediaClient$MediaChannelResult r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r8.setResult(r0)     // Catch:{ all -> 0x004e }
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            goto L_0x0023
        L_0x004e:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            throw r0
        L_0x0051:
            int r3 = r8.zzarn     // Catch:{ all -> 0x004e }
            if (r1 != r3) goto L_0x0066
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.framework.media.RemoteMediaClient$MediaChannelResult r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r8.setResult(r0)     // Catch:{ all -> 0x004e }
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            goto L_0x0023
        L_0x0066:
            int r3 = r8.zzarn     // Catch:{ all -> 0x004e }
            if (r3 <= r1) goto L_0x0095
            int r1 = r8.zzarn     // Catch:{ all -> 0x004e }
            int r1 = r1 + 1
        L_0x006e:
            com.google.android.gms.cast.framework.media.RemoteMediaClient r3 = r8.zzauy     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.MediaStatus r3 = r3.getMediaStatus()     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.MediaQueueItem r1 = r3.getQueueItem(r1)     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x007e
            int r0 = r1.getItemId()     // Catch:{ all -> 0x004e }
        L_0x007e:
            com.google.android.gms.cast.framework.media.RemoteMediaClient r1 = r8.zzauy     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            com.google.android.gms.internal.zzayp r1 = r1.zzaqP     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            com.google.android.gms.internal.zzayt r3 = r8.zzarw     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            r4 = 1
            int[] r4 = new int[r4]     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            r5 = 0
            int r6 = r8.zzarm     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            r4[r5] = r6     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            org.json.JSONObject r5 = r8.zzarc     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
            r1.zza((com.google.android.gms.internal.zzayt) r3, (int[]) r4, (int) r0, (org.json.JSONObject) r5)     // Catch:{ IOException -> 0x00aa, zzayr -> 0x0098 }
        L_0x0093:
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            goto L_0x0023
        L_0x0095:
            int r1 = r8.zzarn     // Catch:{ all -> 0x004e }
            goto L_0x006e
        L_0x0098:
            r0 = move-exception
        L_0x0099:
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 2100(0x834, float:2.943E-42)
            r0.<init>(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.framework.media.RemoteMediaClient$MediaChannelResult r0 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r8.setResult(r0)     // Catch:{ all -> 0x004e }
            goto L_0x0093
        L_0x00aa:
            r0 = move-exception
            goto L_0x0099
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzx.zza(com.google.android.gms.internal.zzaxx):void");
    }
}
