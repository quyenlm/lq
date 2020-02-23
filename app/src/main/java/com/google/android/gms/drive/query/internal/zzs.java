package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public final class zzs implements Parcelable.Creator<zzr> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<FilterHolder> arrayList = null;
        zzx zzx = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zzx = (zzx) zzb.zza(parcel, readInt, zzx.CREATOR);
                    break;
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, FilterHolder.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzr(zzx, (List<FilterHolder>) arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzr[i];
    }
}
