package com.google.android.gms.cast;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.images.WebImage;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zzd implements Parcelable.Creator<ApplicationMetadata> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        Uri uri = null;
        String str = null;
        ArrayList<String> arrayList = null;
        ArrayList<WebImage> arrayList2 = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    arrayList2 = zzb.zzc(parcel, readInt, WebImage.CREATOR);
                    break;
                case 5:
                    arrayList = zzb.zzC(parcel, readInt);
                    break;
                case 6:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 7:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new ApplicationMetadata(str3, str2, arrayList2, arrayList, str, uri);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new ApplicationMetadata[i];
    }
}
