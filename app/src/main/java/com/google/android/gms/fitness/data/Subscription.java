package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.common.internal.zzbo;
import java.util.Arrays;

public class Subscription extends com.google.android.gms.common.internal.safeparcel.zza {
    public static final Parcelable.Creator<Subscription> CREATOR = new zzah();
    private final DataSource zzaUd;
    private final DataType zzaUe;
    private final long zzaVj;
    private final int zzaVk;
    private final int zzaku;

    public static class zza {
        /* access modifiers changed from: private */
        public DataSource zzaUd;
        /* access modifiers changed from: private */
        public DataType zzaUe;
        /* access modifiers changed from: private */
        public long zzaVj = -1;
        /* access modifiers changed from: private */
        public int zzaVk = 2;

        public final zza zza(DataSource dataSource) {
            this.zzaUd = dataSource;
            return this;
        }

        public final zza zza(DataType dataType) {
            this.zzaUe = dataType;
            return this;
        }

        public final Subscription zztQ() {
            boolean z = false;
            zzbo.zza((this.zzaUd == null && this.zzaUe == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzaUe == null || this.zzaUd == null || this.zzaUe.equals(this.zzaUd.getDataType())) {
                z = true;
            }
            zzbo.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new Subscription(this);
        }
    }

    Subscription(int i, DataSource dataSource, DataType dataType, long j, int i2) {
        this.zzaku = i;
        this.zzaUd = dataSource;
        this.zzaUe = dataType;
        this.zzaVj = j;
        this.zzaVk = i2;
    }

    private Subscription(zza zza2) {
        this.zzaku = 1;
        this.zzaUe = zza2.zzaUe;
        this.zzaUd = zza2.zzaUd;
        this.zzaVj = zza2.zzaVj;
        this.zzaVk = zza2.zzaVk;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Subscription)) {
                return false;
            }
            Subscription subscription = (Subscription) obj;
            if (!(zzbe.equal(this.zzaUd, subscription.zzaUd) && zzbe.equal(this.zzaUe, subscription.zzaUe) && this.zzaVj == subscription.zzaVj && this.zzaVk == subscription.zzaVk)) {
                return false;
            }
        }
        return true;
    }

    public DataSource getDataSource() {
        return this.zzaUd;
    }

    public DataType getDataType() {
        return this.zzaUe;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaUd, this.zzaUd, Long.valueOf(this.zzaVj), Integer.valueOf(this.zzaVk)});
    }

    public String toDebugString() {
        Object[] objArr = new Object[1];
        objArr[0] = this.zzaUd == null ? this.zzaUe.getName() : this.zzaUd.toDebugString();
        return String.format("Subscription{%s}", objArr);
    }

    public String toString() {
        return zzbe.zzt(this).zzg("dataSource", this.zzaUd).zzg("dataType", this.zzaUe).zzg("samplingIntervalMicros", Long.valueOf(this.zzaVj)).zzg("accuracyMode", Integer.valueOf(this.zzaVk)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzd.zza(parcel, 2, (Parcelable) getDataType(), i, false);
        zzd.zza(parcel, 3, this.zzaVj);
        zzd.zzc(parcel, 4, this.zzaVk);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }

    public final DataType zztP() {
        return this.zzaUe == null ? this.zzaUd.getDataType() : this.zzaUe;
    }
}
