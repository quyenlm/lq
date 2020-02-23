package com.tencent.qt.alg.network;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public interface NetworkSensor {
    public static final String INVALID_ACCESS_POINT = "None";
    public static final int ISP_CHINA_MOBILE = 1;
    public static final int ISP_CHINA_TELECOM = 3;
    public static final int ISP_CHINA_UNICOM = 2;
    public static final int ISP_UNKNOWN = 0;
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
    public static final int NETWORK_TYPE_WIFI = 1119;

    String getAccessPoint();

    int getISP();

    InetAddress getIp();

    int getNetworkDetailType();

    String getNetworkType();

    InetSocketAddress getProxy();

    String getService();

    boolean hasAvailableNetwork();
}
