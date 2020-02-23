package com.tencent.msdk.stat.a;

import android.content.Context;
import com.tencent.msdk.stat.StatConfig;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.common.p;
import org.json.JSONObject;

public class f extends d {
    public static final StatSpecifyReportedInfo a = new StatSpecifyReportedInfo();

    static {
        a.setAppKey("A9VH9B8L4GX4");
    }

    public f(Context context) {
        super(context, 0, a);
    }

    public e a() {
        return e.NETWORK_DETECTOR;
    }

    public boolean a(JSONObject jSONObject) {
        p.a(jSONObject, "actky", StatConfig.getAppKey(this.n));
        return true;
    }
}
