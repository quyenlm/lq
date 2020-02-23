package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import java.util.Set;

public abstract class zzz<T extends IInterface> extends zzd<T> implements Api.zze, zzad {
    private final zzq zzaCA;
    private final Account zzajb;
    private final Set<Scope> zzame;

    protected zzz(Context context, Looper looper, int i, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzae.zzaC(context), GoogleApiAvailability.getInstance(), i, zzq, (GoogleApiClient.ConnectionCallbacks) zzbo.zzu(connectionCallbacks), (GoogleApiClient.OnConnectionFailedListener) zzbo.zzu(onConnectionFailedListener));
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private zzz(Context context, Looper looper, zzae zzae, GoogleApiAvailability googleApiAvailability, int i, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, zzae, googleApiAvailability, i, connectionCallbacks == null ? null : new zzaa(connectionCallbacks), onConnectionFailedListener == null ? null : new zzab(onConnectionFailedListener), zzq.zzrr());
        this.zzaCA = zzq;
        this.zzajb = zzq.getAccount();
        Set<Scope> zzro = zzq.zzro();
        Set<Scope> zzb = zzb(zzro);
        for (Scope contains : zzb) {
            if (!zzro.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        this.zzame = zzb;
    }

    public final Account getAccount() {
        return this.zzajb;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Set<Scope> zzb(@NonNull Set<Scope> set) {
        return set;
    }

    public zzc[] zzrd() {
        return new zzc[0];
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzrh() {
        return this.zzame;
    }

    /* access modifiers changed from: protected */
    public final zzq zzry() {
        return this.zzaCA;
    }
}
