package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPut;

public final class bv extends zzcxq {
    private static final Set<String> zzbKj = new HashSet(Arrays.asList(new String[]{"GET", HttpHead.METHOD_NAME, "POST", HttpPut.METHOD_NAME}));
    private final zzcux zzbKi;

    public bv(zzcux zzcux) {
        this.zzbKi = zzcux;
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        HashMap hashMap;
        String str = null;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 1);
        zzbo.zzaf(dpVarArr[0] instanceof dz);
        dp<?> zzfZ = dpVarArr[0].zzfZ("url");
        zzbo.zzaf(zzfZ instanceof eb);
        String value = ((eb) zzfZ).value();
        dp<?> zzfZ2 = dpVarArr[0].zzfZ("method");
        if (zzfZ2 == dv.zzbLu) {
            zzfZ2 = new eb("GET");
        }
        zzbo.zzaf(zzfZ2 instanceof eb);
        String value2 = ((eb) zzfZ2).value();
        zzbo.zzaf(zzbKj.contains(value2));
        dp<?> zzfZ3 = dpVarArr[0].zzfZ("uniqueId");
        zzbo.zzaf(zzfZ3 == dv.zzbLu || zzfZ3 == dv.zzbLt || (zzfZ3 instanceof eb));
        String value3 = (zzfZ3 == dv.zzbLu || zzfZ3 == dv.zzbLt) ? null : ((eb) zzfZ3).value();
        dp<?> zzfZ4 = dpVarArr[0].zzfZ("headers");
        zzbo.zzaf(zzfZ4 == dv.zzbLu || (zzfZ4 instanceof dz));
        HashMap hashMap2 = new HashMap();
        if (zzfZ4 == dv.zzbLu) {
            hashMap = null;
        } else {
            for (Map.Entry entry : ((dz) zzfZ4).zzDt().entrySet()) {
                String str2 = (String) entry.getKey();
                dp dpVar = (dp) entry.getValue();
                if (!(dpVar instanceof eb)) {
                    zzcvk.zzaT(String.format("Ignore the non-string value of header key %s.", new Object[]{str2}));
                } else {
                    hashMap2.put(str2, ((eb) dpVar).value());
                }
            }
            hashMap = hashMap2;
        }
        dp<?> zzfZ5 = dpVarArr[0].zzfZ("body");
        zzbo.zzaf(zzfZ5 == dv.zzbLu || (zzfZ5 instanceof eb));
        if (zzfZ5 != dv.zzbLu) {
            str = ((eb) zzfZ5).value();
        }
        if ((value2.equals("GET") || value2.equals(HttpHead.METHOD_NAME)) && str != null) {
            zzcvk.zzaT(String.format("Body of %s hit will be ignored: %s.", new Object[]{value2, str}));
        }
        this.zzbKi.zza(value, value2, value3, hashMap, str);
        zzcvk.v(String.format("QueueRequest:\n  url = %s,\n  method = %s,\n  uniqueId = %s,\n  headers = %s,\n  body = %s", new Object[]{value, value2, value3, hashMap, str}));
        return dv.zzbLu;
    }
}
