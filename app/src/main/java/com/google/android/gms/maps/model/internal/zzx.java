package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzx extends zzee implements zzw {
    public static zzw zzai(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
        return queryLocalInterface instanceof zzw ? (zzw) queryLocalInterface : new zzy(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzw zzy;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                remove();
                parcel2.writeNoException();
                break;
            case 2:
                clearTileCache();
                parcel2.writeNoException();
                break;
            case 3:
                String id = getId();
                parcel2.writeNoException();
                parcel2.writeString(id);
                break;
            case 4:
                setZIndex(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 5:
                float zIndex = getZIndex();
                parcel2.writeNoException();
                parcel2.writeFloat(zIndex);
                break;
            case 6:
                setVisible(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 7:
                boolean isVisible = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, isVisible);
                break;
            case 8:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzy = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ITileOverlayDelegate");
                    zzy = queryLocalInterface instanceof zzw ? (zzw) queryLocalInterface : new zzy(readStrongBinder);
                }
                boolean zza = zza(zzy);
                parcel2.writeNoException();
                zzef.zza(parcel2, zza);
                break;
            case 9:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            case 10:
                setFadeIn(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 11:
                boolean fadeIn = getFadeIn();
                parcel2.writeNoException();
                zzef.zza(parcel2, fadeIn);
                break;
            case 12:
                setTransparency(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 13:
                float transparency = getTransparency();
                parcel2.writeNoException();
                parcel2.writeFloat(transparency);
                break;
            default:
                return false;
        }
        return true;
    }
}
