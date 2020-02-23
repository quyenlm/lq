package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class az extends zzcxq {
    private static void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    private static String zzb(String str, int i, Set<Character> set) {
        switch (i) {
            case 1:
                try {
                    return URLEncoder.encode(str, "UTF-8").replaceAll("\\+", "%20");
                } catch (UnsupportedEncodingException e) {
                    return str;
                }
            case 2:
                String replace = str.replace("\\", "\\\\");
                Iterator<Character> it = set.iterator();
                while (true) {
                    String str2 = replace;
                    if (!it.hasNext()) {
                        return str2;
                    }
                    String ch = it.next().toString();
                    String valueOf = String.valueOf(ch);
                    replace = str2.replace(ch, valueOf.length() != 0 ? "\\".concat(valueOf) : new String("\\"));
                }
            default:
                return str;
        }
    }

    private static void zzb(StringBuilder sb, String str, int i, Set<Character> set) {
        sb.append(zzb(str, i, set));
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        int i = 2;
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        dw dwVar = dpVarArr[0];
        dv dvVar = dpVarArr.length > 1 ? dpVarArr[1] : dv.zzbLu;
        String str = "";
        if (dpVarArr.length > 2) {
            str = dpVarArr[2] == dv.zzbLu ? "" : zzcxp.zzd(dpVarArr[2]);
        }
        String str2 = HttpRequest.HTTP_REQ_ENTITY_MERGE;
        if (dpVarArr.length > 3) {
            str2 = dpVarArr[3] == dv.zzbLu ? HttpRequest.HTTP_REQ_ENTITY_MERGE : zzcxp.zzd(dpVarArr[3]);
        }
        HashSet hashSet = null;
        if (dvVar != dv.zzbLu) {
            zzbo.zzaf(dvVar instanceof eb);
            if ("url".equals(dvVar.zzDl())) {
                i = 1;
            } else if (!"backslash".equals(dvVar.zzDl())) {
                return new eb("");
            } else {
                HashSet hashSet2 = new HashSet();
                zza((Set<Character>) hashSet2, str);
                zza((Set<Character>) hashSet2, str2);
                hashSet2.remove('\\');
                hashSet = hashSet2;
            }
        } else {
            i = 0;
        }
        StringBuilder sb = new StringBuilder();
        if (dwVar instanceof dw) {
            for (dp dpVar : dwVar.zzDs()) {
                if (!z) {
                    sb.append(str);
                }
                zzb(sb, zzcxp.zzd(dpVar), i, hashSet);
                z = false;
            }
        } else if (dwVar instanceof dz) {
            Map zzDt = ((dz) dwVar).zzDt();
            boolean z2 = true;
            for (String str3 : zzDt.keySet()) {
                if (!z2) {
                    sb.append(str);
                }
                String zzd = zzcxp.zzd((dp) zzDt.get(str3));
                zzb(sb, str3, i, hashSet);
                sb.append(str2);
                zzb(sb, zzd, i, hashSet);
                z2 = false;
            }
        } else {
            zzb(sb, zzcxp.zzd(dwVar), i, hashSet);
        }
        return new eb(sb.toString());
    }
}
