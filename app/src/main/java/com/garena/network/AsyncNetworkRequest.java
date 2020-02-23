package com.garena.network;

import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.networking.HttpParam;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncNetworkRequest {
    private HashMap<String, String> customHeaders = new HashMap<>();
    /* access modifiers changed from: private */
    public List<HttpParam> httpParams;
    /* access modifiers changed from: private */
    public String requestMethod;
    /* access modifiers changed from: private */
    public URL uri;

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public URL getUri() {
        return this.uri;
    }

    public List<HttpParam> getHttpParams() {
        if (this.httpParams == null) {
            return new ArrayList();
        }
        return this.httpParams;
    }

    public void addHeader(String headerName, String headerValue) {
        this.customHeaders.put(headerName, headerValue);
    }

    public Map<String, String> getHeaders() {
        return this.customHeaders;
    }

    public String getQuery() throws UnsupportedEncodingException {
        return StringUtils.httpParamsToString(getHttpParams());
    }

    public static final class RequestBuilder {
        private List<HttpParam> httpParams;
        private String requestMethod;
        private URL uri;

        private static String getFieldName(Field field) {
            HTTPRequestParam thisField;
            if (field.getAnnotations() == null || (thisField = (HTTPRequestParam) field.getAnnotation(HTTPRequestParam.class)) == null) {
                return "";
            }
            return thisField.fieldName();
        }

        public RequestBuilder setRequestMethod(String requestMethod2) {
            this.requestMethod = requestMethod2;
            return this;
        }

        public RequestBuilder addHttpParam(Field field, String value) {
            if (!StringUtils.isEmpty(getFieldName(field))) {
                return addHttpParam("field,", "field");
            }
            throw new RuntimeException("HTTP Param Should have a name");
        }

        public RequestBuilder addHttpParam(String key, String value) {
            if (this.httpParams == null) {
                this.httpParams = new ArrayList();
            }
            this.httpParams.add(new HttpParam(key, value));
            return this;
        }

        public RequestBuilder setUri(URL uri2) {
            this.uri = uri2;
            return this;
        }

        public AsyncNetworkRequest build() {
            AsyncNetworkRequest networkRequest = new AsyncNetworkRequest();
            List unused = networkRequest.httpParams = this.httpParams;
            URL unused2 = networkRequest.uri = this.uri;
            String unused3 = networkRequest.requestMethod = this.requestMethod;
            return networkRequest;
        }
    }
}
