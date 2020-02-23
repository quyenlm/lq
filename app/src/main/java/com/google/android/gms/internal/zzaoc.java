package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Map;

public class zzaoc extends zzamh {
    private static String zzain = "3";
    private static String zzaio = "01VDIWEA?";
    private static zzaoc zzaip;

    public zzaoc(zzamj zzamj) {
        super(zzamj);
    }

    private static String zzk(Object obj) {
        if (obj == null) {
            return null;
        }
        Object l = obj instanceof Integer ? new Long((long) ((Integer) obj).intValue()) : obj;
        if (!(l instanceof Long)) {
            return l instanceof Boolean ? String.valueOf(l) : l instanceof Throwable ? l.getClass().getCanonicalName() : "-";
        }
        if (Math.abs(((Long) l).longValue()) < 100) {
            return String.valueOf(l);
        }
        String str = String.valueOf(l).charAt(0) == '-' ? "-" : "";
        String valueOf = String.valueOf(Math.abs(((Long) l).longValue()));
        return str + Math.round(Math.pow(10.0d, (double) (valueOf.length() - 1))) + "..." + str + Math.round(Math.pow(10.0d, (double) valueOf.length()) - 1.0d);
    }

    public static zzaoc zzlM() {
        return zzaip;
    }

    public final void zza(zzanx zzanx, String str) {
        String zzanx2 = zzanx != null ? zzanx.toString() : "no hit data";
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? "Discarding hit. ".concat(valueOf) : new String("Discarding hit. "), zzanx2);
    }

    public final synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        int i2 = 0;
        synchronized (this) {
            zzbo.zzu(str);
            if (i >= 0) {
                i2 = i;
            }
            int length = i2 >= zzaio.length() ? zzaio.length() - 1 : i2;
            char c = zzks().zzln() ? 'C' : 'c';
            String str2 = zzain;
            char charAt = zzaio.charAt(length);
            String str3 = zzami.VERSION;
            String valueOf = String.valueOf(zzc(str, zzk(obj), zzk(obj2), zzk(obj3)));
            String sb = new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(str3).length() + String.valueOf(valueOf).length()).append(str2).append(charAt).append(c).append(str3).append(":").append(valueOf).toString();
            if (sb.length() > 1024) {
                sb = sb.substring(0, 1024);
            }
            zzaog zzkH = zzkp().zzkH();
            if (zzkH != null) {
                zzkH.zzlZ().zzbA(sb);
            }
        }
    }

    public final void zze(Map<String, String> map, String str) {
        String str2;
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry next : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append((String) next.getKey());
                sb.append('=');
                sb.append((String) next.getValue());
            }
            str2 = sb.toString();
        } else {
            str2 = "no hit data";
        }
        String valueOf = String.valueOf(str);
        zzd(valueOf.length() != 0 ? "Discarding hit. ".concat(valueOf) : new String("Discarding hit. "), str2);
    }

    /* access modifiers changed from: protected */
    public final void zzjD() {
        synchronized (zzaoc.class) {
            zzaip = this;
        }
    }
}
