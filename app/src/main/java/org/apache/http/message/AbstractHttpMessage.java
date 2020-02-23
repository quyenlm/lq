package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;
import org.apache.http.params.HttpParams;

@Deprecated
public abstract class AbstractHttpMessage implements HttpMessage {
    protected HeaderGroup headergroup;
    protected HttpParams params;

    protected AbstractHttpMessage(HttpParams params2) {
        throw new RuntimeException("Stub!");
    }

    protected AbstractHttpMessage() {
        throw new RuntimeException("Stub!");
    }

    public boolean containsHeader(String name) {
        throw new RuntimeException("Stub!");
    }

    public Header[] getHeaders(String name) {
        throw new RuntimeException("Stub!");
    }

    public Header getFirstHeader(String name) {
        throw new RuntimeException("Stub!");
    }

    public Header getLastHeader(String name) {
        throw new RuntimeException("Stub!");
    }

    public Header[] getAllHeaders() {
        throw new RuntimeException("Stub!");
    }

    public void addHeader(Header header) {
        throw new RuntimeException("Stub!");
    }

    public void addHeader(String name, String value) {
        throw new RuntimeException("Stub!");
    }

    public void setHeader(Header header) {
        throw new RuntimeException("Stub!");
    }

    public void setHeader(String name, String value) {
        throw new RuntimeException("Stub!");
    }

    public void setHeaders(Header[] headers) {
        throw new RuntimeException("Stub!");
    }

    public void removeHeader(Header header) {
        throw new RuntimeException("Stub!");
    }

    public void removeHeaders(String name) {
        throw new RuntimeException("Stub!");
    }

    public HeaderIterator headerIterator() {
        throw new RuntimeException("Stub!");
    }

    public HeaderIterator headerIterator(String name) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams getParams() {
        throw new RuntimeException("Stub!");
    }

    public void setParams(HttpParams params2) {
        throw new RuntimeException("Stub!");
    }
}
