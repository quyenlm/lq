package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class zzz extends zza {
    public static final Parcelable.Creator<zzz> CREATOR = new zzaa();

    public final void writeToParcel(Parcel parcel, int i) {
        zzd.zzI(parcel, zzd.zze(parcel));
    }

    public final <F> F zza(zzj<F> zzj) {
        return zzj.zztu();
    }
}
