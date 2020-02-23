package com.beetalk.sdk.networking;

import com.beetalk.sdk.helper.BBLogger;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class HttpFactory {
    public static final int CONNECT_TIMEOUT = 60000;
    public static final int READ_TIMEOUT = 60000;

    public static HttpURLConnection newConnection(String urlStr) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
            if (conn instanceof HttpsURLConnection) {
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init((KeyManager[]) null, (TrustManager[]) null, new SecureRandom());
                ((HttpsURLConnection) conn).setSSLSocketFactory(sc.getSocketFactory());
            }
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setRequestProperty("User-Agent", new GarenaUserAgent().toString());
            conn.setInstanceFollowRedirects(false);
            return conn;
        } catch (MalformedURLException e) {
            BBLogger.e(e);
            return null;
        } catch (IOException e2) {
            BBLogger.e(e2);
            return null;
        } catch (NoSuchAlgorithmException e3) {
            BBLogger.e(e3);
            return null;
        } catch (KeyManagementException e4) {
            BBLogger.e(e4);
            return null;
        }
    }
}
