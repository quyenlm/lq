package com.tencent.imsdk.sns.base;

import android.app.Activity;
import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMConfig;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.sns.api.IMFriend;
import com.tencent.imsdk.sns.api.IMLogin;
import java.util.HashMap;
import org.json.JSONException;

public abstract class IMFriendBase extends IMSNSBase {
    protected static String GUID_FRIEND_LIST_URL = (IMConfig.getSdkServerUrl() + "/friends/lists");
    static final int IMSDK_NEED_LOGIN_CODE = 10;
    static final int IMSDK_NO_PACKAGE_CODE = 9;
    static final int IMSDK_NO_SUPPORT_CODE = 7;
    static final int IMSDK_PARAMS_ERROR_CODE = 11;
    static final int IMSDK_SERVER_ERROR_CODE = 5;
    static final int IMSDK_SYSTEM_ERROR_CODE = 3;

    public static class MessageType {
        public static final int IMAGE = 4;
        public static final int IMAGE_DIALOG = 5;
        public static final int LINK = 2;
        public static final int LINK_DIALOG = 3;
        public static final int TEXT = 0;
        public static final int TEXT_DIALOG = 1;
    }

    public abstract void invite(IMFriendContent iMFriendContent, IMCallback<IMFriendResult> iMCallback);

    public abstract void sendImage(IMFriendContent iMFriendContent, IMCallback<IMFriendResult> iMCallback);

    public abstract void sendLink(IMFriendContent iMFriendContent, IMCallback<IMFriendResult> iMCallback);

    public abstract void sendText(IMFriendContent iMFriendContent, IMCallback<IMFriendResult> iMCallback);

    public boolean initialize(Context context) {
        GUID_FRIEND_LIST_URL = IMConfig.getSdkServerUrl() + "/friends/lists";
        return super.setContext(context);
    }

    public void getInviteFriends(int page, int count, String extraJson, IMCallback<IMFriendResult> iMCallback) {
    }

    public void getFriends(int page, int count, final IMCallback<IMFriendResult> callback) {
        if (!isInitialized()) {
            callback.onError(new IMException(9, "not initialized", 9, IMErrorMsg.getMessageByCode(9)));
            return;
        }
        String friendChannel = IMFriend.getChannel();
        if (friendChannel == null || friendChannel.length() <= 0) {
            callback.onError(new IMException(9, "not initialized", 9, IMErrorMsg.getMessageByCode(9)));
            return;
        }
        IMLoginBase loginInstance = IMLogin.getInstance(friendChannel);
        if (loginInstance == null) {
            callback.onError(new IMException(10, "should call login first", 10, IMErrorMsg.getMessageByCode(10)));
            return;
        }
        if (!loginInstance.isInitialized()) {
            loginInstance.initialize(this.currentContext);
        }
        if (!loginInstance.isLogin() || loginInstance.getLoginResult() == null) {
            callback.onError(new IMException(10, "should call login first", 10, IMErrorMsg.getMessageByCode(10)));
            return;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("sInnerToken", loginInstance.getLoginResult().guidToken);
        params.put("iOpenid", loginInstance.getLoginResult().openId);
        params.put("iPage", String.valueOf(page));
        params.put("iPageSize", String.valueOf(count));
        params.put("iChannel", String.valueOf(loginInstance.getChannelId()));
        httpClient.get(GUID_FRIEND_LIST_URL, params, new IMCallback<String>() {
            public void onSuccess(final String result) {
                ((Activity) IMFriendBase.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            IMFriendResult friendResult = new IMFriendResult(result);
                            if (friendResult.retCode != 1) {
                                callback.onError(new IMException(5, friendResult.retMsg, 5, IMErrorMsg.getMessageByCode(5), friendResult.retCode, friendResult.retMsg));
                            } else {
                                callback.onSuccess(friendResult);
                            }
                        } catch (JSONException e) {
                            callback.onError(new IMException(3, e.getMessage(), 3, IMErrorMsg.getMessageByCode(3)));
                        }
                    }
                });
            }

            public void onCancel() {
                ((Activity) IMFriendBase.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        callback.onCancel();
                    }
                });
            }

            public void onError(final IMException exception) {
                ((Activity) IMFriendBase.this.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        callback.onError(exception);
                    }
                });
            }
        });
    }

    public void sendMessage(IMFriendContent content, IMCallback<IMFriendResult> callback) {
        if (content.type == 1) {
            sendText(content, callback);
        } else if (content.type == 3) {
            sendLink(content, callback);
        } else if (content.type == 5) {
            sendImage(content, callback);
        } else {
            IMException exception = new IMException(7, "unknown message type : " + content.type);
            exception.imsdkRetCode = 11;
            exception.imsdkRetMsg = IMErrorMsg.getMessageByCode(exception.imsdkRetCode);
            callback.onError(exception);
        }
    }
}
