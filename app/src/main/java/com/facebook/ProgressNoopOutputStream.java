package com.facebook;

import android.os.Handler;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

class ProgressNoopOutputStream extends OutputStream implements RequestOutputStream {
    private int batchMax;
    private final Handler callbackHandler;
    private GraphRequest currentRequest;
    private RequestProgress currentRequestProgress;
    private final Map<GraphRequest, RequestProgress> progressMap = new HashMap();

    ProgressNoopOutputStream(Handler callbackHandler2) {
        this.callbackHandler = callbackHandler2;
    }

    public void setCurrentRequest(GraphRequest currentRequest2) {
        this.currentRequest = currentRequest2;
        this.currentRequestProgress = currentRequest2 != null ? this.progressMap.get(currentRequest2) : null;
    }

    /* access modifiers changed from: package-private */
    public int getMaxProgress() {
        return this.batchMax;
    }

    /* access modifiers changed from: package-private */
    public Map<GraphRequest, RequestProgress> getProgressMap() {
        return this.progressMap;
    }

    /* access modifiers changed from: package-private */
    public void addProgress(long size) {
        if (this.currentRequestProgress == null) {
            this.currentRequestProgress = new RequestProgress(this.callbackHandler, this.currentRequest);
            this.progressMap.put(this.currentRequest, this.currentRequestProgress);
        }
        this.currentRequestProgress.addToMax(size);
        this.batchMax = (int) (((long) this.batchMax) + size);
    }

    public void write(byte[] buffer) {
        addProgress((long) buffer.length);
    }

    public void write(byte[] buffer, int offset, int length) {
        addProgress((long) length);
    }

    public void write(int oneByte) {
        addProgress(1);
    }
}
