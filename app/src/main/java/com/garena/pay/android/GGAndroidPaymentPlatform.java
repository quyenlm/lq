package com.garena.pay.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.beetalk.sdk.GGLoginSession;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.PaymentChannelStorage;
import com.beetalk.sdk.cache.RedeemCache;
import com.beetalk.sdk.data.Result;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.facebook.internal.AnalyticsEvents;
import com.garena.pay.android.GoogleIapDenominationFetcher;
import com.garena.pay.android.data.GGChannelRequest;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.data.GGRebateOptionsRequest;
import com.garena.pay.android.data.GGRedeemRequest;
import com.garena.pay.android.data.GGRedeemResponse;
import com.garena.pay.android.data.TransactionInfo;
import com.garena.pay.android.data.TransactionStatus;
import com.garena.pay.android.data.ValidationResult;
import com.garena.pay.android.data.VirtualCurrency;
import com.garena.pay.android.exception.ErrorException;
import com.garena.pay.android.exception.GGActivityNotFoundException;
import com.garena.pay.android.exception.ValidationException;
import com.garena.pay.android.helper.DownloadTasks;
import com.garena.pay.android.helper.NetworkUtils;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.inappbilling.Inventory;
import com.garena.pay.android.ndk.RebateOptionItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

public class GGAndroidPaymentPlatform {
    static final /* synthetic */ boolean $assertionsDisabled = (!GGAndroidPaymentPlatform.class.desiredAssertionStatus());
    private static volatile GGAndroidPaymentPlatform androidPay;
    private static Integer channelSource = 0;
    /* access modifiers changed from: private */
    public static GGDenominationResponseCallback denominationResponseCallback;
    private static HttpRequestTask.StringCallback httpsCallback = new HttpRequestTask.StringCallback() {
        public void onResultObtained(String response) {
            List<GGPayment.Denomination> itemList = null;
            Exception exception = null;
            if (response == null || response.length() <= 0) {
                exception = new ValidationException("Response was null or not correct");
            } else {
                itemList = GGAndroidPaymentPlatform.parseSKUItemList(response);
            }
            if (GGAndroidPaymentPlatform.denominationResponseCallback != null) {
                GGAndroidPaymentPlatform.denominationResponseCallback.onResultObtained(itemList, exception);
            }
        }

        public void onTimeout() {
            GGAndroidPaymentPlatform.denominationResponseCallback.onResultObtained((List<GGPayment.Denomination>) null, new TimeoutException("Connection Timed Out"));
        }
    };
    private static final Object lockObject = new Object();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public final List<GGPayResponseCallback> payResponseCallbacks = new ArrayList();
    private GGPayment pendingPayment;
    private Integer requestCode;
    private TransactionStatus status;

    public interface GGDenominationResponseCallback {
        void onResultObtained(List<GGPayment.Denomination> list, Exception exc);
    }

    public interface GGGetRebateIdListCallback {
        void onGetRebateIdList(int i, List<Long> list);
    }

    public interface GGGetRebateOptionsCallback {
        void onGetRebateOptions(int i, List<RebateOptionItem> list);
    }

    public interface GGOnRedeemCallback {
        void onRedeemResultObtained(int i, GGRedeemResponse gGRedeemResponse);
    }

    public interface GGPaymentOptionsCallback {
        void onResultObtained(List<GGPayment.PaymentChannel> list, Exception exc);
    }

    private GGAndroidPaymentPlatform() {
    }

    private static GGAndroidPaymentPlatform instance() {
        if (androidPay == null) {
            synchronized (GGAndroidPaymentPlatform.class) {
                if (androidPay == null) {
                    androidPay = new GGAndroidPaymentPlatform();
                }
            }
        }
        return androidPay;
    }

    public static Integer getChannelSource() {
        return channelSource;
    }

    public static void setChannelSource(Integer channelSource2) {
        channelSource = channelSource2;
    }

    public static void scanGoogleInAppPurchaseInventory(Context context, String appId, String accessToken, int serverId, int roleId, GoogleIapInventoryScanCallback callback) {
        new GoogleIapInventoryScanner(context, appId, accessToken, serverId, roleId, callback).startScan();
    }

    public static void processPayment(Activity context, GGPayment payment, GGPayResponseCallback callback) {
        processPayment(context, payment, callback, SDKConstants.DEFAULT_REQUEST_CODE);
    }

    public static void processPayment(Activity context, GGPayment payment, GGPayResponseCallback callback, int requestCode2) {
        BBLogger.r(context, "processPayment", "start", new Object[0]);
        processPayment(context, payment, callback, requestCode2, (GGPayRequest) null);
    }

    private static void processPayment(Activity context, GGPayment payment, GGPayResponseCallback callback, int requestCode2, GGPayRequest payRequest) {
        instance().addPayResponseCallback(callback);
        if (TextUtils.isEmpty(payment.getToken())) {
            instance().publishStatusChange(instance().status, TransactionStatus.CLOSED_WITH_ERROR, new ErrorException(GGErrorCode.GOP_ERROR_TOKEN.getStringValue(), GGErrorCode.GOP_ERROR_TOKEN.getCode()), instance().createTransactionStatus((Result) null, TransactionStatus.CLOSED_WITH_ERROR));
            return;
        }
        ValidationResult validationResult = verifyPaymentRequest(payment);
        if (validationResult.resultCode == ValidationResult.ResultCode.SUCCESS) {
            instance().requestCode = Integer.valueOf(requestCode2);
            instance().pendingPayment = payment;
            GGPayRequest request = payRequest;
            if (request == null) {
                request = new GGPayRequest(context);
            }
            commencePaymentFlow(context, request);
            return;
        }
        instance().publishStatusChange(TransactionStatus.OPENING, TransactionStatus.CLOSED_WITH_ERROR, new ValidationException("Error in handling request, validation of payment request failed. " + validationResult.errorMessage), instance().createTransactionStatus((Result) null, TransactionStatus.CLOSED_WITH_ERROR));
    }

    public static void getPaymentChannelList(final Activity context, final GGPayment paymentRequest, final GGPaymentOptionsCallback callback) {
        GGPayRequest request = new GGPayRequest(context);
        request.setClientPaymentRequest(paymentRequest);
        DownloadTasks.getPaymentChannels(new HttpRequestTask.StringCallback() {
            public void onResultObtained(String response) {
                if (response == null || response.length() <= 0) {
                    callback.onResultObtained((List<GGPayment.PaymentChannel>) null, new ValidationException("Response was null or not correct"));
                    return;
                }
                List<GGPayment.PaymentChannel> channelList = NetworkUtils.parseRebatePaymentChannelList(response, paymentRequest.getRebateId());
                if (channelList == null) {
                    callback.onResultObtained((List<GGPayment.PaymentChannel>) null, new Exception("parse response error"));
                } else if (paymentRequest.isConvertGooglePlayPrices()) {
                    GGAndroidPaymentPlatform.localizeChannelDenominations(context, channelList, callback);
                } else {
                    callback.onResultObtained(channelList, (Exception) null);
                }
            }

            public void onTimeout() {
                callback.onResultObtained((List<GGPayment.PaymentChannel>) null, new TimeoutException("Connection Timed Out"));
            }
        }, DownloadTasks.buildChannelGetParams(request));
    }

    /* access modifiers changed from: private */
    public static void localizeChannelDenominations(Activity context, final List<GGPayment.PaymentChannel> channelList, final GGPaymentOptionsCallback callback) {
        for (final GGPayment.PaymentChannel channel : channelList) {
            if (String.valueOf(SDKConstants.PaymentProvider.GOOGLE_PROVIDER_ID).equalsIgnoreCase(channel.getChannelId())) {
                List<String> productList = new ArrayList<>();
                List<String> subscriptionList = new ArrayList<>();
                for (GGPayment.Denomination denom : channel.getItems()) {
                    if (denom.isSubscription()) {
                        subscriptionList.add(denom.getItemId());
                    } else {
                        productList.add(denom.getItemId());
                    }
                }
                new GoogleIapDenominationFetcher(context, productList, subscriptionList, new GoogleIapDenominationFetcher.GoogleIapDenominationFetchCallback() {
                    public void onResult(Inventory result) {
                        List<GGPayment.Denomination> convertedDenoms = new ArrayList<>();
                        for (GGPayment.Denomination denom : channel.getItems()) {
                            String sku = denom.getItemId();
                            if (result.hasDetails(sku)) {
                                denom.setPrice(result.getSkuDetails(sku).getPrice());
                                convertedDenoms.add(denom);
                            }
                        }
                        channel.setItems(convertedDenoms);
                        callback.onResultObtained(channelList, (Exception) null);
                    }

                    public void onError(Exception e) {
                        callback.onResultObtained(channelList, (Exception) null);
                    }
                }).startFetch();
                return;
            }
        }
        callback.onResultObtained(channelList, (Exception) null);
    }

    @Deprecated
    public static void GGGetRebateIdList(Context context, Locale locale, int serverId, int roleId, GGGetRebateIdListCallback callback) {
        if (GGLoginSession.checkSessionValidity()) {
            GGChannelRequest.GGChannelRequestBuilder builder = new GGChannelRequest.GGChannelRequestBuilder();
            builder.setAppId(Helper.getMetaDataAppId(context)).setOpenId(GGLoginSession.getCurrentSession().getOpenId()).setPlatform(GGLoginSession.getCurrentSession().getPlatform()).setToken(GGLoginSession.getCurrentSession().getTokenValue().getAuthToken()).setServerId(serverId).setRoleId(roleId).setLocale(locale);
            GGGetRebateIdList(context, builder.Bulid(), callback);
            return;
        }
        callback.onGetRebateIdList(GGErrorCode.SESSION_NOT_INITIALIZED.getCode().intValue(), (List<Long>) null);
    }

    private static void GGGetRebateIdList(Context context, GGChannelRequest request, final GGGetRebateIdListCallback callback) {
        PaymentChannelStorage.getInstance().getPaymentChannels(context, true, request, new HttpRequestTask.StringCallback() {
            public void onResultObtained(String response) {
                List<Long> rebateIdList = NetworkUtils.parseRebateIdList(response);
                if (rebateIdList == null || rebateIdList.size() <= 0) {
                    callback.onGetRebateIdList(GGErrorCode.NETWORK_RESPONSE_PARSE_FAIL.getCode().intValue(), (List<Long>) null);
                } else {
                    callback.onGetRebateIdList(GGErrorCode.SUCCESS.getCode().intValue(), rebateIdList);
                }
            }

            public void onTimeout() {
                BBLogger.e("getRebateIdList request timeout", new Object[0]);
                callback.onGetRebateIdList(GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue(), (List<Long>) null);
            }
        });
    }

    public static void GGGetRebateOptions(Context context, int serverId, int roleId, Locale locale, GGGetRebateOptionsCallback callback) {
        if (GGLoginSession.checkSessionValidity()) {
            GGRebateOptionsRequest.GGRebateOptionsRequestBuilder builder = new GGRebateOptionsRequest.GGRebateOptionsRequestBuilder();
            builder.setAppId(Helper.getMetaDataAppId(context)).setOpenId(GGLoginSession.getCurrentSession().getOpenId()).setPlatform(GGLoginSession.getCurrentSession().getPlatform()).setToken(GGLoginSession.getCurrentSession().getTokenValue().getAuthToken()).setServerId(serverId).setRoleId(roleId).setLocale(locale);
            GGGetRebateOptions(context, builder.Build(), callback);
            return;
        }
        callback.onGetRebateOptions(GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue(), (List<RebateOptionItem>) null);
    }

    public static void GGGetRebateOptions(Context context, int serverId, int roleId, GGGetRebateOptionsCallback callback) {
        GGGetRebateOptions(context, serverId, roleId, (Locale) null, callback);
    }

    private static void GGGetRebateOptions(Context context, GGRebateOptionsRequest request, final GGGetRebateOptionsCallback callback) {
        DownloadTasks.getRebateOptions(new HttpRequestTask.StringCallback() {
            public void onResultObtained(String response) {
                GGErrorCode code = Helper.getErrorCode(response);
                if (code != null) {
                    callback.onGetRebateOptions(code.getCode().intValue(), (List<RebateOptionItem>) null);
                    return;
                }
                List<RebateOptionItem> rebateOptions = NetworkUtils.parseRebateOptions(response);
                if (rebateOptions == null || rebateOptions.size() <= 0) {
                    callback.onGetRebateOptions(GGErrorCode.NETWORK_RESPONSE_PARSE_FAIL.getCode().intValue(), (List<RebateOptionItem>) null);
                } else {
                    callback.onGetRebateOptions(GGErrorCode.SUCCESS.getCode().intValue(), rebateOptions);
                }
            }

            public void onTimeout() {
                BBLogger.e("getRebateOptions request timeout", new Object[0]);
                callback.onGetRebateOptions(GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue(), (List<RebateOptionItem>) null);
            }
        }, request.getParams(context));
    }

    public static void GGRedeem(Context context, GGRedeemRequest request, final GGOnRedeemCallback callback) {
        if (!GGLoginSession.checkSessionValidity()) {
            callback.onRedeemResultObtained(GGErrorCode.GOP_ERROR_TOKEN.getCode().intValue(), (GGRedeemResponse) null);
            return;
        }
        final RedeemCache redeemCache = new RedeemCache();
        final long rebateId = request.getRebateCardId();
        if (rebateId > 0 || redeemCache.isOkForRedeem()) {
            DownloadTasks.redeemRebateCard(new HttpRequestTask.StringCallback() {
                public void onResultObtained(String response) {
                    BBLogger.i("redeem response: %s", response);
                    GGErrorCode code = Helper.getErrorCode(response);
                    if (code != null && code != GGErrorCode.UNKNOWN_ERROR) {
                        callback.onRedeemResultObtained(code.getCode().intValue(), (GGRedeemResponse) null);
                    } else if (response.contains("error_not_available")) {
                        if (rebateId <= 0) {
                            redeemCache.setRedeemTime();
                        }
                        callback.onRedeemResultObtained(GGErrorCode.REDEEM_NOT_AVAILABLE.getCode().intValue(), (GGRedeemResponse) null);
                    } else if (response.contains("error_already_redeemed")) {
                        if (rebateId <= 0) {
                            redeemCache.setRedeemTime();
                        }
                        callback.onRedeemResultObtained(GGErrorCode.ALREADY_REDEEMED.getCode().intValue(), (GGRedeemResponse) null);
                    } else {
                        GGRedeemResponse resp = NetworkUtils.parseRedeemResult(response);
                        if (resp == null || resp.result != GGErrorCode.SUCCESS.getCode().intValue()) {
                            callback.onRedeemResultObtained(GGErrorCode.NETWORK_RESPONSE_PARSE_FAIL.getCode().intValue(), (GGRedeemResponse) null);
                            redeemCache.clearRedeemCache();
                            return;
                        }
                        if (rebateId <= 0) {
                            redeemCache.setRedeemTime();
                        }
                        callback.onRedeemResultObtained(GGErrorCode.SUCCESS.getCode().intValue(), resp);
                    }
                }

                public void onTimeout() {
                    callback.onRedeemResultObtained(GGErrorCode.NETWORK_REQUEST_TIME_OUT.getCode().intValue(), (GGRedeemResponse) null);
                    redeemCache.clearRedeemCache();
                }
            }, request.getParams());
        } else {
            callback.onRedeemResultObtained(GGErrorCode.REDEEM_LIMIT_REACHED.getCode().intValue(), (GGRedeemResponse) null);
        }
    }

    public static void getDenominationList(Activity context, GGPayment paymentRequest, GGDenominationResponseCallback callback) {
        denominationResponseCallback = callback;
        GGPayRequest request = new GGPayRequest(context);
        request.setClientPaymentRequest(paymentRequest);
        DownloadTasks.getPaymentChannels(httpsCallback, DownloadTasks.buildChannelGetParams(request));
    }

    public static void processPaymentWithItem(Activity context, GGPayment payment, GGPayResponseCallback callback, GGPayment.Denomination denomination, int requestCode2) {
        GGPayRequest request = new GGPayRequest(context);
        request.setUseDefaultItemList(true);
        if ($assertionsDisabled || denomination != null) {
            request.setChosenDenomination(denomination);
            processPayment(context, payment, callback, requestCode2, request);
            return;
        }
        throw new AssertionError();
    }

    public static void processPaymentWithChannelItem(Activity context, GGPayment payment, GGPayResponseCallback callback, GGPayment.Denomination denomination, String channelId, int requestCode2) {
        GGPayRequest request = new GGPayRequest(context);
        request.setChosenDenomination(denomination);
        request.setChosenChannelId(channelId);
        processPayment(context, payment, callback, requestCode2, request);
    }

    /* access modifiers changed from: private */
    public static List<GGPayment.Denomination> parseSKUItemList(String response) {
        try {
            return NetworkUtils.parseSKUItemList(new JSONObject(response));
        } catch (JSONException e) {
            BBLogger.e(e);
            return null;
        }
    }

    private static ValidationResult verifyPaymentRequest(GGPayment paymentRequest) {
        ValidationResult result = new ValidationResult();
        result.errorMessage = "";
        result.resultCode = ValidationResult.ResultCode.SUCCESS;
        if (Utils.isNullOrEmpty(paymentRequest.getBuyerId())) {
            return ValidationResult.createError("Buyer Id cannot be null or empty.");
        }
        if (Utils.isNullOrEmpty(paymentRequest.getAppId())) {
            return ValidationResult.createError("Client Id cannot be null or empty.");
        }
        return result;
    }

    private static void commencePaymentFlow(Context context, GGPayRequest request) {
        request.setClientId(instance().pendingPayment.getAppId());
        request.setRequestCode(instance().requestCode);
        request.setDenominations(instance().pendingPayment.getDenominations());
        VirtualCurrency virtualCurrency = new VirtualCurrency();
        virtualCurrency.setVirtualCurrencyName(instance().pendingPayment.getVirtualCurrencyName());
        virtualCurrency.setConversionRatio(instance().pendingPayment.getConversionRatio());
        request.setVirtualCurrency(virtualCurrency);
        request.setClientPaymentRequest(instance().pendingPayment);
        if (!checkIfPayActivityWorks(context, request)) {
            TransactionStatus previousStatus = instance().status;
            instance().status = TransactionStatus.CLOSED_WITH_ERROR;
            instance().publishStatusChange(previousStatus, instance().status, new GGActivityNotFoundException("Failed to validate presence of the payment Activity.Did you forget to include it in the Manifest?"), instance().createTransactionStatus(Result.createErrorResult(request, GGErrorCode.PAYMENT_CANNOT_START_ACTIVITY, "Failed to validate presence of the payment Activity"), instance().status));
        }
    }

    private static boolean checkIfPayActivityWorks(Context context, GGPayRequest request) {
        boolean activityExists;
        BBLogger.d("Starting Payment Activity for request %s", request.getRequestId());
        Intent intent = new Intent(context, GGPayActivity.class);
        intent.putExtra(SDKConstants.GG_EXTRA_PAY_REQUEST, request);
        intent.putExtra(SDKConstants.GG_EXTRA_PAY_APP_KEYHASH, Utils.getSignatureFingerprint(context));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            activityExists = true;
        } else {
            activityExists = false;
        }
        if (!activityExists) {
            return false;
        }
        request.getActivity().startActivityForResult(intent, request.getRequestCode().intValue());
        return true;
    }

    public static void onActivityResult(Intent data) {
        Result result;
        if (data == null) {
            result = null;
        } else {
            result = (Result) data.getSerializableExtra(SDKConstants.GG_EXTRA_RESULT_SERIALIZABLE);
        }
        if (result == null) {
            instance().publishStatusChange(instance().status, TransactionStatus.CLOSED_WITH_ERROR, new ErrorException(GGErrorCode.UNKNOWN_ERROR.getStringValue(), GGErrorCode.UNKNOWN_ERROR.getCode()), instance().createTransactionStatus((Result) null, TransactionStatus.CLOSED_WITH_ERROR));
        } else if (!Result.isError(result.getResultCode())) {
            instance().publishStatusChange(instance().status, TransactionStatus.PROCESSED, (Exception) null, instance().createTransactionStatus(result, TransactionStatus.PROCESSED));
        } else {
            instance().publishStatusChange(instance().status, TransactionStatus.CLOSED_WITH_ERROR, new ErrorException(result.getErrorMessage(), result.getErrorCode()), instance().createTransactionStatus(result, TransactionStatus.CLOSED_WITH_ERROR));
        }
    }

    private void publishStatusChange(TransactionStatus previousStatus, final TransactionStatus status2, final Exception e, final TransactionInfo info) {
        synchronized (lockObject) {
            try {
                final Runnable actualCallback = new Runnable() {
                    public void run() {
                        for (GGPayResponseCallback callback : GGAndroidPaymentPlatform.this.payResponseCallbacks) {
                            callback.onPaymentProcessed(status2, e, info);
                        }
                        GGAndroidPaymentPlatform.this.payResponseCallbacks.clear();
                    }
                };
                this.mHandler.post(new Runnable() {
                    public void run() {
                        actualCallback.run();
                    }
                });
            } catch (Exception exception) {
                BBLogger.e(exception);
            }
        }
    }

    private TransactionInfo createTransactionStatus(Result result, TransactionStatus status2) {
        TransactionInfo transactionStatus;
        if (result == null) {
            return new TransactionInfo("Cannot Get Result", status2, GGErrorCode.PAYMENT_BUNDLE_RESULT_INVALID.getCode(), Result.ResultCode.ERROR, this.pendingPayment);
        }
        switch (result.getResultCode()) {
            case SUCCESS:
                transactionStatus = new TransactionInfo("", status2, GGErrorCode.PAYMENT_NO_ERROR.getCode(), result.getResultCode(), this.pendingPayment);
                Map<String, String> data = result.getData();
                if (data != null) {
                    transactionStatus.setTransactionId(data.get(SDKConstants.WEB_PAY.EXTRA_TXN_ID));
                    transactionStatus.setIcon(data.get(SDKConstants.WEB_PAY.EXTRA_ICON));
                    transactionStatus.setName(data.get("item_name"));
                    transactionStatus.setAppPoints(Integer.valueOf(Integer.parseInt(data.get(SDKConstants.WEB_PAY.EXTRA_AMOUNT))));
                    try {
                        transactionStatus.setRebateId(Long.valueOf(Long.parseLong(data.get(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID))));
                    } catch (Exception e) {
                        BBLogger.e(e);
                    }
                    try {
                        transactionStatus.setRemainingDays(Integer.parseInt(data.get(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS)));
                        break;
                    } catch (Exception e2) {
                        BBLogger.e(e2);
                        break;
                    }
                }
                break;
            case CANCEL:
                transactionStatus = new TransactionInfo(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_CANCELLED, status2, result.getErrorCode(), result.getResultCode(), this.pendingPayment);
                break;
            case ERROR:
                transactionStatus = new TransactionInfo(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_CANCELLED, status2, result.getErrorCode(), result.getResultCode(), this.pendingPayment);
                break;
            default:
                transactionStatus = null;
                break;
        }
        return transactionStatus;
    }

    private void addPayResponseCallback(GGPayResponseCallback payResponseCallback) {
        this.payResponseCallbacks.add(payResponseCallback);
    }

    public static void GGSetEnvironment(SDKConstants.GGEnvironment environment) {
        SDKConstants.setSandboxMode(environment);
    }

    public static String GGGetSDKVersion() {
        return SDKConstants.VERSION.VERSION_NAME;
    }
}
