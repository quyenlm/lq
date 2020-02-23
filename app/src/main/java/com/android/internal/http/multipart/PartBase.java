package com.android.internal.http.multipart;

public abstract class PartBase extends Part {
    public PartBase(String name, String contentType, String charSet, String transferEncoding) {
        throw new RuntimeException("Stub!");
    }

    public String getName() {
        throw new RuntimeException("Stub!");
    }

    public String getContentType() {
        throw new RuntimeException("Stub!");
    }

    public String getCharSet() {
        throw new RuntimeException("Stub!");
    }

    public String getTransferEncoding() {
        throw new RuntimeException("Stub!");
    }

    public void setCharSet(String charSet) {
        throw new RuntimeException("Stub!");
    }

    public void setContentType(String contentType) {
        throw new RuntimeException("Stub!");
    }

    public void setName(String name) {
        throw new RuntimeException("Stub!");
    }

    public void setTransferEncoding(String transferEncoding) {
        throw new RuntimeException("Stub!");
    }
}
