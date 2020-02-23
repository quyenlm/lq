package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzaxx;
import java.io.IOException;
import org.json.JSONObject;

final class zzat extends RemoteMediaPlayer.zzb {
    private /* synthetic */ RemoteMediaPlayer zzaqV;
    private /* synthetic */ GoogleApiClient zzaqW;
    private /* synthetic */ long zzarb;
    private /* synthetic */ JSONObject zzarc;
    private /* synthetic */ MediaInfo zzarj;
    private /* synthetic */ boolean zzark;
    private /* synthetic */ long[] zzarl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzat(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaInfo mediaInfo, boolean z, long j, long[] jArr, JSONObject jSONObject) {
        super(googleApiClient);
        this.zzaqV = remoteMediaPlayer;
        this.zzaqW = googleApiClient2;
        this.zzarj = mediaInfo;
        this.zzark = z;
        this.zzarb = j;
        this.zzarl = jArr;
        this.zzarc = jSONObject;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzaxx zzaxx) {
        synchronized (this.zzaqV.mLock) {
            this.zzaqV.zzaqQ.zzb(this.zzaqW);
            try {
                this.zzaqV.zzaqP.zza(this.zzarw, this.zzarj, this.zzark, this.zzarb, this.zzarl, this.zzarc);
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (IOException e) {
                setResult((RemoteMediaPlayer.MediaChannelResult) zzb(new Status(2100)));
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
            } catch (Throwable th) {
                this.zzaqV.zzaqQ.zzb((GoogleApiClient) null);
                throw th;
            }
        }
    }
}
