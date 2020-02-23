package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.RemoteException;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.lang.ref.WeakReference;

final class zzaqf extends zzaqa<AppInviteInvitationResult> {
    /* access modifiers changed from: private */
    public final WeakReference<Activity> zzaka;
    /* access modifiers changed from: private */
    public final boolean zzakb;
    private final Intent zzakc;

    public zzaqf(zzapy zzapy, GoogleApiClient googleApiClient, Activity activity, boolean z) {
        super(googleApiClient);
        this.zzakb = z;
        this.zzaka = new WeakReference<>(activity);
        this.zzakc = activity != null ? activity.getIntent() : null;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzaqh zzaqh = (zzaqh) zzb;
        if (AppInviteReferral.hasReferral(this.zzakc)) {
            setResult(new zzaqi(Status.zzaBm, this.zzakc));
            zzaqh.zza((zzaqj) null);
            return;
        }
        zzaqh.zza(new zzaqg(this));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzaqi(status, new Intent());
    }
}
