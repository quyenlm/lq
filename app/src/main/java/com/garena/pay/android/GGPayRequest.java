package com.garena.pay.android;

import android.app.Activity;
import com.garena.pay.android.data.GGPayment;
import com.garena.pay.android.data.PayMode;
import com.garena.pay.android.data.VirtualCurrency;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class GGPayRequest implements Serializable {
    private static final long serialVersionUID = 1;
    transient Activity activity;
    private String chosenChannelId = "";
    private GGPayment.Denomination chosenDenomination = null;
    private String clientId;
    private GGPayment clientPaymentRequest;
    private List<GGPayment.Denomination> denominations;
    private boolean isUseDefaultItemList = false;
    private PayMode mode;
    private Integer requestCode;
    private UUID requestId = UUID.randomUUID();
    private VirtualCurrency virtualCurrency;

    public boolean isUseDefaultItemList() {
        return this.isUseDefaultItemList;
    }

    public void setUseDefaultItemList(boolean isUseDefaultItemList2) {
        this.isUseDefaultItemList = isUseDefaultItemList2;
    }

    public GGPayment getClientPaymentRequest() {
        return this.clientPaymentRequest;
    }

    public void setClientPaymentRequest(GGPayment clientPaymentRequest2) {
        this.clientPaymentRequest = clientPaymentRequest2;
    }

    public GGPayRequest(Activity activity2) {
        this.activity = activity2;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity parentActivity) {
        this.activity = parentActivity;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId2) {
        this.clientId = clientId2;
    }

    public UUID getRequestId() {
        return this.requestId;
    }

    public void setRequestId(UUID requestId2) {
        this.requestId = requestId2;
    }

    public Integer getRequestCode() {
        return this.requestCode;
    }

    public void setRequestCode(Integer requestCode2) {
        this.requestCode = requestCode2;
    }

    public PayMode getMode() {
        return this.mode;
    }

    public void setMode(PayMode mode2) {
        this.mode = mode2;
    }

    public List<GGPayment.Denomination> getDenominations() {
        return this.denominations;
    }

    public void setDenominations(List<GGPayment.Denomination> denominations2) {
        this.denominations = denominations2;
    }

    public String getChosenChannelId() {
        return this.chosenChannelId;
    }

    public void setChosenChannelId(String chosenChannelId2) {
        this.chosenChannelId = chosenChannelId2;
    }

    public VirtualCurrency getVirtualCurrency() {
        return this.virtualCurrency;
    }

    public void setVirtualCurrency(VirtualCurrency virtualCurrency2) {
        this.virtualCurrency = virtualCurrency2;
    }

    public GGPayment.Denomination getChosenDenomination() {
        return this.chosenDenomination;
    }

    public void setChosenDenomination(GGPayment.Denomination chosenDenomination2) {
        this.chosenDenomination = chosenDenomination2;
    }

    public GGPayment.Denomination getDenominationById(String purchaseItemId) {
        for (GGPayment.Denomination item : this.denominations) {
            if (item.getItemId().equals(purchaseItemId)) {
                return item;
            }
        }
        return null;
    }
}
