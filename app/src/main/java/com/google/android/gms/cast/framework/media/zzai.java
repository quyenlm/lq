package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import java.util.Set;
import java.util.TimerTask;

final class zzai extends TimerTask {
    private /* synthetic */ RemoteMediaClient zzauF;
    private /* synthetic */ RemoteMediaClient.zzd zzauG;

    zzai(RemoteMediaClient.zzd zzd, RemoteMediaClient remoteMediaClient) {
        this.zzauG = zzd;
        this.zzauF = remoteMediaClient;
    }

    public final void run() {
        RemoteMediaClient.this.zza((Set<RemoteMediaClient.ProgressListener>) this.zzauG.zzauB);
        RemoteMediaClient.this.mHandler.postDelayed(this, this.zzauG.zzauC);
    }
}
