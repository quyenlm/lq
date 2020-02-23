package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DataUpdateRequest extends zza {
    public static final Parcelable.Creator<DataUpdateRequest> CREATOR = new zzy();
    private final long zzaTo;
    private final DataSet zzaVi;
    private final zzbxg zzaWo;
    private final long zzagZ;
    private final int zzaku;

    public static class Builder {
        /* access modifiers changed from: private */
        public long zzaTo;
        /* access modifiers changed from: private */
        public DataSet zzaVi;
        /* access modifiers changed from: private */
        public long zzagZ;

        public DataUpdateRequest build() {
            zzbo.zza(this.zzagZ, (Object) "Must set a non-zero value for startTimeMillis/startTime");
            zzbo.zza(this.zzaTo, (Object) "Must set a non-zero value for endTimeMillis/endTime");
            zzbo.zzb(this.zzaVi, (Object) "Must set the data set");
            for (DataPoint next : this.zzaVi.getDataPoints()) {
                long startTime = next.getStartTime(TimeUnit.MILLISECONDS);
                long endTime = next.getEndTime(TimeUnit.MILLISECONDS);
                zzbo.zza(!((startTime > endTime ? 1 : (startTime == endTime ? 0 : -1)) > 0 || (((startTime > 0 ? 1 : (startTime == 0 ? 0 : -1)) != 0 && (startTime > this.zzagZ ? 1 : (startTime == this.zzagZ ? 0 : -1)) < 0) || (((startTime > 0 ? 1 : (startTime == 0 ? 0 : -1)) != 0 && (startTime > this.zzaTo ? 1 : (startTime == this.zzaTo ? 0 : -1)) > 0) || (endTime > this.zzaTo ? 1 : (endTime == this.zzaTo ? 0 : -1)) > 0 || (endTime > this.zzagZ ? 1 : (endTime == this.zzagZ ? 0 : -1)) < 0))), "Data Point's startTimeMillis %d, endTimeMillis %d should lie between timeRange provided in the request. StartTimeMillis %d, EndTimeMillis: %d", Long.valueOf(startTime), Long.valueOf(endTime), Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo));
            }
            return new DataUpdateRequest(this);
        }

        public Builder setDataSet(DataSet dataSet) {
            zzbo.zzb(dataSet, (Object) "Must set the data set");
            this.zzaVi = dataSet;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            zzbo.zzb(j > 0, "Invalid start time :%d", Long.valueOf(j));
            zzbo.zzb(j2 >= j, "Invalid end time :%d", Long.valueOf(j2));
            this.zzagZ = timeUnit.toMillis(j);
            this.zzaTo = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataUpdateRequest(int i, long j, long j2, DataSet dataSet, IBinder iBinder) {
        this.zzaku = i;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.zzaVi = dataSet;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    private DataUpdateRequest(long j, long j2, DataSet dataSet, IBinder iBinder) {
        this.zzaku = 1;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.zzaVi = dataSet;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    private DataUpdateRequest(Builder builder) {
        this(builder.zzagZ, builder.zzaTo, builder.zzaVi, (IBinder) null);
    }

    public DataUpdateRequest(DataUpdateRequest dataUpdateRequest, IBinder iBinder) {
        this(dataUpdateRequest.zzagZ, dataUpdateRequest.zzaTo, dataUpdateRequest.getDataSet(), iBinder);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataUpdateRequest)) {
                return false;
            }
            DataUpdateRequest dataUpdateRequest = (DataUpdateRequest) obj;
            if (!(this.zzagZ == dataUpdateRequest.zzagZ && this.zzaTo == dataUpdateRequest.zzaTo && zzbe.equal(this.zzaVi, dataUpdateRequest.zzaVi))) {
                return false;
            }
        }
        return true;
    }

    public IBinder getCallbackBinder() {
        if (this.zzaWo == null) {
            return null;
        }
        return this.zzaWo.asBinder();
    }

    public DataSet getDataSet() {
        return this.zzaVi;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaTo, TimeUnit.MILLISECONDS);
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzagZ, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo), this.zzaVi});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("startTimeMillis", Long.valueOf(this.zzagZ)).zzg("endTimeMillis", Long.valueOf(this.zzaTo)).zzg("dataSet", this.zzaVi).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzagZ);
        zzd.zza(parcel, 2, this.zzaTo);
        zzd.zza(parcel, 3, (Parcelable) getDataSet(), i, false);
        zzd.zza(parcel, 4, getCallbackBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }

    public final long zzmc() {
        return this.zzagZ;
    }

    public final long zztU() {
        return this.zzaTo;
    }
}
