package com.tencent.gsdk.utils.c;

import java.net.InetAddress;
import java.util.List;
import org.json.JSONObject;

/* compiled from: SpeedTestStatTask */
public class f implements Runnable {
    public static final f a = new f() {
        public int a() {
            return -1;
        }

        public boolean b() {
            return false;
        }

        public int c() {
            return -1;
        }

        public int d() {
            return -1;
        }
    };
    public static final f b = new f() {
        public int a() {
            return -1;
        }

        public boolean b() {
            return false;
        }

        public int c() {
            return -1;
        }

        public int d() {
            return -1;
        }
    };
    private c c;
    private InetAddress d;
    private int e;
    private int f;
    private e g;

    private f() {
    }

    f(c cVar, InetAddress inetAddress, int i, int i2) {
        this.c = cVar;
        this.d = inetAddress;
        this.e = i;
        this.f = i2;
        this.g = new e(i2);
        this.c.a(this.g);
    }

    public void run() {
        this.c.b(this.d, this.e, this.f);
    }

    public int a() {
        try {
            this.g.a.lock();
            List<Short> list = this.g.c;
            int size = list.size();
            if (size == 0) {
                return -1;
            }
            short shortValue = list.get(size - 1).shortValue();
            this.g.a.unlock();
            return shortValue;
        } finally {
            this.g.a.unlock();
        }
    }

    public boolean b() {
        return a() == this.f;
    }

    public int c() {
        try {
            this.g.a.lock();
            int size = this.g.c.size();
            if (size == 0) {
                return -1;
            }
            int i = (int) (this.g.e / ((long) size));
            this.g.a.unlock();
            return i;
        } finally {
            this.g.a.unlock();
        }
    }

    public int d() {
        try {
            this.g.a.lock();
            int size = this.g.c.size();
            if (size == 0) {
                return -1;
            }
            int size2 = (int) ((((float) (size - this.g.d.size())) / (((float) size) + 0.0f)) * 100.0f);
            this.g.a.unlock();
            return size2;
        } finally {
            this.g.a.unlock();
        }
    }

    public List<Short> e() {
        try {
            this.g.a.lock();
            return this.g.c;
        } finally {
            this.g.a.unlock();
        }
    }

    public List<Short> f() {
        try {
            this.g.a.lock();
            return this.g.d;
        } finally {
            this.g.a.unlock();
        }
    }

    public String toString() {
        JSONObject jSONObject = new JSONObject();
        try {
            this.g.a.lock();
            int a2 = a();
            jSONObject.put("cping", String.valueOf(a2));
            if (-1 == a2 || -1 == a2) {
                jSONObject.put("clostpkg", String.valueOf(a2));
            } else {
                jSONObject.put("clostpkg", b() ? "1" : "0");
            }
            jSONObject.put("avg_ping", String.valueOf(c()));
            jSONObject.put("lostpkgpct", String.valueOf(d()));
        } catch (Exception e2) {
        } finally {
            this.g.a.unlock();
        }
        return jSONObject.toString();
    }

    public static boolean a(f fVar) {
        return (a == fVar || b == fVar) ? false : true;
    }
}
