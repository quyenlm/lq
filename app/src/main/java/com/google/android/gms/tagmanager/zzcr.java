package com.google.android.gms.tagmanager;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzcr extends zzee implements zzcq {
    public zzcr() {
        attachInterface(this, "com.google.android.gms.tagmanager.ITagManagerApi");
    }

    public static zzcq asInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.tagmanager.ITagManagerApi");
        return queryLocalInterface instanceof zzcq ? (zzcq) queryLocalInterface : new zzcs(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzcn zzcp;
        zzcn zzcp2;
        zzce zzce = null;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IObjectWrapper zzM = IObjectWrapper.zza.zzM(parcel.readStrongBinder());
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzcp2 = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.tagmanager.IMeasurementProxy");
                    zzcp2 = queryLocalInterface instanceof zzcn ? (zzcn) queryLocalInterface : new zzcp(readStrongBinder);
                }
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.android.gms.tagmanager.ICustomEvaluatorProxy");
                    zzce = queryLocalInterface2 instanceof zzce ? (zzce) queryLocalInterface2 : new zzcg(readStrongBinder2);
                }
                initialize(zzM, zzcp2, zzce);
                break;
            case 2:
                preview((Intent) zzef.zza(parcel, Intent.CREATOR), IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                break;
            case 3:
                Intent intent = (Intent) zzef.zza(parcel, Intent.CREATOR);
                IObjectWrapper zzM2 = IObjectWrapper.zza.zzM(parcel.readStrongBinder());
                IObjectWrapper zzM3 = IObjectWrapper.zza.zzM(parcel.readStrongBinder());
                IBinder readStrongBinder3 = parcel.readStrongBinder();
                if (readStrongBinder3 == null) {
                    zzcp = null;
                } else {
                    IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.google.android.gms.tagmanager.IMeasurementProxy");
                    zzcp = queryLocalInterface3 instanceof zzcn ? (zzcn) queryLocalInterface3 : new zzcp(readStrongBinder3);
                }
                IBinder readStrongBinder4 = parcel.readStrongBinder();
                if (readStrongBinder4 != null) {
                    IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface("com.google.android.gms.tagmanager.ICustomEvaluatorProxy");
                    zzce = queryLocalInterface4 instanceof zzce ? (zzce) queryLocalInterface4 : new zzcg(readStrongBinder4);
                }
                previewIntent(intent, zzM2, zzM3, zzcp, zzce);
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
