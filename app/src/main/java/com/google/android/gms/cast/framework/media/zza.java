package com.google.android.gms.cast.framework.media;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zza implements Parcelable.Creator<CastMediaOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        NotificationOptions notificationOptions = null;
        IBinder iBinder = null;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 5:
                    notificationOptions = (NotificationOptions) zzb.zza(parcel, readInt, NotificationOptions.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new CastMediaOptions(str2, str, iBinder, notificationOptions);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CastMediaOptions[i];
    }
}
