package com.beetalk.sdk.plugin.impl.friends;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginUtil;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.network.AsyncNetworkRequest;
import com.garena.pay.android.GGErrorCode;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

public class LoadFriendGroupList extends BaseLoadGroupPlugin<Void, DataModel.FriendGroupsRet> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.LOAD_FRIEND_GROUPS_LIST;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.LOAD_FRIEND_GROUPS_LIST;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, Void data) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        final DataModel.FriendGroupsRet ret = new DataModel.FriendGroupsRet();
        try {
            builder.setRequestMethod("GET");
            builder.setUri(getUri());
            builder.addHttpParam("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken());
            builder.addHttpParam("show_uid", "1");
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    GGErrorCode resultCode = LoadFriendGroupList.this.getResponseCode(result);
                    ret.flag = resultCode.getCode().intValue();
                    if (resultCode == GGErrorCode.SUCCESS) {
                        ret.flag = GGErrorCode.SUCCESS.getCode().intValue();
                        ret.groups = PluginUtil.parseFriendGroups(result);
                    }
                    GGPluginManager.getInstance().publishResult(ret, activity, LoadFriendGroupList.this.getId());
                }
            });
        } catch (Exception e) {
            BBLogger.e(e);
            ret.flag = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
            GGPluginManager.getInstance().publishResult(ret, activity, getId());
        }
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getLoadFriendGroupUrl());
    }
}
