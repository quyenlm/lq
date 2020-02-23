package com.tencent.qqgamemi.mgc.connection;

import android.text.TextUtils;
import com.tencent.component.utils.log.LogUtil;
import java.util.List;

public class NetworkAddress {
    private static final String SERAIL_DIVIDER = ":";
    private static final String TAG = "NetworkAddress";
    public String ipAddr;
    public int port;

    public NetworkAddress() {
    }

    public NetworkAddress(String ipAddr2, int port2) {
        this.ipAddr = ipAddr2;
        this.port = port2;
    }

    public String getIpAddr() {
        return this.ipAddr;
    }

    public void setIpAddr(String ipAddr2) {
        this.ipAddr = ipAddr2;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port2) {
        this.port = port2;
    }

    public String toString() {
        return NetworkAddress.class.getSimpleName() + "[" + "IP=" + this.ipAddr + ", port=" + this.port + "]";
    }

    public String serialString() {
        if (TextUtils.isEmpty(this.ipAddr)) {
            return "";
        }
        return this.ipAddr + SERAIL_DIVIDER + this.port;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof NetworkAddress)) {
            return false;
        }
        NetworkAddress other = (NetworkAddress) o;
        if (!other.ipAddr.equals(this.ipAddr) || other.port != this.port) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 0 + this.ipAddr.hashCode() + Integer.valueOf(this.port).hashCode();
    }

    public static void split(List<NetworkAddress> addresses, String[] hosts, int[] ports) {
        for (int i = 0; i < addresses.size(); i++) {
            NetworkAddress address = addresses.get(i);
            hosts[i] = address.ipAddr;
            ports[i] = address.port;
        }
    }

    public static NetworkAddress fromString(String ipAddr2, String proxyStr) {
        if (TextUtils.isEmpty(ipAddr2) || TextUtils.isEmpty(proxyStr)) {
            LogUtil.e(TAG, "NetworkAddress.fromString: null test host");
            return null;
        }
        int port2 = 0;
        if (!TextUtils.isEmpty(proxyStr)) {
            try {
                port2 = Integer.parseInt(proxyStr.trim());
            } catch (NumberFormatException e) {
                LogUtil.e(TAG, "NetworkAddress.fromString: wrong test port[" + proxyStr + "]: " + e);
                return null;
            }
        }
        NetworkAddress address = new NetworkAddress();
        address.ipAddr = ipAddr2;
        address.port = port2;
        return address;
    }

    public static NetworkAddress fromString(String stream) {
        if (TextUtils.isEmpty(stream)) {
            return null;
        }
        String[] fields = stream.split(SERAIL_DIVIDER);
        if (fields.length != 2) {
            return null;
        }
        String ipAddr2 = fields[0];
        if (TextUtils.isEmpty(ipAddr2)) {
            return null;
        }
        try {
            int port2 = Integer.parseInt(fields[1]);
            NetworkAddress address = new NetworkAddress();
            address.ipAddr = ipAddr2;
            address.port = port2;
            return address;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
