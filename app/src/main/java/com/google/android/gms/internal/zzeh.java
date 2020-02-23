package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

public abstract class zzeh extends zzee implements zzeg {
    public static zzeg zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.auth.IAuthManagerService");
        return queryLocalInterface instanceof zzeg ? (zzeg) queryLocalInterface : new zzei(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                Bundle zza = zza(parcel.readString(), parcel.readString(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza);
                break;
            case 2:
                Bundle zza2 = zza(parcel.readString(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza2);
                break;
            case 3:
                AccountChangeEventsResponse zza3 = zza((AccountChangeEventsRequest) zzef.zza(parcel, AccountChangeEventsRequest.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza3);
                break;
            case 5:
                Bundle zza4 = zza((Account) zzef.zza(parcel, Account.CREATOR), parcel.readString(), (Bundle) zzef.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza4);
                break;
            case 6:
                Bundle zza5 = zza((Bundle) zzef.zza(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza5);
                break;
            case 7:
                Bundle zza6 = zza((Account) zzef.zza(parcel, Account.CREATOR));
                parcel2.writeNoException();
                zzef.zzb(parcel2, zza6);
                break;
            default:
                return false;
        }
        return true;
    }
}
