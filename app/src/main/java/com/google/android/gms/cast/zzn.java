package com.google.android.gms.cast;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzn implements Parcelable.Creator<CastDevice> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        int i = 0;
        ArrayList<WebImage> arrayList = null;
        int i2 = 0;
        int i3 = -1;
        String str6 = null;
        String str7 = null;
        int i4 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 5:
                    str4 = zzb.zzq(parcel, readInt);
                    break;
                case 6:
                    str5 = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 8:
                    arrayList = zzb.zzc(parcel, readInt, WebImage.CREATOR);
                    break;
                case 9:
                    i2 = zzb.zzg(parcel, readInt);
                    break;
                case 10:
                    i3 = zzb.zzg(parcel, readInt);
                    break;
                case 11:
                    str6 = zzb.zzq(parcel, readInt);
                    break;
                case 12:
                    str7 = zzb.zzq(parcel, readInt);
                    break;
                case 13:
                    i4 = zzb.zzg(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new CastDevice(str, str2, str3, str4, str5, i, arrayList, i2, i3, str6, str7, i4);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new CastDevice[i];
    }
}
