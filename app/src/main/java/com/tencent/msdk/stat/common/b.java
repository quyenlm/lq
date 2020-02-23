package com.tencent.msdk.stat.common;

import android.content.Context;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.a;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class b {
    static d a;
    private static StatLogger d = j.b();
    private static JSONObject e = new JSONObject();
    Integer b = null;
    String c = null;

    public b(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        try {
            a(context, statSpecifyReportedInfo);
            this.b = j.l(context.getApplicationContext());
            this.c = a.a(context).b();
        } catch (Throwable th) {
            d.e(th);
        }
    }

    static synchronized d a(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        d dVar;
        synchronized (b.class) {
            if (a == null) {
                a = new d(context.getApplicationContext(), statSpecifyReportedInfo);
            }
            dVar = a;
        }
        return dVar;
    }

    public static void a(Context context, Map<String, String> map) {
        if (map != null) {
            for (Map.Entry entry : new HashMap(map).entrySet()) {
                e.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void a(JSONObject jSONObject, Thread thread) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (a != null) {
                a.a(jSONObject2, thread);
            }
            p.a(jSONObject2, "cn", this.c);
            if (this.b != null) {
                jSONObject2.put("tn", this.b);
            }
            if (thread == null) {
                jSONObject.put("ev", jSONObject2);
            } else {
                jSONObject.put("errkv", jSONObject2.toString());
            }
            if (e != null && e.length() > 0) {
                jSONObject.put("eva", e);
            }
        } catch (Throwable th) {
            d.e(th);
        }
    }
}
