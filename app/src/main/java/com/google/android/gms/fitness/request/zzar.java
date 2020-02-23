package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzar implements Parcelable.Creator<zzaq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        IBinder iBinder = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 2:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 1000:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzaq(i, iBinder2, pendingIntent, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzaq[i];
    }
}
