package com.vk.sdk.api;

import android.os.Handler;
import android.os.Looper;
import com.vk.sdk.VKObject;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.httpClient.VKHttpClient;

public class VKBatchRequest extends VKObject {
    private boolean mCanceled = false;
    private final VKRequest.VKRequestListener[] mOriginalListeners;
    private final VKRequest[] mRequests;
    private final VKResponse[] mResponses;
    public VKBatchRequestListener requestListener;

    public VKBatchRequest(VKRequest... requests) {
        this.mRequests = requests;
        this.mResponses = new VKResponse[this.mRequests.length];
        this.mOriginalListeners = new VKRequest.VKRequestListener[this.mRequests.length];
        for (int i = 0; i < this.mRequests.length; i++) {
            this.mOriginalListeners[i] = this.mRequests[i].requestListener;
        }
    }

    public void executeWithListener(VKBatchRequestListener listener) {
        if (this.mRequests == null) {
            provideError(new VKError(-103));
            return;
        }
        this.requestListener = listener;
        Handler intervalHandler = new Handler(Looper.myLooper());
        int nextInterval = 0;
        for (final VKRequest request : this.mRequests) {
            intervalHandler.postDelayed(new Runnable() {
                public void run() {
                    final VKRequest.VKRequestListener originalListener = request.requestListener;
                    request.setRequestListener(new VKRequest.VKRequestListener() {
                        public void onComplete(VKResponse response) {
                            VKBatchRequest.this.provideResponse(response);
                        }

                        public void onError(VKError error) {
                            VKBatchRequest.this.provideError(error);
                        }

                        public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                            if (originalListener != null) {
                                originalListener.onProgress(progressType, bytesLoaded, bytesTotal);
                            }
                        }
                    });
                    VKHttpClient.enqueueOperation(request.getOperation());
                }
            }, (long) nextInterval);
            nextInterval += 333;
        }
    }

    public void cancel() {
        if (!this.mCanceled) {
            this.mCanceled = true;
            for (VKRequest request : this.mRequests) {
                request.cancel();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void provideResponse(VKResponse response) {
        this.mResponses[indexOfRequest(response.request)] = response;
        VKResponse[] vKResponseArr = this.mResponses;
        int length = vKResponseArr.length;
        int i = 0;
        while (i < length) {
            if (vKResponseArr[i] != null) {
                i++;
            } else {
                return;
            }
        }
        for (int i2 = 0; i2 < this.mRequests.length; i2++) {
            VKRequest.VKRequestListener l = this.mOriginalListeners[i2];
            if (l != null) {
                l.onComplete(this.mResponses[i2]);
            }
        }
        if (this.requestListener != null) {
            this.requestListener.onComplete(this.mResponses);
        }
    }

    private int indexOfRequest(VKRequest request) {
        for (int i = 0; i < this.mRequests.length; i++) {
            if (this.mRequests[i].equals(request)) {
                return i;
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void provideError(VKError error) {
        if (!this.mCanceled) {
            for (int i = 0; i < this.mRequests.length; i++) {
                VKRequest.VKRequestListener l = this.mOriginalListeners[i];
                if (l != null) {
                    l.onError(error);
                }
            }
            if (this.requestListener != null) {
                this.requestListener.onError(error);
            }
            cancel();
        }
    }

    public static abstract class VKBatchRequestListener {
        public void onComplete(VKResponse[] responses) {
        }

        public void onError(VKError error) {
        }
    }
}
