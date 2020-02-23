package com.beetalk.sdk.cache;

import android.content.Context;
import android.text.TextUtils;
import com.beetalk.sdk.networking.HttpRequestTask;
import com.garena.pay.android.data.GGChannelRequest;
import com.garena.pay.android.helper.DownloadTasks;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.Map;

public class PaymentChannelStorage {
    private static final int CACHE_VALID_PERIOD = 60000;
    private static volatile PaymentChannelStorage mInstance;
    /* access modifiers changed from: private */
    public String mChannelJson = null;
    /* access modifiers changed from: private */
    public long mCreateTime = 0;

    public static PaymentChannelStorage getInstance() {
        if (mInstance == null) {
            synchronized (PaymentChannelStorage.class) {
                if (mInstance == null) {
                    mInstance = new PaymentChannelStorage();
                }
            }
        }
        return mInstance;
    }

    public void getPaymentChannels(Context context, boolean forceUpdate, GGChannelRequest request, final HttpRequestTask.StringCallback callback) {
        if (forceUpdate || this.mChannelJson == null || System.currentTimeMillis() - this.mCreateTime > Constants.WATCHDOG_WAKE_TIMER) {
            Map<String, String> params = request.getParams(context);
            if (params == null) {
                callback.onTimeout();
            } else {
                DownloadTasks.getPaymentChannels(new HttpRequestTask.StringCallback() {
                    public void onResultObtained(String response) {
                        if (TextUtils.isEmpty(response) || response.contains("error")) {
                            PaymentChannelStorage.this.clear();
                        } else {
                            String unused = PaymentChannelStorage.this.mChannelJson = response;
                            long unused2 = PaymentChannelStorage.this.mCreateTime = System.currentTimeMillis();
                        }
                        callback.onResultObtained(response);
                    }

                    public void onTimeout() {
                        callback.onTimeout();
                    }
                }, params);
            }
        } else {
            callback.onResultObtained(this.mChannelJson);
        }
    }

    public void clear() {
        this.mCreateTime = 0;
        this.mChannelJson = null;
    }
}
