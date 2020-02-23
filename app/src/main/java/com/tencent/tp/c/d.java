package com.tencent.tp.c;

import com.vk.sdk.api.VKApiConst;
import org.apache.http.HttpHost;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

class d {
    private static HttpClient a;

    private d() {
    }

    public static synchronized HttpClient a() {
        HttpClient httpClient;
        synchronized (d.class) {
            if (a == null) {
                BasicHttpParams basicHttpParams = new BasicHttpParams();
                HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);
                HttpProtocolParams.setContentCharset(basicHttpParams, "ISO-8859-1");
                HttpProtocolParams.setUseExpectContinue(basicHttpParams, true);
                HttpProtocolParams.setUserAgent(basicHttpParams, "Android Http Agent");
                ConnManagerParams.setTimeout(basicHttpParams, 1000);
                HttpConnectionParams.setConnectionTimeout(basicHttpParams, 5000);
                HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
                SchemeRegistry schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme(VKApiConst.HTTPS, PlainSocketFactory.getSocketFactory(), 443));
                a = new DefaultHttpClient(new ThreadSafeClientConnManager(basicHttpParams, schemeRegistry), basicHttpParams);
            }
            httpClient = a;
        }
        return httpClient;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
