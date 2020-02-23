package com.vk.sdk.api.model;

import org.json.JSONException;
import org.json.JSONObject;

public class VKApiCommunityArray extends VKList<VKApiCommunityFull> {
    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiCommunityFull.class);
        return this;
    }
}
