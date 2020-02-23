package com.google.android.gms.internal;

import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.Status;

final class zzatv implements WeatherResult {
    private /* synthetic */ zzaud zzaok;

    zzatv(zzatu zzatu, zzaud zzaud) {
        this.zzaok = zzaud;
    }

    public final Status getStatus() {
        return this.zzaok.getStatus();
    }

    public final Weather getWeather() {
        if (this.zzaok.zznb() == null) {
            return null;
        }
        return this.zzaok.zznb().zzmZ();
    }
}
