package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.zzs;
import java.util.ArrayList;

public final class zzbol implements Parcelable.Creator<zzbok> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zzs> arrayList = null;
        int i = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, zzs.CREATOR);
                    break;
                case 3:
                    i = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbok(arrayList, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbok[i];
    }
}
