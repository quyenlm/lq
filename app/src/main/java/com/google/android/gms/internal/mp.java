package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.firebase.crash.FirebaseCrash;
import java.util.concurrent.ExecutorService;

public final class mp {
    private final AppMeasurement zzbYo;

    private mp(AppMeasurement appMeasurement) {
        this.zzbYo = appMeasurement;
    }

    @Nullable
    public static mp zzbE(Context context) {
        try {
            return new mp(AppMeasurement.getInstance(context));
        } catch (NoClassDefFoundError e) {
            String valueOf = String.valueOf(e);
            Log.w("FirebaseCrashAnalytics", new StringBuilder(String.valueOf(valueOf).length() + 50).append("Unable to log event, missing measurement library: ").append(valueOf).toString());
            return null;
        }
    }

    public final void zza(@NonNull Context context, @NonNull ExecutorService executorService, @Nullable FirebaseCrash.zza zza) {
        this.zzbYo.registerOnMeasurementEventListener(new mo(context, executorService, zza));
    }

    public final void zza(boolean z, long j) {
        Bundle bundle = new Bundle();
        if (z) {
            bundle.putInt(AppMeasurement.Param.FATAL, 1);
        } else {
            bundle.putInt(AppMeasurement.Param.FATAL, 0);
        }
        bundle.putLong("timestamp", j);
        this.zzbYo.logEventInternal(AppMeasurement.CRASH_ORIGIN, AppMeasurement.Event.APP_EXCEPTION, bundle);
    }
}
