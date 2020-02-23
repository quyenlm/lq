package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;

public final class zzo implements Parcelable.Creator<PhoneAuthCredential> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        boolean z = false;
        String str = null;
        String str2 = null;
        String str3 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str3 = zzb.zzq(parcel, readInt);
                    break;
                case 2:
                    str2 = zzb.zzq(parcel, readInt);
                    break;
                case 3:
                    z = zzb.zzc(parcel, readInt);
                    break;
                case 4:
                    str = zzb.zzq(parcel, readInt);
                    break;
                default:
                    zzb.zzb(parcel, readInt);
                    break;
            }
        }
        zzb.zzF(parcel, zzd);
        return new PhoneAuthCredential(str3, str2, z, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PhoneAuthCredential[i];
    }
}
