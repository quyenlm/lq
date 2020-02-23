package com.vk.sdk.api.methods;

import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.midas.oversea.api.request.APMidasBaseRequest;
import com.tencent.midas.oversea.network.http.APNetworkManager2;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VkAudioArray;

public class VKApiAudio extends VKApiBase {
    public VKRequest get() {
        return get((VKParameters) null);
    }

    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params, (Class<? extends VKApiModel>) VkAudioArray.class);
    }

    public VKRequest getById(VKParameters params) {
        return prepareRequest("getById", params);
    }

    public VKRequest getLyrics(VKParameters params) {
        return prepareRequest("getLyrics", params);
    }

    public VKRequest search(VKParameters params) {
        return prepareRequest(FirebaseAnalytics.Event.SEARCH, params, (Class<? extends VKApiModel>) VkAudioArray.class);
    }

    public VKRequest getUploadServer() {
        return getUploadServer((VKParameters) null);
    }

    public VKRequest getUploadServer(VKParameters params) {
        return prepareRequest("getUploadServer", params);
    }

    public VKRequest save(VKParameters params) {
        return prepareRequest(APMidasBaseRequest.OFFER_TYPE_GAME, params);
    }

    public VKRequest add(VKParameters params) {
        return prepareRequest(ProductAction.ACTION_ADD, params);
    }

    public VKRequest delete(VKParameters params) {
        return prepareRequest("delete", params);
    }

    public VKRequest edit(VKParameters params) {
        return prepareRequest("edit", params);
    }

    public VKRequest reorder(VKParameters params) {
        return prepareRequest("reorder", params);
    }

    public VKRequest restore(VKParameters params) {
        return prepareRequest(APNetworkManager2.HTTP_KEY_OVERSEARESTORE, params);
    }

    public VKRequest getAlbums() {
        return getAlbums((VKParameters) null);
    }

    public VKRequest getAlbums(VKParameters params) {
        return prepareRequest("getAlbums", params);
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

    public VKRequest moveToAlbum(VKParameters params) {
        return prepareRequest("moveToAlbum", params);
    }

    public VKRequest setBroadcast(VKParameters params) {
        return prepareRequest("setBroadcast", params);
    }

    public VKRequest getBroadcastList() {
        return getBroadcastList((VKParameters) null);
    }

    public VKRequest getBroadcastList(VKParameters params) {
        return prepareRequest("getBroadcastList", params);
    }

    public VKRequest getRecommendations() {
        return getRecommendations((VKParameters) null);
    }

    public VKRequest getRecommendations(VKParameters params) {
        return prepareRequest("getRecommendations", params, (Class<? extends VKApiModel>) VkAudioArray.class);
    }

    public VKRequest getPopular() {
        return getPopular((VKParameters) null);
    }

    public VKRequest getPopular(VKParameters params) {
        return prepareRequest("getPopular", params, (Class<? extends VKApiModel>) VkAudioArray.class);
    }

    public VKRequest getCount(VKParameters params) {
        return prepareRequest("getCount", params);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "audio";
    }
}
