package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.stats.PlayerStats;
import com.google.android.gms.games.stats.Stats;

final class zzct implements Stats.LoadPlayerStatsResult {
    private /* synthetic */ Status zzakB;

    zzct(zzcs zzcs, Status status) {
        this.zzakB = status;
    }

    public final PlayerStats getPlayerStats() {
        return null;
    }

    public final Status getStatus() {
        return this.zzakB;
    }

    public final void release() {
    }
}
