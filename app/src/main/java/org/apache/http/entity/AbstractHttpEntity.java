package org.apache.http.entity;

import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

@Deprecated
public abstract class AbstractHttpEntity implements HttpEntity {
    protected boolean chunked;
    protected Header contentEncoding;
    protected Header contentType;

    protected AbstractHttpEntity() {
        throw new RuntimeException("Stub!");
    }

    public Header getContentType() {
        throw new RuntimeException("Stub!");
    }

    public Header getContentEncoding() {
        throw new RuntimeException("Stub!");
    }

    public boolean isChunked() {
        throw new RuntimeException("Stub!");
    }

    public void setContentType(Header contentType2) {
        throw new RuntimeException("Stub!");
    }

    public void setContentType(String ctString) {
        throw new RuntimeException("Stub!");
    }

    public void setContentEncoding(Header contentEncoding2) {
        throw new RuntimeException("Stub!");
    }

    public void setContentEncoding(String ceString) {
        throw new RuntimeException("Stub!");
    }

    public void setChunked(boolean b) {
        throw new RuntimeException("Stub!");
    }

    public void consumeContent() throws IOException, UnsupportedOperationException {
        throw new RuntimeException("Stub!");
    }
}
