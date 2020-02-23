package com.android.internal.http.multipart;

import java.io.IOException;
import java.io.OutputStream;

public abstract class Part {
    @Deprecated
    protected static final String BOUNDARY = "----------------314159265358979323846";
    @Deprecated
    protected static final byte[] BOUNDARY_BYTES = null;
    protected static final String CHARSET = "; charset=";
    protected static final byte[] CHARSET_BYTES = null;
    protected static final String CONTENT_DISPOSITION = "Content-Disposition: form-data; name=";
    protected static final byte[] CONTENT_DISPOSITION_BYTES = null;
    protected static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
    protected static final byte[] CONTENT_TRANSFER_ENCODING_BYTES = null;
    protected static final String CONTENT_TYPE = "Content-Type: ";
    protected static final byte[] CONTENT_TYPE_BYTES = null;
    protected static final String CRLF = "\r\n";
    protected static final byte[] CRLF_BYTES = null;
    protected static final String EXTRA = "--";
    protected static final byte[] EXTRA_BYTES = null;
    protected static final String QUOTE = "\"";
    protected static final byte[] QUOTE_BYTES = null;

    public abstract String getCharSet();

    public abstract String getContentType();

    public abstract String getName();

    public abstract String getTransferEncoding();

    /* access modifiers changed from: protected */
    public abstract long lengthOfData() throws IOException;

    /* access modifiers changed from: protected */
    public abstract void sendData(OutputStream outputStream) throws IOException;

    public Part() {
        throw new RuntimeException("Stub!");
    }

    @Deprecated
    public static String getBoundary() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public byte[] getPartBoundary() {
        throw new RuntimeException("Stub!");
    }

    public boolean isRepeatable() {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendStart(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendDispositionHeader(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendContentTypeHeader(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendTransferEncodingHeader(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendEndOfHeader(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    /* access modifiers changed from: protected */
    public void sendEnd(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public void send(OutputStream out) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public long length() throws IOException {
        throw new RuntimeException("Stub!");
    }

    public String toString() {
        throw new RuntimeException("Stub!");
    }

    public static void sendParts(OutputStream out, Part[] parts) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public static void sendParts(OutputStream out, Part[] parts, byte[] partBoundary) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public static long getLengthOfParts(Part[] parts) throws IOException {
        throw new RuntimeException("Stub!");
    }

    public static long getLengthOfParts(Part[] parts, byte[] partBoundary) throws IOException {
        throw new RuntimeException("Stub!");
    }
}
