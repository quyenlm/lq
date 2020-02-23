package com.tencent.msdk.stat.a;

import android.content.Context;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.common.p;
import com.tencent.msdk.stat.f;
import org.json.JSONObject;

public class a extends d {
    private f a = null;

    public a(Context context, int i, f fVar, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a = fVar;
    }

    public e a() {
        return e.ADDITION;
    }

    public boolean a(JSONObject jSONObject) {
        p.a(jSONObject, "qq", this.a.b());
        jSONObject.put("acc", this.a.a());
        return true;
    }
}
