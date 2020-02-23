package com.tencent.mna.base.a;

import android.content.SharedPreferences;
import com.tencent.mna.a.b;
import com.tencent.mna.base.a.a.f;
import com.tencent.mna.base.a.b;
import com.tencent.mna.base.jni.entity.CloudRet;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: XmlCloudProxy */
public class e {
    public static String a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", b.d);
            jSONObject.put("zoneid", 1001);
            jSONObject.put("openid", str);
            jSONObject.put("xmlver", str2);
            jSONObject.put("gameIP", "10000");
            return jSONObject.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public static int b(String str, String str2) {
        if (a().equals(str) || str == null || str.length() <= 0 || str.equals("0")) {
            return 0;
        }
        CloudRet a = b.a(b.a.Def_Xml, str2);
        if (a == null) {
            return 1002;
        }
        int i = a.errno;
        h.d("XmlCloudConfig, ret errno:" + i);
        if (a.errno == 0) {
            try {
                JSONObject jSONObject = new JSONObject(a.json);
                i = jSONObject.getInt("errno");
                if (i == 0) {
                    f a2 = f.a(jSONObject);
                    if (a2 == null) {
                        return 1004;
                    }
                    c(str, a2.a);
                    return 0;
                }
            } catch (Exception e) {
                return 1003;
            }
        }
        return i;
    }

    public static String a() {
        SharedPreferences h = com.tencent.mna.b.h();
        if (h != null) {
            return h.getString("xmlver", "0");
        }
        return "0";
    }

    public static String b() {
        SharedPreferences h = com.tencent.mna.b.h();
        if (h != null) {
            return h.getString("xml", "");
        }
        return "";
    }

    private static void c(String str, String str2) {
        SharedPreferences h = com.tencent.mna.b.h();
        if (h != null) {
            h.edit().putString("xmlver", str).putString("xml", str2).apply();
        }
    }
}
