package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;

public final class zzad implements Parcelable.Creator<PlaceEntity> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        ArrayList<Integer> arrayList = null;
        ArrayList<Integer> arrayList2 = null;
        Bundle bundle = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        ArrayList<String> arrayList3 = null;
        LatLng latLng = null;
        float f = 0.0f;
        LatLngBounds latLngBounds = null;
        String str6 = null;
        Uri uri = null;
        boolean z = false;
        float f2 = 0.0f;
        int i = 0;
        zzaj zzaj = null;
        zzal zzal = null;
        zzae zzae = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 3:
                    zzaj = (zzaj) zzb.zza(parcel, readInt, zzaj.CREATOR);
                    break;
                case 4:
                    latLng = (LatLng) zzb.zza(parcel, readInt, LatLng.CREATOR);
                    break;
                case 5:
                    f = zzb.zzl(parcel, readInt);
                    break;
                case 6:
                    latLngBounds = (LatLngBounds) zzb.zza(parcel, readInt, LatLngBounds.CREATOR);
                    break;
                case 7:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 8:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 9:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 10:
                    f2 = zzb.zzl(parcel, readInt);
                    break;
                case 11:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 13:
                    arrayList2 = zzb.zzB(parcel, readInt);
                    break;
                case 14:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 15:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 16:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 17:
                    arrayList3 = zzb.zzC(parcel, readInt);
                    break;
                case 19:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 20:
                    arrayList = zzb.zzB(parcel, readInt);
                    break;
                case 21:
                    zzal = (zzal) zzb.zza(parcel, readInt, zzal.CREATOR);
                    break;
                case 22:
                    zzae = (zzae) zzb.zza(parcel, readInt, zzae.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PlaceEntity(str, arrayList, arrayList2, bundle, str2, str3, str4, str5, arrayList3, latLng, f, latLngBounds, str6, uri, z, f2, i, zzaj, zzal, zzae);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PlaceEntity[i];
    }
}
