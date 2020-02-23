package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class fo extends zza {
    public static final Parcelable.Creator<fo> CREATOR = new fp();

    public final void writeToParcel(Parcel parcel, int i) {
        zzd.zzI(parcel, zzd.zze(parcel));
    }
}
