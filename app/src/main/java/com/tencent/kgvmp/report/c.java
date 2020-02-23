package com.tencent.kgvmp.report;

import com.tencent.kgvmp.e.f;
import com.tencent.midas.oversea.api.UnityPayHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class c {
    private static final String b = com.tencent.kgvmp.a.a.a;
    int a = 0;
    private ArrayList<a> c = new ArrayList<>();
    private a d = null;

    private class a {
        private int b;
        /* access modifiers changed from: private */
        public HashMap<String, String> c;
        private int d = 0;

        public a(int i) {
            this.b = i;
            this.c = new HashMap<>();
        }

        public int a() {
            return this.d;
        }

        public void a(String str, float f) {
            this.d++;
            d.s.put(com.tencent.kgvmp.a.c.CPU_RATE.getKeyStr(), f.j());
            d.s.put(com.tencent.kgvmp.a.c.USED_MEM.getKeyStr(), f.k());
            d.s.put(com.tencent.kgvmp.a.c.TIME_RELATIVE.getKeyStr(), f.i());
            d.s.put(com.tencent.kgvmp.a.c.FPS.getKeyStr(), str);
            d.s.put(com.tencent.kgvmp.a.c.FPS_AVG.getKeyStr(), String.format(Locale.CHINA, "%.2f", new Object[]{Float.valueOf(f)}));
            String a2 = d.a();
            String b2 = d.b();
            if (a2 != null) {
                d.s.put(com.tencent.kgvmp.a.c.FRAME_MISS_AVG.getKeyStr(), a2);
            }
            if (b2 != null) {
                d.s.put(com.tencent.kgvmp.a.c.NET_LATENCY_AVG.getKeyStr(), b2);
            }
            for (String next : d.s.keySet()) {
                String str2 = this.c.get(next);
                if (str2 == null) {
                    String str3 = "";
                    for (int i = 0; i < this.d - 1; i++) {
                        str3 = str3 + "|";
                    }
                    this.c.put(next, str3 + d.s.get(next));
                } else {
                    this.c.put(next, str2 + "|" + d.s.get(next));
                }
                d.a(next);
            }
        }
    }

    private float a(float[] fArr) {
        float f = 0.0f;
        for (float f2 : fArr) {
            f += f2;
        }
        return f / ((float) fArr.length);
    }

    private void a() {
        Iterator<a> it = this.c.iterator();
        while (it.hasNext()) {
            f.g(it.next().c);
        }
        this.c.clear();
        this.a = 0;
    }

    private void a(String str) {
        long currentTimeMillis = System.currentTimeMillis();
        if (d.w.compareTo(UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR) == 0) {
            d.a(currentTimeMillis);
        } else if (d.w.compareTo(str) != 0) {
            f.a(str, d.w);
            d.a(currentTimeMillis);
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0030 A[Catch:{ Exception -> 0x0042 }] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x004e A[SYNTHETIC, Splitter:B:21:0x004e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(int r3, java.lang.String r4) {
        /*
            r2 = this;
            switch(r3) {
                case 4: goto L_0x0007;
                default: goto L_0x0003;
            }
        L_0x0003:
            com.tencent.kgvmp.report.d.a(r3, r4)
        L_0x0006:
            return
        L_0x0007:
            r0 = -1
            int r1 = r4.hashCode()     // Catch:{ Exception -> 0x0042 }
            switch(r1) {
                case 52: goto L_0x001c;
                case 53: goto L_0x0026;
                default: goto L_0x000f;
            }
        L_0x000f:
            switch(r0) {
                case 0: goto L_0x0030;
                case 1: goto L_0x004e;
                default: goto L_0x0012;
            }
        L_0x0012:
            r2.a((java.lang.String) r4)
            com.tencent.kgvmp.report.d.g(r4)
            com.tencent.kgvmp.report.d.a(r3, r4)
            goto L_0x0006
        L_0x001c:
            java.lang.String r1 = "4"
            boolean r1 = r4.equals(r1)     // Catch:{ Exception -> 0x0042 }
            if (r1 == 0) goto L_0x000f
            r0 = 0
            goto L_0x000f
        L_0x0026:
            java.lang.String r1 = "5"
            boolean r1 = r4.equals(r1)     // Catch:{ Exception -> 0x0042 }
            if (r1 == 0) goto L_0x000f
            r0 = 1
            goto L_0x000f
        L_0x0030:
            java.lang.String r0 = com.tencent.kgvmp.report.d.w     // Catch:{ Exception -> 0x0042 }
            com.tencent.kgvmp.a.d r1 = com.tencent.kgvmp.a.d.PLAYING     // Catch:{ Exception -> 0x0042 }
            java.lang.String r1 = r1.getSceneID()     // Catch:{ Exception -> 0x0042 }
            int r0 = r0.compareTo(r1)     // Catch:{ Exception -> 0x0042 }
            if (r0 != 0) goto L_0x0012
            r2.a()     // Catch:{ Exception -> 0x0042 }
            goto L_0x0012
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
            java.lang.String r0 = b
            java.lang.String r1 = "DataDealer:dealData: exception."
            com.tencent.kgvmp.e.f.a(r0, r1)
            goto L_0x0012
        L_0x004e:
            r2.a()     // Catch:{ Exception -> 0x0042 }
            goto L_0x0012
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.kgvmp.report.c.a(int, java.lang.String):void");
    }

    public void a(int i, float[] fArr) {
        switch (i) {
            case 5:
                try {
                    if (this.a >= com.tencent.kgvmp.a.a.l) {
                        String str = d.w;
                        char c2 = 65535;
                        switch (str.hashCode()) {
                            case 55:
                                if (str.equals("7")) {
                                    c2 = 0;
                                    break;
                                }
                                break;
                        }
                        switch (c2) {
                            case 0:
                                return;
                            default:
                                a();
                                break;
                        }
                    }
                    this.a++;
                    if (this.d == null) {
                        this.d = new a(this.a);
                    }
                    if (this.d.a() < com.tencent.kgvmp.a.a.m) {
                        this.d.a(com.tencent.kgvmp.e.a.a(fArr, "_"), a(fArr));
                    }
                    if (this.d.a() >= com.tencent.kgvmp.a.a.m) {
                        this.c.add(this.d);
                        this.d = new a(this.a);
                        return;
                    }
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    f.a(b, "DataDealer:dealData: exception2.");
                    return;
                }
            default:
                return;
        }
    }
}
