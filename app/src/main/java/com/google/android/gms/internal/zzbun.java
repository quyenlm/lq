package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.util.zzj;
import java.util.HashSet;
import java.util.Set;

public abstract class zzbun<T extends IInterface> extends zzz<T> {
    protected zzbun(Context context, Looper looper, int i, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener, zzq zzq) {
        super(context, looper, i, zzq, connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzb(Set<Scope> set) {
        HashSet hashSet = new HashSet(set.size());
        for (Scope next : set) {
            Scope scope = next.equals(new Scope(Scopes.FITNESS_ACTIVITY_READ)) ? new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE) : next.equals(new Scope(Scopes.FITNESS_LOCATION_READ)) ? new Scope(Scopes.FITNESS_LOCATION_READ_WRITE) : next.equals(new Scope(Scopes.FITNESS_BODY_READ)) ? new Scope(Scopes.FITNESS_BODY_READ_WRITE) : next.equals(new Scope(Scopes.FITNESS_NUTRITION_READ)) ? new Scope(Scopes.FITNESS_NUTRITION_READ_WRITE) : next.equals(new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.read")) ? new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.write") : next.equals(new Scope("https://www.googleapis.com/auth/fitness.blood_glucose.read")) ? new Scope("https://www.googleapis.com/auth/fitness.blood_glucose.write") : next.equals(new Scope("https://www.googleapis.com/auth/fitness.oxygen_saturation.read")) ? new Scope("https://www.googleapis.com/auth/fitness.oxygen_saturation.write") : next.equals(new Scope("https://www.googleapis.com/auth/fitness.body_temperature.read")) ? new Scope("https://www.googleapis.com/auth/fitness.body_temperature.write") : next.equals(new Scope("https://www.googleapis.com/auth/fitness.reproductive_health.read")) ? new Scope("https://www.googleapis.com/auth/fitness.reproductive_health.write") : next;
            if (scope.equals(next) || !set.contains(scope)) {
                hashSet.add(next);
            }
        }
        return hashSet;
    }

    public abstract T zzd(IBinder iBinder);

    public abstract String zzdb();

    public abstract String zzdc();

    public final boolean zzmv() {
        return !zzj.zzaG(getContext());
    }

    public final boolean zzrg() {
        return true;
    }
}
