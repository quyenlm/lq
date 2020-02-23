package com.tencent.mna.base.utils;

import com.tencent.mna.base.jni.e;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: IpUtils */
public final class f {
    private static final Pattern a = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    private static final Pattern b = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    private static final Pattern c = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    public static boolean a(String str) {
        if (str == null || str.length() <= 0 || "0.0.0.0".equals(str) || "255.255.255.255".equals(str)) {
            return false;
        }
        return d(str);
    }

    public static boolean b(String str) {
        return a.matcher(str).matches();
    }

    public static boolean c(String str) {
        return m(str) || n(str);
    }

    public static boolean d(String str) {
        return b(str) || c(str);
    }

    private static boolean m(String str) {
        return b.matcher(str).matches();
    }

    private static boolean n(String str) {
        return c.matcher(str).matches();
    }

    private static byte[] o(String str) {
        if (str == null) {
            return null;
        }
        try {
            if (b(str)) {
                String[] split = str.split("\\.");
                byte[] bArr = new byte[4];
                for (int i = 0; i < 4; i++) {
                    bArr[i] = (byte) Integer.parseInt(split[i]);
                }
                byte[] bArr2 = new byte[16];
                bArr2[10] = -1;
                bArr2[11] = -1;
                bArr2[12] = bArr[0];
                bArr2[13] = bArr[1];
                bArr2[14] = bArr[2];
                bArr2[15] = bArr[3];
                return bArr2;
            } else if (c(str)) {
                return InetAddress.getByName(str).getAddress();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static List<String> e(String str) {
        ArrayList arrayList = new ArrayList();
        InetAddress[] g = g(str);
        if (g != null && g.length > 0) {
            for (InetAddress hostAddress : g) {
                arrayList.add(hostAddress.getHostAddress());
            }
        }
        return arrayList;
    }

    public static String f(String str) {
        InetAddress h = h(str);
        if (h != null) {
            return h.getHostAddress();
        }
        return "";
    }

    public static InetAddress[] g(String str) {
        int i = 2;
        if (d(str)) {
            try {
                return InetAddress.getAllByName(str);
            } catch (Exception e) {
            }
        }
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                try {
                    a aVar = new a(str);
                    aVar.start();
                    aVar.join(3000);
                    InetAddress[] a2 = aVar.a();
                    if (a2 != null && a2.length > 0) {
                        return a2;
                    }
                    i = i2;
                } catch (Exception e2) {
                    i = i2;
                }
            } else {
                h.c("dns解析addrs失败:" + str);
                return null;
            }
        }
    }

    public static InetAddress h(String str) {
        InetAddress[] g = g(str);
        if (g == null || g.length <= 0) {
            return null;
        }
        return g[0];
    }

    public static String a(String str, int i) {
        InetAddress c2 = c(str, i);
        if (c2 != null) {
            return c2.getHostAddress();
        }
        return "";
    }

    public static InetAddress[] b(String str, int i) {
        int i2 = 2;
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                try {
                    a aVar = new a(str, i);
                    aVar.start();
                    aVar.join(3000);
                    InetAddress[] a2 = aVar.a();
                    if (a2 != null && a2.length > 0) {
                        return a2;
                    }
                    i2 = i3;
                } catch (Exception e) {
                    i2 = i3;
                }
            } else {
                h.c("dnsOnNet解析addrs失败:" + str + ", netid" + i);
                return null;
            }
        }
    }

    public static InetAddress c(String str, int i) {
        InetAddress[] b2 = b(str, i);
        if (b2 == null || b2.length <= 0) {
            return null;
        }
        return b2[0];
    }

    public static String a(int i) {
        return (i & 255) + "." + ((i >> 8) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 24) & 255);
    }

    public static String b(int i) {
        return ((i >> 24) & 255) + "." + ((i >> 16) & 255) + "." + ((i >> 8) & 255) + "." + (i & 255);
    }

    public static int i(String str) {
        int i = 0;
        if (str != null) {
            String[] split = str.split("\\.");
            if (split.length == 4) {
                int i2 = 3;
                while (i2 >= 0) {
                    try {
                        i |= Integer.parseInt(split[3 - i2]) << (i2 * 8);
                        i2--;
                    } catch (Exception e) {
                    }
                }
            }
        }
        return i;
    }

    public static int c(int i) {
        return i(a(i));
    }

    public static boolean j(String str) {
        long p = p(str);
        return (p >= 167772160 && p <= 184549375) || (p >= 2886729728L && p <= 2887778303L) || ((p >= 3232235520L && p <= 3232301055L) || str.equals("127.0.0.1"));
    }

    private static long p(String str) {
        String[] split = str.split("\\.");
        return ((long) Integer.parseInt(split[3])) + (((long) Integer.parseInt(split[0])) * 256 * 256 * 256) + (((long) Integer.parseInt(split[1])) * 256 * 256) + (((long) Integer.parseInt(split[2])) * 256);
    }

    /* compiled from: IpUtils */
    private static class a extends Thread {
        private InetAddress[] a;
        private String b;
        private int c;

        private a(String str) {
            this(str, -1);
        }

        private a(String str, int i) {
            this.a = null;
            this.b = null;
            this.c = -1;
            this.b = str;
            this.c = i;
        }

        public void run() {
            InetAddress[] allByName;
            try {
                if (this.c > 0) {
                    allByName = (InetAddress[]) InetAddress.class.getMethod("getAllByNameOnNet", new Class[]{String.class, Integer.TYPE}).invoke((Object) null, new Object[]{this.b, Integer.valueOf(this.c)});
                } else {
                    allByName = InetAddress.getAllByName(this.b);
                }
                a(allByName);
            } catch (Throwable th) {
                h.a("dns exception:" + th.getMessage());
            }
        }

        private synchronized void a(InetAddress[] inetAddressArr) {
            this.a = inetAddressArr;
        }

        /* access modifiers changed from: private */
        public synchronized InetAddress[] a() {
            return this.a;
        }
    }

    public static byte[] k(String str) {
        try {
            int f = e.f();
            if (c(str)) {
                return InetAddress.getByName(str).getAddress();
            }
            if (b(str)) {
                if (f == 1 || f == 3) {
                    return o(str);
                }
                if (f == 2) {
                    return a(str, true).getAddress();
                }
                return null;
            } else if (f == 1 || f == 3) {
                return o(a(str, false).getHostAddress());
            } else {
                if (f == 2) {
                    return a(str, true).getAddress();
                }
                return null;
            }
        } catch (Exception e) {
            h.a("getV6IpAddress exception:" + e.getMessage());
            return null;
        }
    }

    private static InetAddress a(String str, boolean z) {
        InetAddress[] g = g(str);
        if (g != null) {
            for (InetAddress inetAddress : g) {
                if (z) {
                    if (inetAddress instanceof Inet6Address) {
                        return inetAddress;
                    }
                } else if (inetAddress instanceof Inet4Address) {
                    return inetAddress;
                }
            }
        }
        return null;
    }

    public static String l(String str) {
        if (b(str)) {
            return "::ffff:" + str;
        }
        if (!c(str)) {
            return "";
        }
        return str;
    }
}
