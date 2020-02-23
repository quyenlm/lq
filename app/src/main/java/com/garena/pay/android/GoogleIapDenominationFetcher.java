package com.garena.pay.android;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.pay.android.inappbilling.IabException;
import com.garena.pay.android.inappbilling.IabHelper;
import com.garena.pay.android.inappbilling.IabResult;
import com.garena.pay.android.inappbilling.Inventory;
import java.util.List;

public class GoogleIapDenominationFetcher {
    private final GoogleIapDenominationFetchCallback mCallback;
    private final Context mContext;
    private IabHandler mHandler;
    private final List<String> mProductList;
    private final List<String> mSubscriptionList;

    public interface GoogleIapDenominationFetchCallback {
        void onError(Exception exc);

        void onResult(Inventory inventory);
    }

    public GoogleIapDenominationFetcher(Context context, List<String> productList, List<String> subscriptionList, GoogleIapDenominationFetchCallback callback) {
        this.mContext = context;
        this.mProductList = productList;
        this.mSubscriptionList = subscriptionList;
        this.mCallback = callback;
    }

    public void startFetch() {
        if (this.mHandler != null) {
            this.mHandler.stop(new Exception("Error: Handler stopped"));
        }
        HandlerThread thread = new HandlerThread("google-iap-denomination-fetcher");
        thread.start();
        this.mHandler = new IabHandler(this.mContext, thread, this.mProductList, this.mSubscriptionList, this.mCallback);
        this.mHandler.start();
    }

    private static class IabHandler extends Handler implements IabHelper.OnIabSetupFinishedListener {
        private static final int MSG_INIT = 1;
        private static final int MSG_QUERY = 2;
        private final GoogleIapDenominationFetchCallback mCallback;
        private final IabHelper mHelper;
        private final List<String> mProductList;
        private Inventory mResults = null;
        private final List<String> mSubscriptionList;
        private final HandlerThread mThread;

        IabHandler(Context context, HandlerThread thread, List<String> productList, List<String> subscriptionList, GoogleIapDenominationFetchCallback callback) {
            super(thread.getLooper());
            this.mHelper = new IabHelper(context);
            this.mThread = thread;
            this.mProductList = productList;
            this.mSubscriptionList = subscriptionList;
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
        public void stop(Exception e) {
            if (this.mThread.isAlive()) {
                if (this.mCallback != null) {
                    if (this.mResults != null) {
                        returnResults(this.mCallback, this.mResults);
                    } else {
                        returnError(this.mCallback, e);
                    }
                }
                BBLogger.i("GoogleIapDenominationFetcher stopping thread", new Object[0]);
                this.mHelper.disposeWhenFinished();
                removeCallbacksAndMessages((Object) null);
                this.mThread.quit();
            }
        }

        private void returnResults(@NonNull final GoogleIapDenominationFetchCallback cb, final Inventory results) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    BBLogger.i("GoogleIapDenominationFetcher propagating result", new Object[0]);
                    cb.onResult(results);
                }
            });
        }

        private void returnError(@NonNull final GoogleIapDenominationFetchCallback cb, final Exception e) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() {
                    BBLogger.i("GoogleIapDenominationFetcher propagating error: %s", e.getMessage());
                    cb.onError(e);
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
                default:
                    return;
            }
        }

        private void doInit() {
            if (!this.mHelper.isSetupDone()) {
                BBLogger.i("GoogleIapDenominationFetcher setup iab helper", new Object[0]);
                this.mHelper.startSetup(this);
            }
        }

        private void doQuery() {
            if (!this.mHelper.isSetupDone()) {
                stop(new Exception("Error: failed to setup iab helper"));
                return;
            }
            BBLogger.i("GoogleIapDenominationFetcher start query inventory", new Object[0]);
            try {
                this.mResults = this.mHelper.queryInventory(true, this.mProductList, this.mSubscriptionList);
                stop(new Exception("Error: failed to fetch inventory"));
            } catch (IabException e) {
                BBLogger.e(e);
                stop(e);
            }
        }

        public void onIabSetupFinished(IabResult result) {
            if (result.isFailure()) {
                stop(new Exception("Error: failed to setup iab helper"));
            } else if (this.mThread.isAlive()) {
                sendEmptyMessage(2);
            }
        }
    }
}
