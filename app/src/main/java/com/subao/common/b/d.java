package com.subao.common.b;

import android.text.TextUtils;
import com.appsflyer.share.Constants;
import com.subao.common.e.ai;
import com.subao.common.j.c;
import com.subao.common.j.m;
import com.subao.common.j.n;
import com.subao.common.n.f;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHost;

/* compiled from: AuthService */
class d {
    private static d a;
    private final String b;
    private final ai c;
    private String d;
    private boolean e;

    private d(ai aiVar, String str) {
        this.c = aiVar;
        this.b = TextUtils.isEmpty(str) ? SystemMediaRouteProvider.PACKAGE_NAME : str;
        a();
    }

    static void a(ai aiVar, String str) {
        a = new d(aiVar, str);
    }

    static String a(int i) {
        return a.d + VKApiConst.VERSION + i + Constants.URL_PATH_DELIMITER;
    }

    public static void a(boolean z) {
        if (a.e != z) {
            a.e = z;
            a.a();
        }
    }

    private static List<m> a(String str) {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(new m("Authorization", "Bearer " + str));
        return arrayList;
    }

    public static void a(String str, int i, String str2, n nVar) {
        try {
            c.a(a(str2), nVar, a(1) + a.b + "/orders", f.a((com.subao.common.c) new f(str, i)));
        } catch (IOException e2) {
        }
    }

    private void a() {
        String str;
        StringBuilder sb = new StringBuilder(512);
        if (this.e) {
            str = HttpHost.DEFAULT_SCHEME_NAME;
        } else if (this.c == null) {
            str = VKApiConst.HTTPS;
        } else {
            str = this.c.a;
        }
        sb.append(str).append("://");
        if (this.c == null) {
            sb.append("api.xunyou.mobi");
        } else {
            sb.append(this.c.b);
            if (this.c.c > 0) {
                sb.append(':').append(this.c.c);
            }
        }
        sb.append("/api/");
        this.d = sb.toString();
    }
}
