package com.garena.android.gpns.network.exception;

public class UnableToConnectException extends AbstractNetworkException {
    private static final String MESSAGE = "Unable to connect to the server.";

    public UnableToConnectException(Exception exception) {
        super(exception, MESSAGE);
    }
}
