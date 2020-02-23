package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.fitness.data.DataType;
import java.util.Arrays;

public class DataTypeResult extends zza implements Result {
    public static final Parcelable.Creator<DataTypeResult> CREATOR = new zze();
    private final Status mStatus;
    private final DataType zzaUe;
    private final int zzaku;

    DataTypeResult(int i, Status status, DataType dataType) {
        this.zzaku = i;
        this.mStatus = status;
        this.zzaUe = dataType;
    }

    private DataTypeResult(Status status, DataType dataType) {
        this.zzaku = 2;
        this.mStatus = status;
        this.zzaUe = null;
    }

    public static DataTypeResult zzC(Status status) {
        return new DataTypeResult(status, (DataType) null);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataTypeResult)) {
                return false;
            }
            DataTypeResult dataTypeResult = (DataTypeResult) obj;
            if (!(this.mStatus.equals(dataTypeResult.mStatus) && zzbe.equal(this.zzaUe, dataTypeResult.zzaUe))) {
                return false;
            }
        }
        return true;
    }

    public DataType getDataType() {
        return this.zzaUe;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzaUe});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("status", this.mStatus).zzg("dataType", this.zzaUe).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getStatus(), i, false);
        zzd.zza(parcel, 3, (Parcelable) getDataType(), i, false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
