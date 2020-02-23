package com.google.android.gms.cast.framework;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzef;

public final class zzl extends zzed implements zzk {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.cast.framework.ICastContext");
    }

    public final void destroy() throws RemoteException {
        zzb(7, zzZ());
    }

    public final boolean isAppVisible() throws RemoteException {
        Parcel zza = zza(2, zzZ());
        boolean zza2 = zzef.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void zza(zzf zzf) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) zzf);
        zzb(3, zzZ);
    }

    public final void zzb(zzf zzf) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) zzf);
        zzb(4, zzZ);
    }

    public final Bundle zznr() throws RemoteException {
        Parcel zza = zza(1, zzZ());
        Bundle bundle = (Bundle) zzef.zza(zza, Bundle.CREATOR);
        zza.recycle();
        return bundle;
    }

    public final zzw zzns() throws RemoteException {
        zzw zzx;
        Parcel zza = zza(5, zzZ());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzx = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.cast.framework.ISessionManager");
            zzx = queryLocalInterface instanceof zzw ? (zzw) queryLocalInterface : new zzx(readStrongBinder);
        }
        zza.recycle();
        return zzx;
    }

    public final zzq zznt() throws RemoteException {
        zzq zzr;
        Parcel zza = zza(6, zzZ());
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            zzr = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.cast.framework.IDiscoveryManager");
            zzr = queryLocalInterface instanceof zzq ? (zzq) queryLocalInterface : new zzr(readStrongBinder);
        }
        zza.recycle();
        return zzr;
    }

    public final IObjectWrapper zznu() throws RemoteException {
        Parcel zza = zza(10, zzZ());
        IObjectWrapper zzM = IObjectWrapper.zza.zzM(zza.readStrongBinder());
        zza.recycle();
        return zzM;
    }

    public final void zzx(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzb(8, zzZ);
    }

    public final void zzy(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzb(9, zzZ);
    }
}
