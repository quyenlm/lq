package com.tencent.imsdk.unity.share;

import android.app.Activity;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.sns.api.IMShare;
import com.tencent.imsdk.sns.api.IMShareListener;
import com.tencent.imsdk.sns.base.IMShareContent;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.unity3d.player.UnityPlayer;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareHelper extends IMShare {
    private static final String unityGameObject = "Tencent.iMSDK.IMShare";

    public static boolean initialize() {
        currentContext = UnityPlayer.currentActivity;
        return initialize(currentContext);
    }

    public static boolean initialize(String channel) {
        IMLogger.d("in initialize : " + channel);
        currentContext = UnityPlayer.currentActivity;
        return initialize(currentContext, channel);
    }

    public static void share(final int callbackTag, String jsonDataString) {
        IMLogger.d("in helper invite : " + callbackTag + "|" + jsonDataString);
        IMShareListener listener = new IMShareListener() {
            public void onShare(final IMResult result) {
                ((Activity) ShareHelper.currentContext).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            UnityPlayer.UnitySendMessage(ShareHelper.unityGameObject, "OnShare", callbackTag + "|" + result.toUnityString());
                        } catch (JSONException e) {
                            UnityPlayer.UnitySendMessage(ShareHelper.unityGameObject, "OnShare", callbackTag + "|" + "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
                        }
                    }
                });
            }
        };
        try {
            IMShareContent content = new IMShareContent();
            JSONObject jsonObject = new JSONObject(jsonDataString);
            if (jsonObject.has("type")) {
                content.type = jsonObject.getInt("type");
            }
            if (jsonObject.has("title")) {
                content.title = jsonObject.getString("title");
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
            IMLogger.d("in helper invite : set listener ... ");
            setListener(listener);
            share(content);
        } catch (JSONException e) {
            UnityPlayer.UnitySendMessage(unityGameObject, "OnShare", callbackTag + "|" + "{\"retCode\" : " + IMErrorDef.SYSTEM + ", \"retMsg\" : \"" + IMErrorDef.getErrorString(IMErrorDef.SYSTEM) + ":encode to json string error\"}");
        }
    }
}
