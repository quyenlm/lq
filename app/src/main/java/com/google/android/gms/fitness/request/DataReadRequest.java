package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Device;
import com.google.android.gms.internal.zzbvv;
import com.google.android.gms.internal.zzbvw;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataReadRequest extends zza {
    public static final Parcelable.Creator<DataReadRequest> CREATOR = new zzm();
    public static final int NO_LIMIT = 0;
    private final List<DataType> zzaTn;
    private final long zzaTo;
    private final int zzaTr;
    private final int zzaWA;
    private final boolean zzaWB;
    private final boolean zzaWC;
    private final zzbvv zzaWD;
    private final List<Device> zzaWE;
    private final List<Integer> zzaWF;
    private final List<DataSource> zzaWr;
    private final List<DataType> zzaWw;
    private final List<DataSource> zzaWx;
    private final long zzaWy;
    private final DataSource zzaWz;
    private final long zzagZ;
    private final int zzaku;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<DataType> zzaTn = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaTo;
        /* access modifiers changed from: private */
        public int zzaTr = 0;
        /* access modifiers changed from: private */
        public int zzaWA = 0;
        private boolean zzaWB = false;
        /* access modifiers changed from: private */
        public boolean zzaWC = false;
        /* access modifiers changed from: private */
        public final List<Device> zzaWE = new ArrayList();
        /* access modifiers changed from: private */
        public final List<Integer> zzaWF = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataSource> zzaWr = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataType> zzaWw = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataSource> zzaWx = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaWy = 0;
        /* access modifiers changed from: private */
        public DataSource zzaWz;
        /* access modifiers changed from: private */
        public long zzagZ;

        public Builder addFilteredDataQualityStandard(int i) {
            zzbo.zzb(this.zzaWE.isEmpty(), (Object) "Cannot add data quality standard filter when filtering by device.");
            this.zzaWF.add(Integer.valueOf(i));
            return this;
        }

        public Builder aggregate(DataSource dataSource, DataType dataType) {
            zzbo.zzb(dataSource, (Object) "Attempting to add a null data source");
            zzbo.zza(!this.zzaWr.contains(dataSource), (Object) "Cannot add the same data source for aggregated and detailed");
            DataType dataType2 = dataSource.getDataType();
            List<DataType> aggregatesForInput = DataType.getAggregatesForInput(dataType2);
            zzbo.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType2);
            zzbo.zzb(aggregatesForInput.contains(dataType), "Invalid output aggregate data type specified: %s -> %s", dataType2, dataType);
            if (!this.zzaWx.contains(dataSource)) {
                this.zzaWx.add(dataSource);
            }
            return this;
        }

        public Builder aggregate(DataType dataType, DataType dataType2) {
            zzbo.zzb(dataType, (Object) "Attempting to use a null data type");
            zzbo.zza(!this.zzaTn.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            List<DataType> aggregatesForInput = DataType.getAggregatesForInput(dataType);
            zzbo.zzb(!aggregatesForInput.isEmpty(), "Unsupported input data type specified for aggregation: %s", dataType);
            zzbo.zzb(aggregatesForInput.contains(dataType2), "Invalid output aggregate data type specified: %s -> %s", dataType, dataType2);
            if (!this.zzaWw.contains(dataType)) {
                this.zzaWw.add(dataType);
            }
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaTr = 4;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivitySegment(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzbo.zzb(dataSource != null, (Object) "Invalid activity data source specified");
            zzbo.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzaWz = dataSource;
            this.zzaTr = 4;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaTr = 3;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByActivityType(int i, TimeUnit timeUnit, DataSource dataSource) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            zzbo.zzb(dataSource != null, (Object) "Invalid activity data source specified");
            zzbo.zzb(dataSource.getDataType().equals(DataType.TYPE_ACTIVITY_SEGMENT), "Invalid activity data source specified: %s", dataSource);
            this.zzaWz = dataSource;
            this.zzaTr = 3;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketBySession(int i, TimeUnit timeUnit) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaTr = 2;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public Builder bucketByTime(int i, TimeUnit timeUnit) {
            zzbo.zzb(this.zzaTr == 0, "Bucketing strategy already set to %s", Integer.valueOf(this.zzaTr));
            zzbo.zzb(i > 0, "Must specify a valid minimum duration for an activity segment: %d", Integer.valueOf(i));
            this.zzaTr = 1;
            this.zzaWy = timeUnit.toMillis((long) i);
            return this;
        }

        public DataReadRequest build() {
            boolean z = true;
            zzbo.zza(!this.zzaWr.isEmpty() || !this.zzaTn.isEmpty() || !this.zzaWx.isEmpty() || !this.zzaWw.isEmpty(), (Object) "Must add at least one data source (aggregated or detailed)");
            zzbo.zza(this.zzagZ > 0, "Invalid start time: %s", Long.valueOf(this.zzagZ));
            zzbo.zza(this.zzaTo > 0 && this.zzaTo > this.zzagZ, "Invalid end time: %s", Long.valueOf(this.zzaTo));
            boolean z2 = this.zzaWx.isEmpty() && this.zzaWw.isEmpty();
            if ((!z2 || this.zzaTr != 0) && (z2 || this.zzaTr == 0)) {
                z = false;
            }
            zzbo.zza(z, (Object) "Must specify a valid bucketing strategy while requesting aggregation");
            return new DataReadRequest(this);
        }

        public Builder enableServerQueries() {
            this.zzaWC = true;
            return this;
        }

        public Builder read(DataSource dataSource) {
            zzbo.zzb(dataSource, (Object) "Attempting to add a null data source");
            zzbo.zzb(!this.zzaWx.contains(dataSource), (Object) "Cannot add the same data source as aggregated and detailed");
            if (!this.zzaWr.contains(dataSource)) {
                this.zzaWr.add(dataSource);
            }
            return this;
        }

        public Builder read(DataType dataType) {
            zzbo.zzb(dataType, (Object) "Attempting to use a null data type");
            zzbo.zza(!this.zzaWw.contains(dataType), (Object) "Cannot add the same data type as aggregated and detailed");
            if (!this.zzaTn.contains(dataType)) {
                this.zzaTn.add(dataType);
            }
            return this;
        }

        public Builder setLimit(int i) {
            zzbo.zzb(i > 0, "Invalid limit %d is specified", Integer.valueOf(i));
            this.zzaWA = i;
            return this;
        }

        public Builder setTimeRange(long j, long j2, TimeUnit timeUnit) {
            this.zzagZ = timeUnit.toMillis(j);
            this.zzaTo = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataReadRequest(int i, List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i2, long j3, DataSource dataSource, int i3, boolean z, boolean z2, IBinder iBinder, List<Device> list5, List<Integer> list6) {
        this.zzaku = i;
        this.zzaTn = list;
        this.zzaWr = list2;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.zzaWw = list3;
        this.zzaWx = list4;
        this.zzaTr = i2;
        this.zzaWy = j3;
        this.zzaWz = dataSource;
        this.zzaWA = i3;
        this.zzaWB = z;
        this.zzaWC = z2;
        this.zzaWD = iBinder == null ? null : zzbvw.zzP(iBinder);
        this.zzaWE = list5 == null ? Collections.emptyList() : list5;
        this.zzaWF = list6 == null ? Collections.emptyList() : list6;
    }

    private DataReadRequest(Builder builder) {
        this(builder.zzaTn, builder.zzaWr, builder.zzagZ, builder.zzaTo, builder.zzaWw, builder.zzaWx, builder.zzaTr, builder.zzaWy, builder.zzaWz, builder.zzaWA, false, builder.zzaWC, (zzbvv) null, builder.zzaWE, builder.zzaWF);
    }

    public DataReadRequest(DataReadRequest dataReadRequest, zzbvv zzbvv) {
        this(dataReadRequest.zzaTn, dataReadRequest.zzaWr, dataReadRequest.zzagZ, dataReadRequest.zzaTo, dataReadRequest.zzaWw, dataReadRequest.zzaWx, dataReadRequest.zzaTr, dataReadRequest.zzaWy, dataReadRequest.zzaWz, dataReadRequest.zzaWA, dataReadRequest.zzaWB, dataReadRequest.zzaWC, zzbvv, dataReadRequest.zzaWE, dataReadRequest.zzaWF);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    private DataReadRequest(List<DataType> list, List<DataSource> list2, long j, long j2, List<DataType> list3, List<DataSource> list4, int i, long j3, DataSource dataSource, int i2, boolean z, boolean z2, zzbvv zzbvv, List<Device> list5, List<Integer> list6) {
        this(6, list, list2, j, j2, list3, list4, i, j3, dataSource, i2, z, z2, zzbvv == null ? null : zzbvv.asBinder(), list5, list6);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataReadRequest)) {
                return false;
            }
            DataReadRequest dataReadRequest = (DataReadRequest) obj;
            if (!(this.zzaTn.equals(dataReadRequest.zzaTn) && this.zzaWr.equals(dataReadRequest.zzaWr) && this.zzagZ == dataReadRequest.zzagZ && this.zzaTo == dataReadRequest.zzaTo && this.zzaTr == dataReadRequest.zzaTr && this.zzaWx.equals(dataReadRequest.zzaWx) && this.zzaWw.equals(dataReadRequest.zzaWw) && zzbe.equal(this.zzaWz, dataReadRequest.zzaWz) && this.zzaWy == dataReadRequest.zzaWy && this.zzaWC == dataReadRequest.zzaWC && this.zzaWA == dataReadRequest.zzaWA && this.zzaWB == dataReadRequest.zzaWB && zzbe.equal(this.zzaWD, dataReadRequest.zzaWD) && zzbe.equal(this.zzaWE, dataReadRequest.zzaWE) && zzbe.equal(this.zzaWF, dataReadRequest.zzaWF))) {
                return false;
            }
        }
        return true;
    }

    public DataSource getActivityDataSource() {
        return this.zzaWz;
    }

    public List<DataSource> getAggregatedDataSources() {
        return this.zzaWx;
    }

    public List<DataType> getAggregatedDataTypes() {
        return this.zzaWw;
    }

    public long getBucketDuration(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaWy, TimeUnit.MILLISECONDS);
    }

    public int getBucketType() {
        return this.zzaTr;
    }

    public List<DataSource> getDataSources() {
        return this.zzaWr;
    }

    public List<DataType> getDataTypes() {
        return this.zzaTn;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaTo, TimeUnit.MILLISECONDS);
    }

    public List<Integer> getFilteredDataQualityStandards() {
        return this.zzaWF;
    }

    public int getLimit() {
        return this.zzaWA;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzagZ, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzaTr), Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo)});
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DataReadRequest{");
        if (!this.zzaTn.isEmpty()) {
            for (DataType zztO : this.zzaTn) {
                sb.append(zztO.zztO()).append(" ");
            }
        }
        if (!this.zzaWr.isEmpty()) {
            for (DataSource debugString : this.zzaWr) {
                sb.append(debugString.toDebugString()).append(" ");
            }
        }
        if (this.zzaTr != 0) {
            sb.append("bucket by ").append(Bucket.zzaS(this.zzaTr));
            if (this.zzaWy > 0) {
                sb.append(" >").append(this.zzaWy).append("ms");
            }
            sb.append(": ");
        }
        if (!this.zzaWw.isEmpty()) {
            for (DataType zztO2 : this.zzaWw) {
                sb.append(zztO2.zztO()).append(" ");
            }
        }
        if (!this.zzaWx.isEmpty()) {
            for (DataSource debugString2 : this.zzaWx) {
                sb.append(debugString2.toDebugString()).append(" ");
            }
        }
        sb.append(String.format("(%tF %tT - %tF %tT)", new Object[]{Long.valueOf(this.zzagZ), Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo), Long.valueOf(this.zzaTo)}));
        if (this.zzaWz != null) {
            sb.append("activities: ").append(this.zzaWz.toDebugString());
        }
        if (!this.zzaWF.isEmpty()) {
            sb.append("quality: ");
            for (Integer intValue : this.zzaWF) {
                sb.append(DataSource.zzaV(intValue.intValue())).append(" ");
            }
        }
        if (this.zzaWC) {
            sb.append(" +server");
        }
        sb.append("}");
        return sb.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getDataTypes(), false);
        zzd.zzc(parcel, 2, getDataSources(), false);
        zzd.zza(parcel, 3, this.zzagZ);
        zzd.zza(parcel, 4, this.zzaTo);
        zzd.zzc(parcel, 5, getAggregatedDataTypes(), false);
        zzd.zzc(parcel, 6, getAggregatedDataSources(), false);
        zzd.zzc(parcel, 7, getBucketType());
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zza(parcel, 8, this.zzaWy);
        zzd.zza(parcel, 9, (Parcelable) getActivityDataSource(), i, false);
        zzd.zzc(parcel, 10, getLimit());
        zzd.zza(parcel, 12, this.zzaWB);
        zzd.zza(parcel, 13, this.zzaWC);
        zzd.zza(parcel, 14, this.zzaWD == null ? null : this.zzaWD.asBinder(), false);
        zzd.zzc(parcel, 16, this.zzaWE, false);
        zzd.zza(parcel, 17, getFilteredDataQualityStandards(), false);
        zzd.zzI(parcel, zze);
    }
}
