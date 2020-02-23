package com.tencent.msdk.stat.a;

import android.content.Context;
import com.tencent.msdk.stat.StatServiceImpl;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import java.util.Map;
import java.util.Properties;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    protected c a = new c();
    private long p = -1;

    public b(Context context, int i, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.a.a = str;
    }

    private void h() {
        Properties commonKeyValueForKVEvent;
        if (this.a.a != null && (commonKeyValueForKVEvent = StatServiceImpl.getCommonKeyValueForKVEvent(this.a.a)) != null && commonKeyValueForKVEvent.size() > 0) {
            if (this.a.c == null || this.a.c.length() == 0) {
                this.a.c = new JSONObject(commonKeyValueForKVEvent);
                return;
            }
            for (Map.Entry entry : commonKeyValueForKVEvent.entrySet()) {
                try {
                    this.a.c.put(entry.getKey().toString(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public e a() {
        return e.CUSTOM;
    }

    public void a(long j) {
        this.p = j;
    }

    public boolean a(JSONObject jSONObject) {
        jSONObject.put("ei", this.a.a);
        if (this.p > 0) {
            jSONObject.put("du", this.p);
        }
        if (this.a.b == null) {
            h();
            jSONObject.put("kv", this.a.c);
            return true;
        }
        jSONObject.put("ar", this.a.b);
        return true;
    }

    public c b() {
        return this.a;
    }
}
