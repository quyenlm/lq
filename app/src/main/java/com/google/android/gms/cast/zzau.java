package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayr;
import java.io.IOException;
import org.json.JSONObject;

final class zzau extends RemoteMediaPlayer.zzb {
    private /* synthetic */ int val$repeatMode;
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ JSONObject zzarc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzau(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int i, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzaqW = googleApiClient2;
        this.val$repeatMode = i;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzaqV.mLock) {
            this.zzaqV.zzaqQ.zzb(this.zzaqW);
            try {
                this.zzaqV.zzaqP.zza(this.zzarw, 0, -1, (MediaQueueItem[]) null, 0, Integer.valueOf(this.val$repeatMode), this.zzarc);
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (zzayr | IOException e) {
                setResult((RemoteMediaPlayer.MediaChannelResult) zzb(new Status(2100)));
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (Throwable th) {
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
                throw th;
            }
        }
    }
}
