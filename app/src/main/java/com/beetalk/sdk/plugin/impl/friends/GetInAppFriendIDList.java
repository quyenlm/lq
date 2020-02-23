package com.beetalk.sdk.plugin.impl.friends;

import com.beetalk.sdk.SDKConstants;
import java.net.MalformedURLException;
import java.net.URL;

public class GetInAppFriendIDList extends GetUserFriendIDList {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GET_INAPP_FRIEND_ID_LIST;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GET_INAPP_FRIEND_ID_LIST;
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getInAppFriendIDListUrl());
    }
}
