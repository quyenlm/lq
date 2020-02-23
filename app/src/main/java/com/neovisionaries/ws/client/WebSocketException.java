package com.neovisionaries.ws.client;

public class WebSocketException extends Exception {
    private static final long serialVersionUID = 1;
    private final WebSocketError mError;

    public WebSocketException(WebSocketError error) {
        this.mError = error;
    }

    public WebSocketException(WebSocketError error, String message) {
        super(message);
        this.mError = error;
    }

    public WebSocketException(WebSocketError error, Throwable cause) {
        super(cause);
        this.mError = error;
    }

    public WebSocketException(WebSocketError error, String message, Throwable cause) {
        super(message, cause);
        this.mError = error;
    }

    public WebSocketError getError() {
        return this.mError;
    }
}
