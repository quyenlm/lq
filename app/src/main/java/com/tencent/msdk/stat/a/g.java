package com.tencent.msdk.stat.a;

import android.content.Context;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.common.p;
import org.json.JSONObject;

public class g extends d {
    Long a = null;
    String p;
    String q;

    public g(Context context, String str, String str2, int i, Long l, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.q = str;
        this.p = str2;
        this.a = l;
    }

    public e a() {
        return e.PAGE_VIEW;
    }

    public boolean a(JSONObject jSONObject) {
        p.a(jSONObject, "pi", this.p);
        p.a(jSONObject, "rf", this.q);
        if (this.a == null) {
            return true;
        }
        jSONObject.put("du", this.a);
        return true;
    }
}
