package com.tencent.gsdk.utils.b;

import com.tencent.gsdk.utils.Logger;
import java.lang.reflect.Method;
import java.util.Map;

/* compiled from: GCloudCoreReporter */
final class d {
    private static Class a;
    private static Method b;

    static {
        try {
            a = Class.forName("com.tencent.gcloud.gem.report.GCloudCoreReporter");
        } catch (Exception e) {
            Logger.d("Can not find GCloudCoreReporter class", e);
            a = null;
        }
    }

    public static boolean a(int i, String str, Map<String, String> map) {
        if (a == null) {
            return false;
        }
        try {
            if (b == null) {
                b = a.getMethod("reportEvent", new Class[]{Integer.TYPE, String.class, Map.class});
            }
            b.invoke((Object) null, new Object[]{Integer.valueOf(i), str, map});
            return true;
        } catch (Exception e) {
            Logger.d("Call GCloudCoreReporter.reportEvent() failed", e);
            return false;
        }
    }
}
