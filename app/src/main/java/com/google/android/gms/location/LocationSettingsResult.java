package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public final class LocationSettingsResult extends zza implements Result {
    public static final Parcelable.Creator<LocationSettingsResult> CREATOR = new zzw();
    private final Status mStatus;
    private final LocationSettingsStates zzbim;

    public LocationSettingsResult(Status status) {
        this(status, (LocationSettingsStates) null);
    }

    public LocationSettingsResult(Status status, LocationSettingsStates locationSettingsStates) {
        this.mStatus = status;
        this.zzbim = locationSettingsStates;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzbim;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzd.zza(parcel, 2, (Parcelable) getLocationSettingsStates(), i, false);
        zzd.zzI(parcel, zze);
    }
}
