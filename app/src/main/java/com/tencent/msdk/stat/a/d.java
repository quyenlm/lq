package com.tencent.msdk.stat.a;

import android.content.Context;
import android.os.Build;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import com.tencent.msdk.a.a.i;
import com.tencent.msdk.stat.StatConfig;
import com.tencent.msdk.stat.StatSpecifyReportedInfo;
import com.tencent.msdk.stat.aj;
import com.tencent.msdk.stat.common.a;
import com.tencent.msdk.stat.common.j;
import com.tencent.msdk.stat.common.p;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public abstract class d {
    private static final List<e> a = Arrays.asList(new e[]{e.NETWORK_DETECTOR});
    protected static String j = null;
    protected String b = null;
    protected long c;
    protected int d;
    protected a e = null;
    protected int f;
    protected String g = null;
    protected String h = null;
    protected String i = null;
    protected boolean k = false;
    protected boolean l = false;
    protected int m = 0;
    protected Context n;
    protected StatSpecifyReportedInfo o = null;

    d(Context context, int i2, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.n = context;
        this.c = System.currentTimeMillis() / 1000;
        this.d = i2;
        this.i = j.i(context);
        if (statSpecifyReportedInfo != null) {
            this.o = statSpecifyReportedInfo;
            if (j.c(statSpecifyReportedInfo.getAppKey())) {
                this.b = statSpecifyReportedInfo.getAppKey();
            }
            if (j.c(statSpecifyReportedInfo.getInstallChannel())) {
                this.h = statSpecifyReportedInfo.getInstallChannel();
            }
            if (j.c(statSpecifyReportedInfo.getVersion())) {
                this.i = statSpecifyReportedInfo.getVersion();
            }
            this.k = statSpecifyReportedInfo.isImportant();
        }
        if (!j.c(this.b)) {
            this.b = StatConfig.getAppKey(context);
        }
        if (!j.c(this.h)) {
            this.h = StatConfig.getInstallChannel(context);
        }
        this.g = StatConfig.getCustomUserId(context);
        this.e = aj.a(context).b(context);
        e a2 = a();
        if (!a.contains(a2)) {
            this.f = j.r(context).intValue();
        } else {
            this.f = -a().a();
            if (a2 != e.NETWORK_DETECTOR) {
                this.l = true;
            }
        }
        if (!i.c(j)) {
            j = StatConfig.getLocalMidOnly(context);
            if (!j.c(j)) {
                j = "0";
            }
        }
        this.m = j.n(context);
    }

    public abstract e a();

    public abstract boolean a(JSONObject jSONObject);

    public boolean b(JSONObject jSONObject) {
        try {
            p.a(jSONObject, "ky", this.b);
            jSONObject.put("et", a().a());
            if (this.e != null) {
                jSONObject.put("ui", this.e.b());
                p.a(jSONObject, "mc", this.e.c());
                int d2 = this.e.d();
                jSONObject.put("ut", d2);
                if (d2 == 0 && j.v(this.n) == 1) {
                    jSONObject.put("ia", 1);
                }
            }
            p.a(jSONObject, "cui", this.g);
            String appVersion = StatConfig.getAppVersion();
            if (!j.c(appVersion)) {
                p.a(jSONObject, "av", this.i);
            } else {
                p.a(jSONObject, "av", appVersion);
                p.a(jSONObject, "appv", this.i);
            }
            if (this.k) {
                jSONObject.put("impt", 1);
            }
            p.a(jSONObject, HttpRequestParams.PUSH_XG_ID, j);
            jSONObject.put("idx", this.f);
            jSONObject.put("si", this.d);
            jSONObject.put("ts", this.c);
            JSONObject commonAttr = StatConfig.getCommonAttr(this.n);
            if (commonAttr != null && commonAttr.length() > 0) {
                jSONObject.put("com", commonAttr.toString());
            }
            jSONObject.put("dts", j.a(this.n, false));
            jSONObject.put(HttpRequestParams.OS, 1);
            p.a(jSONObject, "ov", Integer.toString(Build.VERSION.SDK_INT));
            p.a(jSONObject, "md", Build.MODEL);
            jSONObject.put("jb", this.m);
            p.a(jSONObject, "mf", Build.MANUFACTURER);
            JSONObject customGlobalReportContent = StatConfig.getCustomGlobalReportContent();
            if (customGlobalReportContent != null && customGlobalReportContent.length() > 0) {
                jSONObject.put("cc", customGlobalReportContent.toString());
            }
            p.a(jSONObject, "qq", StatConfig.getQQ());
            return a(jSONObject);
        } catch (Throwable th) {
            return false;
        }
    }

    public long c() {
        return this.c;
    }

    public StatSpecifyReportedInfo d() {
        return this.o;
    }

    public Context e() {
        return this.n;
    }

    public boolean f() {
        return this.k;
    }

    public String g() {
        try {
            JSONObject jSONObject = new JSONObject();
            b(jSONObject);
            return jSONObject.toString();
        } catch (Throwable th) {
            return "";
        }
    }
}
