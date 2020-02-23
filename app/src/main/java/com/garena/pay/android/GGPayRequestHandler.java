package com.garena.pay.android;

import android.content.Intent;
import com.beetalk.sdk.data.Result;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.helper.Utils;
import java.io.Serializable;
import java.util.List;

public abstract class GGPayRequestHandler implements Serializable {
    private static final long serialVersionUID = 1;
    private String channelId;
    protected GGPayClient client;
    protected String displayName;
    protected String iconUri;
    protected int imageResId;
    protected GGPayment.PaymentChannel mPaymentChannel;

    public abstract void onDestroy();

    public abstract boolean startPay(GGPayRequest gGPayRequest);

    public String getChannelId() {
        return this.channelId;
    }

    public void setChannelId(String channelId2) {
        this.channelId = channelId2;
    }

    protected GGPayRequestHandler(GGPayClient client2) {
        this.client = client2;
    }

    public GGPayment.PaymentChannel getPaymentChannel() {
        return this.mPaymentChannel;
    }

    public void setPaymentChannel(GGPayment.PaymentChannel mPaymentChannel2) {
        this.mPaymentChannel = mPaymentChannel2;
    }

    public List<GGPayment.Denomination> getBuyableItems() {
        if (this.mPaymentChannel != null) {
            return this.mPaymentChannel.getItems();
        }
        return null;
    }

    public boolean isDirectPay() {
        if (this.mPaymentChannel != null) {
            return this.mPaymentChannel.isDirectPay();
        }
        return false;
    }

    public int getImageResId() {
        return this.imageResId;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName2) {
        this.displayName = displayName2;
    }

    public String getIconUri() {
        return this.iconUri;
    }

    public void setIconUri(String iconUri2) {
        this.iconUri = iconUri2;
    }

    /* access modifiers changed from: protected */
    public boolean onActivityResult(int requestCode, int resultCode, Intent data, GGPayRequest pendingRequest) {
        if (resultCode == 0) {
            this.client.onHandlerResult(Result.createErrorResult(pendingRequest, GGErrorCode.ERROR, "Error Recd."));
            return true;
        } else if (resultCode != -1) {
            return true;
        } else {
            this.client.onHandlerResult(Result.createSuccessResult(pendingRequest, Utils.convertBundleToMap(data.getExtras())));
            return true;
        }
    }

    public void onNewIntent(Intent intent) {
    }

    public Double getPrice(GGPayRequest request) {
        return Double.valueOf(request.getVirtualCurrency().getCurrencyRate().doubleValue() * ((double) request.getChosenDenomination().getAppPoints().intValue()));
    }
}
