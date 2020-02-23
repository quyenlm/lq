package com.tencent.kgvmp.c;

import org.json.JSONArray;
import org.json.JSONObject;

public class d {
    public String[] a;
    public String[] b;
    public String[] c;
    public String[] d;

    public void a(JSONObject jSONObject) {
        JSONArray jSONArray = jSONObject.getJSONArray("manufacturer");
        this.a = new String[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            this.a[i] = jSONArray.getString(i);
        }
        JSONArray jSONArray2 = jSONObject.getJSONArray("mobile");
        this.b = new String[jSONArray2.length()];
        for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
            this.b[i2] = jSONArray2.getString(i2);
        }
        JSONArray jSONArray3 = jSONObject.getJSONArray("qimei1");
        this.c = new String[jSONArray3.length()];
        for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
            this.c[i3] = jSONArray3.getString(i3);
        }
        JSONArray jSONArray4 = jSONObject.getJSONArray("qimei2");
        this.d = new String[jSONArray4.length()];
        for (int i4 = 0; i4 < jSONArray4.length(); i4++) {
            this.d[i4] = jSONArray4.getString(i4);
        }
    }
}
