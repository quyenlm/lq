package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DataSourcesResult extends zza implements Result {
    public static final Parcelable.Creator<DataSourcesResult> CREATOR = new zzd();
    private final int versionCode;
    private final List<DataSource> zzaXy;
    private final Status zzajl;

    DataSourcesResult(int i, List<DataSource> list, Status status) {
        this.versionCode = i;
        this.zzaXy = Collections.unmodifiableList(list);
        this.zzajl = status;
    }

    public DataSourcesResult(List<DataSource> list, Status status) {
        this.versionCode = 3;
        this.zzaXy = Collections.unmodifiableList(list);
        this.zzajl = status;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataSourcesResult)) {
                return false;
            }
            DataSourcesResult dataSourcesResult = (DataSourcesResult) obj;
            if (!(this.zzajl.equals(dataSourcesResult.zzajl) && zzbe.equal(this.zzaXy, dataSourcesResult.zzaXy))) {
                return false;
            }
        }
        return true;
    }

    public List<DataSource> getDataSources() {
        return this.zzaXy;
    }

    public List<DataSource> getDataSources(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (DataSource next : this.zzaXy) {
            if (next.getDataType().equals(dataType)) {
                arrayList.add(next);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Status getStatus() {
        return this.zzajl;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzajl, this.zzaXy});
    }

    public String toString() {
        return zzbe.zzt(this).zzg("status", this.zzajl).zzg("dataSources", this.zzaXy).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zzc(parcel, 1, getDataSources(), false);
        zzd.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzd.zzc(parcel, 1000, this.versionCode);
        zzd.zzI(parcel, zze);
    }
}
