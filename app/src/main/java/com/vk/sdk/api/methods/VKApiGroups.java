package com.vk.sdk.api.methods;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKUsersArray;

public class VKApiGroups extends VKApiBase {
    public VKRequest isMember(VKParameters params) {
        return prepareRequest("isMember", params);
    }

    public VKRequest getById(VKParameters params) {
        return prepareRequest("getById", params, (Class<? extends VKApiModel>) VKApiCommunityArray.class);
    }

    public VKRequest get(VKParameters params) {
        if (!params.containsKey("extended") || ((Integer) params.get("extended")).intValue() != 1) {
            return prepareRequest("get", params);
        }
        return prepareRequest("get", params, (Class<? extends VKApiModel>) VKApiCommunityArray.class);
    }

    public VKRequest getMembers(VKParameters params) {
        return prepareRequest("getMembers", params);
    }

    public VKRequest join(VKParameters params) {
        return prepareRequest("join", params);
    }

    public VKRequest leave(VKParameters params) {
        return prepareRequest("leave", params);
    }

    public VKRequest leave(final int group_id) {
        return prepareRequest("leave", new VKParameters() {
            {
                put("group_id", String.valueOf(group_id));
            }
        });
    }

    public VKRequest search(VKParameters params) {
        return prepareRequest(FirebaseAnalytics.Event.SEARCH, params, (Class<? extends VKApiModel>) VKApiCommunityArray.class);
    }

    public VKRequest getInvites(VKParameters params) {
        return prepareRequest("getInvites", params, (Class<? extends VKApiModel>) VKApiCommunityArray.class);
    }

    public VKRequest banUser(VKParameters params) {
        return prepareRequest("banUser", params);
    }

    public VKRequest unbanUser(VKParameters params) {
        return prepareRequest("unbanUser", params);
    }

    public VKRequest getBanned(VKParameters params) {
        return prepareRequest("getBanned", params, (Class<? extends VKApiModel>) VKUsersArray.class);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "groups";
    }
}
