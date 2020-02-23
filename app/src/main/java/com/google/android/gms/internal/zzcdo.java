package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public final class zzcdo implements Parcelable.Creator<zzcdn> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        List<zzcbz> list = zzcdn.zzbiU;
        String str = null;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        String str2 = null;
        LocationRequest locationRequest = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    locationRequest = (LocationRequest) zzb.zza(parcel, readInt, LocationRequest.CREATOR);
                    break;
                case 5:
                    list = zzb.zzc(parcel, readInt, zzcbz.CREATOR);
                    break;
                case 6:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    z3 = zzb.zzc(parcel, readInt);
                    break;
                case 8:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 9:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 10:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzcdn(locationRequest, list, str2, z3, z2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcdn[i];
    }
}
