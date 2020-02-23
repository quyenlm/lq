package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzax extends RemoteMediaPlayer.zzb {
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ int zzarm;
    private /* synthetic */ int zzarn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzax(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, int i2, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzarm = i;
        this.zzarn = i2;
        this.zzaqW = googleApiClient2;
        this.zzarc = jSONObject;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaxx r9) {
        /*
            r8 = this;
            r0 = 0
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r8.zzaqV
            java.lang.Object r2 = r1.mLock
            monitor-enter(r2)
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r8.zzaqV     // Catch:{ all -> 0x004e }
            int r3 = r8.zzarm     // Catch:{ all -> 0x004e }
            int r1 = r1.zzX(r3)     // Catch:{ all -> 0x004e }
            r3 = -1
            if (r1 != r3) goto L_0x0024
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
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
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
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
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r8.setResult(r0)     // Catch:{ all -> 0x004e }
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            goto L_0x0023
        L_0x0066:
            int r3 = r8.zzarn     // Catch:{ all -> 0x004e }
            if (r3 <= r1) goto L_0x00ab
            int r1 = r8.zzarn     // Catch:{ all -> 0x004e }
            int r1 = r1 + 1
        L_0x006e:
            com.google.android.gms.cast.RemoteMediaPlayer r3 = r8.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.MediaStatus r3 = r3.getMediaStatus()     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.MediaQueueItem r1 = r3.getQueueItem(r1)     // Catch:{ all -> 0x004e }
            if (r1 == 0) goto L_0x007e
            int r0 = r1.getItemId()     // Catch:{ all -> 0x004e }
        L_0x007e:
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r8.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r1 = r1.zzaqQ     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.GoogleApiClient r3 = r8.zzaqW     // Catch:{ all -> 0x004e }
            r1.zzb(r3)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r8.zzaqV     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            com.google.android.gms.internal.zzayp r1 = r1.zzaqP     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            com.google.android.gms.internal.zzayt r3 = r8.zzarw     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            r4 = 1
            int[] r4 = new int[r4]     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            r5 = 0
            int r6 = r8.zzarm     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            r4[r5] = r6     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            org.json.JSONObject r5 = r8.zzarc     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            r1.zza((com.google.android.gms.internal.zzayt) r3, (int[]) r4, (int) r0, (org.json.JSONObject) r5)     // Catch:{ IOException -> 0x00d6, zzayr -> 0x00ae }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r8.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.zzb(r1)     // Catch:{ all -> 0x004e }
        L_0x00a8:
            monitor-exit(r2)     // Catch:{ all -> 0x004e }
            goto L_0x0023
        L_0x00ab:
            int r1 = r8.zzarn     // Catch:{ all -> 0x004e }
            goto L_0x006e
        L_0x00ae:
            r0 = move-exception
        L_0x00af:
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x00ca }
            r1 = 2100(0x834, float:2.943E-42)
            r0.<init>(r1)     // Catch:{ all -> 0x00ca }
            com.google.android.gms.common.api.Result r0 = r8.zzb(r0)     // Catch:{ all -> 0x00ca }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x00ca }
            r8.setResult(r0)     // Catch:{ all -> 0x00ca }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r8.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.zzb(r1)     // Catch:{ all -> 0x004e }
            goto L_0x00a8
        L_0x00ca:
            r0 = move-exception
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r8.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r1 = r1.zzaqQ     // Catch:{ all -> 0x004e }
            r3 = 0
            r1.zzb(r3)     // Catch:{ all -> 0x004e }
            throw r0     // Catch:{ all -> 0x004e }
        L_0x00d6:
            r0 = move-exception
            goto L_0x00af
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzax.zza(com.google.android.gms.internal.zzaxx):void");
    }
}
