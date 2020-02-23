package com.amazonaws.mobileconnectors.s3.transfermanager.internal;

import java.util.concurrent.Future;

public class DownloadMonitor implements TransferMonitor {
    private final DownloadImpl download;
    private final Future<?> future;

    public DownloadMonitor(DownloadImpl download2, Future<?> future2) {
        this.download = download2;
        this.future = future2;
    }

    public Future<?> getFuture() {
        return this.future;
    }

    public boolean isDone() {
        return this.download.isDone();
    }
}
