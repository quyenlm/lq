package com.tencent.imsdk.unity.push;

import android.content.Context;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.tencent.imsdk.push.xg.IMXGPushReceiver;
import com.tencent.imsdk.tool.etc.IMLogger;
import org.json.JSONException;
import org.json.JSONObject;

public class UnityPushReceiver extends IMXGPushReceiver {
    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult registerMessage) {
        String text;
        if (errorCode == 0) {
            text = registerMessage + "注册成功";
            registerMessage.getToken();
        } else {
            text = registerMessage + "注册失败，错误码：" + errorCode;
        }
        IMLogger.d(text);
    }

    public void onUnregisterResult(Context context, int errorCode) {
        String text;
        if (errorCode == 0) {
            text = "反注册成功";
        } else {
            text = "反注册失败" + errorCode;
        }
        IMLogger.d(text);
    }

    public void onSetTagResult(Context context, int errorCode, String tagName) {
        String text;
        if (errorCode == 0) {
            text = "\"" + tagName + "\"设置成功";
        } else {
            text = "\"" + tagName + "\"设置失败,错误码：" + errorCode;
        }
        IMLogger.d(text);
    }

    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        String text;
        if (errorCode == 0) {
            text = "\"" + tagName + "\"删除成功";
        } else {
            text = "\"" + tagName + "\"删除失败,错误码：" + errorCode;
        }
        IMLogger.d(text);
    }

    public void onTextMessage(Context context, XGPushTextMessage message) {
        String text = "收到消息:" + message.toString();
        String customContent = message.getCustomContent();
        if (!(customContent == null || customContent.length() == 0)) {
            try {
                JSONObject obj = new JSONObject(customContent);
                if (!obj.isNull("key")) {
                    IMLogger.d("get custom value:" + obj.getString("key"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        IMLogger.d(text);
    }

    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
        String text = "通知被打开 :" + message;
        String customContent = message.getCustomContent();
        if (!(customContent == null || customContent.length() == 0)) {
            try {
                JSONObject obj = new JSONObject(customContent);
                if (!obj.isNull("key")) {
                    IMLogger.d("get custom value:" + obj.getString("key"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        IMLogger.d(text);
    }

    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
        IMLogger.d("已展示通知 :" + notifiShowedRlt);
    }
}
