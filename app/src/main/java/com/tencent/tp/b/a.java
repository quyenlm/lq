package com.tencent.tp.b;

import android.content.Context;
import com.appsflyer.share.Constants;
import com.tencent.tp.a.ac;
import com.tencent.tp.a.o;
import com.tencent.tp.a.z;
import com.tencent.tp.b.c;
import com.tencent.tp.c.i;
import com.tencent.tp.m;
import com.tencent.tp.q;

public class a implements c.a {
    private z a;
    private c b;
    private Context c;
    private String d;
    private o.a e = new b(this);

    public a(Context context) {
        this.c = context;
    }

    private String b(int i) {
        return String.format("%.2fMB", new Object[]{Double.valueOf(((((double) i) * 1.0d) / 1024.0d) / 1024.0d)});
    }

    public void a() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        try {
            String c2 = i.c(this.c, i.c(ac.b));
            if (i.a(c2)) {
                new com.tencent.tp.c.a(this.c).a(c2);
                Thread.sleep(2000);
                m.b();
                return;
            }
            q.a(c2 + "not exists");
        } catch (Exception e2) {
            q.a(e2.toString());
        }
    }

    public void a(int i) {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        m.a("rootkit:dl_err_unknown");
        new o(this.c, ac.e, ac.A + i + ac.B, ac.g, (String) null, this.e).n();
    }

    public void a(int i, int i2) {
        if (i2 > 0) {
            int i3 = (i * 100) / i2;
            if (this.a == null) {
                this.a = new z(this.c);
                this.a.a(this.d, (String) null, (o.a) null);
            }
            if (this.a != null && this.a.b()) {
                this.a.a(i3);
                this.a.b(b(i));
                this.a.c(Constants.URL_PATH_DELIMITER + b(i2));
            }
        }
    }

    public void a(String str) {
        this.d = str;
        this.a = new z(this.c);
        this.a.a(this.d, (String) null, (o.a) null);
        this.b = new c(this.c, this, ac.b);
        this.b.execute(new Void[0]);
    }

    public void b() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        m.a("rootkit:dl_err_network");
        new o(this.c, ac.e, ac.y, ac.g, (String) null, this.e).n();
    }

    public void c() {
        if (this.a != null) {
            this.a.a();
            this.a = null;
        }
        m.a("rootkit:dl_err_io");
        new o(this.c, ac.e, ac.z, ac.g, (String) null, this.e).n();
    }
}
