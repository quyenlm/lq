package com.vk.sdk.api.methods;

import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKCommentArray;
import com.vk.sdk.api.model.VKPostArray;
import com.vk.sdk.api.model.VKWallPostResult;

public class VKApiWall extends VKApiBase {
    public static final String EXTENDED = "extended";

    public VKRequest get(VKParameters params) {
        if (!params.containsKey("extended") || ((Integer) params.get("extended")).intValue() != 1) {
            return prepareRequest("get", params);
        }
        return prepareRequest("get", params, (Class<? extends VKApiModel>) VKPostArray.class);
    }

    public VKRequest getById(VKParameters params) {
        return prepareRequest("getById", params, (Class<? extends VKApiModel>) VKPostArray.class);
    }

    public VKRequest savePost(VKParameters params) {
        return prepareRequest("savePost", params);
    }

    public VKRequest post(VKParameters parameters) {
        return prepareRequest("post", parameters, (Class<? extends VKApiModel>) VKWallPostResult.class);
    }

    public VKRequest repost(VKParameters params) {
        return prepareRequest("repost", params);
    }

    public VKRequest getReposts(VKParameters params) {
        return prepareRequest("getReposts", params);
    }

    public VKRequest edit(VKParameters params) {
        return prepareRequest("edit", params);
    }

    public VKRequest delete(VKParameters params) {
        return prepareRequest("delete", params);
    }

    public VKRequest restore(VKParameters params) {
        return prepareRequest(APNetworkManager2.HTTP_KEY_OVERSEARESTORE, params);
    }

    public VKRequest getComments(VKParameters params) {
        return prepareRequest("getComments", params, (Class<? extends VKApiModel>) VKCommentArray.class);
    }

    public VKRequest addComment(VKParameters params) {
        return prepareRequest("addComment", params);
    }

    public VKRequest editComment(VKParameters params) {
        return prepareRequest("editComment", params);
    }

    public VKRequest deleteComment(VKParameters params) {
        return prepareRequest("deleteComment", params);
    }

    public VKRequest restoreComment(VKParameters params) {
        return prepareRequest("restoreComment", params);
    }

    public VKRequest reportPost(VKParameters params) {
        return prepareRequest("reportPost", params);
    }

    public VKRequest reportComment(VKParameters params) {
        return prepareRequest("reportComment", params);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "wall";
    }
}
