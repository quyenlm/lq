package com.tencent.imsdk.unity.friend;

import android.app.Activity;
import android.content.Intent;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.IMException;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.api.IMFriend;
import com.tencent.imsdk.sns.api.IMFriendListener;
import com.tencent.imsdk.sns.base.IMFriendContent;
import com.tencent.imsdk.sns.base.IMFriendResult;
import com.tencent.imsdk.sns.base.IMLaunchResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendHelper extends IMFriend {
    private static final int IMSDK_CANCEL_ERROR = 2;
    private static final int IMSDK_SYSTEM_ERROR = 3;
    /* access modifiers changed from: private */
    public static volatile String unityGameObject = "Tencent.iMSDK.IMFriend";

    public static boolean initialize() {
        IMLogger.d("in unity initialize : " + UnityPlayer.currentActivity);
        currentContext = UnityPlayer.currentActivity;
        return initialize(currentContext);
    }

    public static boolean initialize(String channel) {
        IMLogger.d("in unity initialize : " + channel);
        currentContext = UnityPlayer.currentActivity;
        return initialize(currentContext, channel);
    }

    protected static void callbackByError(String unityCallbackFunction, int callbackTag, int retCode, String retMsg, int iMSDKRetCode, int thirdCode, String thirdMsg) {
        IMResult imResult = new IMResult(retCode, retMsg);
        imResult.imsdkRetCode = iMSDKRetCode;
        imResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(imResult.imsdkRetCode);
        imResult.thirdRetCode = thirdCode;
        imResult.thirdRetMsg = thirdMsg;
        try {
            UnityPlayer.UnitySendMessage(unityGameObject, unityCallbackFunction, callbackTag + "|" + imResult.toUnityString());
        } catch (Exception ex) {
            IMLogger.e("send unity message error : " + ex.getMessage());
        }
    }

    public static void getFriends(final int callbackTag, int page, int count, int type, String extraJson) {
        IMLogger.d("in unity get friend : " + callbackTag + "|" + page + "|" + count + "|" + type + "|" + extraJson);
        final int thisTag = callbackTag;
        IMFriend.setListener(new IMFriendListener() {
            public void onFriend(final IMFriendResult result) {
                ((Activity) FriendHelper.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            UnityPlayer.UnitySendMessage(FriendHelper.unityGameObject, "OnGetFriends", thisTag + "|" + result.toUnityString());
                        } catch (Exception e) {
                            IMLogger.e("send unity message error : " + e.getMessage());
                            FriendHelper.callbackByError("OnGetFriends", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                        }
                    }
                });
            }
        });
        IMFriend.getFriends(page, count, type, extraJson);
    }

    public static void getFriends(final int callbackTag, int page, int count) {
        IMLogger.d("in unity get friend : " + callbackTag + "|" + page + "|" + count);
        final int thisTag = callbackTag;
        IMFriend.setListener(new IMFriendListener() {
            public void onFriend(final IMFriendResult result) {
                ((Activity) FriendHelper.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            UnityPlayer.UnitySendMessage(FriendHelper.unityGameObject, "OnGetFriends", thisTag + "|" + result.toUnityString());
                        } catch (Exception e) {
                            IMLogger.e("send unity message error : " + e.getMessage());
                            FriendHelper.callbackByError("OnGetFriends", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                        }
                    }
                });
            }
        });
        IMFriend.getFriends(page, count);
    }

    protected static IMFriendContent parseFriendContent(String jsonDataString) throws JSONException {
        IMFriendContent content = new IMFriendContent();
        JSONObject jsonObject = new JSONObject(jsonDataString);
        if (jsonObject.has("type")) {
            content.type = jsonObject.getInt("type");
        }
        if (jsonObject.has("title")) {
            content.title = jsonObject.getString("title");
        }
        if (jsonObject.has(IMFriend.FriendPrarams.USER)) {
            content.user = jsonObject.getString(IMFriend.FriendPrarams.USER);
        }
        if (jsonObject.has("content")) {
            content.content = jsonObject.getString("content");
        }
        if (jsonObject.has("link")) {
            content.link = jsonObject.getString("link");
        }
        if (jsonObject.has("thumbPath")) {
            content.thumbPath = jsonObject.getString("thumbPath");
        }
        if (jsonObject.has("imagePath")) {
            content.imagePath = jsonObject.getString("imagePath");
        }
        if (jsonObject.has("extraJson")) {
            content.extraJson = jsonObject.getString("extraJson");
        }
        return content;
    }

    public static void invite(final int callbackTag, String jsonDataString) {
        IMLogger.d("in unity invite : " + callbackTag + "|" + jsonDataString);
        try {
            setListener(new IMFriendListener() {
                public void onFriend(final IMFriendResult result) {
                    ((Activity) FriendHelper.currentContext).runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                UnityPlayer.UnitySendMessage(FriendHelper.unityGameObject, "OnInvite", callbackTag + "|" + result.toUnityString());
                            } catch (Exception e) {
                                IMLogger.e("send unity message error : " + e.getMessage());
                                FriendHelper.callbackByError("OnInvite", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                            }
                        }
                    });
                }
            });
            invite(parseFriendContent(jsonDataString));
        } catch (Exception e) {
            IMLogger.e("unity send invite error : " + e.getMessage());
        }
    }

    public static void sendMessage(final int callbackTag, String jsonDataString) {
        IMLogger.d("in unity send message : " + callbackTag + "|" + jsonDataString);
        IMFriendListener listener = new IMFriendListener() {
            public void onFriend(final IMFriendResult result) {
                ((Activity) FriendHelper.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            UnityPlayer.UnitySendMessage(FriendHelper.unityGameObject, "OnSendMessage", callbackTag + "|" + result.toUnityString());
                        } catch (Exception e) {
                            IMLogger.e("send unity message error : " + e.getMessage());
                            FriendHelper.callbackByError("OnSendMessage", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                        }
                    }
                });
            }
        };
        try {
            IMFriendContent content = parseFriendContent(jsonDataString);
            IMFriend.setListener(listener);
            sendMessage(content);
        } catch (Exception e) {
            IMLogger.e("unity send message error : " + e.getMessage());
        }
    }

    public static void handleIntent(final int callbackTag, final String functionName, String channel, Intent intent) {
        IMLogger.d("in unity handleIntent : " + callbackTag + "|" + functionName + "|" + channel);
        Intent tempIntent = null;
        if (intent != null) {
            tempIntent = intent;
        } else if (UnityPlayer.currentActivity != null) {
            tempIntent = UnityPlayer.currentActivity.getIntent();
        }
        IMFriend.handleIntent(channel, tempIntent, new IMCallback<IMLaunchResult>() {
            public void onSuccess(IMLaunchResult result) {
                try {
                    UnityPlayer.UnitySendMessage(FriendHelper.unityGameObject, functionName, callbackTag + "|" + result.toUnityString());
                } catch (JSONException e) {
                    IMLogger.e("send unity message error : " + e.getMessage());
                    FriendHelper.callbackByError(functionName, callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                }
            }

            public void onCancel() {
                IMLogger.e("send unity message cancel ");
                FriendHelper.callbackByError(functionName, callbackTag, 2, IMErrorDef.getErrorString(2), 2, -1, "");
            }

            public void onError(IMException exception) {
                IMLogger.e("send unity message onError : " + exception.getMessage());
                FriendHelper.callbackByError(functionName, callbackTag, exception.errorCode, exception.getMessage(), exception.imsdkRetCode, -1, "");
            }
        });
    }
}
