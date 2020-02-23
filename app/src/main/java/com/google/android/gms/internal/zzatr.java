package com.google.android.gms.internal;

import android.location.Location;
import com.google.android.gms.awareness.snapshot.LocationResult;
import com.google.android.gms.common.api.Status;

final class zzatr implements LocationResult {
    private /* synthetic */ zzaud zzaok;

    zzatr(zzatq zzatq, zzaud zzaud) {
        this.zzaok = zzaud;
    }

    public final Location getLocation() {
        if (this.zzaok.zznb() == null) {
            return null;
        }
        return this.zzaok.zznb().getLocation();
    }

    public final Status getStatus() {
        return this.zzaok.getStatus();
    }
}
