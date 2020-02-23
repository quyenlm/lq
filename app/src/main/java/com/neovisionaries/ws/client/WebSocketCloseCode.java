package com.neovisionaries.ws.client;

public class WebSocketCloseCode {
    public static final int ABNORMAL = 1006;
    public static final int AWAY = 1001;
    public static final int INCONSISTENT = 1007;
    public static final int INSECURE = 1015;
    public static final int NONE = 1005;
    public static final int NORMAL = 1000;
    public static final int OVERSIZE = 1009;
    public static final int UNACCEPTABLE = 1003;
    public static final int UNCONFORMED = 1002;
    public static final int UNEXPECTED = 1011;
    public static final int UNEXTENDED = 1010;
    public static final int VIOLATED = 1008;

    private WebSocketCloseCode() {
    }
}
