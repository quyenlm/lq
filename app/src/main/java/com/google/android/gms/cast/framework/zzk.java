package com.google.android.gms.cast.framework;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public interface zzk extends IInterface {

    public static abstract class zza extends zzee implements zzk {
        public static zzk zzA(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.framework.ICastContext");
            return queryLocalInterface instanceof zzk ? (zzk) queryLocalInterface : new zzl(iBinder);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            zzf zzf = null;
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            switch (i) {
                case 1:
                    Bundle zznr = zznr();
                    parcel2.writeNoException();
                    zzef.zzb(parcel2, zznr);
                    break;
                case 2:
                    boolean isAppVisible = isAppVisible();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, isAppVisible);
                    break;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.cast.framework.IAppVisibilityListener");
                        zzf = queryLocalInterface instanceof zzf ? (zzf) queryLocalInterface : new zzh(readStrongBinder);
                    }
                    zza(zzf);
                    parcel2.writeNoException();
                    break;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.android.gms.cast.framework.IAppVisibilityListener");
                        zzf = queryLocalInterface2 instanceof zzf ? (zzf) queryLocalInterface2 : new zzh(readStrongBinder2);
                    }
                    zzb(zzf);
                    parcel2.writeNoException();
                    break;
                case 5:
                    zzw zzns = zzns();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, (IInterface) zzns);
                    break;
                case 6:
                    zzq zznt = zznt();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, (IInterface) zznt);
                    break;
                case 7:
                    destroy();
                    parcel2.writeNoException();
                    break;
                case 8:
                    zzx(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    break;
                case 9:
                    zzy(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    break;
                case 10:
                    IObjectWrapper zznu = zznu();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, (IInterface) zznu);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    void destroy() throws RemoteException;

    boolean isAppVisible() throws RemoteException;

    void zza(zzf zzf) throws RemoteException;

    void zzb(zzf zzf) throws RemoteException;

    Bundle zznr() throws RemoteException;

    zzw zzns() throws RemoteException;

    zzq zznt() throws RemoteException;

    IObjectWrapper zznu() throws RemoteException;

    void zzx(IObjectWrapper iObjectWrapper) throws RemoteException;

    void zzy(IObjectWrapper iObjectWrapper) throws RemoteException;
}
