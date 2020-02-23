package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

final class zzawj implements View.OnClickListener {
    private /* synthetic */ zzawi zzavO;

    zzawj(zzawi zzawi) {
        this.zzavO = zzawi;
    }

    public final void onClick(View view) {
        RemoteMediaClient zza = this.zzavO.getRemoteMediaClient();
        if (zza != null && zza.hasMediaSession()) {
            zza.togglePlayback();
        }
    }
}
