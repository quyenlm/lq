package com.beetalk.sdk.plugin.impl.friends;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.network.AsyncNetworkRequest;
import com.garena.pay.android.GGErrorCode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetUserFriendIDList extends GGPlugin<Void, DataModel.FriendIDsRet> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GET_USER_FRIEND_ID_LIST;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GET_USER_FRIEND_ID_LIST;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, Void data) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("GET").setUri(getUri()).addHttpParam("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken()).addHttpParam("show_uid", "1");
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    DataModel.FriendIDsRet returnData = new DataModel.FriendIDsRet();
                    returnData.platform = GGLoginSession.getCurrentSession().getPlatform().intValue();
                    returnData.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                    returnData.ids = new Vector<>();
                    returnData.idInfoList = new Vector<>();
                    if (e != null) {
                        BBLogger.e(e);
                    } else {
                        GGErrorCode code = Helper.getErrorCode(result);
                        if (code != null) {
                            BBLogger.i("GetUserFriendIDList: Error Received response from server", new Object[0]);
                            returnData.flag = code.getCode().intValue();
                        } else {
                            BBLogger.i("GetUserFriendIDList: Received response from server %s", result.toString());
                            try {
                                int getPlatform = result.getInt("platform");
                                JSONArray ids = result.optJSONArray("friends");
                                JSONArray uids = result.optJSONArray("uids");
                                if (ids != null) {
                                    int len = ids.length();
                                    for (int i = 0; i < len; i++) {
                                        DataModel.FriendIdInfo info = new DataModel.FriendIdInfo();
                                        info.openId = ids.getString(i);
                                        if (uids != null && i < uids.length()) {
                                            info.uid = uids.getLong(i);
                                        }
                                        returnData.ids.add(info.openId);
                                        returnData.idInfoList.add(info);
                                    }
                                }
                                returnData.flag = GGErrorCode.SUCCESS.getCode().intValue();
                                returnData.platform = getPlatform;
                            } catch (JSONException e1) {
                                BBLogger.e(e1);
                                returnData.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                            }
                        }
                    }
                    GGPluginManager.getInstance().publishResult(returnData, activity, GetUserFriendIDList.this.getId());
                }
            });
        } catch (MalformedURLException e) {
            BBLogger.e(e);
        }
    }

    /* access modifiers changed from: protected */
    public URL getUri() throws MalformedURLException {
        return new URL(SDKConstants.getUserFriendIDListUrl());
    }

    private boolean responseHasError(JSONObject result) {
        return result == null || result.has("error");
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}
