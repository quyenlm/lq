package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatch;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

final class zzdi implements TurnBasedMultiplayer.InitiateMatchResult {
    private /* synthetic */ Status zzakB;

    zzdi(zzdh zzdh, Status status) {
        this.zzakB = status;
    }

    public final TurnBasedMatch getMatch() {
        return null;
    }

    public final Status getStatus() {
        return this.zzakB;
    }
}
