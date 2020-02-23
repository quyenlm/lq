package com.vk.sdk.api.httpClient;

import com.vk.sdk.api.httpClient.VKAbstractOperation;
import com.vk.sdk.api.httpClient.VKHttpClient;
import org.json.JSONObject;

public class VKJsonOperation extends VKHttpOperation<JSONObject> {
    private JSONObject mResponseJson;

    public static abstract class VKJSONOperationCompleteListener extends VKAbstractOperation.VKAbstractCompleteListener<VKJsonOperation, JSONObject> {
    }

    public VKJsonOperation(VKHttpClient.VKHTTPRequest uriRequest) {
        super(uriRequest);
    }

    public JSONObject getResponseJson() {
        if (this.mResponseJson == null) {
            String response = getResponseString();
            if (response == null) {
                return null;
            }
            try {
                this.mResponseJson = new JSONObject(response);
            } catch (Exception e) {
                this.mLastException = e;
            }
        }
        return this.mResponseJson;
    }

    /* access modifiers changed from: protected */
    public boolean postExecution() {
        if (!super.postExecution()) {
            return false;
        }
        this.mResponseJson = getResponseJson();
        return true;
    }

    public JSONObject getResultObject() {
        return this.mResponseJson;
    }
}
