package com.beetalk.sdk.plugin.impl.friends;

import android.app.Activity;
import android.content.Intent;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.DataModel;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.plugin.GGPlugin;
import com.beetalk.sdk.plugin.GGPluginManager;
import com.garena.network.AsyncHttpClient;
import com.garena.network.AsyncHttpResponse;
import com.garena.network.AsyncNetworkRequest;
import com.garena.pay.android.GGErrorCode;
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetFriendInfo extends GGPlugin<List<String>, DataModel.FriendsInfoRet> {
    private static final int MAX_BATCH_SIZE = 50;

    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GET_FRIEND_INFO;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GET_FRIEND_INFO;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, List<String> data) {
        final DataModel.FriendsInfoRet friendsInfoRet = new DataModel.FriendsInfoRet();
        friendsInfoRet.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
        friendsInfoRet.info = new Vector<>();
        friendsInfoRet.platform = GGLoginSession.getCurrentSession().getPlatform().intValue();
        if (data == null || data.size() > 50) {
            friendsInfoRet.flag = GGErrorCode.BATCH_SIZE_EXCEEDED.getCode().intValue();
            GGPluginManager.getInstance().publishResult(friendsInfoRet, activity, getId());
            return;
        }
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("GET").setUri(new URL(SDKConstants.getFriendsInfoFromOpenId())).addHttpParam("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken()).addHttpParam("friends", StringUtils.join(data, ","));
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    if (e != null) {
                        BBLogger.e(e);
                    } else {
                        GGErrorCode code = Helper.getErrorCode(result);
                        if (code != null) {
                            BBLogger.i("GetFriendInfo: Received error from server", new Object[0]);
                            friendsInfoRet.flag = code.getCode().intValue();
                        } else {
                            friendsInfoRet.flag = GGErrorCode.SUCCESS.getCode().intValue();
                            try {
                                friendsInfoRet.platform = result.optInt("platform");
                                JSONArray friends = result.getJSONArray("friends");
                                for (int i = 0; i < friends.length(); i++) {
                                    JSONObject userObj = friends.getJSONObject(i);
                                    DataModel.UserInfo userInfo = new DataModel.UserInfo();
                                    userInfo.iconURL = userObj.optString("icon");
                                    userInfo.gender = userObj.optInt("gender");
                                    userInfo.nickName = userObj.optString(ChatMsg.KEY_NICK_NAME);
                                    userInfo.openID = userObj.optString("open_id");
                                    friendsInfoRet.info.add(userInfo);
                                }
                            } catch (JSONException e1) {
                                BBLogger.e(e1);
                                friendsInfoRet.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                            }
                        }
                    }
                    GGPluginManager.getInstance().publishResult(friendsInfoRet, activity, GetFriendInfo.this.getId());
                }
            });
        } catch (MalformedURLException e) {
            BBLogger.e(e);
            GGPluginManager.getInstance().publishResult(friendsInfoRet, activity, getId());
        }
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }

    public boolean embedInActivity() {
        return false;
    }
}
