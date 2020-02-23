package com.tencent.imsdk.tool.etc;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.tencent.component.net.download.multiplex.http.Apn;
import java.util.Locale;

public class APNUtil {
    public static final String ANP_NAME_CMNET = "cmnet";
    public static final String ANP_NAME_CMWAP = "cmwap";
    public static final String ANP_NAME_CTNET = "ctnet";
    public static final String ANP_NAME_CTWAP = "ctwap";
    public static final String ANP_NAME_NET = "net";
    public static final String ANP_NAME_NONE = "none";
    public static final String ANP_NAME_UNINET = "uninet";
    public static final String ANP_NAME_UNIWAP = "uniwap";
    public static final String ANP_NAME_WAP = "wap";
    public static final String ANP_NAME_WIFI = "wifi";
    public static final byte APNTYPE_3GNET = 11;
    public static final byte APNTYPE_3GWAP = 10;
    public static final byte APNTYPE_CMNET = 1;
    public static final byte APNTYPE_CMWAP = 2;
    public static final byte APNTYPE_CTNET = 8;
    public static final byte APNTYPE_CTWAP = 9;
    public static final byte APNTYPE_NET = 6;
    public static final byte APNTYPE_NONE = 0;
    public static final byte APNTYPE_UNINET = 4;
    public static final byte APNTYPE_UNIWAP = 5;
    public static final byte APNTYPE_WAP = 7;
    public static final byte APNTYPE_WIFI = 3;
    public static final String APN_PROP_APN = "apn";
    public static final String APN_PROP_PORT = "port";
    public static final String APN_PROP_PROXY = "proxy";
    public static final int JCE_APNTYPE_CMNET = 2;
    public static final int JCE_APNTYPE_CMWAP = 4;
    public static final int JCE_APNTYPE_CTNET = 256;
    public static final int JCE_APNTYPE_CTWAP = 512;
    public static final int JCE_APNTYPE_DEFAULT = 1;
    public static final int JCE_APNTYPE_NET = 64;
    public static final int JCE_APNTYPE_UNINET = 16;
    public static final int JCE_APNTYPE_UNIWAP = 32;
    public static final int JCE_APNTYPE_UNKNOWN = 0;
    public static final int JCE_APNTYPE_WAP = 128;
    public static final int JCE_APNTYPE_WIFI = 8;
    public static final int MPROXYTYPE_3GNET = 2048;
    public static final int MPROXYTYPE_3GWAP = 1024;
    public static final int MPROXYTYPE_CMNET = 4;
    public static final int MPROXYTYPE_CMWAP = 1;
    public static final int MPROXYTYPE_CTNET = 256;
    public static final int MPROXYTYPE_CTWAP = 512;
    public static final int MPROXYTYPE_DEFAULT = 128;
    public static final int MPROXYTYPE_NET = 32;
    public static final int MPROXYTYPE_UNINET = 8;
    public static final int MPROXYTYPE_UNIWAP = 16;
    public static final int MPROXYTYPE_WAP = 64;
    public static final int MPROXYTYPE_WIFI = 2;
    private static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");

    public static byte getApnType(Context context) {
        int netType = getMProxyType(context);
        if (netType == 2) {
            return 3;
        }
        if (netType == 1) {
            return 2;
        }
        if (netType == 4) {
            return 1;
        }
        if (netType == 16) {
            return 5;
        }
        if (netType == 8) {
            return 4;
        }
        if (netType == 64) {
            return 7;
        }
        if (netType == 32) {
            return 6;
        }
        if (netType == 512) {
            return 9;
        }
        if (netType == 256) {
            return 8;
        }
        if (netType == 1024) {
            return 10;
        }
        if (netType == 2048) {
            return APNTYPE_3GNET;
        }
        return 0;
    }

    public static String getApnProxyIp(Context context) {
        byte apnType = getApnType(context);
        if (apnType == 2 || apnType == 5 || apnType == 10) {
            return "10.0.0.172";
        }
        if (apnType == 9) {
            return "10.0.0.200";
        }
        return getApnProxy(context);
    }

    public static String getApnProxy(Context context) {
        Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        c.moveToFirst();
        if (c.isAfterLast()) {
            c.close();
            return null;
        }
        String strResult = c.getString(c.getColumnIndex("proxy"));
        c.close();
        return strResult;
    }

    public static String getApnPort(Context context) {
        Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        c.moveToFirst();
        if (c.isAfterLast()) {
            c.close();
            return "80";
        }
        String port = c.getString(c.getColumnIndex("port"));
        if (port == null) {
            c.close();
            port = "80";
        }
        c.close();
        return port;
    }

    public static int getApnPortInt(Context context) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(PREFERRED_APN_URI, (String[]) null, (String) null, (String[]) null, (String) null);
            c.moveToFirst();
            if (c.isAfterLast()) {
                c.close();
                if (c == null) {
                    return -1;
                }
                c.close();
                return -1;
            }
            int i = c.getInt(c.getColumnIndex("port"));
            if (c == null) {
                return i;
            }
            c.close();
            return i;
        } catch (Exception e) {
            IMLogger.d(" getApnPortInt exception = " + e.getMessage());
            if (c != null) {
                c.close();
            }
            return 0;
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            throw th;
        }
    }

    public static boolean hasProxy(Context context) {
        int netType = getMProxyType(context);
        IMLogger.d("netType:" + netType);
        if (netType == 1 || netType == 16 || netType == 64 || netType == 512 || netType == 1024) {
            return true;
        }
        return false;
    }

    public static int getMProxyType(Context act) {
        NetworkInfo info;
        try {
            ConnectivityManager cm = (ConnectivityManager) act.getSystemService("connectivity");
            if (cm == null || (info = cm.getActiveNetworkInfo()) == null) {
                return 128;
            }
            String typeName = info.getTypeName();
            IMLogger.d("typeName:" + typeName);
            if (typeName.toUpperCase(Locale.CHINA).equals("WIFI")) {
                return 2;
            }
            String extraInfo = info.getExtraInfo().toLowerCase();
            IMLogger.d("extraInfo:" + extraInfo);
            if (extraInfo.startsWith("cmwap")) {
                return 1;
            }
            if (extraInfo.startsWith("cmnet") || extraInfo.startsWith("epc.tmobile.com")) {
                return 4;
            }
            if (extraInfo.startsWith("uniwap")) {
                return 16;
            }
            if (extraInfo.startsWith("uninet")) {
                return 8;
            }
            if (extraInfo.startsWith(ANP_NAME_WAP)) {
                return 64;
            }
            if (extraInfo.startsWith(ANP_NAME_NET)) {
                return 32;
            }
            if (extraInfo.startsWith("ctwap")) {
                return 512;
            }
            if (extraInfo.startsWith("ctnet")) {
                return 256;
            }
            if (extraInfo.startsWith(Apn.APN_3GWAP)) {
                return 1024;
            }
            if (extraInfo.startsWith(Apn.APN_3GNET)) {
                return 2048;
            }
            if (!extraInfo.startsWith(Apn.APN_777)) {
                return 128;
            }
            String proxy = getApnProxy(act);
            if (proxy == null || proxy.length() <= 0) {
                return 256;
            }
            return 512;
        } catch (Exception e) {
            e.printStackTrace();
            return 128;
        }
    }

    public static boolean isNetworkAvailable(Context act) {
        NetworkInfo info;
        ConnectivityManager cm = (ConnectivityManager) act.getSystemService("connectivity");
        if (cm == null || (info = cm.getActiveNetworkInfo()) == null || !info.isAvailable()) {
            return false;
        }
        return true;
    }

    public static boolean isActiveNetworkAvailable(Context ctx) {
        NetworkInfo info = ((ConnectivityManager) ctx.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info != null) {
            return info.isAvailable();
        }
        return false;
    }
}
