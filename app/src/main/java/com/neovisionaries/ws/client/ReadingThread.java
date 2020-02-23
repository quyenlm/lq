package com.neovisionaries.ws.client;

import com.neovisionaries.ws.client.StateManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class ReadingThread extends WebSocketThread {
    private long mCloseDelay;
    private WebSocketFrame mCloseFrame;
    private Object mCloseLock = new Object();
    private CloseTask mCloseTask;
    private Timer mCloseTimer;
    private List<WebSocketFrame> mContinuation = new ArrayList();
    private boolean mNotWaitForCloseFrame;
    private final PerMessageCompressionExtension mPMCE;
    private boolean mStopRequested;

    public ReadingThread(WebSocket websocket) {
        super("ReadingThread", websocket, ThreadType.READING_THREAD);
        this.mPMCE = websocket.getPerMessageCompressionExtension();
    }

    public void runMain() {
        try {
            main();
        } catch (Throwable t) {
            WebSocketException cause = new WebSocketException(WebSocketError.UNEXPECTED_ERROR_IN_READING_THREAD, "An uncaught throwable was detected in the reading thread: " + t.getMessage(), t);
            ListenerManager manager = this.mWebSocket.getListenerManager();
            manager.callOnError(cause);
            manager.callOnUnexpectedError(cause);
        }
        notifyFinished();
    }

    private void main() {
        this.mWebSocket.onReadingThreadStarted();
        while (true) {
            synchronized (this) {
                if (!this.mStopRequested) {
                    WebSocketFrame frame = readFrame();
                    if (frame != null) {
                        if (!handleFrame(frame)) {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        waitForCloseFrame();
        cancelClose();
    }

    /* access modifiers changed from: package-private */
    public void requestStop(long closeDelay) {
        synchronized (this) {
            if (!this.mStopRequested) {
                this.mStopRequested = true;
                interrupt();
                this.mCloseDelay = closeDelay;
                scheduleClose();
            }
        }
    }

    private void callOnFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnFrame(frame);
    }

    private void callOnContinuationFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnContinuationFrame(frame);
    }

    private void callOnTextFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnTextFrame(frame);
    }

    private void callOnBinaryFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnBinaryFrame(frame);
    }

    private void callOnCloseFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnCloseFrame(frame);
    }

    private void callOnPingFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnPingFrame(frame);
    }

    private void callOnPongFrame(WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnPongFrame(frame);
    }

    private void callOnTextMessage(byte[] data) {
        try {
            callOnTextMessage(Misc.toStringUTF8(data));
        } catch (Throwable t) {
            WebSocketException wse = new WebSocketException(WebSocketError.TEXT_MESSAGE_CONSTRUCTION_ERROR, "Failed to convert payload data into a string: " + t.getMessage(), t);
            callOnError(wse);
            callOnTextMessageError(wse, data);
        }
    }

    private void callOnTextMessage(String message) {
        this.mWebSocket.getListenerManager().callOnTextMessage(message);
    }

    private void callOnBinaryMessage(byte[] message) {
        this.mWebSocket.getListenerManager().callOnBinaryMessage(message);
    }

    private void callOnError(WebSocketException cause) {
        this.mWebSocket.getListenerManager().callOnError(cause);
    }

    private void callOnFrameError(WebSocketException cause, WebSocketFrame frame) {
        this.mWebSocket.getListenerManager().callOnFrameError(cause, frame);
    }

    private void callOnMessageError(WebSocketException cause, List<WebSocketFrame> frames) {
        this.mWebSocket.getListenerManager().callOnMessageError(cause, frames);
    }

    private void callOnMessageDecompressionError(WebSocketException cause, byte[] compressed) {
        this.mWebSocket.getListenerManager().callOnMessageDecompressionError(cause, compressed);
    }

    private void callOnTextMessageError(WebSocketException cause, byte[] data) {
        this.mWebSocket.getListenerManager().callOnTextMessageError(cause, data);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.neovisionaries.ws.client.WebSocketFrame readFrame() {
        /*
            r10 = this;
            r6 = 0
            r3 = 0
            r5 = 0
            com.neovisionaries.ws.client.WebSocket r7 = r10.mWebSocket     // Catch:{ InterruptedIOException -> 0x0013, IOException -> 0x005c, WebSocketException -> 0x0088 }
            com.neovisionaries.ws.client.WebSocketInputStream r7 = r7.getInput()     // Catch:{ InterruptedIOException -> 0x0013, IOException -> 0x005c, WebSocketException -> 0x0088 }
            com.neovisionaries.ws.client.WebSocketFrame r3 = r7.readFrame()     // Catch:{ InterruptedIOException -> 0x0013, IOException -> 0x005c, WebSocketException -> 0x0088 }
            r10.verifyFrame(r3)     // Catch:{ InterruptedIOException -> 0x0013, IOException -> 0x005c, WebSocketException -> 0x0088 }
            r4 = r3
            r6 = r3
        L_0x0012:
            return r6
        L_0x0013:
            r1 = move-exception
            boolean r7 = r10.mStopRequested
            if (r7 == 0) goto L_0x001a
            r4 = r3
            goto L_0x0012
        L_0x001a:
            com.neovisionaries.ws.client.WebSocketException r5 = new com.neovisionaries.ws.client.WebSocketException
            com.neovisionaries.ws.client.WebSocketError r7 = com.neovisionaries.ws.client.WebSocketError.INTERRUPTED_IN_READING
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Interruption occurred while a frame was being read from the web socket: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r1.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r5.<init>(r7, r8, r1)
        L_0x0038:
            r2 = 1
            boolean r7 = r5 instanceof com.neovisionaries.ws.client.NoMoreFrameException
            if (r7 == 0) goto L_0x0049
            r7 = 1
            r10.mNotWaitForCloseFrame = r7
            com.neovisionaries.ws.client.WebSocket r7 = r10.mWebSocket
            boolean r7 = r7.isMissingCloseFrameAllowed()
            if (r7 == 0) goto L_0x0049
            r2 = 0
        L_0x0049:
            if (r2 == 0) goto L_0x0051
            r10.callOnError(r5)
            r10.callOnFrameError(r5, r3)
        L_0x0051:
            com.neovisionaries.ws.client.WebSocketFrame r0 = r10.createCloseFrame(r5)
            com.neovisionaries.ws.client.WebSocket r7 = r10.mWebSocket
            r7.sendFrame(r0)
            r4 = r3
            goto L_0x0012
        L_0x005c:
            r1 = move-exception
            boolean r7 = r10.mStopRequested
            if (r7 == 0) goto L_0x0069
            boolean r7 = r10.isInterrupted()
            if (r7 == 0) goto L_0x0069
            r4 = r3
            goto L_0x0012
        L_0x0069:
            com.neovisionaries.ws.client.WebSocketException r5 = new com.neovisionaries.ws.client.WebSocketException
            com.neovisionaries.ws.client.WebSocketError r7 = com.neovisionaries.ws.client.WebSocketError.IO_ERROR_IN_READING
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "An I/O error occurred while a frame was being read from the web socket: "
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r9 = r1.getMessage()
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.String r8 = r8.toString()
            r5.<init>(r7, r8, r1)
            goto L_0x0038
        L_0x0088:
            r1 = move-exception
            r5 = r1
            goto L_0x0038
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.ReadingThread.readFrame():com.neovisionaries.ws.client.WebSocketFrame");
    }

    private void verifyFrame(WebSocketFrame frame) throws WebSocketException {
        verifyReservedBits(frame);
        verifyFrameOpcode(frame);
        verifyFrameMask(frame);
        verifyFrameFragmentation(frame);
        verifyFrameSize(frame);
    }

    private void verifyReservedBits(WebSocketFrame frame) throws WebSocketException {
        if (!this.mWebSocket.isExtended()) {
            verifyReservedBit1(frame);
            verifyReservedBit2(frame);
            verifyReservedBit3(frame);
        }
    }

    private void verifyReservedBit1(WebSocketFrame frame) throws WebSocketException {
        if ((this.mPMCE == null || !verifyReservedBit1ForPMCE(frame)) && frame.getRsv1()) {
            throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV1 bit of a frame is set unexpectedly.");
        }
    }

    private boolean verifyReservedBit1ForPMCE(WebSocketFrame frame) throws WebSocketException {
        if (frame.isTextFrame() || frame.isBinaryFrame()) {
            return true;
        }
        return false;
    }

    private void verifyReservedBit2(WebSocketFrame frame) throws WebSocketException {
        if (frame.getRsv2()) {
            throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV2 bit of a frame is set unexpectedly.");
        }
    }

    private void verifyReservedBit3(WebSocketFrame frame) throws WebSocketException {
        if (frame.getRsv3()) {
            throw new WebSocketException(WebSocketError.UNEXPECTED_RESERVED_BIT, "The RSV3 bit of a frame is set unexpectedly.");
        }
    }

    private void verifyFrameOpcode(WebSocketFrame frame) throws WebSocketException {
        switch (frame.getOpcode()) {
            case 0:
            case 1:
            case 2:
            case 8:
            case 9:
            case 10:
                return;
            default:
                if (!this.mWebSocket.isExtended()) {
                    throw new WebSocketException(WebSocketError.UNKNOWN_OPCODE, "A frame has an unknown opcode: 0x" + Integer.toHexString(frame.getOpcode()));
                }
                return;
        }
    }

    private void verifyFrameMask(WebSocketFrame frame) throws WebSocketException {
        if (frame.getMask()) {
            throw new WebSocketException(WebSocketError.FRAME_MASKED, "A frame from the server is masked.");
        }
    }

    private void verifyFrameFragmentation(WebSocketFrame frame) throws WebSocketException {
        if (!frame.isControlFrame()) {
            boolean continuationExists = this.mContinuation.size() != 0;
            if (frame.isContinuationFrame()) {
                if (!continuationExists) {
                    throw new WebSocketException(WebSocketError.UNEXPECTED_CONTINUATION_FRAME, "A continuation frame was detected although a continuation had not started.");
                }
            } else if (continuationExists) {
                throw new WebSocketException(WebSocketError.CONTINUATION_NOT_CLOSED, "A non-control frame was detected although the existing continuation had not been closed.");
            }
        } else if (!frame.getFin()) {
            throw new WebSocketException(WebSocketError.FRAGMENTED_CONTROL_FRAME, "A control frame is fragmented.");
        }
    }

    private void verifyFrameSize(WebSocketFrame frame) throws WebSocketException {
        byte[] payload;
        if (frame.isControlFrame() && (payload = frame.getPayload()) != null && 125 < payload.length) {
            throw new WebSocketException(WebSocketError.TOO_LONG_CONTROL_FRAME_PAYLOAD, "The payload size of a control frame exceeds the maximum size (125 bytes): " + payload.length);
        }
    }

    private WebSocketFrame createCloseFrame(WebSocketException wse) {
        int closeCode;
        switch (wse.getError()) {
            case INSUFFICENT_DATA:
            case INVALID_PAYLOAD_LENGTH:
            case NO_MORE_FRAME:
                closeCode = 1002;
                break;
            case TOO_LONG_PAYLOAD:
            case INSUFFICIENT_MEMORY_FOR_PAYLOAD:
                closeCode = 1009;
                break;
            case NON_ZERO_RESERVED_BITS:
            case UNEXPECTED_RESERVED_BIT:
            case UNKNOWN_OPCODE:
            case FRAME_MASKED:
            case FRAGMENTED_CONTROL_FRAME:
            case UNEXPECTED_CONTINUATION_FRAME:
            case CONTINUATION_NOT_CLOSED:
            case TOO_LONG_CONTROL_FRAME_PAYLOAD:
                closeCode = 1002;
                break;
            case INTERRUPTED_IN_READING:
            case IO_ERROR_IN_READING:
                closeCode = 1008;
                break;
            default:
                closeCode = 1008;
                break;
        }
        return WebSocketFrame.createCloseFrame(closeCode, wse.getMessage());
    }

    private boolean handleFrame(WebSocketFrame frame) {
        callOnFrame(frame);
        switch (frame.getOpcode()) {
            case 0:
                return handleContinuationFrame(frame);
            case 1:
                return handleTextFrame(frame);
            case 2:
                return handleBinaryFrame(frame);
            case 8:
                return handleCloseFrame(frame);
            case 9:
                return handlePingFrame(frame);
            case 10:
                return handlePongFrame(frame);
            default:
                return true;
        }
    }

    private boolean handleContinuationFrame(WebSocketFrame frame) {
        callOnContinuationFrame(frame);
        this.mContinuation.add(frame);
        if (!frame.getFin()) {
            return true;
        }
        byte[] data = getMessage(this.mContinuation);
        if (data == null) {
            return false;
        }
        if (this.mContinuation.get(0).isTextFrame()) {
            callOnTextMessage(data);
        } else {
            callOnBinaryMessage(data);
        }
        this.mContinuation.clear();
        return true;
    }

    private byte[] getMessage(List<WebSocketFrame> frames) {
        byte[] data = concatenatePayloads(this.mContinuation);
        if (data == null) {
            byte[] bArr = data;
            return null;
        }
        if (this.mPMCE != null && frames.get(0).getRsv1()) {
            data = decompress(data);
        }
        byte[] bArr2 = data;
        return data;
    }

    private byte[] concatenatePayloads(List<WebSocketFrame> frames) {
        Throwable cause;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (WebSocketFrame frame : frames) {
                byte[] payload = frame.getPayload();
                if (!(payload == null || payload.length == 0)) {
                    baos.write(payload);
                }
            }
            return baos.toByteArray();
        } catch (IOException e) {
            cause = e;
        } catch (OutOfMemoryError e2) {
            cause = e2;
        }
        WebSocketException wse = new WebSocketException(WebSocketError.MESSAGE_CONSTRUCTION_ERROR, "Failed to concatenate payloads of multiple frames to construct a message: " + cause.getMessage(), cause);
        callOnError(wse);
        callOnMessageError(wse, frames);
        this.mWebSocket.sendFrame(WebSocketFrame.createCloseFrame(1009, wse.getMessage()));
        return null;
    }

    private byte[] getMessage(WebSocketFrame frame) {
        byte[] payload = frame.getPayload();
        if (this.mPMCE == null || !frame.getRsv1()) {
            return payload;
        }
        return decompress(payload);
    }

    private byte[] decompress(byte[] input) {
        try {
            return this.mPMCE.decompress(input);
        } catch (WebSocketException e) {
            WebSocketException wse = e;
            callOnError(wse);
            callOnMessageDecompressionError(wse, input);
            this.mWebSocket.sendFrame(WebSocketFrame.createCloseFrame(1003, wse.getMessage()));
            return null;
        }
    }

    private boolean handleTextFrame(WebSocketFrame frame) {
        callOnTextFrame(frame);
        if (!frame.getFin()) {
            this.mContinuation.add(frame);
        } else {
            callOnTextMessage(getMessage(frame));
        }
        return true;
    }

    private boolean handleBinaryFrame(WebSocketFrame frame) {
        callOnBinaryFrame(frame);
        if (!frame.getFin()) {
            this.mContinuation.add(frame);
        } else {
            callOnBinaryMessage(getMessage(frame));
        }
        return true;
    }

    private boolean handleCloseFrame(WebSocketFrame frame) {
        StateManager manager = this.mWebSocket.getStateManager();
        this.mCloseFrame = frame;
        boolean stateChanged = false;
        synchronized (manager) {
            WebSocketState state = manager.getState();
            if (!(state == WebSocketState.CLOSING || state == WebSocketState.CLOSED)) {
                manager.changeToClosing(StateManager.CloseInitiator.SERVER);
                this.mWebSocket.sendFrame(frame);
                stateChanged = true;
            }
        }
        if (stateChanged) {
            this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
        }
        callOnCloseFrame(frame);
        return false;
    }

    private boolean handlePingFrame(WebSocketFrame frame) {
        callOnPingFrame(frame);
        this.mWebSocket.sendFrame(WebSocketFrame.createPongFrame(frame.getPayload()));
        return true;
    }

    private boolean handlePongFrame(WebSocketFrame frame) {
        callOnPongFrame(frame);
        return true;
    }

    private void waitForCloseFrame() {
        if (!this.mNotWaitForCloseFrame && this.mCloseFrame == null) {
            scheduleClose();
            do {
                try {
                    WebSocketFrame frame = this.mWebSocket.getInput().readFrame();
                    if (frame.isCloseFrame()) {
                        this.mCloseFrame = frame;
                        return;
                    }
                } catch (Throwable th) {
                    return;
                }
            } while (!isInterrupted());
        }
    }

    private void notifyFinished() {
        this.mWebSocket.onReadingThreadFinished(this.mCloseFrame);
    }

    private void scheduleClose() {
        synchronized (this.mCloseLock) {
            cancelCloseTask();
            scheduleCloseTask();
        }
    }

    private void scheduleCloseTask() {
        this.mCloseTask = new CloseTask();
        this.mCloseTimer = new Timer("ReadingThreadCloseTimer");
        this.mCloseTimer.schedule(this.mCloseTask, this.mCloseDelay);
    }

    private void cancelClose() {
        synchronized (this.mCloseLock) {
            cancelCloseTask();
        }
    }

    private void cancelCloseTask() {
        if (this.mCloseTimer != null) {
            this.mCloseTimer.cancel();
            this.mCloseTimer = null;
        }
        if (this.mCloseTask != null) {
            this.mCloseTask.cancel();
            this.mCloseTask = null;
        }
    }

    private class CloseTask extends TimerTask {
        private CloseTask() {
        }

        public void run() {
            try {
                ReadingThread.this.mWebSocket.getSocket().close();
            } catch (Throwable th) {
            }
        }
    }
}
