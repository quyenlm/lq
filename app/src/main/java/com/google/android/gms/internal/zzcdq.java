package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzcdq implements Parcelable.Creator<zzcdp> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 1;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder3 = null;
        zzcdn zzcdn = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    zzcdn = (zzcdn) zzb.zza(parcel, readInt, zzcdn.CREATOR);
                    break;
                case 3:
                    iBinder3 = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 5:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 6:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcdp(i, zzcdn, iBinder3, pendingIntent, iBinder2, iBinder);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcdp[i];
    }
}
