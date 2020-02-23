package com.beetalk.sdk.plugin.impl.user;

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
import com.tencent.ieg.ntv.ctrl.chat.ChatMsg;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;

public class GetUserInfo extends GGPlugin<Void, DataModel.UserInfoRet> {
    public String getId() {
        return SDKConstants.PLUGIN_KEYS.GET_USER_INFO;
    }

    public Integer getRequestCode() {
        return SDKConstants.PLUGIN_REQUEST_CODES.GET_USER_INFO;
    }

    public boolean embedInActivity() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void executeAction(final Activity activity, Void data) {
        AsyncNetworkRequest.RequestBuilder builder = new AsyncNetworkRequest.RequestBuilder();
        try {
            builder.setRequestMethod("GET").setUri(new URL(SDKConstants.getUserInfoUrl())).addHttpParam("access_token", GGLoginSession.getCurrentSession().getTokenValue().getAuthToken());
            AsyncHttpClient.getInstance().getJSONObject(builder.build(), new AsyncHttpClient.JSONObjectCallback() {
                public void onCompleted(Exception e, AsyncHttpResponse source, JSONObject result) {
                    DataModel.UserInfoRet returnData = new DataModel.UserInfoRet();
                    returnData.flag = GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
                    returnData.platform = GGLoginSession.getCurrentSession().getPlatform().intValue();
                    Object[] objArr = new Object[1];
                    objArr[0] = result != null ? result.toString() : "";
                    BBLogger.i("UserInfoPlugin: Received response from server %s", objArr);
                    if (e != null) {
                        BBLogger.e(e);
                    } else {
                        GGErrorCode code = Helper.getErrorCode(result);
                        if (code != null) {
                            returnData.flag = code.getCode().intValue();
                        } else {
                            try {
                                returnData.flag = GGErrorCode.SUCCESS.getCode().intValue();
                                returnData.platform = result.optInt("platform");
                                returnData.userInfo = new DataModel.UserInfo();
                                returnData.userInfo.iconURL = result.optString("icon");
                                returnData.userInfo.gender = result.optInt("gender");
                                returnData.userInfo.nickName = result.optString(ChatMsg.KEY_NICK_NAME);
                                returnData.userInfo.openID = result.optString("open_id");
                            } catch (Exception e1) {
                                BBLogger.e(e1);
                                returnData.flag = GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
                            }
                        }
                    }
                    GGPluginManager.getInstance().publishResult(returnData, activity, GetUserInfo.this.getId());
                }
            });
        } catch (MalformedURLException e) {
            BBLogger.e(e);
        }
    }

    public boolean onActivityResult(Activity ggPluginActivity, int resultCode, Intent data) {
        return false;
    }
}
