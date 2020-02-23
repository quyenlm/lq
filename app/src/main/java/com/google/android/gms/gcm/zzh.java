package com.google.android.gms.gcm;

import android.os.Parcel;
import android.os.Parcelable;

final class zzh implements Parcelable.Creator<PeriodicTask> {
    zzh() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new PeriodicTask(parcel, (zzh) null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PeriodicTask[i];
    }
}
