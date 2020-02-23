package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.Iterator;

public final class zzcew extends zza implements Iterable<String> {
    public static final Parcelable.Creator<zzcew> CREATOR = new zzcey();
    /* access modifiers changed from: private */
    public final Bundle zzbpJ;

    zzcew(Bundle bundle) {
        this.zzbpJ = bundle;
    }

    /* access modifiers changed from: package-private */
    public final Object get(String str) {
        return this.zzbpJ.get(str);
    }

    public final Iterator<String> iterator() {
        return new zzcex(this);
    }

    public final int size() {
        return this.zzbpJ.size();
    }

    public final String toString() {
        return this.zzbpJ.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, zzyt(), false);
        zzd.zzI(parcel, zze);
    }

    public final Bundle zzyt() {
        return new Bundle(this.zzbpJ);
    }
}
