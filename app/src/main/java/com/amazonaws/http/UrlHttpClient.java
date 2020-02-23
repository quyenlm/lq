package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.HttpResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.http.client.methods.HttpHead;

public class UrlHttpClient implements HttpClient {
    private final ClientConfiguration config;
    private SSLContext sc = null;

    public UrlHttpClient(ClientConfiguration config2) {
        this.config = config2;
    }

    public HttpResponse execute(HttpRequest request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) request.getUri().toURL().openConnection();
        configureConnection(connection);
        applyHeadersAndMethod(request, connection);
        writeContentToConnection(request, connection);
        return createHttpResponse(request, connection);
    }

    /* access modifiers changed from: package-private */
    public HttpResponse createHttpResponse(HttpRequest request, HttpURLConnection connection) throws IOException {
        String statusText = connection.getResponseMessage();
        int statusCode = connection.getResponseCode();
        InputStream content = connection.getErrorStream();
        if (content == null && !request.getMethod().equals(HttpHead.METHOD_NAME)) {
            try {
                content = connection.getInputStream();
            } catch (IOException e) {
            }
        }
        HttpResponse.Builder builder = HttpResponse.builder().statusCode(statusCode).statusText(statusText).content(content);
        for (Map.Entry<String, List<String>> header : connection.getHeaderFields().entrySet()) {
            if (header.getKey() != null) {
                builder.header(header.getKey(), (String) header.getValue().get(0));
            }
        }
        return builder.build();
    }

    public void shutdown() {
    }

    /* access modifiers changed from: package-private */
    public void writeContentToConnection(HttpRequest request, HttpURLConnection connection) throws IOException {
        if (request.getContent() != null && request.getContentLength() >= 0) {
            connection.setDoOutput(true);
            connection.setFixedLengthStreamingMode((int) request.getContentLength());
            OutputStream os = connection.getOutputStream();
            write(request.getContent(), os);
            os.flush();
            os.close();
        }
    }

    /* access modifiers changed from: package-private */
    public HttpURLConnection applyHeadersAndMethod(HttpRequest request, HttpURLConnection connection) throws ProtocolException {
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                String key = header.getKey();
                if (!key.equals("Content-Length") && !key.equals("Host")) {
                    if (key.equals("Expect")) {
                    }
                    connection.setRequestProperty(key, header.getValue());
                }
            }
        }
        connection.setRequestMethod(request.getMethod());
        return connection;
    }

    private void write(InputStream is, OutputStream os) throws IOException {
        byte[] buf = new byte[8192];
        while (true) {
            int len = is.read(buf);
            if (len != -1) {
                os.write(buf, 0, len);
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void configureConnection(HttpURLConnection connection) {
        connection.setConnectTimeout(this.config.getConnectionTimeout());
        connection.setReadTimeout(this.config.getSocketTimeout());
        connection.setInstanceFollowRedirects(false);
        connection.setUseCaches(false);
        if (connection instanceof HttpsURLConnection) {
            HttpsURLConnection https = (HttpsURLConnection) connection;
            if (this.config.getTrustManager() != null) {
                enableCustomTrustManager(https);
            }
        }
    }

    private void enableCustomTrustManager(HttpsURLConnection connection) {
        if (this.sc == null) {
            TrustManager[] customTrustManagers = {this.config.getTrustManager()};
            try {
                this.sc = SSLContext.getInstance("TLS");
                this.sc.init((KeyManager[]) null, customTrustManagers, (SecureRandom) null);
            } catch (GeneralSecurityException e) {
                throw new RuntimeException(e);
            }
        }
        connection.setSSLSocketFactory(this.sc.getSocketFactory());
    }
}
