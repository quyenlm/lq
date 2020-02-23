package com.google.android.gms.gcm;

import android.os.Parcel;
import android.os.Parcelable;

final class zzf implements Parcelable.Creator<OneoffTask> {
    zzf() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new OneoffTask(parcel, (zzf) null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new OneoffTask[i];
    }
}
