package com.google.firebase.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.firebase.auth.PhoneAuthProvider;

public final class zza implements Parcelable.Creator<PhoneAuthProvider.ForceResendingToken> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzb.zzd(parcel);
        while (parcel.dataPosition() < zzd) {
            zzb.zzb(parcel, parcel.readInt());
        }
        zzb.zzF(parcel, zzd);
        return new PhoneAuthProvider.ForceResendingToken();
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PhoneAuthProvider.ForceResendingToken[i];
    }
}
