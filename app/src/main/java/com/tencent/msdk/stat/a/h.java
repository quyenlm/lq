package com.tencent.msdk.stat.a;

import android.content.Context;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.common.b;
import com.tencent.msdk.stat.common.j;
import org.json.JSONObject;

public class h extends d {
    private b a;
    private JSONObject p = null;

    public h(Context context, int i, JSONObject jSONObject, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = new b(context, statSpecifyReportedInfo);
        this.p = jSONObject;
    }

    public e a() {
        return e.SESSION_ENV;
    }

    public boolean a(JSONObject jSONObject) {
        if (this.e != null) {
            jSONObject.put("ut", this.e.d());
        }
        if (this.p != null) {
            jSONObject.put("cfg", this.p);
        }
        if (j.x(this.n)) {
            jSONObject.put("ncts", 1);
        }
        this.a.a(jSONObject, (Thread) null);
        return true;
    }
}
