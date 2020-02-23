package com.neovisionaries.ws.client;

import java.util.List;
import java.util.Map;

public interface WebSocketListener {
    void handleCallbackError(WebSocket webSocket, Throwable th) throws Exception;

    void onBinaryFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onBinaryMessage(WebSocket webSocket, byte[] bArr) throws Exception;

    void onCloseFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onConnectError(WebSocket webSocket, WebSocketException webSocketException) throws Exception;

    void onConnected(WebSocket webSocket, Map<String, List<String>> map) throws Exception;

    void onContinuationFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onDisconnected(WebSocket webSocket, WebSocketFrame webSocketFrame, WebSocketFrame webSocketFrame2, boolean z) throws Exception;

    void onError(WebSocket webSocket, WebSocketException webSocketException) throws Exception;

    void onFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onFrameError(WebSocket webSocket, WebSocketException webSocketException, WebSocketFrame webSocketFrame) throws Exception;

    void onFrameSent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onFrameUnsent(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onMessageDecompressionError(WebSocket webSocket, WebSocketException webSocketException, byte[] bArr) throws Exception;

    void onMessageError(WebSocket webSocket, WebSocketException webSocketException, List<WebSocketFrame> list) throws Exception;

    void onPingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onPongFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onSendError(WebSocket webSocket, WebSocketException webSocketException, WebSocketFrame webSocketFrame) throws Exception;

    void onSendingFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onSendingHandshake(WebSocket webSocket, String str, List<String[]> list) throws Exception;

    void onStateChanged(WebSocket webSocket, WebSocketState webSocketState) throws Exception;

    void onTextFrame(WebSocket webSocket, WebSocketFrame webSocketFrame) throws Exception;

    void onTextMessage(WebSocket webSocket, String str) throws Exception;

    void onTextMessageError(WebSocket webSocket, WebSocketException webSocketException, byte[] bArr) throws Exception;

    void onThreadCreated(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception;

    void onThreadStarted(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception;

    void onThreadStopping(WebSocket webSocket, ThreadType threadType, Thread thread) throws Exception;

    void onUnexpectedError(WebSocket webSocket, WebSocketException webSocketException) throws Exception;
}
