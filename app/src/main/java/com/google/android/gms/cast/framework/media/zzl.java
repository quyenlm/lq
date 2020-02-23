package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import java.io.IOException;
import org.json.JSONObject;

final class zzl extends RemoteMediaClient.zzb {
    private /* synthetic */ int val$repeatMode;
    private /* synthetic */ MediaQueueItem[] zzaqZ;
    private /* synthetic */ int zzara;
    private /* synthetic */ long zzarb;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzl(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzaqZ = mediaQueueItemArr;
        this.zzara = i;
        this.val$repeatMode = i2;
        this.zzarb = j;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzaqZ, this.zzara, this.val$repeatMode, this.zzarb, this.zzarc);
            } catch (IOException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
