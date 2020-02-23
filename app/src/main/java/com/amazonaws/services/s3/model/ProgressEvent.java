package com.amazonaws.services.s3.model;

@Deprecated
public class ProgressEvent extends com.amazonaws.event.ProgressEvent {
    public ProgressEvent(int bytesTransferred) {
        super((long) bytesTransferred);
    }

    public ProgressEvent(int eventCode, long bytesTransferred) {
        super(eventCode, bytesTransferred);
    }

    @Deprecated
    public void setBytesTransfered(int bytesTransferred) {
        setBytesTransferred((long) bytesTransferred);
    }

    @Deprecated
    public int getBytesTransfered() {
        return (int) getBytesTransferred();
    }
}
