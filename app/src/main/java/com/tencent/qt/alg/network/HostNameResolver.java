package com.tencent.qt.alg.network;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HostNameResolver {
    private static final boolean NEED_HOST_RESOVLING = false;
    private static Map<String, String> hostMap = null;
    private static Random mRandom = new Random();

    public static boolean isNeedHostResovling() {
        return false;
    }

    public static void addNameMap(String server, String ip) {
        if (hostMap == null) {
            hostMap = new HashMap();
        }
        String sip = hostMap.get(server);
        if (sip == null) {
            hostMap.put(server, ip);
            return;
        }
        hostMap.put(server, sip + ";" + ip);
    }

    public static String resovleHost(String server) {
        String ip;
        if (!isNeedHostResovling() || hostMap == null || hostMap.size() == 0 || (ip = hostMap.get(server)) == null) {
            return server;
        }
        String[] ips = ip.split(";");
        return ips[(mRandom.nextInt() * 1000000) % ips.length];
    }

    public static String resovleURL(String url) {
        if (!isNeedHostResovling() || hostMap == null || hostMap.size() == 0) {
            return url;
        }
        String str = url;
        try {
            String host = new URL(url).getHost();
            String ip = hostMap.get(host);
            if (ip == null) {
                return str;
            }
            String[] ips = ip.split(";");
            return url.replace(host, ips[(mRandom.nextInt() * 1000000) % ips.length]);
        } catch (MalformedURLException e) {
            return str;
        }
    }
}
