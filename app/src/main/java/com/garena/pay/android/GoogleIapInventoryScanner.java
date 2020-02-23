package com.garena.pay.android;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.helper.BBLogger;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.garena.pay.android.GoogleIapInventoryScanCallback;
import com.garena.pay.android.data.GoogleIapItemInfo;
import com.garena.pay.android.helper.DownloadTasks;
import com.garena.pay.android.inappbilling.IabException;
import com.garena.pay.android.inappbilling.IabHelper;
import com.garena.pay.android.inappbilling.IabResult;
import com.garena.pay.android.inappbilling.Inventory;
import com.garena.pay.android.inappbilling.Purchase;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleIapInventoryScanner {
    private static final String RESULT_SUCCESS = "0";
    private static final String TAG = "GoogleIapInventoryScanner";
    private final String mAccessToken;
    private final String mAppId;
    private final GoogleIapInventoryScanCallback mCallback;
    private final Context mContext;
    private IabHandler mHandler;
    private final int mRoleId;
    private final int mServerId;

    public GoogleIapInventoryScanner(Context context, String appId, String accessToken, int serverId, int roleId, GoogleIapInventoryScanCallback callback) {
        this.mContext = context;
        this.mAppId = appId;
        this.mAccessToken = accessToken;
        this.mServerId = serverId;
        this.mRoleId = roleId;
        this.mCallback = callback;
    }

    public void startScan() {
        if (this.mHandler != null) {
            this.mHandler.stop();
        }
        HandlerThread thread = new HandlerThread("google-iap-inventory-scanner");
        thread.start();
        this.mHandler = new IabHandler(this.mContext, thread, this.mAppId, this.mAccessToken, this.mServerId, this.mRoleId, this.mCallback);
        this.mHandler.start();
    }

    private static class IabHandler extends Handler implements IabHelper.OnIabSetupFinishedListener, IabHelper.OnConsumeFinishedListener {
        private static final int MSG_COMMIT = 3;
        private static final int MSG_CONSUME = 4;
        private static final int MSG_INIT = 1;
        private static final int MSG_QUERY = 2;
        private final String mAccessToken;
        private final String mAppId;
        private final GoogleIapInventoryScanCallback mCallback;
        private final IabHelper mHelper;
        private boolean mIsConsuming;
        private int mNumPurchaseToConsume;
        private final ArrayDeque<Purchase> mPendingPurchasesToConsume = new ArrayDeque<>();
        /* access modifiers changed from: private */
        public final List<GoogleIapInventoryScanCallback.Result> mResults = new ArrayList();
        private final int mRoleId;
        private final int mServerId;
        private final HandlerThread mThread;

        IabHandler(Context context, HandlerThread thread, String appId, String token, int serverId, int roleId, GoogleIapInventoryScanCallback callback) {
            super(thread.getLooper());
            this.mHelper = new IabHelper(context);
            this.mThread = thread;
            this.mAppId = appId;
            this.mAccessToken = token;
            this.mServerId = serverId;
            this.mRoleId = roleId;
            this.mCallback = callback;
        }

        /* access modifiers changed from: package-private */
        public void start() {
            if (this.mThread.isAlive()) {
                removeMessages(1);
                sendEmptyMessage(1);
            }
        }

        /* access modifiers changed from: package-private */
        public void stop() {
            if (this.mThread.isAlive()) {
                if (this.mCallback != null) {
                    notifyCallback(this.mCallback);
                }
                BBLogger.i("GoogleIapInventoryScanner stopping thread", new Object[0]);
                this.mHelper.disposeWhenFinished();
                removeCallbacksAndMessages((Object) null);
                this.mThread.quit();
            }
        }

        private void notifyCallback(@NonNull final GoogleIapInventoryScanCallback cb) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    BBLogger.i("GoogleIapInventoryScanner propagating result: %d items processed", Integer.valueOf(IabHandler.this.mResults.size()));
                    cb.onResult(IabHandler.this.mResults);
                }
            });
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    doInit();
                    return;
                case 2:
                    doQuery();
                    return;
                case 3:
                    doCommit(msg);
                    return;
                case 4:
                    doConsume(msg);
                    return;
                default:
                    return;
            }
        }

        private void doInit() {
            if (!this.mHelper.isSetupDone()) {
                BBLogger.i("GoogleIapInventoryScanner setup iab helper", new Object[0]);
                this.mHelper.startSetup(this);
            }
        }

        private void doQuery() {
            this.mResults.clear();
            this.mNumPurchaseToConsume = 0;
            if (!this.mHelper.isSetupDone()) {
                stop();
                return;
            }
            BBLogger.i("GoogleIapInventoryScanner start query inventory", new Object[0]);
            try {
                Inventory inventory = this.mHelper.queryInventory();
                if (inventory == null) {
                    BBLogger.i("GoogleIapInventoryScanner no inventory", new Object[0]);
                    stop();
                    return;
                }
                List<String> skuList = inventory.getAllOwnedSkus();
                if (skuList == null || skuList.isEmpty()) {
                    BBLogger.i("GoogleIapInventoryScanner inventory empty", new Object[0]);
                    stop();
                    return;
                }
                for (String sku : skuList) {
                    Purchase purchase = inventory.getPurchase(sku);
                    if (purchase != null) {
                        this.mNumPurchaseToConsume++;
                        BBLogger.d("GoogleIapInventoryScanner queuing up commit-able purchase: %s", purchase.getSku());
                        Message msg = obtainMessage();
                        msg.what = 3;
                        msg.obj = purchase;
                        sendMessage(msg);
                    }
                }
                BBLogger.d("GoogleIapInventoryScanner %d purchases to commit/consume", Integer.valueOf(this.mNumPurchaseToConsume));
                tryTeardown();
            } catch (IabException e) {
                BBLogger.e(e);
                stop();
            }
        }

        private void doCommit(Message msg) {
            String str;
            if (msg.obj instanceof Purchase) {
                Purchase purchase = (Purchase) msg.obj;
                Map<String, String> params = DownloadTasks.buildGooglePurchaseCommitParams(purchase, this.mAccessToken, this.mAppId, this.mServerId, this.mRoleId, false);
                BBLogger.i("GoogleIapInventoryScanner start committing sku %s", purchase.getSku());
                BBLogger.r(GoogleIapInventoryScanner.TAG, "gop commit sku %s", purchase.getSku());
                HttpRequestTask.StringResult result = DownloadTasks.commitGooglePaymentSync(params);
                if (result.hasTimedOut) {
                    BBLogger.e("GoogleIapInventoryScanner gop commit sku %s timed out", purchase.getSku());
                    this.mResults.add(GoogleIapInventoryScanCallback.Result.error(new GoogleIapItemInfo.Builder().itemSku(purchase.getSku()).isPromotion(TextUtils.isEmpty(purchase.getDeveloperPayload())).build(), SDKConstants.ERR_GOP_PAY_COMMIT.ERR_COMMIT_TIMEOUT));
                    this.mNumPurchaseToConsume--;
                    tryTeardown();
                    return;
                }
                String response = result.response;
                BBLogger.i("GoogleIapInventoryScanner gop commit response: %s", response);
                BBLogger.r(GoogleIapInventoryScanner.TAG, "gop commit response for sku %s: %s", purchase.getSku(), response);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                } catch (JSONException e) {
                    BBLogger.e(e);
                }
                if (object == null) {
                    BBLogger.e("GoogleIapInventoryScanner gop commit sku %s failed", purchase.getSku());
                    this.mResults.add(GoogleIapInventoryScanCallback.Result.error(new GoogleIapItemInfo.Builder().itemSku(purchase.getSku()).isPromotion(TextUtils.isEmpty(purchase.getDeveloperPayload())).build(), "error_unknown"));
                    this.mNumPurchaseToConsume--;
                    tryTeardown();
                    return;
                }
                boolean isSuccess = "0".equals(object.optString(GGLiveConstants.PARAM.RESULT));
                String error = object.optString("error");
                if (isSuccess) {
                    String itemName = object.optString("item_name");
                    this.mResults.add(GoogleIapInventoryScanCallback.Result.success(new GoogleIapItemInfo.Builder().itemSku(purchase.getSku()).itemName(itemName).itemIconUrl(object.optString(SDKConstants.WEB_PAY.EXTRA_ICON)).appPointAmount(object.optInt(SDKConstants.WEB_PAY.EXTRA_AMOUNT)).isPromotion(TextUtils.isEmpty(purchase.getDeveloperPayload())).build()));
                } else {
                    GoogleIapItemInfo info = new GoogleIapItemInfo.Builder().itemSku(purchase.getSku()).isPromotion(TextUtils.isEmpty(purchase.getDeveloperPayload())).build();
                    List<GoogleIapInventoryScanCallback.Result> list = this.mResults;
                    if (TextUtils.isEmpty(error)) {
                        str = "error_unknown";
                    } else {
                        str = error;
                    }
                    list.add(GoogleIapInventoryScanCallback.Result.error(info, str));
                }
                if (!isSuccess && !SDKConstants.ERR_GOP_PAY_COMMIT.DUPLICATE_TRANSACTION.equals(error)) {
                    this.mNumPurchaseToConsume--;
                } else if (this.mIsConsuming) {
                    BBLogger.i("GoogleIapInventoryScanner queuing up purchase to commit: %s", purchase.getSku());
                    this.mPendingPurchasesToConsume.add(purchase);
                } else {
                    this.mIsConsuming = true;
                    Message consumeMsg = obtainMessage();
                    consumeMsg.what = 4;
                    consumeMsg.obj = purchase;
                    sendMessage(consumeMsg);
                }
                tryTeardown();
            }
        }

        private void tryTeardown() {
            if (this.mNumPurchaseToConsume <= 0) {
                stop();
            }
        }

        private void doConsume(Message msg) {
            if (msg.obj instanceof Purchase) {
                Purchase purchase = (Purchase) msg.obj;
                try {
                    BBLogger.i("GoogleIapInventoryScanner start consuming google product %s", purchase.getSku());
                    this.mHelper.consumeAsync(purchase, (IabHelper.OnConsumeFinishedListener) this);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    BBLogger.e(e);
                }
            }
        }

        public void onIabSetupFinished(IabResult result) {
            if (result.isFailure()) {
                stop();
            } else if (this.mThread.isAlive()) {
                sendEmptyMessage(2);
            }
        }

        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Object[] objArr = new Object[2];
            objArr[0] = purchase.getSku();
            objArr[1] = result.isSuccess() ? "success" : "failure";
            BBLogger.i("GoogleIapInventoryScanner consume google product [%s] %s", objArr);
            this.mNumPurchaseToConsume--;
            Purchase p = this.mPendingPurchasesToConsume.pollFirst();
            if (p == null) {
                BBLogger.i("GoogleIapInventoryScanner no more product to consume", new Object[0]);
                this.mIsConsuming = false;
                tryTeardown();
                return;
            }
            Message msg = obtainMessage();
            msg.what = 4;
            msg.obj = p;
            sendMessage(msg);
        }
    }
}
