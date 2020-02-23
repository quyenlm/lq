package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzr implements Parcelable.Creator<zzq> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzo zzo = null;
        zzo zzo2 = null;
        zzm zzm = null;
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
                    zzm = (zzm) zzb.zza(parcel, readInt, zzm.CREATOR);
                    break;
                case 5:
                    zzo2 = (zzo) zzb.zza(parcel, readInt, zzo.CREATOR);
                    break;
                case 6:
                    zzo = (zzo) zzb.zza(parcel, readInt, zzo.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzq(str2, str, zzm, zzo2, zzo);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzq[i];
    }
}
