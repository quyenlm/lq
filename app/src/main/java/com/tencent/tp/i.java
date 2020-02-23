package com.tencent.tp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import java.util.List;

public class i extends AsyncTask {
    private Context a;

    public i(Context context) {
        this.a = context;
    }

    public static void a(Context context) {
        m.a("i_beg");
        try {
            b(context);
        } catch (Throwable th) {
        }
        m.a("i_end");
    }

    private static void b(Context context) {
        List<PackageInfo> m = h.a().m(context);
        if (m != null) {
            for (PackageInfo packageInfo : m) {
                if (packageInfo != null) {
                    if ((packageInfo.applicationInfo.flags & 1) > 0) {
                        try {
                            m.b("i_sys_apk_name:" + packageInfo.packageName);
                            m.b("i_sys_apk_path:" + packageInfo.applicationInfo.sourceDir);
                        } catch (Exception e) {
                            return;
                        }
                    } else {
                        try {
                            m.b("i_apk_name:" + packageInfo.packageName);
                            m.b("i_apk_path:" + packageInfo.applicationInfo.sourceDir);
                        } catch (Exception e2) {
                            return;
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public Void doInBackground(Void... voidArr) {
        a(this.a);
        return null;
    }
}
