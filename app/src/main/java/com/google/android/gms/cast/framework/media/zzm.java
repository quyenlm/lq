package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaQueueItem;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayr;
import java.io.IOException;
import org.json.JSONObject;

final class zzm extends RemoteMediaClient.zzb {
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ MediaQueueItem[] zzard;
    private /* synthetic */ int zzare;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzm(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzard = mediaQueueItemArr;
        this.zzare = i;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzard, this.zzare, 0, -1, -1, this.zzarc);
            } catch (zzayr | IOException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
