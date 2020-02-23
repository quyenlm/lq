package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import java.util.concurrent.Future;

public interface TransferMonitor {
    Future<?> getFuture();

    boolean isDone();
}
