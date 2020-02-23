package com.appsflyer;

import android.support.annotation.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerConfigHandler {
    @Nullable
    /* renamed from: ॱ  reason: contains not printable characters */
    static JSONObject m37(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
            try {
                if (jSONObject.optBoolean("monitor", false)) {
                    r.m125().m131();
                } else {
                    r.m125().m139();
                    r.m125().m136();
                }
            } catch (JSONException e) {
                r.m125().m139();
                r.m125().m136();
                return jSONObject;
            } catch (Throwable th) {
                th = th;
                AFLogger.afErrorLog(th.getMessage(), th);
                r.m125().m139();
                r.m125().m136();
                return jSONObject;
            }
        } catch (JSONException e2) {
            jSONObject = null;
            r.m125().m139();
            r.m125().m136();
            return jSONObject;
        } catch (Throwable th2) {
            th = th2;
            jSONObject = null;
            AFLogger.afErrorLog(th.getMessage(), th);
            r.m125().m139();
            r.m125().m136();
            return jSONObject;
        }
        return jSONObject;
    }

    public static String getUrl(String str) {
        return String.format(str, new Object[]{AppsFlyerLib.getInstance().getHost()});
    }
}
