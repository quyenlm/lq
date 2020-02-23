package com.vk.sdk.api.photo;

import com.vk.sdk.api.VKUploadBase;
import com.vk.sdk.api.httpClient.VKHttpClient;
import com.vk.sdk.api.httpClient.VKJsonOperation;
import java.io.File;

public abstract class VKUploadPhotoBase extends VKUploadBase {
    private static final long serialVersionUID = -4566961568409572159L;
    protected long mAlbumId;
    protected long mGroupId;
    protected File[] mImages;
    protected long mUserId;

    /* access modifiers changed from: protected */
    public VKJsonOperation getUploadOperation(String uploadUrl) {
        return new VKJsonOperation(VKHttpClient.fileUploadRequest(uploadUrl, this.mImages));
    }
}
