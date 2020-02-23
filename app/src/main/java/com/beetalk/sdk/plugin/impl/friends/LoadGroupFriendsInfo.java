package com.beetalk.sdk.plugin.impl.friends;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.beetalk.sdk.plugin.PluginUtil;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.network.AsyncNetworkRequest;
import com.garena.pay.android.GGErrorCode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.JSONObject;

public class LoadGroupFriendsInfo extends BaseLoadGroupPlugin<List<String>, DataModel.GroupFriendsInfoRet> {
    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getLoadGroupFriendsInfoUrl());
    }

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.LOAD_GROUP_FRIEND_INFO;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.LOAD_GROUP_FRIEND_INFO;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, List<String> data) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        final DataModel.GroupFriendsInfoRet ret = new DataModel.GroupFriendsInfoRet();
        try {
            builder.setRequestMethod("GET");
            builder.setUri(getUri());
            builder.addHttpParam("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken());
            builder.addHttpParam("friends", StringUtils.join(data, ","));
            builder.addHttpParam("platform", String.valueOf(GGLoginSession.getCurrentSession().getSessionProvider().getValue()));
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    GGErrorCode resultCode = LoadGroupFriendsInfo.this.getResponseCode(result);
                    ret.flag = resultCode.getCode().intValue();
                    if (resultCode == GGErrorCode.SUCCESS) {
                        ret.info = PluginUtil.parseGroupUserInfoList(result);
                    }
                    GGPluginManager.getInstance().publishResult(ret, activity, LoadGroupFriendsInfo.this.getId());
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
}
