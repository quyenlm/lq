package android.net.http;

public class LoggingEventHandler implements EventHandler {
    public LoggingEventHandler() {
        throw new RuntimeException("Stub!");
    }

    public void requestSent() {
        throw new RuntimeException("Stub!");
    }

    public void status(int major_version, int minor_version, int code, String reason_phrase) {
        throw new RuntimeException("Stub!");
    }

    public void headers(Headers headers) {
        throw new RuntimeException("Stub!");
    }

    public void locationChanged(String newLocation, boolean permanent) {
        throw new RuntimeException("Stub!");
    }

    public void data(byte[] data, int len) {
        throw new RuntimeException("Stub!");
    }

    public void endData() {
        throw new RuntimeException("Stub!");
    }

    public void certificate(SslCertificate certificate) {
        throw new RuntimeException("Stub!");
    }

    public void error(int id, String description) {
        throw new RuntimeException("Stub!");
    }

    public boolean handleSslErrorRequest(SslError error) {
        throw new RuntimeException("Stub!");
    }
}
