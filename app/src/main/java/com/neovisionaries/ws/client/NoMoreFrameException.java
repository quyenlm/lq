package com.neovisionaries.ws.client;

class NoMoreFrameException extends WebSocketException {
    private static final long serialVersionUID = 1;

    public NoMoreFrameException() {
        super(WebSocketError.NO_MORE_FRAME, "No more WebSocket frame from the server.");
    }
}
