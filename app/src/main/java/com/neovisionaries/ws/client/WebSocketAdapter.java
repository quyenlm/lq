package com.neovisionaries.ws.client;

import java.util.List;
import java.util.Map;

public class WebSocketAdapter implements WebSocketListener {
    public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
    }

    public void onConnected(WebSocket websocket, Map<String, List<String>> map) throws Exception {
    }

    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
    }

    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
    }

    public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onTextMessage(WebSocket websocket, String text) throws Exception {
    }

    public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
    }

    public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {
    }

    public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
    }

    public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
    }

    public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> list) throws Exception {
    }

    public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {
    }

    public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
    }

    public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
    }

    public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
    }

    public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
    }

    public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> list) throws Exception {
    }

    public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
    }

    public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
    }

    public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
    }
}
