package com.vk.sdk.api.methods;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VkVideoArray;

public class VKApiVideo extends VKApiBase {
    public VKRequest get() {
        return get((VKParameters) null);
    }

    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params, (Class<? extends VKApiModel>) VkVideoArray.class);
    }

    public VKRequest edit(VKParameters params) {
        return prepareRequest("edit", params);
    }

    public VKRequest add(VKParameters params) {
        return prepareRequest(ProductAction.ACTION_ADD, params);
    }

    public VKRequest save(VKParameters params) {
        return prepareRequest(APMidasBaseRequest.OFFER_TYPE_GAME, params);
    }

    public VKRequest delete(VKParameters params) {
        return prepareRequest("delete", params);
    }

    public VKRequest restore(VKParameters params) {
        return prepareRequest(APNetworkManager2.HTTP_KEY_OVERSEARESTORE, params);
    }

    public VKRequest search(VKParameters params) {
        return prepareRequest(FirebaseAnalytics.Event.SEARCH, params, (Class<? extends VKApiModel>) VkVideoArray.class);
    }

    public VKRequest getUserVideos(VKParameters params) {
        return prepareRequest("getUserVideos", params, (Class<? extends VKApiModel>) VkVideoArray.class);
    }

    public VKRequest getAlbums() {
        return getAlbums((VKParameters) null);
    }

    public VKRequest getAlbums(VKParameters params) {
        return prepareRequest("getAlbums", params);
    }

    public VKRequest getAlbumById(VKParameters params) {
        return prepareRequest("getAlbumById", params);
    }

    public VKRequest addAlbum(VKParameters params) {
        return prepareRequest("addAlbum", params);
    }

    public VKRequest editAlbum(VKParameters params) {
        return prepareRequest("editAlbum", params);
    }

    public VKRequest deleteAlbum(VKParameters params) {
        return prepareRequest("deleteAlbum", params);
    }

    public VKRequest reorderAlbums(VKParameters params) {
        return prepareRequest("getAlbumById", params);
    }

    public VKRequest reorderVideos(VKParameters params) {
        return prepareRequest("getAlbumById", params);
    }

    public VKRequest addToAlbum(VKParameters params) {
        return prepareRequest("addToAlbum", params);
    }

    public VKRequest removeFromAlbum(VKParameters params) {
        return prepareRequest("removeFromAlbum", params);
    }

    public VKRequest getAlbumsByVideo(VKParameters params) {
        return prepareRequest("getAlbumsByVideo", params);
    }

    public VKRequest getComments(VKParameters params) {
        return prepareRequest("getComments", params);
    }

    public VKRequest createComment(VKParameters params) {
        return prepareRequest("createComment", params);
    }

    public VKRequest deleteComment(VKParameters params) {
        return prepareRequest("deleteComment", params);
    }

    public VKRequest restoreComment(VKParameters params) {
        return prepareRequest("restoreComment", params);
    }

    public VKRequest editComment(VKParameters params) {
        return prepareRequest("editComment", params);
    }

    public VKRequest getTags(VKParameters params) {
        return prepareRequest("getTags", params);
    }

    public VKRequest putTag(VKParameters params) {
        return prepareRequest("putTag", params);
    }

    public VKRequest removeTag(VKParameters params) {
        return prepareRequest("removeTag", params);
    }

    public VKRequest getNewTags(VKParameters params) {
        return prepareRequest("getNewTags", params);
    }

    public VKRequest report(VKParameters params) {
        return prepareRequest("report", params);
    }

    public VKRequest reportComment(VKParameters params) {
        return prepareRequest("reportComment", params);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "video";
    }
}
