package com.garena.android.gpns.network.exception;

public class ConnectionDroppedException extends AbstractNetworkException {
    private static final String MESSAGE = "TCP Socket connection was unexpectedly closed.";

    public ConnectionDroppedException(Exception parent) {
        super(parent, MESSAGE);
    }
}
