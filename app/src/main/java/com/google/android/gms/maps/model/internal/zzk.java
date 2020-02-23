package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import java.util.List;

public abstract class zzk extends zzee implements zzj {
    public static zzj zzad(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        return queryLocalInterface instanceof zzj ? (zzj) queryLocalInterface : new zzl(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzj zzl;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                int activeLevelIndex = getActiveLevelIndex();
                parcel2.writeNoException();
                parcel2.writeInt(activeLevelIndex);
                break;
            case 2:
                int defaultLevelIndex = getDefaultLevelIndex();
                parcel2.writeNoException();
                parcel2.writeInt(defaultLevelIndex);
                break;
            case 3:
                List<IBinder> levels = getLevels();
                parcel2.writeNoException();
                parcel2.writeBinderList(levels);
                break;
            case 4:
                boolean isUnderground = isUnderground();
                parcel2.writeNoException();
                zzef.zza(parcel2, isUnderground);
                break;
            case 5:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzl = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
                    zzl = queryLocalInterface instanceof zzj ? (zzj) queryLocalInterface : new zzl(readStrongBinder);
                }
                boolean zzb = zzb(zzl);
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 6:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            default:
                return false;
        }
        return true;
    }
}
