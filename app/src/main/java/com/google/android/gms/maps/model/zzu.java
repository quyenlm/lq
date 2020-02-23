package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzu implements Parcelable.Creator<VisibleRegion> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        LatLngBounds latLngBounds = null;
        LatLng latLng = null;
        LatLng latLng2 = null;
        LatLng latLng3 = null;
        LatLng latLng4 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    latLng4 = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 3:
                    latLng3 = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 4:
                    latLng2 = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 5:
                    latLng = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) zzb.zza(parcel, readInt, LatLngBounds.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new VisibleRegion(latLng4, latLng3, latLng2, latLng, latLngBounds);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new VisibleRegion[i];
    }
}
