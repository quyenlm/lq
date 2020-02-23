package com.google.android.gms.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;

final class zzf implements Games.GetServerAuthCodeResult {
    private /* synthetic */ Status zzakB;

    zzf(Games.zzc zzc, Status status) {
        this.zzakB = status;
    }

    public final String getCode() {
        return null;
    }

    public final Status getStatus() {
        return this.zzakB;
    }
}
