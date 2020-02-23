package com.garena.android.gpns.network.exception;

public class AbstractNetworkException extends Exception {
    private final String mMessage;

    public AbstractNetworkException(Exception parent, String message) {
        super(parent);
        this.mMessage = message;
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getBaseExceptionMessage() {
        return super.getMessage();
    }
}
