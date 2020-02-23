package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzl implements Parcelable.Creator<PolylineOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<LatLng> arrayList = null;
        float f = 0.0f;
        int i = 0;
        float f2 = 0.0f;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        Cap cap = null;
        Cap cap2 = null;
        int i2 = 0;
        ArrayList<PatternItem> arrayList2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    arrayList = zzb.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 3:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    f2 = zzb.zzl(parcel, readInt);
                    break;
                case 6:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 7:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    cap = (Cap) zzb.zza(parcel, readInt, Cap.CREATOR);
                    break;
                case 10:
                    cap2 = (Cap) zzb.zza(parcel, readInt, Cap.CREATOR);
                    break;
                case 11:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 12:
                    arrayList2 = zzb.zzc(parcel, readInt, PatternItem.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PolylineOptions(arrayList, f, i, f2, z, z2, z3, cap, cap2, i2, arrayList2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PolylineOptions[i];
    }
}
