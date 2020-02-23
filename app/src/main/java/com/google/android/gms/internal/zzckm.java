package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.Payload;
import java.io.IOException;
import java.io.OutputStream;

public final class zzckm extends zzz<zzcnd> {
    private final long zzbwJ = ((long) hashCode());
    private zzcom zzbwK;

    public zzckm(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 54, zzq, connectionCallbacks, onConnectionFailedListener);
    }

    public final void disconnect() {
        if (isConnected()) {
            try {
                ((zzcnd) zzrf()).zza(new zzckk());
            } catch (RemoteException e) {
                Log.w("NearbyConnectionsClient", "Failed to notify client disconnect.", e);
            }
        }
        if (this.zzbwK != null) {
            this.zzbwK.shutdown();
            this.zzbwK = null;
        }
        super.disconnect();
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(@NonNull IInterface iInterface) {
        super.zza((zzcnd) iInterface);
        this.zzbwK = new zzcom();
    }

    public final void zza(zzbaz<Status> zzbaz, String[] strArr, Payload payload, boolean z) throws RemoteException {
        try {
            Pair<zzcoo, Pair<ParcelFileDescriptor, ParcelFileDescriptor>> zza = zzcoq.zza(payload);
            ((zzcnd) zzrf()).zza(new zzcov(new zzclj(zzbaz).asBinder(), strArr, (zzcoo) zza.first, z));
            if (zza.second != null) {
                Pair pair = (Pair) zza.second;
                this.zzbwK.zza(payload.asStream().asInputStream(), (OutputStream) new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor) pair.first), (OutputStream) new ParcelFileDescriptor.AutoCloseOutputStream((ParcelFileDescriptor) pair.second), payload.getId());
            }
        } catch (IOException e) {
            zzbaz.setResult(new Status(ConnectionsStatusCodes.STATUS_PAYLOAD_IO_ERROR));
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.nearby.internal.connection.INearbyConnectionService");
        return queryLocalInterface instanceof zzcnd ? (zzcnd) queryLocalInterface : new zzcne(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.nearby.connection.service.START";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.android.gms.nearby.internal.connection.INearbyConnectionService";
    }

    public final void zzj(zzbaz<Status> zzbaz, String str) throws RemoteException {
        ((zzcnd) zzrf()).zza(new zzcor(new zzclj(zzbaz).asBinder(), str));
    }

    /* access modifiers changed from: protected */
    public final Bundle zzmo() {
        Bundle bundle = new Bundle();
        bundle.putLong("clientId", this.zzbwJ);
        return bundle;
    }
}
