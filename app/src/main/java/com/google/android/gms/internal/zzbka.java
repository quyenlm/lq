package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import com.google.android.gms.awareness.AwarenessOptions;
import com.google.android.gms.awareness.fence.FenceQueryResult;
import com.google.android.gms.awareness.fence.zza;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zze;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.util.zzd;
import java.util.ArrayList;

public final class zzbka extends zzz<zzbkj> {
    private static zzel zzaLu = zzel.zzrI;
    private final zzbkb zzaLv;
    private zzes<zza, zzbit> zzaLw;
    private final Looper zzrM;

    public zzbka(Context context, Looper looper, zzq zzq, AwarenessOptions awarenessOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 47, zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzrM = looper;
        String str = zzq.getAccount() == null ? "@@ContextManagerNullAccount@@" : zzq.getAccount().name;
        this.zzaLv = awarenessOptions == null ? new zzbkb(str, context.getPackageName(), Process.myUid(), context.getPackageName(), zzd.zzA(context, context.getPackageName()), 3, (String) null, (String) null, -1, Process.myPid()) : zzbkb.zza(context, str, awarenessOptions);
    }

    public final void zza(zzbaz<zzaud> zzbaz, zzaub zzaub) throws RemoteException {
        zzre();
        ((zzbkj) zzrf()).zza((zzbkh) zzbkd.zzd(zzbaz), this.zzaLv.packageName, this.zzaLv.zzaLx, this.zzaLv.moduleId, zzaub);
    }

    public final void zza(zzbaz<FenceQueryResult> zzbaz, zzbja zzbja) throws RemoteException {
        zzre();
        ((zzbkj) zzrf()).zza((zzbkh) zzbkd.zze(zzbaz), this.zzaLv.packageName, this.zzaLv.zzaLx, this.zzaLv.moduleId, zzbja);
    }

    public final void zza(zzbaz<Status> zzbaz, zzbjj zzbjj) throws RemoteException {
        zzre();
        if (this.zzaLw == null) {
            this.zzaLw = new zzes<>(this.zzrM, zzbit.zzaKT);
        }
        zzes<zza, zzbit> zzes = this.zzaLw;
        ArrayList arrayList = zzbjj.zzaLj;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            zzbjt zzbjt = (zzbjt) obj;
            if (zzbjt.zzaLp == null) {
                zza zza = zzbjt.zzaLq;
            }
        }
        ((zzbkj) zzrf()).zza((zzbkh) zzbkd.zza(zzbaz, (zzbkg) null), this.zzaLv.packageName, this.zzaLv.zzaLx, this.zzaLv.moduleId, zzbjj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.contextmanager.internal.IContextManagerService");
        return queryLocalInterface instanceof zzbkj ? (zzbkj) queryLocalInterface : new zzbkk(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.contextmanager.service.ContextManagerService.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.contextmanager.internal.IContextManagerService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        Bundle bundle = new Bundle();
        bundle.putByteArray("com.google.android.contextmanager.service.args", zze.zza(this.zzaLv));
        return bundle;
    }

    public final boolean zzrg() {
        return false;
    }
}
