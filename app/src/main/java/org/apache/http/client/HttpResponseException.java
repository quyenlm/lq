package org.apache.http.client;

@Deprecated
public class HttpResponseException extends ClientProtocolException {
    public HttpResponseException(int statusCode, String s) {
        throw new RuntimeException("Stub!");
    }

    public int getStatusCode() {
        throw new RuntimeException("Stub!");
    }
}
