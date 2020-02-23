package com.vk.sdk.api.methods;

import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.docs.VKUploadDocRequest;
import com.vk.sdk.api.docs.VKUploadWallDocRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKDocsArray;
import com.vk.sdk.util.VKUtil;
import java.io.File;

public class VKApiDocs extends VKApiBase {
    public VKRequest getUploadServer() {
        return prepareRequest("getUploadServer", (VKParameters) null);
    }

    public VKRequest getUploadServer(long groupId) {
        return prepareRequest("getUploadServer", VKUtil.paramsFrom("group_id", Long.valueOf(groupId)));
    }

    public VKRequest getUploadWallServer() {
        return prepareRequest("getWallUploadServer", (VKParameters) null);
    }

    public VKRequest getUploadWallServer(long groupId) {
        return prepareRequest("getWallUploadServer", VKUtil.paramsFrom("group_id", Long.valueOf(groupId)));
    }

    public VKRequest save(VKParameters params) {
        return prepareRequest(APMidasBaseRequest.OFFER_TYPE_GAME, params, (Class<? extends VKApiModel>) VKDocsArray.class);
    }

    public VKRequest uploadDocRequest(File doc) {
        return new VKUploadDocRequest(doc);
    }

    public VKRequest uploadDocRequest(File doc, long groupId) {
        return new VKUploadDocRequest(doc, groupId);
    }

    public VKRequest uploadWallDocRequest(File doc) {
        return new VKUploadWallDocRequest(doc);
    }

    public VKRequest uploadWallDocRequest(File doc, long groupId) {
        return new VKUploadWallDocRequest(doc, groupId);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "docs";
    }
}
