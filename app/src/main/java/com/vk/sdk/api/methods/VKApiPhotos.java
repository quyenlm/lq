package com.vk.sdk.api.methods;

import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKPhotoArray;
import com.vk.sdk.util.VKUtil;

public class VKApiPhotos extends VKApiBase {
    public VKRequest getUploadServer(long albumId) {
        return prepareRequest("getUploadServer", VKUtil.paramsFrom(VKApiConst.ALBUM_ID, String.valueOf(albumId)));
    }

    public VKRequest getUploadServer(long albumId, long groupId) {
        return prepareRequest("getUploadServer", VKUtil.paramsFrom(VKApiConst.ALBUM_ID, Long.valueOf(albumId), "group_id", Long.valueOf(groupId)));
    }

    public VKRequest getWallUploadServer() {
        return prepareRequest("getWallUploadServer", (VKParameters) null);
    }

    public VKRequest getWallUploadServer(long groupId) {
        return prepareRequest("getWallUploadServer", VKUtil.paramsFrom("group_id", Long.valueOf(groupId)));
    }

    public VKRequest getMessagesUploadServer() {
        return prepareRequest("getMessagesUploadServer", (VKParameters) null);
    }

    public VKRequest saveWallPhoto(VKParameters params) {
        return prepareRequest("saveWallPhoto", params, (Class<? extends VKApiModel>) VKPhotoArray.class);
    }

    public VKRequest save(VKParameters params) {
        return prepareRequest(APMidasBaseRequest.OFFER_TYPE_GAME, params, (Class<? extends VKApiModel>) VKPhotoArray.class);
    }

    public VKRequest saveMessagesPhoto(VKParameters params) {
        return prepareRequest("saveMessagesPhoto", params, (Class<? extends VKApiModel>) VKPhotoArray.class);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "photos";
    }
}
