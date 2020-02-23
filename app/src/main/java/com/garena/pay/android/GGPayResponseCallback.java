package com.garena.pay.android;

import com.garena.pay.android.data.TransactionInfo;
import com.garena.pay.android.data.TransactionStatus;

public interface GGPayResponseCallback {
    void onPaymentProcessed(TransactionStatus transactionStatus, Exception exc, TransactionInfo transactionInfo);
}
