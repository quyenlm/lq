package com.tencent.imsdk.webview.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.imsdk.api.IMSystem;
import com.tencent.imsdk.framework.request.HttpResponseHandler;
import com.tencent.imsdk.framework.request.IMSDKServer;
import com.tencent.imsdk.sns.api.IMLogin;
import com.tencent.imsdk.sns.base.IMLoginResult;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.webview.request.WebviewGetTicketParams;
import com.tencent.imsdk.webview.request.WebviewGetTicketRequest;
import com.tencent.imsdk.webview.request.WebviewGetTicketResponse;

public class IMWebViewManager {
    private static volatile IMWebViewManager instance = null;
    private final int WEBVIEW_HANDLE_GETTICKET = 1001;
    /* access modifiers changed from: private */
    public IMWebviewGetTickInterface callback;
    private Handler mBgHandler;
    private Handler mainHanlder = new Handler(Looper.getMainLooper());

    private IMWebViewManager() {
    }

    public static IMWebViewManager getInstance() {
        if (instance == null) {
            synchronized (IMWebViewManager.class) {
                if (instance == null) {
                    instance = new IMWebViewManager();
                }
            }
        }
        return instance;
    }

    public void init() {
        this.mBgHandler = new HandlerInBG(IMSystem.getInstance().getLooper(1));
    }

    public Handler getMainHandler() {
        return this.mainHanlder;
    }

    private class HandlerInBG extends Handler {
        public HandlerInBG(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1001:
                    IMLogger.d("IMWebviewManager WEBVIEW_HANDLE_GETTICKET");
                    IMWebViewManager.this.handleGetTicketMessage(msg);
                    return;
                default:
                    return;
            }
        }
    }

    public void getTicketRequest(IMWebviewGetTickInterface callback2) {
        this.callback = callback2;
        try {
            Message msg = this.mBgHandler.obtainMessage();
            msg.what = 1001;
            this.mBgHandler.sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
            IMLogger.d(e.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public void handleGetTicketMessage(Message msg) {
        try {
            IMLogger.d("IMWebviewManager handleGetTicketMessage");
            IMLoginResult loginResult = IMLogin.getLoginResult();
            if (loginResult.retCode == 1 || loginResult.imsdkRetCode == 1) {
                WebviewGetTicketParams params = new WebviewGetTicketParams();
                params.mInnerToken = loginResult.guidToken;
                params.mOpenId = loginResult.openId;
                params.mGameId = String.valueOf(loginResult.gameId);
                params.mChannel = String.valueOf(loginResult.channelId);
                params.mPlatform = "2";
                WebviewGetTicketRequest checkRequest = new WebviewGetTicketRequest(params, new WebviewGetTicketResponseHandler());
                IMLogger.d(" handleSetTagMessage doRequest start");
                IMSDKServer.getInstance().doRequest(checkRequest);
                return;
            }
            IMLogger.w("you need login before webTicket");
            this.callback.onGetTicketFail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class WebviewGetTicketResponseHandler implements HttpResponseHandler<WebviewGetTicketResponse> {
        public WebviewGetTicketResponseHandler() {
        }

        public void onResponse(WebviewGetTicketResponse response) {
            IMLogger.d("PushSetTagResponseHandler response==" + response.toString());
            if (!TextUtils.isEmpty(response.ticket)) {
                IMWebViewManager.this.callback.onGetTicketSuccess(IMWebViewManager.this.getVaildKeyField(response.ticket));
            } else {
                IMWebViewManager.this.callback.onGetTicketFail();
            }
        }
    }

    /* access modifiers changed from: private */
    public String getVaildKeyField(String ticket) {
        try {
            if (TextUtils.isEmpty(ticket)) {
                return "";
            }
            IMLoginResult loginResult = IMLogin.getLoginResult();
            if (!(loginResult.retCode == 1 || loginResult.imsdkRetCode == 1)) {
                IMLogger.w("you need login before webTicket");
                this.callback.onGetTicketFail();
            }
            return ticket;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
