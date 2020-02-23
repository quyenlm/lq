package com.google.android.gms.games.multiplayer.realtime;

import android.os.Parcel;
import android.os.Parcelable;

final class zza implements Parcelable.Creator<RealTimeMessage> {
    zza() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new RealTimeMessage(parcel, (zza) null);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new RealTimeMessage[i];
    }
}
