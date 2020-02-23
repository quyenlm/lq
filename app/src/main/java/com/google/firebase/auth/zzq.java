package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzq implements Parcelable.Creator<UserProfileChangeRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        boolean z2 = false;
        String str = null;
        String str2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    str = zzb.zzq(parcel, readInt);
                    break;
                case 4:
                    z2 = zzb.zzc(parcel, readInt);
                    break;
                case 5:
                    z = zzb.zzc(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new UserProfileChangeRequest(str2, str, z2, z);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new UserProfileChangeRequest[i];
    }
}
