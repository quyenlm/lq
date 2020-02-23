package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbue;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SessionInsertRequest extends zza {
    public static final Parcelable.Creator<SessionInsertRequest> CREATOR = new zzat();
    private final int versionCode;
    private final Session zzaXe;
    private final List<DataSet> zzaXf;
    private final List<DataPoint> zzaXg;
    private final zzbxg zzaXh;

    public static class Builder {
        /* access modifiers changed from: private */
        public Session zzaXe;
        /* access modifiers changed from: private */
        public List<DataSet> zzaXf = new ArrayList();
        /* access modifiers changed from: private */
        public List<DataPoint> zzaXg = new ArrayList();
        private List<DataSource> zzaXi = new ArrayList();

        private final void zzd(DataPoint dataPoint) {
            long startTime = this.zzaXe.getStartTime(TimeUnit.NANOSECONDS);
            long endTime = this.zzaXe.getEndTime(TimeUnit.NANOSECONDS);
            long timestamp = dataPoint.getTimestamp(TimeUnit.NANOSECONDS);
            if (timestamp != 0) {
                TimeUnit timeUnit = TimeUnit.MILLISECONDS;
                long zza = (timestamp < startTime || timestamp > endTime) ? zzbue.zza(timestamp, TimeUnit.NANOSECONDS, timeUnit) : timestamp;
                zzbo.zza(zza >= startTime && zza <= endTime, "Data point %s has time stamp outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime), Long.valueOf(endTime));
                if (dataPoint.getTimestamp(TimeUnit.NANOSECONDS) != zza) {
                    Log.w("Fitness", String.format("Data point timestamp [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getTimestamp(TimeUnit.NANOSECONDS)), Long.valueOf(zza), timeUnit}));
                    dataPoint.setTimestamp(zza, TimeUnit.NANOSECONDS);
                }
            }
            long startTime2 = this.zzaXe.getStartTime(TimeUnit.NANOSECONDS);
            long endTime2 = this.zzaXe.getEndTime(TimeUnit.NANOSECONDS);
            long startTime3 = dataPoint.getStartTime(TimeUnit.NANOSECONDS);
            long endTime3 = dataPoint.getEndTime(TimeUnit.NANOSECONDS);
            if (startTime3 != 0 && endTime3 != 0) {
                TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
                if (endTime3 > endTime2) {
                    endTime3 = zzbue.zza(endTime3, TimeUnit.NANOSECONDS, timeUnit2);
                }
                zzbo.zza(startTime3 >= startTime2 && endTime3 <= endTime2, "Data point %s has start and end times outside session interval [%d, %d]", dataPoint, Long.valueOf(startTime2), Long.valueOf(endTime2));
                if (endTime3 != dataPoint.getEndTime(TimeUnit.NANOSECONDS)) {
                    Log.w("Fitness", String.format("Data point end time [%d] is truncated to [%d] to match the precision [%s] of the session start and end time", new Object[]{Long.valueOf(dataPoint.getEndTime(TimeUnit.NANOSECONDS)), Long.valueOf(endTime3), timeUnit2}));
                    dataPoint.setTimeInterval(startTime3, endTime3, TimeUnit.NANOSECONDS);
                }
            }
        }

        public Builder addAggregateDataPoint(DataPoint dataPoint) {
            zzbo.zzb(dataPoint != null, (Object) "Must specify a valid aggregate data point.");
            DataSource dataSource = dataPoint.getDataSource();
            zzbo.zza(!this.zzaXi.contains(dataSource), "Data set/Aggregate data point for this data source %s is already added.", dataSource);
            DataSet.zzb(dataPoint);
            this.zzaXi.add(dataSource);
            this.zzaXg.add(dataPoint);
            return this;
        }

        public Builder addDataSet(DataSet dataSet) {
            boolean z = true;
            zzbo.zzb(dataSet != null, (Object) "Must specify a valid data set.");
            DataSource dataSource = dataSet.getDataSource();
            zzbo.zza(!this.zzaXi.contains(dataSource), "Data set for this data source %s is already added.", dataSource);
            if (dataSet.getDataPoints().isEmpty()) {
                z = false;
            }
            zzbo.zzb(z, (Object) "No data points specified in the input data set.");
            this.zzaXi.add(dataSource);
            this.zzaXf.add(dataSet);
            return this;
        }

        public SessionInsertRequest build() {
            boolean z = true;
            zzbo.zza(this.zzaXe != null, (Object) "Must specify a valid session.");
            if (this.zzaXe.getEndTime(TimeUnit.MILLISECONDS) == 0) {
                z = false;
            }
            zzbo.zza(z, (Object) "Must specify a valid end time, cannot insert a continuing session.");
            for (DataSet dataPoints : this.zzaXf) {
                for (DataPoint zzd : dataPoints.getDataPoints()) {
                    zzd(zzd);
                }
            }
            for (DataPoint zzd2 : this.zzaXg) {
                zzd(zzd2);
            }
            return new SessionInsertRequest(this);
        }

        public Builder setSession(Session session) {
            this.zzaXe = session;
            return this;
        }
    }

    SessionInsertRequest(int i, Session session, List<DataSet> list, List<DataPoint> list2, IBinder iBinder) {
        this.versionCode = i;
        this.zzaXe = session;
        this.zzaXf = Collections.unmodifiableList(list);
        this.zzaXg = Collections.unmodifiableList(list2);
        this.zzaXh = zzbxh.zzW(iBinder);
    }

    private SessionInsertRequest(Session session, List<DataSet> list, List<DataPoint> list2, zzbxg zzbxg) {
        this.versionCode = 3;
        this.zzaXe = session;
        this.zzaXf = Collections.unmodifiableList(list);
        this.zzaXg = Collections.unmodifiableList(list2);
        this.zzaXh = zzbxg;
    }

    private SessionInsertRequest(Builder builder) {
        this(builder.zzaXe, builder.zzaXf, builder.zzaXg, (zzbxg) null);
    }

    public SessionInsertRequest(SessionInsertRequest sessionInsertRequest, zzbxg zzbxg) {
        this(sessionInsertRequest.zzaXe, sessionInsertRequest.zzaXf, sessionInsertRequest.zzaXg, zzbxg);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof SessionInsertRequest)) {
                return false;
            }
            SessionInsertRequest sessionInsertRequest = (SessionInsertRequest) obj;
            if (!(zzbe.equal(this.zzaXe, sessionInsertRequest.zzaXe) && zzbe.equal(this.zzaXf, sessionInsertRequest.zzaXf) && zzbe.equal(this.zzaXg, sessionInsertRequest.zzaXg))) {
                return false;
            }
        }
        return true;
    }

    public List<DataPoint> getAggregateDataPoints() {
        return this.zzaXg;
    }

    public List<DataSet> getDataSets() {
        return this.zzaXf;
    }

    public Session getSession() {
        return this.zzaXe;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaXe, this.zzaXf, this.zzaXg});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("session", this.zzaXe).zzg("dataSets", this.zzaXf).zzg("aggregateDataPoints", this.zzaXg).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getSession(), i, false);
        zzd.zzc(parcel, 2, getDataSets(), false);
        zzd.zzc(parcel, 3, getAggregateDataPoints(), false);
        zzd.zza(parcel, 4, this.zzaXh == null ? null : this.zzaXh.asBinder(), false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zzI(parcel, zze);
    }
}
