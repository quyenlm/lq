package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayr;
import java.io.IOException;
import org.json.JSONObject;

final class zzad extends RemoteMediaClient.zzb {
    private /* synthetic */ boolean zzacG;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzad(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, boolean z, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzacG = z;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzacG, this.zzarc);
            } catch (zzayr | IOException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
