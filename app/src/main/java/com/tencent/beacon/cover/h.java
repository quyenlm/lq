package com.tencent.beacon.cover;

import android.content.Context;
import com.tencent.component.debug.TraceFormat;
import com.tencent.qqgamemi.util.TimeUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: VersionCheck */
public final class h implements Runnable {
    public static String a = null;
    public static String b = null;
    private static h e = null;
    private Context c;
    private List<a> d = new ArrayList();

    public static h a(Context context) {
        if (e == null) {
            e = new h(context);
        }
        return e;
    }

    private h(Context context) {
        if (context == null) {
            g.a(TraceFormat.STR_WARN, "context is null!", new Object[0]);
        } else {
            this.c = context.getApplicationContext();
        }
    }

    public final void run() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (this.c.getFilesDir() != null && d.a(this.c).a("check")) {
            File file = new File(this.c.getFilesDir(), "beacon/comp");
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(this.c.getFilesDir(), "beacon/odex");
            if (!file2.exists()) {
                file2.mkdirs();
            }
            String c2 = g.c(this.c);
            if (!g.b(this.c, "APP_VER", "").equals(c2)) {
                c();
                g.a(this.c, "APP_VER", c2);
            }
            List<a> a2 = g.a(g.b(this.c, "COMP_INFO", ""));
            if (a2.size() == 0) {
                g.a(TraceFormat.STR_WARN, "comp config has error!", new Object[0]);
            }
            List<String> b2 = b();
            if (b2 == null || b2.size() == 0) {
                g.a(TraceFormat.STR_WARN, "local comps has error!", new Object[0]);
                z = true;
            } else {
                z = true;
                for (a next : a2) {
                    if (!(next == null || next.c == g.c)) {
                        Iterator<String> it = b2.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                                break;
                            }
                            String[] split = it.next().split(",");
                            if (split.length == 3 && next.d.equals(split[0]) && String.valueOf(next.f).equals(split[1]) && next.g.equals(split[2])) {
                                this.d.add(next);
                                z2 = true;
                                break;
                            }
                        }
                        if (!z2) {
                            g.a(TraceFormat.STR_WARN, "the config is not match local comp!", new Object[0]);
                            z3 = false;
                        } else {
                            z3 = z;
                        }
                        z = z3;
                    }
                }
            }
            if (z) {
                g.a(TraceFormat.STR_WARN, "start thread to load component.", new Object[0]);
                new Thread(b.a(this.c, this.d)).start();
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (z && !a(currentTimeMillis)) {
                z4 = false;
            }
            if (z4) {
                new Thread(new i(this.c, this.d)).start();
                g.a(this.c, "LAST_UPDATE_TIME", String.valueOf(currentTimeMillis));
            }
            d.a(this.c).b("check");
        }
    }

    private boolean a(long j) {
        long j2 = 0;
        try {
            j2 = Long.valueOf(g.b(this.c, "LAST_UPDATE_TIME", "0")).longValue();
        } catch (Exception e2) {
        }
        return j - j2 > TimeUtils.MILLIS_IN_DAY;
    }

    private List<String> b() {
        File[] listFiles;
        File file = new File(this.c.getFilesDir(), "beacon/comp");
        ArrayList arrayList = null;
        if (file.exists() && file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
            arrayList = new ArrayList();
            for (File file2 : listFiles) {
                arrayList.add(file2.getName() + "," + file2.length() + "," + g.a(file2));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: package-private */
    public final List<a> a() {
        return this.d;
    }

    private void a(a aVar) {
        int i;
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= this.d.size()) {
                i = -1;
                break;
            } else if (aVar.a == this.d.get(i).a) {
                break;
            } else {
                i2 = i + 1;
            }
        }
        if (i >= 0) {
            this.d.remove(i);
        }
        this.d.add(aVar);
    }

    private static long a(String str) {
        try {
            return Long.parseLong(str.replaceAll("\\.", ""));
        } catch (Exception e2) {
            return 0;
        }
    }

    private List<a> a(List<a> list) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        for (a next : list) {
            boolean z2 = false;
            boolean z3 = false;
            for (a next2 : this.d) {
                if (next.a != next2.a) {
                    z = z2;
                } else if (a(next.b) > a(next2.b)) {
                    z = true;
                    z3 = true;
                } else {
                    z = true;
                }
                z2 = z;
            }
            if (z3 || !z2) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    private String c() {
        a aVar;
        List<a> a2 = a(g.a(g.b(this.c, "beaconcomp" + File.separator + "comp_list")));
        byte[] bArr = new byte[2048];
        String str = this.c.getFilesDir().getAbsolutePath() + File.separator + "beacon/comp";
        a aVar2 = null;
        a aVar3 = null;
        for (a next : a2) {
            String str2 = "beaconcomp" + File.separator + next.d;
            if (next.c == g.b) {
                next.d += ".jar";
                if (g.a(this.c, str2, str, next.d, (long) next.f, bArr)) {
                    a(next);
                }
            }
            if (next.c == g.c) {
                if (g.b().equals(next.h)) {
                    aVar = next;
                } else {
                    aVar = aVar3;
                }
                if (!"armeabi".equals(next.h)) {
                    next = aVar2;
                }
            } else {
                next = aVar2;
                aVar = aVar3;
            }
            aVar2 = next;
            aVar3 = aVar;
        }
        if (aVar3 != null) {
            aVar2 = aVar3;
        }
        if (aVar2 != null) {
            String str3 = "beaconcomp" + File.separator + aVar2.d;
            aVar2.d = aVar2.d.substring(0, aVar2.d.lastIndexOf("."));
            if (g.a(this.c, str3, str, aVar2.d, (long) aVar2.f, bArr)) {
                a(aVar2);
            }
        }
        if (a2.size() <= 0) {
            return "";
        }
        String a3 = g.a(this.d);
        g.a(this.c, "COMP_INFO", a3);
        return a3;
    }
}
