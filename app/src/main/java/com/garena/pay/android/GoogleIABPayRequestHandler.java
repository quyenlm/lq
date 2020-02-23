package com.garena.pay.android;

import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.Toast;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.data.Result;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.helper.Helper;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.msdk.R;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.helper.DownloadTasks;
import com.garena.pay.android.helper.UILoop;
import com.garena.pay.android.helper.Utils;
import com.garena.pay.android.inappbilling.IabBroadcastReceiver;
import com.garena.pay.android.inappbilling.IabHelper;
import com.garena.pay.android.inappbilling.IabResult;
import com.garena.pay.android.inappbilling.Inventory;
import com.garena.pay.android.inappbilling.Purchase;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleIABPayRequestHandler extends GGPayRequestHandler implements IabBroadcastReceiver.IabBroadcastListener {
    private static final String TAG = "payment-gp";
    private static final int TEN_SECONDS = 10000;
    private static final long serialVersionUID = 3;
    /* access modifiers changed from: private */
    public int mAppPointAmount = 0;
    /* access modifiers changed from: private */
    public transient IabBroadcastReceiver mBroadcastReceiver;
    /* access modifiers changed from: private */
    public transient IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            BBLogger.i("Finished Consumption.", new Object[0]);
            BBLogger.r(GoogleIABPayRequestHandler.TAG, "finish consumption.", new Object[0]);
            if (GoogleIABPayRequestHandler.this.mustNotify) {
                UILoop.getInstance().cancelPost(GoogleIABPayRequestHandler.this.submitTimer);
                Intent intent = new Intent();
                int amount = GoogleIABPayRequestHandler.this.mAppPointAmount;
                if (!(amount > 0 || GoogleIABPayRequestHandler.this.pendingRequest == null || GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination() == null)) {
                    amount = GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getAppPoints().intValue();
                }
                intent.putExtra(SDKConstants.WEB_PAY.EXTRA_AMOUNT, amount);
                BBLogger.i("OnConsumeFinished get developer payload %s", purchase.getDeveloperPayload());
                BBLogger.r(GoogleIABPayRequestHandler.TAG, "onConsumeFinished sku %s , payload %s", purchase.getSku(), purchase.getDeveloperPayload());
                if (TextUtils.isEmpty(GoogleIABPayRequestHandler.this.mCurrentCommitTxnId)) {
                    intent.putExtra(SDKConstants.WEB_PAY.EXTRA_TXN_ID, purchase.getOrderId());
                } else {
                    intent.putExtra(SDKConstants.WEB_PAY.EXTRA_TXN_ID, GoogleIABPayRequestHandler.this.mCurrentCommitTxnId);
                    String unused = GoogleIABPayRequestHandler.this.mCurrentCommitTxnId = null;
                }
                intent.putExtra("item_name", GoogleIABPayRequestHandler.this.mItemName);
                intent.putExtra(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID, GoogleIABPayRequestHandler.this.mRebateCardId);
                intent.putExtra(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS, GoogleIABPayRequestHandler.this.mRemainingDays);
                if (GoogleIABPayRequestHandler.this.pendingRequest != null) {
                    boolean unused2 = GoogleIABPayRequestHandler.super.onActivityResult(GoogleIABPayRequestHandler.this.pendingRequest.getRequestCode().intValue(), GoogleIABPayRequestHandler.this.notifyResultCode.intValue(), intent, GoogleIABPayRequestHandler.this.pendingRequest);
                }
                boolean unused3 = GoogleIABPayRequestHandler.this.mustNotify = false;
            }
        }
    };
    /* access modifiers changed from: private */
    public String mCurrentCommitTxnId;
    /* access modifiers changed from: private */
    public transient IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            BBLogger.i("Query inventory finished.", new Object[0]);
            BBLogger.r(GoogleIABPayRequestHandler.TAG, "query inventory Finished", new Object[0]);
            if (GoogleIABPayRequestHandler.this.mHelper != null) {
                if (result.isFailure()) {
                    GoogleIABPayRequestHandler.this.complain("Failed to query inventory: " + result);
                    return;
                }
                BBLogger.i("Query inventory was successful.", new Object[0]);
                BBLogger.r(GoogleIABPayRequestHandler.TAG, "query inventory successful", new Object[0]);
                List<String> ownedItems = inventory.getAllOwnedSkus();
                if (ownedItems != null) {
                    GoogleIABPayRequestHandler.this.commitCheck(inventory, ownedItems.iterator());
                }
                BBLogger.i("Initial inventory query finished.", new Object[0]);
            }
        }
    };
    /* access modifiers changed from: private */
    public transient IabHelper mHelper;
    /* access modifiers changed from: private */
    public String mItemName = "";
    /* access modifiers changed from: private */
    public transient IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            BBLogger.i("Purchase finished: " + result + ", purchase: " + purchase, new Object[0]);
            if (GoogleIABPayRequestHandler.this.mHelper != null) {
                if (result.getResponse() == -1005) {
                    UILoop.getInstance().cancelPost(GoogleIABPayRequestHandler.this.submitTimer);
                    GoogleIABPayRequestHandler.this.complain("User Cancelled", GGErrorCode.PAYMENT_USER_CANCELLED);
                } else if (result.isFailure()) {
                    if (result.getResponse() != 7) {
                        UILoop.getInstance().cancelPost(GoogleIABPayRequestHandler.this.submitTimer);
                        GoogleIABPayRequestHandler.this.complain("Error purchasing: " + result);
                    }
                } else if (!GoogleIABPayRequestHandler.this.verifyDeveloperPayload(purchase)) {
                    UILoop.getInstance().cancelPost(GoogleIABPayRequestHandler.this.submitTimer);
                    GoogleIABPayRequestHandler.this.complain("Error purchasing. Authenticity verification failed.");
                } else {
                    BBLogger.i("Purchase successful. Now delegating to the server for Verification", new Object[0]);
                    GoogleIABPayRequestHandler.this.delegateVerifyToServer(purchase);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public long mRebateCardId = 0;
    /* access modifiers changed from: private */
    public int mRemainingDays = 0;
    /* access modifiers changed from: private */
    public boolean mustNotify = false;
    /* access modifiers changed from: private */
    public Integer notifyResultCode = 0;
    /* access modifiers changed from: private */
    public GGPayRequest pendingRequest = null;
    /* access modifiers changed from: private */
    public boolean setupFailed = false;
    private transient IabHelper.OnIabSetupFinishedListener setupFinishedListener = new IabHelper.OnIabSetupFinishedListener() {
        public void onIabSetupFinished(IabResult result) {
            BBLogger.i("Setup finished.", new Object[0]);
            BBLogger.r(GoogleIABPayRequestHandler.TAG, "Setup finished", new Object[0]);
            if (!result.isSuccess()) {
                boolean unused = GoogleIABPayRequestHandler.this.setupFailed = true;
            }
            if (result.getResponse() == 3) {
                boolean unused2 = GoogleIABPayRequestHandler.this.v3BillingSupported = false;
            } else if (!result.isSuccess()) {
                GoogleIABPayRequestHandler.this.complain("Problem setting up in-app billing: " + result);
            } else if (GoogleIABPayRequestHandler.this.mHelper != null) {
                IabBroadcastReceiver unused3 = GoogleIABPayRequestHandler.this.mBroadcastReceiver = new IabBroadcastReceiver(GoogleIABPayRequestHandler.this);
                GoogleIABPayRequestHandler.this.client.context.registerReceiver(GoogleIABPayRequestHandler.this.mBroadcastReceiver, new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED"));
                BBLogger.d("Setup successful. Querying inventory.", new Object[0]);
                try {
                    GoogleIABPayRequestHandler.this.mHelper.queryInventoryAsync(GoogleIABPayRequestHandler.this.mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    GoogleIABPayRequestHandler.this.complain("Error querying inventory. Another async operation in progress.");
                }
            }
        }
    };
    final transient Runnable submitTimer = new Runnable() {
        public void run() {
            GoogleIABPayRequestHandler.this.complain("Failed to obtain result in the required time: 10 seconds");
        }
    };
    /* access modifiers changed from: private */
    public boolean v3BillingSupported = true;

    protected GoogleIABPayRequestHandler(GGPayClient client, GGPayRequest request) {
        super(client);
        this.pendingRequest = request;
        __init();
    }

    protected GoogleIABPayRequestHandler(GGPayClient client) {
        super(client);
        __init();
    }

    /* access modifiers changed from: private */
    public void delegateVerifyToServer(final Purchase purchase) {
        GGPayment clientPayRequest = this.pendingRequest.getClientPaymentRequest();
        Map<String, String> params = DownloadTasks.buildGooglePurchaseCommitParams(purchase, clientPayRequest.getToken(), clientPayRequest.getAppId(), clientPayRequest.getAppServerId().intValue(), clientPayRequest.getRoleId().intValue(), false);
        if (TextUtils.isEmpty(purchase.getSignature()) && purchase.getOriginalJson().contains(SDKConstants.ANDROID_TEST_PURCHASED)) {
            this.pendingRequest.setChosenDenomination(this.pendingRequest.getDenominationById(SDKConstants.TEST_PURCHASE_ITEM_ID));
        }
        BBLogger.r(TAG, "gop commit sku %s", purchase.getSku());
        DownloadTasks.commitGooglePayment(new HttpRequestTask.StringCallback() {
            /* JADX WARNING: Can't fix incorrect switch cases order */
            /* JADX WARNING: Code restructure failed: missing block: B:11:0x0056, code lost:
                if (r8.equals(com.beetalk.sdk.SDKConstants.ERR_GOP_PAY_COMMIT.ERR_SUBSCRIPTION_BINDING) != false) goto L_0x0039;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onResultObtained(java.lang.String r12) {
                /*
                    r11 = this;
                    r1 = 1
                    r6 = 0
                    java.lang.String r7 = "gop commit response: %s"
                    java.lang.Object[] r8 = new java.lang.Object[r1]
                    r8[r6] = r12
                    com.beetalk.sdk.helper.BBLogger.i(r7, r8)
                    java.lang.String r7 = "payment-gp"
                    java.lang.String r8 = "gop commit response for sku %s: %s"
                    r9 = 2
                    java.lang.Object[] r9 = new java.lang.Object[r9]
                    com.garena.pay.android.inappbilling.Purchase r10 = r9
                    java.lang.String r10 = r10.getSku()
                    r9[r6] = r10
                    r9[r1] = r12
                    com.beetalk.sdk.helper.BBLogger.r(r7, r8, r9)
                    com.garena.pay.android.GoogleIABPayRequestHandler$ServerResponse r5 = new com.garena.pay.android.GoogleIABPayRequestHandler$ServerResponse
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    r5.<init>(r12)
                    boolean r7 = r5.isError
                    if (r7 == 0) goto L_0x0086
                    java.lang.String r8 = r5.error
                    r7 = -1
                    int r9 = r8.hashCode()
                    switch(r9) {
                        case -242206913: goto L_0x0046;
                        case 193830714: goto L_0x0050;
                        default: goto L_0x0038;
                    }
                L_0x0038:
                    r1 = r7
                L_0x0039:
                    switch(r1) {
                        case 0: goto L_0x0059;
                        case 1: goto L_0x007c;
                        default: goto L_0x003c;
                    }
                L_0x003c:
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    java.lang.String r7 = r5.error
                    r6.complain(r7)
                L_0x0045:
                    return
                L_0x0046:
                    java.lang.String r9 = "error_duplicate_txn"
                    boolean r8 = r8.equals(r9)
                    if (r8 == 0) goto L_0x0038
                    r1 = r6
                    goto L_0x0039
                L_0x0050:
                    java.lang.String r9 = "error_subscription_binding"
                    boolean r8 = r8.equals(r9)
                    if (r8 == 0) goto L_0x0038
                    goto L_0x0039
                L_0x0059:
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    boolean unused = r7.mustNotify = r6
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    com.garena.pay.android.inappbilling.IabHelper r6 = r6.mHelper     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    com.garena.pay.android.inappbilling.Purchase r7 = r9     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    com.garena.pay.android.GoogleIABPayRequestHandler r8 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    com.garena.pay.android.inappbilling.IabHelper$OnConsumeFinishedListener r8 = r8.mConsumeFinishedListener     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    r6.consumeAsync((com.garena.pay.android.inappbilling.Purchase) r7, (com.garena.pay.android.inappbilling.IabHelper.OnConsumeFinishedListener) r8)     // Catch:{ IabAsyncInProgressException -> 0x0070 }
                    goto L_0x0045
                L_0x0070:
                    r0 = move-exception
                    com.beetalk.sdk.helper.BBLogger.e(r0)
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    java.lang.String r7 = "Error consuming. Another async operation in progress."
                    r6.complain(r7)
                    goto L_0x0045
                L_0x007c:
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    java.lang.String r7 = "binding subscription error"
                    com.garena.pay.android.GGErrorCode r8 = com.garena.pay.android.GGErrorCode.PAYMENT_ERROR_SUBSCRIPTION_BINDING
                    r6.complain(r7, r8)
                    goto L_0x0045
                L_0x0086:
                    org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x013d }
                    r4.<init>(r12)     // Catch:{ JSONException -> 0x013d }
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = "item_name"
                    java.lang.String r8 = r4.optString(r8)     // Catch:{ JSONException -> 0x013d }
                    java.lang.String unused = r7.mItemName = r8     // Catch:{ JSONException -> 0x013d }
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = "txn_id"
                    long r8 = r4.getLong(r8)     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ JSONException -> 0x013d }
                    java.lang.String unused = r7.mCurrentCommitTxnId = r8     // Catch:{ JSONException -> 0x013d }
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = "rebate_card_id"
                    long r8 = r4.optLong(r8)     // Catch:{ JSONException -> 0x013d }
                    long unused = r7.mRebateCardId = r8     // Catch:{ JSONException -> 0x013d }
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = "remaining_days"
                    int r8 = r4.optInt(r8)     // Catch:{ JSONException -> 0x013d }
                    int unused = r7.mRemainingDays = r8     // Catch:{ JSONException -> 0x013d }
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ JSONException -> 0x013d }
                    java.lang.String r8 = "app_point_amount"
                    int r8 = r4.getInt(r8)     // Catch:{ JSONException -> 0x013d }
                    int unused = r7.mAppPointAmount = r8     // Catch:{ JSONException -> 0x013d }
                L_0x00c6:
                    com.garena.pay.android.inappbilling.Purchase r7 = r9
                    java.lang.String r7 = r7.getSku()
                    java.lang.String r8 = "android.test.purchased"
                    boolean r3 = r7.equals(r8)
                    com.garena.pay.android.inappbilling.Purchase r7 = r9
                    java.lang.String r7 = r7.getDeveloperPayload()
                    boolean r2 = android.text.TextUtils.isEmpty(r7)
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    com.garena.pay.android.GGPayRequest r7 = r7.pendingRequest
                    if (r7 == 0) goto L_0x0161
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    com.garena.pay.android.GGPayRequest r7 = r7.pendingRequest
                    com.garena.pay.android.data.GGPayment$Denomination r7 = r7.getChosenDenomination()
                    if (r7 == 0) goto L_0x0161
                    com.garena.pay.android.inappbilling.Purchase r7 = r9
                    java.lang.String r7 = r7.getSku()
                    com.garena.pay.android.GoogleIABPayRequestHandler r8 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    com.garena.pay.android.GGPayRequest r8 = r8.pendingRequest
                    com.garena.pay.android.data.GGPayment$Denomination r8 = r8.getChosenDenomination()
                    java.lang.String r8 = r8.getItemId()
                    boolean r7 = r7.equals(r8)
                    if (r7 == 0) goto L_0x0161
                L_0x010a:
                    if (r3 != 0) goto L_0x0110
                    if (r2 != 0) goto L_0x0110
                    if (r1 == 0) goto L_0x0045
                L_0x0110:
                    java.lang.String r7 = "commit success, start consumption"
                    java.lang.Object[] r8 = new java.lang.Object[r6]
                    com.beetalk.sdk.helper.BBLogger.i(r7, r8)
                    java.lang.String r7 = "payment-gp"
                    java.lang.String r8 = "commit success, start consumption"
                    java.lang.Object[] r6 = new java.lang.Object[r6]
                    com.beetalk.sdk.helper.BBLogger.r(r7, r8, r6)
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    com.garena.pay.android.inappbilling.IabHelper r6 = r6.mHelper     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    com.garena.pay.android.inappbilling.Purchase r7 = r9     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    com.garena.pay.android.GoogleIABPayRequestHandler r8 = com.garena.pay.android.GoogleIABPayRequestHandler.this     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    com.garena.pay.android.inappbilling.IabHelper$OnConsumeFinishedListener r8 = r8.mConsumeFinishedListener     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    r6.consumeAsync((com.garena.pay.android.inappbilling.Purchase) r7, (com.garena.pay.android.inappbilling.IabHelper.OnConsumeFinishedListener) r8)     // Catch:{ IabAsyncInProgressException -> 0x0133 }
                    goto L_0x0045
                L_0x0133:
                    r0 = move-exception
                    com.garena.pay.android.GoogleIABPayRequestHandler r6 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    java.lang.String r7 = "Error consuming. Another async operation in progress."
                    r6.complain(r7)
                    goto L_0x0045
                L_0x013d:
                    r0 = move-exception
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    java.lang.String r8 = ""
                    java.lang.String unused = r7.mItemName = r8
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    r8 = 0
                    long unused = r7.mRebateCardId = r8
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    int unused = r7.mRemainingDays = r6
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    r8 = 0
                    java.lang.String unused = r7.mCurrentCommitTxnId = r8
                    com.garena.pay.android.GoogleIABPayRequestHandler r7 = com.garena.pay.android.GoogleIABPayRequestHandler.this
                    int unused = r7.mAppPointAmount = r6
                    com.beetalk.sdk.helper.BBLogger.e(r0)
                    goto L_0x00c6
                L_0x0161:
                    r1 = r6
                    goto L_0x010a
                */
                throw new UnsupportedOperationException("Method not decompiled: com.garena.pay.android.GoogleIABPayRequestHandler.AnonymousClass6.onResultObtained(java.lang.String):void");
            }

            public void onTimeout() {
                Toast.makeText(GoogleIABPayRequestHandler.this.client.getActivity(), R.string.hud_network_error, 0).show();
            }
        }, params);
    }

    private void __init() {
        BBLogger.r(TAG, "__init()", new Object[0]);
        this.mHelper = new IabHelper(this.client.getActivity());
        this.mHelper.enableDebugLogging(true);
        BBLogger.i("Starting setup.", new Object[0]);
        this.mHelper.startSetup(this.setupFinishedListener);
    }

    public int getImageResId() {
        return R.drawable.msdk_googleplay_icon;
    }

    public String getDisplayName() {
        return Utils.getString(this.client.getActivity(), R.string.text_google_in_app_purchases);
    }

    /* access modifiers changed from: private */
    public boolean verifyDeveloperPayload(Purchase gasPurchase) {
        return true;
    }

    /* access modifiers changed from: private */
    public void complain(String s, GGErrorCode code) {
        BBLogger.e(s, new Object[0]);
        this.client.onHandlerResult(Result.createErrorResult(this.pendingRequest, code, s));
    }

    /* access modifiers changed from: private */
    public void complain(String s) {
        BBLogger.e(s, new Object[0]);
        this.client.onHandlerResult(Result.createErrorResult(this.pendingRequest, GGErrorCode.PAYMENT_GENERAL_ERROR, s));
    }

    /* access modifiers changed from: private */
    public void initGooglePayment() {
        DownloadTasks.initGooglePayment(new HttpRequestTask.StringCallback() {
            public void onResultObtained(String response) {
                BBLogger.i("initGooglePayment Recd Reply from Server %s", response);
                try {
                    String response2 = new JSONObject(response).getString(SDKConstants.WEB_PAY.EXTRA_TXN_ID);
                    if (GoogleIABPayRequestHandler.this.mHelper == null || !GoogleIABPayRequestHandler.this.mHelper.isSetupDone()) {
                        GoogleIABPayRequestHandler.this.complain("Error launching purchase flow. Helper is not set up.");
                    } else if (GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getItemId().equals(SDKConstants.TEST_PURCHASE_ITEM_ID)) {
                        try {
                            if (GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().isSubscription()) {
                                GoogleIABPayRequestHandler.this.mHelper.launchSubscriptionPurchaseFlow(GoogleIABPayRequestHandler.this.client.getActivity(), SDKConstants.ANDROID_TEST_PURCHASED, GoogleIABPayRequestHandler.this.pendingRequest.getRequestCode().intValue(), GoogleIABPayRequestHandler.this.mPurchaseFinishedListener, response2);
                            } else {
                                GoogleIABPayRequestHandler.this.mHelper.launchPurchaseFlow(GoogleIABPayRequestHandler.this.client.getActivity(), SDKConstants.ANDROID_TEST_PURCHASED, GoogleIABPayRequestHandler.this.pendingRequest.getRequestCode().intValue(), GoogleIABPayRequestHandler.this.mPurchaseFinishedListener, response2);
                            }
                        } catch (IabHelper.IabAsyncInProgressException e) {
                            GoogleIABPayRequestHandler.this.complain("Error launching purchase flow. Another async operation in progress.");
                        }
                    } else {
                        try {
                            if (GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().isSubscription()) {
                                GoogleIABPayRequestHandler.this.mHelper.launchSubscriptionPurchaseFlow(GoogleIABPayRequestHandler.this.client.getActivity(), GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getItemId(), GoogleIABPayRequestHandler.this.pendingRequest.getRequestCode().intValue(), GoogleIABPayRequestHandler.this.mPurchaseFinishedListener, response2);
                            } else {
                                GoogleIABPayRequestHandler.this.mHelper.launchPurchaseFlow(GoogleIABPayRequestHandler.this.client.getActivity(), GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getItemId(), GoogleIABPayRequestHandler.this.pendingRequest.getRequestCode().intValue(), GoogleIABPayRequestHandler.this.mPurchaseFinishedListener, response2);
                            }
                        } catch (IabHelper.IabAsyncInProgressException e2) {
                            GoogleIABPayRequestHandler.this.complain("Error launching purchase flow. Another async operation in progress.");
                        }
                    }
                } catch (JSONException e3) {
                    BBLogger.e(e3);
                    GoogleIABPayRequestHandler.this.complain("Server did not reply correctly.");
                }
            }

            public void onTimeout() {
                Toast.makeText(GoogleIABPayRequestHandler.this.client.getActivity(), R.string.hud_network_error, 0).show();
            }
        }, buildInitRequestParams());
    }

    /* access modifiers changed from: private */
    public void commitCheck(final Inventory inventory, final Iterator<String> ownedSkuIterator) {
        if (ownedSkuIterator.hasNext()) {
            String sku = ownedSkuIterator.next();
            final Purchase purchase = inventory.getPurchase(sku);
            BBLogger.r(TAG, "user own, sku %s, orderid %s, item type %s, token %s, payload %s", sku, purchase.getOrderId(), purchase.getItemType(), purchase.getToken(), purchase.getDeveloperPayload());
            if (TextUtils.equals(purchase.getItemType(), IabHelper.ITEM_TYPE_SUBS) && !purchase.isAutoRenewing()) {
                commitCheck(inventory, ownedSkuIterator);
            } else if (verifyDeveloperPayload(purchase)) {
                GGPayment clientPayRequest = this.pendingRequest.getClientPaymentRequest();
                Map<String, String> params = DownloadTasks.buildGooglePurchaseCommitParams(purchase, clientPayRequest.getToken(), clientPayRequest.getAppId(), clientPayRequest.getAppServerId().intValue(), clientPayRequest.getRoleId().intValue(), true);
                if (TextUtils.isEmpty(purchase.getSignature()) && purchase.getOriginalJson().contains(SDKConstants.ANDROID_TEST_PURCHASED)) {
                    this.pendingRequest.setChosenDenomination(this.pendingRequest.getDenominationById(SDKConstants.TEST_PURCHASE_ITEM_ID));
                }
                BBLogger.r(TAG, "gop commit sku %s", purchase.getSku());
                DownloadTasks.commitGooglePayment(new HttpRequestTask.StringCallback() {
                    public void onResultObtained(String response) {
                        BBLogger.i("gop commit response: %s", response);
                        BBLogger.r(GoogleIABPayRequestHandler.TAG, "gop commit response for sku %s: %s", purchase.getSku(), response);
                        ServerResponse serverResponse = new ServerResponse(response);
                        if (serverResponse.isError) {
                            String access$2000 = serverResponse.error;
                            char c = 65535;
                            switch (access$2000.hashCode()) {
                                case -242206913:
                                    if (access$2000.equals(SDKConstants.ERR_GOP_PAY_COMMIT.DUPLICATE_TRANSACTION)) {
                                        c = 0;
                                        break;
                                    }
                                    break;
                                case 193830714:
                                    if (access$2000.equals(SDKConstants.ERR_GOP_PAY_COMMIT.ERR_SUBSCRIPTION_BINDING)) {
                                        c = 1;
                                        break;
                                    }
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    boolean unused = GoogleIABPayRequestHandler.this.mustNotify = false;
                                    try {
                                        GoogleIABPayRequestHandler.this.mHelper.consumeAsync(purchase, GoogleIABPayRequestHandler.this.mConsumeFinishedListener);
                                        return;
                                    } catch (IabHelper.IabAsyncInProgressException e) {
                                        BBLogger.e(e);
                                        GoogleIABPayRequestHandler.this.complain("Error consuming. Another async operation in progress.");
                                        return;
                                    }
                                case 1:
                                    GoogleIABPayRequestHandler.this.complain("binding subscription error", GGErrorCode.PAYMENT_ERROR_SUBSCRIPTION_BINDING);
                                    return;
                                default:
                                    GoogleIABPayRequestHandler.this.complain(serverResponse.error);
                                    return;
                            }
                        } else {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int result = jsonObject.getInt(GGLiveConstants.PARAM.RESULT);
                                if (!TextUtils.equals(purchase.getItemType(), IabHelper.ITEM_TYPE_SUBS) || result != 2) {
                                    String unused2 = GoogleIABPayRequestHandler.this.mItemName = jsonObject.optString("item_name");
                                    String unused3 = GoogleIABPayRequestHandler.this.mCurrentCommitTxnId = String.valueOf(jsonObject.getLong(SDKConstants.WEB_PAY.EXTRA_TXN_ID));
                                    long unused4 = GoogleIABPayRequestHandler.this.mRebateCardId = jsonObject.optLong(SDKConstants.WEB_PAY.EXTRA_REBATE_CARD_ID);
                                    int unused5 = GoogleIABPayRequestHandler.this.mRemainingDays = jsonObject.optInt(SDKConstants.WEB_PAY.EXTRA_REMAINING_DAYS);
                                    int unused6 = GoogleIABPayRequestHandler.this.mAppPointAmount = jsonObject.getInt(SDKConstants.WEB_PAY.EXTRA_AMOUNT);
                                    GoogleIABPayRequestHandler.this.pendingRequest.setChosenDenomination(GoogleIABPayRequestHandler.this.getPaymentChannel().getDenomination(purchase.getSku()));
                                    BBLogger.i("We have skuId %s. Consuming it", purchase.getSku());
                                    BBLogger.r(GoogleIABPayRequestHandler.TAG, "verify and consume sku %s.", purchase.getSku());
                                    boolean unused7 = GoogleIABPayRequestHandler.this.mustNotify = true;
                                    Integer unused8 = GoogleIABPayRequestHandler.this.notifyResultCode = -1;
                                    UILoop.getInstance().delayPost(GoogleIABPayRequestHandler.this.submitTimer, 10000);
                                    boolean isTestPurchase = purchase.getSku().equals(SDKConstants.ANDROID_TEST_PURCHASED);
                                    boolean isPromotionItem = TextUtils.isEmpty(purchase.getDeveloperPayload());
                                    boolean isMatchingPurchase = (GoogleIABPayRequestHandler.this.pendingRequest == null || GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination() == null || !purchase.getSku().equals(GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getItemId())) ? false : true;
                                    if (isTestPurchase || isPromotionItem || isMatchingPurchase) {
                                        BBLogger.i("commit success, start consumption", new Object[0]);
                                        BBLogger.r(GoogleIABPayRequestHandler.TAG, "commit success, start consumption", new Object[0]);
                                        try {
                                            GoogleIABPayRequestHandler.this.mHelper.consumeAsync(purchase, GoogleIABPayRequestHandler.this.mConsumeFinishedListener);
                                        } catch (IabHelper.IabAsyncInProgressException e2) {
                                            GoogleIABPayRequestHandler.this.complain("Error consuming. Another async operation in progress.");
                                        }
                                    }
                                } else {
                                    GoogleIABPayRequestHandler.this.commitCheck(inventory, ownedSkuIterator);
                                }
                            } catch (JSONException e3) {
                                String unused9 = GoogleIABPayRequestHandler.this.mItemName = "";
                                long unused10 = GoogleIABPayRequestHandler.this.mRebateCardId = 0;
                                int unused11 = GoogleIABPayRequestHandler.this.mRemainingDays = 0;
                                String unused12 = GoogleIABPayRequestHandler.this.mCurrentCommitTxnId = null;
                                int unused13 = GoogleIABPayRequestHandler.this.mAppPointAmount = 0;
                                BBLogger.e(e3);
                            }
                        }
                    }

                    public void onTimeout() {
                        Toast.makeText(GoogleIABPayRequestHandler.this.client.getActivity(), R.string.hud_network_error, 0).show();
                    }
                }, params);
            } else {
                commitCheck(inventory, ownedSkuIterator);
            }
        } else {
            BBLogger.i("gop commit check finished", new Object[0]);
        }
    }

    private void checkSubscription() {
        try {
            this.mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
                public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                    BBLogger.i("Subscription check: Query inventory finished.", new Object[0]);
                    BBLogger.r(GoogleIABPayRequestHandler.TAG, "Subscription check: query inventory Finished", new Object[0]);
                    if (GoogleIABPayRequestHandler.this.mHelper == null) {
                        GoogleIABPayRequestHandler.this.complain("Subscription check: IabHelper has been disposed: " + result);
                    } else if (result.isFailure()) {
                        GoogleIABPayRequestHandler.this.complain("Subscription check: Failed to query inventory: " + result);
                    } else {
                        BBLogger.i("Subscription check: Query inventory was successful.", new Object[0]);
                        BBLogger.r(GoogleIABPayRequestHandler.TAG, "Subscription check: query inventory successful", new Object[0]);
                        List<String> ownedItems = inventory.getAllOwnedSkus();
                        if (ownedItems != null) {
                            boolean foundPurchase = false;
                            Iterator<String> it = ownedItems.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                String sku = it.next();
                                if (TextUtils.equals(sku, GoogleIABPayRequestHandler.this.pendingRequest.getChosenDenomination().getItemId())) {
                                    Purchase purchase = inventory.getPurchase(sku);
                                    BBLogger.i("We have skuId %s. Consuming it", sku);
                                    BBLogger.r(GoogleIABPayRequestHandler.TAG, "verify and consume sku %s.", sku);
                                    if (purchase != null && purchase.isAutoRenewing() && GoogleIABPayRequestHandler.this.verifyDeveloperPayload(purchase)) {
                                        foundPurchase = true;
                                        boolean unused = GoogleIABPayRequestHandler.this.mustNotify = true;
                                        Integer unused2 = GoogleIABPayRequestHandler.this.notifyResultCode = -1;
                                        UILoop.getInstance().delayPost(GoogleIABPayRequestHandler.this.submitTimer, 10000);
                                        GoogleIABPayRequestHandler.this.delegateVerifyToServer(purchase);
                                        break;
                                    }
                                }
                            }
                            if (!foundPurchase) {
                                GoogleIABPayRequestHandler.this.initGooglePayment();
                            }
                        } else {
                            GoogleIABPayRequestHandler.this.initGooglePayment();
                        }
                        BBLogger.i("Subscription check: Initial inventory query finished.", new Object[0]);
                    }
                }
            });
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error querying inventory. Another async operation in progress.");
        }
    }

    public boolean startPay(final GGPayRequest request) {
        if (this.mHelper == null) {
            return false;
        }
        if (!this.mHelper.isSetupDone() && !this.setupFailed) {
            BBLogger.i("Google payment service has not setup done, delay 100ms", new Object[0]);
            UILoop.getInstance().delayPost(new Runnable() {
                public void run() {
                    GoogleIABPayRequestHandler.this.startPay(request);
                }
            }, 100);
            return true;
        } else if (!this.v3BillingSupported) {
            Toast.makeText(this.client.getActivity(), R.string.hud_billing_not_supported, 1).show();
            return false;
        } else {
            this.pendingRequest = request;
            if (request.getChosenDenomination().isSubscription()) {
                checkSubscription();
            } else {
                initGooglePayment();
            }
            return true;
        }
    }

    private Map<String, String> buildInitRequestParams() {
        HashMap<String, String> map = new HashMap<>();
        GGPayment clientPayRequest = this.pendingRequest.getClientPaymentRequest();
        map.put("app_id", clientPayRequest.getAppId());
        map.put("platform", String.valueOf(clientPayRequest.getPlatform()));
        map.put("open_id", clientPayRequest.getBuyerId());
        map.put(GGLiveConstants.PARAM.CLIENT_TYPE, String.valueOf(2));
        map.put("app_server_id", String.valueOf(clientPayRequest.getAppServerId()));
        map.put("app_role_id", String.valueOf(clientPayRequest.getRoleId()));
        map.put("access_token", clientPayRequest.getToken());
        map.put("id", Helper.generateNonce());
        BBLogger.i("Init Request Data %s", map.toString());
        return map;
    }

    /* access modifiers changed from: protected */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, GGPayRequest pendingRequest2) {
        BBLogger.i("onActivityResult(" + requestCode + "," + resultCode + "," + data, new Object[0]);
        if (this.mHelper == null) {
            return true;
        }
        this.mustNotify = true;
        this.notifyResultCode = Integer.valueOf(resultCode);
        UILoop.getInstance().delayPost(this.submitTimer, 10000);
        if (requestCode != pendingRequest2.getRequestCode().intValue() || !this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
            return false;
        }
        return true;
    }

    public void onDestroy() {
        try {
            if (this.mBroadcastReceiver != null) {
                this.client.context.unregisterReceiver(this.mBroadcastReceiver);
            }
        } catch (Exception e) {
            BBLogger.e(e);
        }
        if (this.mHelper != null) {
            this.mHelper.disposeWhenFinished();
        }
        this.mHelper = null;
        UILoop.getInstance().cancelPost(this.submitTimer);
    }

    public void receivedBroadcast() {
        BBLogger.d("Received broadcast notification. Querying inventory.", new Object[0]);
        try {
            this.mHelper.queryInventoryAsync(this.mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            BBLogger.e(e);
        }
    }

    private class ServerResponse {
        /* access modifiers changed from: private */
        public String error;
        /* access modifiers changed from: private */
        public boolean isError;
        private String response;

        public ServerResponse(String response2) {
            this.response = response2;
            try {
                JSONObject object = new JSONObject(response2);
                if (object.has(GGLiveConstants.PARAM.RESULT) && object.getString(GGLiveConstants.PARAM.RESULT).equals("success")) {
                    this.isError = false;
                    this.error = "";
                } else if (object.has("error")) {
                    this.isError = true;
                    this.error = object.getString("error");
                }
            } catch (JSONException e) {
                this.isError = true;
                this.error = "Cannot Parse Data from the Server";
            }
        }
    }
}
