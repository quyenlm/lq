package com.tencent.tp.a;

import android.app.Activity;
import android.content.Context;
import com.tencent.tp.TssSdkRuntime;
import com.tencent.tp.a.a;
import com.tencent.tp.a.af;
import com.tencent.tp.m;

public class e {
    private static volatile e f = null;
    public af.a a = new g(this);
    private d b;
    private j c;
    private i d;
    private af e;
    private a.C0032a g = new f(this);

    public static e a() {
        if (f == null) {
            synchronized (e.class) {
                if (f == null) {
                    f = new e();
                }
            }
        }
        return f;
    }

    private void a(Context context, String str, String str2, String str3, af.a aVar) {
        this.e = new af(context, str, str2, str3, aVar);
        this.e.a();
    }

    private void a(Context context, String str, String str2, String str3, String str4, int i, a.C0032a aVar) {
        this.c = new j(context, str, str2, str3, str4, i, aVar);
        this.c.b();
    }

    private void a(Context context, String str, String str2, String str3, String str4, a.C0032a aVar) {
        this.d = new i(context, str, str2, str3, str4, aVar);
        this.d.b();
    }

    private void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, int i, a.C0032a aVar) {
        this.b = new d(context, str, str2, str3, str4, str5, str6, i, aVar);
        this.b.b();
    }

    /* access modifiers changed from: private */
    public void a(a aVar) {
        if (aVar != null) {
            if (this.b == aVar) {
                this.b.c();
                this.b = null;
            }
            if (this.c == aVar) {
                this.c.c();
                this.c = null;
            }
            if (this.d == aVar) {
                this.d.c();
                this.d = null;
            }
        }
    }

    private void a(af afVar) {
        if (afVar != null && this.e == afVar) {
            this.e.c();
            this.e = null;
        }
    }

    private void b(String str) {
        a((a) this.b);
        a((a) this.c);
        a((a) this.d);
        a(this.e);
    }

    private void c(String str) {
        try {
            d(str);
        } catch (Throwable th) {
        }
    }

    private void d(String str) {
        if (str.startsWith("msgbox:")) {
            String[] split = str.substring(7).split("\\|");
            if (split == null || split.length < 8) {
                m.a("*#07#:TssSdkMessageBox.parseAndShow cmd err");
                return;
            }
            String str2 = split[0];
            String str3 = split[1];
            String str4 = split[2];
            String str5 = split[3];
            String str6 = split[4];
            String str7 = split[5];
            String str8 = split[6];
            String str9 = split[7];
            try {
                int parseInt = Integer.parseInt(str8);
                int parseInt2 = Integer.parseInt(str9);
                Integer.parseInt(str2);
                Activity currentActivity = TssSdkRuntime.getCurrentActivity();
                if (currentActivity == null) {
                    m.a("*#07#:getCurrentActivity failed");
                    return;
                }
                if (parseInt2 == 1) {
                    b((String) null);
                }
                if (str2.compareTo("1001") == 0) {
                    a(currentActivity, str2, str3, str4, str5, str6, str7, parseInt, this.g);
                } else if (str2.compareTo("1002") == 0) {
                    a(currentActivity, str2, str3, str4, str5, parseInt, this.g);
                } else if (str2.compareTo("1003") == 0 || str2.compareTo("1004") == 0) {
                    a(currentActivity, str2, str3, str4, str6, this.g);
                } else if (str2.compareTo("1010") == 0) {
                    a(currentActivity, str3, str4, str6, this.a);
                }
            } catch (Throwable th) {
            }
        }
    }

    public void a(String str) {
        if (str.startsWith("msgbox:")) {
            c(str);
        } else if (str.startsWith("hide_msgbox:")) {
            b(str);
        }
    }
}
