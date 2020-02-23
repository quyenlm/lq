package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;
import java.util.List;

public final class zzk implements Parcelable.Creator<PolygonOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<LatLng> arrayList = null;
        ArrayList arrayList2 = new ArrayList();
        float f = 0.0f;
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i3 = 0;
        ArrayList<PatternItem> arrayList3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 3:
                    zzb.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 5:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 6:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 7:
                    f2 = zzb.zzl(parcel, readInt);
                    break;
                case 8:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 10:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 11:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    arrayList3 = zzb.zzc(parcel, readInt, PatternItem.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PolygonOptions(arrayList, arrayList2, f, i, i2, f2, z, z2, z3, i3, arrayList3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PolygonOptions[i];
    }
}
