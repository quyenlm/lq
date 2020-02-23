package com.facebook.places.internal;

public class ScannerException extends Exception {
    public Type type;

    public enum Type {
        NOT_SUPPORTED,
        PERMISSION_DENIED,
        DISABLED,
        SCAN_ALREADY_IN_PROGRESS,
        UNKNOWN_ERROR,
        TIMEOUT
    }

    public ScannerException(Type type2) {
        super("Type: " + type2.name());
        this.type = type2;
    }

    public ScannerException(Type type2, String message) {
        super(message);
        this.type = type2;
    }

    public ScannerException(Type type2, Exception ex) {
        super("Type: " + type2.name(), ex);
        this.type = type2;
    }
}
