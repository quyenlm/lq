package com.tencent.mna.base.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: PingUtil */
public final class n {
    private static final Pattern a = Pattern.compile("(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}");
    private static final Pattern b = Pattern.compile("(?<=from ).*(?=: icmp_seq=1 ttl=)");

    /* JADX WARNING: Removed duplicated region for block: B:109:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0107 A[SYNTHETIC, Splitter:B:61:0x0107] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x010c A[SYNTHETIC, Splitter:B:64:0x010c] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0111 A[SYNTHETIC, Splitter:B:67:0x0111] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x011f A[SYNTHETIC, Splitter:B:73:0x011f] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0124 A[SYNTHETIC, Splitter:B:76:0x0124] */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0129 A[SYNTHETIC, Splitter:B:79:0x0129] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int a(java.lang.String r8, int r9) {
        /*
            r0 = -2
            r3 = 0
            if (r8 == 0) goto L_0x0012
            int r1 = r8.length()
            if (r1 <= 0) goto L_0x0012
            java.lang.String r1 = "0.0.0.0"
            boolean r1 = r8.equals(r1)
            if (r1 == 0) goto L_0x0014
        L_0x0012:
            r0 = -3
        L_0x0013:
            return r0
        L_0x0014:
            r1 = 1000(0x3e8, float:1.401E-42)
            java.lang.Runtime r2 = java.lang.Runtime.getRuntime()     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            r4.<init>()     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.String r5 = "/system/bin/ping -c "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.StringBuilder r4 = r4.append(r9)     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.String r5 = " -W 1 -i 0.2 "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.StringBuilder r4 = r4.append(r8)     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.lang.Process r5 = r2.exec(r4)     // Catch:{ Exception -> 0x0101, all -> 0x0119 }
            java.io.InputStream r4 = r5.getInputStream()     // Catch:{ Exception -> 0x014e, all -> 0x0145 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Exception -> 0x0152, all -> 0x0149 }
            java.io.InputStreamReader r6 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x0152, all -> 0x0149 }
            java.lang.String r7 = "UTF-8"
            r6.<init>(r4, r7)     // Catch:{ Exception -> 0x0152, all -> 0x0149 }
            r2.<init>(r6)     // Catch:{ Exception -> 0x0152, all -> 0x0149 }
        L_0x004b:
            java.lang.String r6 = r2.readLine()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            if (r6 == 0) goto L_0x0053
            r3 = r6
            goto L_0x004b
        L_0x0053:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            r6.<init>()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r7 = "ping, pingResult:"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.StringBuilder r6 = r6.append(r3)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            com.tencent.mna.base.utils.h.a((java.lang.String) r6)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            if (r3 == 0) goto L_0x00a2
            r1 = 61
            int r1 = r3.indexOf(r1)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            int r1 = r1 + 1
            java.lang.String r6 = "ms"
            int r6 = r3.indexOf(r6)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r1 = r3.substring(r1, r6)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            r3 = 47
            int r3 = r1.indexOf(r3)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            int r3 = r3 + 1
            r6 = 47
            int r6 = r1.indexOf(r6, r3)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r1 = r1.substring(r3, r6)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r1 = r1.trim()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.Double r1 = java.lang.Double.valueOf(r1)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            double r6 = r1.doubleValue()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            int r1 = (int) r6     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            int r1 = r1 + 1
        L_0x00a2:
            int r3 = r5.waitFor()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            r6 = 1
            if (r3 != r6) goto L_0x00c2
            java.lang.String r1 = "ping, ping IP failed."
            com.tencent.mna.base.utils.h.a((java.lang.String) r1)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            if (r5 == 0) goto L_0x00b3
            r5.destroy()     // Catch:{ Exception -> 0x012d }
        L_0x00b3:
            if (r4 == 0) goto L_0x00b8
            r4.close()     // Catch:{ Exception -> 0x012f }
        L_0x00b8:
            if (r2 == 0) goto L_0x0013
            r2.close()     // Catch:{ Exception -> 0x00bf }
            goto L_0x0013
        L_0x00bf:
            r1 = move-exception
            goto L_0x0013
        L_0x00c2:
            if (r3 == 0) goto L_0x00ef
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            r1.<init>()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r6 = "ping, ping IP not support, exitValue:"
            java.lang.StringBuilder r1 = r1.append(r6)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            java.lang.String r1 = r1.toString()     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            com.tencent.mna.base.utils.h.a((java.lang.String) r1)     // Catch:{ Exception -> 0x0155, all -> 0x014c }
            r0 = -1
            if (r5 == 0) goto L_0x00e0
            r5.destroy()     // Catch:{ Exception -> 0x0131 }
        L_0x00e0:
            if (r4 == 0) goto L_0x00e5
            r4.close()     // Catch:{ Exception -> 0x0133 }
        L_0x00e5:
            if (r2 == 0) goto L_0x0013
            r2.close()     // Catch:{ Exception -> 0x00ec }
            goto L_0x0013
        L_0x00ec:
            r1 = move-exception
            goto L_0x0013
        L_0x00ef:
            if (r5 == 0) goto L_0x00f4
            r5.destroy()     // Catch:{ Exception -> 0x0135 }
        L_0x00f4:
            if (r4 == 0) goto L_0x00f9
            r4.close()     // Catch:{ Exception -> 0x0137 }
        L_0x00f9:
            if (r2 == 0) goto L_0x00fe
            r2.close()     // Catch:{ Exception -> 0x0139 }
        L_0x00fe:
            r0 = r1
            goto L_0x0013
        L_0x0101:
            r1 = move-exception
            r1 = r3
            r4 = r3
            r5 = r3
        L_0x0105:
            if (r5 == 0) goto L_0x010a
            r5.destroy()     // Catch:{ Exception -> 0x013b }
        L_0x010a:
            if (r4 == 0) goto L_0x010f
            r4.close()     // Catch:{ Exception -> 0x013d }
        L_0x010f:
            if (r1 == 0) goto L_0x0013
            r1.close()     // Catch:{ Exception -> 0x0116 }
            goto L_0x0013
        L_0x0116:
            r1 = move-exception
            goto L_0x0013
        L_0x0119:
            r0 = move-exception
            r2 = r3
            r4 = r3
            r5 = r3
        L_0x011d:
            if (r5 == 0) goto L_0x0122
            r5.destroy()     // Catch:{ Exception -> 0x013f }
        L_0x0122:
            if (r4 == 0) goto L_0x0127
            r4.close()     // Catch:{ Exception -> 0x0141 }
        L_0x0127:
            if (r2 == 0) goto L_0x012c
            r2.close()     // Catch:{ Exception -> 0x0143 }
        L_0x012c:
            throw r0
        L_0x012d:
            r1 = move-exception
            goto L_0x00b3
        L_0x012f:
            r1 = move-exception
            goto L_0x00b8
        L_0x0131:
            r1 = move-exception
            goto L_0x00e0
        L_0x0133:
            r1 = move-exception
            goto L_0x00e5
        L_0x0135:
            r0 = move-exception
            goto L_0x00f4
        L_0x0137:
            r0 = move-exception
            goto L_0x00f9
        L_0x0139:
            r0 = move-exception
            goto L_0x00fe
        L_0x013b:
            r2 = move-exception
            goto L_0x010a
        L_0x013d:
            r2 = move-exception
            goto L_0x010f
        L_0x013f:
            r1 = move-exception
            goto L_0x0122
        L_0x0141:
            r1 = move-exception
            goto L_0x0127
        L_0x0143:
            r1 = move-exception
            goto L_0x012c
        L_0x0145:
            r0 = move-exception
            r2 = r3
            r4 = r3
            goto L_0x011d
        L_0x0149:
            r0 = move-exception
            r2 = r3
            goto L_0x011d
        L_0x014c:
            r0 = move-exception
            goto L_0x011d
        L_0x014e:
            r1 = move-exception
            r1 = r3
            r4 = r3
            goto L_0x0105
        L_0x0152:
            r1 = move-exception
            r1 = r3
            goto L_0x0105
        L_0x0155:
            r1 = move-exception
            r1 = r2
            goto L_0x0105
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.n.a(java.lang.String, int):int");
    }

    private static String c(String str, int i) {
        if (str == null || str.length() <= 0 || str.equals("0.0.0.0")) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            Process exec = Runtime.getRuntime().exec("ping -w 1 -c 1 -t " + i + " " + str);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "UTF-8"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            bufferedReader.close();
            int waitFor = exec.waitFor();
            if (waitFor != 0 && waitFor != 1 && waitFor != 2) {
                return "";
            }
            Matcher matcher = a.matcher(sb.toString());
            if (matcher.find()) {
                String group = matcher.group();
                h.a("getTraceIp: " + group + ", with hop:" + i);
                return group;
            }
            return "";
        } catch (Throwable th) {
        }
    }

    private static String b(String str) {
        return c(str, 2);
    }

    public static int b(String str, int i) {
        try {
            String b2 = b(str);
            if (b2 == null || b2.length() <= 0) {
                return -5;
            }
            if (!f.j(b2)) {
                return -6;
            }
            return a(b2, i);
        } catch (Throwable th) {
            return -7;
        }
    }

    public static String a(String str) {
        int i = 2;
        while (i <= 30) {
            int i2 = i + 1;
            try {
                String c = c(str, i);
                if (!f.j(c)) {
                    return c;
                }
                if (b.matcher(c).find()) {
                    break;
                }
                i = i2;
            } catch (Exception e) {
            }
        }
        return "";
    }
}
