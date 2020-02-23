package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzbh implements RemoteMediaPlayer.MediaChannelResult {
    private /* synthetic */ Status zzakB;

    zzbh(RemoteMediaPlayer.zzb zzb, Status status) {
        this.zzakB = status;
    }

    public final JSONObject getCustomData() {
        return null;
    }

    public final Status getStatus() {
        return this.zzakB;
    }
}
