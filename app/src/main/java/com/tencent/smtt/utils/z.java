package com.tencent.smtt.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class z {
    private b a = null;
    private b b = null;

    class a {
        private String b;
        private long c;
        private long d;

        a(String str, long j, long j2) {
            this.b = str;
            this.c = j;
            this.d = j2;
        }

        /* access modifiers changed from: package-private */
        public long a() {
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public long b() {
            return this.d;
        }
    }

    class b {
        private Map<String, a> b = new HashMap();

        b(File file) {
            this.b.clear();
            a(file);
        }

        private void a(File file) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                for (File a2 : listFiles) {
                    a(a2);
                }
            } else if (file.isFile()) {
                a(file.getName(), file.length(), file.lastModified());
            }
        }

        private void a(String str, long j, long j2) {
            if (str != null && str.length() > 0 && j > 0 && j2 > 0) {
                a aVar = new a(str, j, j2);
                if (!this.b.containsKey(str)) {
                    this.b.put(str, aVar);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public Map<String, a> a() {
            return this.b;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0027  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(com.tencent.smtt.utils.z.b r11, com.tencent.smtt.utils.z.b r12) {
        /*
            r10 = this;
            r2 = 0
            if (r11 == 0) goto L_0x0063
            java.util.Map r0 = r11.a()
            if (r0 == 0) goto L_0x0063
            if (r12 == 0) goto L_0x0063
            java.util.Map r0 = r12.a()
            if (r0 == 0) goto L_0x0063
            java.util.Map r0 = r11.a()
            java.util.Map r3 = r12.a()
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r4 = r0.iterator()
        L_0x0021:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x0061
            java.lang.Object r0 = r4.next()
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            java.lang.Object r1 = r0.getKey()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r0 = r0.getValue()
            com.tencent.smtt.utils.z$a r0 = (com.tencent.smtt.utils.z.a) r0
            boolean r5 = r3.containsKey(r1)
            if (r5 == 0) goto L_0x005f
            java.lang.Object r1 = r3.get(r1)
            com.tencent.smtt.utils.z$a r1 = (com.tencent.smtt.utils.z.a) r1
            long r6 = r0.a()
            long r8 = r1.a()
            int r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r5 != 0) goto L_0x005d
            long r6 = r0.b()
            long r0 = r1.b()
            int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0021
        L_0x005d:
            r0 = r2
        L_0x005e:
            return r0
        L_0x005f:
            r0 = r2
            goto L_0x005e
        L_0x0061:
            r0 = 1
            goto L_0x005e
        L_0x0063:
            r0 = r2
            goto L_0x005e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.z.a(com.tencent.smtt.utils.z$b, com.tencent.smtt.utils.z$b):boolean");
    }

    public void a(File file) {
        this.a = new b(file);
    }

    public boolean a() {
        return this.b != null && this.a != null && this.b.a().size() == this.a.a().size() && a(this.a, this.b);
    }

    public void b(File file) {
        this.b = new b(file);
    }
}
