package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbr;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

final class zzgo {
    private static zzea<zzbr> zza(zzea<zzbr> zzea) {
        try {
            return new zzea<>(zzgk.zzI(zzfC(zzgk.zzb(zzea.getObject()))), zzea.zzBz());
        } catch (UnsupportedEncodingException e) {
            zzdj.zzb("Escape URI: unsupported encoding", e);
            return zzea;
        }
    }

    static zzea<zzbr> zza(zzea<zzbr> zzea, int... iArr) {
        zzea<zzbr> zza;
        int length = iArr.length;
        int i = 0;
        zzea<zzbr> zzea2 = zzea;
        while (i < length) {
            int i2 = iArr[i];
            if (zzgk.zzg(zzea2.getObject()) instanceof String) {
                switch (i2) {
                    case 12:
                        zza = zza(zzea2);
                        break;
                    default:
                        zzdj.e(new StringBuilder(39).append("Unsupported Value Escaping: ").append(i2).toString());
                        zza = zzea2;
                        break;
                }
            } else {
                zzdj.e("Escaping can only be applied to strings.");
                zza = zzea2;
            }
            i++;
            zzea2 = zza;
        }
        return zzea2;
    }

    static String zzfC(String str) throws UnsupportedEncodingException {
        return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
    }
}
