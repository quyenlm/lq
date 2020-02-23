package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbe;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public final class zzcdn extends zza {
    public static final Parcelable.Creator<zzcdn> CREATOR = new zzcdo();
    static final List<zzcbz> zzbiU = Collections.emptyList();
    @Nullable
    private String mTag;
    private LocationRequest zzaXb;
    @Nullable
    private String zzanK;
    private List<zzcbz> zzbiV;
    private boolean zzbiW;
    private boolean zzbiX;
    private boolean zzbiY;
    private boolean zzbiZ = true;

    zzcdn(LocationRequest locationRequest, List<zzcbz> list, @Nullable String str, boolean z, boolean z2, boolean z3, String str2) {
        this.zzaXb = locationRequest;
        this.zzbiV = list;
        this.mTag = str;
        this.zzbiW = z;
        this.zzbiX = z2;
        this.zzbiY = z3;
        this.zzanK = str2;
    }

    @Deprecated
    public static zzcdn zza(LocationRequest locationRequest) {
        return new zzcdn(locationRequest, zzbiU, (String) null, false, false, false, (String) null);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzcdn)) {
            return false;
        }
        zzcdn zzcdn = (zzcdn) obj;
        return zzbe.equal(this.zzaXb, zzcdn.zzaXb) && zzbe.equal(this.zzbiV, zzcdn.zzbiV) && zzbe.equal(this.mTag, zzcdn.mTag) && this.zzbiW == zzcdn.zzbiW && this.zzbiX == zzcdn.zzbiX && this.zzbiY == zzcdn.zzbiY && zzbe.equal(this.zzanK, zzcdn.zzanK);
    }

    public final int hashCode() {
        return this.zzaXb.hashCode();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.zzaXb.toString());
        if (this.mTag != null) {
            sb.append(" tag=").append(this.mTag);
        }
        if (this.zzanK != null) {
            sb.append(" moduleId=").append(this.zzanK);
        }
        sb.append(" hideAppOps=").append(this.zzbiW);
        sb.append(" clients=").append(this.zzbiV);
        sb.append(" forceCoarseLocation=").append(this.zzbiX);
        if (this.zzbiY) {
            sb.append(" exemptFromBackgroundThrottle");
        }
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, (Parcelable) this.zzaXb, i, false);
        zzd.zzc(parcel, 5, this.zzbiV, false);
        zzd.zza(parcel, 6, this.mTag, false);
        zzd.zza(parcel, 7, this.zzbiW);
        zzd.zza(parcel, 8, this.zzbiX);
        zzd.zza(parcel, 9, this.zzbiY);
        zzd.zza(parcel, 10, this.zzanK, false);
        zzd.zzI(parcel, zze);
    }
}
