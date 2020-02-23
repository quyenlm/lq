package com.tencent.bugly;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.tencent.qqgamemi.BasePlatformConst;
import java.util.Map;

/* compiled from: BUGLY */
public class Bugly {
    public static final String SDK_IS_DEV = "false";
    private static boolean a;
    public static Context applicationContext = null;
    private static String[] b = {"BuglyCrashModule", "BuglyRqdModule", "BuglyBetaModule"};
    private static String[] c = {"BuglyRqdModule", "BuglyCrashModule", "BuglyBetaModule"};
    public static boolean enable = true;
    public static Boolean isDev;

    public static void init(Context context, String str, boolean z) {
        init(context, str, z, (BuglyStrategy) null);
    }

    public static synchronized void init(Context context, String str, boolean z, BuglyStrategy buglyStrategy) {
        synchronized (Bugly.class) {
            if (!a) {
                a = true;
                Context a2 = z.a(context);
                applicationContext = a2;
                if (a2 == null) {
                    Log.e(x.a, "init arg 'context' should not be null!");
                } else {
                    if (isDev()) {
                        b = c;
                    }
                    for (String str2 : b) {
                        try {
                            if (str2.equals("BuglyCrashModule")) {
                                b.a((a) CrashModule.getInstance());
                            } else if (!str2.equals("BuglyBetaModule") && !str2.equals("BuglyRqdModule")) {
                                str2.equals("BuglyFeedbackModule");
                            }
                        } catch (Throwable th) {
                            x.b(th);
                        }
                    }
                    b.a = enable;
                    b.a(applicationContext, str, z, buglyStrategy);
                }
            }
        }
    }

    public static synchronized String getAppChannel() {
        byte[] bArr;
        String str = null;
        synchronized (Bugly.class) {
            a b2 = a.b();
            if (b2 != null) {
                if (TextUtils.isEmpty(b2.l)) {
                    p a2 = p.a();
                    if (a2 == null) {
                        str = b2.l;
                    } else {
                        Map<String, byte[]> a3 = a2.a((int) BasePlatformConst.ACTION_DOWNLOAD_CORE_PLUGIN_SUCCESS, (o) null, true);
                        if (!(a3 == null || (bArr = a3.get("app_channel")) == null)) {
                            str = new String(bArr);
                        }
                    }
                }
                str = b2.l;
            }
        }
        return str;
    }

    public static boolean isDev() {
        if (isDev == null) {
            isDev = Boolean.valueOf(Boolean.parseBoolean("false".replace("@", "")));
        }
        return isDev.booleanValue();
    }
}
