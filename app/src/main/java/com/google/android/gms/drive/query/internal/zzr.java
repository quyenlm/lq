package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.query.Filter;
import java.util.ArrayList;
import java.util.List;

public final class zzr extends zza {
    public static final Parcelable.Creator<zzr> CREATOR = new zzs();
    private List<Filter> zzaQX;
    private zzx zzaRd;
    private List<FilterHolder> zzaRs;

    public zzr(zzx zzx, Filter filter, Filter... filterArr) {
        this.zzaRd = zzx;
        this.zzaRs = new ArrayList(filterArr.length + 1);
        this.zzaRs.add(new FilterHolder(filter));
        this.zzaQX = new ArrayList(filterArr.length + 1);
        this.zzaQX.add(filter);
        for (Filter filter2 : filterArr) {
            this.zzaRs.add(new FilterHolder(filter2));
            this.zzaQX.add(filter2);
        }
    }

    public zzr(zzx zzx, Iterable<Filter> iterable) {
        this.zzaRd = zzx;
        this.zzaQX = new ArrayList();
        this.zzaRs = new ArrayList();
        for (Filter next : iterable) {
            this.zzaQX.add(next);
            this.zzaRs.add(new FilterHolder(next));
        }
    }

    zzr(zzx zzx, List<FilterHolder> list) {
        this.zzaRd = zzx;
        this.zzaRs = list;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaRd, i, false);
        zzd.zzc(parcel, 2, this.zzaRs, false);
        zzd.zzI(parcel, zze);
    }

    public final <T> T zza(zzj<T> zzj) {
        ArrayList arrayList = new ArrayList();
        for (FilterHolder filter : this.zzaRs) {
            arrayList.add(filter.getFilter().zza(zzj));
        }
        return zzj.zza(this.zzaRd, (List<T>) arrayList);
    }
}
