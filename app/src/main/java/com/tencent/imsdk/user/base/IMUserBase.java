package com.tencent.imsdk.user.base;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMHttpClient;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import java.util.HashMap;
import org.json.JSONException;

public class IMUserBase {
    private String GET_USER_INFO_URL = (IMConfig.getSdkServerUrl() + "/userinfo/get");
    private String SET_USER_INFO_URL = (IMConfig.getSdkServerUrl() + "/userinfo/set");
    private Context currentContext;
    private IMHttpClient httpClient;

    public boolean initialize(Context context) {
        this.currentContext = context;
        this.httpClient = new IMHttpClient();
        this.GET_USER_INFO_URL = IMConfig.getSdkServerUrl() + "/userinfo/get";
        this.SET_USER_INFO_URL = IMConfig.getSdkServerUrl() + "/userinfo/set";
        return this.currentContext != null;
    }

    public void getInfo(final IMCallback<IMUserInfoResult> callback) {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult.retCode == 1 || loginResult.imsdkRetCode == 1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("iOpenid", loginResult.openId);
            params.put("sInnerToken", loginResult.guidToken);
            params.put("iChannel", String.valueOf(loginResult.channelId));
            this.httpClient.get(this.GET_USER_INFO_URL, params, new IMCallback<String>() {
                public void onSuccess(String result) {
                    IMLogger.d("get user info server return : " + result);
                    try {
                        IMUserInfoResult infoResult = new IMUserInfoResult(result);
                        IMLogger.d("user info result : " + infoResult.toUnityString());
                        if (infoResult.retCode == 1 || infoResult.imsdkRetCode == 1) {
                            callback.onSuccess(infoResult);
                        } else {
                            callback.onSuccess(new IMUserInfoResult(5, infoResult.retCode + ":" + infoResult.retMsg, 5, infoResult.retCode + ":" + infoResult.retMsg));
                        }
                    } catch (JSONException e) {
                        callback.onError(new IMException(5, "parse get user info result error : " + e.getMessage()));
                    }
                }

                public void onCancel() {
                    callback.onCancel();
                }

                public void onError(IMException exception) {
                    callback.onError(exception);
                }
            });
            return;
        }
        callback.onError(new IMException(10));
    }

    public void setInfo(IMUserInfo info, final IMCallback<IMUserInfoResult> callback) {
        IMLoginResult loginResult = IMLogin.getLoginResult();
        if (loginResult.retCode == 1 || loginResult.imsdkRetCode == 1) {
            HashMap<String, String> params = new HashMap<>();
            params.put("iOpenid", loginResult.openId);
            params.put("sInnerToken", loginResult.guidToken);
            params.put("iChannel", String.valueOf(loginResult.channelId));
            if (info.nick != null && info.nick.length() > 0) {
                params.put("sUserName", info.nick);
            }
            if (info.sex > 0) {
                params.put("iGender", String.valueOf(info.sex));
            }
            if (info.birthday != null && info.birthday.length() > 0) {
                params.put("cBirthdate", info.birthday);
            }
            if (info.email != null && info.email.length() > 0) {
                params.put("sEmail", info.email);
            }
            if (info.phone != null && info.phone.length() > 0) {
                params.put("sPhone", info.phone);
            }
            if (info.mobile != null && info.mobile.length() > 0) {
                params.put("sMobile", info.mobile);
            }
            if (info.address != null && info.address.length() > 0) {
                params.put("sAddress", info.address);
            }
            if (info.portrait != null && info.portrait.length() > 0) {
                params.put("sPictureUrl", info.portrait);
            }
            this.httpClient.get(this.SET_USER_INFO_URL, params, new IMCallback<String>() {
                public void onSuccess(String result) {
                    IMLogger.d("get user info server return : " + result);
                    try {
                        IMUserInfoResult infoResult = new IMUserInfoResult(result);
                        IMLogger.d("user info result : " + infoResult.toUnityString());
                        if (infoResult.retCode == 1 || infoResult.imsdkRetCode == 1) {
                            callback.onSuccess(infoResult);
                        } else {
                            callback.onSuccess(new IMUserInfoResult(5, infoResult.retCode + ":" + infoResult.retMsg, 5, infoResult.retCode + ":" + infoResult.retMsg));
                        }
                    } catch (JSONException e) {
                        callback.onError(new IMException(5, "parse get user info result error : " + e.getMessage()));
                    }
                }

                public void onCancel() {
                    callback.onCancel();
                }

                public void onError(IMException exception) {
                    callback.onError(exception);
                }
            });
            return;
        }
        callback.onError(new IMException(10));
    }
}
