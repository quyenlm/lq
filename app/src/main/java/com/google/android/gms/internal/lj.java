package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.imsdk.framework.request.HttpRequest;

public final class lj extends zza {
    public static final Parcelable.Creator<lj> CREATOR = new lk();
    private String zzJw;
    @Nullable
    private String zzalO;
    private String zzaln;
    private String zzbCx;
    private String zzbVY;
    private String zzbXA;
    private String zzbXB;
    private boolean zzbXC;
    private String zzbXz;

    public lj() {
        this.zzbXC = true;
    }

    public lj(@Nullable String str, @Nullable String str2, String str3, @Nullable String str4, @Nullable String str5) {
        this.zzbXz = "http://localhost";
        this.zzaln = str;
        this.zzbCx = str2;
        this.zzbXB = str5;
        this.zzbXC = true;
        if (!TextUtils.isEmpty(this.zzaln) || !TextUtils.isEmpty(this.zzbCx)) {
            this.zzbVY = zzbo.zzcF(str3);
            this.zzalO = null;
            StringBuilder sb = new StringBuilder();
            if (!TextUtils.isEmpty(this.zzaln)) {
                sb.append("id_token=").append(this.zzaln).append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            if (!TextUtils.isEmpty(this.zzbCx)) {
                sb.append("access_token=").append(this.zzbCx).append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            if (!TextUtils.isEmpty(this.zzalO)) {
                sb.append("identifier=").append(this.zzalO).append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            if (!TextUtils.isEmpty(this.zzbXB)) {
                sb.append("oauth_token_secret=").append(this.zzbXB).append(HttpRequest.HTTP_REQ_ENTITY_JOIN);
            }
            sb.append("providerId=").append(this.zzbVY);
            this.zzJw = sb.toString();
            return;
        }
        throw new IllegalArgumentException("Both idToken, and accessToken cannot be null");
    }

    lj(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, boolean z) {
        this.zzbXz = str;
        this.zzbXA = str2;
        this.zzaln = str3;
        this.zzbCx = str4;
        this.zzbVY = str5;
        this.zzalO = str6;
        this.zzJw = str7;
        this.zzbXB = str8;
        this.zzbXC = z;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.zzbXz, false);
        zzd.zza(parcel, 3, this.zzbXA, false);
        zzd.zza(parcel, 4, this.zzaln, false);
        zzd.zza(parcel, 5, this.zzbCx, false);
        zzd.zza(parcel, 6, this.zzbVY, false);
        zzd.zza(parcel, 7, this.zzalO, false);
        zzd.zza(parcel, 8, this.zzJw, false);
        zzd.zza(parcel, 9, this.zzbXB, false);
        zzd.zza(parcel, 10, this.zzbXC);
        zzd.zzI(parcel, zze);
    }
}
