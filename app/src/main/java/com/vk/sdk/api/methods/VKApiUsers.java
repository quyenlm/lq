package com.vk.sdk.api.methods;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKParser;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.api.model.VKUsersArray;
import org.json.JSONObject;

public class VKApiUsers extends VKApiBase {
    public VKRequest get() {
        return get((VKParameters) null);
    }

    public VKRequest get(VKParameters params) {
        return prepareRequest("get", params, (VKParser) new VKParser() {
            public Object createModel(JSONObject object) {
                return new VKList(object, VKApiUserFull.class);
            }
        });
    }

    public VKRequest search(VKParameters params) {
        return prepareRequest(FirebaseAnalytics.Event.SEARCH, params, (Class<? extends VKApiModel>) VKUsersArray.class);
    }

    public VKRequest isAppUser() {
        return prepareRequest("isAppUser", (VKParameters) null);
    }

    public VKRequest isAppUser(final int userID) {
        return prepareRequest("isAppUser", new VKParameters() {
            private static final long serialVersionUID = 7458591447441581671L;

            {
                put("user_id", String.valueOf(userID));
            }
        });
    }

    public VKRequest getSubscriptions() {
        return getSubscriptions((VKParameters) null);
    }

    public VKRequest getSubscriptions(VKParameters params) {
        return prepareRequest("getSubscriptions", params);
    }

    public VKRequest getFollowers() {
        return getFollowers((VKParameters) null);
    }

    public VKRequest getFollowers(VKParameters params) {
        return prepareRequest("getFollowers", params);
    }

    public VKRequest report(VKParameters params) {
        return prepareRequest("report", params);
    }

    /* access modifiers changed from: protected */
    public String getMethodsGroup() {
        return "users";
    }
}
