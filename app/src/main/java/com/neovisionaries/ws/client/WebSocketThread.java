package com.neovisionaries.ws.client;

abstract class WebSocketThread extends Thread {
    private final ThreadType mThreadType;
    protected final WebSocket mWebSocket;

    /* access modifiers changed from: protected */
    public abstract void runMain();

    WebSocketThread(String name, WebSocket ws, ThreadType type) {
        super(name);
        this.mWebSocket = ws;
        this.mThreadType = type;
    }

    public void run() {
        ListenerManager lm = this.mWebSocket.getListenerManager();
        if (lm != null) {
            lm.callOnThreadStarted(this.mThreadType, this);
        }
        runMain();
        if (lm != null) {
            lm.callOnThreadStopping(this.mThreadType, this);
        }
    }

    public void callOnThreadCreated() {
        ListenerManager lm = this.mWebSocket.getListenerManager();
        if (lm != null) {
            lm.callOnThreadCreated(this.mThreadType, this);
        }
    }
}
