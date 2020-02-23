package com.tencent.mna.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import com.tencent.mna.base.e.b;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: WifiUtil */
public final class q {
    private static int a = -1;

    public static int a(Context context) {
        int i;
        if (context == null) {
            return -1;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo.getBSSID() != null) {
                    i = WifiManager.calculateSignalLevel(connectionInfo.getRssi(), 5);
                    return i;
                }
            }
            i = -1;
        } catch (Exception e) {
            i = -1;
        }
        return i;
    }

    public static a b(Context context) {
        int i;
        if (context == null) {
            h.a("getRouterInfo, context is null");
            return new a(-2);
        } else if (k.a(context) != 4) {
            h.a("getRouterInfo, current net is non-wifi");
            return new a(-3);
        } else {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                h.a("getRouterInfo, wifiManager is null");
                return new a(-4);
            }
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            int i2 = dhcpInfo.ipAddress;
            int i3 = dhcpInfo.netmask;
            if (i3 == 0) {
                i3 = 16777215;
                h.a("getRouterInfo, netmaskNetSeq is null");
            }
            String a2 = f.a(i2);
            try {
                if (!f.a(a2)) {
                    h.a("getRouterInfo, current host ip is not valid ipv4 address");
                    return new a(-5);
                }
                int i4 = f.i(a2);
                int c = f.c(i3);
                int i5 = (i4 & c) + 1;
                int i6 = (i4 & c) | (c ^ -1);
                h.a("getRouterInfo, ip:" + a2 + ", netmask:" + f.a(i3));
                h.a("getRouterInfo, startIp:" + f.b(i5) + ", endIp:" + f.b(i6));
                int i7 = 0;
                ArrayList arrayList = new ArrayList();
                while (true) {
                    if (i5 >= i6) {
                        i = i7;
                        break;
                    }
                    i7++;
                    if (i7 >= 255) {
                        i = i7;
                        break;
                    }
                    String b = f.b(i5);
                    arrayList.add(b);
                    a(b);
                    i5++;
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
                int size = a((List<String>) arrayList).size();
                if (size == 0) {
                    size = 1;
                }
                h.a("getRouterInfo, availableIps:" + i + ", terminals:" + size);
                a = size;
                return new a(size, i);
            } catch (Exception e2) {
                return new a(-5);
            }
        }
    }

    public static int a() {
        return a;
    }

    private static void a(String str) {
        try {
            InetAddress h = f.h(str);
            if (h != null) {
                h.isReachable(5);
            }
        } catch (Exception e) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0082 A[SYNTHETIC, Splitter:B:24:0x0082] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0094 A[SYNTHETIC, Splitter:B:35:0x0094] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.Map<java.lang.String, java.lang.String> a(java.util.List<java.lang.String> r10) {
        /*
            java.util.concurrent.ConcurrentHashMap r2 = new java.util.concurrent.ConcurrentHashMap
            r2.<init>()
            r0 = 0
            if (r10 == 0) goto L_0x0087
            int r1 = r10.size()     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            if (r1 == 0) goto L_0x0087
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            java.io.InputStreamReader r3 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            java.lang.String r5 = "/proc/net/arp"
            r4.<init>(r5)     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            java.lang.String r5 = "UTF-8"
            java.nio.charset.Charset r5 = java.nio.charset.Charset.forName(r5)     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
            r4 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r3, r4)     // Catch:{ Exception -> 0x00a0, all -> 0x008f }
        L_0x0027:
            java.lang.String r3 = r1.readLine()     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r3 == 0) goto L_0x0086
            java.lang.String r0 = "00:00:00:00:00:00"
            boolean r0 = r3.contains(r0)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r0 != 0) goto L_0x0027
            java.util.Iterator r4 = r10.iterator()     // Catch:{ Exception -> 0x007e, all -> 0x009c }
        L_0x0039:
            boolean r0 = r4.hasNext()     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r0 == 0) goto L_0x0027
            java.lang.Object r0 = r4.next()     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            boolean r5 = r3.contains(r0)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r5 == 0) goto L_0x0039
            java.lang.String r5 = "^%s\\s+0x1\\s+0x[2|6]\\s+([:0-9a-fA-F]+)\\s+\\*\\s+\\w+$"
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            r7 = 0
            java.lang.String r8 = "."
            java.lang.String r9 = "\\."
            java.lang.String r8 = r0.replace(r8, r9)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            r6[r7] = r8     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            java.lang.String r5 = java.lang.String.format(r5, r6)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            java.util.regex.Pattern r5 = java.util.regex.Pattern.compile(r5)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            java.util.regex.Matcher r5 = r5.matcher(r3)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            boolean r6 = r5.matches()     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r6 == 0) goto L_0x0039
            r3 = 1
            java.lang.String r3 = r5.group(r3)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            java.lang.String r4 = "00:00:00:00:00:00"
            boolean r4 = r3.equals(r4)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            if (r4 != 0) goto L_0x0027
            r2.put(r0, r3)     // Catch:{ Exception -> 0x007e, all -> 0x009c }
            goto L_0x0027
        L_0x007e:
            r0 = move-exception
            r0 = r1
        L_0x0080:
            if (r0 == 0) goto L_0x0085
            r0.close()     // Catch:{ Exception -> 0x0098 }
        L_0x0085:
            return r2
        L_0x0086:
            r0 = r1
        L_0x0087:
            if (r0 == 0) goto L_0x0085
            r0.close()     // Catch:{ Exception -> 0x008d }
            goto L_0x0085
        L_0x008d:
            r0 = move-exception
            goto L_0x0085
        L_0x008f:
            r1 = move-exception
            r2 = r1
            r3 = r0
        L_0x0092:
            if (r3 == 0) goto L_0x0097
            r3.close()     // Catch:{ Exception -> 0x009a }
        L_0x0097:
            throw r2
        L_0x0098:
            r0 = move-exception
            goto L_0x0085
        L_0x009a:
            r0 = move-exception
            goto L_0x0097
        L_0x009c:
            r0 = move-exception
            r2 = r0
            r3 = r1
            goto L_0x0092
        L_0x00a0:
            r1 = move-exception
            goto L_0x0080
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.q.a(java.util.List):java.util.Map");
    }

    /* compiled from: WifiUtil */
    public static class a {
        public final int a;
        public final int b;

        public a(int i, int i2) {
            this.a = i;
            this.b = i2;
        }

        public a(int i) {
            this(i, 0);
        }
    }

    public static int a(Context context, int i) {
        try {
            String c = c(context);
            int a2 = n.a(c, i);
            h.a("pingGateway, ip: " + c + ", avgDelay: " + a2);
            return a2;
        } catch (Exception e) {
            h.a("pingGateway, exception:" + e.getMessage());
            return -2;
        }
    }

    public static String c(Context context) {
        String str;
        if (context == null) {
            return "0.0.0.0";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            str = wifiManager != null ? f.a(wifiManager.getDhcpInfo().gateway) : "0.0.0.0";
        } catch (Exception e) {
            str = "0.0.0.0";
        }
        return str;
    }

    public static String d(Context context) {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        try {
            if (!(k.a(context) != 4 || (wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi")) == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
                return connectionInfo.getBSSID();
            }
        } catch (Exception e) {
            h.a("getRouterMac, exception:" + e.getMessage());
        }
        return "0";
    }

    public static int e(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo.getBSSID() != null) {
                    return connectionInfo.getLinkSpeed();
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int f(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo.getBSSID() != null) {
                    return connectionInfo.getRssi();
                }
            }
            return 1;
        } catch (Exception e) {
            return 1;
        }
    }

    private static int a(int i) {
        switch (i) {
            case 2412:
                return 1;
            case 2417:
                return 2;
            case 2422:
                return 3;
            case 2427:
                return 4;
            case 2432:
                return 5;
            case 2437:
                return 6;
            case 2442:
                return 7;
            case 2447:
                return 8;
            case 2452:
                return 9;
            case 2457:
                return 10;
            case 2462:
                return 11;
            case 2467:
                return 12;
            case 2472:
                return 13;
            case 2484:
                return 14;
            case 5180:
                return 36;
            case 5190:
                return 38;
            case 5200:
                return 40;
            case 5210:
                return 42;
            case 5220:
                return 44;
            case 5230:
                return 46;
            case 5240:
                return 48;
            case 5260:
                return 52;
            case 5280:
                return 56;
            case 5300:
                return 60;
            case 5320:
                return 64;
            case 5500:
                return 100;
            case 5520:
                return 104;
            case 5745:
                return 149;
            case 5765:
                return 153;
            case 5785:
                return 157;
            case 5805:
                return 161;
            case 5825:
                return 165;
            default:
                return -1;
        }
    }

    public static int g(Context context) {
        int i;
        if (k.a(context) != 4) {
            return -1;
        }
        int i2 = 0;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                return 0;
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (scanResults == null || scanResults.size() <= 0) {
                h.a("channel scanResult is 0, for location switch or permission denied");
                return 0;
            }
            for (ScanResult next : scanResults) {
                int a2 = a(next.frequency);
                if (next.BSSID.equalsIgnoreCase(connectionInfo.getBSSID())) {
                    i = a2;
                } else {
                    i = i2;
                }
                i2 = i;
            }
            return i2;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String h(Context context) {
        int i;
        int i2;
        if (k.a(context) != 4) {
            return "-1_-1_-1_-1";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                return "-1_-1_-1_-1";
            }
            WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            HashMap hashMap = new HashMap();
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (scanResults.size() <= 0) {
                h.a("scanResult is 0");
            }
            int i3 = 0;
            for (ScanResult next : scanResults) {
                int a2 = a(next.frequency);
                if (next.BSSID.equalsIgnoreCase(connectionInfo.getBSSID())) {
                    i2 = a2;
                } else {
                    i2 = i3;
                }
                if (hashMap.containsKey(Integer.valueOf(a2))) {
                    hashMap.put(Integer.valueOf(a2), Integer.valueOf(((Integer) hashMap.get(Integer.valueOf(a2))).intValue() + 1));
                } else {
                    hashMap.put(Integer.valueOf(a2), 1);
                }
                i3 = i2;
            }
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            for (Map.Entry entry : hashMap.entrySet()) {
                int intValue = ((Integer) entry.getKey()).intValue();
                int intValue2 = ((Integer) entry.getValue()).intValue();
                i4 += intValue2;
                if (i3 == intValue) {
                    i = i5;
                } else if (intValue < i3 - 2 || intValue > i3 + 2) {
                    i = i5;
                    intValue2 = i6;
                } else {
                    i = i5 + intValue2;
                    intValue2 = i6;
                }
                i5 = i;
                i6 = intValue2;
            }
            return "" + i3 + "_" + i6 + "_" + i5 + "_" + i4;
        } catch (Exception e) {
            return "-1_-1_-1_-1";
        }
    }

    public static int a(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.wepKeys[0] == null) {
            return 0;
        }
        return 1;
    }

    public static String i(Context context) {
        WifiManager wifiManager;
        WifiInfo connectionInfo;
        try {
            Context applicationContext = context.getApplicationContext();
            if (!(k.a(applicationContext) != 4 || (wifiManager = (WifiManager) applicationContext.getSystemService("wifi")) == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
                return connectionInfo.getSSID();
            }
        } catch (Exception e) {
            h.a("getRouterSSID, exception:" + e.getMessage());
        }
        return "0";
    }

    public static String j(Context context) {
        com.tencent.mna.base.e.a a2;
        String a3;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager == null) {
                return "unknown(0)";
            }
            String a4 = p.a("http://" + f.a(wifiManager.getDhcpInfo().gateway));
            if (!a4.startsWith("unknown") || (a2 = new b(context).a()) == null || (a3 = a2.a()) == null || a3.length() <= 0) {
                return a4;
            }
            return a3;
        } catch (Exception e) {
            return "unknown(-4)";
        }
    }

    public static int k(Context context) {
        WifiManager wifiManager;
        try {
            Context applicationContext = context.getApplicationContext();
            if (k.a(context) == 4 && (wifiManager = (WifiManager) applicationContext.getSystemService("wifi")) != null) {
                for (WifiConfiguration next : wifiManager.getConfiguredNetworks()) {
                    if (next.status == 0) {
                        return a(next);
                    }
                }
            }
        } catch (Exception e) {
            h.a("getWifiCode exception:" + e.getMessage());
        }
        return -1;
    }

    @SuppressLint({"HardwareIds"})
    public static String l(Context context) {
        String str;
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    str = connectionInfo.getMacAddress();
                } else {
                    str = "";
                }
                if ("02:00:00:00:00:00".equals(str)) {
                    String b = b();
                    if (b != null && b.length() > 0) {
                        return b.toUpperCase();
                    }
                    String c = c();
                    if (c != null && c.length() > 0) {
                        return c.toUpperCase();
                    }
                } else if (str != null) {
                    return str.toUpperCase();
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    private static String b() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if (nextElement != null && nextElement.getName().equalsIgnoreCase("wlan0")) {
                        byte[] bArr = new byte[0];
                        if (Build.VERSION.SDK_INT >= 9) {
                            bArr = nextElement.getHardwareAddress();
                        }
                        if (bArr == null || bArr.length <= 0) {
                            return "";
                        }
                        StringBuilder sb = new StringBuilder();
                        int length = bArr.length;
                        for (int i = 0; i < length; i++) {
                            sb.append(String.format("%02X:", new Object[]{Byte.valueOf(bArr[i])}));
                        }
                        if (sb.length() > 0) {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        return sb.toString();
                    }
                }
            }
        } catch (Exception e) {
            h.a("getMacAddress ByInterface failed, exception:" + e.getMessage());
        }
        return "";
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004c A[SYNTHETIC, Splitter:B:19:0x004c] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0056 A[SYNTHETIC, Splitter:B:25:0x0056] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String c() {
        /*
            r2 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            java.lang.String r4 = "/sys/class/net/wlan0/address"
            r3.<init>(r4)     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            java.lang.String r4 = "UTF-8"
            r0.<init>(r3, r4)     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            r3 = 1024(0x400, float:1.435E-42)
            r1.<init>(r0, r3)     // Catch:{ Exception -> 0x002e, all -> 0x0052 }
            java.lang.String r0 = r1.readLine()     // Catch:{ Exception -> 0x0062 }
            if (r0 == 0) goto L_0x0026
            java.lang.String r0 = r0.toUpperCase()     // Catch:{ Exception -> 0x0062 }
            if (r1 == 0) goto L_0x0025
            r1.close()     // Catch:{ Exception -> 0x005a }
        L_0x0025:
            return r0
        L_0x0026:
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch:{ Exception -> 0x005c }
        L_0x002b:
            java.lang.String r0 = ""
            goto L_0x0025
        L_0x002e:
            r0 = move-exception
            r1 = r2
        L_0x0030:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0060 }
            r2.<init>()     // Catch:{ all -> 0x0060 }
            java.lang.String r3 = "getMacAddress ByFile failed, exception:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x0060 }
            java.lang.String r0 = r0.getMessage()     // Catch:{ all -> 0x0060 }
            java.lang.StringBuilder r0 = r2.append(r0)     // Catch:{ all -> 0x0060 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0060 }
            com.tencent.mna.base.utils.h.a((java.lang.String) r0)     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x002b
            r1.close()     // Catch:{ Exception -> 0x0050 }
            goto L_0x002b
        L_0x0050:
            r0 = move-exception
            goto L_0x002b
        L_0x0052:
            r0 = move-exception
            r1 = r2
        L_0x0054:
            if (r1 == 0) goto L_0x0059
            r1.close()     // Catch:{ Exception -> 0x005e }
        L_0x0059:
            throw r0
        L_0x005a:
            r1 = move-exception
            goto L_0x0025
        L_0x005c:
            r0 = move-exception
            goto L_0x002b
        L_0x005e:
            r1 = move-exception
            goto L_0x0059
        L_0x0060:
            r0 = move-exception
            goto L_0x0054
        L_0x0062:
            r0 = move-exception
            goto L_0x0030
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.base.utils.q.c():java.lang.String");
    }
}
