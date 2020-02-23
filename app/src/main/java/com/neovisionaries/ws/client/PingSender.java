package com.neovisionaries.ws.client;

class PingSender extends PeriodicalFrameSender {
    private static final String TIMER_NAME = "PingSender";

    public PingSender(WebSocket webSocket, PayloadGenerator generator) {
        super(webSocket, TIMER_NAME, generator);
    }

    /* access modifiers changed from: protected */
    public WebSocketFrame createFrame(byte[] payload) {
        return WebSocketFrame.createPingFrame(payload);
    }
}
