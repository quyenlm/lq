package com.amazonaws.http;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import com.tencent.imsdk.tool.net.AsyncHttpClient;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestFactory {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public HttpRequest createHttpRequest(Request<?> request, ClientConfiguration clientConfiguration, ExecutionContext context) {
        String len;
        String uri = HttpUtils.appendUri(request.getEndpoint().toString(), request.getResourcePath(), true);
        String encodedParams = HttpUtils.encodeParameters(request);
        HttpMethodName method = request.getHttpMethod();
        boolean putParamsInUri = !(method == HttpMethodName.POST) || (request.getContent() != null);
        if (encodedParams != null && putParamsInUri) {
            uri = uri + "?" + encodedParams;
        }
        Map<String, String> headers = new HashMap<>();
        configureHeaders(headers, request, context, clientConfiguration);
        InputStream is = request.getContent();
        if (method == HttpMethodName.PATCH) {
            method = HttpMethodName.POST;
            headers.put("X-HTTP-Method-Override", HttpMethodName.PATCH.toString());
        }
        if (method == HttpMethodName.POST && request.getContent() == null && encodedParams != null) {
            byte[] contentBytes = encodedParams.getBytes(StringUtils.UTF8);
            is = new ByteArrayInputStream(contentBytes);
            headers.put("Content-Length", String.valueOf(contentBytes.length));
        }
        if ((method == HttpMethodName.POST || method == HttpMethodName.PUT) && ((len = headers.get("Content-Length")) == null || len.isEmpty())) {
            if (is != null) {
                throw new AmazonClientException("Unknown content-length");
            }
            headers.put("Content-Length", "0");
        }
        if (headers.get("Accept-Encoding") == null) {
            headers.put("Accept-Encoding", AsyncHttpClient.ENCODING_GZIP);
        }
        return new HttpRequest(method.toString(), URI.create(uri), headers, is);
    }

    private void configureHeaders(Map<String, String> headers, Request<?> request, ExecutionContext context, ClientConfiguration clientConfiguration) {
        URI endpoint = request.getEndpoint();
        String hostHeader = endpoint.getHost();
        if (HttpUtils.isUsingNonDefaultPort(endpoint)) {
            hostHeader = hostHeader + ":" + endpoint.getPort();
        }
        headers.put("Host", hostHeader);
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }
        if (headers.get("Content-Type") == null || headers.get("Content-Type").isEmpty()) {
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=" + StringUtils.lowerCase("UTF-8"));
        }
        if (context != null && context.getContextUserAgent() != null) {
            headers.put("User-Agent", createUserAgentString(clientConfiguration, context.getContextUserAgent()));
        }
    }

    private String createUserAgentString(ClientConfiguration clientConfiguration, String contextUserAgent) {
        if (clientConfiguration.getUserAgent().contains(contextUserAgent)) {
            return clientConfiguration.getUserAgent();
        }
        return clientConfiguration.getUserAgent() + " " + contextUserAgent;
    }
}
