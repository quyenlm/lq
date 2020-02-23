package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzao implements Parcelable.Creator<zzal> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzan> arrayList = null;
        ArrayList<zzam> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList2 = zzb.zzc(parcel, readInt, zzam.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, zzan.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzal(arrayList2, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzal[i];
    }
}
