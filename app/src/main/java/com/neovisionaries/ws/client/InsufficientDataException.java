package com.neovisionaries.ws.client;

class InsufficientDataException extends WebSocketException {
    private static final long serialVersionUID = 1;
    private final int mReadByteCount;
    private final int mRequestedByteCount;

    public InsufficientDataException(int requestedByteCount, int readByteCount) {
        super(WebSocketError.INSUFFICENT_DATA, "The end of the stream has been reached unexpectedly.");
        this.mRequestedByteCount = requestedByteCount;
        this.mReadByteCount = readByteCount;
    }

    public int getRequestedByteCount() {
        return this.mRequestedByteCount;
    }

    public int getReadByteCount() {
        return this.mReadByteCount;
    }
}
