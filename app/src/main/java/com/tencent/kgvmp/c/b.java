package com.tencent.kgvmp.c;

import com.tencent.kgvmp.e.f;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class b {
    /* access modifiers changed from: private */
    public static final String g = com.tencent.kgvmp.a.a.a;
    public boolean a;
    public String b;
    public String c;
    public c d;
    public C0021b e;
    public a f;

    public class a {
        public String[] a;
        public String[] b;

        public a() {
        }

        public boolean a(JSONObject jSONObject) {
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("cpu");
                this.a = new String[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.a[i] = jSONArray.getString(i);
                }
                JSONArray jSONArray2 = jSONObject.getJSONArray("gpu");
                this.b = new String[jSONArray2.length()];
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    this.b[i2] = jSONArray2.getString(i2);
                }
                return true;
            } catch (Exception e) {
                f.a(b.g, "Hardware:parseJson: exception.");
                return false;
            }
        }
    }

    /* renamed from: com.tencent.kgvmp.c.b$b  reason: collision with other inner class name */
    public class C0021b {
        public ArrayList<String> a = new ArrayList<>();
        public ArrayList<String> b = new ArrayList<>();

        public C0021b() {
        }

        public boolean a(JSONObject jSONObject) {
            try {
                JSONArray jSONArray = jSONObject.getJSONArray("match");
                for (int i = 0; i < jSONArray.length(); i++) {
                    this.a.add(jSONArray.getString(i));
                }
                JSONArray jSONArray2 = jSONObject.getJSONArray("exsit");
                for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                    this.b.add(jSONArray2.getString(i2));
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                f.a(b.g, "PackageList:parseJson: exception.");
                return false;
            }
        }
    }

    public class c {
        public HashMap<String, String[]> a = new HashMap<>();
        public HashMap<String, String[]> b = new HashMap<>();

        public c() {
        }

        public boolean a(JSONObject jSONObject) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("match");
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    JSONArray jSONArray = jSONObject2.getJSONArray(next);
                    String[] strArr = new String[jSONArray.length()];
                    for (int i = 0; i < jSONArray.length(); i++) {
                        strArr[i] = jSONArray.getString(i);
                    }
                    this.a.put(next, strArr);
                }
                JSONObject jSONObject3 = jSONObject.getJSONObject("exsit");
                Iterator<String> keys2 = jSONObject3.keys();
                while (keys2.hasNext()) {
                    String next2 = keys2.next();
                    JSONArray jSONArray2 = jSONObject3.getJSONArray(next2);
                    String[] strArr2 = new String[jSONArray2.length()];
                    for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                        strArr2[i2] = jSONArray2.getString(i2);
                    }
                    this.b.put(next2, strArr2);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                f.a(b.g, "Prop:parseJson: exception.");
                return false;
            }
        }
    }

    public boolean a(JSONObject jSONObject) {
        try {
            this.b = jSONObject.getString("model");
            this.c = jSONObject.getString("brand");
            this.a = jSONObject.getBoolean("available");
            this.d = new c();
            this.d.a(jSONObject.getJSONObject("prop"));
            this.e = new C0021b();
            this.e.a(jSONObject.getJSONObject("package"));
            this.f = new a();
            this.f.a(jSONObject.getJSONObject("hardware"));
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            f.a(g, "DeviceCheckConfig:parseJson: exception.");
            return false;
        }
    }
}
