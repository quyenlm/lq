package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzaw extends RemoteMediaPlayer.zzb {
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ long zzarb;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ int zzarm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaw(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, long j, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzarm = i;
        this.zzaqW = googleApiClient2;
        this.zzarb = j;
        this.zzarc = jSONObject;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaxx r12) {
        /*
            r11 = this;
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV
            java.lang.Object r10 = r0.mLock
            monitor-enter(r10)
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV     // Catch:{ all -> 0x004e }
            int r1 = r11.zzarm     // Catch:{ all -> 0x004e }
            int r0 = r0.zzX(r1)     // Catch:{ all -> 0x004e }
            r1 = -1
            if (r0 != r1) goto L_0x0023
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.<init>(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.Result r0 = r11.zzb(r0)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x004e }
            r11.setResult(r0)     // Catch:{ all -> 0x004e }
            monitor-exit(r10)     // Catch:{ all -> 0x004e }
        L_0x0022:
            return
        L_0x0023:
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004e }
            com.google.android.gms.common.api.GoogleApiClient r1 = r11.zzaqW     // Catch:{ all -> 0x004e }
            r0.zzb(r1)     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            com.google.android.gms.internal.zzayp r1 = r0.zzaqP     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            com.google.android.gms.internal.zzayt r2 = r11.zzarw     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            int r3 = r11.zzarm     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            long r4 = r11.zzarb     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            r6 = 0
            r7 = 0
            r8 = 0
            org.json.JSONObject r9 = r11.zzarc     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            r1.zza((com.google.android.gms.internal.zzayt) r2, (int) r3, (long) r4, (com.google.android.gms.cast.MediaQueueItem[]) r6, (int) r7, (java.lang.Integer) r8, (org.json.JSONObject) r9)     // Catch:{ IOException -> 0x0079, zzayr -> 0x0051 }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.zzb(r1)     // Catch:{ all -> 0x004e }
        L_0x004c:
            monitor-exit(r10)     // Catch:{ all -> 0x004e }
            goto L_0x0022
        L_0x004e:
            r0 = move-exception
            monitor-exit(r10)     // Catch:{ all -> 0x004e }
            throw r0
        L_0x0051:
            r0 = move-exception
        L_0x0052:
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x006d }
            r1 = 2100(0x834, float:2.943E-42)
            r0.<init>(r1)     // Catch:{ all -> 0x006d }
            com.google.android.gms.common.api.Result r0 = r11.zzb(r0)     // Catch:{ all -> 0x006d }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x006d }
            r11.setResult(r0)     // Catch:{ all -> 0x006d }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r11.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004e }
            r1 = 0
            r0.zzb(r1)     // Catch:{ all -> 0x004e }
            goto L_0x004c
        L_0x006d:
            r0 = move-exception
            com.google.android.gms.cast.RemoteMediaPlayer r1 = r11.zzaqV     // Catch:{ all -> 0x004e }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r1 = r1.zzaqQ     // Catch:{ all -> 0x004e }
            r2 = 0
            r1.zzb(r2)     // Catch:{ all -> 0x004e }
            throw r0     // Catch:{ all -> 0x004e }
        L_0x0079:
            r0 = move-exception
            goto L_0x0052
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzaw.zza(com.google.android.gms.internal.zzaxx):void");
    }
}
