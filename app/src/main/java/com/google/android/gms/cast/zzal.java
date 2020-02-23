package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import java.io.IOException;
import org.json.JSONObject;

final class zzal extends RemoteMediaPlayer.zzb {
    private /* synthetic */ int val$repeatMode;
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ MediaQueueItem[] zzaqZ;
    private /* synthetic */ int zzara;
    private /* synthetic */ long zzarb;
    private /* synthetic */ JSONObject zzarc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzal(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzaqW = googleApiClient2;
        this.zzaqZ = mediaQueueItemArr;
        this.zzara = i;
        this.val$repeatMode = i2;
        this.zzarb = j;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzaqV.mLock) {
            this.zzaqV.zzaqQ.zzb(this.zzaqW);
            try {
                this.zzaqV.zzaqP.zza(this.zzarw, this.zzaqZ, this.zzara, this.val$repeatMode, this.zzarb, this.zzarc);
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (IOException e) {
                setResult((RemoteMediaPlayer.MediaChannelResult) zzb(new Status(2100)));
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (Throwable th) {
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
                throw th;
            }
        }
    }
}
