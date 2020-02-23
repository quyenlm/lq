package com.neovisionaries.ws.client;

import com.neovisionaries.ws.client.StateManager;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

class WritingThread extends WebSocketThread {
    private static final int FLUSH_THRESHOLD = 1000;
    private static final int SHOULD_CONTINUE = 2;
    private static final int SHOULD_FLUSH = 3;
    private static final int SHOULD_SEND = 0;
    private static final int SHOULD_STOP = 1;
    private WebSocketFrame mCloseFrame;
    private boolean mFlushNeeded;
    private final LinkedList<WebSocketFrame> mFrames = new LinkedList<>();
    private final PerMessageCompressionExtension mPMCE;
    private boolean mStopRequested;
    private boolean mStopped;

    public WritingThread(WebSocket websocket) {
        super("WritingThread", websocket, ThreadType.WRITING_THREAD);
        this.mPMCE = websocket.getPerMessageCompressionExtension();
    }

    public void runMain() {
        try {
            main();
        } catch (Throwable t) {
            WebSocketException cause = new WebSocketException(WebSocketError.UNEXPECTED_ERROR_IN_WRITING_THREAD, "An uncaught throwable was detected in the writing thread: " + t.getMessage(), t);
            ListenerManager manager = this.mWebSocket.getListenerManager();
            manager.callOnError(cause);
            manager.callOnUnexpectedError(cause);
        }
        synchronized (this) {
            this.mStopped = true;
            notifyAll();
        }
        notifyFinished();
    }

    private void main() {
        this.mWebSocket.onWritingThreadStarted();
        while (true) {
            int result = waitForFrames();
            if (result == 1) {
                break;
            } else if (result == 3) {
                flushIgnoreError();
            } else if (result != 2) {
                try {
                    sendFrames(false);
                } catch (WebSocketException e) {
                }
            }
        }
        try {
            sendFrames(true);
        } catch (WebSocketException e2) {
        }
    }

    public void requestStop() {
        synchronized (this) {
            this.mStopRequested = true;
            notifyAll();
        }
    }

    public boolean queueFrame(WebSocketFrame frame) {
        int queueSize;
        synchronized (this) {
            while (!this.mStopped) {
                if (this.mStopRequested || this.mCloseFrame != null || frame.isControlFrame() || (queueSize = this.mWebSocket.getFrameQueueSize()) == 0 || this.mFrames.size() < queueSize) {
                    if (isHighPriorityFrame(frame)) {
                        addHighPriorityFrame(frame);
                    } else {
                        this.mFrames.addLast(frame);
                    }
                    notifyAll();
                    return true;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            return false;
        }
    }

    private static boolean isHighPriorityFrame(WebSocketFrame frame) {
        return frame.isPingFrame() || frame.isPongFrame();
    }

    private void addHighPriorityFrame(WebSocketFrame frame) {
        int index = 0;
        Iterator it = this.mFrames.iterator();
        while (it.hasNext() && isHighPriorityFrame((WebSocketFrame) it.next())) {
            index++;
        }
        this.mFrames.add(index, frame);
    }

    public void queueFlush() {
        synchronized (this) {
            this.mFlushNeeded = true;
            notifyAll();
        }
    }

    private void flushIgnoreError() {
        try {
            flush();
        } catch (IOException e) {
        }
    }

    private void flush() throws IOException {
        this.mWebSocket.getOutput().flush();
    }

    private int waitForFrames() {
        synchronized (this) {
            if (this.mStopRequested) {
                return 1;
            }
            if (this.mCloseFrame != null) {
                return 1;
            }
            if (this.mFrames.size() == 0) {
                if (this.mFlushNeeded) {
                    this.mFlushNeeded = false;
                    return 3;
                }
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            if (this.mStopRequested) {
                return 1;
            }
            if (this.mFrames.size() != 0) {
                return 0;
            }
            if (!this.mFlushNeeded) {
                return 2;
            }
            this.mFlushNeeded = false;
            return 3;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001e, code lost:
        sendFrame(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        if (r0.isPingFrame() != false) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        if (r0.isPongFrame() == false) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x002d, code lost:
        doFlush();
        r2 = java.lang.System.currentTimeMillis();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003c, code lost:
        if (isFlushNeeded(r5) == false) goto L_0x0004;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003e, code lost:
        r2 = flushIfLongInterval(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0017, code lost:
        if (isFlushNeeded(r5) == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0019, code lost:
        doFlush();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendFrames(boolean r5) throws com.neovisionaries.ws.client.WebSocketException {
        /*
            r4 = this;
            long r2 = java.lang.System.currentTimeMillis()
        L_0x0004:
            monitor-enter(r4)
            java.util.LinkedList<com.neovisionaries.ws.client.WebSocketFrame> r1 = r4.mFrames     // Catch:{ all -> 0x0035 }
            java.lang.Object r0 = r1.poll()     // Catch:{ all -> 0x0035 }
            com.neovisionaries.ws.client.WebSocketFrame r0 = (com.neovisionaries.ws.client.WebSocketFrame) r0     // Catch:{ all -> 0x0035 }
            r4.notifyAll()     // Catch:{ all -> 0x0035 }
            if (r0 != 0) goto L_0x001d
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            boolean r1 = r4.isFlushNeeded(r5)
            if (r1 == 0) goto L_0x001c
            r4.doFlush()
        L_0x001c:
            return
        L_0x001d:
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            r4.sendFrame(r0)
            boolean r1 = r0.isPingFrame()
            if (r1 != 0) goto L_0x002d
            boolean r1 = r0.isPongFrame()
            if (r1 == 0) goto L_0x0038
        L_0x002d:
            r4.doFlush()
            long r2 = java.lang.System.currentTimeMillis()
            goto L_0x0004
        L_0x0035:
            r1 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0035 }
            throw r1
        L_0x0038:
            boolean r1 = r4.isFlushNeeded(r5)
            if (r1 == 0) goto L_0x0004
            long r2 = r4.flushIfLongInterval(r2)
            goto L_0x0004
        */
        throw new UnsupportedOperationException("Method not decompiled: com.neovisionaries.ws.client.WritingThread.sendFrames(boolean):void");
    }

    private boolean isFlushNeeded(boolean last) {
        return last || this.mWebSocket.isAutoFlush() || this.mFlushNeeded || this.mCloseFrame != null;
    }

    private long flushIfLongInterval(long lastFlushAt) throws WebSocketException {
        long current = System.currentTimeMillis();
        if (1000 >= current - lastFlushAt) {
            return lastFlushAt;
        }
        doFlush();
        return current;
    }

    private void doFlush() throws WebSocketException {
        try {
            flush();
            synchronized (this) {
                this.mFlushNeeded = false;
            }
        } catch (IOException e) {
            WebSocketException cause = new WebSocketException(WebSocketError.FLUSH_ERROR, "Flushing frames to the server failed: " + e.getMessage(), e);
            ListenerManager manager = this.mWebSocket.getListenerManager();
            manager.callOnError(cause);
            manager.callOnSendError(cause, (WebSocketFrame) null);
            throw cause;
        }
    }

    private void sendFrame(WebSocketFrame frame) throws WebSocketException {
        WebSocketFrame frame2 = WebSocketFrame.compressFrame(frame, this.mPMCE);
        this.mWebSocket.getListenerManager().callOnSendingFrame(frame2);
        boolean unsent = false;
        if (this.mCloseFrame != null) {
            unsent = true;
        } else if (frame2.isCloseFrame()) {
            this.mCloseFrame = frame2;
        }
        if (unsent) {
            this.mWebSocket.getListenerManager().callOnFrameUnsent(frame2);
            return;
        }
        if (frame2.isCloseFrame()) {
            changeToClosing();
        }
        try {
            this.mWebSocket.getOutput().write(frame2);
            this.mWebSocket.getListenerManager().callOnFrameSent(frame2);
        } catch (IOException e) {
            WebSocketException cause = new WebSocketException(WebSocketError.IO_ERROR_IN_WRITING, "An I/O error occurred when a frame was tried to be sent: " + e.getMessage(), e);
            ListenerManager manager = this.mWebSocket.getListenerManager();
            manager.callOnError(cause);
            manager.callOnSendError(cause, frame2);
            throw cause;
        }
    }

    private void changeToClosing() {
        StateManager manager = this.mWebSocket.getStateManager();
        boolean stateChanged = false;
        synchronized (manager) {
            WebSocketState state = manager.getState();
            if (!(state == WebSocketState.CLOSING || state == WebSocketState.CLOSED)) {
                manager.changeToClosing(StateManager.CloseInitiator.CLIENT);
                stateChanged = true;
            }
        }
        if (stateChanged) {
            this.mWebSocket.getListenerManager().callOnStateChanged(WebSocketState.CLOSING);
        }
    }

    private void notifyFinished() {
        this.mWebSocket.onWritingThreadFinished(this.mCloseFrame);
    }
}
