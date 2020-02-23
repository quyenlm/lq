package com.tencent.gsdk.utils.b;

import android.content.Context;
import com.tencent.gsdk.utils.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* compiled from: ReportManager */
public final class f {
    private static List<e> a;

    public static void a(int i) {
        Logger.d("ReportManager.init(" + i + ") called.");
        a(i, (Context) null, (String) null);
    }

    public static void a(int i, Context context, String str) {
        a = g.a(i);
        Logger.d("sReporters:" + Arrays.toString(a.toArray()));
        if (context != null && str != null) {
            for (e next : a) {
                if (!next.a(context, str)) {
                    Logger.d(next + " init failed");
                }
            }
        }
    }

    public static void a(int i, String str, Map<String, String> map) {
        a(a, i, str, map);
    }

    private static void a(List<e> list, int i, String str, Map<String, String> map) {
        for (e next : list) {
            if (!next.a(i, str, map)) {
                Logger.d(next + " report failed");
            }
        }
    }
}
