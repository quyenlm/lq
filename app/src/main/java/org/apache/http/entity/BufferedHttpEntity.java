package org.apache.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.HttpEntity;

@Deprecated
public class BufferedHttpEntity extends HttpEntityWrapper {
    public BufferedHttpEntity(HttpEntity entity) throws IOException {
        super((HttpEntity) null);
        throw new RuntimeException("Stub!");
    }

    public long getContentLength() {
        throw new RuntimeException("Stub!");
    }

    public InputStream getContent() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isChunked() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRepeatable() {
        throw new RuntimeException("Stub!");
    }

    public void writeTo(OutputStream outstream) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public boolean isStreaming() {
        throw new RuntimeException("Stub!");
    }
}
