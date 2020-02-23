package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import java.io.IOException;
import org.json.JSONObject;

final class zzt extends RemoteMediaClient.zzb {
    private /* synthetic */ long zzarb;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ MediaInfo zzarj;
    private /* synthetic */ boolean zzark;
    private /* synthetic */ long[] zzarl;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzt(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzarj = mediaInfo;
        this.zzark = z;
        this.zzarb = j;
        this.zzarl = jArr;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzarj, this.zzark, this.zzarb, this.zzarl, this.zzarc);
            } catch (IOException | IllegalStateException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
