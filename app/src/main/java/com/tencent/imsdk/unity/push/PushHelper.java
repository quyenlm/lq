package com.tencent.imsdk.unity.push;

import android.app.Activity;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.api.IMSDKApi;
import com.tencent.imsdk.push.api.IMPush;
import com.tencent.imsdk.push.api.IMPushListener;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

public class PushHelper extends IMPush {
    /* access modifiers changed from: private */
    public static volatile String unityGameObject = "Tencent.iMSDK.IMPush";

    /* access modifiers changed from: private */
    public static Activity getActivity() {
        return UnityPlayer.currentActivity;
    }

    public static boolean initialize() {
        currentContext = UnityPlayer.currentActivity;
        initialize(currentContext);
        IMSDKApi.onCreate((Activity) currentContext);
        return true;
    }

    public static boolean unitySetChannel(String channel) {
        boolean ret = setChannel(channel);
        setPushListener(new IMPushListener() {
            public void OnRegister(final IMResult result) {
                IMLogger.d("android push register callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            IMLogger.d("push register success callback send unity message : " + result.toUnityString());
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnRegister", result.toUnityString());
                        } catch (JSONException e) {
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnRegister", "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                        }
                    }
                });
            }

            public void OnUnregister(final IMResult result) {
                IMLogger.d("android push un-register callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            IMLogger.d("push un-register success callback send unity message : " + result.toUnityString());
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnUnregister", result.toUnityString());
                        } catch (JSONException e) {
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnUnregister", "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                        }
                    }
                });
            }

            public void OnSetTag(final IMResult result) {
                IMLogger.d("android push set tag callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            IMLogger.d("push set tag success callback send unity message : " + result.toUnityString());
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnTag", result.toUnityString());
                        } catch (JSONException e) {
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnTag", "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                        }
                    }
                });
            }

            public void OnDeleteTag(final IMResult result) {
                IMLogger.d("android push del tag callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            IMLogger.d("push del tag success callback send unity message : " + result.toUnityString());
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnDelTag", result.toUnityString());
                        } catch (JSONException e) {
                            UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnDelTag", "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                        }
                    }
                });
            }

            public void OnNotification(final String jsonMessage) {
                IMLogger.d("android push notify callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        IMLogger.d("push del tag success callback send unity message : " + jsonMessage);
                        UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnNotifyRecv", jsonMessage);
                    }
                });
            }

            public void OnNotifactionClick(final String jsonMessage) {
                IMLogger.d("android push notify click callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        IMLogger.d("push del tag success callback send unity message : " + jsonMessage);
                        UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnNotifyClick", jsonMessage);
                    }
                });
            }

            public void OnNotifactionShow(final String notifiShowedRlt) {
                IMLogger.d("android push notify show callback ...");
                PushHelper.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        IMLogger.d("push del tag success callback send unity message : " + notifiShowedRlt);
                        UnityPlayer.UnitySendMessage(PushHelper.unityGameObject, "OnNotifyShow", notifiShowedRlt);
                    }
                });
            }
        });
        return ret;
    }

    public static boolean initialize(String channel) {
        currentContext = UnityPlayer.currentActivity;
        initialize(currentContext);
        return setChannel(channel);
    }

    public static void register(String account) {
        IMLogger.d("call native push register : " + account);
        if (account == null || account.length() <= 0) {
            registerPush();
            return;
        }
        IMLogger.d("push with account : " + account);
        registerPush(account);
    }

    public static void setTags(String jsonDataString) {
        try {
            JSONArray array = new JSONArray(jsonDataString);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            setTag(list);
        } catch (JSONException e) {
            IMLogger.e(IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ": parse data from unity error !");
        }
    }

    public static void delTags(String jsonDataString) {
        try {
            JSONArray array = new JSONArray(jsonDataString);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                list.add(array.getString(i));
            }
            deleteTag(list);
        } catch (JSONException e) {
            IMLogger.e(IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ": parse data from unity error !");
        }
    }
}
