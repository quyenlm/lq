package com.neovisionaries.ws.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class ListenerManager {
    private List<WebSocketListener> mCopiedListeners;
    private final List<WebSocketListener> mListeners = new ArrayList();
    private boolean mSyncNeeded = true;
    private final WebSocket mWebSocket;

    public ListenerManager(WebSocket websocket) {
        this.mWebSocket = websocket;
    }

    public List<WebSocketListener> getListeners() {
        return this.mListeners;
    }

    public void addListener(WebSocketListener listener) {
        if (listener != null) {
            synchronized (this.mListeners) {
                this.mListeners.add(listener);
                this.mSyncNeeded = true;
            }
        }
    }

    public void addListeners(List<WebSocketListener> listeners) {
        if (listeners != null) {
            synchronized (this.mListeners) {
                for (WebSocketListener listener : listeners) {
                    if (listener != null) {
                        this.mListeners.add(listener);
                        this.mSyncNeeded = true;
                    }
                }
            }
        }
    }

    public void removeListener(WebSocketListener listener) {
        if (listener != null) {
            synchronized (this.mListeners) {
                if (this.mListeners.remove(listener)) {
                    this.mSyncNeeded = true;
                }
            }
        }
    }

    public void removeListeners(List<WebSocketListener> listeners) {
        if (listeners != null) {
            synchronized (this.mListeners) {
                for (WebSocketListener listener : listeners) {
                    if (listener != null && this.mListeners.remove(listener)) {
                        this.mSyncNeeded = true;
                    }
                }
            }
        }
    }

    public void clearListeners() {
        synchronized (this.mListeners) {
            if (this.mListeners.size() != 0) {
                this.mListeners.clear();
                this.mSyncNeeded = true;
            }
        }
    }

    private List<WebSocketListener> getSynchronizedListeners() {
        List<WebSocketListener> copiedListeners;
        synchronized (this.mListeners) {
            if (!this.mSyncNeeded) {
                copiedListeners = this.mCopiedListeners;
            } else {
                copiedListeners = new ArrayList<>(this.mListeners.size());
                for (WebSocketListener listener : this.mListeners) {
                    copiedListeners.add(listener);
                }
                this.mCopiedListeners = copiedListeners;
                this.mSyncNeeded = false;
            }
        }
        return copiedListeners;
    }

    public void callOnStateChanged(WebSocketState newState) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onStateChanged(this.mWebSocket, newState);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnConnected(Map<String, List<String>> headers) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onConnected(this.mWebSocket, headers);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnConnectError(WebSocketException cause) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onConnectError(this.mWebSocket, cause);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnDisconnected(WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onDisconnected(this.mWebSocket, serverCloseFrame, clientCloseFrame, closedByServer);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnContinuationFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onContinuationFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnTextFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onTextFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnBinaryFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onBinaryFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnCloseFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onCloseFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnPingFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onPingFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnPongFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onPongFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnTextMessage(String message) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onTextMessage(this.mWebSocket, message);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnBinaryMessage(byte[] message) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onBinaryMessage(this.mWebSocket, message);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnSendingFrame(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onSendingFrame(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnFrameSent(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onFrameSent(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnFrameUnsent(WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onFrameUnsent(this.mWebSocket, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnThreadCreated(ThreadType threadType, Thread thread) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onThreadCreated(this.mWebSocket, threadType, thread);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnThreadStarted(ThreadType threadType, Thread thread) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onThreadStarted(this.mWebSocket, threadType, thread);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnThreadStopping(ThreadType threadType, Thread thread) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onThreadStopping(this.mWebSocket, threadType, thread);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnError(WebSocketException cause) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onError(this.mWebSocket, cause);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnFrameError(WebSocketException cause, WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onFrameError(this.mWebSocket, cause, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnMessageError(WebSocketException cause, List<WebSocketFrame> frames) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onMessageError(this.mWebSocket, cause, frames);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnMessageDecompressionError(WebSocketException cause, byte[] compressed) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onMessageDecompressionError(this.mWebSocket, cause, compressed);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnTextMessageError(WebSocketException cause, byte[] data) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onTextMessageError(this.mWebSocket, cause, data);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnSendError(WebSocketException cause, WebSocketFrame frame) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onSendError(this.mWebSocket, cause, frame);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    public void callOnUnexpectedError(WebSocketException cause) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onUnexpectedError(this.mWebSocket, cause);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }

    private void callHandleCallbackError(WebSocketListener listener, Throwable cause) {
        try {
            listener.handleCallbackError(this.mWebSocket, cause);
        } catch (Throwable th) {
        }
    }

    public void callOnSendingHandshake(String requestLine, List<String[]> headers) {
        for (WebSocketListener listener : getSynchronizedListeners()) {
            try {
                listener.onSendingHandshake(this.mWebSocket, requestLine, headers);
            } catch (Throwable t) {
                callHandleCallbackError(listener, t);
            }
        }
    }
}
