package com.google.android.gms.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzd;
import java.util.List;

@zzzn
public final class zzia extends zza {
    public static final Parcelable.Creator<zzia> CREATOR = new zzib();
    @Nullable
    public final String url;
    private long zzzu;
    private String zzzv;
    private String zzzw;
    private String zzzx;
    private Bundle zzzy;
    private boolean zzzz;

    zzia(@Nullable String str, long j, String str2, String str3, String str4, Bundle bundle, boolean z) {
        this.url = str;
        this.zzzu = j;
        this.zzzv = str2 == null ? "" : str2;
        this.zzzw = str3 == null ? "" : str3;
        this.zzzx = str4 == null ? "" : str4;
        this.zzzy = bundle == null ? new Bundle() : bundle;
        this.zzzz = z;
    }

    @Nullable
    public static zzia zzB(String str) {
        return zze(Uri.parse(str));
    }

    @Nullable
    public static zzia zze(Uri uri) {
        try {
            if (!"gcache".equals(uri.getScheme())) {
                return null;
            }
            List<String> pathSegments = uri.getPathSegments();
            if (pathSegments.size() != 2) {
                zzafr.zzaT(new StringBuilder(62).append("Expected 2 path parts for namespace and id, found :").append(pathSegments.size()).toString());
                return null;
            }
            String str = pathSegments.get(0);
            String str2 = pathSegments.get(1);
            String host = uri.getHost();
            String queryParameter = uri.getQueryParameter("url");
            boolean equals = "1".equals(uri.getQueryParameter("read_only"));
            String queryParameter2 = uri.getQueryParameter("expiration");
            long parseLong = queryParameter2 == null ? 0 : Long.parseLong(queryParameter2);
            Bundle bundle = new Bundle();
            for (String next : zzbs.zzbB().zzh(uri)) {
                if (next.startsWith("tag.")) {
                    bundle.putString(next.substring(4), uri.getQueryParameter(next));
                }
            }
            return new zzia(queryParameter, parseLong, host, str, str2, bundle, equals);
        } catch (NullPointerException | NumberFormatException e) {
            zzafr.zzc("Unable to parse Uri into cache offering.", e);
            return null;
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzd.zze(parcel);
        zzd.zza(parcel, 2, this.url, false);
        zzd.zza(parcel, 3, this.zzzu);
        zzd.zza(parcel, 4, this.zzzv, false);
        zzd.zza(parcel, 5, this.zzzw, false);
        zzd.zza(parcel, 6, this.zzzx, false);
        zzd.zza(parcel, 7, this.zzzy, false);
        zzd.zza(parcel, 8, this.zzzz);
        zzd.zzI(parcel, zze);
    }
}
