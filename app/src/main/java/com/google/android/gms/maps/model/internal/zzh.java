package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public abstract class zzh extends zzee implements zzg {
    public static zzg zzac(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
        return queryLocalInterface instanceof zzg ? (zzg) queryLocalInterface : new zzi(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzg zzi;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                remove();
                parcel2.writeNoException();
                break;
            case 2:
                String id = getId();
                parcel2.writeNoException();
                parcel2.writeString(id);
                break;
            case 3:
                setPosition((LatLng) zzef.zza(parcel, LatLng.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                LatLng position = getPosition();
                parcel2.writeNoException();
                zzef.zzb(parcel2, position);
                break;
            case 5:
                setDimensions(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 6:
                zzf(parcel.readFloat(), parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 7:
                float width = getWidth();
                parcel2.writeNoException();
                parcel2.writeFloat(width);
                break;
            case 8:
                float height = getHeight();
                parcel2.writeNoException();
                parcel2.writeFloat(height);
                break;
            case 9:
                setPositionFromBounds((LatLngBounds) zzef.zza(parcel, LatLngBounds.CREATOR));
                parcel2.writeNoException();
                break;
            case 10:
                LatLngBounds bounds = getBounds();
                parcel2.writeNoException();
                zzef.zzb(parcel2, bounds);
                break;
            case 11:
                setBearing(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 12:
                float bearing = getBearing();
                parcel2.writeNoException();
                parcel2.writeFloat(bearing);
                break;
            case 13:
                setZIndex(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 14:
                float zIndex = getZIndex();
                parcel2.writeNoException();
                parcel2.writeFloat(zIndex);
                break;
            case 15:
                setVisible(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 16:
                boolean isVisible = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, isVisible);
                break;
            case 17:
                setTransparency(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 18:
                float transparency = getTransparency();
                parcel2.writeNoException();
                parcel2.writeFloat(transparency);
                break;
            case 19:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzi = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IGroundOverlayDelegate");
                    zzi = queryLocalInterface instanceof zzg ? (zzg) queryLocalInterface : new zzi(readStrongBinder);
                }
                boolean zzb = zzb(zzi);
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 20:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            case 21:
                zzJ(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 22:
                setClickable(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 23:
                boolean isClickable = isClickable();
                parcel2.writeNoException();
                zzef.zza(parcel2, isClickable);
                break;
            case 24:
                setTag(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 25:
                IObjectWrapper tag = getTag();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) tag);
                break;
            default:
                return false;
        }
        return true;
    }
}
