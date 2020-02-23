package com.tencent.mid.local;

import android.util.Log;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import org.json.JSONException;
import org.json.JSONObject;

class c {
    String a = null;
    String b = null;
    String c = "0";
    long d = 0;

    c() {
    }

    static c a(String str) {
        c cVar = new c();
        if (i.b(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull("ui")) {
                    cVar.a = jSONObject.getString("ui");
                }
                if (!jSONObject.isNull("mc")) {
                    cVar.b = jSONObject.getString("mc");
                }
                if (!jSONObject.isNull(HttpRequestParams.PUSH_XG_ID)) {
                    cVar.c = jSONObject.getString(HttpRequestParams.PUSH_XG_ID);
                }
                if (!jSONObject.isNull("ts")) {
                    cVar.d = jSONObject.getLong("ts");
                }
            } catch (JSONException e) {
                Log.w("MID", "", e);
            }
        }
        return cVar;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return i.c(this.c);
    }

    /* access modifiers changed from: package-private */
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            i.a(jSONObject, "ui", this.a);
            i.a(jSONObject, "mc", this.b);
            i.a(jSONObject, HttpRequestParams.PUSH_XG_ID, this.c);
            jSONObject.put("ts", this.d);
        } catch (JSONException e) {
            i.a((Throwable) e);
        }
        return jSONObject;
    }

    public String toString() {
        return b().toString();
    }
}
