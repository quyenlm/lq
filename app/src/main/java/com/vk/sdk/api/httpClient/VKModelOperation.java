package com.vk.sdk.api.httpClient;

import com.vk.sdk.api.VKDefaultParser;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.model.VKApiModel;

public class VKModelOperation extends VKJsonOperation {
    protected final VKParser mParser;
    public Object parsedModel;

    public VKModelOperation(VKHttpClient.VKHTTPRequest uriRequest, Class<? extends VKApiModel> modelClass) {
        super(uriRequest);
        this.mParser = new VKDefaultParser(modelClass);
    }

    public VKModelOperation(VKHttpClient.VKHTTPRequest uriRequest, VKParser parser) {
        super(uriRequest);
        this.mParser = parser;
    }

    /* access modifiers changed from: protected */
    public boolean postExecution() {
        if (!super.postExecution() || this.mParser == null) {
            return false;
        }
        try {
            this.parsedModel = this.mParser.createModel(getResponseJson());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
