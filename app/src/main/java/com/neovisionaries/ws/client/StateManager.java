package com.neovisionaries.ws.client;

class StateManager {
    private CloseInitiator mCloseInitiator = CloseInitiator.NONE;
    private WebSocketState mState = WebSocketState.CREATED;

    enum CloseInitiator {
        NONE,
        SERVER,
        CLIENT
    }

    public WebSocketState getState() {
        return this.mState;
    }

    public void setState(WebSocketState state) {
        this.mState = state;
    }

    public void changeToClosing(CloseInitiator closeInitiator) {
        this.mState = WebSocketState.CLOSING;
        if (this.mCloseInitiator == CloseInitiator.NONE) {
            this.mCloseInitiator = closeInitiator;
        }
    }

    public boolean getClosedByServer() {
        return this.mCloseInitiator == CloseInitiator.SERVER;
    }
}
