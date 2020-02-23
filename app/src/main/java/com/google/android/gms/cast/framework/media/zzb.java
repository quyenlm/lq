package com.google.android.gms.cast.framework.media;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzef;

public interface zzb extends IInterface {

    public static abstract class zza extends zzee implements zzb {
        public zza() {
            attachInterface(this, "com.google.android.gms.cast.framework.media.IImagePicker");
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            switch (i) {
                case 1:
                    WebImage onPickImage = onPickImage((MediaMetadata) zzef.zza(parcel, MediaMetadata.CREATOR), parcel.readInt());
                    parcel2.writeNoException();
                    zzef.zzb(parcel2, onPickImage);
                    break;
                case 2:
                    IObjectWrapper zznT = zznT();
                    parcel2.writeNoException();
                    zzef.zza(parcel2, (IInterface) zznT);
                    break;
                case 3:
                    int zznm = zznm();
                    parcel2.writeNoException();
                    parcel2.writeInt(zznm);
                    break;
                case 4:
                    WebImage zza = zza((MediaMetadata) zzef.zza(parcel, MediaMetadata.CREATOR), (ImageHints) zzef.zza(parcel, ImageHints.CREATOR));
                    parcel2.writeNoException();
                    zzef.zzb(parcel2, zza);
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    WebImage onPickImage(MediaMetadata mediaMetadata, int i) throws RemoteException;

    WebImage zza(MediaMetadata mediaMetadata, ImageHints imageHints) throws RemoteException;

    IObjectWrapper zznT() throws RemoteException;

    int zznm() throws RemoteException;
}
