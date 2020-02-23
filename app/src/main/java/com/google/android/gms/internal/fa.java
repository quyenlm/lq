package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

public final class fa extends zzed implements ez {
    fa(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
    }

    public final ex zza(IObjectWrapper iObjectWrapper, eu euVar) throws RemoteException {
        ex eyVar;
        Parcel zzZ = zzZ();
        zzef.zza(zzZ, (IInterface) iObjectWrapper);
        zzef.zza(zzZ, (Parcelable) euVar);
        Parcel zza = zza(1, zzZ);
        IBinder readStrongBinder = zza.readStrongBinder();
        if (readStrongBinder == null) {
            eyVar = null;
        } else {
            IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetector");
            eyVar = queryLocalInterface instanceof ex ? (ex) queryLocalInterface : new ey(readStrongBinder);
        }
        zza.recycle();
        return eyVar;
    }
}
