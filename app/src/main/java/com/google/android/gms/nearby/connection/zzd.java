package com.google.android.gms.nearby.connection;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzd implements Parcelable.Creator<DiscoveryOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Strategy strategy = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    strategy = (Strategy) zzb.zza(parcel, readInt, Strategy.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new DiscoveryOptions(strategy);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new DiscoveryOptions[i];
    }
}
