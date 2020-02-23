package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzayt;
import org.json.JSONObject;

final class zzbg implements zzayt {
    private /* synthetic */ RemoteMediaPlayer.zzb zzarx;

    zzbg(RemoteMediaPlayer.zzb zzb) {
        this.zzarx = zzb;
    }

    public final void zza(long j, int i, Object obj) {
        this.zzarx.setResult(new RemoteMediaPlayer.zzc(new Status(i), obj instanceof JSONObject ? (JSONObject) obj : null));
    }

    public final void zzx(long j) {
        this.zzarx.setResult((RemoteMediaPlayer.MediaChannelResult) this.zzarx.zzb(new Status(2103)));
    }
}
