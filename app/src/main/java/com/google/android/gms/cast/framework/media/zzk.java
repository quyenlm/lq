package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import com.google.android.gms.internal.zzayr;
import java.io.IOException;

final class zzk extends RemoteMediaClient.zzb {
    private /* synthetic */ TextTrackStyle zzaqY;
    private /* synthetic */ RemoteMediaClient zzauy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzk(RemoteMediaClient remoteMediaClient, GoogleApiClient googleApiClient, TextTrackStyle textTrackStyle) {
        super(googleApiClient);
        this.zzauy = remoteMediaClient;
        this.zzaqY = textTrackStyle;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzauy.mLock) {
            try {
                this.zzauy.zzaqP.zza(this.zzarw, this.zzaqY);
            } catch (zzayr | IOException e) {
                setResult((RemoteMediaClient.MediaChannelResult) zzb(new Status(2100)));
            }
        }
    }
}
