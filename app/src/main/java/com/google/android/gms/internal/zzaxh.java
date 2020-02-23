package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public abstract class zzaxh extends zzaxg<GameManagerClient.GameManagerInstanceResult> {
    final /* synthetic */ zzawy zzaxd;
    /* access modifiers changed from: private */
    public GameManagerClient zzaxl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzaxh(zzawy zzawy, GameManagerClient gameManagerClient) {
        super(zzawy);
        this.zzaxd = zzawy;
        this.zzaxl = gameManagerClient;
        this.zzarw = new zzaxi(this, zzawy);
    }

    public static GameManagerClient.GameManagerInstanceResult zzk(Status status) {
        return new zzaxj(status, (GameManagerClient) null);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return zzk(status);
    }
}
