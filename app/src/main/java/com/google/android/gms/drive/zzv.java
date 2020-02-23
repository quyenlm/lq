package com.google.android.gms.drive;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.zzbo;

public abstract class zzv extends zza {
    private volatile transient boolean zzaMP = false;

    public void writeToParcel(Parcel parcel, int i) {
        zzbo.zzae(!this.zzaMP);
        this.zzaMP = true;
        zzJ(parcel, i);
    }

    /* access modifiers changed from: protected */
    public abstract void zzJ(Parcel parcel, int i);
}
