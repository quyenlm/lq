package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.zza;
import com.google.android.gms.drive.zzv;
import java.util.List;

public final class zzbox extends zzv {
    public static final Parcelable.Creator<zzbox> CREATOR = new zzboy();
    private DataHolder zzaOR;
    private List<DriveId> zzaOS;
    private zza zzaOT;
    private boolean zzaOU;

    public zzbox(DataHolder dataHolder, List<DriveId> list, zza zza, boolean z) {
        this.zzaOR = dataHolder;
        this.zzaOS = list;
        this.zzaOT = zza;
        this.zzaOU = z;
    }

    /* access modifiers changed from: protected */
    public final void zzJ(Parcel parcel, int i) {
        int i2 = i | 1;
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, (Parcelable) this.zzaOR, i2, false);
        zzd.zzc(parcel, 3, this.zzaOS, false);
        zzd.zza(parcel, 4, (Parcelable) this.zzaOT, i2, false);
        zzd.zza(parcel, 5, this.zzaOU);
        zzd.zzI(parcel, zze);
    }
}
