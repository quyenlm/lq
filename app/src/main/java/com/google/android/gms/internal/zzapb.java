package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.midas.oversea.api.UnityPayHelper;

public final class zzapb extends zza {
    public static final Parcelable.Creator<zzapb> CREATOR = new zzapc();
    private static int zzaje = Integer.parseInt(UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR);
    private static final zzapi zzajf = new zzapj("SsbContext").zzK(true).zzbI("blob").zzml();
    private String zzajg;
    private zzapi zzajh;
    public final int zzaji;
    private byte[] zzajj;

    public zzapb(String str, zzapi zzapi) {
        this(str, zzapi, zzaje, (byte[]) null);
    }

    zzapb(String str, zzapi zzapi, int i, byte[] bArr) {
        zzbo.zzb(i == zzaje || zzaph.zzQ(i) != null, (Object) new StringBuilder(32).append("Invalid section type ").append(i).toString());
        this.zzajg = str;
        this.zzajh = zzapi;
        this.zzaji = i;
        this.zzajj = bArr;
        String sb = (this.zzaji == zzaje || zzaph.zzQ(this.zzaji) != null) ? (this.zzajg == null || this.zzajj == null) ? null : "Both content and blobContent set" : new StringBuilder(32).append("Invalid section type ").append(this.zzaji).toString();
        if (sb != null) {
            throw new IllegalArgumentException(sb);
        }
    }

    public zzapb(String str, zzapi zzapi, String str2) {
        this(str, zzapi, zzaph.zzbH(str2), (byte[]) null);
    }

    public zzapb(byte[] bArr, zzapi zzapi) {
        this((String) null, zzapi, zzaje, bArr);
    }

    public static zzapb zzd(byte[] bArr) {
        return new zzapb(bArr, zzajf);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 1, this.zzajg, false);
        zzd.zza(parcel, 3, (Parcelable) this.zzajh, i, false);
        zzd.zzc(parcel, 4, this.zzaji);
        zzd.zza(parcel, 5, this.zzajj, false);
        zzd.zzI(parcel, zze);
    }
}
