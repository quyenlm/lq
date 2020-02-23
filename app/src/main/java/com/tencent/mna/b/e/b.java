package com.tencent.mna.b.e;

import com.tencent.mna.StartSpeedRet;
import com.tencent.mna.base.d.c;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: PingUpload */
public class b {
    private static c a;

    public static synchronized void a(String str, int i) {
        synchronized (b.class) {
            if (a == null) {
                a = new c(e.a(300), str, i);
            }
        }
    }

    public static synchronized int a(int i) {
        int i2;
        synchronized (b.class) {
            i2 = StartSpeedRet.SPEED_GETACCELERATORFAILED;
            if (a != null) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("lastdelay", i).put("appid", com.tencent.mna.a.b.d).put("openid", com.tencent.mna.a.b.f).put("pvpid", com.tencent.mna.a.b.a);
                    i2 = a.a(500, jSONObject.toString());
                } catch (Exception e) {
                }
            }
            h.a("uploadPingValue delay:" + i + ", res:" + i2);
        }
        return i2;
    }

    public static synchronized void a() {
        synchronized (b.class) {
            if (a != null) {
                a.a();
                a = null;
            }
        }
    }
}
