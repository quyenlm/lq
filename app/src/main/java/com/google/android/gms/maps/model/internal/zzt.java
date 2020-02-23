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

public abstract class zzt extends zzee implements zzs {
    public static zzs zzag(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
        return queryLocalInterface instanceof zzs ? (zzs) queryLocalInterface : new zzu(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzs zzu;
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
                setPoints(parcel.createTypedArrayList(LatLng.CREATOR));
                parcel2.writeNoException();
                break;
            case 4:
                List<LatLng> points = getPoints();
                parcel2.writeNoException();
                parcel2.writeTypedList(points);
                break;
            case 5:
                setHoles(zzef.zzb(parcel));
                parcel2.writeNoException();
                break;
            case 6:
                List holes = getHoles();
                parcel2.writeNoException();
                parcel2.writeList(holes);
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
                setGeodesic(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 18:
                boolean isGeodesic = isGeodesic();
                parcel2.writeNoException();
                zzef.zza(parcel2, isGeodesic);
                break;
            case 19:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzu = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IPolygonDelegate");
                    zzu = queryLocalInterface instanceof zzs ? (zzs) queryLocalInterface : new zzu(readStrongBinder);
                }
                boolean zzb = zzb(zzu);
                parcel2.writeNoException();
                zzef.zza(parcel2, zzb);
                break;
            case 20:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            case 21:
                setClickable(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 22:
                boolean isClickable = isClickable();
                parcel2.writeNoException();
                zzef.zza(parcel2, isClickable);
                break;
            case 23:
                setStrokeJointType(parcel.readInt());
                parcel2.writeNoException();
                break;
            case 24:
                int strokeJointType = getStrokeJointType();
                parcel2.writeNoException();
                parcel2.writeInt(strokeJointType);
                break;
            case 25:
                setStrokePattern(parcel.createTypedArrayList(PatternItem.CREATOR));
                parcel2.writeNoException();
                break;
            case 26:
                List<PatternItem> strokePattern = getStrokePattern();
                parcel2.writeNoException();
                parcel2.writeTypedList(strokePattern);
                break;
            case 27:
                setTag(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 28:
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
