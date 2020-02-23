package com.amazonaws.mobileconnectors.s3.transferutility;

public interface TransferListener {
    void onError(int i, Exception exc);

    void onProgressChanged(int i, long j, long j2);

    void onStateChanged(int i, TransferState transferState);
}
