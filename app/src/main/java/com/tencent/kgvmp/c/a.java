package com.tencent.kgvmp.c;

import com.tencent.kgvmp.e.c;
import com.tencent.kgvmp.e.d;
import com.tencent.kgvmp.e.f;
import com.tencent.kgvmp.e.g;
import com.tencent.kgvmp.report.b;
import com.tencent.kgvmp.report.e;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    private static final String a = com.tencent.kgvmp.a.a.a;
    private String b;
    private c c;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;
    private String h;
    private String i;
    private String j;
    private String k;

    public a(String str) {
        this.b = str;
    }

    private boolean a(String[] strArr, String str) {
        return com.tencent.kgvmp.e.a.a(strArr, str) || com.tencent.kgvmp.e.a.a(strArr, "ALL");
    }

    private boolean h() {
        return a(this.c.g.a, this.h) && a(this.c.g.b, this.i) && a(this.c.g.c, this.j) && a(this.c.g.d, this.k);
    }

    private boolean i() {
        if (com.tencent.kgvmp.e.a.a(this.c.h.a, this.h) || com.tencent.kgvmp.e.a.a(this.c.h.b, this.i) || com.tencent.kgvmp.e.a.a(this.c.h.c, this.j) || com.tencent.kgvmp.e.a.a(this.c.h.d, this.k)) {
            return true;
        }
        f.a(a, "CCChecker: user is not in func black config. ");
        return false;
    }

    private boolean j() {
        if (!a(this.c.e.a, this.h) || !a(this.c.e.b, this.i) || !a(this.c.e.c, this.j) || !a(this.c.e.d, this.k)) {
            return false;
        }
        f.a(a, "CCChecker: user in report white config. ");
        return true;
    }

    private boolean k() {
        if (com.tencent.kgvmp.e.a.a(this.c.f.a, this.h) || com.tencent.kgvmp.e.a.a(this.c.f.b, this.i) || com.tencent.kgvmp.e.a.a(this.c.f.c, this.j) || com.tencent.kgvmp.e.a.a(this.c.f.d, this.k)) {
            return true;
        }
        f.a(a, "CCChecker: user is not in report black config. ");
        return false;
    }

    private boolean l() {
        if (com.tencent.kgvmp.e.a.a(this.c.j.a, this.h) || com.tencent.kgvmp.e.a.a(this.c.j.b, this.i) || com.tencent.kgvmp.e.a.a(this.c.j.c, this.j) || com.tencent.kgvmp.e.a.a(this.c.j.d, this.k)) {
            return true;
        }
        f.a(a, "CCChecker: user is not in report black config. ");
        return false;
    }

    private boolean m() {
        if (!a(this.c.i.a, this.h) || !a(this.c.i.b, this.i) || !a(this.c.i.c, this.j) || !a(this.c.i.d, this.k)) {
            return false;
        }
        f.a(a, "CCChecker: user in report white config. ");
        return true;
    }

    public e a() {
        f.b(a, "CCChecker: start to load config . ");
        String str = this.b;
        File file = new File(this.b);
        if (!file.exists()) {
            f.a(a, "CCChecker: can not find local cloud file. ");
            return e.CANNT_FIND_LOCAL_CONFIG;
        } else if (!file.canRead()) {
            f.a(a, "CCChecker: can not read local cloud file. ");
            return e.CANNT_READ_LOCAL_CONFIG;
        } else {
            String str2 = null;
            try {
                str2 = d.a(str);
            } catch (Exception e2) {
                e2.printStackTrace();
                f.a(a, "CCChecker: read vmp local config exception.");
            }
            if (str2 == null) {
                f.a(a, "CCChecker: get local config content null.");
                return e.READ_LOCAL_CONFIG_EXCEPTION;
            } else if (str2.isEmpty()) {
                f.a(a, "CCChecker: local config content is empty.");
                return e.GET_LOCAL_CONFIG_EMPTY;
            } else {
                c cVar = new c();
                try {
                    if (!cVar.a(new JSONObject(str2))) {
                        f.a(a, "CCChecker: parse json's value exception.");
                        return e.PARSE_JSON_VALUE_EXCEPTION;
                    }
                    f.b(a, "CCChecker: parse json success.");
                    this.c = cVar;
                    return e.VMP_SUCCESS;
                } catch (JSONException e3) {
                    e3.printStackTrace();
                    f.a(a, "CCChecker: file content parse exception.");
                    return e.PARSE_JSON_CONFIG_EXCEPTION;
                }
            }
        }
    }

    public boolean b() {
        return this.d;
    }

    public boolean c() {
        return this.e;
    }

    public boolean d() {
        return this.f;
    }

    public boolean e() {
        return this.g;
    }

    public a f() {
        this.h = c.b().toLowerCase();
        this.i = c.a().toLowerCase();
        String a2 = b.a();
        if (g.a(a2) || a2.length() < 2) {
            this.j = "z";
            this.k = "z";
        } else {
            int length = a2.length();
            this.j = a2.substring(length - 1);
            this.k = a2.substring(length - 2, length - 1);
        }
        return this;
    }

    public void g() {
        boolean z = true;
        if (this.c == null) {
            f.a(a, "CCChecker: globalConfig is null.");
            return;
        }
        this.d = this.c.b;
        if (this.c.b) {
            this.d = !i();
        } else {
            this.d = h();
        }
        this.e = this.c.a;
        if (this.c.a) {
            this.e = !k();
        } else {
            this.e = j();
        }
        this.f = this.c.c;
        if (this.c.c) {
            this.f = !l();
        } else {
            this.f = m();
        }
        this.g = this.c.d;
        if (this.c.d) {
            if (l()) {
                z = false;
            }
            this.g = z;
            return;
        }
        this.g = m();
    }
}
