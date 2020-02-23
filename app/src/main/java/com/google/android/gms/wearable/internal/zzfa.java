package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class zzfa {
    /* access modifiers changed from: private */
    public static Map<String, CapabilityInfo> zzN(List<zzaa> list) {
        HashMap hashMap = new HashMap(list.size() << 1);
        for (zzaa next : list) {
            hashMap.put(next.getName(), new zzw(next));
        }
        return hashMap;
    }
}
