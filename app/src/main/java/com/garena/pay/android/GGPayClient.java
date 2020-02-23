package com.garena.pay.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ProgressBar;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.PaymentChannelStorage;
import com.beetalk.sdk.data.Result;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.helper.StringUtils;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.garena.msdk.R;
import com.garena.pay.android.data.GGChannelRequest;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.helper.NetworkUtils;
import com.garena.pay.android.view.DenominationView;
import com.garena.pay.android.view.GGBaseWebResult;
import com.garena.pay.android.view.GatewayView;
import com.garena.pay.android.view.WebViewJSInterface;
import com.google.android.gms.drive.DriveFile;
import com.tencent.component.net.download.multiplex.http.ContentType;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class GGPayClient implements Serializable {
    private static final long serialVersionUID = 1;
    transient Activity context;
    GGPayRequestHandler currentHandler = null;
    LinkedHashMap<String, GGPayRequestHandler> handlers = new LinkedHashMap<>();
    private String imsiCallback = "";
    /* access modifiers changed from: private */
    public PaymentState mPaymentState = PaymentState.INIT;
    private transient WebViewJSInterface mWebBridge;
    transient OnPaymentComplete paymentCompleteListener;
    GGPayRequest pendingRequest;
    private transient ProgressDialog progress;
    private boolean retryable = false;
    private transient WebViewJSInterface.CmdHandler webCmdHandler = new WebViewJSInterface.CmdHandler() {
        public boolean isCmdHandlable(String cmd) {
            if (TextUtils.isEmpty(cmd) || (!cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_GET_IMSI) && !cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_SEND_SMS) && !cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_SEND_SMS_INAPP) && !cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_REQUEST_PERMISSION_SETTING))) {
                return false;
            }
            return true;
        }

        public boolean handleCmd(WebViewJSInterface bridge, String cmd, JSONObject data, String callback) {
            if (cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_REQUEST_PERMISSION_SETTING)) {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", GGPayClient.this.context.getPackageName(), (String) null));
                intent.addFlags(DriveFile.MODE_READ_ONLY);
                GGPayClient.this.context.startActivity(intent);
                return true;
            } else if (!cmd.equalsIgnoreCase(SDKConstants.WEB_JS_CMD.CMD_SEND_SMS) && !cmd.equals(SDKConstants.WEB_JS_CMD.CMD_SEND_SMS_INAPP)) {
                return false;
            } else {
                String phone = data.optString("phoneNum");
                String text = data.optString(ContentType.TYPE_TEXT);
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(text)) {
                    return true;
                }
                Intent intent2 = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + phone));
                intent2.putExtra("sms_body", text);
                GGPayClient.this.context.startActivity(intent2);
                return true;
            }
        }
    };

    public interface OnPaymentComplete {
        void onPaymentComplete(Result result);
    }

    public void setWebBridge(WebViewJSInterface bridge) {
        this.mWebBridge = bridge;
    }

    public WebViewJSInterface.CmdHandler getWebCmdHandler() {
        return this.webCmdHandler;
    }

    public void onIMSIReceived(String imsi, boolean granted) {
        if (this.mWebBridge != null) {
            GGBaseWebResult result = new GGBaseWebResult(granted ? 0 : 1);
            result.addData("imsi", imsi);
            this.mWebBridge.sendResultBack(this.imsiCallback, result);
        }
    }

    private enum PaymentState {
        INIT(0),
        CHOOSE_CHANNEL(1),
        CHOOSE_ITEM(2),
        READY_TO_PAY(3),
        DONE(4);
        
        private int mValue;

        private PaymentState(int value) {
            this.mValue = value;
        }

        public int getIntValue() {
            return this.mValue;
        }

        public PaymentState stateOfValue(int value) {
            if (value < 0 || value > 4) {
                return DONE;
            }
            return values()[value];
        }
    }

    public void setContext(Activity context2) {
        this.context = context2;
    }

    public void setPaymentCompleteListener(OnPaymentComplete paymentCompleteListener2) {
        this.paymentCompleteListener = paymentCompleteListener2;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return requestCode == this.pendingRequest.getRequestCode().intValue() && this.currentHandler.onActivityResult(requestCode, resultCode, data, this.pendingRequest);
    }

    public void onNewIntent(Intent intent) {
        if (this.currentHandler != null) {
            this.currentHandler.onNewIntent(intent);
        }
    }

    public void startOrResumePayment(GGPayRequest request) {
        if (this.mPaymentState != PaymentState.DONE) {
            if (this.pendingRequest == null || this.mPaymentState == PaymentState.INIT) {
                this.mPaymentState = PaymentState.CHOOSE_CHANNEL;
                commencePayment(request);
                return;
            }
            continuePayment(request);
        }
    }

    /* access modifiers changed from: private */
    public void continuePayment(GGPayRequest request) {
        if (request.getRequestId() != this.pendingRequest.getRequestId()) {
            failedToPay(GGErrorCode.PAYMENT_REQUEST_ID_MISMATCH);
        }
        if (!StringUtils.isEmpty(request.getChosenChannelId())) {
            this.currentHandler = this.handlers.get(String.valueOf(request.getChosenChannelId()));
        }
        if (this.currentHandler == null && this.handlers.size() == 1 && this.mPaymentState == PaymentState.CHOOSE_CHANNEL) {
            for (GGPayRequestHandler theHandler : this.handlers.values()) {
                this.currentHandler = theHandler;
            }
        }
        if (this.currentHandler != null && request.getChosenDenomination() == null) {
            if (this.currentHandler.isDirectPay()) {
                this.mPaymentState = PaymentState.READY_TO_PAY;
            } else {
                this.pendingRequest.setDenominations(this.currentHandler.getBuyableItems());
                this.mPaymentState = PaymentState.CHOOSE_ITEM;
            }
        }
        if (!(this.currentHandler == null || request.getChosenDenomination() == null)) {
            request.setDenominations(this.currentHandler.getBuyableItems());
            this.mPaymentState = PaymentState.READY_TO_PAY;
        }
        switch (this.mPaymentState) {
            case CHOOSE_ITEM:
                showDenominations();
                return;
            case CHOOSE_CHANNEL:
                showHandlers();
                return;
            case READY_TO_PAY:
                boolean succeed = false;
                if (this.currentHandler != null) {
                    succeed = this.currentHandler.startPay(this.pendingRequest);
                }
                if (!succeed) {
                    failedToPay(GGErrorCode.PAYMENT_CANNOT_START_ACTIVITY);
                }
                this.mPaymentState = PaymentState.DONE;
                return;
            default:
                return;
        }
    }

    public void onHandlerResult(Result result) {
        if (result != null) {
            if (result.getResultCode() != Result.ResultCode.ERROR || !GGErrorCode.PAYMENT_USER_CANCELLED.getCode().equals(result.getErrorCode()) || this.pendingRequest == null || !this.retryable) {
                notifyListener(result);
                return;
            }
            this.mPaymentState = PaymentState.INIT;
            this.currentHandler = null;
            this.pendingRequest.setChosenChannelId("");
            this.pendingRequest.setChosenDenomination((GGPayment.Denomination) null);
            startOrResumePayment(this.pendingRequest);
        }
    }

    /* access modifiers changed from: private */
    public void failedToPay(GGErrorCode errorCode) {
        notifyListener(Result.createErrorResult(this.pendingRequest, errorCode, errorCode.getStringValue()));
    }

    private void notifyListener(Result outcome) {
        if (this.paymentCompleteListener != null) {
            this.paymentCompleteListener.onPaymentComplete(outcome);
        }
    }

    private void showHandlers() {
        this.retryable = true;
        GatewayView.dismiss(false);
        DenominationView.dismiss(false);
        GatewayView.setCallback(new GatewayView.GatewayViewCallback() {
            public void onGatewayChosen(GGPayRequestHandler handler) {
                PaymentState unused = GGPayClient.this.mPaymentState = PaymentState.CHOOSE_ITEM;
                GGPayClient.this.currentHandler = handler;
                if (!GGPayClient.this.pendingRequest.isUseDefaultItemList()) {
                    GGPayClient.this.pendingRequest.setDenominations(GGPayClient.this.currentHandler.getBuyableItems());
                }
                GGPayClient.this.continuePayment(GGPayClient.this.pendingRequest);
            }

            public void onDismissed() {
                if (GGPayClient.this.paymentCompleteListener != null) {
                    GGPayClient.this.paymentCompleteListener.onPaymentComplete(Result.createErrorResult(GGPayClient.this.pendingRequest, GGErrorCode.PAYMENT_USER_CANCELLED, GGErrorCode.PAYMENT_USER_CANCELLED.getStringValue()));
                }
            }
        });
        GatewayView.initialize(getActivity(), this.handlers);
        GatewayView.showAtCenter(getActivity().findViewById(R.id.main_layout));
    }

    private void showDenominations() {
        GatewayView.dismiss(false);
        DenominationView.dismiss(false);
        DenominationView.setCallback(new DenominationView.DenominationCallback() {
            public void onDenominationChosen(GGPayment.Denomination denomination) {
                PaymentState unused = GGPayClient.this.mPaymentState = PaymentState.READY_TO_PAY;
                GGPayClient.this.pendingRequest.setChosenDenomination(denomination);
                GGPayClient.this.continuePayment(GGPayClient.this.pendingRequest);
                BBLogger.d("We have a denomination chosen %s Continue with payment next step", denomination.getName());
            }

            public void onDismissed() {
                BBLogger.d("User Dismissed the Dialog Box. Therefore invoking failed to pay", new Object[0]);
                GGPayClient.this.failedToPay(GGErrorCode.PAYMENT_USER_CANCELLED);
            }
        });
        DenominationView.initialize(getActivity(), this.pendingRequest.getDenominations(), this.pendingRequest.getVirtualCurrency());
        DenominationView.showAtCenter(getActivity().findViewById(R.id.main_layout));
    }

    private void showProgress(boolean canCancel) {
        if (this.progress == null) {
            this.progress = new ProgressDialog(getActivity());
        }
        this.progress.setCancelable(canCancel);
        this.progress.show();
        this.progress.setContentView(new ProgressBar(getActivity()));
    }

    /* access modifiers changed from: private */
    public void hideProgress() {
        if (this.progress != null) {
            this.progress.hide();
        }
    }

    /* access modifiers changed from: private */
    public void onHttpResultObtained(String response, GGPayRequest request) {
        BBLogger.d("Response Recd from Server: %s", response);
        try {
            hideProgress();
            if (request != null) {
                GGErrorCode code = Helper.getErrorCode(response);
                if (code != null) {
                    failedToPay(code);
                    return;
                }
                this.pendingRequest = request;
                this.handlers = parseWebHandlers(request, response);
                continuePayment(request);
            }
        } catch (JSONException e) {
            BBLogger.e(e);
            hideProgress();
            failedToPay(GGErrorCode.PAYMENT_INVALID_SERVER_RESPONSE);
        }
    }

    private List<GGPayment.Denomination> parseSKUItemList(String response) {
        try {
            return NetworkUtils.parseSKUItemList(new JSONObject(response));
        } catch (JSONException e) {
            BBLogger.e(e);
            failedToPay(GGErrorCode.PAYMENT_INVALID_SERVER_RESPONSE);
            return null;
        }
    }

    private void commencePayment(final GGPayRequest request) {
        showProgress(false);
        PaymentChannelStorage.getInstance().getPaymentChannels(request.getActivity(), false, new GGChannelRequest.GGChannelRequestBuilder().setAppId(request.getClientPaymentRequest().getAppId()).setLocale(request.getClientPaymentRequest().getLocale()).setOpenId(request.getClientPaymentRequest().getBuyerId()).setPlatform(request.getClientPaymentRequest().getPlatform()).setToken(request.getClientPaymentRequest().getToken()).setServerId(request.getClientPaymentRequest().getAppServerId().intValue()).setRoleId(request.getClientPaymentRequest().getRoleId().intValue()).Bulid(), new HttpRequestTask.StringCallback() {
            public void onResultObtained(String response) {
                GGPayClient.this.onHttpResultObtained(response, request);
            }

            public void onTimeout() {
                GGPayClient.this.hideProgress();
                GGPayClient.this.failedToPay(GGErrorCode.PAYMENT_NETWORK_CONNECTION_EXCEPTION);
            }
        });
    }

    private LinkedHashMap<String, GGPayRequestHandler> parseWebHandlers(GGPayRequest request, String response) throws JSONException {
        BBLogger.d("JSON From Payment Server %s", response);
        List<GGPayment.PaymentChannel> channelList = NetworkUtils.parseRebatePaymentChannelList(response, request.getClientPaymentRequest().getRebateId());
        List<GGPayment.Denomination> denominationList = parseSKUItemList(response);
        if (request.isUseDefaultItemList()) {
            request.setDenominations(denominationList);
        }
        return generateChannelHandlers(request, channelList);
    }

    private LinkedHashMap<String, GGPayRequestHandler> generateChannelHandlers(GGPayRequest request, List<GGPayment.PaymentChannel> channelList) throws JSONException {
        LinkedHashMap<String, GGPayRequestHandler> webHandlers = new LinkedHashMap<>();
        if (channelList != null) {
            for (GGPayment.PaymentChannel paymentChannel : channelList) {
                if (paymentChannel.getChannelId().equalsIgnoreCase(String.valueOf(SDKConstants.PaymentProvider.GOOGLE_PROVIDER_ID))) {
                    Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
                    serviceIntent.setPackage("com.android.vending");
                    try {
                        if (!getActivity().getPackageManager().queryIntentServices(serviceIntent, 0).isEmpty()) {
                            GGPayRequestHandler payRequestHandler = new GoogleIABPayRequestHandler(this, request);
                            payRequestHandler.setPaymentChannel(paymentChannel);
                            payRequestHandler.setChannelId(paymentChannel.getChannelId());
                            webHandlers.put(String.valueOf(SDKConstants.PaymentProvider.GOOGLE_PROVIDER_ID), payRequestHandler);
                        }
                    } catch (NullPointerException e) {
                    }
                }
            }
        }
        return webHandlers;
    }

    public void onBackPressed() {
        this.mPaymentState = this.mPaymentState.stateOfValue(this.mPaymentState.getIntValue() - 1);
        if (this.mPaymentState == PaymentState.INIT || this.pendingRequest == null) {
            this.context.finish();
        } else {
            continuePayment(this.pendingRequest);
        }
    }

    public Activity getActivity() {
        return this.context;
    }

    public void onDestroy() {
        if (this.mWebBridge != null) {
            this.mWebBridge.unregisterHandler();
        }
        for (GGPayRequestHandler handler : this.handlers.values()) {
            handler.onDestroy();
        }
        if (this.progress != null) {
            this.progress.dismiss();
            this.progress = null;
        }
    }
}
