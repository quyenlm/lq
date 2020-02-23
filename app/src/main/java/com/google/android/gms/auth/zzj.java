package com.google.android.gms.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzj implements Parcelable.Creator<TokenData> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<String> arrayList = null;
        boolean z = false;
        boolean z2 = false;
        Long l = null;
        String str = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    l = zzb.zzj(parcel, readInt);
                    break;
                case 4:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 6:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new TokenData(i, str, l, z2, z, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new TokenData[i];
    }
}
