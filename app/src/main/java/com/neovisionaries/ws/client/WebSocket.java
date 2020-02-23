package com.neovisionaries.ws.client;

import com.neovisionaries.ws.client.StateManager;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class WebSocket {
    private static final long DEFAULT_CLOSE_DELAY = 10000;
    private List<WebSocketExtension> mAgreedExtensions;
    private String mAgreedProtocol;
    private boolean mAutoFlush = true;
    private WebSocketFrame mClientCloseFrame;
    private boolean mExtended;
    private int mFrameQueueSize;
    private HandshakeBuilder mHandshakeBuilder;
    private WebSocketInputStream mInput;
    private final ListenerManager mListenerManager;
    private int mMaxPayloadSize;
    private boolean mMissingCloseFrameAllowed = true;
    private boolean mOnConnectedCalled;
    private Object mOnConnectedCalledLock = new Object();
    private WebSocketOutputStream mOutput;
    private PerMessageCompressionExtension mPerMessageCompressionExtension;
    private final PingSender mPingSender;
    private final PongSender mPongSender;
    private ReadingThread mReadingThread;
    private boolean mReadingThreadFinished;
    private boolean mReadingThreadStarted;
    private WebSocketFrame mServerCloseFrame;
    private Map<String, List<String>> mServerHeaders;
    private final SocketConnector mSocketConnector;
    private final StateManager mStateManager;
    private final Object mThreadsLock = new Object();
    private final WebSocketFactory mWebSocketFactory;
    private WritingThread mWritingThread;
    private boolean mWritingThreadFinished;
    private boolean mWritingThreadStarted;

    WebSocket(WebSocketFactory factory, boolean secure, String userInfo, String host, String path, SocketConnector connector) {
        this.mWebSocketFactory = factory;
        this.mSocketConnector = connector;
        this.mStateManager = new StateManager();
        this.mHandshakeBuilder = new HandshakeBuilder(secure, userInfo, host, path);
        this.mListenerManager = new ListenerManager(this);
        this.mPingSender = new PingSender(this, new CounterPayloadGenerator());
        this.mPongSender = new PongSender(this, new CounterPayloadGenerator());
    }

    public WebSocket recreate() throws IOException {
        return recreate(this.mSocketConnector.getConnectionTimeout());
    }

    public WebSocket recreate(int timeout) throws IOException {
        if (timeout < 0) {
            throw new IllegalArgumentException("The given timeout value is negative.");
        }
        WebSocket instance = this.mWebSocketFactory.createSocket(getURI(), timeout);
        instance.mHandshakeBuilder = new HandshakeBuilder(this.mHandshakeBuilder);
        instance.setPingInterval(getPingInterval());
        instance.setPongInterval(getPongInterval());
        instance.setPingPayloadGenerator(getPingPayloadGenerator());
        instance.setPongPayloadGenerator(getPongPayloadGenerator());
        instance.mExtended = this.mExtended;
        instance.mAutoFlush = this.mAutoFlush;
        instance.mMissingCloseFrameAllowed = this.mMissingCloseFrameAllowed;
        instance.mFrameQueueSize = this.mFrameQueueSize;
        List<WebSocketListener> listeners = this.mListenerManager.getListeners();
        synchronized (listeners) {
            instance.addListeners(listeners);
        }
        return instance;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (isInState(WebSocketState.CREATED)) {
            finish();
        }
        super.finalize();
    }

    public WebSocketState getState() {
        WebSocketState state;
        synchronized (this.mStateManager) {
            state = this.mStateManager.getState();
        }
        return state;
    }

    public boolean isOpen() {
        return isInState(WebSocketState.OPEN);
    }

    private boolean isInState(WebSocketState state) {
        boolean z;
        synchronized (this.mStateManager) {
            z = this.mStateManager.getState() == state;
        }
        return z;
    }

    public WebSocket addProtocol(String protocol) {
        this.mHandshakeBuilder.addProtocol(protocol);
        return this;
    }

    public WebSocket removeProtocol(String protocol) {
        this.mHandshakeBuilder.removeProtocol(protocol);
        return this;
    }

    public WebSocket clearProtocols() {
        this.mHandshakeBuilder.clearProtocols();
        return this;
    }

    public WebSocket addExtension(WebSocketExtension extension) {
        this.mHandshakeBuilder.addExtension(extension);
        return this;
    }

    public WebSocket addExtension(String extension) {
        this.mHandshakeBuilder.addExtension(extension);
        return this;
    }

    public WebSocket removeExtension(WebSocketExtension extension) {
        this.mHandshakeBuilder.removeExtension(extension);
        return this;
    }

    public WebSocket removeExtensions(String name) {
        this.mHandshakeBuilder.removeExtensions(name);
        return this;
    }

    public WebSocket clearExtensions() {
        this.mHandshakeBuilder.clearExtensions();
        return this;
    }

    public WebSocket addHeader(String name, String value) {
        this.mHandshakeBuilder.addHeader(name, value);
        return this;
    }

    public WebSocket removeHeaders(String name) {
        this.mHandshakeBuilder.removeHeaders(name);
        return this;
    }

    public WebSocket clearHeaders() {
        this.mHandshakeBuilder.clearHeaders();
        return this;
    }

    public WebSocket setUserInfo(String userInfo) {
        this.mHandshakeBuilder.setUserInfo(userInfo);
        return this;
    }

    public WebSocket setUserInfo(String id, String password) {
        this.mHandshakeBuilder.setUserInfo(id, password);
        return this;
    }

    public WebSocket clearUserInfo() {
        this.mHandshakeBuilder.clearUserInfo();
        return this;
    }

    public boolean isExtended() {
        return this.mExtended;
    }

    public WebSocket setExtended(boolean extended) {
        this.mExtended = extended;
        return this;
    }

    public boolean isAutoFlush() {
        return this.mAutoFlush;
    }

    public WebSocket setAutoFlush(boolean auto) {
        this.mAutoFlush = auto;
        return this;
    }

    public boolean isMissingCloseFrameAllowed() {
        return this.mMissingCloseFrameAllowed;
    }

    public WebSocket setMissingCloseFrameAllowed(boolean allowed) {
        this.mMissingCloseFrameAllowed = allowed;
        return this;
    }

    public WebSocket flush() {
        synchronized (this.mStateManager) {
            WebSocketState state = this.mStateManager.getState();
            if (state == WebSocketState.OPEN || state == WebSocketState.CLOSING) {
                WritingThread wt = this.mWritingThread;
                if (wt != null) {
                    wt.queueFlush();
                }
            }
        }
        return this;
    }

    public int getFrameQueueSize() {
        return this.mFrameQueueSize;
    }

    public WebSocket setFrameQueueSize(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("size must not be negative.");
        }
        this.mFrameQueueSize = size;
        return this;
    }

    public int getMaxPayloadSize() {
        return this.mMaxPayloadSize;
    }

    public WebSocket setMaxPayloadSize(int size) throws IllegalArgumentException {
        if (size < 0) {
            throw new IllegalArgumentException("size must not be negative.");
        }
        this.mMaxPayloadSize = size;
        return this;
    }

    public long getPingInterval() {
        return this.mPingSender.getInterval();
    }

    public WebSocket setPingInterval(long interval) {
        this.mPingSender.setInterval(interval);
        return this;
    }

    public long getPongInterval() {
        return this.mPongSender.getInterval();
    }

    public WebSocket setPongInterval(long interval) {
        this.mPongSender.setInterval(interval);
        return this;
    }

    public PayloadGenerator getPingPayloadGenerator() {
        return this.mPingSender.getPayloadGenerator();
    }

    public WebSocket setPingPayloadGenerator(PayloadGenerator generator) {
        this.mPingSender.setPayloadGenerator(generator);
        return this;
    }

    public PayloadGenerator getPongPayloadGenerator() {
        return this.mPongSender.getPayloadGenerator();
    }

    public WebSocket setPongPayloadGenerator(PayloadGenerator generator) {
        this.mPongSender.setPayloadGenerator(generator);
        return this;
    }

    public WebSocket addListener(WebSocketListener listener) {
        this.mListenerManager.addListener(listener);
        return this;
    }

    public WebSocket addListeners(List<WebSocketListener> listeners) {
        this.mListenerManager.addListeners(listeners);
        return this;
    }

    public WebSocket removeListener(WebSocketListener listener) {
        this.mListenerManager.removeListener(listener);
        return this;
    }

    public WebSocket removeListeners(List<WebSocketListener> listeners) {
        this.mListenerManager.removeListeners(listeners);
        return this;
    }

    public WebSocket clearListeners() {
        this.mListenerManager.clearListeners();
        return this;
    }

    public Socket getSocket() {
        return this.mSocketConnector.getSocket();
    }

    public URI getURI() {
        return this.mHandshakeBuilder.getURI();
    }

    public WebSocket connect() throws WebSocketException {
        changeStateOnConnect();
        try {
            this.mSocketConnector.connect();
            this.mServerHeaders = shakeHands();
            this.mPerMessageCompressionExtension = findAgreedPerMessageCompressionExtension();
            this.mStateManager.setState(WebSocketState.OPEN);
            this.mListenerManager.callOnStateChanged(WebSocketState.OPEN);
            startThreads();
            return this;
        } catch (WebSocketException e) {
            this.mSocketConnector.closeSilently();
            this.mStateManager.setState(WebSocketState.CLOSED);
            this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
            throw e;
        }
    }

    public Future<WebSocket> connect(ExecutorService executorService) {
        return executorService.submit(connectable());
    }

    public Callable<WebSocket> connectable() {
        return new Connectable(this);
    }

    public WebSocket connectAsynchronously() {
        Thread thread = new ConnectThread(this);
        ListenerManager lm = this.mListenerManager;
        if (lm != null) {
            lm.callOnThreadCreated(ThreadType.CONNECT_THREAD, thread);
        }
        thread.start();
        return this;
    }

    public WebSocket disconnect() {
        return disconnect(1000, (String) null);
    }

    public WebSocket disconnect(int closeCode) {
        return disconnect(closeCode, (String) null);
    }

    public WebSocket disconnect(String reason) {
        return disconnect(1000, reason);
    }

    public WebSocket disconnect(int closeCode, String reason) {
        return disconnect(closeCode, reason, 10000);
    }

    public WebSocket disconnect(int closeCode, String reason, long closeDelay) {
        synchronized (this.mStateManager) {
            switch (this.mStateManager.getState()) {
                case CREATED:
                    finishAsynchronously();
                    break;
                case OPEN:
                    this.mStateManager.changeToClosing(StateManager.CloseInitiator.CLIENT);
                    sendFrame(WebSocketFrame.createCloseFrame(closeCode, reason));
                    this.mListenerManager.callOnStateChanged(WebSocketState.CLOSING);
                    if (closeDelay < 0) {
                        closeDelay = 10000;
                    }
                    stopThreads(closeDelay);
                    break;
            }
        }
        return this;
    }

    public List<WebSocketExtension> getAgreedExtensions() {
        return this.mAgreedExtensions;
    }

    public String getAgreedProtocol() {
        return this.mAgreedProtocol;
    }

    public WebSocket sendFrame(WebSocketFrame frame) {
        if (frame != null) {
            synchronized (this.mStateManager) {
                WebSocketState state = this.mStateManager.getState();
                if (state == WebSocketState.OPEN || state == WebSocketState.CLOSING) {
                    WritingThread wt = this.mWritingThread;
                    if (wt != null) {
                        List<WebSocketFrame> frames = splitIfNecessary(frame);
                        if (frames == null) {
                            wt.queueFrame(frame);
                        } else {
                            for (WebSocketFrame f : frames) {
                                wt.queueFrame(f);
                            }
                        }
                    }
                }
            }
        }
        return this;
    }

    private List<WebSocketFrame> splitIfNecessary(WebSocketFrame frame) {
        return WebSocketFrame.splitIfNecessary(frame, this.mMaxPayloadSize, this.mPerMessageCompressionExtension);
    }

    public WebSocket sendContinuation() {
        return sendFrame(WebSocketFrame.createContinuationFrame());
    }

    public WebSocket sendContinuation(boolean fin) {
        return sendFrame(WebSocketFrame.createContinuationFrame().setFin(fin));
    }

    public WebSocket sendContinuation(String payload) {
        return sendFrame(WebSocketFrame.createContinuationFrame(payload));
    }

    public WebSocket sendContinuation(String payload, boolean fin) {
        return sendFrame(WebSocketFrame.createContinuationFrame(payload).setFin(fin));
    }

    public WebSocket sendContinuation(byte[] payload) {
        return sendFrame(WebSocketFrame.createContinuationFrame(payload));
    }

    public WebSocket sendContinuation(byte[] payload, boolean fin) {
        return sendFrame(WebSocketFrame.createContinuationFrame(payload).setFin(fin));
    }

    public WebSocket sendText(String message) {
        return sendFrame(WebSocketFrame.createTextFrame(message));
    }

    public WebSocket sendText(String payload, boolean fin) {
        return sendFrame(WebSocketFrame.createTextFrame(payload).setFin(fin));
    }

    public WebSocket sendBinary(byte[] message) {
        return sendFrame(WebSocketFrame.createBinaryFrame(message));
    }

    public WebSocket sendBinary(byte[] payload, boolean fin) {
        return sendFrame(WebSocketFrame.createBinaryFrame(payload).setFin(fin));
    }

    public WebSocket sendClose() {
        return sendFrame(WebSocketFrame.createCloseFrame());
    }

    public WebSocket sendClose(int closeCode) {
        return sendFrame(WebSocketFrame.createCloseFrame(closeCode));
    }

    public WebSocket sendClose(int closeCode, String reason) {
        return sendFrame(WebSocketFrame.createCloseFrame(closeCode, reason));
    }

    public WebSocket sendPing() {
        return sendFrame(WebSocketFrame.createPingFrame());
    }

    public WebSocket sendPing(byte[] payload) {
        return sendFrame(WebSocketFrame.createPingFrame(payload));
    }

    public WebSocket sendPing(String payload) {
        return sendFrame(WebSocketFrame.createPingFrame(payload));
    }

    public WebSocket sendPong() {
        return sendFrame(WebSocketFrame.createPongFrame());
    }

    public WebSocket sendPong(byte[] payload) {
        return sendFrame(WebSocketFrame.createPongFrame(payload));
    }

    public WebSocket sendPong(String payload) {
        return sendFrame(WebSocketFrame.createPongFrame(payload));
    }

    private void changeStateOnConnect() throws WebSocketException {
        synchronized (this.mStateManager) {
            if (this.mStateManager.getState() != WebSocketState.CREATED) {
                throw new WebSocketException(WebSocketError.NOT_IN_CREATED_STATE, "The current state of the WebSocket is not CREATED.");
            }
            this.mStateManager.setState(WebSocketState.CONNECTING);
        }
        this.mListenerManager.callOnStateChanged(WebSocketState.CONNECTING);
    }

    private Map<String, List<String>> shakeHands() throws WebSocketException {
        Socket socket = this.mSocketConnector.getSocket();
        WebSocketInputStream input = openInputStream(socket);
        WebSocketOutputStream output = openOutputStream(socket);
        String key = generateWebSocketKey();
        writeHandshake(output, key);
        Map<String, List<String>> headers = readHandshake(input, key);
        this.mInput = input;
        this.mOutput = output;
        return headers;
    }

    private WebSocketInputStream openInputStream(Socket socket) throws WebSocketException {
        try {
            return new WebSocketInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            throw new WebSocketException(WebSocketError.SOCKET_INPUT_STREAM_FAILURE, "Failed to get the input stream of the raw socket: " + e.getMessage(), e);
        }
    }

    private WebSocketOutputStream openOutputStream(Socket socket) throws WebSocketException {
        try {
            return new WebSocketOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            throw new WebSocketException(WebSocketError.SOCKET_OUTPUT_STREAM_FAILURE, "Failed to get the output stream from the raw socket: " + e.getMessage(), e);
        }
    }

    private static String generateWebSocketKey() {
        byte[] data = new byte[16];
        Misc.nextBytes(data);
        return Base64.encode(data);
    }

    private void writeHandshake(WebSocketOutputStream output, String key) throws WebSocketException {
        this.mHandshakeBuilder.setKey(key);
        String requestLine = this.mHandshakeBuilder.buildRequestLine();
        List<String[]> headers = this.mHandshakeBuilder.buildHeaders();
        String handshake = HandshakeBuilder.build(requestLine, headers);
        this.mListenerManager.callOnSendingHandshake(requestLine, headers);
        try {
            output.write(handshake);
            output.flush();
        } catch (IOException e) {
            throw new WebSocketException(WebSocketError.OPENING_HAHDSHAKE_REQUEST_FAILURE, "Failed to send an opening handshake request to the server: " + e.getMessage(), e);
        }
    }

    private Map<String, List<String>> readHandshake(WebSocketInputStream input, String key) throws WebSocketException {
        return new HandshakeReader(this).readHandshake(input, key);
    }

    private void startThreads() {
        ReadingThread readingThread = new ReadingThread(this);
        WritingThread writingThread = new WritingThread(this);
        synchronized (this.mThreadsLock) {
            this.mReadingThread = readingThread;
            this.mWritingThread = writingThread;
        }
        readingThread.callOnThreadCreated();
        writingThread.callOnThreadCreated();
        readingThread.start();
        writingThread.start();
    }

    private void stopThreads(long closeDelay) {
        ReadingThread readingThread;
        WritingThread writingThread;
        synchronized (this.mThreadsLock) {
            readingThread = this.mReadingThread;
            writingThread = this.mWritingThread;
            this.mReadingThread = null;
            this.mWritingThread = null;
        }
        if (readingThread != null) {
            readingThread.requestStop(closeDelay);
        }
        if (writingThread != null) {
            writingThread.requestStop();
        }
    }

    /* access modifiers changed from: package-private */
    public WebSocketInputStream getInput() {
        return this.mInput;
    }

    /* access modifiers changed from: package-private */
    public WebSocketOutputStream getOutput() {
        return this.mOutput;
    }

    /* access modifiers changed from: package-private */
    public StateManager getStateManager() {
        return this.mStateManager;
    }

    /* access modifiers changed from: package-private */
    public ListenerManager getListenerManager() {
        return this.mListenerManager;
    }

    /* access modifiers changed from: package-private */
    public HandshakeBuilder getHandshakeBuilder() {
        return this.mHandshakeBuilder;
    }

    /* access modifiers changed from: package-private */
    public void setAgreedExtensions(List<WebSocketExtension> extensions) {
        this.mAgreedExtensions = extensions;
    }

    /* access modifiers changed from: package-private */
    public void setAgreedProtocol(String protocol) {
        this.mAgreedProtocol = protocol;
    }

    /* access modifiers changed from: package-private */
    public void onReadingThreadStarted() {
        boolean bothStarted = false;
        synchronized (this.mThreadsLock) {
            this.mReadingThreadStarted = true;
            if (this.mWritingThreadStarted) {
                bothStarted = true;
            }
        }
        callOnConnectedIfNotYet();
        if (bothStarted) {
            onThreadsStarted();
        }
    }

    /* access modifiers changed from: package-private */
    public void onWritingThreadStarted() {
        boolean bothStarted = false;
        synchronized (this.mThreadsLock) {
            this.mWritingThreadStarted = true;
            if (this.mReadingThreadStarted) {
                bothStarted = true;
            }
        }
        callOnConnectedIfNotYet();
        if (bothStarted) {
            onThreadsStarted();
        }
    }

    private void callOnConnectedIfNotYet() {
        synchronized (this.mOnConnectedCalledLock) {
            if (!this.mOnConnectedCalled) {
                this.mOnConnectedCalled = true;
                this.mListenerManager.callOnConnected(this.mServerHeaders);
            }
        }
    }

    private void onThreadsStarted() {
        this.mPingSender.start();
        this.mPongSender.start();
    }

    /* access modifiers changed from: package-private */
    public void onReadingThreadFinished(WebSocketFrame closeFrame) {
        synchronized (this.mThreadsLock) {
            this.mReadingThreadFinished = true;
            this.mServerCloseFrame = closeFrame;
            if (this.mWritingThreadFinished) {
                onThreadsFinished();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onWritingThreadFinished(WebSocketFrame closeFrame) {
        synchronized (this.mThreadsLock) {
            this.mWritingThreadFinished = true;
            this.mClientCloseFrame = closeFrame;
            if (this.mReadingThreadFinished) {
                onThreadsFinished();
            }
        }
    }

    private void onThreadsFinished() {
        finish();
    }

    /* access modifiers changed from: package-private */
    public void finish() {
        this.mPingSender.stop();
        this.mPongSender.stop();
        try {
            this.mSocketConnector.getSocket().close();
        } catch (Throwable th) {
        }
        synchronized (this.mStateManager) {
            this.mStateManager.setState(WebSocketState.CLOSED);
        }
        this.mListenerManager.callOnStateChanged(WebSocketState.CLOSED);
        this.mListenerManager.callOnDisconnected(this.mServerCloseFrame, this.mClientCloseFrame, this.mStateManager.getClosedByServer());
    }

    private void finishAsynchronously() {
        WebSocketThread thread = new FinishThread(this);
        thread.callOnThreadCreated();
        thread.start();
    }

    private PerMessageCompressionExtension findAgreedPerMessageCompressionExtension() {
        if (this.mAgreedExtensions == null) {
            return null;
        }
        for (WebSocketExtension extension : this.mAgreedExtensions) {
            if (extension instanceof PerMessageCompressionExtension) {
                return (PerMessageCompressionExtension) extension;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public PerMessageCompressionExtension getPerMessageCompressionExtension() {
        return this.mPerMessageCompressionExtension;
    }
}
