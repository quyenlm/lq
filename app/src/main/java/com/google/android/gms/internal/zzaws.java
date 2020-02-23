package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import org.json.JSONObject;

final class zzaws implements View.OnClickListener {
    private /* synthetic */ zzawr zzavY;

    zzaws(zzawr zzawr) {
        this.zzavY = zzawr;
    }

    public final void onClick(View view) {
        RemoteMediaClient zza = this.zzavY.getRemoteMediaClient();
        if (zza != null && zza.hasMediaSession()) {
            zza.queuePrev((JSONObject) null);
        }
    }
}
