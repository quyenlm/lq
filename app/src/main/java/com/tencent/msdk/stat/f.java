package com.tencent.msdk.stat;

import com.tencent.midas.oversea.comm.APDataReportManager;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.p;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    private String a = "";
    private int b = 0;
    private String c = "";
    private String d = "";

    public f(String str) {
        this.a = str;
    }

    public String a() {
        JSONObject jSONObject = new JSONObject();
        if (j.c(this.a)) {
            try {
                p.a(jSONObject, APDataReportManager.GAMEANDMONTHSLIST_PRE, this.a);
                jSONObject.put("t", this.b);
                p.a(jSONObject, APDataReportManager.ACCOUNTINPUT_PRE, this.c);
                p.a(jSONObject, "e1", this.d);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    public String b() {
        return this.a;
    }

    public String toString() {
        return "StatAccount [account=" + this.a + ", accountType=" + this.b + ", ext=" + this.c + ", ext1=" + this.d + "]";
    }
}
