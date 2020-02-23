package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public final class zzah implements Parcelable.Creator<StreetViewPanoramaOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        byte b = 0;
        byte b2 = 0;
        byte b3 = 0;
        byte b4 = 0;
        byte b5 = 0;
        Integer num = null;
        LatLng latLng = null;
        String str = null;
        StreetViewPanoramaCamera streetViewPanoramaCamera = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    streetViewPanoramaCamera = (StreetViewPanoramaCamera) zzb.zza(parcel, readInt, StreetViewPanoramaCamera.CREATOR);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    latLng = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 5:
                    num = zzb.zzh(parcel, readInt);
                    break;
                case 6:
                    b5 = zzb.zze(parcel, readInt);
                    break;
                case 7:
                    b4 = zzb.zze(parcel, readInt);
                    break;
                case 8:
                    b3 = zzb.zze(parcel, readInt);
                    break;
                case 9:
                    b2 = zzb.zze(parcel, readInt);
                    break;
                case 10:
                    b = zzb.zze(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new StreetViewPanoramaOptions(streetViewPanoramaCamera, str, latLng, num, b5, b4, b3, b2, b);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new StreetViewPanoramaOptions[i];
    }
}
