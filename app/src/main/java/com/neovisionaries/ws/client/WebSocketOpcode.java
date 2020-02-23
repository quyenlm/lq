package com.neovisionaries.ws.client;

public class WebSocketOpcode {
    public static final int BINARY = 2;
    public static final int CLOSE = 8;
    public static final int CONTINUATION = 0;
    public static final int PING = 9;
    public static final int PONG = 10;
    public static final int TEXT = 1;

    private WebSocketOpcode() {
    }
}
