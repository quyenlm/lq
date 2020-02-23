package com.tencent.mna.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/* compiled from: NetworkUtil */
public final class k {
    public static String a(int i) {
        switch (i) {
            case 0:
                return "unknown或无网络";
            case 1:
                return "2G";
            case 2:
                return "3G";
            case 3:
                return "4G";
            case 4:
                return "wifi";
            case 5:
                return "ethernet";
            default:
                return "unknown";
        }
    }

    public static int a(Context context) {
        int i;
        if (context == null) {
            return 0;
        }
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
                return 0;
            }
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                i = 0;
            } else if (activeNetworkInfo.getType() == 1) {
                i = 4;
            } else if (activeNetworkInfo.getType() == 0) {
                i = a(activeNetworkInfo);
            } else {
                i = activeNetworkInfo.getType() == 9 ? 5 : 0;
            }
            return i;
        } catch (Exception e) {
            h.a("getNetworkState exception:" + e.getMessage());
            i = 0;
        }
    }

    static int a(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return 0;
        }
        switch (networkInfo.getSubtype()) {
            case 0:
                return 0;
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return 1;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return 2;
            case 13:
            case 18:
                return 3;
            default:
                return 3;
        }
    }

    public static boolean b(int i) {
        return i == 0 || i == 1;
    }

    public static boolean b(Context context) {
        return b(a(context));
    }

    public static boolean c(int i) {
        return i == 1 || i == 2 || i == 3;
    }

    public static boolean d(int i) {
        return i == 4;
    }

    public static boolean c(Context context) {
        return d(a(context));
    }

    public static boolean e(int i) {
        return i == 5;
    }

    public static int a(Context context, int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return i.a();
            case 4:
                return q.a(context);
            default:
                return -1;
        }
    }

    public static int b(Context context, int i) {
        switch (i) {
            case 1:
            case 2:
            case 3:
                return i.b();
            case 4:
                return q.f(context);
            default:
                return 1;
        }
    }

    public static String a(boolean z) {
        Enumeration<InetAddress> inetAddresses;
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface nextElement = networkInterfaces.nextElement();
                    if (!(nextElement == null || (inetAddresses = nextElement.getInetAddresses()) == null)) {
                        while (inetAddresses.hasMoreElements()) {
                            InetAddress nextElement2 = inetAddresses.nextElement();
                            if (!nextElement2.isLoopbackAddress() && !nextElement2.isLinkLocalAddress()) {
                                if ((z && (nextElement2 instanceof Inet4Address)) || (!z && (nextElement2 instanceof Inet6Address))) {
                                    return nextElement2.getHostAddress();
                                }
                            }
                        }
                        continue;
                    }
                }
            }
        } catch (Throwable th) {
        }
        return "0.0.0.0";
    }

    public static void d(Context context) {
        if (context != null) {
            i.a(context);
        }
    }
}
