package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayr;
import java.io.IOException;
import org.json.JSONObject;

final class zzac extends RemoteMediaClient.zzb {
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ double zzarq;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzac(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, double d, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzarq = d;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzarq, this.zzarc);
            } catch (zzayr | IOException | IllegalArgumentException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
