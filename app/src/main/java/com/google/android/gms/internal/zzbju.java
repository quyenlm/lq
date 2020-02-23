package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbju implements Parcelable.Creator<zzbjt> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = 0;
        long j = 0;
        long j2 = 0;
        String str = null;
        PendingIntent pendingIntent = null;
        IBinder iBinder = null;
        zzbiw zzbiw = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 3:
                    zzbiw = (zzbiw) zzb.zza(parcel, readInt, zzbiw.CREATOR);
                    break;
                case 4:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 5:
                    pendingIntent = (PendingIntent) zzb.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    j2 = zzb.zzi(parcel, readInt);
                    break;
                case 8:
                    j = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbjt(i, zzbiw, iBinder, pendingIntent, str, j2, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbjt[i];
    }
}
