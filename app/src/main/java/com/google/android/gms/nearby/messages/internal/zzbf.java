package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbf implements Parcelable.Creator<zzbe> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ClientAppContext clientAppContext = null;
        boolean z = false;
        String str = null;
        String str2 = null;
        int i = 0;
        PendingIntent pendingIntent = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    iBinder2 = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 4:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    clientAppContext = (ClientAppContext) zzb.zza(parcel, readInt, ClientAppContext.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbe(i2, iBinder2, iBinder, pendingIntent, i, str2, str, z, clientAppContext);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbe[i];
    }
}
