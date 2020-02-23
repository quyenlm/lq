package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzj implements Parcelable.Creator<zzg> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        zzm zzm = null;
        String str = null;
        zzh zzh = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    zzh = (zzh) zzb.zza(parcel, readInt, zzh.CREATOR);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    zzm = (zzm) zzb.zza(parcel, readInt, zzm.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzg(str2, zzh, str, zzm);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzg[i];
    }
}
