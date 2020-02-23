package com.google.firebase.database.connection.idl;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzl extends zzee implements zzk {
    public zzl() {
        attachInterface(this, "com.google.firebase.database.connection.idl.IConnectionAuthTokenProvider");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzn zzp;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        boolean zza = zzef.zza(parcel);
        IBinder readStrongBinder = parcel.readStrongBinder();
        if (readStrongBinder == null) {
            zzp = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.firebase.database.connection.idl.IGetTokenCallback");
            zzp = queryLocalInterface instanceof zzn ? (zzn) queryLocalInterface : new zzp(readStrongBinder);
        }
        zza(zza, zzp);
        parcel2.writeNoException();
        return true;
    }
}
