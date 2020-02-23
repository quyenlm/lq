package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzbto implements Parcelable.Creator<zzbtn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        zzbtd zzbtd = null;
        ArrayList<String> arrayList = null;
        DataHolder dataHolder = null;
        ArrayList<zzbtl> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList2 = zzb.zzc(parcel, readInt, zzbtl.CREATOR);
                    break;
                case 3:
                    dataHolder = (DataHolder) zzb.zza(parcel, readInt, DataHolder.CREATOR);
                    break;
                case 4:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 6:
                    zzbtd = (zzbtd) zzb.zza(parcel, readInt, zzbtd.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzbtn(arrayList2, dataHolder, z, arrayList, zzbtd);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzbtn[i];
    }
}
