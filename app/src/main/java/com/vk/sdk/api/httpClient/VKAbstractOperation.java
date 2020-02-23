package com.vk.sdk.api.httpClient;

import android.support.annotation.Nullable;
import com.vk.sdk.api.VKError;
import java.util.concurrent.ExecutorService;

public abstract class VKAbstractOperation {
    private boolean mCanceled = false;
    /* access modifiers changed from: private */
    public VKOperationCompleteListener mCompleteListener;
    @Nullable
    private ExecutorService mResponseQueue;
    private VKOperationState mState = VKOperationState.Created;

    public static abstract class VKAbstractCompleteListener<OperationType extends VKAbstractOperation, ResponseType> {
        public abstract void onComplete(OperationType operationtype, ResponseType responsetype);

        public abstract void onError(OperationType operationtype, VKError vKError);
    }

    public interface VKOperationCompleteListener {
        void onComplete();
    }

    public enum VKOperationState {
        Created,
        Ready,
        Executing,
        Paused,
        Finished,
        Canceled
    }

    public abstract Object getResultObject();

    public VKAbstractOperation() {
        setState(VKOperationState.Ready);
    }

    public void start(ExecutorService responseQueue) {
        this.mResponseQueue = responseQueue;
    }

    public void cancel() {
        this.mCanceled = true;
        setState(VKOperationState.Canceled);
    }

    public void finish() {
        Runnable r = new Runnable() {
            public void run() {
                if (VKAbstractOperation.this.mCompleteListener != null) {
                    VKAbstractOperation.this.mCompleteListener.onComplete();
                }
            }
        };
        if (this.mResponseQueue != null) {
            this.mResponseQueue.submit(r);
        } else {
            r.run();
        }
    }

    /* access modifiers changed from: protected */
    public void setCompleteListener(VKOperationCompleteListener listener) {
        this.mCompleteListener = listener;
    }

    /* access modifiers changed from: protected */
    public VKOperationState state() {
        return this.mState;
    }

    /* access modifiers changed from: protected */
    public void setState(VKOperationState state) {
        if (!isStateTransitionInvalid(this.mState, state, this.mCanceled)) {
            this.mState = state;
            if (this.mState == VKOperationState.Finished || this.mState == VKOperationState.Canceled) {
                finish();
            }
        }
    }

    private boolean isStateTransitionInvalid(VKOperationState fromState, VKOperationState toState, boolean isCancelled) {
        switch (fromState) {
            case Paused:
                switch (toState) {
                    case Canceled:
                        return false;
                    default:
                        if (toState == VKOperationState.Ready) {
                            return false;
                        }
                        return true;
                }
            case Executing:
                switch (toState) {
                    case Paused:
                    case Canceled:
                    case Finished:
                        return false;
                    default:
                        return true;
                }
            case Canceled:
            case Finished:
                return true;
            case Ready:
                switch (toState) {
                    case Paused:
                    case Executing:
                    case Canceled:
                        return false;
                    case Finished:
                        if (isCancelled) {
                            return false;
                        }
                        return true;
                    default:
                        return true;
                }
            default:
                return false;
        }
    }
}
