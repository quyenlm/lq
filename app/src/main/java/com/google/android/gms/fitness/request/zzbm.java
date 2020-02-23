package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbxg;
import com.google.android.gms.internal.zzbxh;
import java.util.Arrays;

public final class zzbm extends zza {
    public static final Parcelable.Creator<zzbm> CREATOR = new zzbn();
    private final DataSource zzaUd;
    private final DataType zzaUe;
    private final zzbxg zzaWo;
    private final int zzaku;

    zzbm(int i, DataType dataType, DataSource dataSource, IBinder iBinder) {
        this.zzaku = i;
        this.zzaUe = dataType;
        this.zzaUd = dataSource;
        this.zzaWo = zzbxh.zzW(iBinder);
    }

    public zzbm(DataType dataType, DataSource dataSource, zzbxg zzbxg) {
        this.zzaku = 3;
        this.zzaUe = dataType;
        this.zzaUd = dataSource;
        this.zzaWo = zzbxg;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzbm)) {
                return false;
            }
            zzbm zzbm = (zzbm) obj;
            if (!(zzbe.equal(this.zzaUd, zzbm.zzaUd) && zzbe.equal(this.zzaUe, zzbm.zzaUe))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzaUd, this.zzaUe});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaUe, i, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzaUd, i, false);
        zzd.zza(parcel, 3, this.zzaWo == null ? null : this.zzaWo.asBinder(), false);
        zzd.zzc(parcel, 1000, this.zzaku);
        zzd.zzI(parcel, zze);
    }
}
