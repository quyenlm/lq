package com.google.android.gms.internal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.Set;

final class zzaen implements GoogleApiClient.ConnectionCallbacks {
    final /* synthetic */ zzajg zzXk;
    final /* synthetic */ Set zzXl;
    final /* synthetic */ zzael zzXm;

    zzaen(zzael zzael, Set set, zzajg zzajg) {
        this.zzXm = zzael;
        this.zzXl = set;
        this.zzXk = zzajg;
    }

    public final void onConnected(@Nullable Bundle bundle) {
        try {
            this.zzXm.zzXi.schedule(new zzaeo(this), ((Long) zzbs.zzbL().zzd(zzmo.zzGd)).longValue());
        } catch (IllegalStateException e) {
            String valueOf = String.valueOf(e.getMessage());
            zzaes.zzaC(valueOf.length() != 0 ? "Cannot schedule thread: ".concat(valueOf) : new String("Cannot schedule thread: "));
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
