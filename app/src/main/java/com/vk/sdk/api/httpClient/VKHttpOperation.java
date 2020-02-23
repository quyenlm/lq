package com.vk.sdk.api.httpClient;

import android.support.annotation.Nullable;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.httpClient.VKHttpClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;

public class VKHttpOperation<ResponseType> extends VKAbstractOperation {
    protected Exception mLastException;
    private String mResponseString;
    private final VKHttpClient.VKHTTPRequest mUriRequest;
    @Nullable
    public VKHttpClient.VKHttpResponse response;

    public VKHttpOperation(VKHttpClient.VKHTTPRequest uriRequest) {
        this.mUriRequest = uriRequest;
    }

    public void start(ExecutorService s) {
        super.start(s);
        setState(VKAbstractOperation.VKOperationState.Executing);
        try {
            if (!this.mUriRequest.isAborted) {
                this.response = VKHttpClient.execute(this.mUriRequest);
                setState(VKAbstractOperation.VKOperationState.Finished);
            }
        } catch (IOException e) {
            this.mLastException = e;
        }
    }

    public void finish() {
        postExecution();
        super.finish();
    }

    public ResponseType getResultObject() {
        if (this.response != null) {
            return this.response.responseBytes;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public boolean postExecution() {
        return true;
    }

    public void cancel() {
        VKHttpClient.cancelHttpOperation(this);
        super.cancel();
    }

    public VKHttpClient.VKHTTPRequest getUriRequest() {
        return this.mUriRequest;
    }

    public byte[] getResponseData() {
        if (this.response != null) {
            return this.response.responseBytes;
        }
        return null;
    }

    public String getResponseString() {
        if (this.response == null || this.response.responseBytes == null) {
            return null;
        }
        if (this.mResponseString == null) {
            try {
                this.mResponseString = new String(this.response.responseBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                this.mLastException = e;
            }
        }
        return this.mResponseString;
    }

    /* access modifiers changed from: protected */
    public VKError generateError(Exception e) {
        VKError error;
        if (state() == VKAbstractOperation.VKOperationState.Canceled) {
            error = new VKError(-102);
        } else {
            error = new VKError(-105);
        }
        if (e != null) {
            error.errorMessage = e.getMessage();
            if (error.errorMessage == null) {
                error.errorMessage = e.toString();
            }
            error.httpError = e;
        }
        return error;
    }

    public <OperationType extends VKHttpOperation> void setHttpOperationListener(final VKAbstractOperation.VKAbstractCompleteListener<OperationType, ResponseType> listener) {
        setCompleteListener(new VKAbstractOperation.VKOperationCompleteListener() {
            public void onComplete() {
                if (VKHttpOperation.this.state() == VKAbstractOperation.VKOperationState.Finished && VKHttpOperation.this.mLastException == null) {
                    listener.onComplete(VKHttpOperation.this, VKHttpOperation.this.getResultObject());
                } else {
                    listener.onError(VKHttpOperation.this, VKHttpOperation.this.generateError(VKHttpOperation.this.mLastException));
                }
            }
        });
    }
}
