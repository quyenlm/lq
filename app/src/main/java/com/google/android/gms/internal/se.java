package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class se {
    public static pz zza(pz pzVar, Map<String, Object> map) {
        pz zzGI = pz.zzGI();
        Iterator<Map.Entry<qr, xm>> it = pzVar.iterator();
        while (true) {
            pz pzVar2 = zzGI;
            if (!it.hasNext()) {
                return pzVar2;
            }
            Map.Entry next = it.next();
            zzGI = pzVar2.zze((qr) next.getKey(), zza((xm) next.getValue(), map));
        }
    }

    public static xm zza(xm xmVar, Map<String, Object> map) {
        Object value = xmVar.zzIR().getValue();
        if (value instanceof Map) {
            Map map2 = (Map) value;
            if (map2.containsKey(".sv")) {
                value = map.get((String) map2.get(".sv"));
            }
        }
        xm zzc = xs.zzc((qr) null, value);
        if (xmVar.zzIQ()) {
            Object value2 = xmVar.getValue();
            if (value2 instanceof Map) {
                Map map3 = (Map) value2;
                if (map3.containsKey(".sv")) {
                    String str = (String) map3.get(".sv");
                    if (map.containsKey(str)) {
                        value2 = map.get(str);
                    }
                }
            }
            return (!value2.equals(xmVar.getValue()) || !zzc.equals(xmVar.zzIR())) ? xp.zza(value2, zzc) : xmVar;
        } else if (xmVar.isEmpty()) {
            return xmVar;
        } else {
            wr wrVar = (wr) xmVar;
            sh shVar = new sh(wrVar);
            wrVar.zza(new sg(map, shVar), false);
            return !shVar.zzHm().zzIR().equals(zzc) ? shVar.zzHm().zzf(zzc) : shVar.zzHm();
        }
    }

    public static Map<String, Object> zza(ys ysVar) {
        HashMap hashMap = new HashMap();
        hashMap.put("timestamp", Long.valueOf(ysVar.zzJF()));
        return hashMap;
    }
}
