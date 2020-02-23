package com.tencent.imsdk.unity.webview;

import android.app.Activity;
import com.tencent.imsdk.IMErrorMsg;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.webview.api.IMWebViewActionListener;
import com.tencent.imsdk.webview.api.IMWebview;
import com.tencent.imsdk.webview.api.IMWebviewGetTickInterface;
import com.tencent.imsdk.webview.request.WebviewActionResponse;
import com.unity3d.player.UnityPlayer;

public class WebViewHelper extends IMWebview {
    public static final int IMSDK_NOT_INITIALIZED = 17;
    public static final int IMSDK_SUCCESS = 1;
    public static final int IMSDK_SYSTEM_ERROR = 3;
    private static final String unityGameObject = "Tencent.iMSDK.IMWebViewLite";

    protected static void callbackByError(String unityCallbackFunction, int callbackTag, int retCode, String retMsg, int iMSDKRetCode, int thirdCode, String thirdMsg) {
        IMWebViewStatusResult imResult = new IMWebViewStatusResult(retCode, retMsg);
        imResult.imsdkRetCode = iMSDKRetCode;
        imResult.imsdkRetMsg = IMErrorMsg.getMessageByCode(imResult.imsdkRetCode);
        imResult.thirdRetCode = thirdCode;
        imResult.thirdRetMsg = thirdMsg;
        try {
            UnityPlayer.UnitySendMessage(unityGameObject, unityCallbackFunction, callbackTag + "|" + imResult.toUnityString());
        } catch (Exception ex) {
            IMLogger.e("send unity message failed : " + ex.getMessage());
        }
    }

    public static void getStatus(final int callbackTag) {
        if (currentContext == null) {
            callbackByError("OnGetStatus", callbackTag, 9, "call initialize first", 17, -1, "");
        } else {
            ((Activity) currentContext).runOnUiThread(new Runnable() {
                public void run() {
                    IMWebViewStatusResult result = new IMWebViewStatusResult(1);
                    result.imsdkRetCode = 1;
                    result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
                    result.isOpen = IMWebview.isActivated();
                    result.canGoBack = IMWebview.canGoBack();
                    result.canGoForward = IMWebview.canGoForward();
                    try {
                        UnityPlayer.UnitySendMessage(WebViewHelper.unityGameObject, "OnGetStatus", callbackTag + "|" + result.toUnityString());
                    } catch (Exception e) {
                        WebViewHelper.callbackByError("OnGetStatus", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                    }
                }
            });
        }
    }

    public static void getTicket(final int callbackTag) {
        getIMSDKTicket(new IMWebviewGetTickInterface() {
            public void onGetTicketSuccess(String ticket) {
                IMWebViewTicketResult result = new IMWebViewTicketResult(1);
                result.imsdkRetCode = 1;
                result.imsdkRetMsg = IMErrorMsg.getMessageByCode(result.imsdkRetCode);
                result.ticket = ticket;
                try {
                    UnityPlayer.UnitySendMessage(WebViewHelper.unityGameObject, "OnGetTicket", callbackTag + "|" + result.toUnityString());
                } catch (Exception e) {
                    WebViewHelper.callbackByError("OnGetTicket", callbackTag, 3, e.getMessage(), 3, -1, e.getMessage());
                }
            }

            public void onGetTicketFail() {
                WebViewHelper.callbackByError("OnGetTicket", callbackTag, 3, "unknown error", 3, -1, "unknown error");
            }
        });
    }

    public static void setActionListener() {
        setWebViewActionListener(new IMWebViewActionListener() {
            public void onAction(WebviewActionResponse result) {
                try {
                    UnityPlayer.UnitySendMessage(WebViewHelper.unityGameObject, "OnWebViewAction", result.toUnityString());
                } catch (Exception e) {
                    IMLogger.e("send unity get error : " + e.getMessage());
                }
            }
        });
    }

    public static void setActionListener(IMWebViewActionListener listener) {
        setWebViewActionListener(listener);
    }
}
