package com.tencent.kgvmp.c;

import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public boolean a;
    public boolean b;
    public boolean c;
    public boolean d;
    public d e = new d();
    public d f = new d();
    public d g = new d();
    public d h = new d();
    public d i = new d();
    public d j = new d();
    public d k = new d();
    public d l = new d();

    public boolean a(JSONObject jSONObject) {
        try {
            this.a = jSONObject.getBoolean("reportAvailable");
            this.b = jSONObject.getBoolean("funcAvailable");
            this.c = jSONObject.getBoolean("optFuncAvailable");
            this.d = jSONObject.getBoolean("optReportAvailable");
            this.e.a(jSONObject.getJSONObject("reportConfigWhite"));
            this.f.a(jSONObject.getJSONObject("reportConfigBlack"));
            this.g.a(jSONObject.getJSONObject("funcConfigWhite"));
            this.h.a(jSONObject.getJSONObject("funcConfigBlack"));
            this.i.a(jSONObject.getJSONObject("optFuncConfigWhite"));
            this.j.a(jSONObject.getJSONObject("optFuncConfigBlack"));
            this.k.a(jSONObject.getJSONObject("optReportConfigWhite"));
            this.l.a(jSONObject.getJSONObject("optReportConfigBlack"));
            return true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
