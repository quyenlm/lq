package com.tencent.mna.base.d;

import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

/* compiled from: LossRateCounter */
public class b {
    private static Vector<c> a = null;
    private static Vector<Long> b = null;
    private static Vector<c> c = null;
    private static C0031b d = null;
    /* access modifiers changed from: private */
    public static int e = 0;
    /* access modifiers changed from: private */
    public static int f;
    /* access modifiers changed from: private */
    public static String g;
    /* access modifiers changed from: private */
    public static int h;
    private static int i;
    private static int j;
    private static int k;
    private static int l;
    private static boolean m = false;
    private static final ReentrantLock n = new ReentrantLock();

    public static boolean a(String str, int i2, String str2, int i3, int i4, int i5, int i6, int i7, float f2, int i8, int i9, int i10, int i11, int i12) {
        String str3;
        boolean z = false;
        if (!n.tryLock()) {
            h.a("startUdpLoop get lock fail");
        } else {
            try {
                h.a("startUdpLoop get lock success");
                z = b(str, i2, str2, i3, i4, i5, i6, i7, f2, i8, i9, i10, i11, i12);
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                n.unlock();
                str3 = "startUdpLoop release lock";
                h.a(str3);
            }
        }
        return z;
    }

    private static boolean b(String str, int i2, String str2, int i3, int i4, final int i5, final int i6, int i7, float f2, int i8, final int i9, int i10, int i11, int i12) {
        h.a("startUdpLoopInner");
        a = new Vector<>();
        b = new Vector<>();
        c = new Vector<>();
        d = null;
        g = str2;
        h = i3;
        e = i4;
        f = e.b(500);
        i = i8;
        j = i10;
        k = i11;
        l = i12;
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("pushTimes", i8);
            jSONObject.put("pushLen", i7);
            jSONObject.put("pushInterval", (double) f2);
            jSONObject.put("pushIsDouble", 0);
        } catch (Exception e2) {
        }
        h.a("双发包配置config: " + jSONObject.toString());
        m = e.a(f, f.k(str), i2, jSONObject.toString());
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        h.a("启动收发包");
        newFixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    e.a(b.f, f.k(b.g), b.h, b.e, i5, i6, i9);
                } catch (Throwable th) {
                }
            }
        });
        newFixedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    e.a(b.f, i9 + 600);
                } catch (Throwable th) {
                }
            }
        });
        try {
            if (newFixedThreadPool.awaitTermination((long) (i9 + 1000), TimeUnit.MILLISECONDS)) {
                return true;
            }
            try {
                newFixedThreadPool.shutdownNow();
                return true;
            } catch (Exception e3) {
                return true;
            }
        } catch (InterruptedException e4) {
            try {
                newFixedThreadPool.shutdownNow();
                return true;
            } catch (Exception e5) {
                return true;
            }
        }
    }

    public static void a(int i2, int i3, long j2) {
        if (i2 == f) {
            synchronized (b) {
                b.add(Long.valueOf(j2));
            }
        }
    }

    public static void a(int i2, int i3, int i4, long j2) {
        if (i2 == f) {
            synchronized (a) {
                a.add(new c(k.a(com.tencent.mna.b.g()), i3, i4, j2));
            }
        }
    }

    public static void a(int i2, int i3) {
        if (m) {
            synchronized (c) {
                c.add(new c(0, i2, 0, (long) i3));
            }
        }
    }

    /* compiled from: LossRateCounter */
    public static class a {
        public long a = -1;
        public long b = -1;
        public long c = -1;
        public int d = -1;
        public String e = "";
        public String f = "";

        public a(long j, long j2, long j3) {
            this.a = j;
            this.b = j2;
            this.c = j3;
            this.d = -1;
        }
    }

    public static int a() {
        if (b != null) {
            return 0 + b.size();
        }
        return 0;
    }

    public static int b() {
        int i2 = 0;
        if (c != null) {
            i2 = 0 + c.size();
        }
        if (a != null) {
            return i2 + a.size();
        }
        return i2;
    }

    public static a c() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        a aVar = new a(-1, -1, -1);
        if (!n.tryLock()) {
            h.a("getDgnData get lock fail");
        } else {
            try {
                if (!(b == null || b.size() <= 0 || a == null)) {
                    int size = b.size();
                    Vector vector = new Vector();
                    Vector vector2 = new Vector();
                    for (int i7 = 0; i7 < size; i7++) {
                        vector2.add(false);
                    }
                    int i8 = 0;
                    Vector vector3 = new Vector();
                    int size2 = a.size();
                    int i9 = -1;
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    int i10 = 0;
                    while (i10 < size2) {
                        c cVar = a.get(i10);
                        if (cVar.b >= size) {
                            i5 = i9;
                            i6 = i8;
                        } else {
                            int longValue = (int) (cVar.c - b.get(cVar.b).longValue());
                            if (longValue > 500) {
                                i4 = i9 + 500;
                                sb.append("500,");
                            } else {
                                i4 = i9 + longValue;
                                sb.append(longValue).append(',');
                            }
                            vector3.add(new c(cVar.a, cVar.b, longValue, (long) cVar.d));
                            vector2.set(cVar.b, true);
                            if (cVar.d == 1) {
                                i6 = i8 + 1;
                                i5 = i4;
                            } else {
                                i5 = i4;
                                i6 = i8;
                            }
                        }
                        i10++;
                        i9 = i5;
                        i8 = i6;
                    }
                    for (int i11 = 0; i11 < size; i11++) {
                        if (!((Boolean) vector2.get(i11)).booleanValue()) {
                            vector.add(Integer.valueOf(i11));
                        }
                    }
                    if (size > 0) {
                        double size3 = ((double) vector.size()) / ((double) size);
                    }
                    int i12 = i;
                    int i13 = 0;
                    Vector vector4 = new Vector();
                    Vector vector5 = new Vector();
                    for (int i14 = 0; i14 < i12; i14++) {
                        vector5.add(false);
                    }
                    Iterator<c> it = c.iterator();
                    while (it.hasNext()) {
                        c next = it.next();
                        if (next.d != 6 || ((Boolean) vector5.get(next.b)).booleanValue()) {
                            i3 = i13;
                        } else {
                            i3 = i13 + 1;
                        }
                        vector5.set(next.b, true);
                        i13 = i3;
                    }
                    for (int i15 = 0; i15 < i12; i15++) {
                        if (!((Boolean) vector5.get(i15)).booleanValue()) {
                            vector4.add(Integer.valueOf(i15));
                            sb2.append(i15).append(',');
                        }
                    }
                    int i16 = 0;
                    int size4 = vector3.size();
                    int i17 = 1;
                    while (i17 < size4) {
                        c cVar2 = (c) vector3.get(i17 - 1);
                        c cVar3 = (c) vector3.get(i17);
                        if (cVar3.b != cVar2.b + 1 || cVar3.c <= ((long) k) || cVar2.c >= ((long) j) || cVar3.c - cVar2.c <= ((long) l)) {
                            i2 = i16;
                        } else {
                            i2 = i16 + 1;
                        }
                        i17++;
                        i16 = i2;
                    }
                    aVar.a = (long) vector.size();
                    aVar.b = (long) i16;
                    aVar.c = (long) vector4.size();
                    if (!m) {
                        h.a("neg fail, set push 0, orig:" + aVar.c);
                        aVar.c = 0;
                    }
                    if (a.size() > 0) {
                        aVar.d = size > 0 ? (int) ((((long) i9) + (500 * aVar.a)) / ((long) size)) : -1;
                    } else {
                        aVar.d = 501;
                    }
                    aVar.e = sb.append(";").toString();
                    aVar.f = sb2.append(";").toString();
                }
            } catch (Exception e2) {
                aVar = new a(-1, -1, -1);
            } finally {
                n.unlock();
            }
        }
        return aVar;
    }

    /* compiled from: LossRateCounter */
    public static class c {
        public int a;
        public int b;
        public long c;
        public int d;

        public c(int i, int i2, int i3, long j) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = j;
        }
    }

    /* renamed from: com.tencent.mna.base.d.b$b  reason: collision with other inner class name */
    /* compiled from: LossRateCounter */
    public static class C0031b {
        public int a;
        public int b;
        public int c;
        public double d;
        public Vector<Integer> e;
        public Vector<c> f;
        public int g;
        public int h;
        public int i;
        public Vector<Integer> j;

        public String toString() {
            int i2;
            int i3;
            StringBuilder sb = new StringBuilder();
            if (this.e != null) {
                Iterator<Integer> it = this.e.iterator();
                while (it.hasNext()) {
                    sb.append(it.next()).append(",");
                }
            }
            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
            sb2.append("[");
            sb3.append("[");
            if (this.f != null) {
                Iterator<c> it2 = this.f.iterator();
                while (it2.hasNext()) {
                    c next = it2.next();
                    sb2.append(next.a).append("-").append(next.c).append("-").append(next.d).append(",");
                    if (next.d == 1) {
                        sb3.append(next.b).append(",");
                    }
                }
            }
            sb2.append("]");
            sb3.append("]");
            StringBuilder sb4 = new StringBuilder();
            int i4 = 0;
            StringBuilder sb5 = new StringBuilder();
            sb4.append("[");
            sb5.append("[");
            if (this.f != null) {
                int size = this.f.size();
                int i5 = 1;
                while (i5 < size) {
                    c cVar = this.f.get(i5 - 1);
                    c cVar2 = this.f.get(i5);
                    if (cVar2.b != cVar.b + 1 || cVar2.c <= 200 || cVar.c >= 200 || cVar2.c - cVar.c <= 100) {
                        i3 = i4;
                    } else {
                        i3 = i4 + 1;
                        sb4.append(cVar2.b).append(",");
                    }
                    i5++;
                    i4 = i3;
                }
                int i6 = 0;
                i2 = 0;
                while (i6 < size) {
                    c cVar3 = this.f.get(i6);
                    if (cVar3.c > 200) {
                        i2++;
                        sb5.append(cVar3.b).append(",");
                    }
                    i6++;
                    i2 = i2;
                }
            } else {
                i2 = 0;
            }
            sb4.append("]");
            sb5.append("]");
            StringBuilder sb6 = new StringBuilder();
            sb6.append("[");
            Iterator<Integer> it3 = this.j.iterator();
            while (it3.hasNext()) {
                sb6.append(it3.next());
                sb6.append(",");
            }
            sb6.append("]");
            return "push包发包数:" + this.g + ",丢包数:" + this.h + ",冗余包数:" + this.i + ",丢包率:" + String.format(com.tencent.mna.a.a.a, "%.3f", new Object[]{Double.valueOf(((double) this.h) / ((double) this.g))}) + "\n丢包序号:" + sb6.toString() + "\n\n游戏包发包数:" + this.a + ",丢包数:" + this.b + ",冗余包数:" + this.c + ",卡顿包数:" + i4 + ",高延迟包数:" + i2 + ",丢包率:" + String.format(com.tencent.mna.a.a.a, "%.3f", new Object[]{Double.valueOf(this.d)}) + ",冗余包率:" + String.format(com.tencent.mna.a.a.a, "%.3f", new Object[]{Double.valueOf(((double) this.c) / ((double) this.a))}) + ",卡顿包率:" + String.format(com.tencent.mna.a.a.a, "%.3f", new Object[]{Double.valueOf(((double) i4) / ((double) this.a))}) + ",高延迟包率:" + String.format(com.tencent.mna.a.a.a, "%.3f", new Object[]{Double.valueOf(((double) i2) / ((double) this.a))}) + "\n丢包序号:" + sb.toString() + "\n冗余包序号:" + sb3.toString() + "\n卡顿包序号:" + sb4.toString() + "\n高延迟包序号:" + sb5.toString() + "\ndoublevalue:" + sb2.toString() + "\n";
        }
    }
}
