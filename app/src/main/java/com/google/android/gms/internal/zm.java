package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;

public final class zm extends zzz<zv> {
    public zm(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 131, zzq, connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.dynamiclinks.internal.IDynamicLinksService");
        return queryLocalInterface instanceof zv ? (zv) queryLocalInterface : new zw(iBinder);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdb() {
        return "com.google.firebase.dynamiclinks.service.START";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdc() {
        return "com.google.firebase.dynamiclinks.internal.IDynamicLinksService";
    }
}
