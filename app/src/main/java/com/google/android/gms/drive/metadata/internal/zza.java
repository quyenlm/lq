package com.google.android.gms.drive.metadata.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zza implements Parcelable.Creator<AppVisibleCustomProperties> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzc> arrayList = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, zzc.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new AppVisibleCustomProperties(arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new AppVisibleCustomProperties[i];
    }
}
