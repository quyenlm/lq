package com.tencent.component.net.download.multiplex.http;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.tencent.component.net.download.multiplex.FileDownload;
import com.tencent.imsdk.tool.etc.APNUtil;

public class Apn {
    public static final String APN_3GNET = "3gnet";
    public static final String APN_3GWAP = "3gwap";
    public static final String APN_777 = "#777";
    private static String APN_AND_NETWORK_NAME = "";
    public static final String APN_CMNET = "cmnet";
    public static final String APN_CMWAP = "cmwap";
    public static final String APN_CTNET = "ctnet";
    public static final String APN_CTWAP = "ctwap";
    public static final String APN_NET = "Net";
    public static final String APN_UNINET = "uninet";
    public static final String APN_UNIWAP = "uniwap";
    public static final String APN_UNKNOWN = "N/A";
    public static final String APN_WAP = "Wap";
    public static final String APN_WIFI = "Wlan";
    private static String APN_WIFI_NAME_WITH_SSID = "";
    public static final int NETWORK_TYPE_1xRTT = 7;
    public static final int NETWORK_TYPE_CDMA = 4;
    public static final int NETWORK_TYPE_EDGE = 2;
    public static final int NETWORK_TYPE_EHRPD = 14;
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    public static final int NETWORK_TYPE_EVDO_A = 6;
    public static final int NETWORK_TYPE_EVDO_B = 12;
    public static final int NETWORK_TYPE_GPRS = 1;
    public static final int NETWORK_TYPE_HSDPA = 8;
    public static final int NETWORK_TYPE_HSPA = 10;
    public static final int NETWORK_TYPE_HSPAP = 15;
    public static final int NETWORK_TYPE_HSUPA = 9;
    public static final int NETWORK_TYPE_IDEN = 11;
    public static final int NETWORK_TYPE_LTE = 13;
    public static final int NETWORK_TYPE_UMTS = 3;
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    private static final String PROXY_CTWAP = "10.0.0.200";
    public static final byte PROXY_TYPE_CM = 0;
    public static final byte PROXY_TYPE_CT = 1;
    private static final String TAG = "Apn";
    public static final int TYPE_NET = 1;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WAP = 2;
    public static final int TYPE_WIFI = 4;
    public static final int T_APN_3GNET = 512;
    public static final int T_APN_3GWAP = 16;
    public static final int T_APN_CMNET = 1024;
    public static final int T_APN_CMWAP = 8;
    public static final int T_APN_CTNET = 128;
    public static final int T_APN_CTWAP = 64;
    public static final int T_APN_UNINET = 256;
    public static final int T_APN_UNIWAP = 32;
    private static ApnProxyInfo sApnProxyInfo = new ApnProxyInfo();
    private static int sApnType = 4;
    private static int sApnTypeS = 4;

    public static class ApnProxyInfo {
        public int apnPort = 80;
        public String apnProxy = null;
        public byte apnProxyType = 0;
        public boolean apnUseProxy = false;
    }

    public static synchronized int getApnType(boolean shouldRefrash) {
        int i;
        synchronized (Apn.class) {
            if (shouldRefrash) {
                init();
            }
            i = sApnType;
        }
        return i;
    }

    public static synchronized int getApnType() {
        int apnType;
        synchronized (Apn.class) {
            apnType = getApnType(true);
        }
        return apnType;
    }

    public static int getUploadApn() {
        int uploadApn = getApnType(true);
        switch (uploadApn) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 2;
            case 4:
                return 3;
            default:
                return uploadApn;
        }
    }

    public static synchronized int getApnTypeS(boolean shouldRefrash) {
        int i;
        synchronized (Apn.class) {
            if (shouldRefrash) {
                init();
            }
            i = sApnTypeS;
        }
        return i;
    }

    public static synchronized int getApnTypeS() {
        int apnTypeS;
        synchronized (Apn.class) {
            apnTypeS = getApnTypeS(true);
        }
        return apnTypeS;
    }

    public static String getNetworkTypeName() {
        NetworkInfo networkInfo;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return "";
        }
        return networkInfo.getSubtypeName();
    }

    public static synchronized ApnProxyInfo getApnProxyInfo() {
        ApnProxyInfo apnProxyInfo;
        synchronized (Apn.class) {
            init();
            apnProxyInfo = sApnProxyInfo;
        }
        return apnProxyInfo;
    }

    private static void init() {
        NetworkInfo networkInfo = ((ConnectivityManager) FileDownload.context.getSystemService("connectivity")).getActiveNetworkInfo();
        int type = -1;
        try {
            sApnType = 0;
            sApnTypeS = 0;
            String extraInfo = null;
            if (networkInfo != null) {
                type = networkInfo.getType();
                extraInfo = networkInfo.getExtraInfo();
                if (extraInfo == null) {
                    sApnType = 0;
                    sApnTypeS = 0;
                } else {
                    extraInfo = extraInfo.trim().toLowerCase();
                }
            }
            if (type == 1) {
                sApnType = 4;
                sApnTypeS = 4;
                sApnProxyInfo.apnUseProxy = false;
                APN_WIFI_NAME_WITH_SSID = APN_WIFI + getWifiBSSID();
                APN_AND_NETWORK_NAME = "Wlan-unknown";
                return;
            }
            if (extraInfo == null) {
                sApnType = 0;
                sApnTypeS = 0;
            } else if (extraInfo.contains("cmwap")) {
                sApnType = 2;
                sApnTypeS = 8;
            } else if (extraInfo.contains("uniwap")) {
                sApnType = 2;
                sApnTypeS = 32;
            } else if (extraInfo.contains(APN_3GWAP)) {
                sApnType = 2;
                sApnTypeS = 16;
            } else if (extraInfo.contains("ctwap")) {
                sApnType = 2;
                sApnTypeS = 64;
            } else if (extraInfo.contains("cmnet")) {
                sApnType = 1;
                sApnTypeS = 1024;
            } else if (extraInfo.contains("uninet")) {
                sApnType = 1;
                sApnTypeS = 256;
            } else if (extraInfo.contains(APN_3GNET)) {
                sApnType = 1;
                sApnTypeS = 512;
            } else if (extraInfo.contains("ctnet")) {
                sApnType = 1;
                sApnTypeS = 128;
            } else if (extraInfo.contains(APN_777)) {
                sApnType = 0;
                sApnTypeS = 0;
            } else {
                sApnType = 0;
                sApnTypeS = 0;
            }
            sApnProxyInfo.apnUseProxy = false;
            if (isProxyMode(sApnType)) {
                sApnProxyInfo.apnProxy = Proxy.getDefaultHost();
                sApnProxyInfo.apnPort = Proxy.getDefaultPort();
                if (sApnProxyInfo.apnProxy != null) {
                    sApnProxyInfo.apnProxy = sApnProxyInfo.apnProxy.trim();
                }
                if (!TextUtils.isEmpty(sApnProxyInfo.apnProxy)) {
                    sApnProxyInfo.apnUseProxy = true;
                    sApnType = 2;
                    if (PROXY_CTWAP.equals(sApnProxyInfo.apnProxy)) {
                        sApnProxyInfo.apnProxyType = 1;
                        sApnTypeS = 64;
                    } else {
                        sApnProxyInfo.apnProxyType = 0;
                    }
                } else {
                    sApnProxyInfo.apnUseProxy = false;
                    sApnType = 1;
                    if (extraInfo != null && extraInfo.contains(APN_777)) {
                        sApnTypeS = 128;
                    }
                }
            }
            APN_AND_NETWORK_NAME = getApnName(sApnTypeS) + "-" + (networkInfo != null ? networkInfo.getSubtypeName() : "unknown");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNetworkConnected() {
        NetworkInfo networkInfo;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return false;
        }
        if (networkInfo.isConnected() || networkInfo.isAvailable()) {
            return true;
        }
        return false;
    }

    public static boolean isWifiMode() {
        NetworkInfo networkInfo;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null || networkInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static String getWifiBSSID() {
        WifiManager manager;
        WifiInfo wifiInfo;
        if (sApnTypeS != 4 || (manager = (WifiManager) FileDownload.context.getSystemService("wifi")) == null || (wifiInfo = manager.getConnectionInfo()) == null) {
            return null;
        }
        return wifiInfo.getBSSID();
    }

    public static String getWifiSSID() {
        WifiManager manager;
        WifiInfo wifiInfo;
        if (sApnTypeS != 4 || (manager = (WifiManager) FileDownload.context.getSystemService("wifi")) == null || (wifiInfo = manager.getConnectionInfo()) == null) {
            return null;
        }
        return wifiInfo.getSSID();
    }

    public static boolean is3GOr2GMode() {
        return is3GMode() || is2GMode();
    }

    public static boolean is3GMode() {
        NetworkInfo networkInfo;
        int type;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null || (type = networkInfo.getType()) == 1 || type != 0) {
            return false;
        }
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
                return false;
            default:
                return true;
        }
    }

    public static boolean is2GMode() {
        NetworkInfo networkInfo;
        int type;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null || (type = networkInfo.getType()) == 1 || type != 0) {
            return false;
        }
        switch (networkInfo.getSubtype()) {
            case 1:
            case 2:
            case 4:
                return true;
            default:
                return false;
        }
    }

    private static boolean isProxyMode(int apnType) {
        return apnType == 2 || apnType == 0;
    }

    public static String getApnName(int apnType) {
        switch (apnType) {
            case 4:
                return APN_WIFI;
            case 8:
                return "cmwap";
            case 16:
                return APN_3GWAP;
            case 32:
                return "uniwap";
            case 64:
                return "ctwap";
            case 128:
                return "ctnet";
            case 256:
                return "uninet";
            case 512:
                return APN_3GNET;
            case 1024:
                return "cmnet";
            default:
                return APN_UNKNOWN;
        }
    }

    public static String getCurrentApnAndNetworkName() {
        return APN_AND_NETWORK_NAME;
    }

    public static String getApnNameWithBSSID(int apnType) {
        if (apnType != 4) {
            return getApnName(apnType);
        }
        return APN_WIFI_NAME_WITH_SSID;
    }

    public static boolean isNetworkAvailable() {
        NetworkInfo networkInfo;
        ConnectivityManager manager = (ConnectivityManager) FileDownload.context.getSystemService("connectivity");
        if (manager == null || (networkInfo = manager.getActiveNetworkInfo()) == null) {
            return false;
        }
        if (networkInfo.isConnected() || networkInfo.isAvailable()) {
            return true;
        }
        return false;
    }

    public static int getType(String apnName) {
        if (TextUtils.isEmpty(apnName)) {
            return 0;
        }
        String apnLowserCase = apnName.trim().toLowerCase();
        if (apnLowserCase.indexOf("wifi") != -1 || apnLowserCase.indexOf("wlan") != -1) {
            return 4;
        }
        if (apnLowserCase.indexOf(APNUtil.ANP_NAME_NET) != -1) {
            return 1;
        }
        if (apnLowserCase.indexOf(APNUtil.ANP_NAME_WAP) != -1) {
            return 2;
        }
        return 0;
    }
}
