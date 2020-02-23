package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzapc implements Parcelable.Creator<zzapb> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        int i = -1;
        byte[] bArr = null;
        zzapi zzapi = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    zzapi = (zzapi) zzb.zza(parcel, readInt, zzapi.CREATOR);
                    break;
                case 4:
                    i = zzb.zzg(parcel, readInt);
                    break;
                case 5:
                    bArr = zzb.zzt(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new zzapb(str, zzapi, i, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzapb[i];
    }
}
