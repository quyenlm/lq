package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DataDeleteRequest extends zza {
    public static final Parcelable.Creator<DataDeleteRequest> CREATOR = new zzi();
    private final List<DataType> zzaTn;
    private final long zzaTo;
    private final zzbxg zzaWo;
    private final List<DataSource> zzaWr;
    private final List<Session> zzaWs;
    private final boolean zzaWt;
    private final boolean zzaWu;
    private final long zzagZ;
    private final int zzaku;

    public static class Builder {
        /* access modifiers changed from: private */
        public List<DataType> zzaTn = new ArrayList();
        /* access modifiers changed from: private */
        public long zzaTo;
        /* access modifiers changed from: private */
        public List<DataSource> zzaWr = new ArrayList();
        /* access modifiers changed from: private */
        public List<Session> zzaWs = new ArrayList();
        /* access modifiers changed from: private */
        public boolean zzaWt = false;
        /* access modifiers changed from: private */
        public boolean zzaWu = false;
        /* access modifiers changed from: private */
        public long zzagZ;

        public Builder addDataSource(DataSource dataSource) {
            boolean z = true;
            zzbo.zzb(!this.zzaWt, (Object) "All data is already marked for deletion.  addDataSource() cannot be combined with deleteAllData()");
            if (dataSource == null) {
                z = false;
            }
            zzbo.zzb(z, (Object) "Must specify a valid data source");
            if (!this.zzaWr.contains(dataSource)) {
                this.zzaWr.add(dataSource);
            }
            return this;
        }

        public Builder addDataType(DataType dataType) {
            boolean z = true;
            zzbo.zzb(!this.zzaWt, (Object) "All data is already marked for deletion.  addDataType() cannot be combined with deleteAllData()");
            if (dataType == null) {
                z = false;
            }
            zzbo.zzb(z, (Object) "Must specify a valid data type");
            if (!this.zzaTn.contains(dataType)) {
                this.zzaTn.add(dataType);
            }
            return this;
        }

        public Builder addSession(Session session) {
            boolean z = true;
            zzbo.zzb(!this.zzaWu, (Object) "All sessions already marked for deletion.  addSession() cannot be combined with deleteAllSessions()");
            zzbo.zzb(session != null, (Object) "Must specify a valid session");
            if (session.getEndTime(TimeUnit.MILLISECONDS) <= 0) {
                z = false;
            }
            zzbo.zzb(z, (Object) "Cannot delete an ongoing session. Please stop the session prior to deleting it");
            this.zzaWs.add(session);
            return this;
        }

        public DataDeleteRequest build() {
            zzbo.zza(this.zzagZ > 0 && this.zzaTo > this.zzagZ, (Object) "Must specify a valid time interval");
            zzbo.zza((this.zzaWt || !this.zzaWr.isEmpty() || !this.zzaTn.isEmpty()) || (this.zzaWu || !this.zzaWs.isEmpty()), (Object) "No data or session marked for deletion");
            if (!this.zzaWs.isEmpty()) {
                for (Session next : this.zzaWs) {
                    zzbo.zza(next.getStartTime(TimeUnit.MILLISECONDS) >= this.zzagZ && next.getEndTime(TimeUnit.MILLISECONDS) <= this.zzaTo, "Session %s is outside the time interval [%d, %d]", next, Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo));
                }
            }
            return new DataDeleteRequest(this);
        }

        public Builder deleteAllData() {
            zzbo.zzb(this.zzaTn.isEmpty(), (Object) "Specific data type already added for deletion. deleteAllData() will delete all data types and cannot be combined with addDataType()");
            zzbo.zzb(this.zzaWr.isEmpty(), (Object) "Specific data source already added for deletion. deleteAllData() will delete all data sources and cannot be combined with addDataSource()");
            this.zzaWt = true;
            return this;
        }

        public Builder deleteAllSessions() {
            zzbo.zzb(this.zzaWs.isEmpty(), (Object) "Specific session already added for deletion. deleteAllData() will delete all sessions and cannot be combined with addSession()");
            this.zzaWu = true;
            return this;
        }

        public Builder setTimeInterval(long j, long j2, TimeUnit timeUnit) {
            zzbo.zzb(j > 0, "Invalid start time :%d", Long.valueOf(j));
            zzbo.zzb(j2 > j, "Invalid end time :%d", Long.valueOf(j2));
            this.zzagZ = timeUnit.toMillis(j);
            this.zzaTo = timeUnit.toMillis(j2);
            return this;
        }
    }

    DataDeleteRequest(int i, long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, IBinder iBinder) {
        this.zzaku = i;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.zzaWr = Collections.unmodifiableList(list);
        this.zzaTn = Collections.unmodifiableList(list2);
        this.zzaWs = list3;
        this.zzaWt = z;
        this.zzaWu = z2;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    private DataDeleteRequest(long j, long j2, List<DataSource> list, List<DataType> list2, List<Session> list3, boolean z, boolean z2, zzbxg zzbxg) {
        this.zzaku = 3;
        this.zzagZ = j;
        this.zzaTo = j2;
        this.zzaWr = Collections.unmodifiableList(list);
        this.zzaTn = Collections.unmodifiableList(list2);
        this.zzaWs = list3;
        this.zzaWt = z;
        this.zzaWu = z2;
        this.zzaWo = zzbxg;
    }

    private DataDeleteRequest(Builder builder) {
        this(builder.zzagZ, builder.zzaTo, builder.zzaWr, builder.zzaTn, builder.zzaWs, builder.zzaWt, builder.zzaWu, (zzbxg) null);
    }

    public DataDeleteRequest(DataDeleteRequest dataDeleteRequest, zzbxg zzbxg) {
        this(dataDeleteRequest.zzagZ, dataDeleteRequest.zzaTo, dataDeleteRequest.zzaWr, dataDeleteRequest.zzaTn, dataDeleteRequest.zzaWs, dataDeleteRequest.zzaWt, dataDeleteRequest.zzaWu, zzbxg);
    }

    public boolean deleteAllData() {
        return this.zzaWt;
    }

    public boolean deleteAllSessions() {
        return this.zzaWu;
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataDeleteRequest)) {
                return false;
            }
            DataDeleteRequest dataDeleteRequest = (DataDeleteRequest) obj;
            if (!(this.zzagZ == dataDeleteRequest.zzagZ && this.zzaTo == dataDeleteRequest.zzaTo && zzbe.equal(this.zzaWr, dataDeleteRequest.zzaWr) && zzbe.equal(this.zzaTn, dataDeleteRequest.zzaTn) && zzbe.equal(this.zzaWs, dataDeleteRequest.zzaWs) && this.zzaWt == dataDeleteRequest.zzaWt && this.zzaWu == dataDeleteRequest.zzaWu)) {
                return false;
            }
        }
        return true;
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

    public List<Session> getSessions() {
        return this.zzaWs;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzagZ, TimeUnit.MILLISECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzagZ), Long.valueOf(this.zzaTo)});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("startTimeMillis", Long.valueOf(this.zzagZ)).zzg("endTimeMillis", Long.valueOf(this.zzaTo)).zzg("dataSources", this.zzaWr).zzg("dateTypes", this.zzaTn).zzg("sessions", this.zzaWs).zzg("deleteAllData", Boolean.valueOf(this.zzaWt)).zzg("deleteAllSessions", Boolean.valueOf(this.zzaWu)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzagZ);
        zzd.zza(parcel, 2, this.zzaTo);
        zzd.zzc(parcel, 3, getDataSources(), false);
        zzd.zzc(parcel, 4, getDataTypes(), false);
        zzd.zzc(parcel, 5, getSessions(), false);
        zzd.zza(parcel, 6, this.zzaWt);
        zzd.zza(parcel, 7, this.zzaWu);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zza(parcel, 8, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zzI(parcel, zze);
    }
}
