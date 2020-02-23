package com.google.android.gms.fitness.request;

import android.os.SystemClock;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.location.LocationRequest;
import com.tencent.component.debug.FileTracerConfig;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SensorRequest {
    public static final int ACCURACY_MODE_DEFAULT = 2;
    public static final int ACCURACY_MODE_HIGH = 3;
    public static final int ACCURACY_MODE_LOW = 1;
    private final DataSource zzaUd;
    private final DataType zzaUe;
    private final long zzaVj;
    private final int zzaVk;
    private final long zzaWW;
    private final long zzaWX;
    private final LocationRequest zzaXb;
    private final long zzaXc;

    public static class Builder {
        /* access modifiers changed from: private */
        public DataSource zzaUd;
        /* access modifiers changed from: private */
        public DataType zzaUe;
        /* access modifiers changed from: private */
        public long zzaVj = -1;
        /* access modifiers changed from: private */
        public int zzaVk = 2;
        /* access modifiers changed from: private */
        public long zzaWW = 0;
        /* access modifiers changed from: private */
        public long zzaWX = 0;
        /* access modifiers changed from: private */
        public long zzaXc = FileTracerConfig.FOREVER;
        private boolean zzaXd = false;

        public SensorRequest build() {
            boolean z = false;
            zzbo.zza((this.zzaUd == null && this.zzaUe == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzaUe == null || this.zzaUd == null || this.zzaUe.equals(this.zzaUd.getDataType())) {
                z = true;
            }
            zzbo.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new SensorRequest(this);
        }

        public Builder setAccuracyMode(int i) {
            switch (i) {
                case 1:
                case 3:
                    break;
                default:
                    i = 2;
                    break;
            }
            this.zzaVk = i;
            return this;
        }

        public Builder setDataSource(DataSource dataSource) {
            this.zzaUd = dataSource;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            this.zzaUe = dataType;
            return this;
        }

        public Builder setFastestRate(int i, TimeUnit timeUnit) {
            zzbo.zzb(i >= 0, (Object) "Cannot use a negative interval");
            this.zzaXd = true;
            this.zzaWX = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setMaxDeliveryLatency(int i, TimeUnit timeUnit) {
            zzbo.zzb(i >= 0, (Object) "Cannot use a negative delivery interval");
            this.zzaWW = timeUnit.toMicros((long) i);
            return this;
        }

        public Builder setSamplingRate(long j, TimeUnit timeUnit) {
            zzbo.zzb(j >= 0, (Object) "Cannot use a negative sampling interval");
            this.zzaVj = timeUnit.toMicros(j);
            if (!this.zzaXd) {
                this.zzaWX = this.zzaVj / 2;
            }
            return this;
        }

        public Builder setTimeout(long j, TimeUnit timeUnit) {
            boolean z = true;
            zzbo.zzb(j > 0, "Invalid time out value specified: %d", Long.valueOf(j));
            if (timeUnit == null) {
                z = false;
            }
            zzbo.zzb(z, (Object) "Invalid time unit specified");
            this.zzaXc = timeUnit.toMicros(j);
            return this;
        }
    }

    private SensorRequest(DataSource dataSource, LocationRequest locationRequest) {
        int i;
        this.zzaXb = locationRequest;
        this.zzaVj = TimeUnit.MILLISECONDS.toMicros(locationRequest.getInterval());
        this.zzaWX = TimeUnit.MILLISECONDS.toMicros(locationRequest.getFastestInterval());
        this.zzaWW = this.zzaVj;
        this.zzaUe = dataSource.getDataType();
        switch (locationRequest.getPriority()) {
            case 100:
                i = 3;
                break;
            case 104:
                i = 1;
                break;
            default:
                i = 2;
                break;
        }
        this.zzaVk = i;
        this.zzaUd = dataSource;
        long expirationTime = locationRequest.getExpirationTime();
        if (expirationTime == FileTracerConfig.FOREVER) {
            this.zzaXc = FileTracerConfig.FOREVER;
        } else {
            this.zzaXc = TimeUnit.MILLISECONDS.toMicros(expirationTime - SystemClock.elapsedRealtime());
        }
    }

    private SensorRequest(Builder builder) {
        this.zzaUd = builder.zzaUd;
        this.zzaUe = builder.zzaUe;
        this.zzaVj = builder.zzaVj;
        this.zzaWX = builder.zzaWX;
        this.zzaWW = builder.zzaWW;
        this.zzaVk = builder.zzaVk;
        this.zzaXb = null;
        this.zzaXc = builder.zzaXc;
    }

    public static SensorRequest fromLocationRequest(DataSource dataSource, LocationRequest locationRequest) {
        return new SensorRequest(dataSource, locationRequest);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof SensorRequest)) {
                return false;
            }
            SensorRequest sensorRequest = (SensorRequest) obj;
            if (!(zzbe.equal(this.zzaUd, sensorRequest.zzaUd) && zzbe.equal(this.zzaUe, sensorRequest.zzaUe) && this.zzaVj == sensorRequest.zzaVj && this.zzaWX == sensorRequest.zzaWX && this.zzaWW == sensorRequest.zzaWW && this.zzaVk == sensorRequest.zzaVk && zzbe.equal(this.zzaXb, sensorRequest.zzaXb) && this.zzaXc == sensorRequest.zzaXc)) {
                return false;
            }
        }
        return true;
    }

    public int getAccuracyMode() {
        return this.zzaVk;
    }

    public DataSource getDataSource() {
        return this.zzaUd;
    }

    public DataType getDataType() {
        return this.zzaUe;
    }

    public long getFastestRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaWX, TimeUnit.MICROSECONDS);
    }

    public long getMaxDeliveryLatency(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaWW, TimeUnit.MICROSECONDS);
    }

    public long getSamplingRate(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzaVj, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaUd, this.zzaUe, Long.valueOf(this.zzaVj), Long.valueOf(this.zzaWX), Long.valueOf(this.zzaWW), Integer.valueOf(this.zzaVk), this.zzaXb, Long.valueOf(this.zzaXc)});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("dataSource", this.zzaUd).zzg("dataType", this.zzaUe).zzg("samplingRateMicros", Long.valueOf(this.zzaVj)).zzg("deliveryLatencyMicros", Long.valueOf(this.zzaWW)).zzg("timeOutMicros", Long.valueOf(this.zzaXc)).toString();
    }

    public final long zztW() {
        return this.zzaXc;
    }
}
