package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.maps.model.LatLng;

public abstract class zzq extends zzee implements zzp {
    public static zzp zzaf(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
        return queryLocalInterface instanceof zzp ? (zzp) queryLocalInterface : new zzr(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        zzp zzr;
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
                setTitle(parcel.readString());
                parcel2.writeNoException();
                break;
            case 6:
                String title = getTitle();
                parcel2.writeNoException();
                parcel2.writeString(title);
                break;
            case 7:
                setSnippet(parcel.readString());
                parcel2.writeNoException();
                break;
            case 8:
                String snippet = getSnippet();
                parcel2.writeNoException();
                parcel2.writeString(snippet);
                break;
            case 9:
                setDraggable(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 10:
                boolean isDraggable = isDraggable();
                parcel2.writeNoException();
                zzef.zza(parcel2, isDraggable);
                break;
            case 11:
                showInfoWindow();
                parcel2.writeNoException();
                break;
            case 12:
                hideInfoWindow();
                parcel2.writeNoException();
                break;
            case 13:
                boolean isInfoWindowShown = isInfoWindowShown();
                parcel2.writeNoException();
                zzef.zza(parcel2, isInfoWindowShown);
                break;
            case 14:
                setVisible(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 15:
                boolean isVisible = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, isVisible);
                break;
            case 16:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    zzr = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IMarkerDelegate");
                    zzr = queryLocalInterface instanceof zzp ? (zzp) queryLocalInterface : new zzr(readStrongBinder);
                }
                boolean zzj = zzj(zzr);
                parcel2.writeNoException();
                zzef.zza(parcel2, zzj);
                break;
            case 17:
                int hashCodeRemote = hashCodeRemote();
                parcel2.writeNoException();
                parcel2.writeInt(hashCodeRemote);
                break;
            case 18:
                zzK(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 19:
                setAnchor(parcel.readFloat(), parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 20:
                setFlat(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 21:
                boolean isFlat = isFlat();
                parcel2.writeNoException();
                zzef.zza(parcel2, isFlat);
                break;
            case 22:
                setRotation(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 23:
                float rotation = getRotation();
                parcel2.writeNoException();
                parcel2.writeFloat(rotation);
                break;
            case 24:
                setInfoWindowAnchor(parcel.readFloat(), parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 25:
                setAlpha(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 26:
                float alpha = getAlpha();
                parcel2.writeNoException();
                parcel2.writeFloat(alpha);
                break;
            case 27:
                setZIndex(parcel.readFloat());
                parcel2.writeNoException();
                break;
            case 28:
                float zIndex = getZIndex();
                parcel2.writeNoException();
                parcel2.writeFloat(zIndex);
                break;
            case 29:
                setTag(IObjectWrapper.zza.zzM(parcel.readStrongBinder()));
                parcel2.writeNoException();
                break;
            case 30:
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
