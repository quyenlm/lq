package com.tencent.mna.base.a;

import android.content.SharedPreferences;
import com.tencent.mna.b;
import com.tencent.mna.base.a.a.e;
import com.tencent.mna.base.a.b;
import com.tencent.mna.base.jni.entity.CloudRet;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: RulesCache */
class d {

    /* compiled from: RulesCache */
    static class a {
        int a;
        e b;

        a(int i, e eVar) {
            this.a = i;
            this.b = eVar;
        }
    }

    static a a(int i, String str) {
        SharedPreferences h = b.h();
        e a2 = a(h, i);
        int i2 = 0;
        if (a2 == null) {
            try {
                CloudRet a3 = b.a(b.a.Def_Dgn, str);
                if (a3 == null) {
                    return new a(1001, (e) null);
                }
                i2 = a3.errno;
                h.b("DgnRulesConfig, ret errno:" + i2);
                if (i2 == 0) {
                    a2 = e.a(new JSONObject(a3.json));
                    a(h, i, a3.json);
                }
            } catch (Throwable th) {
                i2 = 1002;
                a2 = null;
            }
        }
        return new a(i2, a2);
    }

    private static boolean a(SharedPreferences sharedPreferences, int i, String str) {
        try {
            sharedPreferences.edit().putInt("rules_ver", i).putString("diagnose_rules", str).apply();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static e a(SharedPreferences sharedPreferences, int i) {
        if (sharedPreferences == null || sharedPreferences.getInt("rules_ver", -1) != i) {
            return null;
        }
        try {
            return e.a(new JSONObject(sharedPreferences.getString("diagnose_rules", "")));
        } catch (Throwable th) {
            return null;
        }
    }
}
