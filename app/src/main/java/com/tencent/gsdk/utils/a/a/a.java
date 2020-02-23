package com.tencent.gsdk.utils.a.a;

import java.net.HttpURLConnection;
import java.net.URL;

/* compiled from: URLConnections */
public final class a {
    public static HttpURLConnection a(String str) {
        HttpURLConnection c = c(str);
        c.setRequestMethod("GET");
        return c;
    }

    public static HttpURLConnection b(String str) {
        HttpURLConnection c = c(str);
        c.setRequestMethod("POST");
        return c;
    }

    private static HttpURLConnection c(String str) {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        a(httpURLConnection);
        return httpURLConnection;
    }

    private static void a(HttpURLConnection httpURLConnection) {
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setReadTimeout(5000);
        httpURLConnection.setUseCaches(true);
    }
}
