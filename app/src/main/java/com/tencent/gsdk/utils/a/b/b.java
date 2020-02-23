package com.tencent.gsdk.utils.a.b;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;

/* compiled from: EnvironmentFactory */
final class b {
    private static final Map<Integer, String> a = new HashMap(4);

    static {
        a.put(0, "http://ssl.cloud.gsdk.qq.com:60000");
        a.put(1, "https://gsdk.qq.com:18082");
        a.put(2, "https://cloud2.gsdk.proximabeta.com:18082");
        a.put(3, "https://gsdkcloud.mobiletest.activision.com:18082");
    }

    public static d a(int i) {
        String str = a.get(Integer.valueOf(i));
        if (TextUtils.isEmpty(str)) {
            str = "http://ssl.cloud.gsdk.qq.com:60000";
        }
        return new c(str);
    }
}
