package com.google.android.gms.cast.framework.media;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public interface zzd extends IInterface {

    public static abstract class zza extends zzee implements zzd {
        public static zzd zzE(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.framework.media.IMediaNotificationService");
            return queryLocalInterface instanceof zzd ? (zzd) queryLocalInterface : new zze(iBinder);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            switch (i) {
                case 1:
                    onCreate();
                    parcel2.writeNoException();
                    break;
                case 2:
                    int onStartCommand = onStartCommand((Intent) zzef.zza(parcel, Intent.CREATOR), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(onStartCommand);
                    break;
                case 3:
                    IBinder onBind = onBind((Intent) zzef.zza(parcel, Intent.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(onBind);
                    break;
                case 4:
                    onDestroy();
                    parcel2.writeNoException();
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    IBinder onBind(Intent intent) throws RemoteException;

    void onCreate() throws RemoteException;

    void onDestroy() throws RemoteException;

    int onStartCommand(Intent intent, int i, int i2) throws RemoteException;
}
