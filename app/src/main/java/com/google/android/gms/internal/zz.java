package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public final class zz implements Parcelable.Creator<zx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        ArrayList<zy> arrayList = null;
        Uri uri = null;
        Uri uri2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    uri2 = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 2:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 3:
                    arrayList = zzb.zzc(parcel, readInt, zy.CREATOR);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zx(uri2, uri, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zx[i];
    }
}
