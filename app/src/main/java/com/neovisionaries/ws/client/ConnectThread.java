package com.neovisionaries.ws.client;

class ConnectThread extends WebSocketThread {
    public ConnectThread(WebSocket ws) {
        super("ConnectThread", ws, ThreadType.CONNECT_THREAD);
    }

    public void runMain() {
        try {
            this.mWebSocket.connect();
        } catch (WebSocketException e) {
            handleError(e);
        }
    }

    private void handleError(WebSocketException cause) {
        ListenerManager manager = this.mWebSocket.getListenerManager();
        manager.callOnError(cause);
        manager.callOnConnectError(cause);
    }
}
