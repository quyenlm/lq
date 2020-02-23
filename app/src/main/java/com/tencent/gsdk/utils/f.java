package com.tencent.gsdk.utils;

import java.util.HashMap;
import java.util.Map;

/* compiled from: GemReportHelper */
public final class f {
    public static void a(int i, String str) {
        HashMap hashMap = new HashMap(2);
        hashMap.put("create_time", String.valueOf(i));
        hashMap.put("uuid", str);
        Logger.d("create_time: " + i);
        Logger.d("uuid: " + str);
        com.tencent.gsdk.utils.b.f.a(3, "gsdk_init", (Map<String, String>) hashMap);
    }
}
