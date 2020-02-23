package com.neovisionaries.ws.client;

class PongSender extends PeriodicalFrameSender {
    private static final String TIMER_NAME = "PongSender";

    public PongSender(WebSocket webSocket, PayloadGenerator generator) {
        super(webSocket, TIMER_NAME, generator);
    }

    /* access modifiers changed from: protected */
    public WebSocketFrame createFrame(byte[] payload) {
        return WebSocketFrame.createPongFrame(payload);
    }
}
