package com.google.android.gms.internal;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;

@zzzn
public final class zzaax extends zza {
    public static final Parcelable.Creator<zzaax> CREATOR = new zzaay();
    private ApplicationInfo applicationInfo;
    private PackageInfo zzSA;
    private Bundle zzTU;
    private zzaje zzTV;

    public zzaax(Bundle bundle, zzaje zzaje, PackageInfo packageInfo, ApplicationInfo applicationInfo2) {
        this.zzTU = bundle;
        this.zzTV = zzaje;
        this.zzSA = packageInfo;
        this.applicationInfo = applicationInfo2;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzTU, false);
        zzd.zza(parcel, 2, (Parcelable) this.zzTV, i, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzSA, i, false);
        zzd.zza(parcel, 4, (Parcelable) this.applicationInfo, i, false);
        zzd.zzI(parcel, zze);
    }
}
