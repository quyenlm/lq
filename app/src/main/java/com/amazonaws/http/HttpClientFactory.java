package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.impl.client.HttpRequestNoRetryHandler;
import com.amazonaws.http.impl.client.SdkHttpClient;
import com.vk.sdk.api.VKApiConst;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

class HttpClientFactory {
    HttpClientFactory() {
    }

    public HttpClient createHttpClient(ClientConfiguration config) {
        HttpParams httpClientParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpClientParams, config.getConnectionTimeout());
        HttpConnectionParams.setSoTimeout(httpClientParams, config.getSocketTimeout());
        HttpConnectionParams.setStaleCheckingEnabled(httpClientParams, true);
        HttpConnectionParams.setTcpNoDelay(httpClientParams, true);
        int socketSendBufferSizeHint = config.getSocketBufferSizeHints()[0];
        int socketReceiveBufferSizeHint = config.getSocketBufferSizeHints()[1];
        if (socketSendBufferSizeHint > 0 || socketReceiveBufferSizeHint > 0) {
            HttpConnectionParams.setSocketBufferSize(httpClientParams, Math.max(socketSendBufferSizeHint, socketReceiveBufferSizeHint));
        }
        ThreadSafeClientConnManager connectionManager = ConnectionManagerFactory.createThreadSafeClientConnManager(config, httpClientParams);
        SdkHttpClient httpClient = new SdkHttpClient(connectionManager, httpClientParams);
        httpClient.setHttpRequestRetryHandler(HttpRequestNoRetryHandler.Singleton);
        httpClient.setRedirectHandler(new LocationHeaderNotRequiredRedirectHandler());
        if (config.getLocalAddress() != null) {
            ConnRouteParams.setLocalAddress(httpClientParams, config.getLocalAddress());
        }
        Scheme http = new Scheme(HttpHost.DEFAULT_SCHEME_NAME, PlainSocketFactory.getSocketFactory(), 80);
        SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
        Scheme https = new Scheme(VKApiConst.HTTPS, sslSocketFactory, 443);
        SchemeRegistry sr = connectionManager.getSchemeRegistry();
        sr.register(http);
        sr.register(https);
        String proxyHost = config.getProxyHost();
        int proxyPort = config.getProxyPort();
        if (proxyHost != null && proxyPort > 0) {
            AmazonHttpClient.log.info("Configuring Proxy. Proxy Host: " + proxyHost + " Proxy Port: " + proxyPort);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(proxyHost, proxyPort));
            String proxyUsername = config.getProxyUsername();
            String proxyPassword = config.getProxyPassword();
            String proxyDomain = config.getProxyDomain();
            String proxyWorkstation = config.getProxyWorkstation();
            if (!(proxyUsername == null || proxyPassword == null)) {
                httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort), new NTCredentials(proxyUsername, proxyPassword, proxyWorkstation, proxyDomain));
            }
        }
        return httpClient;
    }

    private static final class LocationHeaderNotRequiredRedirectHandler extends DefaultRedirectHandler {
        private LocationHeaderNotRequiredRedirectHandler() {
        }

        public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (response.getFirstHeader("location") == null && statusCode == 301) {
                return false;
            }
            return super.isRedirectRequested(response, context);
        }
    }
}
