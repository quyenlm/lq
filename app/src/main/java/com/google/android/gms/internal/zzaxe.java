package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

public abstract class zzaxe extends zzaxg<GameManagerClient.GameManagerResult> {
    final /* synthetic */ zzawy zzaxd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzaxe(zzawy zzawy) {
        super(zzawy);
        this.zzaxd = zzawy;
        this.zzarw = new zzaxf(this, zzawy);
    }

    public static GameManagerClient.GameManagerResult zzj(Status status) {
        return new zzaxk(status, (String) null, -1, (JSONObject) null);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return zzj(status);
    }
}
