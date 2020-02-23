package com.garena.pay.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.LinearLayout;
import com.beetalk.sdk.GGPlatform;
import com.beetalk.sdk.SDKConstants;
import com.beetalk.sdk.cache.PaymentChannelStorage;
import com.beetalk.sdk.cache.RedeemCache;
import com.beetalk.sdk.data.Result;
import com.beetalk.sdk.helper.BBLogger;
import com.garena.msdk.R;
import com.garena.pay.android.GGPayClient;

public class GGPayActivity extends Activity {
    static final /* synthetic */ boolean $assertionsDisabled = (!GGPayActivity.class.desiredAssertionStatus());
    /* access modifiers changed from: private */
    public GGPayClient client;
    LinearLayout layout;
    private Handler mHandler;
    /* access modifiers changed from: private */
    public GGPayRequest request;

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        new RedeemCache().clearRedeemCache();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BBLogger.d("GGPayActivity onCreate() start...", new Object[0]);
        GGPlatform.initialize((Activity) this);
        if (savedInstanceState != null) {
            this.client = (GGPayClient) savedInstanceState.getSerializable(SDKConstants.GG_EXTRA_PAY_CLIENT);
            this.request = (GGPayRequest) savedInstanceState.getSerializable(SDKConstants.GG_EXTRA_PAY_REQUEST);
        } else {
            this.client = new GGPayClient();
            this.request = (GGPayRequest) getIntent().getSerializableExtra(SDKConstants.GG_EXTRA_PAY_REQUEST);
        }
        if (this.request != null) {
            this.request.setActivity(this);
            if ($assertionsDisabled || this.client != null) {
                this.client.setContext(this);
                this.mHandler = new Handler(Looper.getMainLooper());
                this.layout = new LinearLayout(this);
                this.layout.setOrientation(1);
                this.layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                this.layout.setId(R.id.main_layout);
                this.layout.setMinimumHeight(getWindowManager().getDefaultDisplay().getHeight());
                this.layout.setBackgroundColor(0);
                setContentView(this.layout);
                this.client.setPaymentCompleteListener(new GGPayClient.OnPaymentComplete() {
                    public void onPaymentComplete(Result result) {
                        BBLogger.d("Recd result after payment completion: %s", result.getErrorMessage());
                        GGPayActivity.this.onPaymentComplete(result);
                    }
                });
                BBLogger.d("GGPayActivity onCreate() end...", new Object[0]);
                return;
            }
            throw new AssertionError();
        }
        BBLogger.d("GGPayActivity no request to process, finish...", new Object[0]);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        BBLogger.d("GGPayActivity onNewIntent", new Object[0]);
        this.client.onNewIntent(intent);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        this.client.onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SDKConstants.GG_EXTRA_PAY_CLIENT, this.client);
        outState.putSerializable(SDKConstants.GG_EXTRA_PAY_REQUEST, this.request);
    }

    /* access modifiers changed from: private */
    public void onPaymentComplete(Result result) {
        this.request = null;
        int resultCode = Result.isError(result.getResultCode()) ? -1 : 0;
        Intent intent = new Intent();
        intent.putExtra(SDKConstants.GG_EXTRA_RESULT_SERIALIZABLE, result);
        setResult(resultCode, intent);
        finish();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        BBLogger.d("GGPayActivity onPause()", new Object[0]);
        PaymentChannelStorage.getInstance().clear();
    }

    public void onBackPressed() {
        this.client.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                if (GGPayActivity.this.client != null && GGPayActivity.this.request != null) {
                    GGPayActivity.this.client.startOrResumePayment(GGPayActivity.this.request);
                }
            }
        }, 200);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (this.client != null) {
            this.client.onDestroy();
        }
    }
}
