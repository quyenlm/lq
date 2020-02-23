package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzanx {
    private final Map<String, String> zzHa;
    private final List<zzane> zzaib;
    private final long zzaic;
    private final long zzaid;
    private final int zzaie;
    private final boolean zzaif;
    private final String zzaig;

    public zzanx(zzamg zzamg, Map<String, String> map, long j, boolean z) {
        this(zzamg, map, j, z, 0, 0, (List<zzane>) null);
    }

    public zzanx(zzamg zzamg, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzamg, map, j, z, j2, i, (List<zzane>) null);
    }

    public zzanx(zzamg zzamg, Map<String, String> map, long j, boolean z, long j2, int i, List<zzane> list) {
        String zza;
        String zza2;
        zzbo.zzu(zzamg);
        zzbo.zzu(map);
        this.zzaid = j;
        this.zzaif = z;
        this.zzaic = j2;
        this.zzaie = i;
        this.zzaib = list != null ? list : Collections.emptyList();
        this.zzaig = zzt(list);
        HashMap hashMap = new HashMap();
        for (Map.Entry next : map.entrySet()) {
            if (zzj(next.getKey()) && (zza2 = zza(zzamg, next.getKey())) != null) {
                hashMap.put(zza2, zzb(zzamg, next.getValue()));
            }
        }
        for (Map.Entry next2 : map.entrySet()) {
            if (!zzj(next2.getKey()) && (zza = zza(zzamg, next2.getKey())) != null) {
                hashMap.put(zza, zzb(zzamg, next2.getValue()));
            }
        }
        if (!TextUtils.isEmpty(this.zzaig)) {
            zzaos.zzb((Map<String, String>) hashMap, "_v", this.zzaig);
            if (this.zzaig.equals("ma4.0.0") || this.zzaig.equals("ma4.0.1")) {
                hashMap.remove(HttpRequestParams.AD_ID);
            }
        }
        this.zzHa = Collections.unmodifiableMap(hashMap);
    }

    private static String zza(zzamg zzamg, Object obj) {
        if (obj == null) {
            return null;
        }
        String obj2 = obj.toString();
        if (obj2.startsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN)) {
            obj2 = obj2.substring(1);
        }
        int length = obj2.length();
        if (length > 256) {
            obj2 = obj2.substring(0, 256);
            zzamg.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), obj2);
        }
        if (TextUtils.isEmpty(obj2)) {
            return null;
        }
        return obj2;
    }

    private static String zzb(zzamg zzamg, Object obj) {
        String obj2 = obj == null ? "" : obj.toString();
        int length = obj2.length();
        if (length <= 8192) {
            return obj2;
        }
        String substring = obj2.substring(0, 8192);
        zzamg.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), substring);
        return substring;
    }

    private static boolean zzj(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN);
    }

    private final String zzn(String str, String str2) {
        zzbo.zzcF(str);
        zzbo.zzb(!str.startsWith(HttpRequest.HTTP_REQ_ENTITY_JOIN), (Object) "Short param name required");
        String str3 = this.zzHa.get(str);
        return str3 != null ? str3 : str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x002a  */
    /* JADX WARNING: Removed duplicated region for block: B:14:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String zzt(java.util.List<com.google.android.gms.internal.zzane> r5) {
        /*
            r1 = 0
            if (r5 == 0) goto L_0x002c
            java.util.Iterator r2 = r5.iterator()
        L_0x0007:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x002c
            java.lang.Object r0 = r2.next()
            com.google.android.gms.internal.zzane r0 = (com.google.android.gms.internal.zzane) r0
            java.lang.String r3 = "appendVersion"
            java.lang.String r4 = r0.getId()
            boolean r3 = r3.equals(r4)
            if (r3 == 0) goto L_0x0007
            java.lang.String r0 = r0.getValue()
        L_0x0023:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x002a
        L_0x0029:
            return r1
        L_0x002a:
            r1 = r0
            goto L_0x0029
        L_0x002c:
            r0 = r1
            goto L_0x0023
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzanx.zzt(java.util.List):java.lang.String");
    }

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.zzaid);
        if (this.zzaic != 0) {
            stringBuffer.append(", dbId=").append(this.zzaic);
        }
        if (this.zzaie != 0) {
            stringBuffer.append(", appUID=").append(this.zzaie);
        }
        ArrayList arrayList = new ArrayList(this.zzHa.keySet());
        Collections.sort(arrayList);
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            String str = (String) obj;
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append(HttpRequest.HTTP_REQ_ENTITY_MERGE);
            stringBuffer.append(this.zzHa.get(str));
        }
        return stringBuffer.toString();
    }

    public final Map<String, String> zzdV() {
        return this.zzHa;
    }

    public final int zzlE() {
        return this.zzaie;
    }

    public final long zzlF() {
        return this.zzaic;
    }

    public final long zzlG() {
        return this.zzaid;
    }

    public final List<zzane> zzlH() {
        return this.zzaib;
    }

    public final boolean zzlI() {
        return this.zzaif;
    }

    public final long zzlJ() {
        return zzaos.zzbC(zzn("_s", "0"));
    }

    public final String zzlK() {
        return zzn("_m", "");
    }
}
