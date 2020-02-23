package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzbs implements Parcelable.Creator<zzbr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        boolean z2 = false;
        ConnectionResult connectionResult = null;
        IBinder iBinder = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    iBinder = zzb.zzr(parcel, readInt);
                    break;
                case 3:
                    connectionResult = (ConnectionResult) zzb.zza(parcel, readInt, ConnectionResult.CREATOR);
                    break;
                case 4:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbr(i, iBinder, connectionResult, z2, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbr[i];
    }
}
