package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

public class PlacePhotoMetadataResult extends zza implements Result {
    public static final Parcelable.Creator<PlacePhotoMetadataResult> CREATOR = new zzj();
    private final Status mStatus;
    private DataHolder zzbjF;
    private final PlacePhotoMetadataBuffer zzbjG;

    public PlacePhotoMetadataResult(Status status, DataHolder dataHolder) {
        this.mStatus = status;
        this.zzbjF = dataHolder;
        if (dataHolder == null) {
            this.zzbjG = null;
        } else {
            this.zzbjG = new PlacePhotoMetadataBuffer(this.zzbjF);
        }
    }

    public PlacePhotoMetadataBuffer getPhotoMetadata() {
        return this.zzbjG;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzbjF, i, false);
        zzd.zzI(parcel, zze);
    }
}
