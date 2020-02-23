package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import java.util.List;

public abstract class zze extends zzee implements zzd {
    public static zzd zzab(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
        return queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zzf(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzd zzf;
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
                setCenter((LatLng) zzef.zza(parcel, LatLng.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                LatLng center = getCenter();
                parcel2.writeNoException();
                zzef.zzb(parcel2, center);
                break;
            case 5:
                setRadius(parcel.readDouble());
                parcel2.writeNoException();
                break;
            case 6:
                double radius = getRadius();
                parcel2.writeNoException();
                parcel2.writeDouble(radius);
                break;
            case 7:
                setStrokeWidth(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 8:
                float strokeWidth = getStrokeWidth();
                parcel2.writeNoException();
                parcel2.writeFloat(strokeWidth);
                break;
            case 9:
                setStrokeColor(parcel.readInt());
                parcel2.writeNoException();
                break;
            case 10:
                int strokeColor = getStrokeColor();
                parcel2.writeNoException();
                parcel2.writeInt(strokeColor);
                break;
            case 11:
                setFillColor(parcel.readInt());
                parcel2.writeNoException();
                break;
            case 12:
                int fillColor = getFillColor();
                parcel2.writeNoException();
                parcel2.writeInt(fillColor);
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
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzf = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.ICircleDelegate");
                    zzf = queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zzf(readStrongBinder);
                }
                boolean zzb = zzb(zzf);
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 18:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            case 19:
                setClickable(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 20:
                boolean isClickable = isClickable();
                parcel2.writeNoException();
                zzef.zza(parcel2, isClickable);
                break;
            case 21:
                setStrokePattern(parcel.createTypedArrayList(PatternItem.CREATOR));
                parcel2.writeNoException();
                break;
            case 22:
                List<PatternItem> strokePattern = getStrokePattern();
                parcel2.writeNoException();
                parcel2.writeTypedList(strokePattern);
                break;
            case 23:
                setTag(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 24:
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
