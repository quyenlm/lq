package com.tencent.qt.alg.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.tencent.qt.base.net.PLog;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SmartNetworkSensor implements NetworkSensor {
    private ConnectivityManager connectivityManager;
    private TelephonyManager telephonyManager;

    public static String getMobileNetworkType(int type, int subtype) {
        if (type == 1) {
            return "WIFI";
        }
        if (type != 0) {
            return "2G";
        }
        switch (subtype) {
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
                return "3G";
            default:
                return "2G";
        }
    }

    public static int getMobileNetworkDetailType(int type, int subtype) {
        if (type == 1) {
            return NetworkSensor.NETWORK_TYPE_WIFI;
        }
        if (type != 0) {
            return 0;
        }
        return subtype;
    }

    public SmartNetworkSensor(Context context) {
        this.connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.telephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public String getAccessPoint() {
        String mobiName;
        NetworkInfo info = this.connectivityManager.getActiveNetworkInfo();
        if (info == null || info.getState() != NetworkInfo.State.CONNECTED) {
            return NetworkSensor.INVALID_ACCESS_POINT;
        }
        String networkType = info.getTypeName();
        if (networkType == null || networkType.equalsIgnoreCase("WIFI") || (mobiName = info.getExtraInfo()) == null || mobiName.equals("")) {
            return networkType;
        }
        return mobiName;
    }

    public String getNetworkType() {
        NetworkInfo info = this.connectivityManager.getActiveNetworkInfo();
        if (info == null || info.getState() != NetworkInfo.State.CONNECTED) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        return getMobileNetworkType(info.getType(), this.telephonyManager.getNetworkType());
    }

    public int getNetworkDetailType() {
        NetworkInfo info = this.connectivityManager.getActiveNetworkInfo();
        if (info == null || info.getState() != NetworkInfo.State.CONNECTED) {
            return 0;
        }
        return getMobileNetworkDetailType(info.getType(), this.telephonyManager.getNetworkType());
    }

    public int getISP() {
        String IMSI = this.telephonyManager.getSubscriberId();
        if (IMSI == null) {
            IMSI = "";
        }
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            return 1;
        }
        if (IMSI.startsWith("46001")) {
            return 2;
        }
        if (IMSI.startsWith("46003")) {
            return 3;
        }
        return 0;
    }

    public InetSocketAddress getProxy() {
        return null;
    }

    public InetAddress getIp() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en != null && en.hasMoreElements()) {
                Enumeration<InetAddress> addresses = en.nextElement().getInetAddresses();
                while (addresses != null && addresses.hasMoreElements()) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            PLog.printStackTrace(ex);
        }
        return null;
    }

    public String getService() {
        String imsi = this.telephonyManager.getSubscriberId();
        if (imsi == null) {
            return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
        }
        if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
            return "中国移动";
        }
        if (imsi.startsWith("46001")) {
            return "中国联通";
        }
        if (imsi.startsWith("46003")) {
            return "中国电信";
        }
        return AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
    }

    public boolean hasAvailableNetwork() {
        String apn = getAccessPoint();
        if (TextUtils.isEmpty(apn) || apn.equals(NetworkSensor.INVALID_ACCESS_POINT)) {
            return false;
        }
        return true;
    }
}
