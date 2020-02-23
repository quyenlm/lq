package com.tencent.tp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class o {
    private static boolean a = false;
    private static boolean b = false;

    public static class a {
        public String a;
        public int b;
        public String c;
        public int d;
        public int e;
        public int f;
        public boolean g;

        public String toString() {
            return String.format("localAddr: '%s', localPort: '%d', remoteAddr: '%s', remotePort: '%d', skState: '%d', uid: '%d'", new Object[]{this.a, Integer.valueOf(this.b), this.c, Integer.valueOf(this.d), Integer.valueOf(this.e), Integer.valueOf(this.f)});
        }
    }

    private enum b {
        ESTABLISHED,
        LISTENING,
        OTHERS,
        NOT_OPENED
    }

    private static b a(ArrayList arrayList, int i) {
        Iterator it = arrayList.iterator();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar.b == i) {
                if (aVar.e == 1) {
                    z2 = true;
                } else if (aVar.e == 10) {
                    z3 = true;
                } else {
                    z = true;
                }
            }
        }
        return z2 ? b.ESTABLISHED : z3 ? b.LISTENING : z ? b.OTHERS : b.NOT_OPENED;
    }

    private static ArrayList a(BufferedReader bufferedReader) throws IOException {
        Pattern compile = Pattern.compile("^\\s*([\\d]+):\\s([\\dA-F]{8}):([\\dA-F]{4})\\s([\\dA-F]{8}):([\\dA-F]{4})\\s([\\dA-F]{2})\\s([\\dA-F]{8,}+):([\\dA-F]{8,}+)\\s([\\d]{2}):([\\dA-F]{8,}+)\\s([\\dA-F]{8,}+)\\s+([\\d]+)\\s");
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            Matcher matcher = compile.matcher(readLine);
            if (matcher.find()) {
                a aVar = new a();
                aVar.a = matcher.group(2);
                aVar.b = Integer.parseInt(matcher.group(3), 16);
                aVar.c = matcher.group(4);
                aVar.d = Integer.parseInt(matcher.group(5), 16);
                String group = matcher.group(6);
                String group2 = matcher.group(12);
                aVar.e = Integer.parseInt(group, 16);
                aVar.f = Integer.parseInt(group2, 10);
                aVar.g = false;
                if (a(aVar)) {
                    arrayList.add(aVar);
                }
            }
        }
    }

    public static boolean a() {
        return a;
    }

    public static boolean a(int i, int i2) {
        e();
        ArrayList d = d();
        d.addAll(c());
        if (d.isEmpty()) {
            return false;
        }
        if (i > 0) {
            a = a(d, i) == b.ESTABLISHED;
        } else if (i2 > 0) {
            a = a(d, i2) == b.ESTABLISHED;
        } else {
            a = false;
        }
        b = false;
        Iterator it = d.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (!(aVar.b == i || aVar.b == i2 || aVar.e != 1)) {
                b = true;
            }
        }
        return true;
    }

    private static boolean a(a aVar) {
        return aVar.f == 2000;
    }

    private static ArrayList b(BufferedReader bufferedReader) throws IOException {
        Pattern compile = Pattern.compile("^\\s*([\\d]+):\\s([\\dA-F]{32}):([\\dA-F]{4})\\s([\\dA-F]{32}):([\\dA-F]{4})\\s([\\dA-F]{2})\\s([\\dA-F]{8,}+):([\\dA-F]{8,}+)\\s([\\d]{2}):([\\dA-F]{8,}+)\\s([\\dA-F]{8,}+)\\s+([\\d]+)\\s");
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            Matcher matcher = compile.matcher(readLine);
            if (matcher.find()) {
                a aVar = new a();
                aVar.a = matcher.group(2);
                aVar.b = Integer.parseInt(matcher.group(3), 16);
                aVar.c = matcher.group(4);
                aVar.d = Integer.parseInt(matcher.group(5), 16);
                String group = matcher.group(6);
                String group2 = matcher.group(12);
                aVar.e = Integer.parseInt(group, 16);
                aVar.f = Integer.parseInt(group2, 10);
                aVar.g = true;
                if (a(aVar)) {
                    arrayList.add(aVar);
                }
            }
        }
    }

    public static boolean b() {
        return b;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x002d A[SYNTHETIC, Splitter:B:20:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0036 A[SYNTHETIC, Splitter:B:25:0x0036] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList c() {
        /*
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.lang.String r3 = "/proc/net/tcp6"
            r0.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            r1.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.util.ArrayList r0 = b(r1)     // Catch:{ FileNotFoundException -> 0x0046, IOException -> 0x0043 }
            if (r1 == 0) goto L_0x0016
            r1.close()     // Catch:{ IOException -> 0x003a }
        L_0x0016:
            return r0
        L_0x0017:
            r0 = move-exception
            r1 = r2
        L_0x0019:
            r0.printStackTrace()     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ IOException -> 0x003c }
        L_0x0021:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x0016
        L_0x0027:
            r0 = move-exception
        L_0x0028:
            r0.printStackTrace()     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0021
            r2.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0021
        L_0x0031:
            r0 = move-exception
            goto L_0x0021
        L_0x0033:
            r0 = move-exception
        L_0x0034:
            if (r2 == 0) goto L_0x0039
            r2.close()     // Catch:{ IOException -> 0x003e }
        L_0x0039:
            throw r0
        L_0x003a:
            r1 = move-exception
            goto L_0x0016
        L_0x003c:
            r0 = move-exception
            goto L_0x0021
        L_0x003e:
            r1 = move-exception
            goto L_0x0039
        L_0x0040:
            r0 = move-exception
            r2 = r1
            goto L_0x0034
        L_0x0043:
            r0 = move-exception
            r2 = r1
            goto L_0x0028
        L_0x0046:
            r0 = move-exception
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tp.o.c():java.util.ArrayList");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x002d A[SYNTHETIC, Splitter:B:20:0x002d] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0036 A[SYNTHETIC, Splitter:B:25:0x0036] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList d() {
        /*
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.io.FileReader r0 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.lang.String r3 = "/proc/net/tcp"
            r0.<init>(r3)     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            r1.<init>(r0)     // Catch:{ FileNotFoundException -> 0x0017, IOException -> 0x0027 }
            java.util.ArrayList r0 = a((java.io.BufferedReader) r1)     // Catch:{ FileNotFoundException -> 0x0046, IOException -> 0x0043 }
            if (r1 == 0) goto L_0x0016
            r1.close()     // Catch:{ IOException -> 0x003a }
        L_0x0016:
            return r0
        L_0x0017:
            r0 = move-exception
            r1 = r2
        L_0x0019:
            r0.printStackTrace()     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x0021
            r1.close()     // Catch:{ IOException -> 0x003c }
        L_0x0021:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            goto L_0x0016
        L_0x0027:
            r0 = move-exception
        L_0x0028:
            r0.printStackTrace()     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x0021
            r2.close()     // Catch:{ IOException -> 0x0031 }
            goto L_0x0021
        L_0x0031:
            r0 = move-exception
            goto L_0x0021
        L_0x0033:
            r0 = move-exception
        L_0x0034:
            if (r2 == 0) goto L_0x0039
            r2.close()     // Catch:{ IOException -> 0x003e }
        L_0x0039:
            throw r0
        L_0x003a:
            r1 = move-exception
            goto L_0x0016
        L_0x003c:
            r0 = move-exception
            goto L_0x0021
        L_0x003e:
            r1 = move-exception
            goto L_0x0039
        L_0x0040:
            r0 = move-exception
            r2 = r1
            goto L_0x0034
        L_0x0043:
            r0 = move-exception
            r2 = r1
            goto L_0x0028
        L_0x0046:
            r0 = move-exception
            goto L_0x0019
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.tp.o.d():java.util.ArrayList");
    }

    private static void e() {
        b = false;
        a = false;
    }
}
