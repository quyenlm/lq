package com.google.android.gms.internal;

import com.amazonaws.services.s3.Headers;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.Map;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public final class zzam {
    public static String zza(Map<String, String> map) {
        String str = map.get("Content-Type");
        if (str != null) {
            String[] split = str.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split(HttpRequest.HTTP_REQ_ENTITY_MERGE);
                if (split2.length == 2 && split2[0].equals("charset")) {
                    return split2[1];
                }
            }
        }
        return "ISO-8859-1";
    }

    public static zzc zzb(zzn zzn) {
        boolean z;
        long j;
        long j2;
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, String> map = zzn.zzy;
        long j3 = 0;
        long j4 = 0;
        long j5 = 0;
        boolean z2 = false;
        String str = map.get("Date");
        if (str != null) {
            j3 = zzf(str);
        }
        String str2 = map.get("Cache-Control");
        if (str2 != null) {
            String[] split = str2.split(",");
            int i = 0;
            z = false;
            while (i < split.length) {
                String trim = split[i].trim();
                if (trim.equals("no-cache") || trim.equals("no-store")) {
                    return null;
                }
                if (trim.startsWith("max-age=")) {
                    try {
                        j4 = Long.parseLong(trim.substring(8));
                    } catch (Exception e) {
                    }
                } else if (trim.startsWith("stale-while-revalidate=")) {
                    try {
                        j5 = Long.parseLong(trim.substring(23));
                    } catch (Exception e2) {
                    }
                } else if (trim.equals("must-revalidate") || trim.equals("proxy-revalidate")) {
                    z = true;
                }
                i++;
                j5 = j5;
            }
            z2 = true;
        } else {
            z = false;
        }
        String str3 = map.get(Headers.EXPIRES);
        long zzf = str3 != null ? zzf(str3) : 0;
        String str4 = map.get("Last-Modified");
        long zzf2 = str4 != null ? zzf(str4) : 0;
        String str5 = map.get(Headers.ETAG);
        if (z2) {
            j2 = currentTimeMillis + (1000 * j4);
            j = z ? j2 : (1000 * j5) + j2;
        } else if (j3 <= 0 || zzf < j3) {
            j = 0;
            j2 = 0;
        } else {
            long j6 = currentTimeMillis + (zzf - j3);
            j = j6;
            j2 = j6;
        }
        zzc zzc = new zzc();
        zzc.data = zzn.data;
        zzc.zza = str5;
        zzc.zze = j2;
        zzc.zzd = j;
        zzc.zzb = j3;
        zzc.zzc = zzf2;
        zzc.zzf = map;
        return zzc;
    }

    private static long zzf(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }
}
