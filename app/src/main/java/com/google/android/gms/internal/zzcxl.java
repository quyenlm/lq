package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class zzcxl {
    private static final Map<String, zzcxm> zzbJO;

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(zzbf.CONTAINS.toString(), new zzcxm("contains"));
        hashMap.put(zzbf.ENDS_WITH.toString(), new zzcxm("endsWith"));
        hashMap.put(zzbf.EQUALS.toString(), new zzcxm("equals"));
        hashMap.put(zzbf.GREATER_EQUALS.toString(), new zzcxm("greaterEquals"));
        hashMap.put(zzbf.GREATER_THAN.toString(), new zzcxm("greaterThan"));
        hashMap.put(zzbf.LESS_EQUALS.toString(), new zzcxm("lessEquals"));
        hashMap.put(zzbf.LESS_THAN.toString(), new zzcxm("lessThan"));
        hashMap.put(zzbf.REGEX.toString(), new zzcxm("regex", new String[]{zzbg.ARG0.toString(), zzbg.ARG1.toString(), zzbg.IGNORE_CASE.toString()}));
        hashMap.put(zzbf.STARTS_WITH.toString(), new zzcxm("startsWith"));
        zzbJO = hashMap;
    }

    public static ea zza(String str, Map<String, dp<?>> map, zzcwa zzcwa) {
        if (!zzbJO.containsKey(str)) {
            throw new RuntimeException(new StringBuilder(String.valueOf(str).length() + 47).append("Fail to convert ").append(str).append(" to the internal representation").toString());
        }
        zzcxm zzcxm = zzbJO.get(str);
        List<dp<?>> zza = zza(zzcxm.zzCH(), map);
        ArrayList arrayList = new ArrayList();
        arrayList.add(new eb("gtmUtils"));
        ea eaVar = new ea("15", arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(eaVar);
        arrayList2.add(new eb("mobile"));
        ea eaVar2 = new ea("17", arrayList2);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(eaVar2);
        arrayList3.add(new eb(zzcxm.zzCG()));
        arrayList3.add(new dw(zza));
        return new ea("2", arrayList3);
    }

    public static String zza(zzbf zzbf) {
        return zzfM(zzbf.toString());
    }

    private static List<dp<?>> zza(String[] strArr, Map<String, dp<?>> map) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= strArr.length) {
                return arrayList;
            }
            if (!map.containsKey(strArr[i2])) {
                arrayList.add(dv.zzbLu);
            } else {
                arrayList.add(map.get(strArr[i2]));
            }
            i = i2 + 1;
        }
    }

    public static String zzfM(String str) {
        if (zzbJO.containsKey(str)) {
            return zzbJO.get(str).zzCG();
        }
        return null;
    }
}
