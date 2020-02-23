package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import org.json.JSONObject;

final class zzav extends RemoteMediaPlayer.zzb {
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ int zzarm;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzav(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzarm = i;
        this.zzaqW = googleApiClient2;
        this.zzarc = jSONObject;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.internal.zzaxx r7) {
        /*
            r6 = this;
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV
            java.lang.Object r1 = r0.mLock
            monitor-enter(r1)
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV     // Catch:{ all -> 0x004f }
            int r2 = r6.zzarm     // Catch:{ all -> 0x004f }
            int r0 = r0.zzX(r2)     // Catch:{ all -> 0x004f }
            r2 = -1
            if (r0 != r2) goto L_0x0023
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x004f }
            r2 = 0
            r0.<init>(r2)     // Catch:{ all -> 0x004f }
            com.google.android.gms.common.api.Result r0 = r6.zzb(r0)     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x004f }
            r6.setResult(r0)     // Catch:{ all -> 0x004f }
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
        L_0x0022:
            return
        L_0x0023:
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004f }
            com.google.android.gms.common.api.GoogleApiClient r2 = r6.zzaqW     // Catch:{ all -> 0x004f }
            r0.zzb(r2)     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            com.google.android.gms.internal.zzayp r0 = r0.zzaqP     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            com.google.android.gms.internal.zzayt r2 = r6.zzarw     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            r3 = 1
            int[] r3 = new int[r3]     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            r4 = 0
            int r5 = r6.zzarm     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            r3[r4] = r5     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            org.json.JSONObject r4 = r6.zzarc     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            r0.zza((com.google.android.gms.internal.zzayt) r2, (int[]) r3, (org.json.JSONObject) r4)     // Catch:{ IOException -> 0x007a, zzayr -> 0x0052 }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004f }
            r2 = 0
            r0.zzb(r2)     // Catch:{ all -> 0x004f }
        L_0x004d:
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
            goto L_0x0022
        L_0x004f:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x004f }
            throw r0
        L_0x0052:
            r0 = move-exception
        L_0x0053:
            com.google.android.gms.common.api.Status r0 = new com.google.android.gms.common.api.Status     // Catch:{ all -> 0x006e }
            r2 = 2100(0x834, float:2.943E-42)
            r0.<init>(r2)     // Catch:{ all -> 0x006e }
            com.google.android.gms.common.api.Result r0 = r6.zzb(r0)     // Catch:{ all -> 0x006e }
            com.google.android.gms.cast.RemoteMediaPlayer$MediaChannelResult r0 = (com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult) r0     // Catch:{ all -> 0x006e }
            r6.setResult(r0)     // Catch:{ all -> 0x006e }
            com.google.android.gms.cast.RemoteMediaPlayer r0 = r6.zzaqV     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r0 = r0.zzaqQ     // Catch:{ all -> 0x004f }
            r2 = 0
            r0.zzb(r2)     // Catch:{ all -> 0x004f }
            goto L_0x004d
        L_0x006e:
            r0 = move-exception
            com.google.android.gms.cast.RemoteMediaPlayer r2 = r6.zzaqV     // Catch:{ all -> 0x004f }
            com.google.android.gms.cast.RemoteMediaPlayer$zza r2 = r2.zzaqQ     // Catch:{ all -> 0x004f }
            r3 = 0
            r2.zzb(r3)     // Catch:{ all -> 0x004f }
            throw r0     // Catch:{ all -> 0x004f }
        L_0x007a:
            r0 = move-exception
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.zzav.zza(com.google.android.gms.internal.zzaxx):void");
    }
}
