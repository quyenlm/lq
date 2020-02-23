package com.android.internal.http.multipart;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.http.Header;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.params.HttpParams;

public class MultipartEntity extends AbstractHttpEntity {
    public static final String MULTIPART_BOUNDARY = "http.method.multipart.boundary";
    protected Part[] parts = null;

    public MultipartEntity(Part[] parts2, HttpParams params) {
        throw new RuntimeException("Stub!");
    }

    public MultipartEntity(Part[] parts2) {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public byte[] getMultipartBoundary() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRepeatable() {
        throw new RuntimeException("Stub!");
    }

    public void writeTo(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public Header getContentType() {
        throw new RuntimeException("Stub!");
    }

    public long getContentLength() {
        throw new RuntimeException("Stub!");
    }

    public InputStream getContent() throws IOException, IllegalStateException {
        throw new RuntimeException("Stub!");
    }

    public boolean isStreaming() {
        throw new RuntimeException("Stub!");
    }
}
