package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.BinderThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.internal.zzy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzbej extends zzctp implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static Api.zza<? extends zzctk, zzctl> zzaEV = zzctg.zzajS;
    private final Context mContext;
    private final Handler mHandler;
    private final Api.zza<? extends zzctk, zzctl> zzaAx;
    private zzq zzaCA;
    private zzctk zzaDh;
    private final boolean zzaEW;
    private zzbel zzaEX;
    private Set<Scope> zzame;

    @WorkerThread
    public zzbej(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaAx = zzaEV;
        this.zzaEW = true;
    }

    @WorkerThread
    public zzbej(Context context, Handler handler, @NonNull zzq zzq, Api.zza<? extends zzctk, zzctl> zza) {
        this.mContext = context;
        this.mHandler = handler;
        this.zzaCA = (zzq) zzbo.zzb(zzq, (Object) "ClientSettings must not be null");
        this.zzame = zzq.zzrn();
        this.zzaAx = zza;
        this.zzaEW = false;
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzc(zzctx zzctx) {
        ConnectionResult zzpz = zzctx.zzpz();
        if (zzpz.isSuccess()) {
            zzbr zzAx = zzctx.zzAx();
            ConnectionResult zzpz2 = zzAx.zzpz();
            if (!zzpz2.isSuccess()) {
                String valueOf = String.valueOf(zzpz2);
                Log.wtf("SignInCoordinator", new StringBuilder(String.valueOf(valueOf).length() + 48).append("Sign-in succeeded with resolve account failure: ").append(valueOf).toString(), new Exception());
                this.zzaEX.zzh(zzpz2);
                this.zzaDh.disconnect();
                return;
            }
            this.zzaEX.zzb(zzAx.zzrH(), this.zzame);
        } else {
            this.zzaEX.zzh(zzpz);
        }
        this.zzaDh.disconnect();
    }

    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zzaDh.zza(this);
    }

    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zzaEX.zzh(connectionResult);
    }

    @WorkerThread
    public final void onConnectionSuspended(int i) {
        this.zzaDh.disconnect();
    }

    @WorkerThread
    public final void zza(zzbel zzbel) {
        if (this.zzaDh != null) {
            this.zzaDh.disconnect();
        }
        if (this.zzaEW) {
            GoogleSignInOptions zzmO = zzy.zzaj(this.mContext).zzmO();
            this.zzame = zzmO == null ? new HashSet() : new HashSet(zzmO.zzmA());
            this.zzaCA = new zzq((Account) null, this.zzame, (Map<Api<?>, zzr>) null, 0, (View) null, (String) null, (String) null, zzctl.zzbCM);
        }
        this.zzaCA.zzc(Integer.valueOf(System.identityHashCode(this)));
        this.zzaDh = (zzctk) this.zzaAx.zza(this.mContext, this.mHandler.getLooper(), this.zzaCA, this.zzaCA.zzrt(), this, this);
        this.zzaEX = zzbel;
        this.zzaDh.connect();
    }

    @BinderThread
    public final void zzb(zzctx zzctx) {
        this.mHandler.post(new zzbek(this, zzctx));
    }

    public final void zzqI() {
        if (this.zzaDh != null) {
            this.zzaDh.disconnect();
        }
    }

    public final zzctk zzqy() {
        return this.zzaDh;
    }
}
