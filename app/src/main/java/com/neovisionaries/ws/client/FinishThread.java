package com.neovisionaries.ws.client;

class FinishThread extends WebSocketThread {
    public FinishThread(WebSocket ws) {
        super("FinishThread", ws, ThreadType.FINISH_THREAD);
    }

    public void runMain() {
        this.mWebSocket.finish();
    }
}
