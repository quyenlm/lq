package com.tencent.imsdk.tool.net;

import java.lang.ref.WeakReference;

public class RequestHandle {
    private final WeakReference<AsyncHttpRequest> request;

    public RequestHandle(AsyncHttpRequest request2) {
        this.request = new WeakReference<>(request2);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        AsyncHttpRequest _request = (AsyncHttpRequest) this.request.get();
        return _request == null || _request.cancel(mayInterruptIfRunning);
    }

    public boolean isFinished() {
        AsyncHttpRequest _request = (AsyncHttpRequest) this.request.get();
        return _request == null || _request.isDone();
    }

    public boolean isCancelled() {
        AsyncHttpRequest _request = (AsyncHttpRequest) this.request.get();
        return _request == null || _request.isCancelled();
    }

    public boolean shouldBeGarbageCollected() {
        boolean should = isCancelled() || isFinished();
        if (should) {
            this.request.clear();
        }
        return should;
    }
}
