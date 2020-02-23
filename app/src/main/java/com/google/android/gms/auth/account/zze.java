package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zze extends zzee implements zzd {
    public static zzd zzz(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountService");
        return queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zzf(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zza zzc;
        zza zza = null;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzO(zzef.zza(parcel));
                break;
            case 2:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountCallback");
                    zza = queryLocalInterface instanceof zza ? (zza) queryLocalInterface : new zzc(readStrongBinder);
                }
                zza(zza, parcel.readString());
                break;
            case 3:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 == null) {
                    zzc = null;
                } else {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.android.gms.auth.account.IWorkAccountCallback");
                    zzc = queryLocalInterface2 instanceof zza ? (zza) queryLocalInterface2 : new zzc(readStrongBinder2);
                }
                zza(zzc, (Account) zzef.zza(parcel, Account.CREATOR));
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
