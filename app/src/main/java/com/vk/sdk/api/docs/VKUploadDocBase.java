package com.vk.sdk.api.docs;

import com.vk.sdk.api.VKUploadBase;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKJsonOperation;
import java.io.File;

public abstract class VKUploadDocBase extends VKUploadBase {
    protected File mDoc;
    protected long mGroupId;

    /* access modifiers changed from: protected */
    public VKJsonOperation getUploadOperation(String uploadUrl) {
        return new VKJsonOperation(VKHttpClient.docUploadRequest(uploadUrl, this.mDoc));
    }
}
