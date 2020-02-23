package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbs;

@zzzn
public final class zzaez {
    public static String zzb(String str, Context context) {
        String zzw;
        if (!zzbs.zzbY().zzp(context) || TextUtils.isEmpty(str) || (zzw = zzbs.zzbY().zzw(context)) == null) {
            return str;
        }
        if (((Boolean) zzbs.zzbL().zzd(zzmo.zzDz)).booleanValue()) {
            String str2 = (String) zzbs.zzbL().zzd(zzmo.zzDA);
            if (!str.contains(str2)) {
                return str;
            }
            zzbs.zzbz();
            if (zzagz.zzaL(str)) {
                zzbs.zzbY().zzf(context, zzw);
                return str.replaceAll(str2, zzw);
            }
            zzbs.zzbz();
            if (!zzagz.zzaM(str)) {
                return str;
            }
            zzbs.zzbY().zzg(context, zzw);
            return str.replaceAll(str2, zzw);
        } else if (str.contains("fbs_aeid")) {
            return str;
        } else {
            zzbs.zzbz();
            if (zzagz.zzaL(str)) {
                zzbs.zzbY().zzf(context, zzw);
                zzbs.zzbz();
                return zzagz.zzb(str, "fbs_aeid", zzw).toString();
            }
            zzbs.zzbz();
            if (!zzagz.zzaM(str)) {
                return str;
            }
            zzbs.zzbY().zzg(context, zzw);
            zzbs.zzbz();
            return zzagz.zzb(str, "fbs_aeid", zzw).toString();
        }
    }
}
