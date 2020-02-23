package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.awareness.state.BeaconState;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.tp.a.h;

public final class zzasu extends zza implements BeaconState.BeaconInfo {
    public static final Parcelable.Creator<zzasu> CREATOR = new zzass();
    private final String zzVB;
    private final String zzanR;
    private final byte[] zzanS;

    public zzasu(String str, String str2, byte[] bArr) {
        this.zzanR = zzbo.zzcF(str);
        this.zzVB = zzbo.zzcF(str2);
        this.zzanS = bArr;
    }

    public final byte[] getContent() {
        return this.zzanS;
    }

    public final String getNamespace() {
        return this.zzanR;
    }

    public final String getType() {
        return this.zzVB;
    }

    public final String toString() {
        String str = this.zzanS == null ? "<null>" : new String(this.zzanS);
        String str2 = this.zzanR;
        String str3 = this.zzVB;
        return new StringBuilder(String.valueOf(str2).length() + 6 + String.valueOf(str3).length() + String.valueOf(str).length()).append(h.a).append(str2).append(", ").append(str3).append(", ").append(str).append(h.b).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, getNamespace(), false);
        zzd.zza(parcel, 3, getType(), false);
        zzd.zza(parcel, 4, getContent(), false);
        zzd.zzI(parcel, zze);
    }
}
