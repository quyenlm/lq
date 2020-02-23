package com.vk.sdk.api;

import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKJsonOperation;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class VKUploadBase extends VKRequest {
    /* access modifiers changed from: protected */
    public abstract VKRequest getSaveRequest(JSONObject jSONObject);

    /* access modifiers changed from: protected */
    public abstract VKRequest getServerRequest();

    /* access modifiers changed from: protected */
    public abstract VKJsonOperation getUploadOperation(String str);

    public VKUploadBase() {
        super((String) null);
    }

    public VKAbstractOperation getOperation() {
        return new VKUploadOperation();
    }

    private class VKUploadOperation extends VKAbstractOperation {
        protected VKAbstractOperation lastOperation;

        private VKUploadOperation() {
        }

        public void start(ExecutorService s) {
            super.start(s);
            final VKRequest.VKRequestListener originalListener = VKUploadBase.this.requestListener;
            VKUploadBase.this.requestListener = new VKRequest.VKRequestListener() {
                public void onComplete(VKResponse response) {
                    VKUploadOperation.this.setState(VKAbstractOperation.VKOperationState.Finished);
                    response.request = VKUploadBase.this;
                    if (originalListener != null) {
                        originalListener.onComplete(response);
                    }
                }

                public void onError(VKError error) {
                    VKUploadOperation.this.setState(VKAbstractOperation.VKOperationState.Finished);
                    error.request = VKUploadBase.this;
                    if (originalListener != null) {
                        originalListener.onError(error);
                    }
                }

                public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                    if (originalListener != null) {
                        originalListener.onProgress(progressType, bytesLoaded, bytesTotal);
                    }
                }
            };
            setState(VKAbstractOperation.VKOperationState.Executing);
            VKRequest serverRequest = VKUploadBase.this.getServerRequest();
            serverRequest.setRequestListener(new VKUploadRequestListener());
            this.lastOperation = serverRequest.getOperation();
            VKHttpClient.enqueueOperation(this.lastOperation);
        }

        public void cancel() {
            if (this.lastOperation != null) {
                this.lastOperation.cancel();
            }
            super.cancel();
        }

        public void finish() {
            super.finish();
            this.lastOperation = null;
        }

        public Object getResultObject() {
            return null;
        }

        private class VKUploadRequestListener extends VKRequest.VKRequestListener {
            private VKUploadRequestListener() {
            }

            public void onComplete(VKResponse response) {
                try {
                    VKJsonOperation postFileRequest = VKUploadBase.this.getUploadOperation(response.json.getJSONObject("response").getString("upload_url"));
                    postFileRequest.setHttpOperationListener(new VKJsonOperation.VKJSONOperationCompleteListener() {
                        public void onComplete(VKJsonOperation operation, JSONObject response) {
                            VKRequest saveRequest = VKUploadBase.this.getSaveRequest(response);
                            saveRequest.setRequestListener(new VKRequest.VKRequestListener() {
                                public void onComplete(VKResponse response) {
                                    if (VKUploadBase.this.requestListener != null) {
                                        VKUploadBase.this.requestListener.onComplete(response);
                                    }
                                    VKUploadOperation.this.setState(VKAbstractOperation.VKOperationState.Finished);
                                }

                                public void onError(VKError error) {
                                    if (VKUploadBase.this.requestListener != null) {
                                        VKUploadBase.this.requestListener.onError(error);
                                    }
                                }
                            });
                            VKUploadOperation.this.lastOperation = saveRequest.getOperation();
                            VKHttpClient.enqueueOperation(VKUploadOperation.this.lastOperation);
                        }

                        public void onError(VKJsonOperation operation, VKError error) {
                            if (VKUploadBase.this.requestListener != null) {
                                VKUploadBase.this.requestListener.onError(error);
                            }
                        }
                    });
                    VKUploadOperation.this.lastOperation = postFileRequest;
                    VKHttpClient.enqueueOperation(VKUploadOperation.this.lastOperation);
                } catch (JSONException e) {
                    VKError error = new VKError(-104);
                    error.httpError = e;
                    error.errorMessage = e.getMessage();
                    if (VKUploadBase.this.requestListener != null) {
                        VKUploadBase.this.requestListener.onError(error);
                    }
                }
            }

            public void onError(VKError error) {
                if (VKUploadBase.this.requestListener != null) {
                    VKUploadBase.this.requestListener.onError(error);
                }
            }
        }
    }
}
