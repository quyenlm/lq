package com.vk.sdk.api.model;

import org.json.JSONObject;

public class VKPrivacy {
    public static final int PRIVACY_ALL = 0;
    public static final int PRIVACY_FRIENDS = 1;
    public static final int PRIVACY_FRIENDS_OF_FRIENDS = 2;
    public static final int PRIVACY_NOBODY = 3;
    public static final int PRIVACY_UNKNOWN = 4;

    VKPrivacy() {
    }

    public static int parsePrivacy(JSONObject privacyView) {
        if (privacyView == null) {
            return 0;
        }
        String type = privacyView.optString("type");
        if ("all".equals(type)) {
            return 0;
        }
        if ("friends".equals(type)) {
            return 1;
        }
        if ("friends_of_friends".equals(type)) {
            return 2;
        }
        if ("nobody".equals(type)) {
            return 3;
        }
        return 4;
    }
}
