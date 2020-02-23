package com.subao.common.i;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.subao.common.e.g;
import com.subao.common.i.m;
import com.subao.common.i.n;
import com.subao.common.i.o;
import com.tencent.imsdk.tool.etc.APNUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: MessageBuilder */
public class a {
    private final g a;
    private final p b;
    private final l c;

    public a(Context context, g gVar, p pVar) {
        this.a = gVar;
        this.b = pVar;
        this.c = new l(context);
    }

    public static k a(Context context) {
        ApplicationInfo applicationInfo;
        CharSequence loadLabel;
        String str = null;
        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        if (!(packageManager == null || (applicationInfo = context.getApplicationInfo()) == null || (loadLabel = applicationInfo.loadLabel(packageManager)) == null)) {
            str = loadLabel.toString();
        }
        return new k(str, packageName);
    }

    public p a() {
        return this.b;
    }

    public l b() {
        return this.c;
    }

    public o a(j jVar, int i, int i2) {
        return new o(jVar, o.c.START, i, i2, (o.b) null, this.b, this.a);
    }

    public n a(long j, n.a aVar) {
        return new n(this.a, j, aVar, this.c, this.b);
    }

    public m a(j jVar, List<m.a> list) {
        return new m(jVar, this.a, this.b, list);
    }

    public m a(j jVar, String str, String str2) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("param", str2);
        return a(jVar, a(str, (Map<String, String>) hashMap));
    }

    public m a(j jVar, int i, int i2, boolean z) {
        HashMap hashMap = new HashMap(3);
        hashMap.put(GGLiveConstants.PARAM.RESULT, Integer.toString(i));
        hashMap.put(APNUtil.ANP_NAME_NET, Integer.toString(i2));
        hashMap.put("accel", z ? "1" : "0");
        return a(jVar, a("tg_accel_recommend", (Map<String, String>) hashMap));
    }

    private List<m.a> a(String str, Map<String, String> map) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new m.a(str, System.currentTimeMillis() / 1000, map));
        return arrayList;
    }
}
