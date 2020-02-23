package com.tencent.mna.base.a.a;

import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: XmlCloudConfig */
public class f {
    public String a = "";

    public static f a(JSONObject jSONObject) {
        f fVar;
        try {
            String optString = jSONObject.optString("xml", "");
            if (optString.length() <= 0) {
                return null;
            }
            fVar = new f();
            try {
                fVar.a = optString;
                return fVar;
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            fVar = null;
            h.a("xml parse exception:" + e.getMessage());
            return fVar;
        }
    }
}
