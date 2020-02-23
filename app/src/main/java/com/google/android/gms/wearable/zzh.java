package com.google.android.gms.wearable;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzh implements Parcelable.Creator<PutDataRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        long j = 0;
        byte[] bArr = null;
        Bundle bundle = null;
        Uri uri = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    uri = (Uri) zzb.zza(parcel, readInt, Uri.CREATOR);
                    break;
                case 4:
                    bundle = zzb.zzs(parcel, readInt);
                    break;
                case 5:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                case 6:
                    j = zzb.zzi(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PutDataRequest(uri, bundle, bArr, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PutDataRequest[i];
    }
}
