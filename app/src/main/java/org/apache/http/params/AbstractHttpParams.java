package org.apache.http.params;

@Deprecated
public abstract class AbstractHttpParams implements HttpParams {
    protected AbstractHttpParams() {
        throw new RuntimeException("Stub!");
    }

    public long getLongParameter(String name, long defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams setLongParameter(String name, long value) {
        throw new RuntimeException("Stub!");
    }

    public int getIntParameter(String name, int defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams setIntParameter(String name, int value) {
        throw new RuntimeException("Stub!");
    }

    public double getDoubleParameter(String name, double defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams setDoubleParameter(String name, double value) {
        throw new RuntimeException("Stub!");
    }

    public boolean getBooleanParameter(String name, boolean defaultValue) {
        throw new RuntimeException("Stub!");
    }

    public HttpParams setBooleanParameter(String name, boolean value) {
        throw new RuntimeException("Stub!");
    }

    public boolean isParameterTrue(String name) {
        throw new RuntimeException("Stub!");
    }

    public boolean isParameterFalse(String name) {
        throw new RuntimeException("Stub!");
    }
}
