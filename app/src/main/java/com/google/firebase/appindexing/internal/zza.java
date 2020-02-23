package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.firebase.appindexing.Action;

public final class zza extends com.google.android.gms.common.internal.safeparcel.zza implements Action {
    public static final Parcelable.Creator<zza> CREATOR = new zzc();
    private final String zzbVu;
    private final String zzbVv;
    private final String zzbVw;
    private final String zzbVx;
    private final zzb zzbVy;
    private final String zzbVz;

    public zza(String str, String str2, String str3, String str4, zzb zzb, String str5) {
        this.zzbVu = str;
        this.zzbVv = str2;
        this.zzbVw = str3;
        this.zzbVx = str4;
        this.zzbVy = zzb;
        this.zzbVz = str5;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActionImpl { ");
        sb.append("{ actionType: '").append(this.zzbVu).append("' } ");
        sb.append("{ objectName: '").append(this.zzbVv).append("' } ");
        sb.append("{ objectUrl: '").append(this.zzbVw).append("' } ");
        if (this.zzbVx != null) {
            sb.append("{ objectSameAs: '").append(this.zzbVx).append("' } ");
        }
        if (this.zzbVy != null) {
            sb.append("{ metadata: '").append(this.zzbVy.toString()).append("' } ");
        }
        if (this.zzbVz != null) {
            sb.append("{ actionStatus: '").append(this.zzbVz).append("' } ");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzbVu, false);
        zzd.zza(parcel, 2, this.zzbVv, false);
        zzd.zza(parcel, 3, this.zzbVw, false);
        zzd.zza(parcel, 4, this.zzbVx, false);
        zzd.zza(parcel, 5, (Parcelable) this.zzbVy, i, false);
        zzd.zza(parcel, 6, this.zzbVz, false);
        zzd.zzI(parcel, zze);
    }

    public final zzb zzEB() {
        return this.zzbVy;
    }
}
