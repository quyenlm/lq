package com.subao.common.j;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.subao.common.j.j;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

/* compiled from: NetUtils */
public class k {

    /* compiled from: NetUtils */
    public interface a {
        boolean a(byte[] bArr);
    }

    public static byte[] a(a aVar) {
        return a(new b(), aVar);
    }

    static byte[] a(b bVar, a aVar) {
        try {
            return a(bVar.a(), aVar);
        } catch (SocketException e) {
            return null;
        }
    }

    private static byte[] a(Enumeration<NetworkInterface> enumeration, a aVar) {
        while (enumeration.hasMoreElements()) {
            byte[] a2 = a(aVar, enumeration.nextElement());
            if (a2 != null) {
                return a2;
            }
        }
        return null;
    }

    public static byte[] b(@Nullable a aVar) {
        return a(aVar, new b());
    }

    static byte[] a(@Nullable a aVar, @NonNull b bVar) {
        byte[] a2;
        try {
            Enumeration<NetworkInterface> a3 = bVar.a();
            while (a3.hasMoreElements()) {
                NetworkInterface nextElement = a3.nextElement();
                if (a(nextElement) && (a2 = a(aVar, nextElement)) != null) {
                    return a2;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] a(@Nullable a aVar, @NonNull NetworkInterface networkInterface) {
        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
        while (inetAddresses.hasMoreElements()) {
            InetAddress nextElement = inetAddresses.nextElement();
            if (!nextElement.isLoopbackAddress() && !nextElement.isAnyLocalAddress() && !nextElement.isLinkLocalAddress() && (nextElement instanceof Inet4Address)) {
                byte[] address = nextElement.getAddress();
                if (aVar == null || aVar.a(address)) {
                    return Arrays.copyOf(address, address.length);
                }
            }
        }
        return null;
    }

    private static boolean a(NetworkInterface networkInterface) {
        String name = networkInterface.getName();
        if (name == null) {
            return false;
        }
        String lowerCase = name.toLowerCase();
        if (lowerCase.contains("rmnet") || lowerCase.contains("ccmni")) {
            return true;
        }
        return false;
    }

    @Nullable
    public static String a(Context context, j.a aVar) {
        WifiInfo connectionInfo;
        if (aVar == j.a.DISCONNECT || aVar == j.a.UNKNOWN) {
            return null;
        }
        if (aVar != j.a.WIFI) {
            return a(context);
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
            if (!(wifiManager == null || (connectionInfo = wifiManager.getConnectionInfo()) == null)) {
                return connectionInfo.getSSID();
            }
        } catch (RuntimeException e) {
        }
        return null;
    }

    private static String a(Context context) {
        return null;
    }

    /* compiled from: NetUtils */
    static class b {
        b() {
        }

        /* access modifiers changed from: package-private */
        public Enumeration<NetworkInterface> a() {
            return NetworkInterface.getNetworkInterfaces();
        }
    }
}
