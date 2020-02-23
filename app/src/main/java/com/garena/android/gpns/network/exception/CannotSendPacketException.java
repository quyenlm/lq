package com.garena.android.gpns.network.exception;

public class CannotSendPacketException extends AbstractNetworkException {
    private static final String MESSAGE = "Unable to send data on TCP Socket.";

    public CannotSendPacketException(Exception parent) {
        super(parent, MESSAGE);
    }
}
