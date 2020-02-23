package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public abstract class zzl extends zzee implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        IObjectWrapper iObjectWrapper = null;
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 2:
                IObjectWrapper zzty = zzty();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zzty);
                break;
            case 3:
                Bundle arguments = getArguments();
                parcel2.writeNoException();
                zzef.zzb(parcel2, arguments);
                break;
            case 4:
                int id = getId();
                parcel2.writeNoException();
                parcel2.writeInt(id);
                break;
            case 5:
                zzk zztz = zztz();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zztz);
                break;
            case 6:
                IObjectWrapper zztA = zztA();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zztA);
                break;
            case 7:
                boolean retainInstance = getRetainInstance();
                parcel2.writeNoException();
                zzef.zza(parcel2, retainInstance);
                break;
            case 8:
                String tag = getTag();
                parcel2.writeNoException();
                parcel2.writeString(tag);
                break;
            case 9:
                zzk zztB = zztB();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) zztB);
                break;
            case 10:
                int targetRequestCode = getTargetRequestCode();
                parcel2.writeNoException();
                parcel2.writeInt(targetRequestCode);
                break;
            case 11:
                boolean userVisibleHint = getUserVisibleHint();
                parcel2.writeNoException();
                zzef.zza(parcel2, userVisibleHint);
                break;
            case 12:
                IObjectWrapper view = getView();
                parcel2.writeNoException();
                zzef.zza(parcel2, (IInterface) view);
                break;
            case 13:
                boolean isAdded = isAdded();
                parcel2.writeNoException();
                zzef.zza(parcel2, isAdded);
                break;
            case 14:
                boolean isDetached = isDetached();
                parcel2.writeNoException();
                zzef.zza(parcel2, isDetached);
                break;
            case 15:
                boolean isHidden = isHidden();
                parcel2.writeNoException();
                zzef.zza(parcel2, isHidden);
                break;
            case 16:
                boolean isInLayout = isInLayout();
                parcel2.writeNoException();
                zzef.zza(parcel2, isInLayout);
                break;
            case 17:
                boolean isRemoving = isRemoving();
                parcel2.writeNoException();
                zzef.zza(parcel2, isRemoving);
                break;
            case 18:
                boolean isResumed = isResumed();
                parcel2.writeNoException();
                zzef.zza(parcel2, isResumed);
                break;
            case 19:
                boolean isVisible = isVisible();
                parcel2.writeNoException();
                zzef.zza(parcel2, isVisible);
                break;
            case 20:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = queryLocalInterface instanceof IObjectWrapper ? (IObjectWrapper) queryLocalInterface : new zzm(readStrongBinder);
                }
                zzC(iObjectWrapper);
                parcel2.writeNoException();
                break;
            case 21:
                setHasOptionsMenu(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 22:
                setMenuVisibility(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 23:
                setRetainInstance(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 24:
                setUserVisibleHint(zzef.zza(parcel));
                parcel2.writeNoException();
                break;
            case 25:
                startActivity((Intent) zzef.zza(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                break;
            case 26:
                startActivityForResult((Intent) zzef.zza(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                break;
            case 27:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
                    iObjectWrapper = queryLocalInterface2 instanceof IObjectWrapper ? (IObjectWrapper) queryLocalInterface2 : new zzm(readStrongBinder2);
                }
                zzD(iObjectWrapper);
                parcel2.writeNoException();
                break;
            default:
                return false;
        }
        return true;
    }
}
