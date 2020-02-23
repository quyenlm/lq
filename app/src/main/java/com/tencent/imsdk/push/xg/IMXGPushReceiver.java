package com.tencent.imsdk.push.xg;

import android.content.Context;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;
import com.tencent.android.tpush.otherpush.fcm.impl.OtherPushImpl;
import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.push.api.IMPushListener;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.T;
import org.json.JSONException;
import org.json.JSONObject;

public class IMXGPushReceiver extends XGPushBaseReceiver {
    private static final String FIREBASE_TOKEN_KEY = "firebaseToken";
    public static final String LogTag = "imsdk-push";
    private static final int THIRD_RETURN_CODE = 9999;
    private IMPushListener listener = null;

    public void onRegisterResult(Context context, int errorCode, XGPushRegisterResult registerMessage) {
        int errorCode2;
        String text;
        if (errorCode == 0) {
            errorCode2 = 1;
            text = registerMessage + "注册成功";
        } else {
            errorCode2 = 3;
            text = registerMessage + "注册失败，错误码：" + 3;
        }
        IMLogger.d("IMXGPushReceiver:" + text);
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            IMResult result = new IMResult();
            result.retCode = errorCode2;
            result.retMsg = registerMessage.toString();
            result.thirdRetCode = errorCode2;
            result.thirdRetMsg = errorCode2 == 1 ? IMErrorDef.getErrorString(errorCode2) : registerMessage.toString();
            result.imsdkRetCode = errorCode2 == 1 ? 1 : 9999;
            result.imsdkRetMsg = IMErrorDef.getErrorString(result.imsdkRetCode);
            String extraJson = null;
            String firebaseToken = "";
            if (registerMessage != null) {
                try {
                    firebaseToken = registerMessage.getOtherPushToken();
                } catch (Exception e) {
                    IMLogger.e("get firebase token failed : " + e.getMessage());
                }
            }
            if (T.ckIsEmpty(firebaseToken)) {
                firebaseToken = OtherPushImpl.getToken(context);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(FIREBASE_TOKEN_KEY, firebaseToken);
            extraJson = jsonObject.toString();
            if (extraJson != null) {
                result.retExtraJson = extraJson;
            }
            this.listener.OnRegister(result);
            return;
        }
        IMLogger.d("IMXGPushReceiver getListener is null");
    }

    public void onUnregisterResult(Context context, int errorCode) {
        int errorCode2;
        String unregisterMessage;
        int i = 1;
        if (errorCode == 0) {
            errorCode2 = 1;
            unregisterMessage = "unregister success";
        } else {
            errorCode2 = 3;
            unregisterMessage = "unregister fail";
        }
        IMLogger.d("IMXGPushReceiver:" + unregisterMessage);
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            IMResult result = new IMResult();
            result.retCode = errorCode2;
            result.retMsg = unregisterMessage;
            result.thirdRetCode = errorCode2;
            if (errorCode2 == 1) {
                unregisterMessage = IMErrorDef.getErrorString(errorCode2);
            }
            result.thirdRetMsg = unregisterMessage;
            if (errorCode2 != 1) {
                i = 9999;
            }
            result.imsdkRetCode = i;
            result.imsdkRetMsg = IMErrorDef.getErrorString(result.imsdkRetCode);
            this.listener.OnUnregister(result);
        }
    }

    public void onSetTagResult(Context context, int errorCode, String tagName) {
        int errorCode2;
        String text;
        int i = 1;
        if (errorCode == 0) {
            errorCode2 = 1;
            text = "\"" + tagName + "\"设置成功";
        } else {
            errorCode2 = 3;
            text = "\"" + tagName + "\"设置失败,错误码：" + 3;
        }
        IMLogger.d("IMXGPushReceiver:" + text);
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            IMResult result = new IMResult();
            result.retCode = errorCode2;
            result.retMsg = tagName;
            result.thirdRetCode = errorCode2;
            if (errorCode2 != 1) {
                i = 9999;
            }
            result.imsdkRetCode = i;
            result.imsdkRetMsg = IMErrorDef.getErrorString(result.imsdkRetCode);
            this.listener.OnSetTag(result);
        }
    }

    public void onDeleteTagResult(Context context, int errorCode, String tagName) {
        int errorCode2;
        String text;
        int i = 1;
        if (errorCode == 0) {
            errorCode2 = 1;
            text = "\"" + tagName + "\"删除成功";
        } else {
            errorCode2 = 3;
            text = "\"" + tagName + "\"删除失败,错误码：" + 3;
        }
        IMLogger.d("IMXGPushReceiver:" + text);
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            IMResult result = new IMResult();
            result.retCode = errorCode2;
            result.retMsg = tagName;
            result.thirdRetCode = errorCode2;
            if (errorCode2 != 1) {
                i = 9999;
            }
            result.imsdkRetCode = i;
            result.imsdkRetMsg = IMErrorDef.getErrorString(result.imsdkRetCode);
            this.listener.OnDeleteTag(result);
        }
    }

    public void onTextMessage(Context context, XGPushTextMessage message) {
        IMLogger.d("IMXGPushReceiver:" + ("收到消息:" + message.toString()));
        String customContent = message.getCustomContent() == null ? "" : message.getCustomContent();
        if (!(customContent == null || customContent.length() == 0)) {
            try {
                JSONObject obj = new JSONObject(customContent);
                if (!obj.isNull("key")) {
                    IMLogger.d(LogTag, "get custom value:" + obj.getString("key"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            this.listener.OnNotification(customContent);
        }
    }

    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
        IMLogger.d("IMXGPushReceiver:" + ("通知被打开 :" + message));
        String customContent = message.getCustomContent() == null ? "" : message.getCustomContent();
        if (!(customContent == null || customContent.length() == 0)) {
            try {
                JSONObject obj = new JSONObject(customContent);
                if (!obj.isNull("key")) {
                    IMLogger.d(LogTag, "get custom value:" + obj.getString("key"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null) {
            this.listener.OnNotifactionClick(customContent);
        }
    }

    public void onNotifactionShowedResult(Context context, XGPushShowedResult notifiShowedRlt) {
        IMLogger.d("IMXGPushReceiver:" + ("已展示通知 :" + notifiShowedRlt));
        if (this.listener == null) {
            this.listener = XGPushHelper.getInstance().getPushListener();
        }
        if (this.listener != null && notifiShowedRlt != null) {
            this.listener.OnNotifactionShow(notifiShowedRlt.toString());
        }
    }
}
