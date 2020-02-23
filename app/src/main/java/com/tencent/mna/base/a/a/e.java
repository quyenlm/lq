package com.tencent.mna.base.a.a;

import com.tencent.mna.b.d.g;
import com.tencent.mna.base.utils.h;
import com.vk.sdk.VKScope;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: RulesConfig */
public class e {
    public g[] a = new g[0];
    public g[] b = new g[0];
    public g[] c = new g[0];
    public g[] d = new g[0];
    public g[] e = new g[0];
    public g[] f = new g[0];
    public g[] g = new g[0];

    public static e a(JSONObject jSONObject) {
        e eVar = new e();
        String optString = jSONObject.optString("router", "");
        String optString2 = jSONObject.optString("export", "");
        String optString3 = jSONObject.optString("terminal", "");
        String optString4 = jSONObject.optString("signal", "");
        String optString5 = jSONObject.optString(VKScope.DIRECT, "");
        String optString6 = jSONObject.optString("network", "");
        String optString7 = jSONObject.optString("queryRule", "");
        eVar.a = a(optString);
        eVar.b = a(optString2);
        eVar.c = a(optString3);
        eVar.d = a(optString4);
        eVar.e = a(optString5);
        eVar.f = a(optString6);
        eVar.g = a(optString7);
        return eVar;
    }

    private static g[] a(String str) {
        int i = 0;
        h.a("parseRules(" + str + com.tencent.tp.a.h.b);
        g[] gVarArr = new g[0];
        try {
            JSONObject jSONObject = new JSONObject(str);
            gVarArr = new g[jSONObject.length()];
            while (true) {
                int i2 = i;
                if (i2 >= gVarArr.length) {
                    return gVarArr;
                }
                if (jSONObject.isNull(String.valueOf(i2 + 1))) {
                    h.a("parseRules(" + str + ") rule error");
                    return new g[0];
                }
                try {
                    gVarArr[i2] = new g();
                    gVarArr[i2].a = i2 + 1;
                    JSONArray jSONArray = jSONObject.getJSONArray(String.valueOf(i2 + 1));
                    gVarArr[i2].b = jSONArray.getString(0);
                    gVarArr[i2].c = jSONArray.getString(1);
                    gVarArr[i2].d = jSONArray.getInt(2);
                } catch (Exception e2) {
                    h.c("parseRules(" + str + ") error:" + e2.getMessage());
                }
                i = i2 + 1;
            }
        } catch (JSONException e3) {
            h.c("parseRules(" + str + ") error:" + e3.getMessage());
            return gVarArr;
        }
    }
}
