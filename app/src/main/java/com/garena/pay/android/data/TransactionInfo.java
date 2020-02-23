package com.garena.pay.android.data;

import com.beetalk.sdk.data.Result;
import java.io.Serializable;

public class TransactionInfo implements Serializable {
    private static final long serialVersionUID = 1;
    private Integer appPoints;
    private Integer errorCode;
    private String icon;
    private String name;
    private GGPayment paymentRequest;
    private Long rebateId = 0L;
    private int remainingDays = 0;
    private Result.ResultCode resultCode;
    private String transactionError;
    private String transactionId;
    private TransactionStatus transactionStatus;

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon2) {
        this.icon = icon2;
    }

    public Integer getAppPoints() {
        return this.appPoints;
    }

    public void setAppPoints(Integer appPoints2) {
        this.appPoints = appPoints2;
    }

    public TransactionInfo(String transactionError2, TransactionStatus transactionStatus2, Integer errorCode2, Result.ResultCode resultCode2, GGPayment paymentRequest2) {
        this.transactionError = transactionError2;
        this.transactionStatus = transactionStatus2;
        this.resultCode = resultCode2;
        this.paymentRequest = paymentRequest2;
        this.errorCode = errorCode2;
    }

    public String getTransactionError() {
        return this.transactionError;
    }

    public void setTransactionError(String transactionError2) {
        this.transactionError = transactionError2;
    }

    public TransactionStatus getTransactionStatus() {
        return this.transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus2) {
        this.transactionStatus = transactionStatus2;
    }

    public Result.ResultCode getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(Result.ResultCode resultCode2) {
        this.resultCode = resultCode2;
    }

    public GGPayment getPaymentRequest() {
        return this.paymentRequest;
    }

    public void setPaymentRequest(GGPayment paymentRequest2) {
        this.paymentRequest = paymentRequest2;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(String transactionId2) {
        this.transactionId = transactionId2;
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public Long getRebateId() {
        return this.rebateId;
    }

    public void setRebateId(Long rebateId2) {
        this.rebateId = rebateId2;
    }

    public int getRemainingDays() {
        return this.remainingDays;
    }

    public void setRemainingDays(int remainingDays2) {
        this.remainingDays = remainingDays2;
    }
}
