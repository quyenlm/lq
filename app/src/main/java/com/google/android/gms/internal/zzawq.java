package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import org.json.JSONObject;

final class zzawq implements View.OnClickListener {
    private /* synthetic */ zzawp zzavX;

    zzawq(zzawp zzawp) {
        this.zzavX = zzawp;
    }

    public final void onClick(View view) {
        RemoteMediaClient zza = this.zzavX.getRemoteMediaClient();
        if (zza != null && zza.hasMediaSession()) {
            zza.queueNext((JSONObject) null);
        }
    }
}
