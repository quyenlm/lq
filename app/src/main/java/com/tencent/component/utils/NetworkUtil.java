package com.tencent.component.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.http.Apn;
import com.tencent.component.utils.log.LogUtil;
import java.util.HashMap;

public class NetworkUtil {
    public static final String APN_NAME_WIFI = "wifi";
    private static final Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    private static final String TAG = "NetworkUtil";
    private static final HashMap<String, NetworkProxy> sAPNProxies = new HashMap<>();

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    public static boolean isWifiConnected(Context context) {
        boolean z = true;
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1) {
            z = false;
        }
        return z;
    }

    public static boolean isViaMobile(Context context) {
        NetworkInfo activeNetworkInfo;
        if (context == null || (activeNetworkInfo = getActiveNetworkInfo(context)) == null || activeNetworkInfo.getType() != 0) {
            return false;
        }
        return true;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            return ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Throwable e) {
            LogUtil.e(TAG, "fail to get active network info", e);
            return null;
        }
    }

    public static class NetworkProxy implements Cloneable {
        public final String host;
        public final int port;

        NetworkProxy(String host2, int port2) {
            this.host = host2;
            this.port = port2;
        }

        /* access modifiers changed from: package-private */
        public final NetworkProxy copy() {
            try {
                return (NetworkProxy) clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }

        public String toString() {
            return this.host + ":" + this.port;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof NetworkProxy)) {
                NetworkProxy proxy = (NetworkProxy) obj;
                if (!TextUtils.equals(this.host, proxy.host) || this.port != proxy.port) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    static {
        sAPNProxies.put("cmwap", new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put(Apn.APN_3GWAP, new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put("uniwap", new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put("ctwap", new NetworkProxy("10.0.0.200", 80));
    }

    public static NetworkProxy getProxy(Context context, boolean apnProxy) {
        return !apnProxy ? getProxy(context) : getProxyByAPN(context);
    }

    public static NetworkProxy getProxy(Context context) {
        if (!isViaMobile(context)) {
            return null;
        }
        String proxyHost = getProxyHost(context);
        int proxyPort = getProxyPort(context);
        if (isEmpty(proxyHost) || proxyPort < 0) {
            return null;
        }
        return new NetworkProxy(proxyHost, proxyPort);
    }

    private static String getProxyHost(Context context) {
        if (PlatformUtil.version() < 11) {
            return Proxy.getDefaultHost();
        }
        return System.getProperty("http.proxyHost");
    }

    private static int getProxyPort(Context context) {
        int port = -1;
        if (PlatformUtil.version() < 11) {
            port = Proxy.getDefaultPort();
        } else {
            String portStr = System.getProperty("http.proxyPort");
            if (!isEmpty(portStr)) {
                try {
                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        if (port < 0 || port > 65535) {
            return -1;
        }
        return port;
    }

    public static NetworkProxy getProxyByAPN(Context context) {
        NetworkProxy proxy;
        if (isViaMobile(context) && (proxy = sAPNProxies.get(getAPN(context))) != null) {
            return proxy.copy();
        }
        return null;
    }

    public static String getAPN(Context context) {
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo == null) {
            return null;
        }
        String apn = null;
        if (activeNetInfo.getType() == 1) {
            apn = "wifi";
        } else if (activeNetInfo.getType() == 0) {
            if (PlatformUtil.version() < 17) {
                Cursor cursor = null;
                try {
                    Cursor cursor2 = context.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
                    while (cursor2 != null && cursor2.moveToNext()) {
                        apn = cursor2.getString(cursor2.getColumnIndex("apn"));
                    }
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (TextUtils.isEmpty(apn)) {
                apn = activeNetInfo.getExtraInfo();
            }
        }
        if (apn != null) {
            return apn.toLowerCase();
        }
        return apn;
    }

    public static final class DNS {
        public String primary;
        public String secondary;

        DNS() {
        }

        public String toString() {
            return this.primary + "," + this.secondary;
        }
    }

    public static DNS getDNS(Context context) {
        DhcpInfo dhcpInfo;
        DNS dns = new DNS();
        if (!(context == null || !isWifiConnected(context) || (dhcpInfo = ((WifiManager) context.getSystemService("wifi")).getDhcpInfo()) == null)) {
            dns.primary = int32ToIPStr(dhcpInfo.dns1);
            dns.secondary = int32ToIPStr(dhcpInfo.dns2);
        }
        if (dns.primary == null && dns.secondary == null) {
            dns.primary = PropertyUtils.get("net.dns1", (String) null);
            dns.secondary = PropertyUtils.get("net.dns2", (String) null);
        }
        return dns;
    }

    private static String int32ToIPStr(int ip) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ip & 255).append(".");
        buffer.append((ip >> 8) & 255).append(".");
        buffer.append((ip >> 16) & 255).append(".");
        buffer.append((ip >> 24) & 255);
        return buffer.toString();
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    private NetworkUtil() {
    }
}
