package com.amazonaws.http;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.http.HttpResponse;
import com.vk.sdk.api.VKApiConst;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class ApacheHttpClient implements HttpClient {
    private final HttpClient httpClient;
    private HttpParams params = null;

    public ApacheHttpClient(ClientConfiguration config) {
        this.httpClient = new HttpClientFactory().createHttpClient(config);
        ((AbstractHttpClient) this.httpClient).setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        ((SSLSocketFactory) this.httpClient.getConnectionManager().getSchemeRegistry().getScheme(VKApiConst.HTTPS).getSocketFactory()).setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public HttpResponse execute(HttpRequest request) throws IOException {
        HttpResponse httpResponse = this.httpClient.execute(createHttpRequest(request));
        String statusText = httpResponse.getStatusLine().getReasonPhrase();
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        InputStream content = null;
        if (httpResponse.getEntity() != null) {
            content = httpResponse.getEntity().getContent();
        }
        HttpResponse.Builder builder = HttpResponse.builder().statusCode(statusCode).statusText(statusText).content(content);
        for (Header header : httpResponse.getAllHeaders()) {
            builder.header(header.getName(), header.getValue());
        }
        return builder.build();
    }

    public void shutdown() {
        this.httpClient.getConnectionManager().shutdown();
    }

    private HttpUriRequest createHttpRequest(HttpRequest request) {
        HttpUriRequest httpRequest;
        String method = request.getMethod();
        if (method.equals("POST")) {
            HttpPost postRequest = new HttpPost(request.getUri());
            if (request.getContent() != null) {
                postRequest.setEntity(new InputStreamEntity(request.getContent(), request.getContentLength()));
            }
            httpRequest = postRequest;
        } else if (method.equals("GET")) {
            httpRequest = new HttpGet(request.getUri());
        } else if (method.equals(HttpPut.METHOD_NAME)) {
            HttpPut putRequest = new HttpPut(request.getUri());
            if (request.getContent() != null) {
                putRequest.setEntity(new InputStreamEntity(request.getContent(), request.getContentLength()));
            }
            httpRequest = putRequest;
        } else if (method.equals(HttpDelete.METHOD_NAME)) {
            httpRequest = new HttpDelete(request.getUri());
        } else if (method.equals(HttpHead.METHOD_NAME)) {
            httpRequest = new HttpHead(request.getUri());
        } else {
            throw new UnsupportedOperationException("Unsupported method: " + method);
        }
        if (request.getHeaders() != null && !request.getHeaders().isEmpty()) {
            for (Map.Entry<String, String> header : request.getHeaders().entrySet()) {
                String key = header.getKey();
                if (!key.equals("Content-Length") && !key.equals("Host")) {
                    httpRequest.addHeader(header.getKey(), header.getValue());
                }
            }
        }
        if (this.params == null) {
            this.params = new BasicHttpParams();
            this.params.setParameter(ClientPNames.HANDLE_REDIRECTS, false);
        }
        httpRequest.setParams(this.params);
        return httpRequest;
    }
}
